# Oxygen Time Lab — scoped roadmap

Roadmap format and status rules: [`ROADMAP_SPECIFICATION.md`](ROADMAP_SPECIFICATION.md).
Product and semantic boundaries: [`SPECIFICATION.md`](SPECIFICATION.md).

## Cross-cutting hard rule

Every visual enhancement must preserve timeline responsiveness, gesture predictability, and readability, as defined in `docs/SPECIFICATION.md`. Each visual cycle that changes or reviews visual behavior must include an acceptance check for all three.

## Owner-directed data and verification policy — 2026-11-01

The app visualizes supplied supported values and categories; design slices do not independently judge meteorological plausibility. A selected supported `CLEAR`, `PARTLY_CLOUDY`, or `CLOUDY` condition is represented as dry. Slice acceptance checks the value-to-presentation mapping and only the behavior inside that slice's scope.

Design and implementation slices do not run RTL, TalkBack, or broad system/environment test matrices. Reserve those cross-cutting checks for roadmap slices explicitly designated `TEST`. This changes verification scope for future work and does not rewrite historical cycle evidence.

## Workstream priority

WX.1, WX.2, WX.3, WX.5, WX.6, WX.9–WX.13, WX.13R, WX.15–WX.20, WX.22, WX.23, WX.24, WX.10R, WX.25, and WX.26 are `DONE`. WX.27 and WX.28 are `DEFERRED` until the product is finished and polished and release-readiness work is appropriate. WX.30 was cancelled by the owner on 2026-11-03 because no defect in the app's actual source path had been demonstrated; any future need must be planned as a new slice through the workflow. WX.31 remains `DEFERRED` pending a separately established need and workflow review. G.5, SG.0, SG.1, SG.2, SG.3, SG.4, SG.4a, SG.9, and BT.1 are `DONE`; cycle 079 was owner-accepted with unverified rain/unavailable-weather assigned to SG.4 and later SG slices, cycle 080 closed SG.3, cycle 081 closed SG.4 with focused installed evidence and owner-directed test limits, cycle 082 closed SG.4a with installed cloud-height evidence, and cycle 083 closed SG.9 with the typed catalog scene path and installed state comparisons. DFM.4.1–DFM.4.6 are `DONE` in cycles 085–090. **SG.5 is `DONE` in cycle 091;** SG.10 is `DONE` in cycle 092 with approved boards and installed evidence; SG.6 is `DEFERRED`, SG.7 remains `PLANNED` and depends on SG.6, and SG.8 is `DONE` in cycle 093 with approved boards, provider-boundary resolution, responsive compact layout, focused tests, and installed evidence. DFM.4's revised behavior/workbench and clean-main/readout artwork boards were approved on 2026-11-01, as recorded in the DFM.4 slice below; DFM.4.6 closed the split DFM.4 workstream in cycle 090 on 2026-11-02. The SG.5 behavior and UI artwork boards were approved on 2026-11-02; cycle 091 closed with installed evidence and scoped NOT RUN states recorded in its closeout and evidence README. SG.8 lunar work remains separate from SG.4; its approved board constraints and cycle plan are recorded below. Every unfinished slice requires its objective-specific behavior board and, for UI/visual work, UI artwork board(s) before its cycle plan can be finalized. The owner-directed P.2.16 correction is DONE in cycle 048. SG work does not depend on deferred release-readiness slices.

WA.1–WA.6 are `DONE`; the six-row Weather Artwork sequence is complete. SG.11a Sky mapping is `DONE` in cycle 102, SG.11b Rain and ice mapping is `DONE` in cycle 103, SG.11c Snow and mixed precipitation mapping is `DONE` in cycle 104, SG.11d Visibility and wind mapping is `DONE` in cycle 105, SG.11e Storm and fallback mapping is `DONE` in cycle 106, SG.11f Additional events mapping is `DONE` in cycle 107, SG.12's board-acceptance correction is `DONE` in cycle 108, and SG.13 is `DONE` in cycle 109 with its approved skyline-alignment board and installed evidence.

**Owner-directed interaction slice — 2026-11-05:** The owner requested replacing the daily timeline's hold-to-enter-hourly gesture with a double-tap scale toggle, then directed removal of timeline single-tap selection while retaining daily drag navigation and hourly scrubbing. The revised combined behavior/UI artwork board and cycle 110 plan were approved as shown on 2026-11-05. The owner authorized execution; the bounded exception was recorded in both governing specifications before activation.

**Cycle execution update — 2026-11-05:** Cycle 110 completed P.2.17 on the installed Pixel 3 XL. Active-catalog and fixture/debug checks passed for double-tap scale toggling with selected-time continuity, no-op single taps, daily drag, hourly scrub, stationary hold and hold-then-drag, main-area collapse, and fixture main-area reset. Captures and verification limits are recorded in `.codex/test-artifacts/110-p2-double-tap-scale-toggle/README.md` and `.codex/history/110-p2-double-tap-scale-toggle.md`.

**Owner-directed reprioritization — 2026-11-06:** Cycle 111 completed P.2.18. The owner cancelled the unactivated P.2.19 daily-drag correction and selected P.2.20 daily/hourly icon refinement as the next priority. P.2.19 remains recorded as CANCELLED for roadmap traceability; cycle 112 is closed as cancelled before activation. The owner approved P.2.20's combined behavior/UI artwork board on 2026-11-06. Cycle 113 then completed P.2.20 with installed Pixel evidence; the daily-drag/noon discrepancy remains outside that slice.

**Owner-directed SG follow-up — 2026-11-06:** A Florence LIVE catalog contains Sunday 11 PM temperature, precipitation chance, and wind, while the installed readout shows unavailable values after 11:30 PM. The owner requested the `SG-missing-eleventh-hour-data` slice and a first-draft plan on the `scene_generator` branch. The proposed late-day representative-display rule would amend the current SG.1 half-hour limit and requires an objective-specific approved behavior/UI board before plan finalization or activation.

### Owner-directed reprioritization — 2026-11-01

DFM.4 was promoted from `PLANNED` to `NEXT` so the debug workbench and location-search command session can be completed before further UI-related slices. SG.5 was moved from `NEXT` to `PLANNED`; SG.6–SG.8 remain `PLANNED`. Existing slice dependencies are unchanged. At reprioritization, DFM.4's supplemental clean-main/readout artwork was still pending; the product owner subsequently approved the revised board set on 2026-11-01, as recorded in the DFM.4 slice below.

**Owner-directed sequencing update — 2026-11-02:** DFM.4.6 closed the split DFM.4 workstream in cycle 090. SG.5 is now the sole `NEXT` slice; its approved scope centers the loaded city with selected temperature and atmosphere in one quick-read group.

**Owner-directed copy follow-up — 2026-11-02:** After cycle 091, the owner requested removal of “Selected” only from the main readout date/time line and chose the main timestamp only. The `Selected location` fallback and forecast-browser copy remain unchanged. The owner approved both SG.10 boards as written; cycle 092 closed with scoped installed evidence. SG.6 is the next eligible follow-up.

**Owner-directed deferral and sequencing update — 2026-11-02:** The owner deferred SG.6. SG.7 remains ineligible because it depends on SG.6; SG.8 became the next eligible SG slice. The owner approved SG.8's behavior and UI artwork boards and requested activation of cycle 093.

**Owner-directed cancellation — 2026-11-03:** The owner cancelled WX.30 before implementation because no defect in the app's actual source path had been demonstrated. Remove its plan and board artifacts and clear the current-cycle pointer; any future need must be assessed and planned as a new slice through the workflow. WX.31 is deferred until an independent provider/failover need is established and reviewed through that workflow.

**Owner-directed sequencing update — 2026-11-03:** After WA.4 completed, the owner directed planning of WA.5. WA.5 is the sole `NEXT` slice; its dependency WA.4 is `DONE`, the shared behavior and UI artwork boards are approved, and WA.6 remains `PLANNED` behind WA.5.

**Cycle execution update — 2026-11-03:** WA.5 completed in cycle 098 with installed Pixel 3 XL evidence for all eight Storms + Fallback symbols and a saved/reopened authored simulation. Scoped results and unrun checks are recorded in the cycle closeout and evidence README. WA.6 is now the sole `NEXT` slice.

**Cycle execution update — 2026-11-03:** WA.6 completed in cycle 099 with installed Pixel 3 XL evidence for all eight Additional Events symbols and a saved/reopened authored simulation. Scoped results and unrun checks are recorded in the cycle closeout and evidence README. The six-row Weather Artwork sequence is complete; WA.6 is no longer NEXT.

**Owner-directed splits — 2026-11-04:** The owner moved full 48-event mapping out of the center-readout draft into SG.11, then divided mapping into SG.11a–f by the six approved artwork rows. SG.11a is NEXT; SG.11b–f and SG.12 are PLANNED. The approved SG.11 behavior and artwork boards and complete fallback hierarchy remain shared authority for the six row slices. The existing cycle 101 center-readout draft remains SG.12; cycle 102 now plans SG.11a.

**Cycle execution update — 2026-11-05:** SG.11b completed in cycle 103 with installed Pixel 3 XL evidence for all eight Rain and ice states, exact-code mapper checks, and version 1–4 replay compatibility checks. SG.11c completed in cycle 104 with installed Pixel 3 XL evidence for all eight Snow and mixed states, version-5 recording/replay checks, and direct selected-time interaction evidence. By owner direction, the five-state center label/date-time crowding is recorded as a limitation and deferred to SG.12. SG.11d completed in cycle 105 with installed evidence for all eight Visibility and wind identities, the approved code 45/48 assignments, canonical-only fallback states, focused version-5/legacy checks, and hourly timeline selection. SG.11e completed in cycle 106 with installed evidence for all seven Storm/fallback identities and the unavailable state, focused mapper/replay checks, and direct timeline interaction. The owner accepted the observed scrub mismatch between the unavailable event overlay and central condition readout as a documented limitation assigned to SG.12. SG.11f completed in cycle 107 with installed evidence for all eight canonical-only Additional identities, version-5 reload, full ledger/priority reconciliation, and direct timeline scrub evidence. The same scrub-time event/readout disagreement remains assigned to SG.12. Following board approval on 2026-11-05, SG.12 activated in cycle 101.

**Cycle execution update — 2026-11-05:** SG.12 completed in cycle 101 against the owner-approved “Central Selected Forecast Readout” artwork board. Installed Pixel 3 XL evidence covers regular cloudy, sunny, precipitation, carried-gap and unavailable states, the 360×800 dp compact viewport, picker open/dismiss, selected-time changes, and central swipe/double-tap. The carried-gap fixture has no WA assignment at its source hour; its capture verifies the source disclosure with the honest unavailable symbol fallback, not a carried typed symbol. Scoped evidence and verification limits are recorded in `.codex/test-artifacts/101-sg11-center-readout/README.md` and `.codex/history/101-sg11-center-readout.md`. SG.6 remains deferred and SG.7 depends on SG.6; no new slice was activated.

**Cycle execution update — 2026-11-05:** Cycle 108 corrected the lower-row acceptance gap with board-matched inline precipitation and direction-bearing wind cues. Pixel 3 XL release captures cover 411×846 dp and 360×800 dp with no debug DEMO/REPLAY bar. Evidence and limits are recorded in the cycle-108 README and closeout. SG.12 is complete.

## Build tooling

### BT.1 — Enable Gradle configuration cache — DONE

**Objective/scope:** Enable Gradle configuration caching through one root property for this repository's wrapper-driven builds. Preserve the existing build task selection, plugin versions, and application behavior.
**Depends on:** None.
**Exit:** Root `gradle.properties` enables configuration caching; `:app:assembleDebug` and `:app:lintDebug` both complete with the setting, store configuration entries and reuse them on repeat invocation; configuration-cache problems remain build failures.
**Slice board(s) — Behavior:** Product-owner approved `.codex/concepts/BT.1-configuration-cache-behavior-board.png` on 2026-10-31, with no additional constraints stated (editable `.svg` and detailed `.md` at the same stem). No UI artwork board applies.
**Evidence:** Cycle 077 command logs and result record: `.codex/test-artifacts/077-bt1-configuration-cache/`; closed outcome: `.codex/history/077-bt1-configuration-cache.md`; archived plan: `.codex/history/plans/077-bt1-configuration-cache.md`.

## Roadmap transition record — Scene Generation pivot

**Jump timestamp:** 2026-10-29 20:47:50 America/Chicago / 2026-10-30 01:47:50 UTC.

**Source:** This roadmap immediately before the pivot: WX.25 active in cycle 071; WX.26 next after WX.25; WX.27 and WX.28 planned; WX.29 cancelled; then ATM.8 → ATM.9/ATM.10 → ATM.7, followed by deferred environment packs. The source architecture was `docs/OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md`.

**Destination:** WX.26 remains next after WX.25 and WX.27/WX.28 retain their order. After WX.28, the proposed `SG.1`–`SG.7` workstream replaces the unstarted ATM.7–ATM.10 scene-mapping sequence and reframes future environment packs as theme-specific scene compositions. Completed ATM.0–ATM.6 and DFM.1–DFM.3 remain historical and unchanged.

**First-slice insertion:** 2026-10-29 20:54:52 America/Chicago / 2026-10-30 01:54:52 UTC. The owner-directed Codex kickoff prompt was saved as `.codex/prompts/scene-generation-roadmap-kickoff.md` and added as SG.0, a documentation-only review slice dependent on WX.28. Existing SG.1–SG.7 IDs and outcomes were preserved; SG.1 now depends on SG.0.

**Owner-directed reprioritization:** 2026-10-30. WX.27 and WX.28 were deferred because release-readiness work is premature while at least two roadmaps of product work remain. SG.0 became NEXT and may review the scene-generation proposal before release-readiness work. The parked WX.27 cycle plan was not activated.

**Owner-directed SG dependency and acceptance update:** 2026-10-30. SG.1–SG.7 do not depend on WX.27, WX.28, or cancelled WX.29; the existing WX dependency edges remain unchanged. Existing-product RTL and TalkBack acceptance is resolved and accepted for SG to inherit as its baseline. Review font scale in SG visual slices when the changed layout or content makes it applicable. Historical cycle records retain what each cycle did and did not test.

**Specification audit insertion:** 2026-10-30. The owner selected one documentation-only G.5 resolution slice ahead of SG.0. G.5 was completed in cycle 074; SG.0 depended on G.5 and completed in cycle 075. The owner approved G.5's behavior board later that day. Cycle 074 closed with findings dispositions and documentation evidence.

**Planning basis:** Draft `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` (OXY-SG-001). It defines semantic asset/layer/timeline tracks, theme-owned composition and placement, day/night transitions, deterministic scene planning, and focused acceptance. The draft is subordinate to controlling product authorities and does not authorize implementation.

## Slice board gate for unfinished slices

Project-wide, every unfinished slice on every roadmap requires an objective-specific behavior board and, for UI/visual work, a UI artwork board; a combined slice board may cover both when each is clear. This applies to current and future workstreams throughout the repository. Behavior boards can be finite-state-machine-like flowcharts of states and transitions annotated with text and images, or content maps, contracts, schemas, sequences, and boundary diagrams. UI artwork boards show the intended interface/scene and relevant states. The product owner must approve the required board(s) before the plan can be finalized. Record paths, approval, constraints, and the boards' relationship to scope and acceptance in the plan. A pending board may be prepared after a draft plan is started, but the plan remains unfinalized and cannot be activated until approval is recorded. Prompts, target paths, screenshots, and renderer output alone do not satisfy this gate. Boards are design inputs, not installed acceptance evidence. ATM.5 artwork and the WX.3 and WX.5 contract boards are approved; the WX.22 debug selector storyboard was approved on 2026-10-28; and the WX.23 projection-boundary board was approved on 2026-10-29. The WX.24 absolute-axis board was owner-approved on 2026-10-29; approval remains pending for other unfinished slices. Historical completed slices are not reopened by this rule.

## G — Roadmap governance

### G.1 — Adopt and apply the roadmap specification — DONE

**Objective/scope:** Define the lightweight roadmap format and migrate every existing Oxygen Time Lab leaf slice to it.
**Depends on:** None.
**Exit:** `docs/ROADMAP_SPECIFICATION.md` defines status, required slice fields, evidence, and failure/replanning rules; every roadmap leaf slice conforms; `AGENTS.md` identifies the roadmap specification as authority; `git diff --check` passes.
**Evidence:** `.codex/history/008-roadmap-specification.md`; `.codex/history/plans/008-roadmap-specification.md`; `.codex/test-artifacts/008-roadmap-specification/README.md`.

### G.2 — Adopt applicable oxygenWX skills and agent rules — DONE

**Objective/scope:** Bring the reusable oxygenWX planning, review, execution, context-audit, history-compaction, and model-rating skills into this repository's `.agents/skills/`, adapted to Oxygen Time Lab authorities and constraints.
**Depends on:** G.1.
**Exit:** The selected skill set is installed with valid skill metadata and no production-only assumptions; `AGENTS.md` describes this project's actual workflow and nonnegotiable boundaries; skill validation and `git diff --check` pass; adoption evidence and limitations are recorded.
**Evidence:** `.codex/history/009-agent-skills-ruleset.md`; `.codex/history/plans/009-agent-skills-ruleset.md`; `.codex/test-artifacts/009-agent-skills-ruleset/README.md`.

### G.3 — Adopt the Oxygen experience specification — DONE

**Objective/scope:** Verify and adopt `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` as the experience-level companion to `docs/SPECIFICATION.md`; reconcile current-tree discrepancies and stale README claims.
**Depends on:** G.1 and G.2.
**Exit:** The authority relationship is explicit, the proposal's status reflects adoption without claiming unverified acceptance, current behavior discrepancies and owner decisions are recorded, stale README behavior claims are corrected, and documentation/workflow checks plus `git diff --check` pass.
**Evidence:** `.codex/history/022-experience-spec-adoption.md`; `.codex/history/plans/022-experience-spec-adoption.md`; `.codex/test-artifacts/022-experience-spec-adoption/README.md`.

### G.4 — Ingest the Living Atmosphere Engine proposal — DONE

**Objective/scope:** Reconcile OXY-ATM-001 against controlling prototype and experience authorities and the current source tree; adopt it as a subordinate architecture proposal for roadmap planning; add bounded ATM/ENV follow-ups.
**Depends on:** G.3.
**Exit:** Authority precedence and adoption limits are explicit; every cited source path is compared with the proposal's baseline; conflicts and missing evidence are recorded; proposed ATM/ENV slices use this roadmap's leaf format; documentation checks pass without application changes.
**Evidence:** `.codex/history/023-atmosphere-spec-intake.md`; `.codex/history/plans/023-atmosphere-spec-intake.md`; `.codex/test-artifacts/023-atmosphere-spec-intake/README.md`.

### G.5 — Resolve specification audit findings — DONE

**Objective/scope:** Resolve the ten prioritized findings in `findings.md` as one documentation-only clarification of `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`.
**Depends on:** G.3.
**Exit:** All ten findings have explicit dispositions; both specifications agree on selected-time navigation, data gaps, reset, calendar swipes, conditional alert scope, and acceptance evidence.
**Slice board(s) — Behavior:** Owner-approved 2026-10-30 at `.codex/concepts/G.5-specification-clarity-behavior-board.md`, with no additional constraints. The board governs the selected-time, availability, reset, and alert contract in plan 074. This documentation-only slice has no UI implementation or visual change.
**Evidence:** `.codex/test-artifacts/074-g5-specification-clarity/README.md`; `.codex/history/074-g5-specification-clarity.md`; `.codex/history/plans/074-g5-specification-clarity.md`.

### G.6 — Compact closed workflow history — DONE

**Objective/scope:** Reduce routine context in 47 completed G, P.1, P.2, ATM, DFM, and WA roadmap entries and relocate four verified stale plan records. Leave BT.1 and WA.3 intact under the owner-approved revised scope. Preserve all active work, durable authority, and exact recovery paths. Documentation and workflow records only.
**Depends on:** None.
**Exit:** Every reduced entry retains its ID, title, DONE status, order, dependency, concise outcome and exit, board approval reference, and exact history/evidence links; applicable lasting rules are confirmed in authority before reduction; four stale plans remain content-preserved and discoverable under `.codex/history/plans/drafts/`; active and deferred records remain intact; references resolve; workflow and final diff checks pass.
**Slice board(s) — Behavior:** Owner-approved flowchart `.codex/concepts/G.6-history-compaction-behavior-board.svg` on 2026-11-04 (editable `.dot`, review `.png`, and detailed `.md` at the same stem); no additional constraints stated. No UI artwork board applies.
**Evidence:** `.codex/test-artifacts/100-g6-history-compaction/README.md`; `.codex/history/100-g6-history-compaction.md`; `.codex/history/plans/100-g6-history-compaction.md`.

## WX — OxygenWX unified 44-day weather catalog and production data path

**Status:** Integrated into `docs/ROADMAP.md` after a format, dependency, authority, and provider-capability audit. WX.1 is DONE in cycle 043. WX.17 adds a dormant geocoding contract and adapter; cycle 061 wires selected-location catalog activation, while the active catalog remains separate from the current SampleForecast renderer.

**Authority transition:** WX.1 records the owner-approved successor direction. The current prototype remains the only integrated app behavior; bounded WX data-domain work may proceed without changing app behavior. The deprecated oxygenWX application is a reference only; do not copy its app structure or introduce its four-page Home pager. The immersive elastic timeline remains the successor app's primary experience.

**Roadmap priority:** WX.1–WX.26 work listed above is complete, with exact cycle references preserved in its slice records. WX.27 and WX.28 remain DEFERRED as independent release-readiness outcomes; neither gates SG work. WX.30 was cancelled before implementation because no app-path defect had been demonstrated; WX.31 is deferred pending a separately established need. G.5, SG.0, SG.1, SG.2, SG.3, and BT.1 are DONE; cycle 079 is owner-accepted with rain/unavailable-weather coverage assigned to SG.4 and later SG slices. Cycle 080 closes SG.3 with its focused evidence and test limits recorded. DFM.4 is the approved follow-up after SG.2. P.2.16 is DONE in cycle 048. Cycle 045 closed for WX.3; its contract board was owner-approved on 2026-10-27.

