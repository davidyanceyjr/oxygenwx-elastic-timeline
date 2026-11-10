#!/usr/bin/env python3
"""Bounded cycle tracker with ordered slice rosters and durable checkpoints."""
from __future__ import annotations

import argparse
import datetime as dt
import hashlib
import json
import pathlib
import re
from typing import Any

ROOT = pathlib.Path(__file__).resolve().parents[1]
STAGES = ("SLICE", "BOARDS", "DRAFT", "REVIEW", "EXECUTE", "CLOSEOUT")
PLANNING_STAGES = STAGES[:4]
TERMINAL_SLICE_STATES = {"DONE", "BLOCKED", "CANCELLED", "SUPERSEDED", "DEFERRED"}
SLICE_STATES = TERMINAL_SLICE_STATES | {"PLANNED", "ACTIVE"}
RESOLVED_DEPENDENCY_STATES = {"DONE", "CANCELLED", "SUPERSEDED"}
MANIFEST_START = "<!-- cycle-manifest:start -->"
MANIFEST_END = "<!-- cycle-manifest:end -->"
CHECKPOINT_START = "<!-- cycle-checkpoint:start -->"
CHECKPOINT_END = "<!-- cycle-checkpoint:end -->"


class WorkflowError(ValueError):
    """Raised when cycle records disagree or omit required fields."""


def _today() -> str:
    return dt.date.today().isoformat()


def _inside_repo(root: pathlib.Path, relative_path: str) -> pathlib.Path:
    path = (root / relative_path).resolve()
    if root.resolve() not in path.parents:
        raise WorkflowError(f"Path must remain inside the repository: {relative_path}")
    return path


def _read_fields(path: pathlib.Path) -> dict[str, str]:
    fields: dict[str, str] = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        match = re.match(r"^([\w ]+):\s*(.+)$", line)
        if match:
            fields[match.group(1).strip()] = match.group(2).strip()
    return fields


def _write_json_block(text: str, start: str, end: str, value: dict[str, Any]) -> str:
    block = f"{start}\n```json\n{json.dumps(value, indent=2, ensure_ascii=False)}\n```\n{end}"
    pattern = re.compile(re.escape(start) + r".*?" + re.escape(end), re.S)
    if not pattern.search(text):
        raise WorkflowError(f"Missing structured record marker: {start}")
    return pattern.sub(lambda _: block, text, count=1)


def _read_json_block(text: str, start: str, end: str) -> dict[str, Any]:
    pattern = re.compile(re.escape(start) + r"\s*```json\s*(.*?)\s*```\s*" + re.escape(end), re.S)
    match = pattern.search(text)
    if not match:
        raise WorkflowError(f"Missing or malformed structured record: {start}")
    try:
        value = json.loads(match.group(1))
    except json.JSONDecodeError as exc:
        raise WorkflowError(f"Invalid JSON in {start}: {exc}") from exc
    if not isinstance(value, dict):
        raise WorkflowError(f"Structured record must be a JSON object: {start}")
    return value


def load_plan_records(plan: pathlib.Path) -> tuple[str, dict[str, Any], dict[str, Any]]:
    text = plan.read_text(encoding="utf-8")
    return text, _read_json_block(text, MANIFEST_START, MANIFEST_END), _read_json_block(text, CHECKPOINT_START, CHECKPOINT_END)


def save_plan_records(plan: pathlib.Path, text: str, manifest: dict[str, Any], checkpoint: dict[str, Any]) -> None:
    text = _write_json_block(text, MANIFEST_START, MANIFEST_END, manifest)
    text = _write_json_block(text, CHECKPOINT_START, CHECKPOINT_END, checkpoint)
    plan.write_text(text, encoding="utf-8")


def _set_plan_state(text: str, state: str) -> str:
    updated, count = re.subn(r"(?m)^State: [A-Z]+\s*$", f"State: {state}", text, count=1)
    if count != 1:
        raise WorkflowError("Plan must contain exactly one top-level State field")
    return updated


def _write_current(root: pathlib.Path, state: str, cycle_id: str | None = None,
                   slice_ids: list[str] | None = None, active_slice: str | None = None,
                   stage: str | None = None, plan: str | None = None,
                   resume_state: str | None = None) -> None:
    lines = ["# Current Development Cycle", "", f"State: {state}"]
    if resume_state:
        lines.append(f"Resume state: {resume_state}")
    if cycle_id:
        lines.append(f"Cycle ID: {cycle_id}")
    if slice_ids:
        lines.append(f"Cycle slices: {', '.join(slice_ids)}")
        if len(slice_ids) == 1:
            lines.append(f"Roadmap item: {slice_ids[0]}")
        else:
            lines.append(f"Roadmap items: {', '.join(slice_ids)}")
    if active_slice:
        lines.append(f"Active slice: {active_slice}")
    elif slice_ids:
        lines.append("Active slice: None")
    if stage:
        lines.append(f"Stage: {stage}")
    if plan:
        lines.append(f"Plan: {plan}")
    lines += [f"Last updated: {_today()}", ""]
    (root / ".codex/current.md").write_text("\n".join(lines), encoding="utf-8")


def _normalize_slice_ids(fields: dict[str, str]) -> list[str]:
    raw = fields.get("Cycle slices") or fields.get("Roadmap items") or fields.get("Roadmap item", "")
    if not raw:
        return []
    return [part.strip() for part in raw.split(",") if part.strip()]


def _roadmap_items(root: pathlib.Path) -> dict[str, dict[str, Any]]:
    path = root / "docs/ROADMAP.md"
    if not path.exists():
        raise WorkflowError("Missing docs/ROADMAP.md")
    headings: list[tuple[str, str, int]] = []
    heading_re = re.compile(r"^###\s+(\S+)\s+—\s+(.+?)\s+—\s+(NEXT|ACTIVE|PLANNED|DONE|CANCELLED|SUPERSEDED|BLOCKED|DEFERRED)\s*$")
    lines = path.read_text(encoding="utf-8").splitlines()
    for index, line in enumerate(lines):
        match = heading_re.match(line)
        if match:
            headings.append((match.group(1), match.group(3), index))
    result: dict[str, dict[str, Any]] = {}
    for position, (slice_id, status, start) in enumerate(headings):
        if slice_id in result:
            raise WorkflowError(f"Duplicate roadmap slice ID: {slice_id}")
        end = headings[position + 1][2] if position + 1 < len(headings) else len(lines)
        body = "\n".join(lines[start:end])
        dependency_match = re.search(r"(?m)^\*\*Depends on:\*\*\s*(.+?)\s*$", body)
        dependency_text = dependency_match.group(1).strip() if dependency_match else "None"
        dependencies = [] if dependency_text.rstrip(".").lower() == "none" else [x.strip().rstrip(".") for x in re.split(r",|\band\b", dependency_text) if x.strip()]
        result[slice_id] = {"status": status, "dependencies": dependencies, "line": start + 1}
    return result


