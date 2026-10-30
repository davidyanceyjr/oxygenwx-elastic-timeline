# Codex prompt — Scene Generation roadmap kickoff

Help mature the Scene Generation proposal into a clear, implementable planning foundation. This is the execution brief for roadmap slice SG.0. It authorizes documentation review and edits only; it does not authorize application implementation.

## Read first

Read the repository instructions and controlling product, experience, and roadmap specifications. Review:

- `AGENTS.md`
- `docs/SPECIFICATION.md`
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`
- `docs/ROADMAP_SPECIFICATION.md`
- `docs/ROADMAP.md`, including its Scene Generation pivot and SG.0
- `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`
- `docs/OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md` as historical context
- `.codex/README.md`, `.codex/current.md`, and the current plan
- relevant source code only to confirm current architecture boundaries; do not edit application code

Inspect the current cycle pointer and workflow. The recorded sequence at the pivot was WX.25 active, WX.26 next, then WX.27 and WX.28, with SG after WX.28. Respect the current repository state; do not rewind cycle status or disturb completed WX work.

## Review objectives

Review OXY-SG-001 and SG.1–SG.7 as a product and architecture proposal. Determine whether these boundaries are clear and internally consistent:

1. The deterministic scene-plan generator maps supported, typed forecast events and selected time to semantic tracks. Each track stays close to the agreed contract: asset or asset family, semantic `layer_name`, absolute timeline, and only the transition information needed to express state changes such as day/night.
2. Tracks contain no fixed screen coordinates. Each theme owns its background/world, composition, layer placement and order, paths, and asset variants. Neither generator nor renderer may assume a landscape, sky, horizon, ground, or planetary setting.
3. Scrubbing answers forecast questions visually over time. Event onset, duration, and clearing align with supported forecast times. Supported dry conditions, missing data, and probability-only information remain distinct. Probability does not imply certainty or intensity.
4. Absolute `Instant`, location timezone, DST, day/night transitions, selected-time authority, reduced motion, decorative/accessibility semantics, readability, gesture ownership, deterministic output, asset loading, and cache invalidation have clear owners.
5. SG.0–SG.7 are bounded, non-overlapping, correctly ordered, and have observable exits, dependencies, applicable visual conditions, installed evidence, and objective-specific concept-art briefs. Later atmospheric data families receive their own focused boards and slices; do not expand a slice silently.

Every slice plan must remain an unfinalized draft until its objective-specific
behavior board and, for UI/visual work, UI artwork board have been approved by
the product owner and recorded in the plan with constraints. A combined board
is acceptable when both behavior and appearance are reviewable. The board(s)
are required before plan finalization, not merely before implementation.
Behavior boards can use finite-state-machine-like flows with states,
transitions, explanatory text, and illustrative images. SG.0 itself needs an
annotated behavior board for the proposal-review objective; this kickoff prompt
is only the execution brief and does not satisfy that gate. SG.2–SG.7 include
UI/scene outcomes and need artwork boards as well as any behavior board their
scope requires.

## Work

1. Record contradictions, missing contracts, costly assumptions, and decisions requiring owner input.
2. Resolve routine wording and sequencing using the existing owner direction: generated tracks are semantic and theme-independent; themes own composition; timeline time drives weather; loading should be fast through deterministic planning and bounded caching; the scene should visually answer near-term forecast questions while exact facts remain textual.
3. Update only `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` and the SG entries/transition note in `docs/ROADMAP.md` when needed. Keep OXY-SG-001 marked as a draft unless the owner has explicitly adopted it. Preserve the WX sequence and all completed ATM/DFM history.
4. Keep truly open choices in a concise, actionable list. Do not block the review on choices that can safely remain deferred to a later bounded slice.
5. Report the resulting decisions, document changes, and unresolved owner choices.

## Boundaries

- Do not modify application code, build files, current cycle state, active WX plans, or unrelated documentation.
- Do not create artwork or claim concept approval. Update concept-art briefs only.
- Do not activate a cycle or create fabricated evidence. SG.0 executes only after its WX.28 dependency is complete.
- Do not run application tests or builds. Finish with `git diff --check` and inspect the documentation diff.