**Slice board gate:** Every unfinished WX slice requires its objective-specific behavior board and, for UI/visual work, a UI artwork board before its cycle plan can be finalized. Existing concept paths below are board targets, not evidence of owner approval. The WX.3 annotated catalog-contract board was approved on 2026-10-27 as shown, with its constraints recorded in the active plan/evidence. The WX.5 annotated window-completeness board was approved on 2026-10-27 as shown, with its constraints recorded in plan 047. The WX.6 fixture-adapter board, WX.10 simulator board, and WX.11 record/replay board were approved on 2026-10-27 as shown, with their constraints recorded in their plans/evidence. Other proposed `.codex/concepts/WX.*.png` paths remain targets. Behavior boards may use flows, states, schemas, sequences, or boundaries annotated with text and images. UI artwork is additionally required for UI/visual outcomes. Record approval and constraints in the plan; device/installed acceptance evidence remains separate.

**Cycle/evidence convention:** On starting a slice, assign the next **chronological** cycle ID by the repository's workflow. Each planned evidence line below means the following target paths are allocated **when that slice is planned**, not that files already exist: `.codex/plans/<cycle-id>.md`, `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` after closure. Record the roadmap slice ID in the plan and history. Run `python scripts/dev.py workflow` and the actual checks authorized by the bounded cycle. Do not invent passes, screenshots, tests, or evidence. The project `AGENTS.md` currently says not to add/run tests unless asked; these exits specify the **planned verification** for owner authorization in each cycle, not authorization to run tests now.

**Delivery contract across all WX slices:**

- **One canonical catalog schema** for live API, deterministic simulation, and recorded replay. Origin is metadata, not a parallel weather type system. Provider DTOs never enter Compose or the atmospheric engine.
- Each catalog belongs to a stable locally assigned location ID, coordinates, and IANA `ZoneId`; it is anchored to an explicit `asOf: Instant`. The target **44 local dates** are `D-30` through `D-1` (history), then `D` through `D+13` (forecast), where `D` is the location's date at `asOf`. The current day is included in the forecast window. The window contains 44 **calendar dates**, not a guaranteed 1,056 hourly values; DST, incomplete coverage, and provider gaps are real.
- Hour validity uses `Instant`; local date/labels use the location's timezone. Distinguish forecast, archived model forecast, reanalysis, actual observation, and synthetic meteorological products. Open-Meteo Forecast API `past_days` is **archived model/forecast data**, not verified station observation. Any omitted or unsupported field remains unavailable, never defaulted to zero.
- Model clear/cloud/rain/snow/storm **and severe-weather facts** using independent typed conditions, quantitative fields, phenomena, and probabilities where supplied. Thunderstorm probability is not generic precipitation chance; snow depth is not new snowfall; precipitation probability is not precipitation amount/rate. No invented hail, lightning, tornado probability, intensity, or severity. **Official alerts are a separate future subsystem**, not catalog conditions or warning flags.
- Preserve `DebugForecastCatalog`'s **12 existing scenario IDs**, **three transition sequences**, `DebugForecastProfiles`' **three complete 7/11/14-day loaded profiles**, `SampleForecast`, the current debug picker, and current screen/gesture/atmosphere behavior until individually regression-gated. The 12 rows are a starting test matrix, **not** the full weather-condition vocabulary. Keep the legacy `STORM_WARNING_PREVIEW` debug-only; never claim it represents an official warning.
- Live and simulated data cannot be field-merged implicitly. Switching must atomically replace a validated whole dataset, preserve selection by absolute instant where possible, clamp otherwise, reject stale async responses, and keep real, simulated, and replay caches isolated. A cached real dataset retains real origin plus explicit cache/freshness state.
- Preserve the atmospheric scene, selected-time authority, interactive timeline, screen-reader forecast browser, accessibility, RTL/reduced-motion semantics, and the hard triad of **responsiveness, predictable gesture ownership, and readability**. Decorative interpolation never fabricates hourly meteorological facts.
- Do not start production integration with an Android application-ID/signing migration, unrelated UI redesign, an official-alert subsystem, or a port of the deprecated application's architecture. Manual location search comes before any optional device-location permission.

**Owner-approved slice consolidation (2026-10-26):** These unstarted outcomes are combined into the retained earlier slice ID, with their requirements and acceptance coverage preserved: WX.4 into WX.3; WX.7 and WX.8 into WX.6; WX.14 into WX.13; WX.21 into WX.20. The absorbed IDs are retired and will not be reused. Completed IDs and outcomes are unchanged.

**Checkpoint gates:** WX.1–WX.3 and WX.5–WX.6 = authority, baseline, canonical contracts, and unchanged legacy behavior; WX.9–WX.11 = common source contract plus simulated and recorded 44-day datasets; WX.12–WX.13 = real 44-day Open-Meteo ingestion with honest provenance; WX.15–WX.20 = persistence, location selection, and safe catalog activation; WX.22 = debug source selection; WX.23–WX.25 = integrated 44-day timeline; WX.26–WX.27 = production startup and release hygiene; WX.28 = final application integration and release-boundary acceptance. Slice-focused checks remain within their implementing slices. A separate broader test roadmap covering cross-app accessibility, UI performance, persistence, and other system-wide checks will be planned after the basic first-tier features work; it is not part of the current WX sequence. Passing an intermediate gate does **not** imply overall production acceptance.

### WX.1 — Adopt successor-product authority and integration boundaries — DONE

