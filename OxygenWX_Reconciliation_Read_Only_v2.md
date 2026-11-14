# OxygenWX Scene Engine — Architectural Reconciliation Plan

**Document type:** Read-only architectural audit with two explicitly permitted draft-document outputs  
**Status:** Read-only reconciliation assignment; outputs remain proposals for owner review, **not** implementation authority  
**Prepared:** 2026-11-13  
**Repository:** https://github.com/davidyanceyjr/oxygenwx-elastic-timeline  
**Inspected baseline:** `main` commit `1de16660adeffafca6e1386faef053a52283bae6` (remote snapshot inspected 2026-11-13; local/unpushed changes not inspected)  
**Workflow pointer observed:** `.codex/current.md` — `IDLE`, last updated 2026-11-11  
**Purpose:** Analyze the existing architecture without changing repository state, except for updating the two explicitly authorized draft documents. Produce review-ready proposals; separate workflow-governed adoption and implementation occur later.

## Execution boundary — mandatory for Codex

**This is a read-only repository audit with exactly two permitted file edits as its output.** The architectural reconciliation is an analysis/drafting assignment, **not** an Oxygen development cycle or an authorization to implement, adopt, or approve anything. The controlling repository documents are inputs to inspect and respect, not instructions to start a cycle for this read-only pass. If a governing rule conflicts with this deliberately narrow assignment, report the conflict; do not silently broaden the task or mutate workflow state to satisfy a gate.

**Permitted operations:**

1. Read and inspect repository files, Git status/history, specifications, roadmap entries, code, and existing tests as evidence. Use read-only commands (`git status`, `git log`, `git diff`, `rg`, `sed`, etc.).
2. Analyze all eight reconciliation steps in one coordinated pass. Prepare architecture diagrams, matrices, and decisions **inside the two output documents** rather than creating separate artifacts.
3. Edit **only** these existing draft files on disk:
   - `oxygen_scene_engine_spec.md`
   - `Oxygen_Scene_Engine_Slice_Roadmap_Workflow_Aligned.md`
4. Report findings, proposed decisions, unresolved owner questions, exact file diffs, and any checks actually performed in the final response. Report any issue that prevents safely editing the permitted outputs.

**Prohibited operations during this assignment:**

- No edits, additions, deletions, or generated artifacts anywhere else in the repository—including application code, tests, `README.md`, `AGENTS.md`, `docs/ROADMAP.md`, controlling specifications, `.codex/`, concept boards, checkpoint files, or evidence/history files.
- Do **not** open, advance, pause, or close a cycle; change roadmap statuses; create board packages or bounded plans; request gate approvals between analysis steps; invoke workflow mutation scripts; or mark requirements approved.
- Do **not** build, run tests, install, launch, benchmark, generate screenshots, or claim runtime/device acceptance. Read existing test code and prior evidence if helpful.
- Do **not** commit, merge, push, open a PR, or change branches as part of this assignment. The operator should create/select `fix/scene-engine` **before** invoking Codex. Codex may verify the branch and working-tree status read-only, and should stop if it is not safe to edit the two allowed files.
- Do **not** treat this plan's conclusions as superior to controlling product/roadmap authorities. Record proposed conflicts and owner decisions rather than changing those authorities.

**Output status:** The two edited files must be visibly marked **DRAFT / PROPOSED / NOT ADOPTED**. They are working proposals for later owner review. Readiness for owner review is not approval, workflow activation, or implementation authority.

**No intermediate gating:** Complete Steps 1–7 as read-only investigation plus the two authorized draft edits. Step 8 is **a future adoption recommendation only** and has no executable actions in this pass. Boards, plans, owner approvals, and cycle checkpoints apply when a separate, explicitly authorized workflow cycle is initiated—not as prerequisites to reading source or drafting these two proposal files under this assignment.

> **Core architectural decision to resolve:** What is the smallest shared, typed, source-faithful representation that drives both (a) a weather-responsive world and (b) exact weather readings integrated into objects in that world?

## 1. Outcome and limits

