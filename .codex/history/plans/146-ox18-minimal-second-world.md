# Cycle plan — 146-ox18-minimal-second-world

State: CLOSED
Plan maturity: FINAL
Cycle ID: `146-ox18-minimal-second-world`
Roadmap items: OX-18
Objective: Prove a minimal non-city composition can consume the same selected catalog weather decisions as First City without duplicating meteorological interpretation.

## Cycle roster and board approvals

The roster records the approved behavior and UI artwork packages, their exact
fingerprints, scope constraints, dependency order, and observable exit.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "146-ox18-minimal-second-world",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-18",
      "depends_on": [
        "OX-06",
        "OX-04"
      ],
      "status": "DONE",
      "exit": "Side-by-side minimal city and non-city compositions driven by the same catalog selection and semantic decisions, with no duplicated meteorological interpretation. Cloud/precipitation primitive extraction is not required for this early proof.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.dot",
              "sha256": "156646f8be612e9f50ecbcd5e287ecbc7472ca570331f45ec5559fd02e0e0ac5"
            },
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.svg",
              "sha256": "2919d6d3831c85687edaa6ae1a0476fbb3005f86a86ee73af1b46f9bc20f5ed2"
            },
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.png",
              "sha256": "5d6579d386e0a61eb954cf6992104fdef32c8bd6ad612a527330f695a1c68b0e"
            },
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.md",
              "sha256": "61253b4f628ec4ffe75f993302bb8f88305bda6e063c81464386748e717a4d84"
            }
          ],
          "scope": "One selected catalog reading and exact selected instant feed the same minimal shared semantic weather-decision seam for First City and a minimal open-landscape composition; both preserve OX-03/OX-04 evidence meaning and the OX-05/OX-06 First City boundaries.",
          "constraints": [
            "Resolve weather meaning and evidence once; do not copy First City condition, support, carry, or precipitation interpretation into the second world.",
            "Preserve selectedInstant, per-field evidence, sourceValidTime, unavailable states, independent supported facts, and probability meaning.",
            "Keep First City depth order and First City-local semantic identities; do not require a universal registry or public scene schema.",
            "A side-by-side comparison is review evidence, not an in-app split-screen; renderer extraction is not required.",
            "The proposed open landscape does not adopt the Living Urban Park proposal or decide default/selectable-world product role."
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.svg",
              "sha256": "8245f1ca6857ecc9bf76231865e6e069f3e84e8510c46e3b42ed4f564ae837c6"
            },
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.png",
              "sha256": "5f337991719f339c5d21b54b865f5f4db07df5abe3d6f0d768e0be27ebd52db4"
            },
            {
              "path": ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.md",
              "sha256": "b7da52956b23ab79da2abcd8d9b2da95f8e2ed1723b850d75a08b3b75cd72dc3"
            }
          ],
          "scope": "Schematic paired supported-rain comparison of the existing First City and proposed minimal open landscape with the same catalog, exact selected instant, weather decision, evidence support, and source-time meaning.",
          "constraints": [
            "Preserve First City approved appearance, OX-05 cloud/depth/rain relationship, and OX-06 local identities.",
            "Keep the second world sparse and recognizably non-city; do not imply a polished scene pack or a product role.",
            "Keep cloud artwork within the approved upper third; rain marks remain illustrative and do not encode measured rate or amount.",
            "Comparison panes, labels, and selected-time rails are board annotations; add no in-app split-screen, scene picker, interaction, layout, or forecast changes.",
            "Unavailable condition creates no condition-derived marks; eligible carried evidence retains its original source time; independent facts remain."
          ]
        }
      },
      "outcome": "Shared weather decisions now feed both approved compositions; focused behavior checks and installed city/open-landscape captures meet the scoped acceptance. The MainActivity supported-rain capture was re-established with saved Chicago selected, exact SG.4 replay active, and Chicago/57°F/75%/10:30 AM CDT confirmed. No location-name synchronization implementation change was needed.",
      "evidence": [
        ".codex/test-artifacts/146-ox18-minimal-second-world/README.md",
        ".codex/test-artifacts/146-ox18-minimal-second-world/main-supported-rain.png",
        ".codex/test-artifacts/146-ox18-minimal-second-world/main-supported-rain.xml",
        ".codex/test-artifacts/146-ox18-minimal-second-world/catalog-picker-supported-rain.xml",
        ".codex/test-artifacts/146-ox18-minimal-second-world/city-supported-rain-clean-baseline.png",
        ".codex/test-artifacts/146-ox18-minimal-second-world/city-supported-rain.png",
        ".codex/test-artifacts/146-ox18-minimal-second-world/landscape-supported-rain.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-17",
    "summary": "Cycle 146 completed OX-18 within the approved shared-weather-decision scope. One SceneWeatherDecision now feeds First City and the sparse open-landscape composition. Focused behavior checks and installed scene acceptance passed. After the product owner chose option 2, saved Chicago was selected and the exact SG.4 replay was reactivated; the recaptured MainActivity confirms Chicago, 57°F, 75%, and 10:30 AM CDT. The earlier Florence-labeled capture remains preserved as historical evidence. OX-18 is DONE.",
    "verification": [
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.SharedSceneWeatherDecisionTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest — PASS; 10 tests, 0 failures. XML reports retained under .codex/test-artifacts/146-ox18-minimal-second-world/.",
      "python scripts/dev.py contract — PASS.",
      "python scripts/dev.py build — PASS.",
      "python scripts/dev.py run --serial 8AKY0M32X — PASS; debug build installed and launched on Pixel 3 XL.",
      "Installed Pixel 3 XL acceptance — PASS; First City matches its clean baseline, open landscape is sparse with upper-third clouds and illustrative rain, and confirmed MainActivity shows Chicago, DEMO DATA, rain, 57°F, 75%, and selected 10:30 AM CDT. Picker hierarchy confirms active Chicago SG.4 replay.",
      "git diff --check — PASS.",
      "python scripts/dev.py workflow — PASS at ACTIVE/CLOSEOUT before cycle close.",
      "Not run: RTL, TalkBack, broad device matrix, and unrelated system acceptance; these remain outside this design slice."
    ],
    "evidence_directory": ".codex/test-artifacts/146-ox18-minimal-second-world/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "146-ox18-minimal-second-world",
  "slice_ids": [
    "OX-18"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.dot",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.svg",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.png",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.md",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.svg",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.png",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.md"
  ],
  "completed_gates": [
    "Both required OX-18 board packages approved and fingerprinted; owner review questions resolved.",
    "Cycle 146 advanced from BOARDS to DRAFT only after both board approvals were recorded.",
    "Final plan review passed: OX-18 behavior and UI boards, dependencies, invariants, scoped acceptance, authorized verification, evidence targets, risks, exclusions, and exit are recorded.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-18 exit met; no open owner questions remain."
  ],
  "decisions": [
    "The draft uses one selected-time SceneReading and one shared evidence-aware weather-decision output for both world-specific composers; the output contract is fixed, while internal type names remain an implementation choice.",
    "Installed visual evidence is scoped to the approved supported-rain state; carried and unavailable states are semantic parity cases. OX-04 cloud visual subcheck remains NOT RUN and is not reused as proof.",
    "On 2026-11-17, the product owner confirmed temperature remains in the unchanged forecast foreground, outside the shared scene-decision contract; shared decision parity covers only SceneReading fields.",
    "Temperature remains in the unchanged forecast foreground and outside the shared scene-decision contract; shared weather-decision parity covers only SceneReading fields.",
    "On 2026-11-17, the product owner explicitly requested execution of cycle 146 via oxygen-execute-plan; this supersedes the planning-only authorization recorded at final plan review and authorizes only the implementation and verification specified by this plan.",
    "The existing replay provenance badge overlaps the supported-rain cloud band. Use a debug-only, single-scene review host for unobstructed city/landscape scene captures as allowed by the plan, and capture the normal MainActivity separately for foreground and timeline acceptance.",
    "Installed scene review host uses the explicit Chicago CatalogLocation, sg4-canonical-gaps-v8, and 2026-10-04T15:30:00Z; semantic tests and clean scene captures pass.",
    "On 2026-11-17, the product owner resolved the Chicago-versus-Florence checkpoint question by choosing option 2: re-establish the exact SG.4 Chicago replay with Chicago shown, then recapture the MainActivity evidence."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Product owner approved both complete OX-18 behavior and UI board packages on 2026-11-16; exact package fingerprints are recorded in the cycle manifest.",
    "On 2026-11-17, the product owner reconfirmed the 2026-11-16 board approvals; stale pending-review labels were corrected and package fingerprints refreshed without changing approved scope or artwork.",
    "Resolved the OX-18 temperature boundary with the product owner: keep temperature in the unchanged forecast foreground, outside the shared scene-decision contract.",
    "Cycle 146 BOARDS completed with both exact owner-approved packages and fingerprints recorded in the manifest.",
    "Completed the bounded OX-18 plan from the approved boards, OX-04/OX-06 dependency records, OX-03/OX-05 constraints, and named implementation boundaries; recorded exact evidence, history, and archive targets.",
    "Final plan review passed, and the owner explicitly authorized execution of cycle 146 through oxygen-execute-plan.",
    "Implemented one shared evidence-aware SceneWeatherDecision for First City and the minimal open-landscape composition; First City-local treatment, cues, depth order, and identities remain in their approved boundary.",
    "Added sparse open-landscape rendering and a debug-only single-scene review host; the normal catalog route continues to render First City.",
    "Focused behavior verification, contract/build/workflow checks, and Pixel 3 XL installed scene captures completed; exact results and limits are indexed in the cycle evidence README and test reports.",
    "Paused in EXECUTE when installed evidence showed a Chicago replay picker and Florence MainActivity title; recorded the owner question without changing implementation.",
    "After the owner selected option 2, selected saved Chicago, reactivated the exact SG.4 replay at 2026-10-04T15:30:00Z, and recaptured MainActivity; Chicago, 57°F, 75%, and 10:30 AM CDT are confirmed.",
    "Retained the earlier Florence-labeled screenshot and hierarchy as historical evidence; refreshed the MainActivity and replay-picker evidence for the confirmed Chicago state.",
    "Recorded OX-18 DONE after confirming the approved exit and evidence; advanced cycle 146 to CLOSEOUT.",
    "Inspected the complete final diff; git diff --check and python scripts/dev.py workflow passed at ACTIVE/CLOSEOUT.",
    "Restored the full cycle decision, output, and evidence history in the CLOSEOUT checkpoint before archival.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/146-ox18-minimal-second-world/README.md",
    ".codex/test-artifacts/146-ox18-minimal-second-world/TEST-com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest.xml",
    ".codex/test-artifacts/146-ox18-minimal-second-world/TEST-com.oxygen.weather.timeline.presentation.scene.SharedSceneWeatherDecisionTest.xml",
    ".codex/test-artifacts/146-ox18-minimal-second-world/catalog-picker-supported-rain.xml",
    ".codex/test-artifacts/146-ox18-minimal-second-world/city-supported-rain-baseline.png",
    ".codex/test-artifacts/146-ox18-minimal-second-world/city-supported-rain-baseline.xml",
    ".codex/test-artifacts/146-ox18-minimal-second-world/city-supported-rain-clean-baseline.png",
    ".codex/test-artifacts/146-ox18-minimal-second-world/city-supported-rain.png",
    ".codex/test-artifacts/146-ox18-minimal-second-world/landscape-supported-rain.png",
    ".codex/test-artifacts/146-ox18-minimal-second-world/main-supported-rain-florence-mismatch.png",
    ".codex/test-artifacts/146-ox18-minimal-second-world/main-supported-rain-florence-mismatch.xml",
    ".codex/test-artifacts/146-ox18-minimal-second-world/main-supported-rain.png",
    ".codex/test-artifacts/146-ox18-minimal-second-world/main-supported-rain.xml",
    ".codex/history/146-ox18-minimal-second-world.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/plans/146-ox18-minimal-second-world.md",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.md",
    ".codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-ui-board.md",
    ".codex/test-artifacts/146-ox18-minimal-second-world/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SceneWeatherDecision.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/MinimalOpenLandscapeComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/debug/AndroidManifest.xml",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugOx18ReviewScenario.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugOx18SceneReviewActivity.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/SharedSceneWeatherDecisionTest.kt",
    "scripts/dev.py",
    "scripts/codex_cycle.py"
  ],
  "next_action": "Archive cycle 146 with exact verification and evidence, then validate the closed IDLE workflow."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Dependency basis and approved scope

- **OX-04 is DONE.** Cycle 143 preserved supplied, eligible-carried, unavailable, independent-field, and probability evidence on the active catalog routes. Its cloud visual subcheck was **NOT RUN** because a replay diagnostic overlay obscured the cloud band. This cycle may reuse OX-04's typed evidence cases, but does not treat that obscured capture as cloud-rendering proof.
- **OX-06 is DONE.** Cycle 145 added 11 First City-local identities and mappings for its 12 physical render passes; both cloud passes use one identity and the two compositing-only passes remain unmapped. The renderer and composition builder were unchanged, and no installed visual acceptance was claimed.
- The approved OX-18 behavior and UI packages govern this draft. The UI comparison is review evidence; its paired panes, labels, and selected-time rails are not app UI. The behavior board's shared-decision contract is approved, while exact Kotlin type names, a public scene schema, and a universal registry are not.
- OX-03's approved carried-rain rule remains in force: visible-cloud rain begins at its cloud base; eligible carried rain with no visible cloud decision keeps the existing no-cloud scene and rain-field origin. OX-05's First City depth order and single foreground precipitation field remain in force.

The dependency records reviewed for this draft are `.codex/history/143-ox04-scene-truth.md`, `.codex/history/plans/143-ox04-scene-truth.md`, `.codex/test-artifacts/143-ox04-scene-truth/README.md`, `.codex/history/145-ox06-semantic-identity.md`, `.codex/history/plans/145-ox06-semantic-identity.md`, and `.codex/test-artifacts/145-ox06-semantic-identity/README.md`. The specific OX-03 and OX-05 carry/depth constraints above are recorded in `docs/ROADMAP.md` and adopted by the approved OX-18 boards.

### Functional boundary

The proof uses one catalog selection and one exact selected instant `T`:

```text
catalog + T
  -> SemanticScenePlan.evaluate(T): one SceneReading
  -> one internal, evidence-aware shared weather-decision value
       -> existing FirstCityTheme / FirstCityComposition
       -> minimal open-landscape composition
```

The shared value resolves weather meaning once from the selected `SceneReading`. Both world-specific composers consume that same value. Composition and artwork may differ by world; neither world may re-read catalog/provider data or independently redo condition, support, carry, or precipitation interpretation. Keep the seam in the existing typed presentation boundary and keep rendering dependent on typed presentation values.

The shared value must retain or preserve the source reading's exact `selectedInstant`, per-field evidence/support, `sourceValidTime`, unavailable states, and independently supported fields. The existing First City-specific treatment, selected-time cues, depth order, physical pass order, semantic identities, and approved artwork remain First City-local. The open-landscape composition is sparse and visibly non-city, using open terrain and two tree groups without buildings; it maps the shared decisions to its own placement. No public/persisted identity or schema is introduced.

### In-scope work

1. Separate the smallest shared semantic weather-decision seam from world-specific composition in `presentation/scene`, reusing the existing `SceneReading` input and current First City decisions. Do not prescribe a public Kotlin type name or build a generalized scene framework.
2. Keep `FirstCityTheme` and `FirstCityComposition` consuming the shared weather meaning while preserving their current selected-time and composition behavior, OX-05 depth/pass order, and OX-06 First City-local identity map.
3. Add the minimal open-landscape composition and renderer needed to show that the same decisions reach a non-city world. Keep this as a renderer/composition entry point for review; do not add a production world-selection state or control.
4. Prove decision parity for the approved evidence classes and capture the supported-rain visual target on an installed Pixel 3 XL reference viewport. The paired images are separate review artifacts, not a combined in-app layout.

### Evidence contract and scoped scenarios

The focused behavior proof uses existing OX-04 catalog cases where available:

| Case | Catalog selection and `T` | Required result |
|---|---|---|
| Supplied rain, installed visual target | `sg4-canonical-gaps-v8`, `2026-10-04T15:30:00Z` | Both compositions receive the same supplied-rain decision and evidence meaning at exact `T`. Temperature (57°F) remains in the existing forecast foreground, outside the shared scene decision. The 75% chance remains a separate probability; rain stays illustrative and is not a measured amount or rate. |
| Eligible carried rain | `sg4-canonical-gaps-v8`, `2026-10-04T16:30:00Z` | The selected instant remains `T`; carried rain retains source-valid time `2026-10-04T15:00:00Z`. No cloud is inferred when there is no visible cloud decision; preserve the OX-03 rain-field origin. |
| Unavailable condition with independent facts | `ox01f-condition-unavailable-typed-v1`, `2026-10-28T20:00:00Z` | No condition-derived marks in either world. Independently supported cloud cover (40%) and 25% precipitation chance retain their support and meaning in the shared scene decision. The independent 9°C catalog temperature remains in the existing forecast presentation path, outside that decision. The OX-04 cloud capture is not treated as visual evidence because its cloud region was obscured. |

Behavior acceptance requires assertions that both compositions receive the same shared weather-decision value for a single `SceneReading`; both retain `selectedInstant == T`; carried evidence retains its original source time; unavailable condition yields no condition-derived marks; independently supported `SceneReading` fields remain independent; and precipitation probability is never converted to amount or rate. Temperature is not a `SceneReading` or composition field and is not part of this parity assertion; the existing forecast foreground continues to present it unchanged. The proof is about shared semantic input and world-specific mapping, not pixel equality.

Visual acceptance is limited to the approved supplied-rain state. Capture the existing First City output and minimal landscape output separately using the same catalog and `T`, then index them as a matched pair. Confirm the landscape is sparse and recognizably non-city, the First City baseline is unchanged, clouds stay within the approved upper third, and rain marks remain illustrative. Confirm the existing forecast foreground still shows the supported 57°F temperature and 75% precipitation chance readably, and that the timeline and selection remain unchanged; these facts stay outside the shared composition contract. Use a test-only/non-product review host if needed to render the second composition; it must not add a production split-screen, scene picker, switching interaction, or layout change. The normal `CatalogTimeLabScreen` caller continues rendering First City and does not select the landscape. Current device inventory shows a connected Pixel 3 XL (`8AKY0M32X`); reconfirm device and viewport when execution begins.

No app interaction is added or changed. The directly affected check is that both compositions retain the same exact `T`; do not claim broader gesture, accessibility, RTL, or system acceptance.

### Exclusions

- A polished scene pack, Living Urban Park adoption, default/selectable-world role, scene switching, picker, or any other product-facing world-selection behavior.
- An in-app side-by-side/split-screen comparison, new layout, controls, labels, forecast foreground, timeline, or interaction changes.
- A public or persisted scene schema, universal element registry, or cross-world identity system.
- Cloud/precipitation primitive extraction or renderer-library extraction.
- New forecast fields, providers, catalog/schema changes, meteorological plausibility correction, inferred cloud cover, measured precipitation claims from decorative marks, or changes to the deprecated oxygenWX app.
- Changes to First City visual output, OX-05 physical depth/pass ordering, OX-06 First City-local identities, or supported selected-time light/event inputs. If preserving this baseline requires a visible change, stop and return for a revised approved artwork target.

### Compatibility and documentation

The planned seam is internal presentation code. Temperature remains in the existing forecast presentation path and is not added to `SceneReading` or the shared scene-decision value. There is no persisted data, public API, provider/catalog migration, or product-role change. Update code-level documentation for the shared decision ownership and world-specific mapping. Keep the existing `CatalogTimeLabScreen` catalog caller on First City and keep the new composition out of normal product selection. No product specification change is proposed; update this bounded cycle plan and its evidence index with actual results during authorized execution.

### Verification, evidence, and authorization

**Authorization at plan review:** Finalizing this plan authorized planning only; it did not authorize implementation, tests, build/install work, or device captures.

**Execution authorization (2026-11-17):** The product owner explicitly requested execution of this current cycle through `$oxygen-execute-plan`. That request authorizes the scoped implementation and verification listed below, including the focused JVM tests, contract/build/workflow checks, install, and Pixel 3 XL captures. Keep execution within this plan.

When execution is authorized, the scoped verification target is:

1. Focused JVM assertions for the shared decision value across the three evidence cases above, plus existing First City composition/depth/identity assertions. The proposed focused command is `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.SharedSceneWeatherDecisionTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest` after the new test class exists and is authorized.
2. `python scripts/dev.py contract`, `python scripts/dev.py build`, and `python scripts/dev.py workflow` for the scoped architecture/build/workflow checks. Record exact command results; do not treat a build as visual acceptance.
3. Install and capture the affected supplied-rain state on the Pixel 3 XL reference viewport. Save separate actual screenshots for First City and open landscape, both tied to the same catalog selection and `T`, and inspect the city baseline, landscape silhouette, upper-third cloud limit, illustrative rain, readable existing forecast foreground, and unchanged timeline/selection.
4. Inspect the scoped final diff and run `git diff --check`. Record each planned check as PASS, FAIL, or NOT RUN with a reason; do not add broad RTL/TalkBack/device matrices to this design slice.

Planned evidence targets (not yet created) are `.codex/test-artifacts/146-ox18-minimal-second-world/README.md`, `city-supported-rain.png`, `landscape-supported-rain.png`, `TEST-com.oxygen.weather.timeline.presentation.scene.SharedSceneWeatherDecisionTest.xml`, and `TEST-com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest.xml`. The closeout target is `.codex/history/146-ox18-minimal-second-world.md`; the archived plan target is `.codex/history/plans/146-ox18-minimal-second-world.md`. Evidence must record the catalog, exact `T`, device/build/viewport, capture method, actual verification results, and limitations. No implementation or installed acceptance evidence is claimed at plan finalization.

### Execution checkpoint (2026-11-17)

Implementation and scoped verification met the approved OX-18 exit. The shared evidence-aware scene decision feeds both First City and the minimal open-landscape composition. The three planned evidence cases pass the focused shared-decision assertions, and the pre-refactor and installed First City captures visually match. Separate installed Pixel 3 XL captures show the approved sparse landscape with upper-third clouds and illustrative rain. After the owner-directed installed-state re-establishment, the normal MainActivity capture shows Chicago, the selected 10:30 AM point, 57°F, and 75% precipitation chance.

**Location-label resolution (2026-11-17):** the product owner chose option 2. The installed selector showed Florence, Alabama (`geonames:4062577`) as the saved selected location and Chicago (`geonames:4887398`) as another saved location. Chicago was explicitly selected through the normal location selector, and the exact `sg4-canonical-gaps-v8` replay was reactivated at `2026-10-04T15:30:00Z`. The refreshed MainActivity hierarchy shows Chicago; the replay picker confirms active Chicago SG.4 and selected Chicago. The prior Florence-labeled screenshot and hierarchy remain in the evidence directory to preserve the discrepancy record. No location-name synchronization implementation change was made.

Execution evidence is indexed at `.codex/test-artifacts/146-ox18-minimal-second-world/README.md`. The focused JVM command passed with 10 tests and no failures; `python scripts/dev.py contract`, `python scripts/dev.py build`, `python scripts/dev.py run --serial 8AKY0M32X`, `git diff --check`, and `python scripts/dev.py workflow` passed. Workflow validation passed after resuming the same cycle at `ACTIVE` / `EXECUTE`, and again at `ACTIVE` / `CLOSEOUT` after OX-18 was recorded `DONE`. `git diff --check` passed during closeout review. The closeout and archived-plan records are created by cycle 146 closeout.

### Risks and stop conditions

- A world-specific composer must not become a second weather interpreter. If the shared value cannot supply both outputs while preserving evidence distinctions, stop for a bounded plan decision rather than copy rules into the landscape composer.
- Any First City visible-output, depth, or identity change, any unsupported weather mark, or any need for a product-facing world-selection surface exceeds this approved scope and requires replanning against an updated board before proceeding.
- The OX-04 cloud-visibility subcheck remains unverified in its historical evidence; OX-18's own installed captures must establish the approved supported-rain visual state.
