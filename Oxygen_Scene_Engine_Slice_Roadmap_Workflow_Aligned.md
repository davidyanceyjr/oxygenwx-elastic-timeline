# Oxygen Scene Engine — Workflow-Aligned Slice Roadmap

**Status:** Proposed Scene Engine workstream intake; no slice is approved for execution.\
**Repository:** https://github.com/davidyanceyjr/oxygenwx-elastic-timeline\
**Source basis:** Scene Engine first-draft specification, original 20-slice roadmap, and repository `main` workflow authorities reviewed November 10, 2026.\
**Principles:** “The default state is the world.” “If information can exist meaningfully in the scene, it should not permanently sit on top of the scene.”\
**Goal:** Advance the functioning Oxygen weather/timeline application toward a reusable, evidence-faithful, interactive Scene Engine while preserving proven catalog, timeline, and First City behavior.

## 1. Authority and adoption boundary

This is a **proposed workstream backlog**, suitable for owner-reviewed integration into `docs/ROADMAP.md`. It is **not** an independent workflow, new product authority, active cycle, or implementation approval. The repository's demonstrated development discipline remains the implementation mechanism; do not replace or duplicate it.

Follow the controlling repository documents in their existing precedence: `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, and the current `.codex/` cycle state and approved bounded plan. Where Scene Engine proposals conflict with those authorities, record the conflict for an owner-directed bounded decision; do not silently amend an authority through this backlog. The Scene Engine specification is a **proposal for review**, not permission to overturn established product contracts.

**Adoption:** Integrate these leaf slices into `docs/ROADMAP.md` through a separate, owner-approved documentation/governance slice under the same workflow. Confirm the chosen identifiers, dependencies, global ordering, and status during that adoption. The identifiers below retain the original `OX-01`–`OX-20` names; do not rename or reuse them after adoption without an explicit recorded migration decision. This document can remain a supporting rationale, but `docs/ROADMAP.md` is the authoritative execution backlog.

**Initial statuses:** Every leaf below is `PLANNED` **as a proposal only**, not yet a validated live-roadmap state. No `NEXT` or `ACTIVE` is assigned here; there is at most one global `NEXT` and one global `ACTIVE` across the existing repository roadmap. These entries become workflow-operative only after acceptance into the canonical roadmap. `DEFERRED` requires a reason and concrete re-entry condition, and should only be applied by owner direction during integration.

## 2. Repository cycle contract — authoritative, not redefined here

- A **slice ID** identifies one stable bounded outcome; a **cycle ID** is independently assigned from the chronological execution sequence when a cycle is planned. Never derive a cycle number from `OX-##`. A cycle may include ordered dependent slices, but the single-slice cycle remains the default and only one roster slice may be `ACTIVE` at a time.
- Use the repository's stages **SLICE → BOARDS → DRAFT → REVIEW → EXECUTE → CLOSEOUT**, with lifecycle states `IDLE`, `PLANNED`, `ACTIVE`, and `PAUSED`. The actual stage transitions, resume/checkpoint semantics, and owner approval gates are governed by `docs/ROADMAP_SPECIFICATION.md` and `AGENTS.md`.
- Every slice requires an **objective-specific behavior board**; slices that change UI or visual output additionally require an **UI artwork board** depicting affected states. A combined board is allowed if both are reviewable. Product-owner approval and approved-input content fingerprints belong in the bounded plan; a question, prompt, screenshot, or target filename alone is not an approved board.
- A draft plan may exist while boards are pending, but **finalizing/activating** a plan requires the owner-approved boards for every included slice. Review/update loops take place inside the existing BOARDS/DRAFT/REVIEW stages; they are not separate lifecycle stages. Preserve each stage checkpoint and exact next-stage read set.
- `.codex/current.md` identifies the cycle; `.codex/plans/<cycle-id>.md` is the bounded plan; `.codex/concepts/` stores board artifacts; `.codex/test-artifacts/<cycle-id>/` stores real evidence; `.codex/history/` records closeout. Paths using `<cycle-id>` below are *future targets*, not assigned IDs or claims that artifacts exist. At planning, replace them with exact cycle-specific paths and preserve the full manifest/checkpoint requirements.
- Verification is **slice-local**, bounded by the approved plan and explicit test authorization. Do not add or run tests unless authorized. Installed visual acceptance requires real device captures and directly affected interaction checks. Broader TalkBack, RTL, accessibility, and system/device matrices belong to explicitly designated **TEST** slices; preserve product accessibility requirements without inventing a full matrix for every design slice.
- Run `python scripts/dev.py workflow` at the repository-defined gates, then authorized contract/build/test/lint/device checks as applicable. Inspect `git diff --check` and the final diff. No work is `DONE` without its actual exit evidence and recorded history; failed exits, pauses, dependencies, and replanning follow existing rules, never automatic scope expansion.

