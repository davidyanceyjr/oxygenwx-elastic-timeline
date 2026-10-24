#!/usr/bin/env python3
"""Minimal Oxygen-style bounded cycle tracker for this independent prototype.

Maintains persistent text records in .codex, never rewrites existing history.
"""
import argparse
import datetime
import pathlib
import re

ROOT = pathlib.Path(__file__).resolve().parents[1]
CODEX = ROOT / ".codex"
CURRENT = CODEX / "current.md"


def fields() -> dict[str, str]:
    result = {}
    for line in CURRENT.read_text(encoding="utf-8").splitlines():
        match = re.match(r"^([\w ]+):\s*(.+)$", line)
        if match:
            result[match.group(1).strip()] = match.group(2).strip()
    return result


def set_current(state: str, cycle: str | None = None,
                roadmap: str | None = None, plan: str | None = None) -> None:
    lines = ["# Current Development Cycle", "", f"State: {state}"]
    if cycle:
        lines += [f"Cycle ID: {cycle}"]
    if roadmap:
        lines += [f"Roadmap item: {roadmap}"]
    if plan:
        lines += [f"Plan: {plan}"]
    lines += [f"Last updated: {datetime.date.today().isoformat()}", ""]
    CURRENT.write_text("\n".join(lines), encoding="utf-8")


def cmd_start(args: argparse.Namespace) -> None:
    if fields().get("State") != "IDLE":
        raise SystemExit("Cannot start: another cycle is planned/active")
    if not re.fullmatch(r"[a-z0-9][a-z0-9-]{2,75}", args.id):
        raise SystemExit("ID must be a lowercase slug, e.g. 002-touch-evidence")
    path = CODEX / "plans" / f"{args.id}.md"
    if path.exists():
        raise SystemExit(f"Plan exists: {path}")
    path.write_text(f"""# Cycle plan — {args.id}

State: PLANNED
Roadmap: {args.roadmap}
Objective: {args.objective}

## Production boundary

TODO: list exact files that may be changed.

## Functional invariants

TODO: list facts and navigation behaviors that must not change.

## Steps

- [ ] Scope and baseline inspection
- [ ] One bounded implementation
- [ ] Focused checks, installed review and evidence

## Acceptance/evidence

TODO: exact tests, device/font/RTL/effects conditions, screenshot path.

## Risks / assumptions

TODO

## Out of scope

TODO
""", encoding="utf-8")
    set_current("PLANNED", args.id, args.roadmap, str(path.relative_to(ROOT)))
    print(f"Planned {args.id}; edit and review {path} before activate")


def cmd_activate() -> None:
    info = fields()
    if info.get("State") != "PLANNED":
        raise SystemExit("No planned cycle to activate")
    plan = ROOT / info["Plan"]
    if not plan.exists():
        raise SystemExit("Plan file missing")
    content = plan.read_text(encoding="utf-8")
    plan.write_text(content.replace("State: PLANNED", "State: ACTIVE", 1), encoding="utf-8")
    set_current("ACTIVE", info["Cycle ID"], info.get("Roadmap item"), info["Plan"])
    print(f"Activated {info['Cycle ID']}")


def cmd_close(args: argparse.Namespace) -> None:
    info = fields()
    if info.get("State") != "ACTIVE":
        raise SystemExit("No active cycle to close")
    slug = info["Cycle ID"]
    source = ROOT / info["Plan"]
    target = CODEX / "history" / f"{slug}.md"
    archive = CODEX / "history" / "plans" / source.name
    if target.exists() or archive.exists():
        raise SystemExit("Refusing to overwrite archived cycle")
    verification = args.verification or "Not recorded; do not claim unverified gates passed."
    target.write_text(
        f"# Cycle {slug}\n\nDate: {datetime.date.today()}\n"
        f"Roadmap: {info.get('Roadmap item', 'unspecified')}\n\n"
        f"## Outcome\n\n{args.summary}\n\n"
        f"## Verification actually performed\n\n{verification}\n\n"
        "## Follow-up\n\nState remaining gaps before declaring full acceptance.\n",
        encoding="utf-8",
    )
    archive.parent.mkdir(parents=True, exist_ok=True)
    source.replace(archive)
    set_current("IDLE")
    print(f"Archived {slug}: {target.relative_to(ROOT)}")


def main() -> None:
    parser = argparse.ArgumentParser()
    sub = parser.add_subparsers(dest="command", required=True)
    start = sub.add_parser("start")
    start.add_argument("--id", required=True)
    start.add_argument("--roadmap", required=True)
    start.add_argument("--objective", required=True)
    sub.add_parser("activate")
    close = sub.add_parser("close")
    close.add_argument("--summary", required=True)
    close.add_argument("--verification")
    args = parser.parse_args()
    if args.command == "start":
        cmd_start(args)
    elif args.command == "activate":
        cmd_activate()
    else:
        cmd_close(args)


if __name__ == "__main__":
    main()