This assignment produces two reconciled **draft** documents for owner review:

1. `oxygen_scene_engine_spec.md` — proposed target architecture, responsibilities, contracts, invariants, and constrained examples; not yet finalized or approved.
2. `Oxygen_Scene_Engine_Slice_Roadmap_Workflow_Aligned.md` — proposed revised, dependency-checked implementation slices for **separate, later owner-approved adoption** into `docs/ROADMAP.md`.

Supporting analysis should include a code-backed responsibility matrix, selected-instant pipeline diagram, boundary decision records, default-scene reference example, dependency graph, and traceability matrix. **Embed this material in the two permitted draft outputs or summarize it in the final Codex response; do not create additional files, boards, or artifacts.**

**Explicit exclusions:** No repository changes beyond the two named draft outputs; no application code edits, scene artwork production, package store, public scene DSL, test execution, device acceptance, roadmap activation, cycle-state mutation, or claim that the docs are approved. Adoption follows the existing workflow in a separate later assignment.

## 2. Repository authority and evidence

Treat `README.md` as stale and non-authoritative for this work. Start with the following, in repository authority order:

- `AGENTS.md`: task-scoped workflow; stage gates; board approval and verification constraints.
- `docs/SPECIFICATION.md`: controlling prototype scope and semantic constraints.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`: adopted UX principles, selected-time and gesture behavior, readout and access rules.
- `docs/ROADMAP_SPECIFICATION.md`: leaf-slice format, dependency/status constraints, board packages, cycle/checkpoint rules.
- `docs/ROADMAP.md`: authoritative **adopted** execution backlog.
- `docs/UI_DEVELOPMENT_WORKFLOW.md`: installed visual acceptance requirements.
- `.codex/current.md`, `.codex/README.md`, and the bounded plan when a cycle is opened.

**Non-authoritative proposals to reconcile:**

- `oxygen_scene_engine_spec.md`
- `Oxygen_Scene_Engine_Slice_Roadmap_Workflow_Aligned.md`
- `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` (existing SG behavior; subordinate to controlling specs)
- `docs/OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md` (historical context; unstarted ATM/ENV follow-ups superseded)

**Critical implementation anchors observed:**

| File | Currently observed concern | Reconciliation task |
|---|---|---|
| `data/catalog/CanonicalWeatherFacts.kt`, `CatalogWindow.kt`, `CatalogActivationCoordinator.kt` | Catalog truth, expected support, selected activation | Keep provider acquisition and canonical truth outside Scene Engine; preserve selected-instant semantics |
| `data/openmeteo/` | Source acquisition and mapping | Explicitly exclude provider DTOs from scene contracts |
| `presentation/scene/SemanticScenePlan.kt` | `SemanticSceneGenerator`, `SemanticScenePlan.evaluate`, `SceneReading`, evidence and typed tracks | Determine what is already sufficient as a shared semantic boundary |
| `presentation/CatalogTimelinePresentation.kt` | `CatalogTimelinePresenter`, `CatalogTimelineScenePresentation`, `CatalogTimelineView`, formatted facts | Separate data binding from world-specific composition without duplicating interpretation |
| `presentation/CanonicalForecastProjection.kt` | Forecast meaning, display measurements and visual-related projection | Decide authoritative formatting/provenance interface; identify redundant decisions |
| `presentation/scene/FirstCityTheme.kt`, `FirstCityComposition.kt` | City-only composition and typed layer decisions | Specify incremental extraction to reusable world/element composition |
| `presentation/atmosphere/SceneVisualState.kt` | Atmospheric and compatibility state | Inventory usage and designate retained vs migrated routes |
| `ui/AtmosphericScene.kt` | Multiple `CatalogAtmosphericScene` routes; Canvas, weather artwork, layering | Make rendering passive; identify currently interpreted fields and render-only responsibilities |
| `ui/CatalogTimeLabScreen.kt` | Scene + selected-weather readout + gesture ownership + timeline | Protect existing interactions while introducing scene hit targets and integrated data |
| `ui/AccessibleForecastBrowser.kt` | Alternate accessible forecast access | Preserve exact data access if overlays relocate into the world |

**Observed selected-instant production route (verify during formal tracing):**

```
ActiveCatalogSelection / CanonicalCatalog + selected Instant
    -> CatalogTimelinePresenter (builds SemanticScenePlan on construction)
    -> SemanticScenePlan.evaluate(selectedInstant): SceneReading
    -> FirstCityTheme.compose(reading, zone): FirstCityComposition
    -> CatalogTimeLabScreen / CatalogAtmosphericScene (Canvas)