def _set_roadmap_status(root: pathlib.Path, slice_id: str, status: str) -> None:
    path = root / "docs/ROADMAP.md"
    lines = path.read_text(encoding="utf-8").splitlines()
    pattern = re.compile(rf"^(###\s+{re.escape(slice_id)}\s+—\s+.+?\s+—\s+)(NEXT|ACTIVE|PLANNED|DONE|CANCELLED|SUPERSEDED|BLOCKED|DEFERRED)(\s*)$")
    matches = [i for i, line in enumerate(lines) if pattern.match(line)]
    if len(matches) != 1:
        raise WorkflowError(f"Expected one roadmap heading for {slice_id}, found {len(matches)}")
    lines[matches[0]] = pattern.sub(rf"\g<1>{status}\g<3>", lines[matches[0]])
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def _latest_cycle_number(root: pathlib.Path) -> int:
    paths = []
    for directory in (root / ".codex/plans", root / ".codex/history", root / ".codex/history/plans"):
        if directory.is_dir():
            paths.extend(directory.glob("*.md"))
    numbers = []
    for path in paths:
        match = re.match(r"^(\d{3})-", path.name)
        if match:
            numbers.append(int(match.group(1)))
    return max(numbers, default=0)


def _record_state(plan_text: str) -> str:
    match = re.search(r"(?m)^State:\s*([A-Z]+)\s*$", plan_text)
    if not match:
        raise WorkflowError("Plan has no State field")
    return match.group(1)


def _validate_checkpoint(checkpoint: dict[str, Any], manifest: dict[str, Any]) -> None:
    required = {
        "cycle_id", "slice_ids", "stage", "prior_cycle_state", "accepted_boards",
        "completed_gates", "decisions", "open_questions", "completed_outputs",
        "evidence", "next_stage_read_set", "next_action",
    }
    missing = sorted(required - checkpoint.keys())
    if missing:
        raise WorkflowError("Checkpoint missing fields: " + ", ".join(missing))
    if checkpoint["cycle_id"] != manifest.get("cycle_id"):
        raise WorkflowError("Checkpoint cycle_id does not match the cycle manifest")
    ids = [entry.get("id") for entry in manifest.get("slices", [])]
    if checkpoint["slice_ids"] != ids:
        raise WorkflowError("Checkpoint slice_ids do not match the ordered cycle roster")
    if checkpoint["stage"] not in STAGES:
        raise WorkflowError(f"Invalid checkpoint stage: {checkpoint['stage']}")
    if checkpoint["prior_cycle_state"] not in {"IDLE", "PLANNED", "ACTIVE"}:
        raise WorkflowError("Checkpoint prior_cycle_state must be IDLE, PLANNED, or ACTIVE")
    for key in ("slice_ids", "accepted_boards", "completed_gates", "decisions", "open_questions", "completed_outputs", "evidence", "next_stage_read_set"):
        value = checkpoint[key]
        if not isinstance(value, list) or any(not isinstance(item, str) or not item.strip() for item in value):
            raise WorkflowError(f"Checkpoint {key} must be a list of non-empty strings")
    if not isinstance(checkpoint["next_action"], str) or not checkpoint["next_action"].strip():
        raise WorkflowError("Checkpoint next_action must be a non-empty string")
    if not checkpoint["next_stage_read_set"]:
        raise WorkflowError("Checkpoint next_stage_read_set must identify the next stage's exact inputs")


