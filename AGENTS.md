# AGENTS.md — Oxygen Time Lab

This repository is the successor OxygenWX app, developed in stages from an isolated synthetic-data prototype. It is separate from the deprecated oxygenWX application; the WX roadmap does not modify that application's files or navigation.

## Authority

1. `docs/SPECIFICATION.md` — controlling prototype scope and semantic invariants; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` — adopted experience principles, interaction contract, and experience-level acceptance criteria. If they conflict, `docs/SPECIFICATION.md` controls unless a bounded owner decision updates it.
2. `docs/ROADMAP_SPECIFICATION.md` — required roadmap statuses, slice format, evidence, and replanning rules.
3. `docs/ROADMAP.md` — bounded ordered follow-ups conforming to the roadmap specification.
4. `docs/UI_DEVELOPMENT_WORKFLOW.md` — installed visual acceptance.
5. `.codex/current.md` and its referenced bounded plan — cycle status.

## Required cycle lifecycle

Before app implementation or behavior changes:

1. Read the relevant authority above, `.codex/README.md`, and `.codex/current.md`.
2. Run `python scripts/dev.py workflow`.
3. Ensure a bounded plan exists under `.codex/plans/`. A PLANNED plan is eligible for final review and activation only after its required approved slice board(s) are recorded. If the board gate is unmet, keep the plan an unfinalized draft and resolve that prerequisite first. If the current state is IDLE, select and plan a roadmap slice before implementation.
4. Before finalizing any cycle plan for any project roadmap or workstream, ensure it has an objective-specific behavior board and, for UI/visual work, a UI artwork board; one combined slice board may cover both if it makes each reviewable. This project-wide gate applies to every slice, including future roadmaps, and covers code, text/documentation, interface, and logic changes. The product owner must approve the required board(s). Record their paths, approval, constraints, and relationship to scope and acceptance in the plan. Behavior boards may use finite-state-machine-like flows with text and images, annotated content maps, contracts, schemas, sequences, or boundary diagrams. UI artwork boards show the intended appearance and affected states. A prompt, target path, existing implementation, or screenshot alone does not satisfy the board or approval requirement. A draft may be prepared while its board is pending, but it cannot be finalized or activated. Boards guide scope and acceptance; they do not replace implementation verification or installed acceptance evidence.
5. Stay within the plan's scope, invariants, acceptance criteria, and exclusions. Do not silently expand a slice.
6. Preserve actual evidence under `.codex/test-artifacts/<cycle-id>/`.
7. Inspect `git diff --check` and the final diff, then close completed work into `.codex/history/` with exact verification results and limitations.

Do not claim slice-scoped visual/device acceptance without installed screenshots and the applicable scoped interaction checks. A build or preview alone is not visual evidence. App-wide accessibility and system acceptance is established in dedicated `TEST` slices.

## Project-local skills

Reusable workflows live under `.agents/skills/`. Use a skill when it directly fits the request; its instructions support but do not override the user, this file, or higher-priority instructions. Do not use skill guidance to widen scope. Do not delegate unless the user or applicable instructions explicitly authorize it.

## Repository map

- `app/src/main/java/com/oxygen/weather/timeline/data/` — deterministic fixture data.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/` — typed forecast presentation and pure time projection.
- `app/src/main/java/com/oxygen/weather/timeline/ui/` — Compose timeline and atmospheric rendering.
- `app/src/test/` — focused unit tests.
- `scripts/dev.py` — workflow, contract, build, test, lint, run, and screenshot entry points.

## Verification

Don't borrow trouble. First make the complete slice work for its intended purpose, refactoring as needed. Do not treat a hypothetical problem as a demonstrated problem or build tests for problems before working code exists. After the slice works, assess and verify relevant potential problems within its scope. Required acceptance and evidence still apply, and tests are added or run only when the user authorizes them.

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