**Objective/scope:** Documentation-only owner decision that this repository is the successor OxygenWX app; amend `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `AGENTS.md`, `README.md`, and relevant roadmap language to authorize future live data, location, cache, 44-day chronology, and hot-swap without authorizing implementation yet. Record the future `scripts/dev.py` contract change for WX.12; do not change that script in this documentation-only slice. Reconcile stale prototype exclusions and deprecated `P.3`/`P.4` integration assumptions; leave completed slice/history claims intact. Decide precedence relative to `ATM.8` and preserve exactly one `NEXT`.
**Depends on:** None.
**Exit:** A signed-off authority/decision record names the successor app, 44-day date convention, simulation/live parity, separate official alerts, existing interaction invariants, roadmap precedence, and non-goals; conflicting statements are enumerated and reconciled; docs workflow/format validation and diff check are recorded; no app sources or active cycle were silently changed.
**Concept artwork:** Waived by the owner on 2026-10-26 because WX.1 is a documentation-only change. No artwork is required for this cycle; the waiver does not change the general concept-art gate for other unfinished slices.
**Evidence:** Cycle 043 (`043-wx1-successor-authority`): [decision and verification artifacts](../.codex/test-artifacts/043-wx1-successor-authority/README.md); [closeout](../.codex/history/043-wx1-successor-authority.md); [archived plan](../.codex/history/plans/043-wx1-successor-authority.md).

### WX.2 — Inventory and regression-baseline the existing catalog and timeline — DONE

**Objective/scope:** Read-only audit of actual debug catalog, three transitions, profiles, eight-day normal sample, `ForecastSource`, `ForecastProjection`, `ElasticTimeAxis`, `TimeLabScreen`, atmosphere mapping, and present tests/device evidence. Document the independently generated `DebugForecastProfiles.profileFacts()` versus `DebugForecastCatalog.scenarios` relationship; current synthetic warning and snow/unknown visual mappings; `LocalDateTime`, 24-hour day, reference reset, and test-scaffold assumptions. No behavior changes.
**Depends on:** WX.1.
**Exit:** A source-path/line-grounded inventory, migration compatibility table, recorded snapshots or reproducibility plan, currently verified/unverified baseline, and specific owners for every legacy invariant exist; discrepancies and preexisting failures are dispositioned rather than silently fixed.
**Concept artwork:** Product-owner approved annotated current-versus-target dataflow board at `.codex/concepts/WX.2-baseline-inventory.png` (approval and constraints recorded in cycle 044 evidence).
**Evidence:** Cycle 044 (`044-wx2-baseline-inventory`): `.codex/test-artifacts/044-wx2-baseline-inventory/README.md`, `inventory.md`, `baseline.md`, and post-closeout owner disposition `owner-disposition.md`; closeout `.codex/history/044-wx2-baseline-inventory.md`; archived plan `.codex/history/plans/044-wx2-baseline-inventory.md`; approved board `.codex/concepts/WX.2-baseline-inventory.png`.

### WX.3 — Define canonical catalog identity and weather facts — DONE

**Objective/scope:** Define the provider-neutral catalog envelope and canonical nullable weather facts together. Include stable local location identity, coordinates/timezone, `asOf: Instant`, absolute valid times, dataset identity, source/product provenance, canonical units, ranges, and a composable condition/phenomena vocabulary. Cover existing clear, partly cloudy, cloudy, rain, snow, storm; distinguish precipitation and thunder probabilities, rain, total precipitation, snowfall, snow depth, wind/gust/direction, temperature, pressure, humidity, cloud, visibility, and optional severe-weather inputs only where supported. Keep official alerts and legacy synthetic warning outside canonical facts. Keep existing fixture and presentation paths unchanged; no storage, transport, or renderer changes.
**Depends on:** WX.2.
**Exit:** Pure construction and data-contract checks cover identity, timezone, reference instant, consistent source metadata, valid/ranged finite measurements, missing versus zero, distinct snowfall/depth, separate thunder and rain semantics, severe conditions without alerts, unsupported phenomena left absent, provider-neutral identity, identical live/simulated/replay envelopes, and no giant Cartesian-product condition enum. No provider DTO or synthetic-only field leaks into the canonical envelope.
**Concept artwork:** Product-owner approved on 2026-10-27 as shown: catalog identity, weather-fact, and provenance contract board at `.codex/concepts/WX.3-canonical-catalog-contract.png` with editable `.svg`.
**Evidence:** Cycle 045 (`045-wx3-canonical-catalog-contract`): `.codex/test-artifacts/045-wx3-canonical-catalog-contract/README.md`; closeout `.codex/history/045-wx3-canonical-catalog-contract.md`; archived plan `.codex/history/plans/045-wx3-canonical-catalog-contract.md`; approved concept `.codex/concepts/WX.3-canonical-catalog-contract.png` and editable `.svg`. The full unit suite retains the documented unrelated time-label assertion failure; focused WX.3 tests pass.

### WX.5 — Define 44-day date windows, coverage, and daily aggregate rules — DONE

**Objective/scope:** Introduce pure calendar window/coverage helpers, `CatalogDay`/daily aggregate rules, duplicate/out-of-order normalization policy, and explicit COMPLETE/PARTIAL/MISSING coverage at day/field level. Separate provider-supplied daily values from derived aggregates; do not infer complete totals from incomplete hours. No provider calls or renderer changes.
**Depends on:** WX.3.
**Exit:** Controlled-clock/location tests cover `D-30..D-1` and `D..D+13`, 44 date identities, midnight rollover, spring/fall DST, 23/25-hour local days, sparse hours, endpoint validation, duplicate-time policy, missing days and valid daily provenance; no tests require 24 hours per calendar day.
**Concept artwork:** Owner-approved on 2026-10-27 as shown in `.codex/concepts/WX.5-window-completeness.png` and editable `.svg`; constraints are recorded in plan 047. This is design input, not implementation or acceptance evidence.
**Evidence:** Cycle 047 (`047-wx5-44-day-window-coverage`): `.codex/test-artifacts/047-wx5-44-day-window-coverage/README.md`; closeout `.codex/history/047-wx5-44-day-window-coverage.md`; archived plan `.codex/history/plans/047-wx5-44-day-window-coverage.md`; approved concept `.codex/concepts/WX.5-window-completeness.png` and editable `.svg`.

### WX.6 — Migrate existing forecast fixtures through canonical adapters — DONE

**Objective/scope:** Add pure canonical adapters for the existing immutable `DebugForecastCatalog`, its three transition sequences, all three generated debug profiles, and normal `SampleForecast`. Preserve the 12 scenario IDs and values, sequence step IDs/order/times, profile IDs/formulas and 7/11/14 loaded-day behavior, two-day buffers, reference times, and the eight-day normal fixture. Define scenario `validTime` versus sequence `sequenceTime`. Preserve existing forecast text, daily extrema, selected-time rules, atmospheric mappings, gesture ownership, app startup, and debug picker. Do not relocate debug fixtures into release sources, rewrite authored expectations, or make the app load a new source.
**Depends on:** WX.3 and WX.5.
**Exit:** Golden/snapshot comparisons show scenario facts and nulls, 27%/90% storm probabilities, transition identities/order, generated profile outputs/reference reset, synthetic warning preview, and normal sample presentation match the recorded baseline. Existing debug entry points remain usable, scenario tables remain absent from release, and the adapter does not introduce source DTOs into presentation. Document or guard intentional boundaries between catalog scenarios and independently generated `profileFacts`; build and contract checks pass.
**Concept artwork:** Owner-approved on 2026-10-27 as shown in `.codex/concepts/WX.6-fixture-adapters.png` with editable `.svg`; it is design input, not implementation or acceptance evidence.
**Evidence:** Cycle 049 (`049-wx6-fixture-adapters`): `.codex/test-artifacts/049-wx6-fixture-adapters/README.md`; closeout `.codex/history/049-wx6-fixture-adapters.md`; archived plan `.codex/history/plans/049-wx6-fixture-adapters.md`.

### WX.9 — Introduce one source and load-result contract — DONE

**Objective/scope:** Define `WeatherCatalogSource.load(request): CatalogLoadResult` (or equivalent) for real, synthetic, and recorded providers, with request location/as-of/dataset ID, injectable clock, cancellation, typed success/partial/no-data/invalid/transport failure, and metadata. Implement source registration/injection without an active-source UI or data persistence. A cached LIVE dataset keeps LIVE origin with separate cache state.
**Depends on:** WX.5 and WX.6.
**Exit:** Stubbed independent live/simulated/replay providers return the same canonical type and identical consumer-facing result semantics; failed/no-data results cannot overwrite a valid active dataset; no transport DTO crosses the source boundary.
**Concept artwork:** Owner-approved 2026-10-27 as shown in `.codex/concepts/WX.9-source-interface.png` with editable `.svg`; approval is recorded in `.codex/test-artifacts/050-wx9-source-contract/board-approval.md`. This is design input, not implementation or acceptance evidence.
**Evidence:** Cycle 050 (`050-wx9-source-contract`): `.codex/test-artifacts/050-wx9-source-contract/README.md`; closeout `.codex/history/050-wx9-source-contract.md`; archived plan `.codex/history/plans/050-wx9-source-contract.md`.

### WX.10 — Generate one deterministic 44-day DEMO timeline — DONE

**Owner direction (2026-10-27):** Use one linear synthetic DEMO sequence for troubleshooting, as resolved in the `/side` conversation. Do not add scenario selection or multiple alternate timelines. The approved board defines the 44-day arrangement and the event families. The owner subsequently approved the fixed sample/support fixture and a narrowly bounded `WeatherPhenomenon.HAZE` addition for this slice; haze must be explicit rather than inferred from visibility.
**Objective/scope:** Add one deterministic `SimulatedCatalogSource` dataset for a supplied `CatalogLocation` and `asOf`. Anchor the 44 local dates to `D = asOf.atZone(location.zoneId).toLocalDate()`: D−30…D−1 are historical dates and D…D+13 are forecast dates. Keep two explicit clear buffer days at each edge (D−30, D−29, D+12, D+13) and arrange the 40-day event core across D−28…D+11 as one fixed linear progression. Include the approved rain, snow, sleet, thunderstorm, heavy-rain/flash-flood-condition, clear/partly-cloudy, haze, fog, and high-wind examples; these are synthetic weather facts, never official alerts. Preserve explicit missingness and unsupported fields. Keep the legacy 7/11/14-day debug profile generators and outputs unchanged. Data-only: no UI, network, active-source wiring, or scenario picker.
**Depends on:** WX.6 and WX.9.
**Exit:** The same location/as-of request produces an identical canonical 44-date catalog with the same single event order and explicit clear buffers; no scenario or alternate-sequence input exists. Tests cover local-date anchoring, the 30/14 historical/forecast split, 40-day event core, both clear edges, DST/date policies, missing versus zero, synthetic provenance, weather-fact plausibility, and no alert semantics. Existing 7/11/14 debug profiles remain unchanged.
**Concept artwork:** Owner-approved 2026-10-27 as shown in `.codex/concepts/WX.10-simulator.png` with editable `.svg`; this is data-shape design input, not UI or implementation acceptance evidence.
**Evidence:** Cycle 051 (`051-wx10-simulated-catalog`): `.codex/test-artifacts/051-wx10-simulated-catalog/README.md` and focused test XML; `.codex/history/051-wx10-simulated-catalog.md`; archived plan `.codex/history/plans/051-wx10-simulated-catalog.md`; exact sequence `.codex/plans/051-wx10-sequence-fixture.md`.

### WX.10R — Expand the deterministic SIMULATED catalog to every real local hour — DONE (cycle 070)

**Owner direction (2026-10-29):** Extend the existing one-sequence, 44-date synthetic catalog to one canonical fact at every real local hour on each date. A date may contain 23, 24, or 25 hourly instants according to its timezone. Keep deterministic within-day event windows and preserve the existing weather-event families, source truth, and missing-versus-zero semantics. This is specific to the SIMULATED development product; the shared 44-date horizon still permits sparse provider/replay coverage.
**Objective/scope:** Update the deterministic SIMULATED source and any source-derived debug replay fixture that must remain equivalent, so every local date D−30…D+13 contains a condition fact at each valid top-of-hour instant. Define stable within-day event windows from the approved daily anchors; preserve existing event order and 9 AM/9 PM sample values, assign facts to local dates using their point time and accumulated support using its true interval, and keep unsupported fields absent. Version any cached/product identity needed to prevent the old 88-row dataset from being mistaken for hourly coverage. No provider mapping, official alerts, browser/layout redesign, or visual polish.
**Depends on:** WX.10 and WX.11.
**Exit:** Deterministic identical requests yield exactly the 44 local dates and every real top-of-hour instant in each date; Chicago fall-back includes both repeated local hours and spring-forward omits the nonexistent hour. Existing 9 AM/9 PM anchors and every approved weather condition/phenomenon remain present; event windows transition according to the approved concept; precipitation/snow accumulation intervals, daily support, provenance, nullable fields, explicit zeroes, and source identity remain valid. Focused data verification records counts/date coverage and compares all event families. The upgraded catalog is available through the installed debug SIMULATED route, and any derived fixed REPLAY acceptance fixture remains consistent.
**Concept artifact:** Owner approved 2026-10-29 at `.codex/concepts/WX.10R-simulated-hourly-windows.md`. The board defines hourly instant generation, 9 AM/9 PM event-window assignment, DST behavior, and field/support semantics. Approval and constraints are recorded in cycle 070's plan.
**Evidence:** At planning, assign a chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths. This owner-directed data slice precedes the remaining installed WX.25 acceptance.

**Completed evidence:** Cycle 070 (`070-wx10r-simulated-hourly-windows`): installed and read-only data audit `.codex/test-artifacts/070-wx10r-simulated-hourly-windows/`; closeout `.codex/history/070-wx10r-simulated-hourly-windows.md`; archived plan `.codex/history/plans/070-wx10r-simulated-hourly-windows.md`.

### WX.11 — Implement versioned recorded-catalog replay — DONE

**Objective/scope:** Define a stable serialized canonical catalog recording with schema version, location, source/product timestamps, nullable fields, and deterministic replay loader. Record an approved deterministic synthetic fixture without network calls. Provider-derived fixture selection and verification are deferred to WX.13, where provider product meaning can be established. No live transport, UI, or editing raw scenario tables.
**Depends on:** WX.9 and WX.10.
**Exit:** An approved synthetic catalog recording round-trips without losing units, timezone, source/product identity, severe-weather fields, declared support, or missingness; malformed/old schemas fail or migrate explicitly; replay produces the same canonical type as simulation. Provider-derived round-trip evidence remains a WX.13 follow-up and is not claimed by WX.11.
**Concept artwork:** Owner-approved 2026-10-27 as shown in `.codex/concepts/WX.11-record-replay.png`, with editable `.svg`; its section 04 contract and fixture choices remain for the bounded plan to resolve before finalization.
**Evidence:** Cycle 052 (`052-wx11-recorded-catalog-replay`): fixture and verification evidence `.codex/test-artifacts/052-wx11-recorded-catalog-replay/`; closeout `.codex/history/052-wx11-recorded-catalog-replay.md`; archived plan `.codex/history/plans/052-wx11-recorded-catalog-replay.md`; wire format `docs/RECORDED_CATALOG_V1.md`.

### WX.12 — Add the Open-Meteo transport and revise prototype isolation guard — DONE

**Objective/scope:** Add minimal network transport with injectable HTTP client, timeouts/cancellation, request configuration, and mocked transport fixtures. Update manifest permissions as required and change `scripts/dev.py contract` from prohibiting any network/location capability to enforcing approved provider/release boundaries; do not remove unrelated package/gesture/isolation assertions. Verify current Open-Meteo variables, rate/attribution/license requirements, and product meaning. No provider-to-catalog mapping or app wiring.
**Depends on:** WX.1 and WX.9.
**Exit:** Recorded request/failure tests cover HTTP errors, cancellation, timeout and transport parsing boundaries; contract script no longer blocks approved Internet permission but rejects unintended location/background/debug exposure; provider policy/attribution assumptions and documented endpoint capabilities have auditable references.
**Concept artwork:** Owner-approved transport/security boundary board at `.codex/concepts/WX.12-transport-guards.png` (2026-10-28; no additional constraints).
**Evidence:** Cycle 053 (`053-wx12-open-meteo-transport`): provider review, mocked test XML, manifest inspection, and verification record in `.codex/test-artifacts/053-wx12-open-meteo-transport/`; closeout `.codex/history/053-wx12-open-meteo-transport.md`; archived plan `.codex/history/plans/053-wx12-open-meteo-transport.md`.

### WX.13 — Build the Open-Meteo catalog source — DONE

**Objective/scope:** Implement `OpenMeteoCatalogSource` using the common source contract. Add provider DTO decoding, pure mapping, and catalog assembly for the agreed hourly/daily variables, canonical units, weather codes, nullable/unknown fields, timestamp validity, source/model provenance, and optional severe inputs. Request `past_days=30`, `forecast_days=14`, metric units, coordinates, `timeformat=unixtime`, and agreed hourly/daily fields. Convert returned instants through the location's IANA timezone and assemble target date identities with explicit partial coverage. Do not use a fixed request-time UTC offset to label dates across DST. Treat past Forecast API samples as **archived model/forecast data**, not station observations. Never infer independent thunder/hail/tornado values from WMO codes or generic precipitation. Support missing hours, short horizons, and failures without filling facts. No location UI or active-app wiring.
**Depends on:** WX.3, WX.5, WX.9, and WX.12.
**Exit:** Recorded complete, sparse, malformed, duplicate/out-of-order, unknown-code, and null-variable fixtures validate transport parsing, mapping, and assembly. An owner-approved sanitized provider-derived canonical recording round-trips through the WX.11 codec without losing units, timezone, source/product identity, severe fields, declared support, or missingness; its provenance, timestamp meaning, and gaps are documented. Metrics match canonical units; unsupported severe probabilities remain null; daily provider-supplied versus derived summaries and per-segment product metadata are accurate. Request fixtures verify `D-30..D+13` membership, timezone/DST conversion using Unix instants and IANA rules, attribution, product distinctions, partial/no-data/failure outcomes, and no unsupported hazard claims. A bounded optional live smoke, when approved, records actual fields and coverage instead of assuming complete 44-date coverage.
**Concept artwork:** Owner approved 2026-10-28 as shown in the revised provider fields, normalization, request, and 44-day assembly board at `.codex/concepts/WX.13-openmeteo-catalog-source.png` (editable SVG); no additional constraints stated.
**Evidence:** Cycle 054: `.codex/test-artifacts/054-wx13-open-meteo-catalog-source/` (provider capture, approved canonical v2 recording/hash, fixture manifest, scoped XML tests, and verification README); closeout `.codex/history/054-wx13-open-meteo-catalog-source.md`; archived plan `.codex/history/plans/054-wx13-open-meteo-catalog-source.md`.

### WX.13R — Extend the forecast request for a complete 44-date recording — DONE

**Objective/scope:** Extend the Open-Meteo request by one forecast day as a tail buffer, while retaining the canonical target window D−30 through D+13. Update the request contract and focused fixtures, capture a new public-reference response, and prepare a new canonical v2 candidate with coverage checked against the existing IANA-zone support grid. Preserve the owner-approved v2 artifact and hash from cycle 054 unchanged; the replacement candidate requires its own owner review. No UI or active-app wiring.
**Depends on:** WX.13.
**Exit:** The request includes one forecast day beyond the D+13 canonical boundary; focused request/mapper checks prove extra D+14 rows are excluded from the 44-date catalog; the new candidate has complete returned hourly point and accumulation support through the target boundary (or its precise remaining gaps are recorded without claiming a complete tail); v2 encoding/decoding/replay round-trips deterministically; provenance, raw response, query manifest, hashes, and coverage inventory are retained; owner approval is recorded for the exact new hash before closure. The cycle 054 v2 artifact remains byte-identical.
**Concept artwork:** Owner approved `.codex/concepts/WX.13R-buffered-capture.png` as shown on 2026-10-28; editable source is `.codex/concepts/WX.13R-buffered-capture.svg`. No additional constraints were supplied.
**Evidence:** Cycle 055 (`055-wx13r-buffered-capture`): `.codex/test-artifacts/055-wx13r-buffered-capture/` (approved candidate, raw response, manifest, complete coverage report, scoped test reports); closeout `.codex/history/055-wx13r-buffered-capture.md`; archived plan `.codex/history/plans/055-wx13r-buffered-capture.md`.

### WX.15 — Persist versioned per-location datasets and catalog history — DONE

**Objective/scope:** Implement durable, versioned storage for canonical datasets in AndroidX Room/SQLite, using one validated `RecordedCatalogCodec` JSON recording payload per dataset row. Index stable local location ID, storage namespace (`LIVE`/`SIMULATED`/`REPLAY`), dataset ID, and `asOf` for history lookup; keep the weather facts in the canonical versioned payload rather than duplicating them into SQL columns. Preserve current source metadata, separate storage namespaces from captured product provenance, and define retention and explicit database/payload migration behavior. No UI, provider refresh, or geocoding.
**Depends on:** WX.11 and WX.13.
**Exit:** Room persistence round-trips 44-day live/simulated/replay datasets, missing facts, per-record provenance, and DST timestamps; a failed transactional row replacement leaves the prior complete dataset readable; database and payload schema version failure/migration are explicit; removing one location/dataset leaves all others untouched; no simulation overwrites live storage. Retention is explicit and does not silently evict datasets; WX.16 adds a two-snapshot LIVE cap for caller-driven refresh, while recurring/background scheduling remains out of scope.
**Concept artwork:** Owner-approved storage schema board at `.codex/concepts/WX.15-storage.png` (editable `.svg`), 2026-10-28; no additional constraints.
**Evidence:** Cycle 056 (`056-wx15-room-catalog-storage`): `.codex/test-artifacts/056-wx15-room-catalog-storage/` (build, contract, focused host and Pixel instrumentation logs/XML); closeout `.codex/history/056-wx15-room-catalog-storage.md`; archived plan `.codex/history/plans/056-wx15-room-catalog-storage.md`; exported schema `app/schemas/com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase/1.json`.

### WX.16 — Implement cache freshness, refresh, and partial recovery — DONE

**Objective/scope:** Add whole-catalog repository refresh/state policy for live data, preserving last useful cached data on network failure, distinguishing fresh/cached/stale/failed/partial outcomes and rolling the `asOf` local-date window explicitly. The current source and stored recording are indivisible; segment-selective history/forecast schedules require a future bounded source and storage contract. Do not present a failed refresh as new live weather. No UI yet.
**Depends on:** WX.13 and WX.15.
**Exit:** Controlled-clock repository checks prove fresh whole-catalog fetch, cache-only startup, stale display, forced refresh, failed refresh with/without cache, corrupt cache, cache-write failure with still-usable live data, midnight rollover, and partial provider windows, preserving truthful provenance and location identity. One successful whole-catalog retrieval records one retrieval time; separate product clocks and schedules are outside this slice.
**Concept artwork:** Owner approved 2026-10-28 at `.codex/concepts/WX.16-cache-refresh.png` (editable `.svg`). The board keeps freshness, coverage, and cache-write status distinct; preserves a valid prior-window cache as stale across local midnight without changing its `asOf`; keeps valid live data usable when cache persistence fails; and requires bounded retention before recurring refresh. Define whole-catalog freshness and refresh thresholds in the bounded plan.
**Evidence:** Cycle 057 (`057-wx16-cache-refresh-recovery`): `.codex/test-artifacts/057-wx16-cache-refresh-recovery/` (controlled-clock host XML, Pixel 3 XL Room migration/retention XML and per-test logs, baseline, and verification README); closeout `.codex/history/057-wx16-cache-refresh-recovery.md`; archived plan `.codex/history/plans/057-wx16-cache-refresh-recovery.md`.

### WX.17 — Add manual geocoding/location search provider contract — DONE

**Objective/scope:** Define provider-neutral place-search results and implement an initial Open-Meteo geocoding adapter with a pure conversion seam for an accepting layer's stable local ID, returned coordinates, correct IANA timezone, ambiguity/error/no-result behavior, and documented attribution. No Android device-location permission or saved-location UI.
**Depends on:** WX.3 and WX.12.
**Exit:** Recorded search fixtures cover duplicate place names, differing timezones, coordinate precision, country context, rejected rows, empty/error results, cancellation, and provider metadata; chosen outputs create a `CatalogLocation` with caller-supplied local IDs independent of provider IDs.
**Concept artwork:** Owner approved the geocoding-to-location identity board as shown on 2026-10-28 at `.codex/concepts/WX.17-location-search-source.png` (editable `.svg`). It defines the provider-neutral search, ambiguity, local identity, and failure boundaries; no additional constraints were stated. This is design input, not implementation evidence.
**Evidence:** Cycle 058 (`058-wx17-location-search-source`): `.codex/test-artifacts/058-wx17-location-search-source/` (provider documentation recheck, workflow, contract, build, and focused unit-test logs); closeout `.codex/history/058-wx17-location-search-source.md`; archived plan `.codex/history/plans/058-wx17-location-search-source.md`.

### WX.18 — Persist selected and saved locations as application state — DONE

**Objective/scope:** Add stable local saved-location records, add/remove/select persistence, selected-location restoration, and explicit location-to-catalog association independent of data origin. Implement domain/controller/storage contracts only; no search/switching UI, no foreground GPS permission.
**Depends on:** WX.15 and WX.17.
**Exit:** State/storage checks prove saved locations survive relaunch, distinct coordinate/timezone locations remain distinct, removing one location handles its catalog retention policy explicitly, and restored selection cannot accidentally point to a different provider place with the same display name.
**Concept artwork:** Owner approved 2026-10-28 at `.codex/concepts/WX.18-saved-locations.png` (editable `.svg`). Grounded in WX.15 Room identity/migration boundaries, WX.13R's fixed catalog identity/window, and WX.17's caller-supplied local-ID handoff. The bounded plan must resolve selected-location removal/fallback, catalog-retention, duplicate-ID, and invalid-restore policies. This is design input, not implementation or acceptance evidence.
**Evidence:** Cycle 059 (`059-wx18-saved-locations`): evidence `.codex/test-artifacts/059-wx18-saved-locations/README.md`; closeout `.codex/history/059-wx18-saved-locations.md`; archived plan `.codex/history/plans/059-wx18-saved-locations.md`.

### WX.19 — Build accessible manual search and saved-location selector — DONE

**Objective/scope:** Add a compact owner-reviewed location search/add/remove/select interface that coexists with, but does not replace, the immersive timeline. Handle progress, ambiguous matches, no results, request failure, current selection, and saved locations. This slice does not change the weather renderer or implement data-source hot-swap UI.
**Depends on:** WX.17 and WX.18.
**Exit:** Focused flow checks show searching, saving, selecting, deleting, and restoring locations without location permission; control labels and state semantics identify the selector's own actions and current selection; the selected location identity is unambiguous. System-wide accessibility and environment coverage is outside this slice and will be considered in the later test roadmap.
**Concept artwork:** Owner approved the “selector open” look as shown on 2026-10-28 at `.codex/concepts/WX.19-location-selector.png` (editable `.svg`), grounded in the installed before capture `.codex/concepts/WX.19-location-selector-before-installed.png`. The owner has separate plans for main-screen access; that route remains to be designed and approved before its UI implementation. The proposed composite is not an installed after capture.
**Evidence:** Cycle 060 (`060-wx19-location-selector`): installed captures and interaction/accessibility hierarchy evidence `.codex/test-artifacts/060-wx19-location-selector/`; evidence index `.codex/test-artifacts/060-wx19-location-selector/README.md`; closeout `.codex/history/060-wx19-location-selector.md`; archived plan `.codex/history/plans/060-wx19-location-selector.md`. Main-screen access route remains owner-planned. TalkBack audio/traversal, RTL, landscape, and performance profiling were not run; these remain candidates for the later test roadmap.

### WX.20 — Load and atomically activate location-scoped catalogs — DONE

**Objective/scope:** Wire selected-location identity to source loading and implement location-scoped `ActiveCatalogSelection` with atomic validated dataset activation. Add load state, cancellation/sequence tokens, per-location and per-origin cache namespaces, and location-aware refresh requests. Reject late results from previous locations or older same-location requests. Switching between live, simulated, and replay data replaces the whole catalog, preserves selected `Instant` when available or clamps to the nearest valid sample, and retains the previous valid state on failure. Keep storage/retrieval and renderer independent; no debug selector UI.
**Depends on:** WX.10, WX.11, WX.15, WX.16, and WX.18.
**Exit:** Controlled asynchronous checks cover A→B→A, repeated same-location refresh, failures, cache restore, and app recreation; only the newest matching location/request can publish active weather. LIVE↔SIMULATED↔REPLAY switches contain no mixed fields, metadata, or locations; failed replacement is safe; live cache survives simulation; selected-instant retention/clamping and view-generation markers are deterministic; non-selected datasets remain unchanged; switching requires no app restart.
**Concept artwork:** Owner-approved location request, race handling, and atomic source-activation board on 2026-10-28 at `.codex/concepts/WX.20-catalog-activation.png` (editable `.svg`); owner-resolved replay storage contract is recorded in plan 061.
**Evidence:** Cycle 061 (`061-wx20-catalog-activation`): `.codex/test-artifacts/061-wx20-catalog-activation/`; closeout `.codex/history/061-wx20-catalog-activation.md`; plan `.codex/history/plans/061-wx20-catalog-activation.md`.

### WX.22 — Expose debug-only dataset selection and replay controls — DONE (cycle 062)

**Objective/scope:** Add an isolated debug activity/picker to choose active location dataset and existing/new scenarios, replay IDs, and live mode; build on the existing working debug profile picker without adding permanent controls to the immersive timeline. Keep request decoding and activation wiring in a debug-only variant delegate with no release provider. Mark simulated provenance, show explicit load failures, and verify debug-only gating. For installed LIVE acceptance, move bounded response-body consumption and JSON parsing in `OpenMeteoTransport` to `Dispatchers.IO` after the observed `NetworkOnMainThreadException`; preserve endpoint/request parameters and provider semantics. No official-alert controls.
**Depends on:** WX.20.
**Exit:** Installed debug build selects all three origins and existing profiles, changes application data without process restart, shows source labels, recovers on rejected data, and passes release manifest/DEX/source-set isolation checks; timeline controls and legacy profile entry remain usable.
**Concept artwork:** Owner approved the debug selector storyboard on 2026-10-28 at `.codex/concepts/WX.22-source-picker.png` (editable `.svg`). Approval covers the isolated debug activity, location and SIMULATED/REPLAY/LIVE selection, source-specific profile/recording choices, truthful provenance and failure states, whole-dataset replacement, and return to the existing timeline without process restart. Constraints: no permanent timeline controls, official-alert controls, production-startup/location-selector changes, or debug entry point in release builds.
**Evidence:** Cycle 062 (`062-wx22-debug-source-picker`): `.codex/test-artifacts/062-wx22-debug-source-picker/`; closeout `.codex/history/062-wx22-debug-source-picker.md`; plan `.codex/history/plans/062-wx22-debug-source-picker.md`.

### WX.23 — Implement time-indexed canonical presentation without 24-hour assumptions — DONE (cycle 063)

**Objective/scope:** Extend the presentation boundary to project sparse canonical `Instant`-keyed hours and local `CatalogDay`s, with nullable facts, source/product labels, daily extrema, exact nearest-hour meteorological samples, and interpolated *decorative scene only*. Retain the old `ForecastSource` adapter for the working fixtures until their migration completes; do not feed 44-day/DST data into the old full-24-hour validator. No touch or layout change.
**Depends on:** WX.5, WX.6, WX.13, and WX.20.
**Exit:** Pure projection tests cover 23/25-hour and missing-hour dates, unknown measurements, historical versus forecast semantics, exact sample selection, distinct snow visual identity, provenance, stable unit conversion, and unchanged existing fixture presentations; no precipitation unknown is silently displayed as measured zero.
**Concept artwork:** Owner approved 2026-10-29 at `.codex/concepts/WX.23-time-projection.png` (editable source: `.codex/concepts/WX.23-time-projection.svg`). Constraints are recorded in plan 063: preserve selected and sample instants separately; select nearest samples within the local date with earlier-instant tie-breaking and no cross-date fallback; keep missing facts unavailable; derive missing daily temperature extrema only with complete coverage; keep same-day scene interpolation decorative and separate from facts; retain the legacy fixture path and make no UI/timeline changes.
**Evidence:** Cycle 063 (`063-wx23-time-indexed-presentation`): `.codex/test-artifacts/063-wx23-time-indexed-presentation/README.md`; closeout `.codex/history/063-wx23-time-indexed-presentation.md`; archived plan `.codex/history/plans/063-wx23-time-indexed-presentation.md`.

### WX.24 — Migrate the pure ElasticTimeAxis to absolute instants — DONE (cycle 065)

**Objective/scope:** Refactor the pure time-axis/time-selection logic from local-day `selectedHour` assumptions to absolute time with local-calendar day navigation, duration-correct hour spacing, selected-instant persistence, and variable day lengths. Define repeated/nonexistent local-hour selection, daily overview anchors, source-swap preservation/clamping, and real-now versus debug-reference reset. Maintain the existing gesture owners; no visual redesign.
**Depends on:** WX.23.
**Exit:** Pure tests prove monotonic/clamped coordinate mapping, no selected-time jump on zoom, spring/fall DST, day shifts preserving local hour where possible, sparse interval navigation, tap/hold/drag/release semantics, and correct reset/clamp behavior. Previously verified fixture interactions continue to pass through compatibility routes.
**Concept artwork:** Owner approved 2026-10-29 at `.codex/concepts/WX.24-absolute-axis.png` (editable source: `.codex/concepts/WX.24-absolute-axis.svg`). Constraints are recorded in plan 064: keep selection as an exact `Instant`; map axis positions by elapsed duration; preserve local wall time on calendar-day shifts; distinguish repeated hours by offset and retain the prior offset when possible, otherwise choose earlier; resolve missing wall times to the first valid time; use local noon as the daily overview anchor; preserve in-range selection on source swap or clamp to the nearest endpoint (tie earlier); reset LIVE to explicit real-now and DEBUG to the scenario reference, clamped to range; preserve gesture ownership and make no visual/UI integration changes.
**Result:** The cycle 064 verification mismatch was resolved by aligning the stale legacy test expectation to the existing projection output; no production behavior changed. Cycle 064 remains preserved as an incomplete attempt.
**Evidence:** Cycle 065 (`065-wx24-legacy-label-test-alignment`): `.codex/test-artifacts/065-wx24-legacy-label-test-alignment/README.md`; closeout `.codex/history/065-wx24-legacy-label-test-alignment.md`; archived plan `.codex/history/plans/065-wx24-legacy-label-test-alignment.md`. Prior attempt: `.codex/test-artifacts/064-wx24-absolute-axis/README.md`; `.codex/history/064-wx24-absolute-axis.md`.

### WX.25 — Bind the immersive timeline and forecast browser to 44 days — DONE (cycle 071)

**Objective/scope:** Connect `TimeLabScreen`, its visual tape, accessible forecast browser, and atmospheric projection to the active canonical catalog/time axis for 30 historical plus 14 forecast dates. Retain the current full-screen scene and gestures; display historical/forecast meaning, provenance, freshness, current missing data and selected time without source-based presentation branches. This slice integrates supported visual facts; it does **not** duplicate existing `ATM.8`–`ATM.10` scene-polish outcomes or implement official alerts.
**Depends on:** WX.22, WX.23, and WX.24.
**Exit:** Focused installed Pixel 3 XL checks scrub across history/today/forecast boundaries and dataset switches; exact timestamps/facts/source labels agree with the selected catalog; the forecast browser exposes usable day/hour rows to accessibility services; day/night/snow/storm mapping does not fabricate unsupported facts; responsiveness, gesture arbitration, and readability receive evidence-backed dispositions for the changed timeline behavior. System-wide font-scale, orientation, RTL, TalkBack, and reduced-motion testing is outside this slice and deferred to the later test roadmap.
**Concept artwork:** Owner approved 2026-10-29 at `.codex/concepts/WX.25-timeline-44-days.png` (editable source `.codex/concepts/WX.25-timeline-44-days.svg`), based on the unaltered connected Pixel 3 XL capture `.codex/concepts/WX.25-pixel-3-xl-current-screen.png`. Approval covers retaining the installed scene/information hierarchy while binding the 44-date catalog, exact selected Instant and source-sample facts, truthful history/forecast provenance, and the accessible browser to the same selection. Startup/location flow, official alerts, and ATM.8–ATM.10 scene polish remain out of scope. This board is design input, not installed acceptance evidence.
**Evidence:** Cycle 066 (`066-wx25-timeline-catalog-binding`) is an incomplete integration attempt: `.codex/test-artifacts/066-wx25-timeline-catalog-binding/README.md`, closeout `.codex/history/066-wx25-timeline-catalog-binding.md`, and archived plan `.codex/history/plans/066-wx25-timeline-catalog-binding.md`. Exact-selection and installed value/boundary observations are recorded there. Cycle 067 (`067-wx25-indicators-acceptance`) restored the approved separate precipitation and wind indicators, aligned one stale test expectation with WX.24, passed focused tests, and captured installed normal/missing/large-font and selected gesture states: `.codex/test-artifacts/067-wx25-indicators-acceptance/README.md`; closeout `.codex/history/067-wx25-indicators-acceptance.md`; archived plan `.codex/history/plans/067-wx25-indicators-acceptance.md`. Cycle 068 and 069 remain incomplete; their installed browser/timeline observations and exact gaps are preserved in their evidence/closeout. Cycle 071 completes the expanded SIMULATED v3 installed acceptance: `.codex/test-artifacts/071-wx25-hourly-timeline-acceptance/README.md`; closeout `.codex/history/071-wx25-hourly-timeline-acceptance.md`; archived plan `.codex/history/plans/071-wx25-hourly-timeline-acceptance.md`.

### WX.29 — Run cross-app experience and accessibility suite — CANCELLED

**Objective/scope:** Cancel the proposed standalone cross-app accessibility and experience test suite. Keep checks focused on the behavior and acceptance criteria of each implementation slice.
**Depends on:** None; this cancelled slice is not a dependency or required gate for remaining work.
**Exit:** Owner cancellation and downstream roadmap updates are recorded. No implementation, test execution, or coverage matrix is required for WX.29.
**Concept artwork:** Not applicable; cancelled before planning.
**Evidence:** Owner decision recorded here on 2026-10-29. The active WX.25 plan and remaining WX roadmap references reflect the revised test scope.
**Owner decision:** After the basic first-tier features work, plan a separate test roadmap for TalkBack, font scale, UI performance, data persistence, and other system-wide concerns. Until then, validation remains slice-focused.

### WX.26 — Compose production startup and selected-location experience — DONE (cycle 072)

**Objective/scope:** Replace the normal-app unconditional `SampleForecast` startup with a location-aware controller: restore selected saved location and valid cache, initiate optional manual location selection when none exists, allow explicit foreground refresh, and show loading/live/cached/stale/failed provenance. Keep `SampleForecast` accessible only as a clearly labeled development fallback, not attributed to searched locations. No background location tracking or real-alert system. Keep verification focused on these startup and source-state behaviors; system-wide accessibility and environment coverage belongs to the later test roadmap.
**Depends on:** WX.19, WX.20, and WX.25.
**Exit:** Focused flow checks cover first launch, saved-location relaunch, offline launch, location switch, source refresh, and failed live fetch with/without cache. Each state shows truthful labels/data; no synthetic data masquerades as real local weather and the immersive timeline remains usable.
**Slice board:** Owner-approved 2026-10-30 combined startup/cache/location behavior and UI artwork storyboard: `.codex/concepts/WX.26-startup-experience.svg` (rendered review image: `.codex/concepts/WX.26-startup-experience.png`). The approved board includes the compact timeline-header location control that opens the existing selector. It covers first launch, saved-location restore, fresh/cached/stale/failed states, foreground refresh, and location switching. Constraints: no device-location permission or background tracking; `SampleForecast` remains a clearly labeled development fallback; show only a matching location cache and never relabel another location's weather; do not show forecast values when loading fails without a cache. This board guides WX.26 scope and acceptance; it is not installed acceptance evidence. Approval, constraints, and the board's relationship to scope and exit are recorded in plan 072.
**Evidence:** On planning, allocate chronological cycle paths and installed state/evidence captures. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.
**Closeout evidence:** `.codex/test-artifacts/072-wx26-production-startup/README.md` records first launch, saved/offline restore, cached and uncached location switches, refresh success/failure, and retry recovery on Pixel 3 XL. Automated tests and selector-dismissal/stale-callback runtime checks were not performed; see cycle history for exact limits. These omissions do not alter the focused roadmap exit flows.

### WX.27 — Adopt OxygenWX product labels and audit release-data boundaries — DEFERRED

**Objective/scope:** Replace experimental user-facing Time Lab branding with owner-approved OxygenWX naming, align permissions, source attribution/modified-data notices, privacy and debug/release packaging with actual implemented capabilities, and make an explicit Android application-ID/signing/migration decision **without silently changing identity**. Do not copy the deprecated app or claim official alerts. Coordinate rather than duplicate deferred `P.4` slices.
**Depends on:** WX.26 and the owner-approved WX.1 product decision.
**Exit:** Release-installed app presents approved OxygenWX labels and accurate provider/freshness context; manifests, permissions, source/DEX scan, security/attribution and legacy-app coexistence or replacement decision are recorded; synthetic data and debug picker are absent from release; no unsupported weather warning claims or misleading DEMO DATA labels exist.
**Re-entry condition:** The product is finished and polished and release-readiness work is appropriate; explicitly restore this slice to the ordered execution path before planning or activation.
**Concept artwork:** Owner approved 2026-10-30 at `.codex/concepts/WX.27-release-identity.png` (editable source: `.codex/concepts/WX.27-release-identity.svg`). Approval covers the proposed OxygenWX identity placement, live/cached/unavailable source and freshness states, attribution/privacy/permission review, debug-versus-release boundary, and explicit app-ID/signing decision flow. It does not pre-decide final provider/legal copy or the coexist-versus-replace outcome; record those within WX.27. No application-ID or signing migration is authorized by this storyboard approval.
**Evidence:** On planning, allocate chronological cycle paths and release manifest/APK/installed evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.28 — Run final application-wide integration and release acceptance — DEFERRED

**Objective/scope:** Execute final bounded integration and release-boundary acceptance for the implemented catalog and app experience; publish a coverage/limitations matrix without hiding fixes in the verification cycle. Exercise real/recorded/simulated sources, 30+14 date coverage, archived-past labeling, DST, nullable severe fields, source hot-swap and races, multi-location persistence, offline/lifecycle behavior, and debug/release isolation. Keep checks focused on integration and release boundaries; system-wide accessibility, UI performance, and other cross-app suites belong to a separate test roadmap after the basic first-tier features work. Confirm registered devices during planning; Pixel 3 XL remains the reference. Handle defects as separate corrective slices.
**Depends on:** WX.10, WX.11, WX.13, WX.16, WX.19, WX.22, WX.25, WX.26, and WX.27.
**Re-entry condition:** WX.27 is complete and the product is finished and polished enough for application-wide integration and release acceptance; explicitly restore this slice to the ordered execution path before planning or activation.
**Exit:** An owner-reviewable report records each scoped integration/release scenario as **PASS/FAIL/NOT RUN**, with exact commands/builds/device details, installed captures where needed, source/units/provenance agreement, target-versus-actual date coverage, remaining blockers, and release risks. Mark DONE only when the recorded release acceptance criteria pass; fixes require separate slices.
**Concept artwork:** Pending owner-approved acceptance coverage and evidence storyboard at `.codex/concepts/WX.28-acceptance-matrix.png`.
**Evidence:** On planning, allocate chronological cycle paths and integrated verification matrix/captures. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.30 — Gate enrolled catalog quality and recover failed LIVE refreshes — CANCELLED

**Objective/scope:** This proposed candidate-quality and LIVE-refresh recovery work was cancelled before implementation because no defect in the app's actual source path had been demonstrated.
**Depends on:** None; cancelled.
**Exit:** Owner cancellation recorded. No implementation or acceptance is claimed.
**Slice board(s):** Removed by owner direction; do not reuse. A future need requires fresh boards through the normal workflow.
**Evidence:** Cancellation decision recorded here on 2026-11-03. No implementation evidence was produced; cycle 094 draft/evidence and its planning boards were removed.

### WX.31 — Integrate one independent LIVE fallback provider — DEFERRED

**Objective/scope:** Select and integrate one genuinely independent LIVE weather provider through the canonical catalog/source contract, then define bounded automatic failover from the primary LIVE source on eligible fetch failure. Preserve provider identity, location and dataset matching, units, timezone, support/missingness, attribution, and atomic whole-catalog activation. Keep each provider's facts and caches separate; never field-merge provider data or relabel SIMULATED/REPLAY as LIVE. Select the actual provider and failover trigger/order in this slice's approved board and bounded plan.
**Depends on:** WX.13 and WX.20.
**Re-entry condition:** An independent-provider/failover need is established and the owner explicitly returns this outcome to the ordered execution path through the normal workflow.
**Exit:** A provider-derived canonical catalog from the selected independent source retains truthful provider/product provenance, supported values, source bounds, date/timezone semantics, and missingness. Focused primary-success, primary-failure/fallback-success, both-fail-with-matching-cache, and both-fail-without-cache flows show bounded attempts, atomic activation, accurate source/freshness disclosure, stable selected time, and no mixed-source facts or synthetic masquerade. The fallback is shown to be independent rather than a second route to the same provider or cached data. Broader release/system acceptance remains in its dedicated workstream.
**Slice board(s):** Objective-specific source/failover sequence board pending product-owner approval at `.codex/concepts/WX.31-independent-live-failover.md`; UI artwork board for affected source/freshness states pending at `.codex/concepts/WX.31-source-failover-states.png` (editable source to be prepared when planned). Record selected provider, trigger/order, constraints, and both approvals in its later cycle plan before finalization or activation.
**Evidence:** Assign the next chronological cycle ID when this slice is planned. Allocate provider mapping, failure/fallback observations, and affected installed source/freshness captures under `.codex/test-artifacts/<cycle-id>/`, with plan `.codex/plans/<cycle-id>.md`, closeout `.codex/history/<cycle-id>.md`, and archived plan `.codex/history/plans/<cycle-id>.md`. These are targets, not existing evidence.

**Definition of this workstream's completion:** The successor OxygenWX app can search/save/select multiple locations; retrieve and persist a per-location 44-local-date **target** catalog with truthful complete/partial/missing coverage; display 30 past dates (archived model forecast initially, not falsely labeled observation) and 14 forecast dates on its immersive timeline; generate fully canonical 44-day simulated scenarios from the preserved working debug system; replay recorded datasets; atomically hot-swap all three sources in debug builds; retain accurate weather/severe-field/provenance semantics; support offline restoration and safe concurrent requests; preserve timeline/atmosphere/accessibility quality; and exclude developer simulation tools from the production build. Official weather alerts, GPS/background tracking, and a dedicated station-observation/reanalysis source are separate future decisions, **not silently included here**.

**Primary source references for planning (revalidate during source implementation):** `docs/ROADMAP_SPECIFICATION.md`, `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, `.codex/current.md`, current `app/src/debug/.../DebugForecastCatalog.kt` and `DebugForecastProfiles.kt`, current `app/src/main/.../presentation/ForecastSource.kt` / `ForecastProjection.kt` / `ElasticTimeAxis.kt`, and `scripts/dev.py`. Provider documents: https://open-meteo.com/en/docs , https://open-meteo.com/en/docs/historical-weather-api , and https://open-meteo.com/en/docs/historical-forecast-api .

