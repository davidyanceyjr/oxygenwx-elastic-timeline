# Cycle plan — 179-ox110-storybook-cloud-layers

State: CLOSED
Plan maturity: FINAL
Cycle ID: `179-ox110-storybook-cloud-layers`
Roadmap items: OX-110
Objective: Replace Urban Park cloud placeholders with Compose-drawn storybook layers, using a centered Park plane crop and sky underlay only through Background transparency while preserving generator and selected-time mechanics

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "179-ox110-storybook-cloud-layers",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-110",
      "depends_on": [
        "OX-99",
        "OX-108",
        "OX-109"
      ],
      "status": "DONE",
      "exit": "Installed Urban Park evidence shows the Park Background, Middleground, and Foreground filling the atmospheric viewport with a shared center crop; the weather-sky underlay is visible only through transparent Background pixels. Evidence shows clearly visible mapped one-, two-, and three-layer storybook cloud forms, including a Moon-visible and Sun-visible overlap where supported. Back to front, the scene composites weather-sky underlay (beneath transparent Background pixels) → Park Background → stars → Sun → Moon → Clouds → base/added Middleground, including the tree → Foreground, including the hanging banner. Clouds cover overlapping celestial pixels; the tree and banner remain above clouds. Cloud output stays wholly inside the top 40% of the atmospheric scene viewport measured from its top edge, with no painted backing outside cloud forms. Selected-time motion and forecast/source-time meaning remain unchanged; no scene-generator mechanics or other theme behavior changes.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-25",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.dot",
              "sha256": "dd4e28c164fa394312c83a3ab0d297a88788627b132cb57833a1c8e72c15fb74"
            },
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.svg",
              "sha256": "63fd856e79cbfd99e3194cbb0de76bff8feede567fc015e9168c42e77fdcf325"
            },
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.png",
              "sha256": "173293e87abb7c58be2fb650fe3a4aa559d7503395adba0b2740f4e41bb83621"
            },
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.md",
              "sha256": "7d258b332fb43d6713422783b31acbed81a290754ff90685524f6b7efc30cb4a"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-25",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.svg",
              "sha256": "80e25bb618e375cfdad0f47d07e2089b4ddacaa48b3bc2b555cd464999695969"
            },
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.png",
              "sha256": "20d0d7141211637d99861114af468ba4ac0e1b3e48317f8d060a89572b9794ee"
            },
            {
              "path": ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.md",
              "sha256": "3e358a1325571741e4f0cb47889f6ba365a1a1bbd507b782fa58a6ae102b01f2"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
              "sha256": "cbdbb4f89e409a4ba1086a644509c715800b089958f03eb2c72af9ef0e727e2f"
            },
            {
              "path": ".codex/concepts/OX-109-current-sun-cloudy-reference.png",
              "sha256": "e37497642d94d8b1ea039c4f768761d44f9eccfa35f871a3c2a081d4881fd19c"
            },
            {
              "path": ".codex/concepts/OX-110-sunlit-storybook-clouds-reference.png",
              "sha256": "c50ab55a04c8172460648f12d95ffd26f725608a7a0976bb17163ecb7e1112ef"
            },
            {
              "path": ".codex/concepts/OX-110-generated-storybook-cloud-layers.png",
              "sha256": "d7513a538c5db58cc52954696fb8face2260d473c88ab4d2919817253d1db202"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png",
              "sha256": "47236d2e588ae623e115373fe0934d036528c4a88a4de2a6cd3434b8a3aa8125"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-park-middleground-preview.png",
              "sha256": "1f7e4b4ee17437b9f00f17876a6d347f7bf120e3d630ffba08ede3ca645a54f2"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-park-foreground-preview.png",
              "sha256": "524908a4d289c8995ac0a56951c44bfd09808fee3c2a325dd2c72191d814da75"
            },
            {
              "path": "design_draft/SunLitStorybookClouds.png",
              "sha256": "c50ab55a04c8172460648f12d95ffd26f725608a7a0976bb17163ecb7e1112ef"
            }
          ]
        }
      },
      "outcome": "Replaced the Park cloud placeholders with three independently tracked Compose storybook cloud layers; the installed states meet the approved shared-crop, transparent-sky underlay, separate celestial, layer-depth, top-40-percent, event-mapping, and selected-time criteria.",
      "evidence": [
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/README.md",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/04-partly-sunny-12-00-installed.png",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/04-partly-sunny-12-05-selected-from-12-00.png",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/05-partly-cloudy-night-13-00-installed.png",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/06-mostly-cloudy-14-00-installed.png",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/07-cloudy-overcast-15-00-installed.png",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/08-sunny-08-00-control-installed.png",
        ".codex/test-artifacts/179-ox110-storybook-cloud-layers/09-direct-scrub-selected-time.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-25",
    "summary": "OX-110 completed: Urban Park now draws three stable, independently tracked Compose storybook cloud layers with the approved canonical event mapping. The Park planes share a centered viewport-covering crop; the weather-sky underlay appears only through transparent Background pixels; Sun, Moon, and stars remain separate scene elements; and the banner is redrawn above clouds. Installed states and a direct timeline scrub met the scoped visual and interaction exit.",
    "verification": [
      "python scripts/dev.py workflow — passed; cycle checkpoint, approvals, dependencies, and roadmap validated.",
      "python scripts/dev.py build — passed (BUILD SUCCESSFUL).",
      "python scripts/dev.py run --serial 8AKY0M32X — passed; debug build installed and MainActivity launched on Pixel 3 XL.",
      "git diff --check — passed with no whitespace errors.",
      "No automated tests run, per plan authorization; installed acceptance is documented in the cycle evidence README."
    ],
    "evidence_directory": ".codex/test-artifacts/179-ox110-storybook-cloud-layers/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "179-ox110-storybook-cloud-layers",
  "slice_ids": [
    "OX-110"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.dot",
    ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.svg",
    ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.png",
    ".codex/concepts/OX-110-storybook-cloud-layers-behavior-board.md",
    ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.svg",
    ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.png",
    ".codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.md",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
    ".codex/concepts/OX-109-current-sun-cloudy-reference.png",
    ".codex/concepts/OX-110-sunlit-storybook-clouds-reference.png",
    ".codex/concepts/OX-110-generated-storybook-cloud-layers.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-middleground-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-foreground-preview.png",
    "design_draft/SunLitStorybookClouds.png"
  ],
  "completed_gates": [
    "OX-110_behavior_board_approved",
    "OX-110_ui_artwork_board_approved",
    "OX-110_bounded_plan_finalized",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-110_installed_acceptance_exit_met"
  ],
  "decisions": [
    "After presenting the three source-art scope options and waiting 60 seconds with no reply, followed the recommended approved-scope option per the owner instruction: preserve the existing Park planes, use sky only behind transparent Background pixels, and keep Sun, Moon, and stars as separate render elements."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Resolved the source-art scope question without expanding OX-110; the approved board already specifies this preservation treatment and no board input changed.",
    "Closed slice OX-110 as DONE.",
    "Installed OX-110 states and scoped scrub evidence recorded under .codex/test-artifacts/179-ox110-storybook-cloud-layers/.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/README.md",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/04-partly-sunny-12-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/04-partly-sunny-12-05-selected-from-12-00.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/05-partly-cloudy-night-13-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/06-mostly-cloudy-14-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/07-cloudy-overcast-15-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/08-sunny-08-00-control-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/09-direct-scrub-selected-time.png",
    ".codex/history/179-ox110-storybook-cloud-layers.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/179-ox110-storybook-cloud-layers.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/UrbanParkComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/README.md",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/04-partly-sunny-12-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/04-partly-sunny-12-05-selected-from-12-00.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/05-partly-cloudy-night-13-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/06-mostly-cloudy-14-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/07-cloudy-overcast-15-00-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/08-sunny-08-00-control-installed.png",
    ".codex/test-artifacts/179-ox110-storybook-cloud-layers/09-direct-scrub-selected-time.png"
  ],
  "next_action": "Inspect the final scoped diff, confirm git diff --check and python scripts/dev.py workflow, then close cycle 179."
}
```
<!-- cycle-checkpoint:end -->

## Approved board package — owner approval recorded 2026-11-25

**Owner reconciliation (2026-11-25):** The product owner confirmed the explicit manifest approval record as authoritative. Stale status labels in the approved board notes and review renders were updated to reflect that approval; the approved behavior and artwork content was not changed. Affected source and render fingerprints are refreshed below.

The behavior board source is `.codex/concepts/OX-110-storybook-cloud-layers-behavior-board.dot`; its review render is `.codex/concepts/OX-110-storybook-cloud-layers-behavior-board.svg` and `.png`, with the description in `.md`. The UI artwork source is `.codex/concepts/OX-110-storybook-cloud-layers-ui-artwork-board.svg`; `.png` is its review render and `.md` describes the proposal. Its SVG shows the OX-108 and OX-109 app captures and uses `.codex/concepts/OX-110-generated-storybook-cloud-layers.png`, `.codex/concepts/OX-110-sunlit-storybook-clouds-reference.png`, and the three OX-108 Park plane preview PNGs as board-render inputs. The generated cloud PNG is reference-only visual direction; it does not propose a runtime bitmap asset.

The product owner approved both complete board packages on 2026-11-25. The OX-110 roster records approver/date and a SHA-256 fingerprint for every board source, render, contract/notes file, and referenced image. The behavior board governs event mapping, declared layer identities, composition order, the top-40% bound, and preserved generator/selected-time mechanics. The UI artwork board governs the four event/celestial states, the visible painterly cloud layers, warm Moon-visible highlights, overcast celestial coverage, tree/banner occlusion, and screenshot-based composition references.

The board package status labels were reconciled per the owner’s later 2026-11-25 direction to match the manifest approval record. Only approval-status wording and the corresponding render labels changed; approved behavior and artwork content remains unchanged. The OX-110 roster fingerprints were refreshed for each affected source and render.

**Owner-directed sky and crop decision (2026-11-25):** Center-crop the Park Background, Middleground, and Foreground planes with one shared viewport-covering transform. Keep the weather-sky underlay only beneath transparent Park Background pixels. This updates the earlier constraint to keep Park plane transforms fixed. Both affected board packages were revised and their owner approvals and fingerprints refreshed before execution resumed.

**Owner clarification and scope resolution (2026-11-25):** The owner clarified that the Moon, Sun, and stars are separate elements. The existing Park Background source also contains sky and embedded Park artwork; the approved boards preserve that artwork. After the owner-directed 60-second clarification wait elapsed without a reply, the recommended approved-scope interpretation was applied: preserve the existing Background/Middleground/Foreground artwork, remove the separate top sky band, fill-crop the three Park planes together, expose the sky underlay only through transparent Background pixels, and keep Sun, Moon, and stars as separate render elements. No asset split or board-content change was introduced.

## Slice scope and acceptance summary

### Captured baseline

The pre-slice screen capture and its analysis are preserved at `.codex/test-artifacts/179-ox110-storybook-cloud-layers/baseline-screen.png` and `baseline-screen-analysis.md`. The visible “Cloudy or overcast” state has one small generic cloud over the Park image and a fully exposed Sun in the separate sky band. This motivates the artwork replacement and layer-coverage acceptance; it does not establish the cause or count as installed acceptance evidence.

### Objective and scope

Replace the three Urban Park placeholder cloud forms with Kotlin/Compose-drawn cloud artwork inspired by `design_draft/SunLitStorybookClouds.png`. Introduce an internal, theme-specific `UrbanParkComposition` model that reuses the existing `FirstCityComposition` weather, celestial, precipitation, and selected-instant inputs while declaring stable Park-only cloud-layer identities and their back-to-front positions. Adapt `CatalogTimeLabScreen` to construct and pass this Park-specific model with the selected `WeatherSymbolId`; `CatalogUrbanParkScene` consumes it. Do not add Park declarations to or alter the shared `FirstCityComposition.layerOrder`. Composite weather-sky underlay (visible only through Background transparency) → center-cropped Park Background plane → stars → Sun → Moon → active Cloud Layers 1–3 → the complete base and added Middleground, including the tree → Foreground, including the hanging banner. Clouds cover Sun, Moon, and star pixels where forms overlap; the Middleground tree and Foreground banner cover clouds. The banner pixels currently reside in the Background image, so make the banner a Foreground element while preserving its source artwork and mask and avoiding a visible duplicate. Center-crop the Background, Middleground, and Foreground planes to cover the viewport with one shared transform so their alignment and the banner mask remain registered. Keep the scene generator's forecast-decision and selected-time mechanics unchanged.

Preserve OX-108's selected-event mapping: `PARTLY_SUNNY_CLOUDY` and `PARTLY_CLOUDY_NIGHT` activate Layer 1; `MOSTLY_CLOUDY` activates Layers 1–2; `CLOUDY_OVERCAST` activates Layers 1–3; other or absent events activate no Park cloud layer. Preserve OX-109's independent selected-time tracking for each active layer.

### Dependencies and source boundaries

- OX-99, OX-108, and OX-109 are recorded `DONE` in the roadmap. OX-99 supplies the aligned Park image planes, OX-108 the event mapping, and OX-109 the cloud/celestial composite order and deterministic tracks.
- The implementation boundary includes an internal, theme-specific `UrbanParkComposition` in the scene-presentation package, the Park branch of `CatalogTimeLabScreen.kt` that constructs it from the existing `FirstCityComposition` plus selected `WeatherSymbolId`, and the `CatalogUrbanParkScene` drawing path in `AtmosphericScene.kt` that consumes it. The Park model owns the stable cloud-layer identities and Park-local composite positions; it reuses existing weather/celestial/precipitation/selected-instant decisions without changing `FirstCityComposition.layerOrder` or other theme routes. The supplied PNG informs Compose paths, gradients, palette, and volume; it is not a runtime image asset. Any banner isolation needed to render it in Foreground is part of this slice and must preserve its existing art and transform. Keep the Park plane source artwork fixed; use one viewport-covering center crop for all three planes.
- `docs/SPECIFICATION.md` constrains all cloud output, including edges and motion, to the top 40% of the atmospheric scene viewport, measured from its top edge. Forecast selection, source time, and event carry rules remain controlled upstream.

### Scoped acceptance and evidence

Installed captures will review the four canonical cloudy event IDs, a non-cloudy control, and visible Sun/Moon overlap where supported. Scoped review will check the shared center crop and sky underlay only through transparent Background pixels, then confirm that the declared order is visible in the composite; one-, two-, and three-layer forms map correctly; clouds cover celestial objects where their pixels overlap; the complete Middleground tree and Foreground banner stay over clouds; cloud gaps remain transparent; every visible cloud pixel, edge, and movement stays in the top 40% of the scene canvas; selected-time movement follows a direct timeline scrub; and forecast/timeline labels remain readable. Evidence target: `.codex/test-artifacts/179-ox110-storybook-cloud-layers/`.

No automated tests are authorized by this planning request. Broad accessibility, RTL, and system matrices are outside this design slice.

### Plan boundaries

- Keep the OX-108 selected-event mapping exactly: `PARTLY_SUNNY_CLOUDY` and `PARTLY_CLOUDY_NIGHT` activate Layer 1; `MOSTLY_CLOUDY` activates Layers 1–2; `CLOUDY_OVERCAST` activates Layers 1–3; all other or absent events activate none. Do not derive layer count from cloud-cover percentage.
- Preserve OX-109's independent, deterministic selected-time movement for each active layer. Do not use wall-clock drift, zoom, or animation time as forecast time or as the source of cloud position.
- Constrain all cloud pixels, edges, movement, and transitions to the top 40% of the atmospheric scene viewport. Each layer paints only its cloud forms; gaps expose the underlying celestial/sky pixels.
- Keep precipitation as its existing separate pass. Keep the weather-sky underlay beneath the Park planes so it is visible only through transparent Background pixels. Preserve the shared center-crop transform across all three source-aligned Park planes. Preserve the Sun/Moon selection, paths and semantics; forecast data/readouts, selected-time authority, timeline gestures, and all non-Park themes remain unchanged.
- Do not add runtime bitmap clouds, new weather inputs, generator mechanics, new themes, forecast interpretations, or broad accessibility/system work.

## REVIEW — final bounded execution plan

### Review result and approved-board contract

- OX-110 remains the sole `NEXT` slice in this single-slice cycle. Its OX-99, OX-108, and OX-109 dependencies are recorded `DONE`; the roadmap and ordered cycle roster agree. The cycle was subsequently activated; the current pointer is `ACTIVE / EXECUTE` and refers to this plan.
- The behavior board defines the exact three-layer event mapping, explicit cloud identities/composite placement, background/celestial/cloud/Middleground/Foreground order, cloud-only transparency, top-40% boundary, preserved generator and selected-time mechanics, and the banner's required foreground treatment. The UI artwork board defines the four affected appearances and celestial coverage, warm Moon-visible palette, tree/banner-over-cloud depth, and actual app-screen composition references. Product owner approval is recorded per OX-110 roster item on 2026-11-25 with complete source/render/notes/reference SHA-256 fingerprints; workflow validation confirms the approved inputs exist and match.
- The board sources and review renders carry the refreshed approval labels. The product owner directed the Park sky-underlay and shared center-crop treatment on 2026-11-25; the revised behavior/UI package records that direction and its new per-file fingerprints.
- The full base Middleground is composited after the cloud group, so the tree silhouette masks cloud edges. The hanging banner is a Foreground object despite its source pixels being present in the Background image. Preserve those source pixels, the banner's appearance, and its source-space mask while giving it foreground occlusion. Apply the same viewport-covering center crop to the Background, Middleground, and Foreground planes to preserve registration.

### Product and implementation boundary

- **In scope:** Add an internal, theme-specific `UrbanParkComposition` model in the scene-presentation package, adapt the Park branch of `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` to construct it from the existing `FirstCityComposition` and selected `WeatherSymbolId`, and have the Park branch of `CatalogUrbanParkScene` in `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` consume it. `UrbanParkComposition` reuses existing weather, celestial, precipitation, and selected-instant decisions while declaring stable Park-only Cloud Layer 1–3 identities and their composite positions. Do not add Park declarations to or alter the shared `FirstCityComposition.layerOrder` used by other theme routes. Isolate or redeclare the hanging banner as a Foreground element while preserving its artwork and transform.
- **Layer contract:** Draw the weather-sky underlay, visible only through transparent Park Background pixels, then the center-cropped Park Background plane, stars, selected-time Sun, supported Moon, active clouds, base and added Middleground including the tree, then Foreground including the banner. The cloud group is after all celestial objects (so cloud forms cover overlapping pixels) and before all Middleground content. The banner must visibly cover cloud pixels and must not appear twice.
- **Art and geometry:** Draw the cloud style in Kotlin/Compose using `design_draft/SunLitStorybookClouds.png` as reference. The image may inform color, volume, highlights, and shading, but no generated/reference bitmap is introduced into runtime. Keep transparent gaps. Clip the entire cloud output to a Park-local top-40% boundary of the atmospheric canvas, including cloud extents, movement, edges, and transitions. Preserve the shared `.33f` `CLOUD_REGION_FRACTION` for non-Park renderers; OX-110 changes Urban Park only.
- **Weather mapping and time:** Preserve the selected `WeatherSymbolId` mapping exactly: `PARTLY_SUNNY_CLOUDY` and `PARTLY_CLOUDY_NIGHT` select Layer 1; `MOSTLY_CLOUDY` selects Layers 1–2; `CLOUDY_OVERCAST` selects Layers 1–3; all other or absent events select none. Keep each active layer on its existing deterministic `selectedInstant` track. No cloud-cover-driven remapping or wall-clock/zoom time.
- **Compatibility and exclusions:** No scene-generator or forecast-decision changes, canonical data changes, public weather/presentation API changes, new theme/selection or persistence work, precipitation changes, celestial path/meaning changes, forecast/timeline UI or gesture changes, First City/Living Urban Park/other theme changes, or broad accessibility/system matrix. No automated tests are authorized in this planning request.

### Ordered execution and scoped verification

1. On later activation, run `python scripts/dev.py workflow`; confirm OX-110 is the active slice and its three dependencies remain complete. Read the approved contract and inspect the Park renderer/data and banner source placement before editing.
2. Add the internal `UrbanParkComposition` model with stable Park cloud-layer identities and composite positions, reusing the existing `FirstCityComposition` weather/celestial/precipitation/selected-instant inputs. Adapt `CatalogTimeLabScreen` to construct it from that existing composition and the selected `WeatherSymbolId`; update `CatalogUrbanParkScene` to consume the Park-specific model and draw the three Compose-authored layers in the approved order, preserving OX-109's per-layer tracks. Do not change shared `FirstCityComposition.layerOrder`. Keep the full base Middleground after the cloud group and put the banner in Foreground without changing its artwork or transform or producing a duplicate.
3. Keep the whole visible cloud result inside a Park-local top-40% boundary of the scene canvas, including its contours, movement, and transitions, and keep non-cloud pixels transparent. Preserve the shared `.33f` `CLOUD_REGION_FRACTION` used by non-Park renderers. Do not change the generator, forecast decisions, selected-time authority, celestial semantics, precipitation, or other themes.
4. Run the authorized scoped build and install path using `python scripts/dev.py build` and `python scripts/dev.py run --serial <connected-device>` when the review device is available. Do not add or run automated tests unless separately authorized.
5. Capture installed Park states in `.codex/test-artifacts/179-ox110-storybook-cloud-layers/`: `PARTLY_SUNNY_CLOUDY`, `PARTLY_CLOUDY_NIGHT`, `MOSTLY_CLOUDY`, `CLOUDY_OVERCAST`, a non-cloudy control, and supported Sun/Moon overlaps. Record exact selected instants and event IDs. Inspect the shared center crop, sky only through Background alpha, all three cumulative layer counts, celestial occlusion in overlaps, tree and banner in front, cloud alpha gaps, and the top-40% boundary.
6. Directly scrub the timeline across affected states and capture the selected-time cloud movement, selected-time continuity, gesture response, and forecast/timeline readability. Record actual screenshots and scoped observations; these are the slice evidence, not the approved artboard or baseline capture.
7. Run `git diff --check`, inspect the scoped final diff, and run `python scripts/dev.py workflow`. Add an evidence README under the cycle evidence directory naming device, events/instants, screenshots, observations, exact commands/results, and limits. Close OX-110 only when its roadmap exit is met and this actual evidence is present.

### Acceptance, authorization, and limits

- **Observable exit:** Installed Urban Park evidence shows the Park planes filling the atmospheric viewport with one shared center crop and the weather-sky underlay visible only through transparent Background pixels. It also shows the approved mapped one-, two-, and three-layer storybook clouds, including supported Sun- and Moon-visible states; clouds overlay celestial pixels where they intersect, the full base Middleground/tree and Foreground/banner overlay clouds, and the back-to-front order is explicit in the composition data. Every cloud pixel/motion is within the scene canvas's top 40%, with transparent non-cloud gaps. Selected-time motion and forecast/source-time meaning, timeline interaction, and non-Park behavior remain unchanged.
- **Authorized verification:** The roadmap calls for installed screenshots and focused direct-scrub observations for these states. The future activated execution may use the scoped build/run/screenshot path and checks listed above. Automated tests, RTL, TalkBack, and broad device/system matrices are not authorized by this planning request.
- **Evidence target:** `.codex/test-artifacts/179-ox110-storybook-cloud-layers/`, with closeout `.codex/history/179-ox110-storybook-cloud-layers.md` and archived final plan `.codex/history/plans/179-ox110-storybook-cloud-layers.md`. Existing baseline and approved board screenshots remain context/reference, not acceptance evidence.
- **Open material decisions:** None. The product owner directed the Park plane center crop and sky-underlay visibility through Background alpha on 2026-11-25. Layer order, cloud direction, Moon-visible palette, top-40% boundary, and Middleground tree/Foreground banner depth are resolved by the approved board package and recorded owner direction.
- **Current authorization state:** This plan was finalized and activated for execution. OX-110 implementation is underway under the scoped authorization in the active cycle.