## 3. Cross-cutting product invariants

- Preserve selected-instant authority, time scrubbing, gesture ownership, catalog/location switching, provenance, source-supported facts, missing versus zero, and precise forecast access unless a bounded owner decision changes them.
- Preserve the known First City appearance and interaction outside explicitly approved changed states. Every visual enhancement protects timeline responsiveness, gesture predictability, and readability in the affected states.
- Represent supported supplied weather values faithfully. The scene and UI do **not** independently reject meteorological plausibility or reinterpret categorical `CLEAR`, `PARTLY_CLOUDY`, and `CLOUDY` as precipitation. Distinguish missing/unsupported from supported zero and do not invent astronomical or weather measurements.
- Preserve accessible textual weather facts and reachable deeper information even as overlays change. Broader accessibility acceptance is deferred to dedicated `TEST` work as required by the repository.
- Scrubbing stability, cross-time continuity, and honest transitions are central, but tests, graphics abstractions, caches, or architecture redesign must be justified by observed requirements and owner-authorized scope.
- Existing baseline/debug evidence and completed WX/ATM/DFM work must be checked before duplicate work is authorized. Do not reopen completed historical gates without changed inputs or contradictory evidence.

## 4. Proposed wave order (not a new scheduling mechanism)

| Wave | Proposed leaf slices | Learning objective | Proceed when |
|---|---|---|---|
| A — Establish the seam | OX-01–OX-04 | Baseline current behavior and the actual selected-instant scene route | Source-backed boundaries and scoped preservation evidence exist |
| B — Compose the world | OX-05–OX-09 | Depth, identity, reusable drawing, stable repeatability | Existing city can use proven, bounded contracts |
| C — Explain the world | OX-10–OX-14 | Moon inspect, supported explanation, diegetic data, progressive disclosure | Essential values stay discoverable and correct |
| D — Scene laboratory | OX-15–OX-17 | Transition evidence, debug inspection, performance measurements | Fixture/measurement evidence supports next decisions |
| E — Portability | OX-18–OX-20 | Second-world proof, independence, future pack boundary | Two scenes share weather truth without duplicated interpretation |

Waves are proposed learning order, **not** sprints, lifecycle stages, a global `NEXT`, or implicit permission to bypass dependencies. An owner-reviewed intake may split, combine, reorder, cancel, or defer work under `docs/ROADMAP_SPECIFICATION.md`. Dependency links below name only actual prerequisites; review them alongside existing WX/ATM work when integrating.

## 5. Proposed Scene Engine leaf slices

Each entry uses the canonical leaf structure (**Objective/scope**, **Depends on**, **Exit**, **Evidence**, **Slice board(s)**). The additional *Repo anchors* and *Board intake questions* guide review; they are not approved contracts, actual board artifacts, or implementation instructions. Proposed board paths are explicitly **targets only** until created and owner-approved.


### Wave A — Establish the seam


### OX-01 — Baseline the current world — PLANNED