def _validate_manifest(root: pathlib.Path, manifest: dict[str, Any], plan_text: str,
                       require_final: bool = False) -> None:
    if manifest.get("schema_version") != 1:
        raise WorkflowError("Unsupported or missing cycle manifest schema_version")
    if not isinstance(manifest.get("cycle_id"), str) or not manifest["cycle_id"].strip():
        raise WorkflowError("Cycle manifest requires cycle_id")
    maturity = manifest.get("plan_maturity")
    if maturity not in {"DRAFT", "FINAL"}:
        raise WorkflowError("plan_maturity must be DRAFT or FINAL")
    if require_final and maturity != "FINAL":
        raise WorkflowError("Activation requires a FINAL plan")
    slices = manifest.get("slices")
    if not isinstance(slices, list) or not slices:
        raise WorkflowError("Cycle manifest requires a non-empty ordered slices list")
    ids: list[str] = []
    roadmap = _roadmap_items(root)
    for entry in slices:
        if not isinstance(entry, dict):
            raise WorkflowError("Every cycle roster entry must be an object")
        slice_id = entry.get("id")
        if not isinstance(slice_id, str) or not slice_id.strip():
            raise WorkflowError("Every cycle roster entry requires a stable id")
        if slice_id in ids:
            raise WorkflowError(f"Duplicate slice ID in cycle roster: {slice_id}")
        ids.append(slice_id)
        if slice_id not in roadmap:
            raise WorkflowError(f"Cycle slice {slice_id} is missing from docs/ROADMAP.md")
        if entry.get("status") not in SLICE_STATES:
            raise WorkflowError(f"Invalid status for cycle slice {slice_id}: {entry.get('status')}")
        exit_text = entry.get("exit")
        if require_final and (not isinstance(exit_text, str) or not exit_text.strip()):
            raise WorkflowError(f"Final plan requires a concrete exit for {slice_id}")
        dependencies = entry.get("depends_on")
        if not isinstance(dependencies, list) or any(not isinstance(dep, str) or not dep.strip() for dep in dependencies):
            raise WorkflowError(f"depends_on must be a list of slice IDs for {slice_id}")
        if len(set(dependencies)) != len(dependencies):
            raise WorkflowError(f"Duplicate dependency in roster entry {slice_id}")
        if set(dependencies) != set(roadmap[slice_id]["dependencies"]):
            raise WorkflowError(f"Cycle dependencies for {slice_id} do not match its roadmap dependencies")
        boards = entry.get("boards")
        if not isinstance(boards, dict):
            raise WorkflowError(f"Slice {slice_id} requires a boards record")
        behavior = boards.get("behavior")
        artwork = boards.get("ui_artwork")
        if not isinstance(behavior, dict):
            raise WorkflowError(f"Slice {slice_id} requires a behavior board record")
        if not isinstance(artwork, dict) or not isinstance(artwork.get("required"), bool):
            raise WorkflowError(f"Slice {slice_id} requires an explicit UI artwork applicability record")
        for label, board, needed in (("behavior", behavior, True), ("ui_artwork", artwork, artwork.get("required", False))):
            if not needed:
                continue
            board_path = board.get("path")
            if not isinstance(board_path, str) or not board_path.strip():
                if require_final:
                    raise WorkflowError(f"Final plan is missing the {label} board path for {slice_id}")
                continue
            if require_final and not _inside_repo(root, board_path).is_file():
                raise WorkflowError(f"Approved {label} board does not exist for {slice_id}: {board_path}")
            if require_final and (board.get("approved") is not True or not board.get("approved_by") or not board.get("approved_on")):
                raise WorkflowError(f"Final plan lacks recorded {label} board approval for {slice_id}")
            if require_final or board.get("approved") is True:
                artifacts = board.get("artifacts")
                if not isinstance(artifacts, list) or (require_final and not artifacts):
                    raise WorkflowError(f"Final plan must fingerprint the complete {label} board package for {slice_id}")
                artifact_paths = set()
                for artifact in artifacts:
                    if not isinstance(artifact, dict) or not isinstance(artifact.get("path"), str) or not isinstance(artifact.get("sha256"), str):
                        raise WorkflowError(f"Malformed {label} board fingerprint for {slice_id}")
                    artifact_path = _inside_repo(root, artifact["path"])
                    if not artifact_path.is_file():
                        raise WorkflowError(f"Approved board package file is missing: {artifact['path']}")
                    current_hash = hashlib.sha256(artifact_path.read_bytes()).hexdigest()
                    if current_hash != artifact["sha256"]:
                        raise WorkflowError(f"Approved board input changed after approval: {artifact['path']}")
                    artifact_paths.add(artifact["path"])
                if (require_final or board.get("approved") is True) and board_path not in artifact_paths:
                    raise WorkflowError(f"Primary {label} board path is not included in its fingerprinted package")
        if not isinstance(entry.get("outcome"), (str, type(None))):
            raise WorkflowError(f"Slice {slice_id} outcome must be text or null")
        if not isinstance(entry.get("evidence"), list) or any(not isinstance(p, str) for p in entry["evidence"]):
            raise WorkflowError(f"Slice {slice_id} evidence must be a list of paths")

    id_positions = {slice_id: index for index, slice_id in enumerate(ids)}
    for index, entry in enumerate(slices):
        for dep in entry["depends_on"]:
            if dep in id_positions:
                if id_positions[dep] >= index:
                    raise WorkflowError(f"Cycle dependency order is invalid: {entry['id']} depends on later/equal slice {dep}")
            else:
                if dep not in roadmap:
                    raise WorkflowError(f"Unknown dependency {dep} for {entry['id']}")
                if roadmap[dep]["status"] not in RESOLVED_DEPENDENCY_STATES:
                    raise WorkflowError(f"External dependency {dep} for {entry['id']} is unresolved")

    active = [entry["id"] for entry in slices if entry["status"] == "ACTIVE"]
    active_slice = manifest.get("active_slice")
    if active_slice:
        if active != [active_slice]:
            raise WorkflowError("active_slice must match the single ACTIVE roster entry")
    elif active:
        raise WorkflowError("Cycle roster has an ACTIVE slice but manifest active_slice is empty")


def _validate_approved_boards(root: pathlib.Path, manifest: dict[str, Any]) -> None:
    for entry in manifest["slices"]:
        for kind in ("behavior", "ui_artwork"):
            board = entry["boards"][kind]
            if kind == "ui_artwork" and not board["required"]:
                continue
            if board.get("approved") is not True or not board.get("approved_by") or not board.get("approved_on"):
                raise WorkflowError(f"Required {kind} board is not approved for {entry['id']}")
            path = board.get("path")
            artifacts = board.get("artifacts")
            if not isinstance(path, str) or not isinstance(artifacts, list) or not artifacts:
                raise WorkflowError(f"Required {kind} board package is incomplete for {entry['id']}")
            paths = set()
            for artifact in artifacts:
                artifact_path = _inside_repo(root, artifact["path"])
                if not artifact_path.is_file() or hashlib.sha256(artifact_path.read_bytes()).hexdigest() != artifact["sha256"]:
                    raise WorkflowError(f"Approved board input changed after approval: {artifact['path']}")
                paths.add(artifact["path"])
            if path not in paths:
                raise WorkflowError(f"Primary {kind} board path is not in the approved package for {entry['id']}")


def _validate_roadmap_state(root: pathlib.Path, state: str, resume_state: str | None,
                            manifest: dict[str, Any] | None, active_slice: str | None) -> None:
    items = _roadmap_items(root)
    next_items = [key for key, value in items.items() if value["status"] == "NEXT"]
    active_items = [key for key, value in items.items() if value["status"] == "ACTIVE"]
    if len(next_items) > 1:
        raise WorkflowError("Roadmap contains more than one NEXT slice")
    if len(active_items) > 1:
        raise WorkflowError("Roadmap contains more than one ACTIVE slice")
    effective_state = resume_state if state == "PAUSED" else state
    expected_active = active_slice if effective_state == "ACTIVE" else None
    if active_items != ([expected_active] if expected_active else []):
        raise WorkflowError("Roadmap ACTIVE status does not match the current cycle pointer")
    if manifest:
        for entry in manifest["slices"]:
            roadmap_status = items[entry["id"]]["status"]
            roster_status = entry["status"]
            if roster_status == "ACTIVE" and roadmap_status != "ACTIVE":
                raise WorkflowError(f"Roadmap status for active roster slice {entry['id']} is {roadmap_status}")
            if roster_status == "DONE" and roadmap_status != "DONE":
                raise WorkflowError(f"Roadmap status for completed slice {entry['id']} is {roadmap_status}")
            if roster_status in {"BLOCKED", "CANCELLED", "SUPERSEDED", "DEFERRED"} and roadmap_status != roster_status:
                raise WorkflowError(f"Roadmap status for {entry['id']} disagrees with the cycle roster")
            if roster_status == "PLANNED" and roadmap_status not in {"PLANNED", "NEXT"}:
                raise WorkflowError(f"Roadmap status for planned slice {entry['id']} is {roadmap_status}")


