# AGENTS.md — Oxygen Time Lab

This repository is the successor OxygenWX app, developed in stages from an isolated synthetic-data prototype. It is separate from the deprecated oxygenWX application; the WX roadmap does not modify that application's files or navigation.

## Authority

1. `docs/SPECIFICATION.md` — controlling prototype scope and semantic invariants; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` — adopted experience principles, interaction contract, and experience-level acceptance criteria. If they conflict, `docs/SPECIFICATION.md` controls unless a bounded owner decision updates it.
2. `docs/ROADMAP_SPECIFICATION.md` — required roadmap statuses, slice format, evidence, and replanning rules.
3. `docs/ROADMAP.md` — bounded ordered follow-ups conforming to the roadmap specification.
4. `docs/UI_DEVELOPMENT_WORKFLOW.md` — installed visual acceptance.
5. `.codex/current.md` and its referenced bounded plan — cycle state, ordered slice roster, workflow stage, and checkpoint.

## Task-scoped workflow context

For an actionable request, begin with `.codex/current.md`, `.codex/README.md`,
and `python scripts/dev.py workflow`, then follow the installed `oxygen-*`
skill for the current stage. Read only its routine set: the current plan,
accepted boards, exact dependency records, relevant authority passages, and
affected files. General questions and concepts stay in `IDLE` and do not create
a cycle. Read full specifications, unrelated roadmap sections, closed plans,
or history only when a named scope, dependency, conflict, regression question,
or audit needs them. Keep every required authority and evidence recoverable.

At each workflow boundary, save the cycle checkpoint before advancing. It
records the cycle and ordered slice IDs, stage and prior cycle state, accepted
boards and gates, decisions and open questions, completed outputs, evidence,
exact next-stage read set, and next action. `PAUSED` preserves the same cycle,
roster, roadmap status, and stage; `Resume state` records whether to restore
`PLANNED` or `ACTIVE`. Resume without resetting IDs or repeating accepted
gates. Only reopen an earlier gate when its approved input changes or evidence
contradicts it.

## Required cycle lifecycle

Before app implementation or behavior changes:

1. Read the current stage's routine sources defined above and by its installed
   `oxygen-*` skill. For app implementation or behavior changes, also read the
   relevant product and UI authority passages and the referenced bounded plan.
2. Run `python scripts/dev.py workflow`; it validates the current pointer,
   plan roster, checkpoint, dependencies, board approvals, and roadmap state.
3. Ensure a bounded plan exists under `.codex/plans/`. A cycle may contain one or more stable roadmap slice IDs in dependency order. A PLANNED plan is eligible for final review and activation only after each included slice has its required approved board(s), its own exit, and finished plan record. If the board gate is unmet, keep the plan an unfinalized draft and resolve that prerequisite first. If the current state is IDLE, select and plan a roadmap slice before implementation.
4. Before finalizing any cycle plan for any project roadmap or workstream, ensure it has an objective-specific behavior board and, for UI/visual work, a UI artwork board; one combined slice board may cover both if it makes each reviewable. This project-wide gate applies to every slice, including future roadmaps, and covers code, text/documentation, interface, and logic changes. The product owner must approve the required board(s). Record their paths, approval, constraints, and relationship to scope and acceptance in the plan. Behavior boards may use finite-state-machine-like flows with text and images, annotated content maps, contracts, schemas, sequences, or boundary diagrams. UI artwork boards show the intended appearance and affected states. A prompt, target path, existing implementation, or screenshot alone does not satisfy the board or approval requirement. A draft may be prepared while its board is pending, but it cannot be finalized or activated. Boards guide scope and acceptance; they do not replace implementation verification or installed acceptance evidence.
5. Stay within the plan's scope, invariants, acceptance criteria, and exclusions. Do not silently expand a slice.
6. Preserve actual evidence under `.codex/test-artifacts/<cycle-id>/`.
7. Inspect `git diff --check` and the final diff, then close completed work into `.codex/history/` with exact verification results and limitations.

Cycle lifecycle states are `IDLE`, `PLANNED`, `ACTIVE`, and `PAUSED`; stages
are `SLICE`, `BOARDS`, `DRAFT`, `REVIEW`, `EXECUTE`, and `CLOSEOUT`. Only one
roster slice may be `ACTIVE` at a time. Close each slice only on its exit or
with a recorded terminal outcome; close the cycle only when every included
slice has a completed or explicitly recorded incomplete outcome. Keep existing
single-slice cycle records readable and closed history unchanged.

Do not claim slice-scoped visual/device acceptance without installed screenshots and the applicable scoped interaction checks. A build or preview alone is not visual evidence. App-wide accessibility and system acceptance is established in dedicated `TEST` slices.

## Project-local skills

Reusable workflows live under `.agents/skills/`. Use a skill when it directly fits the request; its instructions support but do not override the user, this file, or higher-priority instructions. Do not use skill guidance to widen scope. Do not delegate unless the user or applicable instructions explicitly authorize it.

## Repository map

- `app/src/main/java/com/oxygen/weather/timeline/data/` — deterministic fixture data.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/` — typed forecast presentation and pure time projection.
- `app/src/main/java/com/oxygen/weather/timeline/ui/` — Compose timeline and atmospheric rendering.
- `app/src/test/` — focused unit tests.
- `scripts/codex_cycle.py` — ordered cycle roster, stage checkpoint, pause/resume, and closeout tracker.
- `scripts/dev.py` — workflow validation, contract, build, test, lint, run, and screenshot entry points.