**Objective/scope:** Lock down what currently works before restructuring it: scene state, First City frame composition, scrubbing, astronomy cues, and accessibility. Reuse completed baseline records and isolate only the unmet Scene Engine questions. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** None.\
**Exit:** Representative snapshot/transition reference set and a written keep/change inventory; existing scoped evidence or explicitly authorized checks and documented gaps. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-01-baseline-the-current-world-behavior-board.md`; UI artwork board only if the approved scope actually changes visible UI/visual behavior. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/CatalogTimeLabScreen.kt`, `ui/AtmosphericScene.kt`, `presentation/CatalogTimelinePresentation.kt`, `presentation/scene/FirstCityTheme.kt`; `ui/FirstCitySceneRenderingTest.kt`.\
**Behavior board intake:** What are the observable invariants across catalog activation, selecting instants, missing weather data, and repeated scrubs?\
**UI board intake / preservation check:** Which existing visual states, readouts, and interactions are contractual, and which are accidental dashboard UI?


### OX-02 — Trace the selected-instant pipeline — PLANNED

**Objective/scope:** Document and test the authoritative route from catalog facts to the currently visible frame; identify parallel/legacy paths without deleting them prematurely. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-01.\
**Exit:** One source-backed flow diagram, explicit active/compatibility/debug path classifications, and tests for selected-instant evaluation. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-02-trace-the-selected-instant-pipeline-behavior-board.md`; UI artwork board only if the approved scope actually changes visible UI/visual behavior. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `data/catalog/CatalogActivationCoordinator.kt`, `presentation/CatalogTimelinePresentation.kt` (`CatalogTimelinePresenter`), `presentation/scene/SemanticScenePlan.kt` (`SemanticSceneGenerator`, `evaluate`), `presentation/atmosphere/SceneVisualState.kt` (`SceneVisualStateMapper`), `ui/AtmosphericScene.kt` (`AtmosphericScene`, `CatalogAtmosphericScene`).\
**Behavior board intake:** Which state is authoritative at each boundary? What work happens once per dataset versus each scrub? Which input evidence is preserved?\
**UI board intake / preservation check:** What must remain visually continuous if the authority boundaries change? Are legacy previews used for active experiences?


### OX-03 — Establish a single scene decision contract — PLANNED

**Objective/scope:** Make the existing semantic reading → First City composition boundary explicit enough for rendering to consume decisions rather than reinterpret weather. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-02.\
**Exit:** Tested, source-of-truth decision boundary and unchanged First City baseline for covered conditions. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-03-establish-a-single-scene-decision-contract-behavior-board.md`; UI artwork board only if the approved scope actually changes visible UI/visual behavior. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/FirstCityComposition.kt`, `presentation/scene/FirstCityTheme.kt` (`compose`), `presentation/scene/SemanticScenePlan.kt` (`SceneReading`), `ui/AtmosphericScene.kt`.\
**Behavior board intake:** Which values are world/atmosphere/time decisions, which are graphics parameters, and how are unavailable facts represented?\
**UI board intake / preservation check:** Can existing city visuals be drawn from these decisions alone without visual regression or hidden weather calculations in drawing code?


### OX-04 — Protect scene truth and unknown states — PLANNED

**Objective/scope:** Prevent absent, stale, unsupported, or probability-only data from being rendered as confidently observed weather. This concerns faithful evidence propagation, not independent meteorological plausibility validation. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-03.\
**Exit:** Evidence matrix with fixture-backed evidence (with tests only if authorized) and at least one visible unknown-state check. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-04-protect-scene-truth-and-unknown-states-behavior-board.md`; UI artwork board only if the approved scope actually changes visible UI/visual behavior. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/SemanticScenePlan.kt` (`SceneEvidence`, `SceneReading`), `data/catalog/CanonicalWeatherFacts.kt`, `presentation/CatalogTimelinePresentation.kt`.\
**Behavior board intake:** How does each relevant missing-evidence state propagate into scene decisions and interactions?\
**UI board intake / preservation check:** What does an unknown value look like without implying clear skies, zero precipitation, or a known Moon property?


### Wave B — Make the world composable


### OX-05 — Make depth ordering enforceable — PLANNED

**Objective/scope:** Prove the spec's absolute Background → Midground → Foreground rule in the existing city, while retaining local element order and special effects semantics. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-03.\
**Exit:** Ordering assertions and visually checked overlap cases; no cross-depth inversion. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-05-make-depth-ordering-enforceable-behavior-board.md`; UI artwork board target `.codex/concepts/OX-05-make-depth-ordering-enforceable-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/FirstCityComposition.kt` (`FirstCityLayer`, `layerOrder`), `presentation/scene/FirstCityTheme.kt` (`layers`), `ui/AtmosphericScene.kt` (city draw pipeline).\
**Behavior board intake:** Which existing layers belong to which depth group? Where do full-scene wash/effects belong without breaking occlusion?\
**UI board intake / preservation check:** Does the moon pass behind the right silhouettes? Does rain have the intended near/distant relationship? What appearance must not change?