def validate_workflow(root: pathlib.Path = ROOT) -> None:
    """Validate the live pointer, checkpoint, roster, approvals, and roadmap state."""
    current = root / ".codex/current.md"
    if not current.is_file():
        raise WorkflowError("Missing .codex/current.md")
    fields = _read_fields(current)
    state = fields.get("State")
    if state not in {"IDLE", "PLANNED", "ACTIVE", "PAUSED"}:
        raise WorkflowError(f"Invalid current cycle State: {state}")
    if state == "IDLE":
        if any(key in fields for key in ("Cycle ID", "Cycle slices", "Roadmap item", "Roadmap items", "Plan", "Active slice", "Stage", "Resume state")):
            raise WorkflowError("IDLE current pointer must not retain cycle, plan, active-slice, stage, or resume fields")
        _validate_roadmap_state(root, state, None, None, None)
        return
    cycle_id = fields.get("Cycle ID")
    plan_name = fields.get("Plan")
    if not cycle_id or not plan_name:
        raise WorkflowError("Non-IDLE current pointer requires Cycle ID and Plan")
    plan = _inside_repo(root, plan_name)
    if not plan.is_file():
        raise WorkflowError(f"Current plan does not exist: {plan_name}")
    plan_text = plan.read_text(encoding="utf-8")
    expected_plan_state = fields.get("Resume state") if state == "PAUSED" else state
    if state == "PAUSED" and expected_plan_state not in {"PLANNED", "ACTIVE"}:
        raise WorkflowError("PAUSED pointer requires Resume state PLANNED or ACTIVE")
    if _record_state(plan_text) != expected_plan_state:
        raise WorkflowError("Current plan State does not match the current cycle pointer")

    # Preserve readability of pre-schema, single-slice records. New pointers always
    # contain Stage and Cycle slices and therefore cannot silently omit checkpoints.
    if MANIFEST_START not in plan_text or CHECKPOINT_START not in plan_text:
        if "Stage" in fields or "Cycle slices" in fields or state == "PAUSED":
            raise WorkflowError("New-format current pointer requires cycle manifest and checkpoint records")
        slice_ids = _normalize_slice_ids(fields)
        if len(slice_ids) != 1 or not fields.get("Roadmap item"):
            raise WorkflowError("Legacy current pointer compatibility is limited to one roadmap item")
        _validate_roadmap_state(root, state, None, None, slice_ids[0] if state == "ACTIVE" else None)
        return

    _, manifest, checkpoint = load_plan_records(plan)
    if manifest.get("cycle_id") != cycle_id:
        raise WorkflowError("Current Cycle ID does not match the referenced plan")
    plan_fields = _read_fields(plan)
    plan_cycle_id = plan_fields.get("Cycle ID", "").strip("`")
    if plan_cycle_id != cycle_id:
        raise WorkflowError("Plan header Cycle ID does not match the current pointer")
    if plan_fields.get("Plan maturity") != manifest.get("plan_maturity"):
        raise WorkflowError("Plan header maturity does not match the cycle manifest")
    slice_ids = [entry.get("id") for entry in manifest.get("slices", [])]
    plan_slice_ids = _normalize_slice_ids(plan_fields)
    if plan_slice_ids != slice_ids:
        raise WorkflowError("Plan header slice list does not match the ordered cycle roster")
    if _normalize_slice_ids(fields) != slice_ids:
        raise WorkflowError("Current cycle slice list does not match the ordered plan roster")
    active_slice = manifest.get("active_slice")
    current_active = fields.get("Active slice")
    expected_active = active_slice or "None"
    if current_active != expected_active:
        raise WorkflowError("Current Active slice does not match the plan manifest")
    if fields.get("Stage") != checkpoint.get("stage"):
        raise WorkflowError("Current Stage does not match the durable plan checkpoint")
    if checkpoint.get("stage") in PLANNING_STAGES and expected_plan_state != "PLANNED":
        raise WorkflowError("Planning-stage checkpoint requires a PLANNED cycle")
    if checkpoint.get("stage") in STAGES[4:] and expected_plan_state != "ACTIVE":
        raise WorkflowError("Execution/closeout checkpoint requires an ACTIVE cycle")
    _validate_checkpoint(checkpoint, manifest)
    require_final = expected_plan_state == "ACTIVE" or checkpoint.get("stage") == "REVIEW"
    _validate_manifest(root, manifest, plan_text, require_final=require_final)
    if checkpoint.get("stage") == "DRAFT":
        _validate_approved_boards(root, manifest)
    approved_paths = set(_board_approval_paths(manifest))
    if not set(checkpoint["accepted_boards"]).issubset(approved_paths):
        raise WorkflowError("Checkpoint lists a board as accepted without a matching approved roster record")
    _validate_roadmap_state(root, state, expected_plan_state if state == "PAUSED" else None,
                            manifest, active_slice)


def _template_plan(cycle_id: str, slice_ids: list[str], objective: str,
                   dependencies: dict[str, list[str]]) -> tuple[str, dict[str, Any], dict[str, Any]]:
    slices = []
    for slice_id in slice_ids:
        slices.append({
            "id": slice_id,
            "depends_on": dependencies.get(slice_id, []),
            "status": "PLANNED",
            "exit": "TODO: copy the observable roadmap exit for this slice",
            "boards": {
                "behavior": {"path": None, "approved": False, "approved_by": None, "approved_on": None, "artifacts": []},
                "ui_artwork": {"required": True, "path": None, "approved": False, "approved_by": None, "approved_on": None, "artifacts": []},
            },
            "outcome": None,
            "evidence": [],
        })
    manifest = {"schema_version": 1, "cycle_id": cycle_id, "plan_maturity": "DRAFT", "active_slice": None, "slices": slices}
    checkpoint = {
        "cycle_id": cycle_id,
        "slice_ids": slice_ids,
        "stage": "SLICE",
        "prior_cycle_state": "IDLE",
        "accepted_boards": [],
        "completed_gates": [],
        "decisions": [],
        "open_questions": [],
        "completed_outputs": [],
        "evidence": [],
        "next_stage_read_set": ["AGENTS.md", "docs/ROADMAP_SPECIFICATION.md", "docs/ROADMAP.md"],
        "next_action": "Confirm the eligible roadmap slices, dependencies, and objective-specific board needs.",
    }
    text = f"""# Cycle plan — {cycle_id}

State: PLANNED
Plan maturity: DRAFT
Cycle ID: `{cycle_id}`
Roadmap items: {', '.join(slice_ids)}
Objective: {objective}

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

{MANIFEST_START}
```json
{json.dumps(manifest, indent=2, ensure_ascii=False)}
```
{MANIFEST_END}

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

{CHECKPOINT_START}
```json
{json.dumps(checkpoint, indent=2, ensure_ascii=False)}
```
{CHECKPOINT_END}

## Objective, scope, invariants, verification, and evidence

TODO: complete the approved, bounded cycle plan before advancing to REVIEW.
"""
    return text, manifest, checkpoint


def _plan_path(root: pathlib.Path, fields: dict[str, str]) -> pathlib.Path:
    name = fields.get("Plan")
    if not name:
        raise WorkflowError("Current cycle pointer has no Plan path")
    path = (root / name).resolve()
    if root.resolve() not in path.parents:
        raise WorkflowError("Plan path must remain inside the repository")
    return path