## P.1 — Standalone elastic time tape

### P.1.1 — Implement the isolated timeline prototype — DONE

**Objective/scope:** Establish the standalone Android Compose scaffold, deterministic canonical-unit forecast fixture, typed presentation, pure time projection, atmospheric scene, and core time-tape interactions without changing oxygenWX.
**Depends on:** None.
**Exit:** The isolated app and core timeline implementation exist, architecture/isolation checks are recorded, and remaining installed acceptance is tracked separately.
**Evidence:** `.codex/history/001-elastic-timeline-prototype.md`.

### P.1.2 — Install and record the device baseline — DONE

**Objective/scope:** Build, install, launch, and capture the existing standalone app on the Pixel 3 XL; record representative timeline states without changing application source.
**Depends on:** P.1.1.
**Exit:** Installed foreground app is confirmed and actual device screenshots plus reproduction/device details are recorded.
**Evidence:** `.codex/history/002-p1-pixel-install.md`; `.codex/history/007-visual-baseline.md`; `.codex/test-artifacts/002-p1-pixel-install/`; `.codex/test-artifacts/007-visual-baseline/`.

### P.1.3 — Run Android unit tests and lint — DONE

**Objective/scope:** Run the app's debug unit-test and lint tasks on an Android host; diagnose only failures in those tasks.
**Depends on:** P.1.1.
**Exit:** `:app:testDebugUnitTest` and `:app:lintDebug` both pass, or a reproducible blocker with command output and affected scope is recorded; do not mark DONE on an unexplained failure.
**Evidence:** `.codex/history/010-android-host-checks.md`; `.codex/test-artifacts/010-android-host-checks/`.

## P.2 — Owner review, visual polish, and installed acceptance

### P.2.1 — Uniform hourly scale and selected-time label — DONE

**Objective/scope:** Replace localized focus-day expansion with a uniform hourly scale and magnify the selected day/hour label while keeping it centered at the playhead.
**Depends on:** P.1.1.
**Exit:** Daily/hourly labels and ticks show the uniform scale and selected-time cue in installed captures; selected time remains authoritative through scale animation.
**Evidence:** `.codex/history/003-time-label-magnification.md`; `.codex/test-artifacts/003-time-label-magnification/`.

### P.2.2 — Subtle timeline playhead — DONE

**Objective/scope:** Keep the selected position clear with a small axis marker while making the selected day/hour label the primary cue.
**Depends on:** P.2.1.
**Exit:** Installed capture shows the axis marker at the selected position without a full-height playhead obscuring the scene or labels.
**Evidence:** `.codex/history/004-subtle-playhead.md`; `.codex/test-artifacts/004-subtle-playhead/pixel-3xl.png`.

### P.2.3 — Main-display day navigation and realignment — DONE

**Objective/scope:** Shift selected time by 24 hours on a main-display horizontal swipe while preserving hour-of-day, and realign by double-tap within fixture bounds.
**Depends on:** P.2.1.
**Exit:** On-device left/right swipes and double-tap produce the specified selected-time changes, with actual outcome recorded.
**Evidence:** `.codex/history/005-main-display-navigation.md`; `.codex/test-artifacts/005-main-display-navigation/`.

### P.2.4 — Remove visible hints and expose the scale action — DONE

**Objective/scope:** Remove prototype instructional/mode-control rows and the NOW button; retain direct gestures, double-tap realignment, and the timeline's screen-reader scale action.
**Depends on:** P.2.3.
**Exit:** Installed UI has no removed visible controls, and source/semantics expose the daily/hourly custom accessibility action.
**Evidence:** `.codex/history/006-remove-timeline-hints.md`; `.codex/test-artifacts/006-remove-timeline-hints/pixel-3xl.png`.

### P.2.5 — Verify timeline gesture arbitration and time stability — DONE

**Objective/scope:** On the Pixel 3 XL, review immediate drag versus stationary hold, hourly scrubbing, release/collapse, and selected-time stability; assess responsiveness, gesture predictability, and readability.
**Depends on:** P.1.2, P.2.1, P.2.4, and P.2.5a.
**Exit:** Each gesture is exercised and its observed result recorded with screenshots; selected timestamp continuity and all three cross-cutting visual-quality checks receive an evidence-backed pass/fail disposition.
**Evidence:** `.codex/history/010-gesture-acceptance.md`; `.codex/test-artifacts/010-gesture-acceptance/`; `.codex/test-artifacts/011-blocker-investigation/README.md`; `.codex/test-artifacts/012-gesture-acceptance-recheck/README.md`; `.codex/history/012-gesture-acceptance-recheck.md`.

### P.2.5a — Align forecast time label with selected hourly tick — DONE

**Objective/scope:** Make the forecast summary's displayed hour use the same nearest-hour convention as the hourly tape's selected tick, while preserving fractional selection and interpolated forecast values during a drag.
**Depends on:** P.2.1.
**Exit:** On the installed Pixel 3 XL, fractional-hour positions no longer show different hours in the forecast summary and selected tape label; after release/collapse and stationary re-entry, the same rounded selected hour remains visible.
**Evidence:** `.codex/history/011-hour-label-alignment.md`; `.codex/test-artifacts/011-hour-label-alignment/`.

### P.2.6 — Review readability at large text and constrained layouts — DONE

**Objective/scope:** Review representative large system font, landscape/small-screen layout, and bright/dark sky contrast on the installed app.
**Depends on:** P.1.2.
**Exit:** Actual screenshots for each condition are reviewed; essential forecast values and timeline labels are readable and unclipped, with findings and any confirmed defect ownership recorded.
**Evidence:** `.codex/history/013-responsive-readability.md`; `.codex/history/014-responsive-readability-recovery.md`; `.codex/history/014-landscape-high-low.md`; `.codex/history/015-responsive-readability-review.md`; `.codex/test-artifacts/015-responsive-readability-review/`.

### P.2.12 — Restore Android build host and complete P.2.6 evidence — DONE

**Objective/scope:** Provision official Android SDK command-line tools and required SDK packages on the current host, build/install the current prototype on the Pixel 3 XL, and complete the missing exact-time and continuous-contact gesture evidence for P.2.6.
**Depends on:** None.
**Exit:** A fresh build succeeds with supported tooling; required installed captures show the exact bright/night fixture times; immediate drag and continuous hold-then-drag outcomes, selected-time continuity, and all P.2.6 condition dispositions are recorded.
**Evidence:** `.codex/history/014-responsive-readability-recovery.md`; `.codex/test-artifacts/014-responsive-readability-recovery/`.

### P.2.11 — Preserve high/low values in landscape at large font — DONE

**Objective/scope:** Keep the forecast high/low values visible as text in the compact landscape layout at 1.3 system font scale; they are absent from the installed capture.
**Depends on:** None.
**Exit:** Actual installed Pixel 3 XL captures show both high/low values as readable text alongside the date, heading, other essential forecast values, DEMO DATA label, and timeline labels at compact landscape and 1.3 font scale.
**Evidence:** `.codex/history/013-responsive-readability.md`; `.codex/test-artifacts/014-landscape-high-low/README.md`; `.codex/history/014-landscape-high-low.md`.

### P.2.7 — Accept the TalkBack scale action — DONE

**Objective/scope:** Manually traverse the timeline with TalkBack and invoke its daily/hourly custom action; verify understandable semantics and selected-time continuity.
**Depends on:** P.2.4.
**Exit:** TalkBack reaches the timeline, announces useful control/state semantics, invokes the daily/hourly scale action in both directions, and records the spoken label, visible scale, and selected-time continuity after each action.
**Evidence:** `.codex/test-artifacts/016-talkback-timeline/README.md`; `.codex/history/016-talkback-timeline.md`; `.codex/test-artifacts/017-talkback-timeline-recheck/README.md`; `.codex/history/017-talkback-timeline-recheck.md`; `.codex/test-artifacts/019-talkback-scale-action/README.md`; `.codex/history/019-talkback-scale-action.md`.

### P.2.13 — Add a screen-reader forecast browser — DONE

**Objective/scope:** Provide a TalkBack-actionable, linear forecast browser with daily/hourly rows sourced from the same forecast projection and selected-time state as the visual timeline.
**Depends on:** P.2.4.
**Exit:** The timeline exposes a standard accessibility click plus a named custom action to open the browser; daily and hourly rows expose their date/time and forecast values, identify the selected row, and selecting a row updates the existing timeline and summary timestamp.
**Evidence:** `.codex/test-artifacts/018-talkback-forecast-browser/README.md`; `.codex/history/018-talkback-forecast-browser.md`.

### P.2.8 — Verify reduced-motion behavior — DONE

**Objective/scope:** Review the installed app with the platform reduced-motion/animation setting enabled and record the bounded behavior needed for this prototype.
**Depends on:** P.1.2.
**Exit:** The relevant system setting and app response are recorded; selected time and scale remain understandable without disorienting motion, and any required change has its own bounded slice and evidence.
**Evidence:** `.codex/test-artifacts/020-reduced-motion/README.md`; `.codex/history/020-reduced-motion.md`.

### P.2.9 — Verify RTL and close installed acceptance — DONE

**Objective/scope:** Review timeline chronology, gesture directions, and labels under a system RTL locale.
**Depends on:** P.2.5, P.2.6, P.2.7, P.2.8, and P.2.11.
**Exit:** Installed RTL screenshots and direct-touch observations establish that chronology, gesture directions, fixture bounds, and visible labels remain understandable.
**Evidence:** `021-p2-acceptance-closure`; `.codex/test-artifacts/021-p2-acceptance-closure/README.md`; `.codex/history/021-p2-acceptance-closure.md`.

### P.2.14 — Group precipitation and wind metrics with visual signals — DONE

**Objective/scope:** Replace the visible `PRECIP CHANCE` and `WIND` headings with the approved rain-signal and compass indicators, keeping the precipitation value on the left and wind speed on the right as visible text grouped with their indicators.
**Depends on:** None.
**Exit:** The installed Pixel 3 XL reference-state capture matches the approved metric arrangement: no visible metric headings; rain signal above its probability value; compass above wind speed; both values remain readable text.
**Concept artwork:** Owner approved 2026-10-26 at `docs/mockups/approved-precipitation-wind-metrics.png`; installed source baseline at `docs/mockups/app_screen_original.png`.
**Evidence:** `042-p2-wind-metrics`; `.codex/test-artifacts/042-p2-wind-metrics/README.md`; `.codex/history/042-p2-wind-metrics.md`; `.codex/history/plans/042-p2-wind-metrics.md`.

### P.2.15 — Keep hourly precision active until an explicit tap — DONE

**Objective/scope:** Preserve hourly precision after the activating hold-and-drag is released.
**Depends on:** P.2.1, P.2.3, and P.2.5.
**Exit:** Installed Pixel 3 XL interaction evidence confirms hold-and-drag enters hourly mode, release ends dragging while hourly mode remains visible, main-area tap returns to daily mode with the same selected time, and timeline tap returns to daily mode at the tapped time.
**Concept artwork:** Owner waived the UI artwork requirement for this slice only on 2026-10-27. `.codex/concepts/P.2.15-hourly-mode-retention.svg` is retained as an unapproved interaction storyboard, not a gate or acceptance artifact.
**Evidence:** `046-p2-hourly-mode-retention`; `.codex/test-artifacts/046-p2-hourly-mode-retention/README.md`; `.codex/history/046-p2-hourly-mode-retention.md`; `.codex/history/plans/046-p2-hourly-mode-retention.md`.

### P.2.16 — Make hourly scrubbing continuous and directionally consistent — DONE

**Objective/scope:** Correct the reported lag and uneven forward/backward response while scrubbing the uniform hourly timeline.
**Depends on:** P.2.15.
**Exit:** On the installed Pixel 3 XL, repeated forward and backward scrubs show continuous selected-time movement with comparable response to equal finger travel, no visible lag during and after precision entry, and no time jump from scale animation.
**Concept artwork:** Owner waived the concept artwork requirement for P.2.16 only on 2026-10-27. `.codex/concepts/P.2.16-hourly-scrub-responsiveness.svg` remains an unapproved planning aid, not a gate or acceptance artifact. The waiver does not apply to other slices.
**Evidence:** `048-p2-hourly-scrub-responsiveness`; `.codex/test-artifacts/048-p2-hourly-scrub-responsiveness/README.md`; `.codex/history/048-p2-hourly-scrub-responsiveness.md`; `.codex/history/plans/048-p2-hourly-scrub-responsiveness.md`.

### P.2.17 — Toggle timeline scale with a double-tap — DONE

**Objective/scope:** Replace the daily timeline's stationary hold-to-enter-hourly gesture with a double-tap on the timeline tape that toggles daily/hourly scale in either direction. Treat the gesture as a scale-only transition that preserves the exact selected instant and its center-playhead position. Remove daily and hourly timeline single-tap selection; preserve immediate daily dragging, hourly scrubbing, main-area single-tap collapse, main-display double-tap fixture realignment, and the screen-reader scale action. Apply the same gesture contract to the active-catalog and fixture/debug timeline surfaces. Add no visible mode row or hint.
**Depends on:** P.2.1, P.2.5, P.2.15, and P.2.16.
**Exit:** On installed active-catalog and fixture/debug timeline flows, a timeline double-tap enters hourly and returns to daily while preserving the exact selected instant; a single timeline tap neither selects a time nor changes scale; immediate daily drag remains daily navigation and hourly drag scrubs; holding or hold-then-drag does not enter hourly; main-area single-tap collapse and main-display double-tap realignment still work. The timeline remains responsive and readable, selected time remains centered, and gesture ownership is predictable in the affected states. Record actual screenshots and scoped interaction outcomes.
**Evidence:** Cycle 110 installed evidence `.codex/test-artifacts/110-p2-double-tap-scale-toggle/README.md`; closeout `.codex/history/110-p2-double-tap-scale-toggle.md`; archived plan `.codex/history/plans/110-p2-double-tap-scale-toggle.md`.
**Slice board(s):** The product owner approved the revised combined behavior/UI artwork board `.codex/concepts/P.2.17-double-tap-scale-toggle.svg` (PNG preview alongside it; behavior contract `.codex/concepts/P.2.17-double-tap-scale-toggle.md`) as shown on 2026-11-05. Approval covers no-op timeline single taps, preserved daily/hourly dragging, scale-only selected-time stability, separation from main-display realignment, affected daily/hourly states, existing tape styling, and absence of added controls. Installed acceptance remains required during execution.

### P.2.18 — Show weather event icons on daily and hourly timelines — DONE