Parallel presentation:
    -> CanonicalForecastProjection / CatalogTimelineView (formatted readout)

Compatibility routes:
    -> SceneVisualState / alternate AtmosphericScene overloads
```

This is an inspection summary, **not** a guarantee that all runtime branches have been exhaustively traced.

## 3. Architectural target for reconciliation

**Proposed logical structure, not fixed classes or packages:**

```
CanonicalCatalog + SelectedInstant + Zone + SourceEvidence
                           |
                           v
              Shared semantic projection
       (validity, support, events, astronomy)
                 /                     \
                v                       v
      Environmental decisions      Information bindings
      conditions / light /         exact measurements /
      weather/visual response      units / time / provenance
                \                       /
                 v                     v
                   World composer
       world elements / identity / depth /
       responsive anchors / data surfaces
                           |
                           v
                Resolved scene state
           draw instructions + interactions
                           |
                           v
          Compose Canvas + interaction host
```

The **logical separation** of environmental decisions and information bindings does **not** mandate two parallel expensive computations or duplicate source models. Both refer to the same selected instant and canonical evidence.

### Responsibility rules to approve

1. **Canonical/data layer:** Fetch, normalize, preserve timestamps, source meaning, supported/missing values and provenance. Never embed world geometry.
2. **Semantic projection:** Resolve authoritative selected-instant weather/astronomy tracks and evidence; never choose park objects or viewport coordinates.
3. **Environmental interpretation:** Map supported semantic inputs into weather-response values; no provider IO, no world-specific art generation, no invented observed precision.
4. **Information binding:** Provide selected-instant exact values with units, validity, provenance, display/format policy, and explicit unavailable state; do not render signage.
5. **World composition:** Define persistent objects, semantic roles, three depth groups, visual placement, material/treatment, camera/safe anchors, and mappings from resolved decisions/bindings to world elements.
6. **Rendering:** Draw resolved objects deterministically; no reinterpretation of raw forecast, no timeline ownership.
7. **Interaction host:** Resolve semantic hit targets and object inspection actions while preserving time-tape and existing main-screen gestures; exact forecast remains accessible independent of scene geometry.

### Two distinct example consumers

**Weather-responsive tree:** consumes a resolved environmental wind state (possibly unknown), applies park-specific foliage artwork and motion, never queries Open-Meteo or converts raw wind to meteorological meaning.

**Temperature mural:** consumes an exact supported selected-time measurement binding (or an unavailable binding) with time, unit and provenance, then paints it into a perspective-aware wall treatment. Tap invokes an accessible inspection route; mural artwork does not generate or reinterpret temperature.

## 4. Eight ordered reconciliation steps

### Step 1 — Establish the preservation and gap baseline

**Actions:** Compare target specs to source and controlling contracts. Record implemented/partial/new/conflicting/deferred requirements. Classify existing First City visual and interaction behavior, chosen-time authority, missing-data rules, weather meanings, and reusable code. Reuse applicable closed WX/ATM/SG/DFM evidence rather than asserting new tests were run.

**Output:** Baseline and gap matrix included in one of the two authorized draft documents or Codex final report; **do not create a third file**.

**Exit:** Every major spec requirement has a source reference or an explicit unverified status, and known preservation invariants are enumerated.

### Step 2 — Trace the authoritative dataflow and decide ownership

**Actions:** Document catalog activation -> selected Instant -> semantic plan -> reading -> First City -> Canvas, and catalog -> formatted selected forecast. Classify `SceneVisualState` and alternate renderer paths as active/compatibility/debug with source evidence. Decide whether `SceneReading` already carries enough evidence, and how exact readings should reuse existing presentation conversion. State who owns interpolation, sample-valid time, weather vs light, units, stale/carry states, and forecast/observation labeling.

**Output:** Selected-instant flow and boundary decision record inside the revised specification; **no separate flow file**.

**Exit:** Every displayed datum and every environmental visual has exactly one documented authoritative semantic source; unresolved paths are tracked, not silently removed.

**Owner decision A:** What shared typed output will both the environment and information binding use? Prefer evolution of existing types unless evidence demonstrates a gap.

### Step 3 — Define the world and element contracts

**Actions:** Specify minimal `WorldDefinition`, `ElementIdentity`, semantic role, depth/layer order, optional asset references, transform anchors, environmental inputs and fallback policies. Clarify `Background < Midground < Foreground`, including precipitation, post-processing/time-wash and natural occlusion. Show a First City migration table and a tiny non-city example before choosing new package architecture.

**Output:** World contract and First City mapping example inside the revised specification.

**Exit:** Two different worlds could consume equivalent semantic input without duplicating meteorological interpretation; no unnecessary universal registry or public DSL is required.

**Owner decision B:** Which responsibilities are world-owned versus shared renderer-owned?

### Step 4 — Define scene-contained information and interaction

**Actions:** Specify a reusable `DataSurface` / binding boundary: semantic field, supported value, display units/text, source valid time, provenance/meaning, unknown state, accessible equivalent, activation semantics. Define a mural, billboard and water-tower example with distinct treatments but the same binding contract. Explicitly arbitrate hit testing versus existing single tap, double tap, day swipe, and timeline touch rules. Define what happens when an object is occluded or outside the viewport.

**Output:** Information-surface contract and text description of inspect/dismiss behavior inside the revised spec. **No board file is created in this pass.**

**Exit:** One canonical selected-time reading can appear on two different physical surfaces without duplicated weather processing; essential facts are still reachable and correctly labeled.

**Owner decision C:** Which on-scene object replaces, supplements, or coexists with the current prominent central readout? Any changed UX authority requires bounded owner approval.

### Step 5 — Define adaptive camera, viewport, and fold posture

**Actions:** Specify logical world space, anchor rules, fit/crop/reveal behavior, minimum readable primary data surface, transformed hit targets, window-inset handling and hinge-aware exclusion. Provide one Living Urban Park master layout: eye-level park entrance, curved walkway, mural hero, trees and skyline, secondary billboard/water tower. Derive portrait, phone landscape and unfolded Pixel Fold framing from the **same** world. Distinguish optional offscreen objects from essential information.

**Output:** Adaptive composition rules and three textual viewport examples inside the revised spec (artwork boards belong to later authorized cycles).

**Exit:** Each viewport preserves recognizable world identity, timeline access, sky area and exact forecast access without assuming a fixed device model or stretching a portrait bitmap.

**Owner decision D:** Does the scene reframe, reveal extra world, or selectively simplify at narrow heights? Define priority rules rather than device-specific positions.

### Step 6 — Set rendering, determinism and preparation boundaries

**Actions:** Specify procedural/vector/raster roles, how stable seeds are derived, independent ambient vs selected-time movement, caching scopes and invalidation. Compare the existing full-window `SemanticScenePlan` with the proposed compiled-day model; don't mandate day compilation without measurements. Preserve cloud-region constraints from existing SG spec or explicitly propose an owner-reviewed adjustment. Define visual unknown/fallback states. Identify later measurement requirements (allocation, latency, frame cost, scrub smoothness, battery) for Pixel 3 XL and Pixel Fold.

**Output:** Renderer/resolver and performance decision record inside the revised specification.

**Exit:** The specification says what is computed per catalog, selected time, viewport change, and draw frame; no untested performance improvement is asserted.

**Owner decision E:** Retain existing plan granularity unless evidence supports replacement.

### Step 7 — Repair roadmap outcomes and dependency order

**Actions:** Reconcile existing OX-01–OX-20 individually. Keep stable IDs wherever outcome remains stable. Split oversized outcomes only where independently closable. Revisit OX-18 portability proof placement, OX-12 binding vs artwork, OX-10 interaction arbitration, and add an explicit adaptive composition slice if absent. Separate reusable engine capability from polished Living Urban Park production. Update dependencies, acceptance and evidence targets to match source-backed boundaries and controlling roadmap format.

**Output:** Revised proposed roadmap with per-slice traceability and dependency graph included in that same draft file.

**Exit:** Every leaf has bounded objective, genuine prerequisites, owner-reviewable behavior board target, UI artwork board target when applicable, measurable exit and explicit exclusions. No unsupported cycle IDs or DONE claims are introduced.

### Step 8 — Document future adoption requirements (not executed in this pass)

**Actions in this pass:** Record, **within the two authorized drafts**, the later decisions and governance steps needed for adoption. Identify which conflicts would require an owner decision or changes to controlling authorities. **Do not** initiate a cycle, create boards/plans/checkpoints, edit `docs/ROADMAP.md`, or seek staged approvals while performing the reconciliation.

**Future process, separate authorization:** After the owner reviews the two drafts, an appropriate bounded documentation/governance cycle can follow `SLICE -> BOARDS -> DRAFT -> REVIEW -> EXECUTE -> CLOSEOUT` and adopt eligible implementation leaves into canonical `docs/ROADMAP.md`.

**Output now:** A clear proposed adoption path and unresolved decision register in the two draft files; **no additional repository files**.

**Exit now:** The drafts explain what must be approved later and make no claim that adoption or workflow verification has occurred.

## 5. Required revisions to proposed OX workstream

| Slice area | Preserve | Requested planning revision |
|---|---|---|
| OX-01–OX-04 | Baseline, tracing, one decision boundary, unknown-state truth | Make the environmental-vs-information ownership explicit and use current code as source |
| OX-05–OX-06 | Depth order and stable semantic elements | Include worked two-world composition proof and information-surface roles |
| OX-07–OX-09 | Reusable cloud/rain/snow and determinism | Avoid general engine abstractions unsupported by first extraction evidence |
| OX-10–OX-11 | Moon inspect and supported astronomy | Resolve gesture arbitration, occlusion, accessible alternative, and data support before UI change |
| OX-12 | Essential reading inside world | Consider a **binding-only** leaf followed by an **artwork/rendering** leaf |
| OX-13–OX-14 | Progressive depth and reduced overlays | Require verified reachable information before removing readout; amend UX authority if necessary |
| OX-15–OX-17 | Time Lab and performance | Keep scoped; avoid unmeasured caching/day-compilation redesign |
| OX-18 | Second-world proof | Consider moving minimum portability proof earlier, after world/element contract |
| OX-19–OX-20 | Independent location/scene identity and pack boundary | Keep pack delivery/store deferred; define only evidence-backed declarative boundaries |
| Missing work | — | Add viewport/camera/fold posture acceptance leaf(s); separate polished default park scene workstream |

**Ordering principle:** Contract -> first vertical proof -> reusable extraction -> scene-contained data -> interaction -> responsive composition -> polished default -> measured validation/pack boundary. Reorder only where dependencies truly require it. A minimal, early portability proof need not include full weather coverage or finished artwork.

## 6. Living Urban Park as a constrained reference case

**World:** Fictional geographically neutral urban park, entrance camera at pedestrian eye level, curved path, weather mural on left wall, trees/benches on right, skyline, water tower and billboard deeper in the world.

**What it must prove:**

- A persistent authored world responds to shared weather/astronomy decisions without importing data-source logic.
- One source-backed temperature binding can be displayed as a painted mural, with unavailable/source-time handling.
- A secondary object (e.g. billboard) can use the same binding mechanism with a different visual treatment and optionally a different supported fact.
- Portrait shows primary elements; landscape and unfolded Pixel Fold reveal more world rather than enlarging a portrait bitmap.
- The primary reading remains legible; secondary offscreen signs never become the sole access path to essential data.
- The elastic timeline, its fixed selected instant and established gestures remain predictable.
- Reduced-motion and missing-support states preserve weather meaning.

**Not required before refactor readiness:** finished park art, water simulation, complete foldable QA, downloadable scene packs, 3D, or a universal world authoring language.

## 7. Risk and decision register

| Risk / unresolved decision | Consequence if left unresolved | Proposed resolution gate |
|---|---|---|
| Duplicate weather interpretation between semantic/presenter/renderer routes | Inconsistent depiction or expensive migration | Step 2 |
| Information surfaces consume raw forecast/provider values | Scene packs duplicate data processing and mislabel values | Step 4 |
| Foreground post-processing violates strict depth order | Occlusion/lighting inconsistent between worlds | Step 3 |
| Main-area gestures collide with object inspection | Regression in time navigation | Step 4 |
| Portrait-only world art becomes the primary coordinate model | Cropping and hinge failures | Step 5 |
| Premature daily scene compilation/cache rewrite | Complexity without performance proof | Step 6 |
| Refactor and park artwork mixed into one oversized slice | Poor acceptance and hard rollback | Step 7 |
| Older specs inadvertently overridden by proposal | Unauthorized product changes | Step 8 |

## 8. Readiness acceptance checklist

- [ ] Controlling source-of-truth hierarchy reconciled; README treated as non-authoritative.
- [ ] Current active, compatibility and debug scene routes classified from source.
- [ ] One selected-instant semantic authority established and data/evidence ownership recorded.
- [ ] Environmental decisions and exact information bindings defined without duplicate provider logic.
- [ ] Reusable world/element, depth, occlusion and render boundaries specified.
- [ ] DataSurface contract includes units, provenance, valid time, unavailable and accessible meaning.
- [ ] Scene hit testing / timeline gestures explicitly reconciled.
- [ ] Adaptive world coordinate, camera, viewport and hinge rules specified.
- [ ] Existing full-window vs compiled-day design is decided from evidence or explicitly deferred pending measurement.
- [ ] First City preservation and minimal second-world proof specified.
- [ ] OX leaves revised with exits, dependencies, board targets, exclusions, and canonical adoption strategy.
- [ ] Future documentation/governance approval requirements are recorded; **no gate is initiated or treated as satisfied by this pass**.

## 9. Direct execution handoff for local Codex

> **READ-ONLY AUDIT; TWO OUTPUT FILES ONLY.** First confirm that the operator has already selected `fix/scene-engine` and that no unexpected working-tree changes would be overwritten; do not change branches yourself. Inspect current source and controlling documents, using this plan as proposed analytical guidance and the stale `README.md` only as non-authoritative background. Trace actual catalog → selected instant → semantic scene → composition → rendering and classify compatibility/debug paths. Work through Steps 1–7 in one uninterrupted analysis-and-drafting pass; do not stop for workflow gates or create an Oxygen cycle. **The only repository writes permitted are in `oxygen_scene_engine_spec.md` and `Oxygen_Scene_Engine_Slice_Roadmap_Workflow_Aligned.md`.** Put architecture analysis and proposed decisions in those two drafts, with open owner decisions clearly labeled. Step 8 is only a description of future adoption; do not execute it. Do not edit any other file, run tests/builds, change statuses, create boards/plans/checkpoints, commit, or claim approval. Finally report the two edited paths, the diff summary, source-backed findings, and unresolved owner decisions. If the repository appears unsafe to edit, stop and explain without changing anything.

---

**Success condition for this assignment:** Both named draft files contain a source-reconciled architecture and dependency-checked slice roadmap sufficient for owner review; every planned slice identifies its weather input and world/render/interaction responsibilities. **No other repository file or workflow state changes.** The drafts remain non-authoritative until a separately authorized owner-reviewed adoption cycle.
