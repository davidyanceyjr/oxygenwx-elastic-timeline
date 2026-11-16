# Cycle plan — 144-ox05-depth-ordering

State: CLOSED
Plan maturity: FINAL
Cycle ID: `144-ox05-depth-ordering`
Roadmap items: OX-05
Objective: Prove and visually review absolute First City depth ordering on the installed primary catalog composition, using a deterministic debug-only synthetic astronomy fixture for the approved celestial and moderate-rain overlaps while preserving weather semantics and the owner-directed world-layer model.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "144-ox05-depth-ordering",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-05",
      "depends_on": [
        "OX-03"
      ],
      "status": "DONE",
      "exit": "Focused ordering and fixture-projection assertions pass. On the installed primary catalog composition, capture the approved celestial-overlap state and moderate-rain state from the deterministic debug fixture, with the synthetic provenance label visible; no cross-depth inversion. Provide scoped proof or an explicit recorded disposition. Visual claims require installed evidence.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.dot",
              "sha256": "0cb0b81c3805b5d9dcb9f04974e95eb27fdee75ff6aa1ae5a3ae2c554bae9b24"
            },
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.svg",
              "sha256": "5e35bceb1ab4d0106d44ea1a3bf20511245cea59120e161514539264b4079d77"
            },
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.png",
              "sha256": "681d47db1e65880b4fb4a49fde44952c782f86ef84786bee36abc339b580cead"
            },
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.md",
              "sha256": "b44f06dffd277b06dc04063ec7748b72de283ca5dd3006f47152a7d603031498"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.svg",
              "sha256": "69677f4de05e388354b8213f93dd127b42cae3837be00c9f329623e93c6f359c"
            },
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.png",
              "sha256": "ac10fb28ef5599332fcd9450e7c7a83c95cbce0419f6ce7e647b5365a7360e06"
            },
            {
              "path": ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.md",
              "sha256": "be851b872e3aeb6bc4e0c36d00334afa3a8f2e4bf7fe75835b76530a40d63817"
            }
          ]
        }
      },
      "outcome": "Implemented the approved depth ordering and fixed debug fixture; focused JVM and OX-05 composition checks passed, and both exact installed MainActivity states were captured with the authored-synthetic provenance label. The installed browser driver selected the Oct 29 daily row and asserted 12:00:00 PM CDT with rain. The one failure in the broader scoped renderer class is the previously owner-accepted separate state-based overload limitation documented in the evidence README.",
      "evidence": [
        ".codex/test-artifacts/144-ox05-depth-ordering/README.md",
        ".codex/test-artifacts/144-ox05-depth-ordering/ox05-celestial-overlap-installed.png",
        ".codex/test-artifacts/144-ox05-depth-ordering/ox05-moderate-rain-installed.png",
        ".codex/test-artifacts/144-ox05-depth-ordering/ox05-installed-browser-driver.xml",
        ".codex/test-artifacts/144-ox05-depth-ordering/ox05-depth-ordering-jvm-tests.xml",
        ".codex/test-artifacts/144-ox05-depth-ordering/ox05-debug-sidecar-jvm-tests.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-16",
    "summary": "Completed OX-05 depth ordering with the owner-approved debug-only synthetic astronomy fixture and authored sidecar. The First City physical order is explicit across Background, Middle-Ground, and Foreground; installed primary-screen evidence covers the exact celestial-overlap and Oct 29 moderate-rain states with the provenance label visible. The owner-selected existing-browser route selected the local-noon row exactly. The separate accepted state-based renderer test limitation is documented in cycle evidence.",
    "verification": [
      "python scripts/dev.py workflow — passed at CLOSEOUT.",
      "git diff --check — passed.",
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest --tests com.oxygen.weather.timeline.data.debug.DebugOx05AstronomySidecarTest — passed.",
      "./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.FirstCitySceneRenderingTest — 6 passed, 1 failed: selectedLocalTimeProducesStableRainAndStormCloudPoses at FirstCitySceneRenderingTest.kt:168 (rain banks should follow selected local time); the separate state-based overload limitation was owner-accepted and recorded.",
      "./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.Ox05InstalledForecastBrowserDriverTest — passed on Pixel 3 XL, 1/1; selected the Oct 29 row and asserted 12:00:00 PM CDT with rain.",
      "python scripts/dev.py run --serial 8AKY0M32X — debug build, install, and launch passed.",
      "Installed review — captured and inspected the exact Oct 28 celestial-overlap and Oct 29 moderate-rain states on Pixel 3 XL 8AKY0M32X; screenshots and limits are recorded under .codex/test-artifacts/144-ox05-depth-ordering/."
    ],
    "evidence_directory": ".codex/test-artifacts/144-ox05-depth-ordering/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "144-ox05-depth-ordering",
  "slice_ids": [
    "OX-05"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.dot",
    ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.svg",
    ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.png",
    ".codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.md",
    ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.svg",
    ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.png",
    ".codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.md"
  ],
  "completed_gates": [
    "OX-05_behavior_board_prepared",
    "OX-05_ui_artwork_board_prepared",
    "OX-05_behavior_board_approved",
    "OX-05_ui_artwork_board_approved",
    "OX-05_precipitation_depth_selected",
    "OX-05_substantive_plan_complete",
    "OX-05_plan_ready_for_owner_activation",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-05_slice_exit_met"
  ],
  "decisions": [
    "The product owner chose option 1: keep the approved scope and continue through the existing forecast browser with a narrowly scoped UI-test or manual interaction to select its exact local-noon row."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented the Background, Middle-Ground, and Foreground order contract, split cloud passes, preserved named compositing passes, and kept one scene-wide Foreground precipitation field.",
    "Implemented the fixed SIMULATED OX-05 recording, authored-synthetic astronomy sidecar, fixture-only debug action, provenance label, and scoped projection/storage assertions; fixed the demonstrated recording codec round-trip check.",
    "The cycle resumed from its earlier PAUSED / EXECUTE checkpoint after the owner selected option 1; the approved boards and plan scope were preserved.",
    "Captured the exact Oct 28 12:00 CDT celestial-overlap state on the primary catalog screen after the approved #DEBUG / #PICKER entry, with the provenance label visible.",
    "The owner-selected option 1 was completed: the existing daily forecast browser selected the Oct 29 local-noon anchor exactly.",
    "Captured and inspected both approved exact primary-screen states with the synthetic provenance label visible.",
    "The focused installed browser interaction test passed after selecting the Oct 29 row and asserted Selected 12:00:00 PM CDT with rain.",
    "Updated execution evidence with installed screenshots, focused test reports, verification outcomes, and limits.",
    "Closed slice OX-05 as DONE with the separately accepted out-of-scope overload limitation recorded.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/144-ox05-depth-ordering/README.md",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-celestial-overlap-installed.png",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-location-search-open.png",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-debug-catalog-picker.png",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-moderate-rain-installed.png",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-installed-browser-driver.xml",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-depth-ordering-jvm-tests.xml",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-debug-sidecar-jvm-tests.xml",
    "app/src/androidTest/java/com/oxygen/weather/timeline/ui/Ox05InstalledForecastBrowserDriverTest.kt",
    ".codex/history/144-ox05-depth-ordering.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/144-ox05-depth-ordering.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/ActivityVariantDelegate.kt",
    "app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/CatalogPickerDelegate.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugCatalogActivity.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugOx05DepthOrderingFixture.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugOx05AstronomySidecar.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/FirstCityThemeTest.kt",
    "app/src/testDebug/java/com/oxygen/weather/timeline/data/debug/DebugOx05AstronomySidecarTest.kt",
    "app/src/androidTest/java/com/oxygen/weather/timeline/ui/FirstCitySceneRenderingTest.kt",
    "app/src/androidTest/java/com/oxygen/weather/timeline/ui/Ox05InstalledForecastBrowserDriverTest.kt",
    ".codex/test-artifacts/144-ox05-depth-ordering/README.md",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-celestial-overlap-installed.png",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-moderate-rain-installed.png",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-installed-browser-driver.xml",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-depth-ordering-jvm-tests.xml",
    ".codex/test-artifacts/144-ox05-depth-ordering/ox05-debug-sidecar-jvm-tests.xml"
  ],
  "next_action": "Inspect the final source and roadmap diff, run git diff --check and python scripts/dev.py workflow, then close this cycle into history with exact verification and evidence."
}
```
<!-- cycle-checkpoint:end -->

## Slice intake

The owner selected OX-05 as the sole global `NEXT` for cycle 144 board preparation after OX-04 closed. Its dependency OX-03 is `DONE`. OX-05 is the active slice in this cycle. After the original deterministic catalog route proved unable to supply the approved solar/lunar overlap inputs, the owner directed a bounded plan and board revision. The owner approved the revised scope, behavior board, and UI artwork board on 2026-11-16; refreshed fingerprints are recorded in the cycle manifest.

**Objective:** Prove the existing First City follows absolute Background → Middle-Ground → Foreground ordering on the installed primary catalog composition while retaining established weather-field and compositing semantics. A deterministic debug-only synthetic fixture supplies the approved Sun/Moon overlap and moderate-rain review states through an explicit astronomy sidecar.

**Exit:** Focused ordering and fixture-projection assertions pass. On the installed primary catalog composition, capture the approved celestial-overlap state and moderate-rain state from the deterministic debug fixture, with the synthetic provenance label visible; no cross-depth inversion. Provide scoped proof or an explicit recorded disposition. Visual claims require installed evidence.

**Scope boundary:** Review the existing First City composition and its ordered renderer path. Map physical content into ordered sublayers: Background contains the world/outer-space backdrop, stars, Sun, Moon, overlapping Background clouds, and background city architecture; Middle-Ground contains atmosphere and overlapping clouds; Foreground contains near architecture, canopy, window lights, and the single scene-wide precipitation field after near buildings. Clouds may occupy Background and Middle-Ground. Keep `TIME_WASH` and `READOUT_QUIET_ZONE` as named compositing passes outside the physical depth groups, preserving their current coverage and placement relative to city layers. Use only the minimal star field shown in the approved artwork; preserve existing cue art, city geometry, and exact pixel alignment. The precipitation field is not split into near and distant particles. Preserve the OX-03 shared composition decisions and OX-04 evidence/source-time semantics. Add only a fixed debug fixture activation entry, authored-synthetic astronomy sidecar, debug-only scene-reading adapter, and provenance label to reach the two approved states on the primary `CatalogTimeLabScreen`.

**Fixture contract:** Use `CatalogLocation(LocationId("debug:ox05-depth-review-chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))`, dataset ID `ox05-depth-review-synthetic-astronomy-v1`, SIMULATED storage, and fixed `asOf=2026-10-28T12:00:00Z`. Supply CLEAR / 0% cloud at `2026-10-28T17:00:00Z` and RAIN / 88% cloud at `2026-10-29T17:00:00Z`. The debug-source-set sidecar, with origin `AUTHORED_SYNTHETIC_REVIEW_INPUT`, supplies sunrise 06:00, sunset 18:00, moonrise 04:30, moonset 17:30, and phase 0.50 on both dates in `America/Chicago`. The dedicated OX-05 debug action passes this fixed identity directly to the existing `CatalogActivationCoordinator.requestDebugCatalog` path; it does not use the generic saved-location lookup. The debug-only entry works without saved locations or network access and does not add a saved user location. The adapter applies values only to a copy of the evaluated `SceneReading` for this exact dataset, including local daylight phase, then recomposes `FirstCityComposition` while preserving timeline, selected instant, weather values, and diagnostics. Release remains on a default no-op hook. Show `DEMO DATA · OX-05 authored synthetic astronomy fixture` in the debug overlay. Authored astronomy stays outside `CanonicalCatalog.dailySummaries`, `ProviderDailySummary`, canonical recording persistence, live provider mapping, and non-fixture datasets; do not calculate or infer astronomy.

**Reviewed states:** (1) Celestial overlap: select `2026-10-28T17:00:00Z` (Oct 28, 12:00 CDT), CLEAR / 0% cloud, solar 06:00–18:00 CDT, lunar rise 04:30 / set 17:30 CDT, phase 0.50. Current cue mapping is Sun `(0.500, 0.180)` and Moon approximately `(0.558, 0.187)`. (2) Moderate rain: select `2026-10-29T17:00:00Z` (Oct 29, 12:00 CDT), RAIN / 88% cloud, with the same supplied solar/lunar values; retain all sidecar inputs while established rain semantics suppress the Sun. Capture both on the primary `CatalogTimeLabScreen` with the provenance label visible.

**Exclusions:** No semantic element registry or independent element identity system (OX-06); no second world, reusable renderer extraction, new weather interpretation, provider/schema/live-data changes, or unrelated layout changes. Do not change Sun/Moon position, size, phase, or styling; city art, readout, timeline, gestures, and other artwork remain unchanged. Fixture activation and adapter are debug-only. The only new decorative content is the minimal Background star field already shown in the approved artwork; owner-directed cloud layers and clusters follow that artwork. The schematic artwork is a target for review, not installed acceptance evidence.

**Required boards:** Product-owner-approved behavior package `.codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.dot`, `.svg`, `.png`, and `.md`; UI artwork package `.codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.svg`, `.png`, and `.md`. The product owner approved the exact revised packages and bounded plan revision on 2026-11-16; the cycle manifest records each package fingerprint. The owner-selected option B remains: the single precipitation field is in Foreground and crosses in front of near buildings. The boards guide this revised scope and acceptance; installed evidence remains required.

## Board package prepared

- Revised behavior package: `.codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.dot`, `.svg`, `.png`, and `.md`.
- Revised UI artwork package: `.codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.svg`, `.png`, and `.md`.
- The product owner approved both revised packages and the bounded plan revision on 2026-11-16. The cycle manifest records their complete package fingerprints.
- The behavior board places outer space and stars behind Sun → Moon within Background, then nearer Background clouds/architecture; Middle-Ground contains atmosphere and cloud layers; Foreground contains near architecture, canopy, and window lights. The Moon may overlap the Sun, and nearer clouds/world layers may occlude the celestial field. Moderate-rain artwork shows overlapping small-to-medium cloud clusters across Background and Middle-Ground and one scene-wide rain field. `TIME_WASH` and `READOUT_QUIET_ZONE` remain named compositing passes.
- The owner has resolved the world-layer model, cloud form, and Background/Middle-Ground cloud placement, then selected rain option B: the existing single precipitation field is in Foreground and draws in front of near buildings. The UI board records both reviewed options; the selected option is recorded here and in the roadmap.
- The revised boards specify deterministic authored-synthetic astronomy inputs, the debug-only activation and adapter path, and the two primary-route acceptance states. They preserve OX-03 cloud/rain placement and OX-04 evidence semantics. Their artwork is schematic, not installed acceptance evidence.

## Execution plan

### Ordered depth contract

Every physical layer is assigned to one group and one local sublayer. The renderer emits all Background content before Middle-Ground, then all Foreground content; local order is explicit and stable. A lower group's item can never render above an item in a higher group.

| Depth group | Back-to-front sublayers |
|---|---|
| Background | World/outer-space backdrop → stars → Sun (`TIME_CUE`) → Moon (`LUNAR_CUE`) → Background clouds → background city architecture |
| Middle-Ground | Atmosphere → Middle-Ground clouds |
| Foreground | Near architecture → canopy → window lights → single precipitation field |

The Sun precedes the Moon so the Moon can overlap it. Nearer cloud/world layers can occlude either cue. The selected option B places the single precipitation field in Foreground after near buildings; it crosses in front of those buildings across the scene. `TIME_WASH` and `READOUT_QUIET_ZONE` remain separate named operations and do not give physical layers an exception to the depth rule.

### Implementation steps

1. Keep the depth model local to First City. Add an explicit group and local order to the existing First City layer contract in `FirstCityComposition.kt` and `FirstCityTheme.kt`; derive the composed physical draw sequence from that contract. Do not create the OX-06 element registry or a reusable cross-theme renderer.
2. Update the composition renderer in `AtmosphericScene.kt` to follow the accepted sequence. Separate Background and Middle-Ground cloud passes while preserving one supported cloud decision and the board's overlapping small-to-medium clusters. Add the minimal star field in the approved Background position. Keep the split city architecture draws pixel-aligned to their existing asset.
3. Place the existing single precipitation field after near architecture in Foreground. Preserve its scene-wide coverage and existing origin: visible-cloud rain starts at the visible cloud base; eligible precipitation without visible cloud cover retains its established field origin. Do not add a near/far particle split or infer weather from the artwork.
4. Keep `TIME_WASH` and `READOUT_QUIET_ZONE` outside the physical order and preserve their current visual coverage/placement relative to the city layers. Preserve the primary composition route and OX-03 shared decision boundary; keep readout, timeline, and gesture ownership unchanged. Add only the approved debug fixture activation, debug-source-set sidecar, default no-op variant hook, fixture-scoped presentation adapter, and DEMO DATA provenance label.
5. Add focused ordering assertions to the existing `FirstCityThemeTest` for monotonic group order, local cue/cloud order, and option B precipitation after near buildings. Extend `FirstCitySceneRenderingTest` for Moon-over-Sun/nearer-cloud occlusion and moderate-rain option B overlap. Add a focused debug-sidecar projection check for dataset scoping, exact event values, cue positions, selected-instant preservation, and unchanged weather values/diagnostics.

### Functional invariants

- Keep the exact selected instant authoritative and preserve OX-04 support, source-valid time, carried/unavailable-state, and probability semantics.
- Preserve the OX-03 cloud-origin rule for visible-cloud rain and the established no-visible-cloud field origin.
- Cloud cover remains within the product's top-33% scene bound, including edges and effects. Moderate-rain artwork shows overlapping small-to-medium clusters across the width in Background and Middle-Ground, with one field spanning the scene below the visible cloud base.
- Procedural precipitation remains illustrative; it does not claim an amount or rate. No cloud or rain is synthesized from unsupported weather evidence. The debug fixture's astronomy values are explicitly authored synthetic rendering inputs, never inferred from weather or missing data.
- Keep essential forecast facts readable as text, the readout quiet zone's current effect, responsive scrubbing, and existing timeline gesture ownership. This slice changes draw order, approved decorative star/cloud layers, and the bounded debug-only route and presentation adapter specified above.

### Verification and authorization

The owner authorized execution of this bounded plan, including the focused order and fixture-projection test changes and targeted verification. Run `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest --tests com.oxygen.weather.timeline.data.debug.DebugOx05AstronomySidecarTest` and `./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.FirstCitySceneRenderingTest`. Record exact outcomes and limitations. Keep verification limited to these targets; do not use the project-wide `test` or `check` command for this slice.

After the exact Oct 29 state could not be reached by a manual timeline drag, the owner chose to keep scope and use the existing browser's local-noon row. The focused installed interaction check is `./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.Ox05InstalledForecastBrowserDriverTest`. It activates the fixture through the dedicated debug picker, invokes the timeline's existing Browse forecast values accessibility action, selects Oct 29, and asserts the primary timeline reports exactly `12:00:00 PM CDT` with rain. This check is only the approved browser route; it does not establish a broad accessibility or system matrix.

For installed visual acceptance, recheck the connected device with `adb devices -l`, then use the approved debug build/run route to load the fixed fixture from `#DEBUG` and capture the exact Oct 28 celestial state on the primary `CatalogTimeLabScreen`. For the exact Oct 29 moderate-rain state, use the owner-approved driver above to select the existing Daily forecasts row at its local-noon anchor. Both captures are from the primary screen and show the synthetic DEMO DATA label. Inspect the selected-time composition: the Moon overlaps the Sun with nearer clouds/city layers able to occlude cues; moderate rain shows full-width overlapping clouds and option B precipitation crossing in front of near buildings. No broad interaction or accessibility matrix is in scope. An X-display board or build alone is not installed evidence. The Pixel 3 XL `8AKY0M32X` was attached during plan review; confirm it again before execution.

Before the owner-approved revision, an earlier execution attempt found that the default debug catalog picker had no saved locations and the deterministic catalog lacked the authored astronomy needed for the cue overlap. The fixed fixture, sidecar, and dedicated debug entry resolved that gap. The final focused-check outcomes and installed acceptance evidence are recorded in `.codex/test-artifacts/144-ox05-depth-ordering/README.md`.

### Evidence and closeout targets

- `.codex/test-artifacts/144-ox05-depth-ordering/README.md` records the exact order assertions, commands/results, device, selected states, direct observations, screenshots, and limitations.
- `.codex/test-artifacts/144-ox05-depth-ordering/moon-sun-occlusion.png` and `moderate-rain-option-b.png` are installed captures for the two approved visual states.
- Closeout targets remain `.codex/history/144-ox05-depth-ordering.md` and `.codex/history/plans/144-ox05-depth-ordering.md`. Do not claim visual acceptance without the installed captures and scoped observations.

### Risks and compatibility

The existing composition uses a flat local layer sequence and draws the city asset in background and foreground passes. Keep those passes aligned while assigning groups, and keep optional-layer omission from changing the relative order of remaining layers. Splitting the existing cloud decision into two depth passes must not duplicate weather evidence or cross the top-33% bound. The current renderer has no star draw operation, so add only the minimal Background star marks shown in the approved artwork. The scene-reading, selected-time, support, and rendering-route contracts are unchanged.