**Objective/scope:** Add the existing weather-event symbol below each weekday label and above its divider in the daily timeline, using that date's daily representative event. In the hourly timeline, show the existing event symbol for each visible hourly sample, aligned to its hourly tick in the icon band above the divider. Preserve daily/hourly labels, selected-time authority, gestures, and forecast facts; reuse existing event-to-symbol mappings and artwork.
**Depends on:** P.2.17, SG.11f, and SG.12.
**Exit:** On the installed active-catalog timeline, daily icons match the supported mapped event for each date's daily representative time and appear below the corresponding weekday labels. Hourly icons match each supported mapped hourly event and follow their ticks during hourly scrubbing. Missing or unsupported events show no icon and do not imply clear weather or borrow an adjacent event. Daily drag, hourly scrub, scale toggle, selected-time cue, date/hour labels, forecast facts, and timeline responsiveness/readability remain intact. Capture actual daily and hourly states and record focused interaction outcomes.
**Evidence:** Cycle 111 installed captures `.codex/test-artifacts/111-p2-timeline-weather-event-icons/`; history `.codex/history/111-p2-timeline-weather-event-icons.md`; archived plan `.codex/history/plans/111-p2-timeline-weather-event-icons.md`.
**Slice board(s):** The owner approved the combined behavior/UI artwork board `.codex/concepts/P.2.18-timeline-weather-event-icons.svg` and enlarged daily-alignment detail `.codex/concepts/P.2.18-timeline-weather-event-icons-daily-alignment.svg` on 2026-11-05 after both PNG previews were shown in `feh` on `DISPLAY=:0`. The behavior/art direction contract is `.codex/concepts/P.2.18-timeline-weather-event-icons.md`; exact approved preview hashes and constraints are in the archived cycle 111 plan. Installed before and hourly reference captures are `.codex/concepts/P.2.18-timeline-weather-event-icons-before.png` and `P.2.18-timeline-weather-event-icons-hourly-reference.png`.

### P.2.19 — Select target-date noon on daily drag release — CANCELLED

**Objective/scope:** This proposed correction would have changed active-catalog daily drag release to select 12:00 local on the date beneath the playhead, with catalog-boundary clamping and an adjustment notice. The owner cancelled the slice before activation on 2026-11-06 and directed that daily/hourly icon refinement take priority. No implementation began.
**Depends on:** None; cancelled and no longer an active dependency.
**Exit:** Not pursued. The daily-drag/noon requirement remains in `docs/SPECIFICATION.md`; cancelling this roadmap item does not change the controlling specification.
**Evidence:** Cancellation decision and no-implementation result: cycle `.codex/history/112-p2-daily-drag-noon-cancelled.md`; archived unactivated plan `.codex/history/plans/112-p2-daily-drag-noon.md`.
**Slice board(s):** The draft `.codex/concepts/P.2.19-daily-drag-noon-board.svg` was not approved and is not an implementation authority.

### P.2.20 — Refine daily and hourly timeline icons — DONE

**Objective/scope:** Enlarge existing daily representative event icons to fill the band between each weekday label and divider. Remove event icons from the hourly timeline to reduce clutter. Preserve daily/hourly labels, ticks, divider, playhead, forecast facts, event mappings, selected-time authority, and gestures.
**Depends on:** P.2.18.
**Exit:** Installed active-catalog daily evidence shows each supported daily representative symbol enlarged beneath its matching weekday label and above the divider without overlap. Installed hourly evidence shows no weather symbols while hour labels, ticks, divider, playhead, and forecast facts remain readable. Focused checks confirm daily drag, hourly scrub, and scale toggle retain their behavior and selected-time meaning; missing daily events remain icon-free.
**Evidence:** Cycle 113 installed captures `.codex/test-artifacts/113-p2-daily-hourly-icon-refinement/README.md`; closeout `.codex/history/113-p2-daily-hourly-icon-refinement.md`; archived plan `.codex/history/plans/113-p2-daily-hourly-icon-refinement.md`. The daily-drag/noon limitation and checks not run are recorded in the evidence and history.
**Slice board(s):** The owner approved the combined behavior/UI artwork board `.codex/concepts/P.2.20-daily-hourly-icon-refinement.svg` with PNG preview and contract alongside it on 2026-11-06 after fullscreen display in `feh` on `DISPLAY=:0`. Approval covers enlarging daily symbols to fill the weekday-label-to-divider band and removing hourly symbols while preserving daily event identity, labels, ticks, playhead, divider, forecast facts, and interactions. The approved preview hash and constraints are recorded in cycle 113's plan. The before/input is `.codex/concepts/P.2.20-daily-hourly-icon-refinement-before.png`; installed acceptance remains required.

## P.3 — Deprecated oxygenWX integration feasibility — DEFERRED

### P.3.1 — Assess integration into the deprecated oxygenWX app — DEFERRED

**Objective/scope:** Keep integration into the deprecated oxygenWX application's `Now -> Hourly -> Daily -> Details` navigation outside the successor WX roadmap. WX builds the successor experience in this repository and preserves the immersive timeline; this deferred slice is only for a separately authorized future reconsideration of changing the deprecated app.
**Depends on:** None.
**Exit after re-entry:** If the owner explicitly reopens deprecated-app integration, an owner-reviewed design documents pager/timeline gesture ownership, typed normalized presentation binding, source/provenance semantics, and a bounded integration plan that preserves that app's existing network/cache/provider flows. Evidence is the design and a focused source-contract review; no application-wide test matrix is required for this design slice.
**Concept artwork:** Pending owner-approved interaction storyboard at `.codex/concepts/P.3.1-integration-feasibility.png`, showing the deprecated app's outer pager and proposed timeline boundary without implying a production navigation change.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** The owner explicitly reverses the current successor boundary and authorizes a separate design for modifying the deprecated oxygenWX application. WX implementation alone does not satisfy this condition.

## P.4 — Production-release presentation cleanup — DEFERRED

### P.4.1 — Remove experimental branding for production release — DEFERRED

**Objective/scope:** Preserve the deprecated app's branding task as legacy-only. Successor OxygenWX branding and release identity are owned by WX.27.
**Depends on:** None.
**Exit after re-entry:** If a separate release of the deprecated app is explicitly authorized, release-candidate screens use its approved branding and installed captures record the affected surfaces. The successor's WX.27 outcome does not close this legacy task.
**Concept artwork:** Pending owner-approved release-surface concept at `.codex/concepts/P.4.1-production-branding.png`, showing the approved identity replacing `OXYGEN / TIME LAB` on each affected surface.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** A separate production-release cycle is explicitly authorized for the deprecated oxygenWX application.

### P.4.2 — Replace DEMO DATA only with accurate provenance — DEFERRED

**Objective/scope:** Preserve the deprecated app's synthetic-data label task as legacy-only. Successor data/provenance labels are owned by WX.25–WX.27.
**Depends on:** A separately authorized deprecated-app integration and a production data path with verified provenance.
**Exit after re-entry:** Installed legacy release UI identifies actual data/source status accurately, with semantics and source evidence reviewed. Focused projection checks cover source identity, freshness/status labeling, and missing-provenance behavior; captures cover the affected source-label surfaces only.
**Concept artwork:** Pending owner-approved source/freshness treatment at `.codex/concepts/P.4.2-provenance-labels.png`, showing source identity, freshness, and unavailable-provenance states without invented provider claims.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** Production forecast data and verified provenance are available in an explicitly approved integration cycle for the deprecated app. Successor data work does not imply legacy-app integration.

### P.4.3 — Define production timeline reset behavior — DEFERRED

**Objective/scope:** Preserve reset behavior design for the deprecated app as legacy-only. Successor reset semantics are handled in WX.24's absolute-time-axis contract.
**Depends on:** P.3.1.
**Exit after re-entry:** An owner-approved legacy interaction contract states reset gesture/action, relationship to the outer pager, and accessibility semantics before implementation begins. Evidence is the approved contract plus a focused review of reset and pager gesture ownership; no broad application interaction or accessibility matrix is part of this design slice.
**Concept artwork:** Pending owner-approved interaction storyboard at `.codex/concepts/P.4.3-production-reset.png`, showing reset entry, selected-time result, and outer-pager gesture boundary.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** The owner authorizes a separate deprecated-app navigation design following P.3.1. WX.24 does not satisfy this legacy re-entry condition.

## ATM — Living atmosphere engine

### ATM.0 — Capture installed pre-engine atmosphere baseline — DONE

**Objective/scope:** Establish the installed app baseline before renderer changes by consolidating the existing Pixel 3 XL visual, gesture, reduced-motion, RTL, and accessibility reviews.
**Depends on:** G.4.
**Exit:** Existing installed evidence covers daily/hourly navigation, main-view swipe and double-tap Now, bright/night and large-font landscape readability, reduced motion, RTL, TalkBack, responsiveness, gesture predictability, and forecast readability; documented limitations remain explicit.
**Evidence:** `.codex/history/005-main-display-navigation.md`; `.codex/history/012-gesture-acceptance-recheck.md`; `.codex/history/007-visual-baseline.md`; `.codex/test-artifacts/`; `.codex/history/014-landscape-high-low.md`; `.codex/history/015-responsive-readability-review.md`; `.codex/history/018-talkback-forecast-browser.md`; `.codex/history/019-talkback-scale-action.md`; `.codex/history/020-reduced-motion.md`; `.codex/test-artifacts/020-reduced-motion/`; `.codex/history/021-p2-acceptance-closure.md`; `.codex/test-artifacts/021-p2-acceptance-closure/`.

### ATM.1 — Pure typed scene projection and static fallback — DONE

**Objective/scope:** Add a pure typed mapper from existing `SkyFrame`, selected time and explicit effect/environment inputs to immutable scene state, with a coherent static fallback and an adapter seam.
**Depends on:** ATM.0.
**Exit:** Scene state derives only from typed inputs; absent values remain absent; rain probability cannot become rain intensity; `selectedHour`, forecast facts, accessibility text, and gesture ownership remain unchanged; the current renderer still provides the accepted baseline.
**Evidence:** `024-atm1-scene-projection`; `.codex/test-artifacts/024-atm1-scene-projection/README.md`; `.codex/history/024-atm1-scene-projection.md`; `.codex/history/plans/024-atm1-scene-projection.md`.

### ATM.2 — Sky, light and illustrative celestial layer — DONE

**Objective/scope:** Add time-aware sky palettes, diffuse light, and illustrative sun/moon artwork with a static/reduced-motion path.
**Depends on:** ATM.1.
**Exit:** Clear/cloudy/day/dusk/night transitions are continuous across fixture samples.
**Evidence:** `025-atm2-sky-light-celestial`; `.codex/test-artifacts/025-atm2-sky-light-celestial/README.md`; `.codex/history/025-atm2-sky-light-celestial.md`; `.codex/history/plans/025-atm2-sky-light-celestial.md`; `.codex/history/plans/025-atm2-sky-light-celestial-concept.png`.

### ATM.3a — Consolidate ATM.3 evidence and dependency-order corrective work — DONE

**Objective/scope:** Reuse completed ATM.3 installed evidence, capture the missing 7:30 PM fractional sample and one corrected 8 PM sample after discovering a cycle-030 artifact mismatch, then map source and acceptance dependencies.
**Depends on:** None. This bounded audit can proceed while ATM.3 is blocked.
**Exit:** Prior evidence is cited by exact path; the two targeted Pixel 3 XL captures disposition the missing fractional and 8 PM states with limitations; device settings are restored; and a source/acceptance dependency map recommends the first corrective slice.
**Evidence:** `031-atm3-blocker-confirmation`; `.codex/test-artifacts/031-atm3-blocker-confirmation/README.md`; `.codex/history/031-atm3-blocker-confirmation.md`; `.codex/history/plans/031-atm3-blocker-confirmation.md`.

### ATM.3 — Deterministic multi-depth cloud renderer — DONE

**Objective/scope:** Complete the deterministic far/mid cloud renderer driven by supported cloud-cover inputs and selected-time phase.
**Depends on:** ATM.2.
**Exit:** Cloud visuals respond smoothly and deterministically to supported cloud-cover and selected-time inputs without reseeding or popping at sample boundaries; phase changes are perceptible but restrained; the static path is legible.
**Evidence:** `026-atm3-cloud-renderer`; `031-atm3-blocker-confirmation`; `.codex/history/`; `.codex/test-artifacts/026-atm3-cloud-renderer/`; `.codex/test-artifacts/031-atm3-blocker-confirmation/`; `032-atm3-corrective-acceptance`; `.codex/test-artifacts/032-atm3-corrective-acceptance/README.md`; `.codex/history/032-atm3-corrective-acceptance.md`; `.codex/history/plans/032-atm3-corrective-acceptance.md`.

### ATM.3b — Deterministic hourly precipitation demo data — DONE

**Objective/scope:** Expand the deterministic DEMO DATA fixture and typed hourly forecast projection with precipitation inputs for visual design: stepped hourly chance values, explicit rain and snow identities, and explicit hourly precipitation totals where supplied.
**Depends on:** ATM.3.
**Exit:** Normal hourly fixture navigation exposes dry and multiple precipitation-chance levels, rain hours with differing explicit totals, a missing-total case, and an explicitly typed snow case.
**Evidence:** `034-atm3b-precipitation-demo-data`; `.codex/test-artifacts/034-atm3b-precipitation-demo-data/`; `.codex/history/034-atm3b-precipitation-demo-data.md`; `.codex/history/plans/034-atm3b-precipitation-demo-data.md`.

### ATM.4 — Bounded rain and snow depiction — DONE

**Objective/scope:** Add clearly visible precipitation artwork driven by typed hourly condition, chance, and optional amount.
**Depends on:** ATM.3 and ATM.3b.
**Exit:** Rain falls visibly across the atmospheric view; stepped chance states produce distinct, understandable moisture coverage; explicit hourly totals distinguish illustrative rain heaviness without implying measured intensity beyond the DEMO DATA fixture.
**Evidence:** `035-atm4-bounded-precipitation`; `.codex/test-artifacts/035-atm4-bounded-precipitation/README.md`; `.codex/history/035-atm4-bounded-precipitation.md`; `.codex/history/plans/035-atm4-bounded-precipitation.md`; `.codex/history/033-atm4-bounded-precipitation.md`; `.codex/test-artifacts/033-atm4-bounded-precipitation/README.md`.

### ATM.5 — Fog and wind effect primitives — DONE

**Objective/scope:** Add reusable haze/turbulence primitives only as clearly labeled preview effects until supported typed visibility and wind-direction inputs exist.
**Depends on:** ATM.3.
**Exit:** Missing inputs produce no claimed weather effect; preview states are clearly identified as illustrative; effects do not obscure forecast text or timeline and have static fallbacks.
**Concept artwork:** Owner approved 2026-10-25 at `.codex/concepts/ATM.5-fog-wind-preview.png`. The approved direction has a richer background and midground, no data box around forecast facts, clouds within scene bounds, and an increasingly perceptible haze effect as the distant ridge softens and loses contrast. Scrubbing should transition deterministically and smoothly with selected time; do not use wall-clock intensity changes or imply wind direction. Rain/snow intensity panels are reference material only and do not expand ATM.5 scope.
**Evidence:** `036-atm5-fog-wind-primitives`; `.codex/test-artifacts/036-atm5-fog-wind-primitives/`; `.codex/history/036-atm5-fog-wind-primitives.md`; `.codex/history/plans/036-atm5-fog-wind-primitives.md`; `037-atm5-selected-time-profile`; `.codex/test-artifacts/037-atm5-selected-time-profile/README.md`; `.codex/history/037-atm5-selected-time-profile.md`; `.codex/history/plans/037-atm5-selected-time-profile.md`.

### ATM.6 — Restrained storm and night refinements — DONE

**Objective/scope:** Add muted storm and night artwork using supported condition/time cues, adapted to the existing abstract landscape geometry.
**Depends on:** ATM.5.
**Exit:** Storm/night cues produce the intended restrained scene states, remain understandable in static/reduced-motion presentation, and introduce no strobing or text occlusion.
**Concept artwork:** Owner approved 2026-10-25 at `.codex/concepts/ATM.6-storm-night.png`. The approved board shows clear night, cloudy night, and storm night with restrained blue-gray cloud scenes and protected forecast-text/timeline areas; no celestial objects, lightning, alerts, rain/snow effects, or astronomical-accuracy claims. On 2026-10-25 the owner resolved the depicted mountain/forest terrain mismatch by retaining the installed abstract landscape and adapting storm/night styling to it.
**Evidence:** `038-atm6-storm-night-refinements`; `.codex/test-artifacts/038-atm6-storm-night-refinements/README.md`; `.codex/history/038-atm6-storm-night-refinements.md`; `.codex/history/plans/038-atm6-storm-night-refinements.md`.

## DFM — Debug forecast matrix

This workstream establishes an explicit, debug-only set of typed forecast scenarios before adding more weather-scene behavior. It does not change the normal `SampleForecast`, claim provider data, or represent official alerts. Generic precipitation probability remains distinct from thunderstorm probability. Matrix coverage is representative and transition-oriented, not the full Cartesian product of every field.

### DFM.1 — Define and approve the debug forecast matrix — DONE

**Objective/scope:** Document the matrix scenario inventory and typed field contract.
**Depends on:** ATM.6.
**Exit:** An owner-reviewed matrix specification lists every scenario and variant, defines all typed fields and missingness, distinguishes illustrative warning state from official alerts, and states the expected time/condition transitions.
**Design artifact:** `.codex/concepts/DFM.1-debug-forecast-matrix.md` — an annotated coverage table and transition map; owner approval is required before DFM.2.
**Evidence:** `.codex/concepts/DFM.1-debug-forecast-matrix.md`; `039-dfm1-debug-forecast-matrix`; `.codex/test-artifacts/039-dfm1-debug-forecast-matrix/README.md`; `.codex/history/039-dfm1-debug-forecast-matrix.md`; `.codex/history/plans/039-dfm1-debug-forecast-matrix.md`.

### DFM.2 — Implement the approved debug forecast catalog — DONE

**Objective/scope:** Implement the approved matrix as deterministic, immutable typed debug forecast scenarios.
**Depends on:** DFM.1.
**Exit:** Focused data/projection checks demonstrate every approved matrix row is addressable and stable, every field retains its specified type/missingness, and normal fixture output is unchanged.
**Concept artifact:** The owner-approved DFM.1 matrix is the implementation contract; no additional scene artwork is in scope.
**Evidence:** `040-dfm2-debug-forecast-catalog`; `.codex/test-artifacts/040-dfm2-debug-forecast-catalog/README.md`; `forecast-catalog-inventory.md`; `.codex/history/040-dfm2-debug-forecast-catalog.md`; `.codex/history/plans/040-dfm2-debug-forecast-catalog.md`.

### DFM.3 — Build debug forecast scenario sets for timeline review — DONE

**Objective/scope:** Add a debug-only way to load complete synthetic forecast profiles from the debug catalog for visual review of timeline scrubbing and atmospheric transitions.
**Depends on:** DFM.2.
**Exit:** Each forecast profile is selectable in a debuggable build and can be scrubbed across its exact loaded bounds on the installed reference device.
**Concept artwork:** Owner approved 2026-10-26 at `.codex/concepts/DFM.3-debug-forecast-matrix-browser.png`. The Pixel 3 XL-based storyboard shows the same full-screen atmosphere and bottom tape across a clear → partly-cloudy → storm scrub sequence, plus separate rain and extended cold/cloud/wind/snow fixtures. No forecast cards or on-screen debug control are proposed.
**Evidence:** `.codex/test-artifacts/041-dfm3-debug-forecast-matrix-browser/README.md`; `.codex/test-artifacts/041-dfm3-debug-forecast-matrix-browser/release-isolation.txt`; `.codex/history/041-dfm3-debug-forecast-matrix-browser.md`; `.codex/history/plans/041-dfm3-debug-forecast-matrix-browser.md`.

### DFM.4 — Concealed on-device debug workbench (split workstream)

