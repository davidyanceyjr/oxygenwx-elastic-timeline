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
3. Ensure a bounded plan exists under `.codex/plans/`. If it is PLANNED, review it and activate with `python scripts/codex_cycle.py activate`; if the current state is IDLE, select and plan a roadmap slice before implementation.
4. Stay within the plan's scope, invariants, acceptance criteria, and exclusions. Do not silently expand a slice.
5. Preserve actual evidence under `.codex/test-artifacts/<cycle-id>/`.
6. Inspect `git diff --check` and the final diff, then close completed work into `.codex/history/` with exact verification results and limitations.

Do not claim visual/device acceptance without installed screenshots and the applicable interaction/accessibility checks. A build or preview alone is not visual evidence.

## Project-local skills

Reusable workflows live under `.agents/skills/`. Use a skill when it directly fits the request; its instructions support but do not override the user, this file, or higher-priority instructions. Do not use skill guidance to widen scope. Do not delegate unless the user or applicable instructions explicitly authorize it.

## Repository map

- `app/src/main/java/com/oxygen/weather/timeline/data/` — deterministic fixture data.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/` — typed forecast presentation and pure time projection.
- `app/src/main/java/com/oxygen/weather/timeline/ui/` — Compose timeline and atmospheric rendering.
- `app/src/test/` — focused unit tests.
- `scripts/dev.py` — workflow, contract, build, test, lint, run, and screenshot entry points.

## Verification

Use `python scripts/dev.py workflow`, `contract`, `test`, `build`, `lint`, `run`, or `screenshot` as applicable to the authorized task and active plan. Do not add or run tests unless the user asks. Report exact commands and any verification that could not run. Follow a separate full-suite verification plan when work becomes production integration.

## Architecture boundaries

- Data fixtures use canonical metric units; only presentation performs display conversion.
- UI depends on typed projection values, never provider DTOs.
- Zoom is not forecast time. The selected timestamp is authoritative and must not jump because of a zoom animation.
- Preserve honest DEMO DATA labels and distinguish source-supplied observations from interpolated visual values.
- The current prototype has no live network, location selection, official alerts, or real-data cache. Future successor capabilities require their own bounded WX plan and verification.
- Official alerts remain a separate future subsystem, outside catalog weather conditions.
- Keep the immersive timeline as the successor app's primary experience. Do not insert it into the deprecated oxygenWX application's outer pager.
- Important forecast facts remain visible as text; decorative weather rendering is not a substitute for meteorological data or accessible semantics.

## UI slice constraints

Every visual slice identifies its functional invariants, visual objective, applicable compact/large-font/RTL/reduced-motion conditions, installed states, and verification/evidence. Preserve the cross-cutting requirements for timeline responsiveness, predictable gesture ownership, and readable forecast values and labels.