def approve_board(root: pathlib.Path, slice_id: str, kind: str, paths: list[str],
                  approved_by: str, approved_on: str) -> None:
    fields = _read_fields(root / ".codex/current.md")
    paused = fields.get("State") == "PAUSED"
    if fields.get("State") != "PLANNED" and not paused:
        raise WorkflowError("Board approval can be recorded during planning or while a changed board is paused")
    if paused and fields.get("Resume state") not in {"PLANNED", "ACTIVE"}:
        raise WorkflowError("Paused cycle has no valid prior state")
    plan = _plan_path(root, fields)
    text, manifest, checkpoint = load_plan_records(plan)
    if fields.get("State") == "PLANNED" and checkpoint.get("stage") != "BOARDS":
        raise WorkflowError("Board approval can be recorded only at the BOARDS stage")
    if kind not in {"behavior", "ui_artwork"} or not paths or len(set(paths)) != len(paths) or not approved_by.strip() or not approved_on.strip():
        raise WorkflowError("Board approval requires kind, package paths, approver, and approval date")
    entry = next((item for item in manifest["slices"] if item["id"] == slice_id), None)
    if entry is None:
        raise WorkflowError(f"Slice {slice_id} is not in the current cycle roster")
    board = entry["boards"][kind]
    if kind == "ui_artwork" and not board["required"]:
        raise WorkflowError(f"UI artwork board is not applicable to {slice_id}")
    artifacts = []
    for relative in paths:
        path = _inside_repo(root, relative)
        if not path.is_file():
            raise WorkflowError(f"Board package file does not exist: {relative}")
        artifacts.append({"path": relative, "sha256": hashlib.sha256(path.read_bytes()).hexdigest()})
    board.update({"path": paths[0], "approved": True, "approved_by": approved_by.strip(),
                  "approved_on": approved_on.strip(), "artifacts": artifacts})
    checkpoint["accepted_boards"] = _append_unique(checkpoint["accepted_boards"], paths)
    checkpoint["completed_gates"] = _append_unique(checkpoint["completed_gates"], [f"{slice_id}_{kind}_board_approved"])
    checkpoint["completed_outputs"] = _append_unique(checkpoint["completed_outputs"], [f"Recorded {kind} board approval for {slice_id}."])
    checkpoint["decisions"] = _append_unique(checkpoint["decisions"], [f"{slice_id} {kind} board approval refreshed on {approved_on.strip()}."])
    if paused:
        question = f"Re-review {slice_id}'s updated {kind} board against its current plan before resuming."
        checkpoint["open_questions"] = _append_unique(checkpoint["open_questions"], [question])
    save_plan_records(plan, text, manifest, checkpoint)
    validate_workflow(root)


def start_cycle(root: pathlib.Path, cycle_id: str, slice_ids: list[str], objective: str) -> None:
    validate_workflow(root)
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") != "IDLE":
        raise WorkflowError("Cannot start: another cycle is planned, active, or paused")
    cycle_match = re.fullmatch(r"(\d{3})-([a-z0-9][a-z0-9-]{2,75})", cycle_id)
    if not cycle_match:
        raise WorkflowError("Cycle ID must be a three-digit chronological number and lowercase slug, e.g. 118-workflow-slice-roster")
    expected_number = _latest_cycle_number(root) + 1
    if int(cycle_match.group(1)) != expected_number:
        raise WorkflowError(f"Next cycle number is {expected_number:03d}; do not reuse or skip cycle IDs")
    if not slice_ids or len(set(slice_ids)) != len(slice_ids):
        raise WorkflowError("Start requires one or more unique roadmap slice IDs")
    path = root / ".codex/plans" / f"{cycle_id}.md"
    if path.exists() or (root / ".codex/history" / f"{cycle_id}.md").exists() or (root / ".codex/history/plans" / path.name).exists():
        raise WorkflowError(f"Plan already exists: {path}")
    roadmap = _roadmap_items(root)
    unknown = [slice_id for slice_id in slice_ids if slice_id not in roadmap]
    if unknown:
        raise WorkflowError("Unknown roadmap slice IDs: " + ", ".join(unknown))
    if roadmap[slice_ids[0]]["status"] != "NEXT":
        raise WorkflowError("The first cycle slice must be the current eligible NEXT roadmap item")
    later_ineligible = [slice_id for slice_id in slice_ids[1:] if roadmap[slice_id]["status"] != "PLANNED"]
    if later_ineligible:
        raise WorkflowError("Later cycle slices must be PLANNED: " + ", ".join(later_ineligible))
    text, manifest, _ = _template_plan(cycle_id, slice_ids, objective,
                                      {slice_id: roadmap[slice_id]["dependencies"] for slice_id in slice_ids})
    _validate_manifest(root, manifest, text, require_final=False)
    path.write_text(text, encoding="utf-8")
    _write_current(root, "PLANNED", cycle_id, slice_ids, None, "SLICE", str(path.relative_to(root)))
    validate_workflow(root)


def _board_approval_paths(manifest: dict[str, Any]) -> list[str]:
    result: list[str] = []
    for entry in manifest["slices"]:
        for kind in ("behavior", "ui_artwork"):
            board = entry["boards"][kind]
            if kind == "ui_artwork" and not board.get("required"):
                continue
            result.extend(artifact["path"] for artifact in board["artifacts"])
    return result


def activate_cycle(root: pathlib.Path, next_reads: list[str] | None = None, next_action: str | None = None) -> None:
    validate_workflow(root)
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") != "PLANNED":
        raise WorkflowError("No PLANNED cycle to activate")
    plan = _plan_path(root, fields)
    text, manifest, checkpoint = load_plan_records(plan)
    if _record_state(text) != "PLANNED":
        raise WorkflowError("Activation requires a PLANNED cycle plan")
    _validate_manifest(root, manifest, text, require_final=True)
    if any(entry["status"] != "PLANNED" for entry in manifest["slices"]):
        raise WorkflowError("Activation requires every in-scope slice to be PLANNED")
    if checkpoint.get("stage") != "REVIEW":
        raise WorkflowError("Activation requires the cycle checkpoint at REVIEW")
    if checkpoint.get("open_questions"):
        raise WorkflowError("Activation is blocked while owner questions remain open")
    eligible = _eligible_slice(manifest)
    if not eligible:
        raise WorkflowError("No dependency-eligible PLANNED slice is available to activate")
    manifest["active_slice"] = eligible["id"]
    eligible["status"] = "ACTIVE"
    checkpoint["stage"] = "EXECUTE"
    checkpoint["prior_cycle_state"] = "PLANNED"
    checkpoint["accepted_boards"] = _board_approval_paths(manifest)
    checkpoint["completed_gates"] = _append_unique(checkpoint["completed_gates"], ["all_slice_boards_approved", "final_plan_reviewed"])
    checkpoint["completed_outputs"] = _append_unique(checkpoint["completed_outputs"], [f"Activated {eligible['id']} for execution."])
    checkpoint["next_stage_read_set"] = next_reads or ["AGENTS.md", ".codex/current.md", fields["Plan"], "docs/ROADMAP_SPECIFICATION.md"]
    checkpoint["next_action"] = next_action or f"Execute {eligible['id']} within its approved scope and capture the authorized evidence."
    text = _set_plan_state(text, "ACTIVE")
    save_plan_records(plan, text, manifest, checkpoint)
    _set_roadmap_status(root, eligible["id"], "ACTIVE")
    _write_current(root, "ACTIVE", fields["Cycle ID"], [entry["id"] for entry in manifest["slices"]], eligible["id"], "EXECUTE", fields["Plan"])
    validate_workflow(root)


