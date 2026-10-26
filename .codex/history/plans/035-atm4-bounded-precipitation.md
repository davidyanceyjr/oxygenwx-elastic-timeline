# Cycle plan — 035-atm4-bounded-precipitation

State: CLOSED
Roadmap: ATM.4
Objective: Deliver clearly noticeable, bounded rain and snow artwork from explicit typed precipitation inputs, with truthful chance/amount semantics, a readable static fallback, and installed Pixel 3 XL evidence.

## Production boundary

Standalone prototype only. Expected application changes are limited to:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt` for typed precipitation depiction inputs/state, if needed.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` for visibly stronger rain/snow rendering and static behavior.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` only for explicit debug-only fixture previews needed to inspect typed rain amount/chance and synthetic snow states.
- Focused existing source/test files only if needed to verify the mapping without expanding the task.

Cycle records may update this plan, `docs/ROADMAP.md`, `.codex/test-artifacts/035-atm4-bounded-precipitation/`, and `.codex/history/035-atm4-bounded-precipitation.md`. Do not modify production oxygenWX, add provider/network behavior, or alter timeline design/navigation.

## Functional invariants

- Preserve `PrecipitationType`, chance, and nullable typed amount independently. Chance controls stepped ambient moisture coverage; it never implies falling-rain heaviness. Use explicit rain amount only for illustrative rain heaviness. Missing amount stays unknown.
- Snow depiction requires explicit `SNOW` identity or an explicitly labeled synthetic preview; never infer snow from temperature.
- Rain and snow art must be clearly noticeable at normal phone viewing size, including paired absent/present states.
- Static/reduced-motion states retain understandable precipitation meaning without relying on animation.
- Keep forecast facts, DEMO DATA provenance, readable labels, selected time, timeline responsiveness, gesture ownership, and time bounds intact.

## Steps

- [x] Inspect current renderer, typed projection, fixture cases, cycle 033 failed captures, and cycle 034 typed inputs; settle the smallest visual treatment that is unmistakable at phone size and does not cover essential text.
- [x] Implement bounded, visibly legible rain and snow depictions using only supported typed inputs; provide distinct chance coverage steps and explicit amount-based illustrative heaviness, while keeping missing totals unknown and static behavior understandable.
- [x] Run workflow, contract, and build checks; install on Pixel 3 XL and capture paired absent/present effects plus representative chance/amount steps and explicit snow. Check forecast text/timeline readability, responsiveness, gesture predictability, and the relevant timeline interactions.
- [x] Preserve screenshots and observations under `.codex/test-artifacts/035-atm4-bounded-precipitation/`; archive exact verification and limitations in `.codex/history/035-atm4-bounded-precipitation.md`.

## Acceptance/evidence

The authorized app checks are `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, and installed `python scripts/dev.py run` / `python scripts/dev.py screenshot` as available. Do not add or run tests under the repository rule unless separately requested. Existing unit tests may be inspected but are not run. Capture actual Pixel 3 XL screenshots showing clear absence/presence and representative stepped chance/explicit rain-amount states at normal viewing size, plus a typed synthetic snow state. Record device, settings, commands, and observations in `.codex/test-artifacts/035-atm4-bounded-precipitation/README.md`.

Completion requires every ATM.4 exit condition in `docs/ROADMAP.md`; a build or preview alone does not establish visual acceptance. RTL, TalkBack, broad font-scale coverage, and other app-wide matrices remain separate per the roadmap decision.

## Risks / assumptions

Verified at planning: cycle 033 was rejected because its faint, sparse marks were not noticeable; cycle 034 provides deterministic hourly `NONE`, `RAIN`, and `SNOW` identity, stepped chance, typed nullable amounts, and installed input-state evidence. Cycle 035 now maps depiction from typed precipitation identity, preserves chance and amount separately in scene state, uses chance tiers for particle coverage, uses explicit rain amounts for streak weight/length, and leaves missing amounts null. Installed Pixel 3 XL evidence met the scoped visual exit. Device availability was confirmed and used.

Assumptions: the Pixel 3 XL remains available for install/capture. A preview-only path may be used for synthetic snow; it must be clearly labeled and not presented as fixture forecast truth. If visible precipitation cannot be made legible without occluding important content or compromising timeline interaction, record the exact unmet exit and do not claim DONE.

Difficulty: 6/10. Data is now explicit, but precipitation types cross presentation/rendering boundaries and the prior visual attempt failed its core purpose, so installed iteration is central.

Model fit: recommend the least costly available Codex model with a credible path to bounded Compose implementation and visual iteration; the current standard workhorse tier is suitable. Escalate one tier if repeated installed iterations fail to achieve visible effects without harming legibility. This is a relative capability recommendation, not an API availability or pricing claim.

## Out of scope

No live data/provider/schema work; no deriving snow or amount from chance/temperature; no unsupported precipitation rates; no cloud, sky, terrain, fog, or wind redesign; no broad RTL/TalkBack/accessibility matrix; no timeline/navigation/gesture redesign; no production integration; no adding/running tests unless separately requested.