### OX-06 — Give scene elements semantic identity — PLANNED

**Objective/scope:** Identify meaningful objects independently of the draw function that happens to render them. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-05.\
**Exit:** First City elements have testable identities/roles without imposing a universal element registry. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-06-give-scene-elements-semantic-identity-behavior-board.md`; UI artwork board target `.codex/concepts/OX-06-give-scene-elements-semantic-identity-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/FirstCityComposition.kt`, `presentation/scene/FirstCityTheme.kt`, `ui/AtmosphericScene.kt`.\
**Behavior board intake:** What is the minimum stable element identity/role required for rendering, inspection, and future interaction?\
**UI board intake / preservation check:** Which drawn objects are genuinely discoverable versus merely decorative? How can inspection highlight them?


### OX-07 — Extract one reusable cloud renderer — PLANNED

**Objective/scope:** Reuse existing cloud drawing behavior behind the new scene contract; prove portability before extracting the whole renderer. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-05, OX-06.\
**Exit:** One renderer used from the chosen scene path, with before/after visual evidence and scoped state evidence. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-07-extract-one-reusable-cloud-renderer-behavior-board.md`; UI artwork board target `.codex/concepts/OX-07-extract-one-reusable-cloud-renderer-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/AtmosphericScene.kt` (`drawCityCloudFields`, `drawCityCloudBank`, existing cloud drawing functions), `presentation/scene/FirstCityComposition.kt` (`CityCloudDecision`).\
**Behavior board intake:** What input state is sufficient? What weather interpretation must stay outside drawing code?\
**UI board intake / preservation check:** Do shape, scale, cloud intensity, occlusion, and scrub transitions still match First City expectations?


### OX-08 — Extract one reusable precipitation renderer — PLANNED

**Objective/scope:** Apply the cloud extraction lesson to rain/snow without introducing impossible particle counts or false intensity. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-07, OX-04.\
**Exit:** At least clear/rain/snow examples and verified no-data behavior; no regressions in selected-time behavior. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-08-extract-one-reusable-precipitation-renderer-behavior-board.md`; UI artwork board target `.codex/concepts/OX-08-extract-one-reusable-precipitation-renderer-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/AtmosphericScene.kt` (`drawCityRainField`, `drawCitySnowField`, `drawRain`, `drawSnow`), `presentation/scene/FirstCityComposition.kt` (`CityPrecipitation`).\
**Behavior board intake:** How do type, intensity, absence, and evidence drive depiction? What state belongs to the element versus the scene?\
**UI board intake / preservation check:** Is relative intensity legible while preserving painter's depth and the city's look?


### OX-09 — Make repeated scene states deterministic — PLANNED

**Objective/scope:** Make returning to the same scene input produce the same layout of procedural details; evaluate whether current procedural paths already satisfy this. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-07, OX-08.\
**Exit:** Authorized repeatability checks or image comparisons and documented seed policy; no perceptible arbitrary popping. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-09-make-repeated-scene-states-deterministic-behavior-board.md`; UI artwork board target `.codex/concepts/OX-09-make-repeated-scene-states-deterministic-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/AtmosphericScene.kt` (clouds, precipitation, illustrative effects), `presentation/scene/SemanticScenePlan.kt` (dataset/scene identity), `presentation/scene/FirstCityTheme.kt`.\
**Behavior board intake:** Which values form stable seed identity? What changes legitimately vary with time? What remains unchanged across recomposition?\
**UI board intake / preservation check:** When scrubbing away and back, which clouds or particles should return to prior positions, and which should animate continuously?