def _append_unique(existing: list[str], additions: list[str]) -> list[str]:
    return list(dict.fromkeys([*existing, *additions]))


def _eligible_slice(manifest: dict[str, Any]) -> dict[str, Any] | None:
    entries = manifest["slices"]
    positions = {entry["id"]: i for i, entry in enumerate(entries)}
    for entry in entries:
        if entry["status"] != "PLANNED":
            continue
        ready = True
        for dep in entry["depends_on"]:
            if dep in positions:
                ready = ready and entries[positions[dep]]["status"] in RESOLVED_DEPENDENCY_STATES
            else:
                # The dependency's roadmap state was validated before this helper runs.
                ready = ready and True
        if ready:
            return entry
    return None


def update_checkpoint(root: pathlib.Path, next_reads: list[str], next_action: str,
                      outputs: list[str] | None = None, gates: list[str] | None = None,
                      accepted_boards: list[str] | None = None, decisions: list[str] | None = None,
                      open_questions: list[str] | None = None, evidence: list[str] | None = None) -> None:
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") not in {"PLANNED", "ACTIVE", "PAUSED"}:
        raise WorkflowError("No open cycle checkpoint to update")
    plan = _plan_path(root, fields)
    text, manifest, checkpoint = load_plan_records(plan)
    checkpoint["next_stage_read_set"] = next_reads
    checkpoint["next_action"] = next_action
    for key, value in (("completed_outputs", outputs), ("completed_gates", gates),
                       ("accepted_boards", accepted_boards), ("decisions", decisions),
                       ("open_questions", open_questions), ("evidence", evidence)):
        if value is not None:
            checkpoint[key] = value
    save_plan_records(plan, text, manifest, checkpoint)
    validate_workflow(root)


def advance_stage(root: pathlib.Path, next_reads: list[str], next_action: str,
                  outputs: list[str] | None = None, gates: list[str] | None = None,
                  decisions: list[str] | None = None, evidence: list[str] | None = None) -> None:
    if not outputs:
        raise WorkflowError("Stage advancement requires a saved output from the current stage")
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") in {"PLANNED", "ACTIVE"}:
        validate_workflow(root)
    if fields.get("State") == "ACTIVE":
        plan = _plan_path(root, fields)
        text, manifest, checkpoint = load_plan_records(plan)
        if checkpoint.get("stage") != "EXECUTE":
            raise WorkflowError("Only an EXECUTE cycle can advance to CLOSEOUT")
        if checkpoint.get("open_questions"):
            raise WorkflowError("CLOSEOUT is blocked while owner questions remain open")
        if manifest.get("active_slice") or any(entry["status"] not in TERMINAL_SLICE_STATES for entry in manifest["slices"]):
            raise WorkflowError("CLOSEOUT requires every included slice completed or assigned a terminal outcome")
        checkpoint["stage"] = "CLOSEOUT"
        checkpoint["prior_cycle_state"] = "ACTIVE"
        checkpoint["next_stage_read_set"] = next_reads
        checkpoint["next_action"] = next_action
        if outputs is not None:
            checkpoint["completed_outputs"] = _append_unique(checkpoint["completed_outputs"], outputs)
        if gates is not None:
            checkpoint["completed_gates"] = _append_unique(checkpoint["completed_gates"], gates)
        if decisions is not None:
            checkpoint["decisions"] = _append_unique(checkpoint["decisions"], decisions)
        if evidence is not None:
            checkpoint["evidence"] = _append_unique(checkpoint["evidence"], evidence)
        save_plan_records(plan, text, manifest, checkpoint)
        _write_current(root, "ACTIVE", fields["Cycle ID"], [item["id"] for item in manifest["slices"]], None, "CLOSEOUT", fields["Plan"])
        validate_workflow(root)
        return
    if fields.get("State") != "PLANNED":
        raise WorkflowError("Only a PLANNED cycle advances planning stages; ACTIVE cycles advance EXECUTE to CLOSEOUT")
    plan = _plan_path(root, fields)
    text, manifest, checkpoint = load_plan_records(plan)
    stage = checkpoint["stage"]
    if stage not in PLANNING_STAGES[:-1]:
        raise WorkflowError(f"Cannot advance planning stage from {stage}; REVIEW proceeds through activation")
    if checkpoint.get("open_questions"):
        raise WorkflowError("Cannot advance while owner questions remain open")
    next_stage = STAGES[STAGES.index(stage) + 1]
    if next_stage == "DRAFT":
        _validate_manifest(root, manifest, text, require_final=False)
        for entry in manifest["slices"]:
            board = entry["boards"]["behavior"]
            if board.get("approved") is not True or not board.get("approved_by") or not board.get("approved_on"):
                raise WorkflowError(f"Cannot draft before behavior board approval for {entry['id']}")
            artwork = entry["boards"]["ui_artwork"]
            if artwork["required"] and (artwork.get("approved") is not True or not artwork.get("approved_by") or not artwork.get("approved_on")):
                raise WorkflowError(f"Cannot draft before UI artwork board approval for {entry['id']}")
    if next_stage == "REVIEW":
        _validate_manifest(root, manifest, text, require_final=True)
    checkpoint["stage"] = next_stage
    checkpoint["prior_cycle_state"] = "PLANNED"
    checkpoint["next_stage_read_set"] = next_reads
    checkpoint["next_action"] = next_action
    if outputs is not None:
        checkpoint["completed_outputs"] = _append_unique(checkpoint["completed_outputs"], outputs)
    if gates is not None:
        checkpoint["completed_gates"] = _append_unique(checkpoint["completed_gates"], gates)
    if decisions is not None:
        checkpoint["decisions"] = _append_unique(checkpoint["decisions"], decisions)
    if evidence is not None:
        checkpoint["evidence"] = _append_unique(checkpoint["evidence"], evidence)
    text = _set_plan_state(text, "PLANNED")
    save_plan_records(plan, text, manifest, checkpoint)
    _write_current(root, "PLANNED", fields["Cycle ID"], [entry["id"] for entry in manifest["slices"]], None, next_stage, fields["Plan"])
    validate_workflow(root)