**Objective/scope:** Add a full-screen, debug-build-only workbench entered by submitting `#DEBUG` in the existing location search. Debug mode is off by default; release builds do not dispatch developer commands or expose developer controls. While the debug session is active, `#PICKER` opens the existing forecast picker. Provide on-screen diagnostics and a local catalog for composing, saving, and previewing synthetic weather-event timelines on device, using only the typed forecast fields and scenario semantics already supported by DFM.1–DFM.3. Developer-only overlays and raw diagnostics stay out of the default forecast screen; the forecast's essential source/freshness, stale, offline, and error information remains accessible and truthful. Commands remain absent from user-facing manuals. No live network, location selection, provider/cache mutation, official alerts, new weather semantics, or background simulation is in scope.
**Depends on:** DFM.3 and SG.2.
**Exit:** In a release build, `#DEBUG` and `#PICKER` have no developer effect and the main screen exposes no developer diagnostics or scenario controls. In a debuggable build, a default-off session opens the workbench through location search, dispatches `#PICKER` only after debug activation, and returns cleanly to the forecast when the session ends. The workbench can reveal on-screen diagnostics without presenting them as forecast facts, compose and save a local event timeline from supported typed inputs, restore it, and explicitly preview it through the existing selected-time presentation path with synthetic provenance. Missing values remain distinct from zero, probability remains distinct from amount, and unsupported weather remains absent. Installed reference-viewport screens, directly affected command/picker/workbench/preview interactions, and control semantics are reviewed; reduced-motion behavior is checked only where changed UI motion applies. Evidence includes release and debug screenshots and records the manual check that the commands are undocumented in user-facing manuals.
**Slice board(s) — Behavior and UI artwork, OWNER APPROVED:** Product owner approved `.codex/concepts/DFM.4-debug-workbench.png` (editable `.svg`) on 2026-10-31 as the command/session baseline. The revised board set was owner-approved on 2026-11-01: behavior/lifecycle `.codex/concepts/DFM.4-debug-session-flow-board.md`; normal/debug-overlay UI `.codex/concepts/DFM.4-normal-debug-overlay-board.png` (editable `.svg`); catalog-picker UI `.codex/concepts/DFM.4-catalog-picker-board.png` (editable `.svg`). Approval constraints: normal mode contains the date, selected time, temperature, current event, high/low, precipitation chance, and wind meter; the sun is illustrative scene art, not an additional weather fact. The location search remains the app input and debug entry. `#DEBUG` activates a debug-only session; overlay remains independently off by default; saved presets configure only displayed diagnostics; `#PICKER` dispatches only in an active debug session. SIMULATED catalogs are editable; REPLAY catalogs are immutable saved snapshots; preview is explicit and clearly synthetic. Missing values, probability, amount, and unsupported conditions retain current semantics. Provider/cache/location state is never mutated. Essential source/freshness/stale/offline/error information remains truthful and reachable outside the normal main readout. Commands are not described in user-facing manuals. No clean-screen change removes the atmosphere, weather art, timeline, or navigation.
**Evidence:** Target paths to assign when a cycle is planned after SG.2 closes: `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.


**Split transition — 2026-11-01:** The owner directed DFM.4 to be divided into narrow sequential slices, with draft plans for review. Cycle 084 is archived as an incomplete attempt; its code and evidence remain unaccepted inputs, not a completed DFM.4 exit. The parent objective and approval record above remain the combined outcome and constraints. The six exits below collectively retain its obligations. The existing approved board package is a reference; each draft must establish objective-specific board coverage and approval before finalization.

### DFM.4.1 — Coordinator lifecycle and checkpoint — DONE

**Objective/scope:** Implement and independently verify the coordinator-owned startup readiness, debug request completion, opaque in-memory checkpoint and no-I/O restore, plus the existing debug cold-launch handshake.
**Depends on:** DFM.3 and SG.2.
**Exit:** Coordinator readiness, activation completion, atomic no-I/O checkpoint restoration and alternate-launch lifecycle satisfy the original contract, including focused race/cancellation proof; ordinary activation remains compatible.
**Slice board(s):** Parent DFM.4 session board is approved for the product-level outcome. Objective-specific `.codex/concepts/DFM.4.1-coordinator-lifecycle-board.md` was owner-approved as written on 2026-11-01; its lifecycle, cancellation, restoration and scope constraints govern this slice. No UI artwork applies to this nonvisual slice.
**Evidence:** `.codex/history/085-dfm41-coordinator-lifecycle.md`; `.codex/history/plans/085-dfm41-coordinator-lifecycle.md`; `.codex/test-artifacts/085-dfm41-coordinator-lifecycle/README.md`.

### DFM.4.2 — Debug command session and ordinary-action boundary — DONE

**Objective/scope:** Implement debug-only command dispatch, the default-off session and menu, and safe exit before ordinary location selection or refresh/retry.
**Depends on:** DFM.4.1.
**Exit:** Debug-only command/session gate and restore-before-ordinary-action ordering work with focused tests and installed interaction evidence; release has no command handler.
**Slice board(s):** The owner-approved DFM.4 session flow and normal/debug artwork above cover this slice's command, menu, exit, and restore-before-ordinary-action states; the approved workbench baseline supplies command/session context. Cycle 086 records their constraints and scope; overlay and new library states remain later work.
**Evidence:** `.codex/history/086-dfm42-debug-command-session.md`; `.codex/history/plans/086-dfm42-debug-command-session.md`; `.codex/test-artifacts/086-dfm42-debug-command-session/`.

### DFM.4.3 — Clean forecast view and diagnostic overlays — DONE

**Objective/scope:** Apply the approved clean default forecast composition and add optional debug-only diagnostics and local display presets.
**Depends on:** DFM.4.2.
**Exit:** Approved clean main view and independently default-off overlay/presets preserve forecast facts, truthful details and timeline behavior, established by scoped tests and installed captures.
**Slice board(s) — Behavior and UI artwork, OWNER APPROVED:** The product owner approved behavior `.codex/concepts/DFM.4.3-clean-view-behavior-board.md` with visual flow `.svg`, and UI artwork `.codex/concepts/DFM.4.3-clean-view-overlay-artwork-board.png` with editable `.svg` and companion notes `.md`, on 2026-11-01 as shown; no additional constraints were stated. These objective-specific boards use the owner-approved DFM.4 session-flow, normal/debug-overlay, and catalog-picker boards above as references. Approval covers retaining the approved summary and location/refresh/tape actions; independent default-off overlay visibility; presets that change only local display groups; labeled diagnostics that do not obscure forecast facts; source/freshness/error detail reachable through the existing forecast browser; and unchanged installed scene art. These boards define DFM.4.3 scope and acceptance, but do not replace implementation verification or installed evidence. The board gate was satisfied for cycle 087, now closed.
**Pre-slice evidence:** `.codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/pre-slice-main.png`; `README.md`.
**Evidence:** `.codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/README.md`; `.codex/history/087-dfm43-clean-view-diagnostic-overlays.md`; `.codex/history/plans/087-dfm43-clean-view-diagnostic-overlays.md`.

### DFM.4.4 — Local synthetic catalog storage and replay snapshots — DONE

**Objective/scope:** Build the debug-local catalog authoring and persistence foundation independently of editor UI.
**Depends on:** DFM.4.1.
**Exit:** Debug-local editable simulations and immutable replay snapshots round-trip with full canonical horizon, truthful typed support and safe validated preview boundary; focused lifecycle and failure tests pass.
**Slice board(s) — Behavior, OWNER APPROVED:** `.codex/concepts/DFM.4.4-local-catalog-storage-board.md` and visual flow `.codex/concepts/DFM.4.4-local-catalog-storage-board.svg` (PNG preview alongside), approved as written on 2026-11-02. UI artwork is not applicable because this is a nonvisual storage/activation foundation independent of editor UI; DFM.4.5 owns the library/editor presentation and installed UI acceptance. Approval covers canonical SIMULATED materialization, editable SIMULATED round-trip, immutable REPLAY snapshots, explicit-preview revalidation, fail-closed behavior, and LIVE/provider/cache/location isolation. Parent DFM.4 boards remain reference inputs.
**Evidence:** `.codex/history/088-dfm44-local-catalog-storage.md`; `.codex/test-artifacts/088-dfm44-local-catalog-storage/README.md`; `.codex/history/plans/088-dfm44-local-catalog-storage.md`.

### DFM.4.5 — Catalog library and period-first editor — DONE

**Objective/scope:** Extend the debug-local authoring metadata/materializer to support event-specific local-time ranges, then implement the debug-only catalog library, period-first event editor, and explicit synthetic preview.
**Depends on:** DFM.4.2 and DFM.4.4.
**Exit:** Saved SIMULATED catalogs support multiple non-overlapping half-open event ranges within a fixed authored envelope and materialize them faithfully over the full canonical horizon.
**Slice board(s) — behavior and UI artwork:** The product owner approved `.codex/concepts/DFM.4.5-catalog-library-behavior-board.md` with flow `.svg` and PNG preview, UI artwork `.codex/concepts/DFM.4.5-catalog-library-editor-artwork-board.svg` with PNG preview, and authored-range behavior contract `.codex/concepts/DFM.4.5-authored-event-ranges-contract.md` with visual board `.svg` and PNG preview, as shown on 2026-11-02 with no additional constraints. The authored-range board covers half-open event ranges, uncovered baseline periods, overlap rejection, DST, sidecar compatibility, and unchanged schema; the other boards govern `#PICKER` gating, library/editor/SIMULATED/REPLAY transitions, save/cancel without activation, explicit saved-record preview/revalidation, fail-closed preservation, synthetic provenance, and restoration/isolation boundaries. Installed evidence remains required.
**Evidence:** `.codex/history/089-dfm45-catalog-library-editor.md`; `.codex/test-artifacts/089-dfm45-catalog-library-editor/README.md`; `.codex/history/plans/089-dfm45-catalog-library-editor.md`.

### DFM.4.6 — Workbench integration and release acceptance — DONE

**Objective/scope:** Verify the combined original DFM.4 outcome on the installed reference device and release variant.
**Depends on:** DFM.4.2, DFM.4.3 and DFM.4.5.
**Exit:** Final integrated debug and release checks, screenshots, exact restore/no-mutation proof, manual documentation check and original DFM.4 acceptance obligations are recorded and pass.
**Slice board(s) — OWNER APPROVED 2026-11-02:** Objective-specific behavior board `.codex/concepts/DFM.4.6-integrated-workbench-behavior-board.md` and UI artwork board `.codex/concepts/DFM.4.6-integrated-workbench-ui-artwork-board.svg` (PNG preview alongside). Approval covers the integrated debug journey, exact no-I/O restoration, ordinary-action ordering, release command boundary, and final installed states. Constraint: DFM.4.6 remains integration acceptance only; a draggable/resizable diagnostic panel is deferred to separate follow-up work. Cycle 090 records the boards' relationship to scope and acceptance.
**Evidence:** `.codex/history/090-dfm46-workbench-integration-release-acceptance.md`; `.codex/test-artifacts/090-dfm46-workbench-integration-release-acceptance/README.md`; `.codex/history/plans/090-dfm46-workbench-integration-release-acceptance.md`.

## WA — Weather Assets

The WA workstream implements the full 48-symbol weather-event artwork catalog from the owner-approved concept board, in six slices matching its six rows. Symbols use stable semantic IDs and caller-provided size; shared drawings are allowed when they preserve the row's event distinctions. Each slice may add the event representation and mappings needed for its row without claiming unsupported provider facts or official alerts.

### WA.1 — Implement sky symbols — DONE

**Objective/scope:** Implement the first concept-board row: Sunny, Clear night, Mostly sunny, Mostly clear night, Partly sunny/cloudy, Partly cloudy night, Mostly cloudy, and Cloudy/overcast.
**Depends on:** None.
**Exit:** All eight sky entries resolve to scalable, documented artwork through the reusable size-aware API.
**Slice board(s):** Shared behavior/mapping board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and UI artwork board `Oxygen_forecast_weather_symbol_board.png`, both approved by the product owner on 2026-11-03. The behavior board governs the full 48-ID mapping contract; this slice implements only the Sky row.
**Evidence:** `.codex/history/plans/094-weather-symbol-library.md`; `.codex/test-artifacts/094-weather-symbol-library/`; `.codex/history/094-weather-symbol-library.md`.

### WA.2 — Implement rain and ice symbols — DONE

**Objective/scope:** Implement the second concept-board row: Drizzle, Rain, Heavy rain, Rain showers, Freezing drizzle, Freezing rain, Ice pellets/sleet, and Hail.
**Depends on:** WA.1.
**Exit:** All eight entries resolve to scalable, documented artwork through the shared API; rain/freezing distinctions and pellet/hail shapes remain identifiable at intended sizes, with labels retaining precise meaning; scoped installed evidence covers this row and directly affected use.
**Slice board(s):** Shared behavior/mapping board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and artwork board `Oxygen_forecast_weather_symbol_board.png`, both approved 2026-11-03. The behavior board governs the full 48-ID mapping contract; this slice implements only the Rain + Ice row.
**Evidence:** `.codex/history/095-wa2-rain-ice-symbols.md`; `.codex/test-artifacts/095-wa2-rain-ice-symbols/README.md`; `.codex/history/plans/095-wa2-rain-ice-symbols.md`.

### WA.3 — Implement snow and mixed-precipitation symbols — DONE

**Objective/scope:** Implement the third concept-board row: Flurries/snow grains, Snow, Heavy snow, Snow showers, Rain and snow/sleet, Wintry mix, Graupel, and Ice crystals. Preserve supplied component names where available; do not equate graupel, ice pellets, and ice crystals.
**Depends on:** WA.2.
**Exit:** All eight entries resolve to scalable, documented artwork through the shared API; frozen/mixed distinctions remain identifiable at intended sizes, with labels retaining precise meaning; scoped installed evidence covers this row and directly affected use.
**Slice board(s):** Shared behavior/mapping board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and artwork board `Oxygen_forecast_weather_symbol_board.png`, both approved 2026-11-03. The behavior board governs the full 48-ID mapping contract; this slice implements only the Snow + Mixed Precipitation row.
**Evidence:** Cycle 096 complete: `.codex/history/096-wa3-snow-mixed-symbols.md`; installed captures and scoped results `.codex/test-artifacts/096-wa3-snow-mixed-symbols/README.md`; archived plan `.codex/history/plans/096-wa3-snow-mixed-symbols.md`. Automated tests were not authorized or run; the existing exact-16-ID test assertion needs revision before the next authorized focused run.

### WA.4 — Implement visibility and wind symbols — DONE

**Objective/scope:** Implement the fourth concept-board row: Mist, Fog, Freezing fog, Haze/smoke, Dust/sand/ash, Windy/gusty, Blowing snow, and Blizzard.
**Depends on:** WA.3.
**Exit:** All eight entries resolve to scalable, documented artwork through the shared API; cause and event distinctions remain identifiable at intended sizes, with labels retaining precise meaning; scoped installed evidence covers this row and directly affected use.
**Slice board(s):** Shared behavior/mapping board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and artwork board `Oxygen_forecast_weather_symbol_board.png`, both approved 2026-11-03. The behavior board governs the full 48-ID mapping contract; this slice implements only the Visibility + Wind row.
**Evidence:** `.codex/history/plans/097-wa4-visibility-wind-symbols.md`; `.codex/test-artifacts/097-wa4-visibility-wind-symbols/`; `.codex/history/097-wa4-visibility-wind-symbols.md`.

### WA.5 — Implement storm and fallback symbols — DONE

**Objective/scope:** Implement the fifth concept-board row: Thunderstorm, Thunderstorm with rain, Thunderstorm with hail, Thunderstorm with snow, Squall, Tropical cyclone, Tornado/funnel, and Condition unavailable.
**Depends on:** WA.4.
**Exit:** All eight entries resolve to scalable, documented artwork through the shared API.
**Slice board(s):** Shared behavior/mapping board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and artwork board `Oxygen_forecast_weather_symbol_board.png`, both approved 2026-11-03. The behavior board governs the full 48-ID mapping contract; this slice implements only the Storms + Fallback row.
**Evidence:** `.codex/history/plans/098-wa5-storm-fallback-symbols.md`; `.codex/test-artifacts/098-wa5-storm-fallback-symbols/README.md`; `.codex/history/098-wa5-storm-fallback-symbols.md`.

### WA.6 — Implement additional-event symbols — DONE

**Objective/scope:** Implement the sixth concept-board row: Dry thunderstorm, Tropical storm, Waterspout, Frost, Extreme heat, Extreme cold, Dust/sandstorm, and Volcanic ash.
**Depends on:** WA.5.
**Exit:** All eight entries resolve to scalable, documented artwork through the shared API; event distinctions remain identifiable at intended sizes, with labels retaining precise meaning; scoped installed evidence covers this row and directly affected use.
**Slice board(s):** Shared behavior/mapping board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and artwork board `Oxygen_forecast_weather_symbol_board.png`, both approved 2026-11-03. The behavior board governs the full 48-ID mapping contract; this slice implements only the Additional Events row.
**Evidence:** `.codex/history/099-wa6-additional-event-symbols.md`; `.codex/test-artifacts/099-wa6-additional-event-symbols/README.md`; `.codex/history/plans/099-wa6-additional-event-symbols.md`.

## SG — Scene Generation

The SG workstream follows the owner-directed pivot recorded above and is grounded in the draft `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`. It consumes typed forecast events and emits semantic asset/layer/timeline tracks; themes supply backgrounds, placement, paths, ordering, and visual variants. It does not assume a landscape, sky, horizon, or planetary setting. SG.0 is documentation-only; SG.1–SG.7 follow their SG and DFM dependencies and do not wait on deferred WX.27/WX.28 release-readiness work or cancelled WX.29. For SG, a slice board package means a flow/logic board plus a UI artwork board for visual slices; one combined board is acceptable when both parts are clear. Existing-product RTL and TalkBack acceptance is the inherited baseline. Review font scale in SG UI work when applicable.

### SG.0 — Review and mature the Scene Generation proposal — DONE

**Objective/scope:** Execute `.codex/prompts/scene-generation-roadmap-kickoff.md` as a documentation-only review of OXY-SG-001 and SG.1–SG.7. Refine scene-track/theme contracts, transition and data-truth rules, boundaries, slice exits, dependencies, slice-board briefs, and open decisions. Do not change application code or claim concept approval.
**Depends on:** G.5.
**Exit:** The specification and roadmap are internally consistent and grounded in current source; SG.1–SG.7 are bounded, ordered, and have valid dependencies and observable exits; material unresolved choices are listed for owner resolution; slice-board briefs identify the affected visual states and invariants; the spec remains a draft unless the owner explicitly adopts it. Evidence includes the review findings and final documentation diff.
**Slice board(s) — Flow/logic:** Product-owner approved the complete proposal-review behavior board at `.codex/concepts/SG.0-proposal-review-behavior-board.md` on 2026-10-30 after reviewing its visual sheet at `.codex/concepts/SG.0-proposal-review-behavior-board.png`. It covers the behavior/type/ownership review direction and leaves exact transitions, unavailable artwork, and missing-theme-asset fallback to later bounded slices. The kickoff prompt is an execution brief, not the required board.
**Evidence:** Cycle 075 review findings and documentation diff: `.codex/test-artifacts/075-sg0-proposal-review/`; closed outcome: `.codex/history/075-sg0-proposal-review.md`; archived plan: `.codex/history/plans/075-sg0-proposal-review.md`. The SG specification remains a draft.

### SG.1 — Generate deterministic semantic scene tracks — DONE

**Objective/scope:** Define and implement the pure scene-plan contract from canonical typed forecast events, location timezone, and selected-time window. Tracks identify an asset or asset family, semantic `layer_name`, absolute timeline, and bounded transition policy; they contain no theme-specific coordinates or layout. Include day/night transition tracks as explicit light-phase variants without astronomical claims.
**Depends on:** SG.0, DFM.1, DFM.2, and DFM.3.
**Exit:** Repeated generation from the same catalog revision, timezone, window, and generator version yields the same ordered theme-independent tracks; point and interval support retain their granularity; dry, precipitation, missing, and probability-only inputs remain distinct; a documented bounded representative-display, expected-gap, and disclosure rule handles adjacent point samples without reporting an exact event minute; day/night tracks do not alter weather support; a focused six-hour scenario can be inspected as a scene plan without rendering.
**Slice board(s) — Flow/logic:** Product-owner approved `.codex/concepts/SG.1-scene-track-contract.png` on 2026-10-31, with no additional constraints stated; editable source is `.codex/concepts/SG.1-scene-track-contract.svg`. The board governs adjacent supported samples, an expected missing sample, point versus interval support, source valid times, the nearest expected-time display-cell rule, and independent light tracks. It is a behavior contract, not UI artwork or installed evidence. Candidate forecast IDs in `docs/WEATHER_ASSETS_SPEC.md` inform semantic mapping only where canonical typed inputs support them; approval does not extend to artwork or full inventory coverage.
**Evidence:** Cycle 076 trace and command record: `.codex/test-artifacts/076-sg1-semantic-scene-tracks/`; closed outcome: `.codex/history/076-sg1-semantic-scene-tracks.md`; archived plan: `.codex/history/plans/076-sg1-semantic-scene-tracks.md`. No UI or installed acceptance was claimed.

### SG.2 — Refactor the first-city atmosphere composition — DONE

**Objective/scope:** Rework the first-city composition and its existing sky, time-cue, cloud, and rain primitives to achieve the owner's atmosphere-first target after the cycle 078 installed result was rejected. Keep the city present and detailed, reduce sky dominance, add broad selected-time sunlight that moves and diffuses through the atmosphere, scale the sun/moon cues through their cycles, remove the instructional-looking visible path, and compose clouds and rain into coherent weather fields: layered irregular soft-edged cloud masses and fine short rain curtains, not randomly scattered icons. Include the owner-approved timeline layout adjustment: about 120 dp in two bands, date context above the scrub rail/playhead with selected time attached to the playhead, and move the readout/lower facts upward about 20 dp. Preserve selected-instant authority, existing daily/hourly scale semantics and gestures, and forecast fields. Stylized forms are sufficient; photorealism is not required. Do not add forecast inputs, providers, location logic, or another theme.
**Depends on:** SG.1.
**Visual conditions:** The approved refactor board defines Pixel 3 XL portrait and 360×640 dp compact targets. Installed review covers morning, noon, dusk, night, cloudy, rain, and unavailable-weather states; default and 1.3 app font scale; and records RTL/reduced-motion applicability and affected-state checks. Confirm city prominence, sky share, time-cue scale, cloud/rain character, text/tape readability, timeline responsiveness, and gesture ownership.
**Exit:** Cycle 079 installed the approved city composition, selected-time atmospheric light and cue, the two-band timeline, and stylized cloud treatment at portrait/compact sizes with selected-time and gesture behavior retained. On 2026-10-31 the owner accepted the cloud treatment as “pretty good” and directed that the remaining weather-state coverage be handled in coming slices. This closes SG.2 with current cloud treatment accepted; cycle 079 did not capture valid rain or unavailable-weather states, and does not claim those states passed. SG.3 covers light/clear/cloudy/unavailable states; SG.4 covers precipitation states; later SG slices cover remaining approved event families.
**Slice board(s):** Existing cycle-078 boards were approved for the prior visual target and are insufficient for this refactor. Product-owner approved the combined behavior/UI artwork board `.codex/concepts/SG.2-atmosphere-recomposition-v1.png` (editable `.svg`) on 2026-10-31; generated direction study `.codex/concepts/SG.2-atmosphere-recomposition-study-v1.png` is explanatory reference, not a production asset. Approval constraints: prominent near/middle-ground city, about 35–40% sky, softened dusk, sun/moon cues scaling over their illustrative sky cycles, no visible path, broad moving sunlight that supported clouds can diffuse, organic layered cloud fields, and coherent rain curtains rather than scattered icons. The owner also approved `.codex/concepts/SG.2-timeline-footprint-proposal-v2.png` (editable `.svg`) on 2026-10-31 after reviewing it on the persistent X window and approved the revised board again on 2026-10-31. It uses the actual installed Pixel screen and specifies an about-120-dp two-band layout: date context row with the focused weekday 20 dp, bold, uppercase and other weekdays 12 dp, regular, uppercase; then scrub rail/playhead with selected-time label, plus about 20 dp upward movement of the forecast readout and lower facts. Preserve all existing time selection, gesture, and forecast semantics. Stylized forms are sufficient; photorealism is not required. These boards govern the cycle's scope and visual acceptance; selected-time/forecast semantics and unsupported-weather absence remain unchanged. The owner separately approved the moving/diffused lighting direction on 2026-10-31.
**Evidence:** Cycle 079: `.codex/test-artifacts/079-sg2-atmosphere-recomposition/README.md`; owner-accepted closeout: `.codex/history/079-sg2-atmosphere-recomposition.md`; archived plan and owner closeout addendum: `.codex/history/plans/079-sg2-atmosphere-recomposition.md`. Installed city/light/cue/timeline and cloud evidence is retained; rain and unavailable-weather are explicitly deferred, not marked verified.

### SG.3 — Render clear, cloudy, and light-phase scenes — DONE