### Wave C — Make the world explain itself


### OX-10 — Moon: inspect the object — PLANNED

**Objective/scope:** Make the displayed Moon the first genuine scene-element interaction using the spec's Observe → Touch → Learn → Dismiss loop. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-06, OX-09, OX-04.\
**Exit:** Moon tap and scoped reachable inspect interaction, correct data for selected instant, verified dismiss and scrub behavior. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-10-moon-inspect-the-object-behavior-board.md`; UI artwork board target `.codex/concepts/OX-10-moon-inspect-the-object-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/FirstCityComposition.kt` (`CityCueDecision`), `presentation/scene/FirstCityTheme.kt` (`lunarPosition`, `lunarScale`), `presentation/scene/SemanticScenePlan.kt` (`LunarDayFacts`), `ui/AtmosphericScene.kt` (`drawCityTimeCue`), `ui/CatalogTimeLabScreen.kt`.\
**Behavior board intake:** Under which visible/occluded/absent states can the Moon be selected? How is its selected-instant identity retained through scrubbing?\
**UI board intake / preservation check:** What is the hit target, affordance, inspect presentation, dismiss gesture, and accessible equivalent?


### OX-11 — Moon: explain only supported facts — PLANNED

**Objective/scope:** Extend Moon inspection with phase, illumination, rise/set, and optional trajectory **only where the available model actually supports them**. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-10.\
**Exit:** Data-support inventory, verified supported/unsupported states, no fabricated altitude/azimuth/illumination fields. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-11-moon-explain-only-supported-facts-behavior-board.md`; UI artwork board target `.codex/concepts/OX-11-moon-explain-only-supported-facts-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/SemanticScenePlan.kt` (`LunarDayFacts`, `LunarEvent`, `SceneReading`), `presentation/scene/FirstCityTheme.kt`, `presentation/CatalogTimelinePresentation.kt`.\
**Behavior board intake:** Which requested fields exist versus need future data/model work? What is the fallback for absent values?\
**UI board intake / preservation check:** What distinction exists between Level 1 inspect and deeper explanation? How are partial readings presented honestly?


### OX-12 — Put one essential reading inside the world — PLANNED

**Objective/scope:** Introduce one **canonical selected-instant, source-supported** diegetic display (temperature is a plausible first candidate), using the same live/simulated/replay contract without invented numbers. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-03, OX-06, OX-04.\
**Exit:** Reading updates correctly with timeline selection and units; missing state verified; no loss of essential accessibility. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-12-put-one-essential-reading-inside-the-world-behavior-board.md`; UI artwork board target `.codex/concepts/OX-12-put-one-essential-reading-inside-the-world-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/CatalogTimelinePresentation.kt` (`CatalogTimelineView`), `ui/CatalogTimeLabScreen.kt`, `presentation/scene/FirstCityTheme.kt`, `ui/AtmosphericScene.kt`.\
**Behavior board intake:** Which selected-instant value, unit, provenance, and absence policy feed the display?\
**UI board intake / preservation check:** Which existing world object can carry it clearly at realistic device sizes? Does it remain legible and accessible?


### OX-13 — Establish a progressive weather-information ladder — PLANNED