def pause_cycle(root: pathlib.Path) -> None:
    fields = _read_fields(root / ".codex/current.md")
    state = fields.get("State")
    if state not in {"PLANNED", "ACTIVE"}:
        raise WorkflowError("Only a PLANNED or ACTIVE cycle can be paused")
    validate_workflow(root)
    ids = _normalize_slice_ids(fields)
    _write_current(root, "PAUSED", fields["Cycle ID"], ids,
                   None if fields.get("Active slice") == "None" else fields.get("Active slice"),
                   fields["Stage"], fields["Plan"], resume_state=state)
    validate_workflow(root)


def resume_cycle(root: pathlib.Path) -> None:
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") != "PAUSED":
        raise WorkflowError("No PAUSED cycle to resume")
    validate_workflow(root)
    plan = _plan_path(root, fields)
    _, _, checkpoint = load_plan_records(plan)
    if checkpoint["open_questions"]:
        raise WorkflowError("Resolve and record every open owner question before resuming")
    resume_state = fields["Resume state"]
    ids = _normalize_slice_ids(fields)
    active = fields.get("Active slice")
    _write_current(root, resume_state, fields["Cycle ID"], ids,
                   None if active == "None" else active, fields["Stage"], fields["Plan"])
    validate_workflow(root)


def finish_slice(root: pathlib.Path, slice_id: str, status: str, outcome: str,
                 evidence: list[str]) -> None:
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") != "ACTIVE":
        raise WorkflowError("A slice can be closed only in an ACTIVE cycle")
    if status not in TERMINAL_SLICE_STATES:
        raise WorkflowError("Slice close status must be DONE or a recorded terminal outcome")
    if not outcome.strip():
        raise WorkflowError("Every closed slice requires an exact outcome")
    if not evidence:
        raise WorkflowError("Every closed slice requires at least one evidence path or recorded evidence artifact")
    missing_evidence = [path for path in evidence if not _inside_repo(root, path).exists()]
    if missing_evidence:
        raise WorkflowError("Slice evidence paths do not exist: " + ", ".join(missing_evidence))
    validate_workflow(root)
    plan = _plan_path(root, fields)
    text, manifest, checkpoint = load_plan_records(plan)
    entry = next((item for item in manifest["slices"] if item["id"] == slice_id), None)
    if entry is None:
        raise WorkflowError(f"{slice_id} is not in the current cycle roster")
    was_active = manifest.get("active_slice") == slice_id
    if not was_active and (status == "DONE" or entry["status"] != "PLANNED"):
        raise WorkflowError("Only the ACTIVE slice can be marked DONE; an incomplete outcome may be recorded for a PLANNED slice")
    entry["status"] = status
    entry["outcome"] = outcome.strip()
    entry["evidence"] = evidence
    _set_roadmap_status(root, slice_id, status)
    next_entry = None
    if was_active:
        manifest["active_slice"] = None
    if not manifest.get("active_slice"):
        next_entry = _eligible_slice(manifest)
        if next_entry:
            next_entry["status"] = "ACTIVE"
            manifest["active_slice"] = next_entry["id"]
            _set_roadmap_status(root, next_entry["id"], "ACTIVE")
    checkpoint["completed_outputs"] = _append_unique(checkpoint["completed_outputs"], [f"Closed slice {slice_id} as {status}."])
    checkpoint["evidence"] = _append_unique(checkpoint["evidence"], evidence)
    checkpoint["next_stage_read_set"] = [".codex/current.md", fields["Plan"], "docs/ROADMAP.md"]
    if manifest.get("active_slice"):
        checkpoint["next_action"] = f"Continue the active slice {manifest['active_slice']} in dependency order."
    elif next_entry:
        checkpoint["next_action"] = f"Continue with {next_entry['id']} in dependency order."
    else:
        checkpoint["next_action"] = "Record any remaining slice outcomes, then advance to CLOSEOUT."
    save_plan_records(plan, text, manifest, checkpoint)
    _write_current(root, "ACTIVE", fields["Cycle ID"], [item["id"] for item in manifest["slices"]],
                   manifest.get("active_slice"), "EXECUTE", fields["Plan"])
    validate_workflow(root)


def close_cycle(root: pathlib.Path, summary: str, verification: list[str]) -> tuple[pathlib.Path, pathlib.Path]:
    fields = _read_fields(root / ".codex/current.md")
    if fields.get("State") != "ACTIVE":
        raise WorkflowError("No ACTIVE cycle to close")
    validate_workflow(root)
    plan = _plan_path(root, fields)
    text, manifest, checkpoint = load_plan_records(plan)
    if checkpoint.get("stage") != "CLOSEOUT":
        raise WorkflowError("Cycle close requires the CLOSEOUT checkpoint")
    if checkpoint.get("open_questions"):
        raise WorkflowError("Cycle close is blocked while owner questions remain open")
    unfinished = [entry["id"] for entry in manifest["slices"] if entry["status"] not in TERMINAL_SLICE_STATES]
    if unfinished:
        raise WorkflowError("Close requires every slice completed or assigned a recorded terminal outcome: " + ", ".join(unfinished))
    if any(not entry.get("outcome") or not entry.get("evidence") for entry in manifest["slices"]):
        raise WorkflowError("Close requires an outcome and evidence record for every roster slice")
    if not summary.strip():
        raise WorkflowError("Close requires a cycle summary")
    if not verification or any(not item.strip() for item in verification):
        raise WorkflowError("Close requires non-empty exact verification results")
    cycle_id = manifest["cycle_id"]
    history = root / ".codex/history" / f"{cycle_id}.md"
    archive = root / ".codex/history/plans" / plan.name
    if history.exists() or archive.exists():
        raise WorkflowError("Refusing to overwrite archived cycle records")
    history.parent.mkdir(parents=True, exist_ok=True)
    archive.parent.mkdir(parents=True, exist_ok=True)
    today = _today()
    outcomes = "\n".join(
        f"- **{entry['id']} — {entry['status']}:** {entry['outcome']} Evidence: {', '.join(entry['evidence'])}."
        for entry in manifest["slices"]
    )
    manifest["closeout"] = {
        "closed_on": today,
        "summary": summary.strip(),
        "verification": verification,
        "evidence_directory": f".codex/test-artifacts/{cycle_id}/",
    }
    checkpoint["completed_outputs"] = _append_unique(checkpoint["completed_outputs"], ["Cycle closeout summary and verification archived."])
    checkpoint["evidence"] = _append_unique(checkpoint["evidence"], [f".codex/test-artifacts/{cycle_id}/README.md", str(history.relative_to(root))])
    history.write_text(
        f"# Cycle {cycle_id}\n\nDate: {today}\nRoadmap items: {', '.join(entry['id'] for entry in manifest['slices'])}\n\n"
        f"## Outcome\n\n{summary.strip()}\n\n## Slice outcomes\n\n{outcomes}\n\n"
        f"## Verification actually performed\n\n" + "\n".join(f"- {item}" for item in verification) +
        f"\n\n## Evidence and limits\n\nCycle evidence: `.codex/test-artifacts/{cycle_id}/`. Archived plan: `.codex/history/plans/{plan.name}`. See the plan for stage checkpoints and exact boundaries.\n",
        encoding="utf-8",
    )
    text = _set_plan_state(text, "CLOSED")
    checkpoint["stage"] = "CLOSEOUT"
    checkpoint["prior_cycle_state"] = "ACTIVE"
    save_plan_records(plan, text, manifest, checkpoint)
    plan.replace(archive)
    _write_current(root, "IDLE")
    validate_workflow(root)
    return history, archive


