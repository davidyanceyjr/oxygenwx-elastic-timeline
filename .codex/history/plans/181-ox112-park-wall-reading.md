# Cycle plan — 181-ox112-park-wall-reading

State: CLOSED
Plan maturity: FINAL
Cycle ID: `181-ox112-park-wall-reading`
Roadmap items: OX-112
Objective: Move Urban Park's selected-time reader values from the central readout onto its left wall while preserving exact values and accessible reading.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "181-ox112-park-wall-reading",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-112",
      "depends_on": [
        "OX-111E",
        "OX-12"
      ],
      "status": "DONE",
      "exit": "Installed Urban Park evidence across wet, clear, cloudy, and unavailable selected-time states shows the supplied matching weather forecast icon, forecast description, and Current Temp in a perspective-mapped mural on the left wall. The Park-owned renderer receives those presentation values and maps the hero temperature, slightly overlapping icon, and expressive description as one group through the wall's four-corner surface. It clips the marks to the masonry face, expands worn pigment, and samples wall texture through the paint. The mural is a separate layer after Foreground and updates on each selected instant. The three values no longer duplicate in the central visual readout; exact supported/unavailable meaning, existing Wind chill and other facts, tower location inscription, timeline, gesture behavior, source disclosures, and accessible reading remain intact.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-112-park-wall-reading-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-26",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-behavior-board.dot",
              "sha256": "1d37b810564ef6636a2a82bfb39f693885659c8100694720b8b41862bcc5ae5b"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-behavior-board.svg",
              "sha256": "4af81caea97a68ec31e70d579f28620a6af2be45944d9e8675aff03340e7fc56"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-behavior-board.png",
              "sha256": "8c2cba52c7b40a55f84e55b9c2a3f8a117d75bb183cc644b0b6b327a7e4de8b8"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-behavior-board.md",
              "sha256": "6c63ca8ee1ffc6d10bdde53637061990cfdabdbfd57ef016949e2522379d2b2f"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-26",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.svg",
              "sha256": "ac44d5b9f0696182393e7626a2737796a14c7c2e2d6a435a055c83ec49df337b"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.png",
              "sha256": "5a1693f065cf36fdf967d5a0a82c657c7575f6d1f23c334122bf93805010334d"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.md",
              "sha256": "ecf433f4a6762150586894188229e0c27272f3c54e6b709d0b4ff94f9b953dc4"
            },
            {
              "path": ".codex/concepts/OX-112-park-current-screen-capture.png",
              "sha256": "1ab4ff1d0fc77b7913cf3280586a6de3c18a16b8d3c227c8a5051c12dbd863fb"
            },
            {
              "path": ".codex/concepts/OX-112-park-background-reference.png",
              "sha256": "2187e280da36621e2fabb9ab9b83c23b789f5f6d5eb8a51e5749bffa7a397dd3"
            },
            {
              "path": ".codex/concepts/OX-112-park-middleground-reference.png",
              "sha256": "87f45059e3795542e24d34bda1da40566ea1460fd80c04660ca5b99b14737cfc"
            },
            {
              "path": ".codex/concepts/OX-112-park-foreground-reference.png",
              "sha256": "4024b67fce2681e0568daa3dffdaf9f48b0d58f5a73c59a38e235e5d823d3a43"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reader-source.svg",
              "sha256": "ff8264613d3f237c81aeac7b09143a354b1a7229e20878ec9a0f0ed0a4712629"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reader-source.png",
              "sha256": "521b53334649e5895a62b8f48d36100dd6728fffea83686f481e9099612301a3"
            },
            {
              "path": ".codex/concepts/OX-112-park-wall-reader-projected.png",
              "sha256": "09af093ec10079cb5cd1bb016a849e3c93290b62f1b9d1a1ffc5a33d5523ccea"
            },
            {
              "path": "design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png",
              "sha256": "a0406148e8e17831812804dfc4571c8f9f35125bcabc61d424d88bf6f2ff0f4c"
            }
          ]
        }
      },
      "outcome": "Installed Urban Park captures show the selected-time weather icon, description, and Current Temp painted in a perspective-mapped, clipped and textured left-wall mural for cloudy, Drizzle, Sunny, and unavailable-condition states. A direct timeline scrub changed the mural to the new selected Sunny reading. Focused hierarchy captures expose the same mural values accessibly; central duplicates of the moved three values are absent, while Wind chill and other facts remain. The tower location inscription is centered and its placement implementation remains untouched. Final Sunny, Drizzle, cloudy scrub, and unavailable captures are recorded; no guide marks appear in the artwork.",
      "evidence": [
        ".codex/test-artifacts/181-ox112-park-wall-reading/README.md",
        ".codex/test-artifacts/181-ox112-park-wall-reading/12-urban-park-drizzle-final.png",
        ".codex/test-artifacts/181-ox112-park-wall-reading/12-urban-park-drizzle-final-ui.xml",
        ".codex/test-artifacts/181-ox112-park-wall-reading/13-urban-park-sunny-final.png",
        ".codex/test-artifacts/181-ox112-park-wall-reading/13-urban-park-sunny-final-ui.xml",
        ".codex/test-artifacts/181-ox112-park-wall-reading/06-urban-park-cloudy-scrub-08-18.png",
        ".codex/test-artifacts/181-ox112-park-wall-reading/06-urban-park-cloudy-scrub-08-18-ui.xml",
        ".codex/test-artifacts/181-ox112-park-wall-reading/11-urban-park-unavailable.png",
        ".codex/test-artifacts/181-ox112-park-wall-reading/11-urban-park-unavailable-ui.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-26",
    "summary": "OX-112 completed: added the selected-time icon, forecast description, and Current Temp as perspective-mapped, cropped, weathered paint on Urban Park's left wall. The new Park reader shares generic scene-core painted-path projection and pigment mechanics; installed checks show it updates during timeline scrubbing and remains accessible. Existing tower location placement code was untouched. Recorded cloudy, Drizzle, Sunny, and unavailable-condition captures with focused UI hierarchies.",
    "verification": [
      "python scripts/dev.py workflow — passed in CLOSEOUT.",
      "python scripts/dev.py contract — passed.",
      "python scripts/dev.py build — passed (57 tasks up to date).",
      "python scripts/dev.py run --serial 8AKY0M32X — debug build installed and launched successfully.",
      "Installed Pixel 3 XL captures and focused UI hierarchy dumps — cloudy, direct scrub, Sunny, Drizzle, and unavailable condition; final Sunny and Drizzle captures inspected with feh at DISPLAY=:0.",
      "git diff --check — passed.",
      "Automated tests were not added or run, per the authorized OX-112 plan.",
      "Scope limit: installed acceptance was limited to Urban Park states on Pixel 3 XL; no broad RTL, TalkBack, font-scale, motion, or device matrix was run.",
      "Tower location placement source was not modified; the existing inscription and Wind chill remain visible in inspected scene captures."
    ],
    "evidence_directory": ".codex/test-artifacts/181-ox112-park-wall-reading/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "181-ox112-park-wall-reading",
  "slice_ids": [
    "OX-112"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-112-park-wall-reading-behavior-board.dot",
    ".codex/concepts/OX-112-park-wall-reading-behavior-board.svg",
    ".codex/concepts/OX-112-park-wall-reading-behavior-board.png",
    ".codex/concepts/OX-112-park-wall-reading-behavior-board.md",
    ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.svg",
    ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.png",
    ".codex/concepts/OX-112-park-wall-reading-ui-artwork-board.md",
    ".codex/concepts/OX-112-park-current-screen-capture.png",
    ".codex/concepts/OX-112-park-background-reference.png",
    ".codex/concepts/OX-112-park-middleground-reference.png",
    ".codex/concepts/OX-112-park-foreground-reference.png",
    ".codex/concepts/OX-112-park-wall-reader-source.svg",
    ".codex/concepts/OX-112-park-wall-reader-source.png",
    ".codex/concepts/OX-112-park-wall-reader-projected.png",
    "design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png"
  ],
  "completed_gates": [
    "Both objective-specific boards and product-owner approval are required before advancing to DRAFT.",
    "OX-112_behavior_board_approved",
    "OX-112_ui_artwork_board_approved",
    "Both objective-specific boards and the complete package are approved by the product owner.",
    "Both approved objective-specific board packages and the complete final OX-112 plan are recorded in the manifest.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-112 exit met by installed weather-state, scrub, hierarchy, tower, and retained-facts evidence."
  ],
  "decisions": [
    "Product owner approved the complete behavior and UI artwork package on 2026-11-26; the board review question is resolved.",
    "The approved boards authorize substantive plan drafting only; implementation remains gated on plan review and activation.",
    "The final plan review is complete; the previously authorized mural implementation may proceed under the approved OX-112 scope. No open material decisions remain.",
    "The user approved both OX-112 board packages on 2026-11-26; no material questions remain."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Added and rendered the OX-112 behavior board and review contract.",
    "Created a side-by-side UI artwork proposal using the installed screenshot and copied Urban Park scene planes.",
    "Corrected the wall's target plane to Foreground after inspecting the source assets.",
    "Superseded the first UI mock after review: its group was only tilted and looked flat against the scene. The current composition follows the reference's oversized weathered temperature, larger rain icon slightly overlapping its right edge, and large expressive Drizzle lettering beneath, then uses a four-corner projective warp, wall-face crop, expanded worn pigment, and visible brick texture. Only the icon, description, and Current Temp move; Wind chill stays in its existing position. The reader remains a separate MURAL_READER layer after Foreground, distinct from the tower's LOCATION_INSCRIPTION. Florence remains centered on the tower in the composition mock, with tower placement code untouched; every scrub updates the selected values.",
    "Removed board-state wording from the behavior and UI artwork content.",
    "Displayed the first UI artwork and behavior boards with feh at :0; those early design artifacts were not installed acceptance evidence and were superseded after review.",
    "Adjusted the mural after the owner's style note: enlarged the weathered 49°, added a distinct rain cloud with turquoise rain strokes overlapping its right edge, and enlarged the brush-style DRIZZLE lettering beneath. Re-rendered the four-corner wall projection and aligned the behavior board, UI artwork notes, roadmap, and plan.",
    "Displayed the revised UI artwork board, the Urban Living Park visual reference, and the behavior board in feh at :0 for owner review.",
    "Recorded behavior board approval for OX-112.",
    "Recorded ui_artwork board approval for OX-112.",
    "Recorded product-owner approval and complete SHA-256 fingerprints for both OX-112 board packages; all board-stage review questions are resolved.",
    "Completed the substantive bounded OX-112 plan, including implementation boundary, selected-time semantics, visual and behavior acceptance, scoped verification/evidence, compatibility, risks, and exclusions.",
    "Full OX-112 plan review passed on 2026-11-26; approved package fingerprints, completed dependencies, scope, acceptance, verification authorization, evidence, compatibility, and exclusions were confirmed.",
    "Activated OX-112 for execution.",
    "Closed slice OX-112 as DONE.",
    "Implemented and installed the selected-time Urban Park wall reader using a shared scene-core painted-path surface; preserved the existing tower location code.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/181-ox112-park-wall-reading/README.md",
    ".codex/test-artifacts/181-ox112-park-wall-reading/12-urban-park-drizzle-final.png",
    ".codex/test-artifacts/181-ox112-park-wall-reading/12-urban-park-drizzle-final-ui.xml",
    ".codex/test-artifacts/181-ox112-park-wall-reading/13-urban-park-sunny-final.png",
    ".codex/test-artifacts/181-ox112-park-wall-reading/13-urban-park-sunny-final-ui.xml",
    ".codex/test-artifacts/181-ox112-park-wall-reading/06-urban-park-cloudy-scrub-08-18.png",
    ".codex/test-artifacts/181-ox112-park-wall-reading/06-urban-park-cloudy-scrub-08-18-ui.xml",
    ".codex/test-artifacts/181-ox112-park-wall-reading/11-urban-park-unavailable.png",
    ".codex/test-artifacts/181-ox112-park-wall-reading/11-urban-park-unavailable-ui.xml",
    ".codex/history/181-ox112-park-wall-reading.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    "AGENTS.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/plans/181-ox112-park-wall-reading.md",
    ".codex/test-artifacts/181-ox112-park-wall-reading/README.md"
  ],
  "next_action": "Inspect the complete final diff, run final git diff --check and python scripts/dev.py workflow, then archive the verified OX-112 cycle."
}
```
<!-- cycle-checkpoint:end -->

## Slice intake record (draft)

**Objective:** Move Urban Park's selected-time reader values from the central readout onto its left wall while preserving exact values and accessible reading.

**Known scope:** Pass the selected weather icon, forecast description, and Current Temp from the existing typed Meteo-backed presentation to a Park-facing renderer. Follow the reference format: make Current Temp the large hero, pair it with the weather icon, and place expressive forecast-description lettering beneath. Map the complete group onto the left brick wall with a four-corner perspective transform, crop it to the masonry face, expand pigment around text/icon paths, and render weathered texture so the wall reads through the paint. The wall belongs to Foreground; draw the reader in its own layer after that pass. Reuse generic glyph and painted-surface mechanics from scene-core, with Park-owned geometry, layout, icon mapping, and material style. Keep Wind chill, the selected location on the tower, precipitation, wind, humidity, timeline behavior, gestures, and other themes unchanged.

**Semantics and boundaries:** Continue consuming presentation values for the authoritative selected instant. Preserve event/source-time meaning, explicit unavailable values, and the accessible reading. The renderer receives the existing selected icon, description, and formatted Current Temp; it does not use provider DTOs or derive new weather facts. Wind chill remains unchanged in its existing presentation position.

**Placement proposal:** The UI artwork board places a large `49°` paired with the rain icon as the hero, then expressive `DRIZZLE` lettering beneath, in the reference's format. Fit it below the lamp, left of the foliage, and above the bench. The full group is perspective-mapped into a four-corner region that follows the receding brick courses, clipped to the wall face, and treated as expanded, worn pigment with wall texture visible. There are no guide marks in the artwork. Every scrub updates those three mural values from the selected time. The mural is a distinct Park layer after Foreground; the composition mock centers `Florence` on the tower without changing its implementation or placement code.

**Out of scope:** New weather facts or icon mappings; moving Wind chill or other values; changes to scene generation, selected-time authority, existing tower location placement code, non-Urban-Park themes, precipitation/wind/humidity facts, timeline or gesture behavior; extracting a new Gradle module; broad accessibility/device/system acceptance.

**Board gate:** Product owner approved the behavior and UI artwork boards and complete package on 2026-11-26. This is design approval, not installed acceptance evidence or permission to claim implementation behavior.

**Approved board package paths:** Behavior source/render/contract: `.codex/concepts/OX-112-park-wall-reading-behavior-board.dot`, `.svg`, `.png`, and `.md`. UI artwork board: `.codex/concepts/OX-112-park-wall-reading-ui-artwork-board.svg`, `.png`, and `.md`; projected source artwork `.codex/concepts/OX-112-park-wall-reader-source.svg` and `.png`; projected mural surface `.codex/concepts/OX-112-park-wall-reader-projected.png`; current-screen capture and three Park plane references; and `design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png` as visual direction. Full per-board fingerprints and owner approval are recorded in the cycle roster above.

**Evidence target:** `.codex/test-artifacts/181-ox112-park-wall-reading/` and `.codex/history/181-ox112-park-wall-reading.md`. No implementation verification has run.

## DRAFT — substantive OX-112 plan

### Objective and bounded scope

Move only Urban Park's selected weather icon, selected forecast description, and Current Temp from the central visual readout onto the left brick wall as dynamic scene artwork. The Park renderer receives the current presentation values for the selected instant. Use the approved reference hierarchy: Current Temp as the oversized, weathered hero; its matching weather icon enlarged alongside and slightly overlapping its right edge; expressive forecast-description lettering beneath. Layout the elements in one local mural group and project that group onto a four-corner region aligned to the wall's receding brick courses. Clip to the brick face, expand worn pigment around text and vector paths, and sample the Foreground plane texture through the paint. The reader has its own `MURAL_READER` pass after `FOREGROUND`.

Use the existing `:scene-core` plane and painted-surface contracts for common projection, clipping, pigment expansion, and texture sampling. Its current `ScenePaintedDataSurface` creates and projects text glyph paths. Extend/refactor only the generic painted-path seam needed so theme-authored text and Park vector-symbol paths share the same group transform and material pipeline. Keep wall geometry, reader layout, icon artwork/mapping, and Park pigment choices in `:app`; no separate Urban Park Gradle module is present or needed. Keep the existing `ScenePaintedDataSurface` caller compatible so the tower inscription retains its placement and rendering.

### Dependencies and data-flow boundary

- **OX-111E (DONE):** supplies `:scene-core` scene-plane geometry and the `ScenePaintedDataSurface` renderer used by the tower location inscription. Reuse its four-corner mapping, face clip, pigment expansion, tint, and source-plane texture sampling. The left wall is on the Foreground plane, unlike the tower face on Middleground.
- **OX-12 (DONE):** supplies the exact selected-time Current Temp binding, its current Imperial display value, original sample time, and explicit unavailable state. Use the already formatted `CatalogTimelineView.temperature` and typed `SelectedTemperatureBinding`; do not reconvert, interpolate, carry, or source temperature independently.
- The active selected instant remains the timeline's authoritative `view.selectedInstant`. Use the current presentation's selected `weatherSymbolId` and its selected description (`weatherSymbolName`, with the same existing `condition` fallback used by the central readout). Pass these inputs together to a Park-owned reader value object. Do not inspect provider DTOs, derive a description from an icon, infer an icon from temperature, or add forecast-selection logic to the scene.
- Keep condition/event source-time disclosure and other supplied evidence in their existing presentation. Unsupported or absent values remain explicit using the current display's unavailable form; do not turn null into zero or assert a new weather fact. Preserve the existing Wind chill reading and its label/value in its current visual position.

### Implementation boundary and ordered work

1. Add a compact Park-facing selected-reader value object and pass it through the `URBAN_PARK` branch in `CatalogTimeLabScreen.kt`. Keep the selected-time binding and event selection upstream in `CatalogTimelinePresentation.kt`; change that presentation only if inspection proves the already-present selected values cannot be passed without adding a new weather rule.
2. Add `MURAL_READER` to `UrbanParkLayer` after `FOREGROUND`. Have `CatalogUrbanParkScene` draw the mural at that layer using geometry measured on the existing `urban_park_foreground.png`; align all four target corners with the wall plane. Leave `UrbanParkLocationSurface.kt`, `LOCATION_INSCRIPTION`, and the tower's Middleground geometry/call site untouched.
3. In `:scene-core`, share its existing glyph path, `Matrix.setPolyToPoly` projection, face clipping, texture sampling, and pigment treatment through a generic path-surface mechanism that accepts theme-authored vector paths. Lay out the temperature glyph path, icon vector path(s), and description glyph path in one reader coordinate space and map the complete group through one four-corner transform. Use the same worn pigment/material processing for glyphs and icon; configure Park style with no drop shadow, card, backing panel, or keyline. Keep the current text surface behavior compatible for the location inscription.
4. Keep the existing central visual readout for other worlds. In Urban Park, stop visually duplicating only the moved icon, description, and Current Temp. Expose the same values once in the mural's accessible semantics, retain their exact labels/unavailable meaning, and leave Wind chill, source-time/carried disclosures, location/picker semantics, all other facts, and the timeline controls intact.
5. Render a selected-time scrub through the existing composition refresh. The symbol, description, and temperature are consumed from the same `CatalogTimelineView`; no wall-clock or animation-time input may retarget the mural.

**Expected implementation paths:** `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`, the Urban Park scene renderer in `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`, `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/UrbanParkComposition.kt`, and `scene-core/src/main/java/com/oxygen/weather/scenecore/ScenePaintedDataSurface.kt`. `CatalogTimelinePresentation.kt`, the selected symbol source, and `urban_park_foreground.png` are read-only inputs unless implementation inspection demonstrates a narrowly required compatibility change. `UrbanParkLocationSurface.kt` and tower-placement code are explicitly excluded from edits.

### Slice-local acceptance

- The painted group matches the approved visual board: large distressed Current Temp, its matching icon slightly overlapping the hero's right edge, and expressive description lettering underneath. It sits below the lamp, left of the ivy, and above the bench. The lettering baseline and icon follow the wall perspective together; all marks are clipped to the wall face, pigment is worn/expanded, and brick detail remains visible through paint.
- Wet/drizzle, clear/sunny, and cloudy installed states display the icon, description, and temperature supplied for the same selected instant. A direct timeline scrub changes all three to the newly selected presentation values without lagging or leaving stale values behind.
- Supported temperature uses the existing selected binding and Imperial display string. Missing/unsupported temperature and condition remain explicit and do not display a fabricated number or weather event. The visible description and icon do not disagree with the presentation's selected event.
- No guide marks, card, panel, keyline, flat overlay, or drop shadow appears. The mural is after Foreground, while the existing tower inscription remains in its existing depth and placement. The generic painted-path change does not shift, skew, clip, or restyle the tower inscription.
- The central visual readout no longer shows a second copy of the three moved values for Urban Park. The mural has a single accessible reading of those values; the existing Wind chill, other forecast values, location selection, source/carried disclosures, timeline semantics, and gestures remain present and accurate.
- Other world routes keep their existing readout and scene order. This is a scoped regression observation only; it does not claim acceptance of those worlds or broad system behavior.

### Authorized verification and evidence

No automated tests are authorized by the current request. Do not add or run tests. After activation, run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, and `python scripts/dev.py build`. Use `python scripts/dev.py run --serial <connected-pixel-serial>` and `python scripts/dev.py screenshot --serial <connected-pixel-serial> --output .codex/test-artifacts/181-ox112-park-wall-reading/<state>.png` for installed acceptance. Confirm a connected/authorized device before execution; if unavailable, record that installed acceptance could not run and leave the slice incomplete.

Capture and inspect the supported drizzle/rain, clear/sunny, and cloudy states; at least one supported direct scrub between different events; and an unavailable selected-time case for explicit fallback semantics. Record the selected time, displayed icon/description/temperature, source-time or unavailable disclosure, accessible node content from a focused hierarchy dump, tower inscription appearance, and Wind chill/other facts. Use a matched pre-change Park capture if available to check that refactoring the shared surface code has not moved or restyled the tower text. Display captured images with `DISPLAY=:0 feh --auto-zoom <image>` for visual inspection; these installed screenshots and interaction records, not the approved boards or projected concept art, are the acceptance evidence.

Store actual screenshots, UI hierarchy output, commands/results, observations, device identity, and limitations under `.codex/test-artifacts/181-ox112-park-wall-reading/`. Run `git diff --check` and `python scripts/dev.py workflow` before closeout. Record exact results and limitations in `.codex/history/181-ox112-park-wall-reading.md`; archive the final plan at `.codex/history/plans/181-ox112-park-wall-reading.md` only after the slice exits or a terminal incomplete outcome is recorded.

### Compatibility, risks, and exclusions

- Preserve the public `:scene-core` boundary: no dependency on app presentation, weather symbols, or Park types. Keep theme-specific symbol paths, layout, geometry, palette, and descriptions in `:app`. Document any new generic path API with KDoc and retain existing text-surface callers.
- The symbol is vector artwork, not text or a flat screen overlay. Use the existing selected `WeatherSymbolId` mapping as the input to Park-owned vector paths; don't add a second forecast interpretation or alter the source event.
- Shared core rendering is also used by the location inscription, so verify its existing on-screen appearance while keeping all tower geometry/placement code untouched. If preserving its output requires changing OX-111E placement or a separate location behavior, stop and record that as out of scope instead of silently changing it.
- No source data changes, new weather facts, icon mapping policy changes, selected-time changes, temperature unit/rounding changes, windchill relocation, general scene reordering, new module, asset artwork edits, theme-selection changes, timeline or gesture changes, broad accessibility/device matrix, or unrelated UI cleanup.
- The behavior and UI artwork boards plus source/reference package were approved by the product owner on 2026-11-26. Their fingerprints are fixed in the cycle manifest. They govern visual hierarchy, position, no-guide/no-card/no-shadow constraints, layer order, the three values, source behavior, and accessibility; installed evidence remains required.

**Open material decisions:** None. The complete approved board package resolves content, hierarchy, wall placement, material treatment, and depth. Implementation work remains subject to final plan review and cycle activation.

## REVIEW — final bounded execution plan

- **Roster and dependencies:** OX-112 is the sole slice in this cycle and the sole `NEXT` roadmap outcome. Its completed dependencies OX-111E and OX-12 are recorded in the roadmap and their archived plans. No in-cycle ordering or dependency question remains.
- **Boards and owner constraints:** The behavior and UI artwork packages were approved by the product owner on 2026-11-26; the complete paths and SHA-256 fingerprints are recorded in the OX-112 manifest. The behavior board controls the three selected-time values, one-group projection, exact source/unavailable semantics, layer depth, shared generic path/surface mechanics, and preserved tower code. The artwork board controls the reference hierarchy, wall position, perspective, worn pigment, visible brick texture, and absence of guide marks, card, backing, keyline, and shadow. The projected artwork and source screenshots remain design references only.
- **Scope and implementation boundary:** The plan changes only the Urban Park readout and its shared painted-surface rendering seam. Theme-specific vector paths, layout, geometry, style, and selection remain in `:app`; `:scene-core` receives only theme-neutral path/surface mechanics, retains its current text-surface call contract, and imports no app/weather/theme types. Existing location geometry and location call-site placement remain untouched.
- **Product and technical invariants:** All three displayed values come from the same existing selected-time presentation. Temperature preserves the OX-12 selected binding, Imperial display value, source time, and missingness; the event icon and description stay from the selected event/fallback already supplied. No new weather logic, providers, fact derivation, interpolation, or carry is added. The central visual duplicate is removed only for Urban Park, while accessible value reading and current Wind chill, other facts, location behavior, disclosures, timeline, gestures, and all other worlds remain intact.
- **Acceptance, verification, and evidence:** The manifest exit and slice-local acceptance enumerate installed wet, clear, cloudy, unavailable, direct-scrub, projected-material, accessibility, and tower-preservation observations. Verification is limited to workflow, contract, build, and the planned Pixel 3 XL install/capture/hierarchy inspection; no tests are added or run without a separate user request. The device is currently connected as `8AKY0M32X` (Pixel 3 XL), but installed acceptance must still be performed after implementation. Actual results belong under `.codex/test-artifacts/181-ox112-park-wall-reading/` and the closeout; board/mockup files do not satisfy visual acceptance.
- **Limitations and exclusions:** No broad RTL, TalkBack, font-size, system, or device matrix is part of this design slice. The shared core renderer can affect the location inscription, so its visible position/material must be checked in installed evidence. Any requirement to modify tower geometry/placement or broaden source-weather selection is outside this approved slice and would require a new owner decision.
- **Review result (2026-11-26):** Full review passed. Workflow validation confirms the pointer, final manifest, complete approved board fingerprints, and dependency-ordered roster; both dependencies are complete and there are no open material decisions. `git diff --check` passes. The connected Pixel 3 XL is available for the planned installed acceptance. No implementation code or tests have been changed or run during planning.

The board gate, objective-specific scope, dependencies, acceptance, authorized verification, evidence path, compatibility constraints, and exclusions are complete. The cycle remains `PLANNED` until execution begins under the already authorized OX-112 implementation request.