**Objective/scope:** Make Experience → Inspect → Understand → Nerd Out coherent around the scene, while preserving precise forecast access. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-10, OX-12.\
**Exit:** Board-approved information hierarchy with at least one end-to-end flow from world to deeper forecast and back. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-13-establish-a-progressive-weather-information-ladder-behavior-board.md`; UI artwork board target `.codex/concepts/OX-13-establish-a-progressive-weather-information-ladder-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/CatalogTimeLabScreen.kt`, `ui/AccessibleForecastBrowser.kt`, `presentation/CatalogTimelinePresentation.kt`, OX-10 and OX-12 UI.\
**Behavior board intake:** Which measurements are essential, which are available through inspect, and how does deep access work across missing data?\
**UI board intake / preservation check:** How is information discovered without permanent cards? How do keyboard/screen-reader paths reach the same facts?


### OX-14 — Rebalance the normal screen toward the scene — PLANNED

**Objective/scope:** Reduce conventional overlay dominance **only after** replacement access paths are verified. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-13.\
**Exit:** Before/after experience review, precise data remains accessible, verified normal/inspect/advanced modes. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-14-rebalance-the-normal-screen-toward-the-scene-behavior-board.md`; UI artwork board target `.codex/concepts/OX-14-rebalance-the-normal-screen-toward-the-scene-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/CatalogTimeLabScreen.kt`, `ui/AccessibleForecastBrowser.kt`, `ui/AtmosphericScene.kt`.\
**Behavior board intake:** What informational capability must never disappear when overlay UI changes?\
**UI board intake / preservation check:** Which existing temperature/humidity/conditions treatments should move, disappear, or become user-invoked? Can a user still orient quickly?


### Wave D — Build the scene laboratory


### OX-15 — Time Lab: transition scenarios — PLANNED

**Objective/scope:** Turn the existing fixture and Time Lab infrastructure into repeatable scene-transition tests, beginning with a few high-value pairs. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-09, OX-10.\
**Exit:** Repeatable clear→cloudy, cloudy→rain, day→night, and moon-obscured scenarios (or documented subset with a follow-on slice). Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-15-time-lab-transition-scenarios-behavior-board.md`; UI artwork board target `.codex/concepts/OX-15-time-lab-transition-scenarios-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `ui/CatalogTimeLabScreen.kt`, `app/src/debug/java/com/oxygen/weather/timeline/data/debug/` (authored catalog and weather fixtures), `presentation/scene/SemanticScenePlan.kt`.\
**Behavior board intake:** What constitutes a replayable weather/time scenario, and how do fixtures preserve evidence and selected instants?\
**UI board intake / preservation check:** How does a developer choose a transition and inspect before/during/after without a cumbersome debug workflow?


### OX-16 — Time Lab: inspect layers and element bounds — PLANNED