def _cmd_start(args: argparse.Namespace) -> None:
    try:
        start_cycle(ROOT, args.id, args.slice, args.objective)
        print(f"Planned {args.id} with ordered slices: {', '.join(args.slice)}")
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def _cmd_activate(args: argparse.Namespace) -> None:
    try:
        activate_cycle(ROOT, args.next_read, args.next_action)
        print("Activated " + _read_fields(ROOT / ".codex/current.md")["Cycle ID"])
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def _cmd_checkpoint(args: argparse.Namespace) -> None:
    try:
        open_questions = [] if args.clear_open_questions else args.open_question
        update_checkpoint(ROOT, args.next_read, args.next_action, args.output, args.gate,
                          args.accepted_board, args.decision, open_questions, args.evidence)
        print("Checkpoint updated")
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def _cmd_advance(args: argparse.Namespace) -> None:
    try:
        advance_stage(ROOT, args.next_read, args.next_action, args.output, args.gate, args.decision, args.evidence)
        print("Advanced to " + _read_fields(ROOT / ".codex/current.md")["Stage"])
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def _cmd_finish_slice(args: argparse.Namespace) -> None:
    try:
        finish_slice(ROOT, args.slice, args.status, args.outcome, args.evidence)
        print(f"Recorded {args.slice} as {args.status}")
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def _cmd_approve_board(args: argparse.Namespace) -> None:
    try:
        approve_board(ROOT, args.slice, args.kind, args.file, args.approved_by, args.approved_on)
        print(f"Recorded approved {args.kind} board package for {args.slice}")
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def _cmd_close(args: argparse.Namespace) -> None:
    try:
        history, archive = close_cycle(ROOT, args.summary, args.verification)
        print(f"Closed cycle: {history.relative_to(ROOT)}; plan: {archive.relative_to(ROOT)}")
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


def main() -> None:
    parser = argparse.ArgumentParser(description="Oxygen Time Lab cycle and checkpoint tracker")
    sub = parser.add_subparsers(dest="command", required=True)

    start = sub.add_parser("start", help="Create a PLANNED cycle with one or more roadmap slices")
    start.add_argument("--id", required=True)
    start.add_argument("--roadmap", "--slice", dest="slice", action="append", required=True)
    start.add_argument("--objective", required=True)
    start.set_defaults(func=_cmd_start)

    activate = sub.add_parser("activate", help="Activate the first eligible slice after plan and board gates")
    activate.add_argument("--next-read", action="append")
    activate.add_argument("--next-action")
    activate.set_defaults(func=_cmd_activate)

    checkpoint = sub.add_parser("checkpoint", help="Update the current stage handoff")
    checkpoint.add_argument("--next-read", action="append", required=True)
    checkpoint.add_argument("--next-action", required=True)
    checkpoint.add_argument("--output", action="append")
    checkpoint.add_argument("--gate", action="append")
    checkpoint.add_argument("--accepted-board", action="append")
    checkpoint.add_argument("--decision", action="append")
    checkpoint.add_argument("--open-question", action="append")
    checkpoint.add_argument("--clear-open-questions", action="store_true")
    checkpoint.add_argument("--evidence", action="append")
    checkpoint.set_defaults(func=_cmd_checkpoint)

    advance = sub.add_parser("advance", help="Save a planning-stage output and advance one stage")
    advance.add_argument("--next-read", action="append", required=True)
    advance.add_argument("--next-action", required=True)
    advance.add_argument("--output", action="append")
    advance.add_argument("--gate", action="append")
    advance.add_argument("--decision", action="append")
    advance.add_argument("--evidence", action="append")
    advance.set_defaults(func=_cmd_advance)

    pause = sub.add_parser("pause", help="Pause the same cycle at its saved stage")
    pause.set_defaults(func=lambda _args: _dispatch_simple(pause_cycle, "Paused"))
    resume = sub.add_parser("resume", help="Resume the same cycle at its saved stage")
    resume.set_defaults(func=lambda _args: _dispatch_simple(resume_cycle, "Resumed"))

    finish = sub.add_parser("finish-slice", help="Record a completed or incomplete slice outcome")
    finish.add_argument("--slice", required=True)
    finish.add_argument("--status", choices=sorted(TERMINAL_SLICE_STATES), required=True)
    finish.add_argument("--outcome", required=True)
    finish.add_argument("--evidence", action="append", required=True)
    finish.set_defaults(func=_cmd_finish_slice)

    board = sub.add_parser("approve-board", help="Record approval and fingerprints for a board package")
    board.add_argument("--slice", required=True)
    board.add_argument("--kind", choices=("behavior", "ui_artwork"), required=True)
    board.add_argument("--file", action="append", required=True)
    board.add_argument("--approved-by", required=True)
    board.add_argument("--approved-on", required=True)
    board.set_defaults(func=_cmd_approve_board)

    close = sub.add_parser("close", help="Archive a fully recorded cycle")
    close.add_argument("--summary", required=True)
    close.add_argument("--verification", action="append", required=True)
    close.set_defaults(func=_cmd_close)

    args = parser.parse_args()
    args.func(args)


def _dispatch_simple(operation: Any, message: str) -> None:
    try:
        operation(ROOT)
        print(message)
    except WorkflowError as exc:
        raise SystemExit(str(exc)) from exc


if __name__ == "__main__":
    main()