## Verification

Don't borrow trouble. Do not guess how code behaves or invent defects, causes, error paths, race conditions, or tests for unproven scenarios. Do not add speculative error-prevention behavior. Implement the intended behavior, then fix errors when evidence shows they occur. First make the complete slice work for its intended purpose, refactoring as needed. Do not treat a hypothetical problem as a demonstrated problem or build tests for problems before working code exists. After the slice works, assess and verify relevant potential problems within its scope. Required acceptance and evidence still apply, and tests are added or run only when the user authorizes them.

Use `python scripts/dev.py workflow`, `contract`, `test`, `build`, `lint`, `run`, or `screenshot` as applicable to the authorized task and active plan. Do not add or run tests unless the user asks. Report exact commands and any verification that could not run. Follow a separate full-suite verification plan when work becomes production integration.

Verification stays within the bounded slice. Design and implementation slices check only their scoped behavior and directly affected paths. Do not add RTL, TalkBack, or broad system/environment matrices to those slices; reserve cross-cutting system acceptance for roadmap slices explicitly designated `TEST`. A design slice may capture and inspect its approved states and directly affected interactions. Do not claim checks outside the slice.

## Architecture boundaries

- Data fixtures use canonical metric units; only presentation performs display conversion.
- UI depends on typed projection values, never provider DTOs.
- Zoom is not forecast time. The selected timestamp is authoritative and must not jump because of a zoom animation.
- Preserve honest DEMO DATA labels and distinguish supplied observations from interpolated visual values.
- Treat supplied supported weather values and categorical conditions as presentation inputs. The presentation and rendering layers visualize them faithfully and do not independently reject or correct them based on meteorological plausibility.
- The current prototype has no live network, location selection, official alerts, or real-data cache. Future successor capabilities require their own bounded WX plan and verification.
- Official alerts remain a separate future subsystem, outside catalog weather conditions.
- Keep the immersive timeline as the successor app's primary experience. Do not insert it into the deprecated oxygenWX application's outer pager.
- Important forecast facts remain visible as text; decorative weather rendering is not a substitute for meteorological data or accessible semantics.

## UI slice constraints

Every visual slice identifies its functional invariants, visual objective, in-scope installed states, and verification/evidence. Test only that slice's context. RTL, TalkBack, and broad system/environment acceptance belong to dedicated `TEST` slices, not design slices. Preserve timeline responsiveness, predictable gesture ownership, and readable forecast values and labels in the changed states.