**Objective/scope:** Give reviewers visibility into semantic role, depth group, element bounds, and hit targets in a debug-only inspection surface. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-05, OX-06, OX-10.\
**Exit:** Can identify at least a cloud, Moon, and one foreground object; inspection does not alter normal output. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-16-time-lab-inspect-layers-and-element-bounds-behavior-board.md`; UI artwork board target `.codex/concepts/OX-16-time-lab-inspect-layers-and-element-bounds-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/FirstCityComposition.kt`, `ui/AtmosphericScene.kt`, `ui/CatalogTimeLabScreen.kt`.\
**Behavior board intake:** What trace data can be exposed without coupling production rendering to debug UI?\
**UI board intake / preservation check:** Which visibility toggles, bounds, selection, and hit-target overlays make problems diagnosable?


### OX-17 — Validate catalog-wide scene readiness and scrub cost — PLANNED

**Objective/scope:** Measure the existing full-window semantic plan before choosing whether to implement spec-style day compilation or extra caches. No optimization or compilation redesign is authorized by this measurement slice. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-02, OX-09, OX-15.\
**Exit:** Baseline measurements, a board decision to retain full-window plan or change compilation granularity, and scoped cross-day/window-boundary evidence. **No pre-rendering the catalog into full-size bitmaps.** Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-17-validate-catalog-wide-scene-readiness-and-scrub-cost-behavior-board.md`; UI artwork board only if the approved scope actually changes visible UI/visual behavior. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/CatalogTimelinePresentation.kt` (`CatalogTimelinePresenter`), `presentation/scene/SemanticScenePlan.kt` (`generate`, `evaluate`, `.at` lookups), `data/catalog/CatalogWindow.kt`, `data/catalog/CatalogActivationCoordinator.kt`, `ui/CatalogTimeLabScreen.kt`.\
**Behavior board intake:** At what dataset size does generation, lookup, allocation, or memory become problematic? How is scene identity invalidated on catalog updates?\
**UI board intake / preservation check:** What scrub latency, jitter, and visual continuity are acceptable on representative devices?


### Wave E — Prove portability


### OX-18 — Render a second minimal world from the same weather truth — PLANNED

**Objective/scope:** Prove that a non-city scene can consume the shared scene model without copying First City's weather interpretation; use the smallest viable second composition, not an entire polished scene pack. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-07, OX-08, OX-17.\
**Exit:** Side-by-side scenes driven by the same catalog selection, one shared reusable cloud/precipitation primitive, and no duplicated meteorological interpretation. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.md`; UI artwork board target `.codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `presentation/scene/FirstCityTheme.kt`, `presentation/scene/FirstCityComposition.kt`, reusable output from OX-07/08, `ui/AtmosphericScene.kt`.\
**Behavior board intake:** Which contracts are truly scene-independent? Which visual decisions must be scene-specific?\
**UI board intake / preservation check:** Is the second world recognizably distinct while showing the same weather/timing faithfully?


### OX-19 — Decouple weather location from scene identity — PLANNED

**Objective/scope:** Make changing the world independent of changing the selected geographic weather catalog. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-18.\
**Exit:** Same selected place and instant across scene switching, consistent data correctness and interaction behavior. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-19-decouple-weather-location-from-scene-identity-behavior-board.md`; UI artwork board target `.codex/concepts/OX-19-decouple-weather-location-from-scene-identity-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** `data/catalog/CatalogActivationCoordinator.kt`, `presentation/CatalogTimelinePresentation.kt`, `presentation/scene/FirstCityTheme.kt`, second-world prototype from OX-18.\
**Behavior board intake:** What persists on a scene switch? Which settings are world-specific and which are forecast-wide?\
**UI board intake / preservation check:** How does a user select or preview a world without accidentally changing location or resetting time?


### OX-20 — Define the future scene-pack boundary from real evidence — PLANNED

**Objective/scope:** Record what would be required to package two proven worlds safely; do not build a store, downloader, or public scene DSL yet. Scope is limited to this outcome; the boards may propose a split or a changed boundary before approval.\
**Depends on:** OX-18, OX-19.\
**Exit:** Versioned boundary proposal with two example scene manifests or equivalent design evidence; explicit deferred work list. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.\
**Evidence:** **Future target, not created:** `.codex/test-artifacts/<cycle-id>/` and `.codex/history/<cycle-id>.md` (plus `.codex/history/plans/<cycle-id>.md` at closeout). Assign the exact independent cycle ID and final evidence paths at cycle planning.\
**Slice board(s):** **Proposed, not yet created or approved:** behavior board target `.codex/concepts/OX-20-define-the-future-scene-pack-boundary-from-real-evidence-behavior-board.md`; UI artwork board target `.codex/concepts/OX-20-define-the-future-scene-pack-boundary-from-real-evidence-ui-board.svg`. Record owner approval, constraints, and input fingerprints in the bounded plan before activation.\
**Repo anchors (review starting points):** OX-18/19 outcomes; `presentation/scene/FirstCityTheme.kt`, resource/assets used by `ui/AtmosphericScene.kt`.\
**Behavior board intake:** Which manifest/compatibility/capability constraints are necessary to keep downloaded packs declarative and safe?\
**UI board intake / preservation check:** What scene-specific assets, type, interactive capabilities, and fallback behavior would a pack need to declare?


## 6. First intake and evidence reuse

**Suggested first Scene Engine intake:** `OX-01` only **after** the new workstream is adopted and made eligible in the canonical `docs/ROADMAP.md` by owner-directed ordering. Do not change the existing repository-global `NEXT` merely because this is the first Scene Engine slice. Do not assign a cycle ID until the actual repository cycle is planned.

In its `SLICE` stage, read `.codex/current.md`, `.codex/README.md`, the applicable `oxygen-*` skill, relevant `docs/ROADMAP.md` entries and authority passages, and the completed WX/ATM/DFM baseline records that directly overlap OX-01. Use `python scripts/dev.py workflow` in the development environment. Inspect `CatalogTimelinePresenter`, `SemanticScenePlan`, `FirstCityTheme`, `FirstCityComposition`, `CatalogAtmosphericScene`, `CatalogTimeLabScreen`, and existing evidence only to the extent needed for the slice's named questions. Relevant Scene Engine spec sections are reference material, not overriding product authority.

During `BOARDS`, commission the behavior board (observable invariants, ambiguities, source boundaries, regression risks, and acceptance evidence). OX-01 may include visual current-state review without automatically requiring new UI artwork if it is documentation-only; if any visible change is proposed, require the corresponding UI artwork board. During `DRAFT` and `REVIEW`, iterate the plan under the established gates. No implementation begins until owner-approved boards, final plan, status/dependency eligibility, and activation are recorded. A screenshot alone never replaces a board or proves installed acceptance.

### Board kickoff wording (prompt only; not a board artifact)

> Review **[slice ID/title]** against the current Oxygen repository, controlling product/roadmap/workflow authorities, existing completed evidence, and the proposed Scene Engine specification. Treat this slice as a bounded intake problem, not a pre-approved implementation or architecture. Identify observed behavior to preserve, source-backed uncertainties, relevant paths, real dependencies, and measurable exit criteria. Do not implement code. Produce the objective-specific behavior board; for a visual/UI-changing slice, produce the UI artwork board with affected states. Record the required owner decisions and approval constraints for the repository's normal cycle plan.

## 7. Open decisions — not preapproved solutions

1. Whether the current window-wide `SemanticScenePlan` should stay or change only after OX-17 measurements.
2. Whether the existing city composition and layer declarations can be reconciled without a shared runtime abstraction.
3. How scene-wide wash/effects interact with strict background/midground/foreground ordering.
4. Which `SceneVisualStateMapper` routes are active, compatibility, debug, or legacy paths.
5. What the current lunar model actually supports; no inferred illumination, altitude, azimuth, or trajectories.
6. Which source-supported essential reading becomes diegetic first and how units, unknowns, and legibility work.
7. Whether a future observatory world becomes the product default; First City remains protected unless an explicit owner decision changes that contract.
8. Whether any DSL, pack manifest, registry, asset cache, or commercial delivery boundary is necessary once a second world is proven.
9. How Scene Engine adoption interleaves with existing WX/ATM/DFM and other roadmap obligations, without duplicating completed exits or displacing `NEXT`.
10. Whether an earlier, bounded transition fixture proof should precede some Wave B/C rendering changes; change dependencies only through an owner-approved roadmap update.

## 8. Definition of progress

**First City remains correct → scene authority is explicit → bounded reusable depth/elements → stable scrub behavior → supported Moon inspect → source-backed measurements inside the world → Time Lab evidence → a second world proves portability.**

Progress is demonstrated through each approved slice's exit and actual evidence, not abstractions, roadmap status alone, passing builds alone, hypothetical tests, or unverifiable visual claims. **The existing Oxygen workflow governs the entire implementation process.**