**Objective/scope:** Render the first theme's clear, partly cloudy, and cloudy tracks, including continuous day/dawn/dusk/night asset transitions while the forecast timeline is scrubbed. Add provider-backed daily sunrise and sunset to the Open-Meteo request, canonical daily summary, recorded catalog, and shared selected-time scene reading so sun cues cross the horizon at the location/date-specific event in every weather scene. Supported cloud fields move visibly but with restraint as selected forecast time advances; placement is deterministic from selected time, smooth between samples, and does not imply wind direction or use wall-clock drift. Use existing approved debug scenarios; do not add other forecast facts or redesign timeline gestures.
**Depends on:** SG.1 and SG.2.
**Visual conditions:** Installed Pixel 3 XL portrait states cover fresh-provider sunrise/day/sunset/night and selected-time travel. By owner direction, cycle 080 limited verification to slice-local checks and did not run RTL, TalkBack, compact/large-font, reduced-motion setting sweeps, or broader system-wide testing; those checks are not claimed as passes.
**Exit:** Focused installed states show approved sky-condition distinctions, date-specific solar event crossings, light transitions, and perceptible cloud travel across selected instants. Sunrise and sunset remain attached to their provider local dates, are preserved by replay, and reach every weather scene through the shared scene reading; missing events add no solar claim. Cloud placement is deterministic for a selected instant and smooth between samples, while missing cloud data adds no cloud claim. Transitions do not steal input or delay scrubbing; selected time, forecast facts, and readable UI remain authoritative; static presentation preserves the same scene meaning.
**Slice board(s):** Product-owner approved the combined flow/logic and UI artwork board `.codex/concepts/SG.3-sunrise-sunset-provider-mapping-v2.png` (editable `.svg`) on 2026-10-31. The owner directed SG.3 to remain event-only: provider sunrise/sunset set the only horizon crossings; the between-event path is illustrative and makes no solar azimuth/elevation claim. The board shows four selected instants around Chicago sunrise and sunset, the Open-Meteo daily Unix-seconds to canonical `Instant` to selected-date scene-reading path, shared timing across clear/cloud/rain/storm scenes, replay preservation, DST/local-date handling, unavailable-event behavior, and cloud/city depth. Product-owner also approved `.codex/concepts/SG.3-clear-cloud-light-transitions-v2.png` (editable `.svg`) on 2026-10-31. It governs 06:00/12:00/18:00 selected-time cloud poses, one incoming coherent bank for rain, opposing converging banks for storms, missing-cloud behavior, reduced motion, and skyline/foreground depth. Cloud direction is illustrative, not measured wind or speed. Boards guide scope; installed screenshots remain required acceptance evidence.
**Evidence:** Cycle 080: `.codex/test-artifacts/080-sg3-sunrise-sunset-scenes/README.md`; closed outcome: `.codex/history/080-sg3-sunrise-sunset-scenes.md`; archived plan: `.codex/history/plans/080-sg3-sunrise-sunset-scenes.md`. Broader RTL, TalkBack, compact/large-font, and system-wide checks remain unclaimed under the owner's explicit testing constraint.

### SG.4 — Add precipitation event tracks and overlays — DONE

**Objective/scope:** Map supported rain and snow evidence, probability, explicit amounts, and missing values to coherent first-theme scenes with day/night variants. Apply the bounded `sg4-interior-carry-v1` display contract in `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`: an absent expected interior hour bracketed by supported condition records on the same local date may show the preceding condition and scene with its original source time disclosed; its hourly source fact remains unavailable. Leading/trailing gaps and empty or unsupported supplied conditions make no hourly condition claim. A selected rain state must read visually as rain across the whole scene, with cloud-led sky and obscured or strongly subdued direct sun, while preserving supplied cloud-cover facts, provider solar-event timing, source support, and separate chance/amount meanings. Demonstrate a near-term scrub through supported dry, rain, carried-gap, and clearing displays without implying that point samples establish exact event intervals. The general scene-schema repair belongs to SG.9.
**Depends on:** SG.1, SG.2, and SG.3.
**Visual conditions:** Focused installed Pixel 3 XL portrait states cover dry, rain, snow, eligible carried-gap, leading/trailing unavailable, probability-only, explicit-amount, day, and night cases. Check only the changed scenes, selected-time behavior, readability, responsiveness, and gesture ownership. RTL, TalkBack, and broad system/environment matrices are outside this design slice and belong to dedicated `TEST` slices.
**Exit:** A focused six-hour installed scenario shows the selected supported categorical value mapped to its scene, and precipitation changes within source-supported point display cells or explicit interval bounds. A supplied `CLEAR`, `PARTLY_CLOUDY`, or `CLOUDY` category maps to dry. An eligible absent expected interior hour displays the preceding supplied condition and scene with its original source time and selected-hour unavailability disclosed; the next supplied cell takes over under the SG.1 midpoint rule. Leading/trailing gaps and empty or unsupported supplied conditions make no hourly condition claim. The installed day-rain scene shows a cloud-led sky, no bright exposed sun, and clearly visible scene-wide rain behind clouds and readable forecast text; selected rain, chance, cloud cover when supplied, amount/rate when supplied, and selected/fact times are recorded with the capture. Night snow, probability-only, and explicit-amount states remain distinguishable; no chosen visual transition minute is reported as a source event time. Solar-event timing, selected-time behavior, responsiveness, gesture ownership, and readable forecast values remain intact.
**Slice board(s):** Product-owner approved the historical behavior board `.codex/concepts/SG.4-precipitation-event-tracks.png` and historical UI artwork `.codex/concepts/SG.4-precipitation-visual-states-v1.png` on 2026-11-01. They are superseded for missing-hour display and rain-sky appearance. On 2026-11-01, after reviewing the visuals on `DISPLAY=:0`, the product owner approved the revised package without stated exceptions: behavior board `.codex/concepts/SG.4-precipitation-event-tracks-v5.png`, UI artwork board `.codex/concepts/SG.4-precipitation-visual-states-v8.png` (both with editable `.svg` sources), and objective-specific boundary and affected-state annotations `.codex/concepts/SG.4-revised-boards-review-notes.md`. The note covers eligibility, midpoint takeover, local-date and interval boundaries, leading/trailing gaps, and artwork conditions shown only by example in the PNGs. This package governs the SG.4 mapping, artwork, and exit above; installed evidence remains required. The approved intensity matrix `.codex/concepts/SG.4-precipitation-intensity-matrix-v3.png` defines visual variants without authorizing source-intensity selection. The approved device-anchored reference `.codex/concepts/SG.4-device-screen-reference.png` remains an unchanged installed-screen reference, not SG.4 acceptance evidence. Select only supported input families from the proposed weather-assets inventory.
**Evidence:** Cycle history: `.codex/history/081-sg4-precipitation-event-tracks.md`; installed evidence and limitations: `.codex/test-artifacts/081-sg4-precipitation-event-tracks/README.md`; archived cycle plan: `.codex/history/plans/081-sg4-precipitation-event-tracks.md`.

### SG.4a — Constrain clouds to the upper screen region — DONE

**Objective/scope:** Keep every visible cloud pixel within the top 33% of the atmospheric scene viewport in all existing renderer routes and supported cloud states. Measure the region from the top edge of the scene canvas; include antialiased edges, shadows, blur, movement, and transitions. Preserve cloud-cover mapping, supported weather facts, precipitation meaning, city composition, and selected-time behavior. This is a bounded placement change, not the broader typed-scene refactor.
**Depends on:** SG.4.
**Visual conditions:** Focus installed Pixel 3 XL portrait cloudy, rain, snow, and storm states across day/night where available; inspect the cloud boundary and directly affected readability. Check reduced-motion still placement if available. Do not add RTL, TalkBack, or broad system/environment matrices.
**Exit:** In every affected installed state and captured transition/pose, no visible cloud content crosses below `0.33 × atmospheric canvas height`; supported cloud presence/absence and cloud-cover facts remain unchanged. Rain/snow and forecast text retain their existing meaning and layering, and selected time, responsiveness, gesture ownership, and readable forecast facts remain intact.
**Slice board(s):** Product-owner approved 2026-11-01: behavior board `.codex/concepts/SG.9-cloud-height-contract.md` and UI artwork board `.codex/concepts/SG.9-cloud-height-artwork.png` (editable source `.svg`). Approval constraints: atmospheric scene canvas is the measurement area; the cap includes silhouettes, edge pixels, shadow/blur, and intermediate motion poses across all supported states; precipitation may extend below the cloud region; cloud support and forecast facts do not change. Boards govern only SG.4a and do not approve the separate SG.9 refactor boards.
**Evidence:** Cycle history `.codex/history/082-sg4a-cloud-height-cap.md`; installed captures and observation log `.codex/test-artifacts/082-sg4a-cloud-height-cap/README.md`; archived plan `.codex/history/plans/082-sg4a-cloud-height-cap.md`.

### SG.9 — Repair typed scene state and composition boundary — DONE

**Objective/scope:** After SG.4a closes, refactor the scene path so the selected canonical catalog and `Instant` produce one typed, support-aware scene reading and one coherent composition for the first theme. Replace string-valued weather decisions and parallel nearest-sample decorative weather selection on the catalog route. Centralize shared effects on sky light, direct cue visibility, clouds, precipitation, and layer order so later event-family slices do not add isolated renderer rules. Preserve SG.4-approved rain/snow appearance and existing `CLEAR`, storm, day/night, forecast-text, timeline behavior, and the SG.4a upper-third cloud-placement rule; add no provider fields, catalog/replay schema changes, new weather families, or new theme artwork. Keep non-catalog compatibility routes functioning within their existing inputs.
**Depends on:** SG.4 and SG.4a.
**Visual conditions:** Compare installed Pixel 3 XL portrait selected-time `CLEAR`, rain, snow, storm, probability-only, and unknown states before and after the refactor, with day/night where applicable; review only changed scene and directly affected scrub/readability/gesture behavior. Broader RTL, TalkBack, and environment matrices remain dedicated `TEST` work.
**Exit:** A single selected-time typed scene reading carries condition, cloud cover, precipitation type and separate chance/amount/rate support, solar/light inputs, and point/interval bounds without using a renderer-owned nearest-sample weather choice or string parsing as the semantic source. One composition boundary resolves shared sky/cue/cloud/precipitation interactions and documented layer order; the renderer draws the result without re-deciding forecast meaning. Focused mapping and installed comparisons show SG.4 rain/snow, `CLEAR`, storm, day/night, probability-only, and unknown states retain their approved meaning and readability; selected time and source bounds stay stable during scrubbing. The final diff and scoped evidence identify any remaining legacy route separately rather than claiming universal migration.
**Slice board(s):** Product-owner approved both boards on 2026-11-01: behavior/schema flow at `.codex/concepts/SG.9-typed-scene-composition-flow.png` (editable `.svg`) and contract at `.codex/concepts/SG.9-typed-scene-composition-contract.md`; UI artwork comparison at `.codex/concepts/SG.9-scene-composition-states.png` (editable `.svg`). Approval constraints: preserve current selected-time scrubbing behavior and source bounds; no new interpolation/transition requirement, provider fields, catalog/replay schema changes, weather families, or theme artwork. The boards describe the selected-time reading → composition → renderer boundary and the focused state comparisons in this slice. The SG.4 v2 artwork remains a visual reference, not the SG.9 board.
**Evidence:** Cycle history `.codex/history/083-sg9-typed-scene-composition.md`; installed before/after captures, focused mapping observations, commands, and limitations `.codex/test-artifacts/083-sg9-typed-scene-composition/README.md`; archived plan `.codex/history/plans/083-sg9-typed-scene-composition.md`. Existing-fixture gaps and unrun states are recorded in the evidence and history.

### SG.5 — Center city in the selected-weather readout — DONE

**Objective/scope:** Reorganize the first-theme screen hierarchy around one central selected-weather reading that communicates the loaded city label, temperature, and atmosphere (for example, “Chicago · 33° · Windy”). The city remains a noninteractive label. Place the existing location/search-menu action as a magnifier beside the precipitation indicator, separate from the central gesture area; place a gear beside the wind indicator as a settings affordance only. The owner resolved on 2026-11-02 that SG.5 does not implement settings behavior or menu contents; those belong to a later bounded slice. Remove the top City, Place, and Refresh controls to leave the upper scene clear. Keep selected date/time and high/low as supporting facts, with the weekday slightly emphasized to aid timeline scrubbing. Evenly space and visually match the precipitation, magnifier, gear, and wind icons at 33% transparency. Add only a very subtle text shadow or equivalent treatment. Do not introduce a temperature-unit suffix or define US, imperial, or metric unit systems. This slice does not add weather categories or scene light/shadow effects.
**Depends on:** SG.1, SG.2, SG.3, and SG.9.
**Visual conditions:** Use the current installed screen capture as the before/reference cap and the owner-approved UI artwork board `.codex/concepts/SG.5-location-readout-ui-artwork-board.svg` for the intended hierarchy. Review the affected portrait screen on the Pixel 3 XL and a compact viewport; assess large-font, RTL, and reduced-motion applicability in the bounded plan. Directly review magnifier discoverability/activation, settings entry according to the resolved scope, selected-time accuracy, weekday readability, four-icon spacing/opacity, scene readability, timeline responsiveness, and gesture ownership. Do not add broad system/environment matrices.
**Exit:** The central group quickly communicates loaded city label, selected temperature, and atmosphere; the separate magnifier opens the existing location/search flow and opening/dismissing it alone does not change selected time. Selected weekday, date/time, and high/low remain legible supporting facts. The four lower indicators/icons are evenly spaced, visually matched, and 33% transparent. Top City, Place, and Refresh controls are removed while unrelated source/freshness and dataset facts remain unless separately directed. The gear behavior meets the owner-resolved scope. Subtle text separation preserves the scene; forecast values, selected-time behavior, timeline responsiveness, gesture ownership, and readable facts remain intact in scoped installed evidence.
**Slice board(s):** Product owner approved the revised behavior board `.codex/concepts/SG.5-location-readout-behavior-board.md` as written on 2026-11-02. It governs the noninteractive loaded-city label, lower-row magnifier and existing location-menu behavior, gear affordance-only scope, selected-time/data/gesture invariants, weekday emphasis, and exact removal boundary. Product owner also approved the UI artwork board `.codex/concepts/SG.5-location-readout-ui-artwork-board.svg` on 2026-11-02, prepared from reference cap `.codex/concepts/SG.4-device-current.png`; it governs the visual hierarchy, weekday emphasis, subtle separation, and evenly spaced matched lower indicators/icons at 33% transparency. Neither board authorizes settings behavior, weather semantics/assets, unit policy, search changes, or atmospheric lighting effects.
**Evidence:** Cycle 091 closeout `.codex/history/091-sg5-center-city-weather-readout.md`; installed baseline/after captures, command results, and scoped observations `.codex/test-artifacts/091-sg5-center-city-weather-readout/README.md`; archived plan `.codex/history/plans/091-sg5-center-city-weather-readout.md`.

### SG.10 — Remove prefix from main timestamp label — DONE

**Objective/scope:** Remove only the literal `Selected` prefix from the main forecast readout's date/time line, for example `MON Nov 2 · Selected 6:25:31 PM CST` → `MON Nov 2 · 6:25:31 PM CST`. Preserve the same weekday, date, selected clock time, timezone, forecast values, layout, and interactions. Keep `Selected location` fallback and forecast-browser `Selected …` copy unchanged, as chosen by the owner on 2026-11-02.
**Depends on:** SG.5.
**Exit:** Installed main-screen captures show the prefix-free label at the current reading and after a different timeline selection. The displayed date/time/timezone continue to match the same selected instant; no other labels or interactions change. Scoped evidence records actual device screenshots and exact results.
**Slice board(s):** Product owner approved both boards as written on 2026-11-02: behavior board `.codex/concepts/SG.10-main-timestamp-label-behavior-board.md` and UI artwork board `.codex/concepts/SG.10-main-timestamp-label-ui-artwork-board.svg`. Approval constraints: remove the prefix only from the main date/time line; preserve the selected instant, date/time/timezone, forecast values, fallback location copy, browser copy, layout, and interactions.
**Evidence:** Cycle 092 closeout `.codex/history/092-sg10-remove-selected-time-prefix.md`; installed screenshots, hierarchy captures, command results, observations, and limits `.codex/test-artifacts/092-sg10-remove-selected-time-prefix/README.md`; archived plan `.codex/history/plans/092-sg10-remove-selected-time-prefix.md`.

### SG.11a — Map sky events — DONE

**Objective/scope:** Establish the shared typed event, primary-selection, source-provenance, selected-time projection, raw live-capture, recording v5/replay, and debug review path. Map and install-review only the Sky row: SUNNY, CLEAR_NIGHT, MOSTLY_SUNNY, MOSTLY_CLEAR_NIGHT, PARTLY_SUNNY_CLOUDY, PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY, CLOUDY_OVERCAST. Preserve older recordings, broad scene conditions, and the approved local-date Sun rule for legacy broad clear/partly-cloudy input.
**Depends on:** WA.1–WA.6, SG.3, and SG.9.
**Exit:** A captured Open-Meteo response yields a 44-local-date catalog and matching selected-time Sky symbol/name; the same bounded bytes and request identity survive recording and replay. Sky IDs can be supplied, recorded, projected, and inspected on device. Version 1–4 recordings remain readable without typed-field backfill; version 5 survives storage reload. Explicit primary, complete event retention, deterministic precedence, unavailable fallback, selected-time/source-time ownership, and day/night boundaries work for scoped Sky and cross-row representative facts. Exact-code ledger is expanded before mapper changes, with unsupported intermediate codes explicit. Eight installed Sky states and directly affected interactions have scoped evidence.
**Slice board(s):** The owner-approved SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` govern this row and the shared rules it uses. Approval was recorded on 2026-11-04; the owner approved the six-row execution split on 2026-11-04. The retained boards cover all rows; each slice accepts only its named row and directly affected shared behavior.
**Owner acceptance clarification, 2026-11-04:** One valid installed screenshot showing a slice-specific icon is the visual acceptance criterion for this slice. Cycle 102's `sky-sunny.png` and paired hierarchy dump satisfy it; the seven other Sky captures are supplementary coverage.
**Evidence:** Cycle 102 closeout `.codex/history/102-sg11-weather-event-mapping.md`; archived plan `.codex/history/plans/102-sg11-weather-event-mapping.md`; installed captures, live response, commands, and limitations `.codex/test-artifacts/102-sg11-weather-event-mapping/README.md`.

### SG.11b — Map rain and ice events — DONE

**Objective/scope:** Extend the established SG.11a typed mapping and bounded debug route for the Rain and ice row: DRIZZLE, RAIN, HEAVY_RAIN, RAIN_SHOWERS, FREEZING_DRIZZLE, FREEZING_RAIN, ICE_PELLETS, HAIL. Add only documented provider-code assignments that support this row; preserve prior rows, recorded compatibility, and the shared selection contract.
**Depends on:** SG.11a.
**Exit:** The eight Rain and ice IDs survive canonical input, version-5 recording/replay, selected-time projection, and matching symbol/accessibility wording. All eight installed Pixel 3 XL states and direct timeline selection were checked. Focused runtime checks cover all 13 documented Open-Meteo codes, unsupported intermediate values, version-5 replay, and legacy versions 1–4 without typed-field backfill.
**Slice board(s):** The owner-approved SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` govern this row and the shared rules it uses. Approval was recorded on 2026-11-04; the owner approved the six-row execution split on 2026-11-04. The retained boards cover all rows; each slice accepts only its named row and directly affected shared behavior.
**Evidence:** Cycle 103 closeout `.codex/history/103-sg11b-rain-ice-events.md`; archived plan `.codex/history/plans/103-sg11b-rain-ice-events.md`; installed captures, hierarchy dumps, focused mapper and legacy replay checks, commands, observations, and limits `.codex/test-artifacts/103-sg11b-rain-ice-events/README.md`.

### SG.11c — Map snow and mixed precipitation events — DONE

**Objective/scope:** Extend the established SG.11a typed mapping and bounded debug route for the Snow and mixed precipitation row: FLURRIES_SNOW_GRAINS, SNOW, HEAVY_SNOW, SNOW_SHOWERS, RAIN_AND_SNOW, WINTRY_MIX, GRAUPEL, ICE_CRYSTALS. Add only documented provider-code assignments that support this row; preserve prior rows, recorded compatibility, and the shared selection contract.
**Depends on:** SG.11b.
**Exit:** Each supplyable row ID survives canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Every row state is inspected in an installed capture with exact fact, symbol ID, name, command, and limitation; directly affected selection is checked. Existing rows and legacy replay remain valid.
**Slice board(s):** The owner-approved SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` govern this row and the shared rules it uses. Approval was recorded on 2026-11-04; the owner approved the six-row execution split on 2026-11-04. The retained boards cover all rows; each slice accepts only its named row and directly affected shared behavior.
**Owner acceptance clarification, 2026-11-05:** The product owner directed cycle 104 to close and assigned center-readout weather-data refactoring to SG.12. Cycle 104's installed captures show the center condition label colliding with/crowding selected date/time in five snow/mixed states. This limitation is accepted for SG.11c and deferred to SG.12; no collision fix is claimed by this slice.
**Evidence:** Cycle 104 closeout `.codex/history/104-sg11c-snow-mixed-events.md`; installed captures, hierarchy dumps, scoped tests, commands, and limitation `.codex/test-artifacts/104-sg11c-snow-mixed-events/README.md`; archived plan `.codex/history/plans/104-sg11c-snow-mixed-events.md`.

### SG.11d — Map visibility and wind events — DONE

**Objective/scope:** Extend the established SG.11a typed mapping and bounded debug route for the Visibility and wind row: MIST, FOG, FREEZING_FOG, HAZE_SMOKE, DUST_SAND_ASH, WINDY_GUSTY, BLOWING_SNOW, BLIZZARD. Add only documented provider-code assignments that support this row; preserve prior rows, recorded compatibility, and the shared selection contract.
**Depends on:** SG.11c.
**Exit:** Each supplyable row ID survives canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Every row state is inspected in an installed capture with exact fact, symbol ID, name, command, and limitation; directly affected selection is checked. Existing rows and legacy replay remain valid.
**Slice board(s):** The owner-approved SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` govern this row and the shared rules it uses. Approval was recorded on 2026-11-04; the owner approved the six-row execution split on 2026-11-04. The retained boards cover all rows; each slice accepts only its named row and directly affected shared behavior.
**Recorded limitation, 2026-11-05:** Installed row states retain close spacing between the central condition and selected date/time. The readout is outside SG.11d's mapping scope and remains assigned to SG.12; the exact installed states and hierarchy evidence are recorded in the cycle README.
**Evidence:** Cycle 105 closeout `.codex/history/105-sg11d-visibility-wind-events.md`; installed captures, hierarchy dumps, focused mapper/replay checks, commands, observations, and limits `.codex/test-artifacts/105-sg11d-visibility-wind-events/README.md`; archived plan `.codex/history/plans/105-sg11d-visibility-wind-events.md`.

### SG.11e — Map storm and fallback events — DONE

**Objective/scope:** Extend the established SG.11a typed mapping and bounded debug route for the Storm and fallback row: THUNDERSTORM, THUNDERSTORM_WITH_RAIN, THUNDERSTORM_WITH_HAIL, THUNDERSTORM_WITH_SNOW, SQUALL, TROPICAL_CYCLONE, TORNADO_FUNNEL; CONDITION_UNAVAILABLE is presentation-only. Add only documented provider-code assignments that support this row; preserve prior rows, recorded compatibility, and the shared selection contract.
**Depends on:** SG.11d.
**Exit:** Each supplyable row ID survives canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Every row state is inspected in an installed capture with exact fact, symbol ID, name, command, and limitation; directly affected selection is checked. Existing rows and legacy replay remain valid. The unavailable state is also inspected in an installed capture and cannot be stored as a supplied event.
**Slice board(s):** The owner-approved SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` govern this row and the shared rules it uses. Approval was recorded on 2026-11-04; the owner approved the six-row execution split on 2026-11-04. The retained boards cover all rows; each slice accepts only its named row and directly affected shared behavior.
**Recorded limitation, 2026-11-05:** During a scrub to 3:45 PM from the 3:00 PM unavailable event, the overlay retained `CONDITION_UNAVAILABLE` while the central condition text showed “partly cloudy” from the nearest 4:00 PM synthetic sample. The product owner accepted this mismatch as a documented limitation assigned to SG.12; no center-readout behavior change is claimed by SG.11e.
**Evidence:** Cycle 106 closeout `.codex/history/106-sg11e-storm-fallback-events.md`; installed captures, hierarchy dumps, focused mapper/replay checks, commands, observations, owner disposition, and limits `.codex/test-artifacts/106-sg11e-storm-fallback-events/README.md`; archived plan `.codex/history/plans/106-sg11e-storm-fallback-events.md`.

### SG.11f — Map additional weather events — DONE

**Objective/scope:** Extend the established SG.11a typed mapping and bounded debug route for the Additional events row: DRY_THUNDERSTORM, TROPICAL_STORM, WATERSPOUT, FROST, EXTREME_HEAT, EXTREME_COLD, DUST_SANDSTORM, VOLCANIC_ASH. Add only documented provider-code assignments that support this row; preserve prior rows, recorded compatibility, and the shared selection contract.
**Depends on:** SG.11e.
**Exit:** Each supplyable row ID survives canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Every row state is inspected in an installed capture with exact fact, symbol ID, name, command, and limitation; directly affected selection is checked. Existing rows and legacy replay remain valid. Reconcile the complete 47-ID mapping, precedence order, and exact-code ledger at series exit.
**Slice board(s):** The owner-approved SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` govern this row and the shared rules it uses. Approval was recorded on 2026-11-04; the owner approved the six-row execution split on 2026-11-04. The retained boards cover all rows; each slice accepts only its named row and directly affected shared behavior.
**Recorded limitation, 2026-11-05:** During the scoped 9:15 AM scrub from the 10:00 AM `WATERSPOUT` state, selection and the timeline label moved together, while the event overlay retained the preceding 9:00 AM `TROPICAL_STORM` fact and the center readout reported “No supplied weather event.” This related scrub-time projection/readout disagreement remains assigned to SG.12; no readout redesign is claimed here.
**Evidence:** Cycle 107 closeout `.codex/history/107-sg11f-additional-weather-events.md`; eight installed captures, paired hierarchies, timeline scrub evidence, full 47-ID ledger and precedence reconciliation, commands, observations, and limits `.codex/test-artifacts/107-sg11f-additional-weather-events/README.md`; archived plan `.codex/history/plans/107-sg11f-additional-weather-events.md`.

### SG.12 — Refactor the central selected-forecast readout — DONE

**Objective/scope:** Recompose the central readout as one legible hierarchy for loaded location with a small adjacent location-picker trigger, temperature tightly paired with the matching reusable WA weather symbol from SG.11a–f's typed selected-fact projection, supported Sun/Moon rise and set times, and daily high/low. Remove the lower magnifier and gear icons; the loaded-location label remains noninteractive, and its adjacent trigger opens the existing picker. Omit the central condition word and duplicate weekday/time line; the timeline retains the selected day/time, while accessible semantics and the forecast browser retain condition text. Show an honest unavailable state when no supported selected-time event can be claimed, and disclose original source time for eligible carried conditions. Refine lower precipitation-chance and wind-speed indicators around prominent numeric values and compact cues. Cover the catalog route and directly affected compact layout while preserving the scene, timeline, source/provenance labels, and screen-reader forecast meaning. Add no event mapping, new symbol artwork, provider endpoint, unit policy, or other navigation behavior.
**Depends on:** SG.5, SG.10, SG.11f, and WA.1–WA.6.
**Visual conditions:** The owner-approved `.codex/concepts/SG.12-center-readout-ui-artwork-board.svg`, titled “Central Selected Forecast Readout,” is the visual acceptance target for successful SG.12 completion. It defines the full hierarchy at Pixel 3 XL portrait and a compact viewport, including representative clear/cloudy, precipitation, carried-condition, and unavailable states. Installed review compares these states with that board and inspects selected-time updates, symbol/accessible-name agreement, Sun/Moon times, high/low and lower-indicator readability, atmospheric-scene legibility, timeline responsiveness, and central gesture ownership. Broader RTL, TalkBack, and system matrices remain dedicated TEST work.
**Exit:** Installed captures meet the visual acceptance target in `.codex/concepts/SG.12-center-readout-ui-artwork-board.svg` for the approved regular and compact readout hierarchy, close temperature/symbol pairing, supported Sun/Moon event times, high/low, and legible numeric precipitation/wind indicators with a blank middle slot between them. The selected symbol and accessible condition name agree with the same supported selected-time event supplied by SG.11a–f; unavailable or source-time-disclosed carry-forward conditions do not imply a newly supplied fact. The timeline visibly carries selected day/time; location, temperature, high/low, event times, provenance, and accessible condition meaning remain accurate. Directly affected swipe/tap and timeline checks preserve selected-time authority, responsiveness, and gesture ownership. The bounded owner decision to omit the visible center condition word is reconciled with the experience specification. Scoped evidence records exact installed states, interactions, commands, limitations, and final diff.
**Slice board(s):** The product owner approved the UI artwork board `.codex/concepts/SG.12-center-readout-ui-artwork-board.svg` (PNG alongside), titled “Central Selected Forecast Readout,” on 2026-11-04 under the earlier SG.11 label; the composition is unchanged and the artifact label/path now identify SG.12. The owner confirmed on 2026-11-05 that this board is the visual acceptance target for successful completion of SG.12. After the 48-ID event mapping moved to SG.11a–f, the owner approved the replacement behavior board `.codex/concepts/SG.12-center-readout-behavior-board.svg` (PNG preview and supporting `.md` contract alongside) on 2026-11-05. It uses `.codex/concepts/SG.12-pixel-main-screen-reference.png` as an explicitly labeled installed current-state reference. The behavior board governs center-readout states and selected-condition/source disclosure; the approved artwork board governs visual acceptance, regular/compact composition, and the blank lower-indicator slot.
**Owner sequencing decision:** On 2026-11-04, the owner separated the 48-ID typed mapping from the center readout. SG.11a became NEXT; SG.11b–f and SG.12 followed it. The center boards and existing cycle ID `101-sg11-center-readout` remain with the center slice; the cycle ID is historical and independent of its new SG.12 roadmap ID. No mapping implementation is authorized by this center slice.
**Correction opened 2026-11-05:** Owner review confirmed the debug-only top source bar is not product UI and found the approved inline lower-indicator cues were omitted from cycle 101. Cycle 108 completes the already-approved lower-indicator board composition and recaptures a clean product comparison. Cycle 101 remains historical; correction scope and evidence are tracked in `.codex/plans/108-sg12-board-acceptance-correction.md` and `.codex/test-artifacts/108-sg12-board-acceptance-correction/`.
**Evidence:** Cycle 101 installed evidence `.codex/test-artifacts/101-sg11-center-readout/README.md`; closeout `.codex/history/101-sg11-center-readout.md`; archived plan `.codex/history/plans/101-sg11-center-readout.md`. Cycle 108 correction installed evidence `.codex/test-artifacts/108-sg12-board-acceptance-correction/README.md`; closeout `.codex/history/108-sg12-board-acceptance-correction.md`; archived plan `.codex/history/plans/108-sg12-board-acceptance-correction.md`.

### SG.13 — Align the central readout with the skyline landmark — DONE

**Objective/scope:** Position the complete SG.12 central readout as one block until the Chicago text baseline aligns with the top edge of the third tallest background building in the board's Proposed Placement panel. Add selected weather-event text between temperature and sunrise at the location label's font weight; this owner-approved SG.13 change supersedes SG.12's omission of the visible condition word. Preserve other internal hierarchy, spacing, facts, semantics, picker, scene and timeline. Move the precipitation/wind cue-value row and blank center slot up by exactly 23 dp as a separate group; check the compact affected layout.
**Depends on:** SG.12.
**Exit:** Installed regular Pixel 3 XL capture shows Chicago's baseline aligned with the board's third-tallest-building cap, selected event text between temperature and sunrise with the specified weight, and the whole lower indicator row raised by 23 dp while the timeline stays fixed; compact capture remains legible without collisions. Focused selected-time, event-text, picker and directly affected gesture checks retain existing behavior and source honesty. Actual screenshots and scoped verification limits are recorded.
**Slice board(s):** The product owner approved the combined behavior and UI artwork board `.codex/concepts/SG.13-skyline-anchored-center-readout.svg` (PNG preview), current clean installed reference `.codex/concepts/SG.13-current-main-screen-reference.png`, and board contract `.codex/concepts/SG.13-skyline-anchored-center-readout.md` on 2026-11-05 as shown. Approval covers the board's third-tallest-building anchor, new selected weather-event text row, separate exact 23 dp lower-indicator lift, fixed scene/timeline, and regular/compact affected states. It governs scope and visual acceptance; installed verification remains required.
**Evidence:** Cycle 109 installed evidence `.codex/test-artifacts/109-sg13-skyline-anchored-readout/README.md`; closeout `.codex/history/109-sg13-skyline-anchored-readout.md`; archived plan `.codex/history/plans/109-sg13-skyline-anchored-readout.md`.

### SG-missing-eleventh-hour-data — Resolve the late-day hourly readout gap — NEXT

**Objective/scope:** Resolve the demonstrated 11:30 PM–midnight presentation gap for a selected local date with a supported final hourly point. Propose extending that date's final representative display cell to its local midnight boundary, without carrying a source fact into the next local date; retain exact selected-time and source-valid-time labels. Keep temperature and wind text independent of condition-track availability, and precipitation chance tied to its own supported point. Update the SG.1 representative-display contract only after the owner approves the objective-specific board. Preserve genuinely absent, empty, unsupported, isolated, or sparse point values as unavailable under their existing rules.
**Depends on:** SG.9 and SG.13.
**Exit:** The owner-approved boundary contract and controlling scene specification agree. For a supplied final hourly point, installed scrubbing at 11:29, 11:30, 11:36, 11:59, and the next local midnight keeps the selected timestamp fixed and shows the applicable local-date source time and supplied temperature, precipitation chance, wind, and condition/scene meaning without a false missing-data claim. The next date takes over at midnight. Missing final-hour and independently missing field cases remain unavailable without invented zero or cross-date carry. Daily high/low, forecast facts, timeline gestures, responsiveness, and readable labels remain intact. Record focused installed screenshots, scoped interaction observations, exact commands, and limitations; run automated tests only with separate owner authorization.
**Evidence:** Planned `.codex/test-artifacts/115-sg-missing-eleventh-hour-data/README.md`, `.codex/history/115-sg-missing-eleventh-hour-data.md`, and archived plan `.codex/history/plans/115-sg-missing-eleventh-hour-data.md`.
**Slice board(s):** Draft objective-specific behavior contract `.codex/concepts/SG-missing-eleventh-hour-data-behavior-board.md` and draft UI artwork `.codex/concepts/SG-missing-eleventh-hour-data-ui-artwork-board.svg` with PNG preview alongside; product-owner review and approval are pending. A prior installed screenshot and this roadmap text are diagnostic inputs, not approved boards. The board package must resolve the SG.1 half-hour limit and show late-day supplied, independently missing, and midnight-switch states before the cycle plan can be finalized or activated.

### SG.6 — Prove theme-specific spatial composition — DEFERRED

**Objective/scope:** Build one bounded second theme with a materially different spatial composition to prove scene tracks remain free of fixed positions and planet/sky assumptions. Reuse the same semantic forecast tracks while the theme supplies its own background, layer placement, paths, ordering, and relevant asset variants.
**Depends on:** SG.1, SG.2, SG.3, SG.4, and SG.9.
**Visual conditions:** The approved board defines both themes at portrait phone and compact viewport sizes, plus applicable large-font, RTL, and reduced-motion states. Installed paired captures use the same selected instants and forecast events.
**Exit:** The same selected-time scene plan renders in both themes with matching event times and forecast meaning but distinct theme-owned composition; missing theme assets follow the declared fallback; theme changes do not change selected time or forecast facts. This is an architecture proof, not a complete theme catalog or theme-selection UI.
**Slice board(s):** Combined flow/logic and UI artwork board pending owner approval: contrasting-theme composition at `.codex/concepts/SG.6-contrasting-theme-proof.png`.
**Evidence:** At planning, allocate paired installed captures, theme manifests, and focused history paths.
**Re-entry condition:** The product owner explicitly restores the second-theme architecture proof to the execution path after the current higher-priority scene-generation work is complete; update ordering and status before planning it.

### SG.7 — Bound scene planning, loading, lifecycle, and performance — PLANNED

**Objective/scope:** Measure and bound deterministic scene-plan generation, asset preloading/cache behavior, renderer work, lifecycle/background behavior, and static/reduced-motion policy after representative event tracks and two themes exist. Do not pre-render every animation frame or claim speed without measurement.
**Depends on:** SG.3, SG.4, SG.6, and SG.9.
**Visual conditions:** Profile the same representative states at the Pixel 3 XL reference viewport and one compact viewport; include foreground, background/offscreen, and reduced-motion states. Record large-font and RTL applicability for scene/text composition.
**Exit:** Focused reference-device evidence records plan-build and asset-load latency, cache invalidation on catalog/location/theme-version changes, timeline responsiveness during scrub, lifecycle behavior, and static/reduced-motion meaning. Define and meet the bounded budget in the cycle plan or record an evidenced blocker and follow-up.
**Slice board(s):** Flow/logic and UI artwork board pending owner approval: scene quality/lifecycle at `.codex/concepts/SG.7-scene-runtime-quality.png`.
**Evidence:** At planning, allocate reference-device logs, captures, exact measurement method, and closeout paths.

### SG.8 — Add lunar rise/set events and accurate phase scenes — DONE

**Objective/scope:** Add location- and local-date-specific Open-Meteo `moonrise`, `moonset`, and `moon_phase` data to the shared catalog/scene path, then show the moon's rise/set behavior and correct lunar stage as the selected forecast time changes. Preserve event instants, local-date/timezone semantics, replay compatibility, missing-value honesty, and the selected-time authority. Use the provider's phase fraction to distinguish new moon, waxing crescent, first quarter, waxing gibbous, full moon, waning gibbous, last quarter, and waning crescent. Enlarge the Moon cue near supported horizon crossings using the Sun cue's existing scale emphasis, with smaller appearance high on the illustrative path; treat size as visual art direction, not an actual angular-size or altitude claim. Do not claim an exact phase-transition time, lunar azimuth/elevation, or visible orientation from daily phase/rise/set values alone. Keep clouds/weather composition and readable forecast facts intact.
**Depends on:** SG.1, SG.2, SG.3, and SG.9.
**Visual conditions:** The approved UI artwork board defines lunar phase appearance, waxing/waning distinction, rise/set horizon crossings, selected-time changes, independently unavailable event/phase states, and cloud/weather overlap. Installed review covers Pixel 3 XL portrait and compact viewport, default/1.3 font scale where applicable, and reduced-motion meaning; verify timeline responsiveness, gesture ownership, and readable phase/event text. RTL, TalkBack, and broad system matrices remain with dedicated TEST work.
**Exit:** Focused installed states use provider-backed daily moonrise/moonset instants and phase values for the selected local date; replay preserves them; approved phase cues and labels progress through all eight stages with waxing/waning direction correct; the Moon cue is visibly larger near its supported horizon crossings and smaller high on its illustrative path, matching the Sun cue's scale emphasis without implying measured lunar size or altitude; null event or phase values create no invented claim; the moon cue does not imply unsupported position precision or orientation; scene changes remain deterministic from selected time and preserve forecast facts, responsiveness, and readable phase/event semantics.
**Slice board(s):** Product owner approved both boards as written on 2026-11-02: behavior board `.codex/concepts/SG.8-lunar-events-and-phases-behavior-board.svg` with detailed contract `.codex/concepts/SG.8-lunar-events-and-phases-behavior-board.md` and PNG preview, plus UI artwork board `.codex/concepts/SG.8-lunar-events-and-phases-ui-artwork-board.svg` with PNG preview. Approval covers the API-to-catalog/replay/shared-scene path, daily phase semantics, local-date/DST handling, the eight equal-width phase bins, chronological cross-date rise/set pairing with unresolved spans left without a Moon-position claim, independent missing-value behavior, neutral phase-cycle marker and named stage labels without hemisphere-specific visible orientation, cloud overlap, and Sun-like cue scaling that is largest near supported horizon crossings and smaller high on the illustrative path. Scale is visual art direction and does not claim actual angular diameter or measured altitude. This separate outcome was requested on 2026-10-31; cycle 093 completed execution after the owner resolved the provider-row midnight boundary and approved responsive compact-layout refinement on 2026-11-03.
**Evidence:** Cycle 093 plan `.codex/plans/093-sg8-lunar-events-and-phases.md`, evidence directory `.codex/test-artifacts/093-sg8-lunar-events-and-phases/`, and closeout `.codex/history/093-sg8-lunar-events-and-phases.md`.

## ATM — Earlier matrix-driven scene sequence (superseded)

The following unstarted slices are retained as stable roadmap IDs for traceability. Their fixed abstract-landscape mapping is replaced by SG's theme-neutral scene tracks and theme-owned compositions. Completed ATM.0–ATM.6 and DFM.1–DFM.3 are unchanged.

### ATM.8 — Map clear, cloudy and day/night matrix states — SUPERSEDED

**Objective/scope:** Original bounded outcome: map clear/cloudy/day-night matrix states onto the retained abstract landscape.
**Depends on:** None; this outcome is retired.
**Exit:** The supersession and successor mapping are recorded. No ATM.8 implementation remains.
**Evidence:** Superseded at the 2026-10-29 20:47:50 America/Chicago roadmap jump point by SG.1–SG.3; successor specification: `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`.

### ATM.9 — Map rain and snow matrix variants — SUPERSEDED

**Objective/scope:** Original bounded outcome: map matrix rain/snow variants onto the retained abstract landscape.
**Depends on:** None; this outcome is retired.
**Exit:** The supersession and successor mapping are recorded. No ATM.9 implementation remains.
**Evidence:** Superseded at the roadmap jump point by SG.4; see `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`.

### ATM.10 — Map storm likelihood and warning variants — SUPERSEDED

**Objective/scope:** Original bounded outcome: map storm likelihood and debug-warning variants onto the retained abstract landscape.
**Depends on:** None; this outcome is retired.
**Exit:** The supersession and successor mapping are recorded. No ATM.10 implementation remains.
**Evidence:** Superseded at the roadmap jump point; future selected event-family boards are planned under SG.5. Official alerts remain out of scope.

### ATM.7 — Effects policy, lifecycle and performance gate — SUPERSEDED

**Objective/scope:** Original bounded outcome: define effects policy, lifecycle coordination, static fallback, and performance review for the earlier atmosphere renderer.
**Depends on:** None; this outcome is retired.
**Exit:** The supersession and successor mapping are recorded. No ATM.7 implementation remains.
**Evidence:** Superseded at the roadmap jump point by SG.7, which measures the scene-generation runtime after representative themes and event tracks exist.

## ENV — Earlier environment-pack sequence (superseded)

The earlier ENV slices treated geography as a separate interchangeable backdrop. The SG theme model now owns the complete composition, which may or may not depict a geographic environment. Specific landscape themes remain future candidates, but will be scoped under SG after its composition contract is proven.

### ENV.1 — Environment contract and Neutral baseline — SUPERSEDED

**Objective/scope:** Original bounded outcome: place the current landscape behind a Neutral environment-pack contract.
**Depends on:** None; this standalone environment contract is retired.
**Exit:** The supersession and successor workstream are recorded. No ENV.1 implementation remains.
**Evidence:** Superseded at the roadmap jump point by the theme-composition boundary in SG.1–SG.2.

### ENV.2 — Alpine environment pack — SUPERSEDED

**Objective/scope:** Original bounded outcome: add an Alpine landscape pack.
**Depends on:** None; this standalone pack slice is retired.
**Exit:** The supersession and successor workstream are recorded. No ENV.2 implementation remains.
**Evidence:** Superseded at the roadmap jump point. An Alpine-like scene may be proposed later as a theme-specific SG slice after SG.6 validates theme composition.

### ENV.3 — Coastal environment pack — SUPERSEDED

**Objective/scope:** Original bounded outcome: add a Coastal landscape pack.
**Depends on:** None; this standalone pack slice is retired.
**Exit:** The supersession and successor workstream are recorded. No ENV.3 implementation remains.
**Evidence:** Superseded at the roadmap jump point. A Coastal-like scene may be proposed later as a theme-specific SG slice after SG.6 validates theme composition.

### ENV.4 — Cross-pack transitions and acceptance matrix — SUPERSEDED

**Objective/scope:** Original bounded outcome: validate transitions among Neutral, Alpine, and Coastal environment packs.
**Depends on:** None; this standalone transition slice is retired.
**Exit:** The supersession and successor workstream are recorded. No ENV.4 implementation remains.
**Evidence:** Superseded at the roadmap jump point; SG.6 proves shared semantic tracks across two materially different theme compositions.

### ENV.5 — Additional environment packs — SUPERSEDED

**Objective/scope:** Original bounded outcome: consider additional geographic environment packs.
**Depends on:** None; this candidate-pack slice is retired.
**Exit:** The supersession and successor workstream are recorded. No ENV.5 implementation remains.
**Evidence:** Superseded at the roadmap jump point. Any future additional theme or environment requires a newly bounded, owner-approved SG roadmap slice.
