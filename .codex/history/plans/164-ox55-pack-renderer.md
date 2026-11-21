# Cycle plan — 164-ox55-pack-renderer

State: CLOSED
Plan maturity: FINAL
Cycle ID: `164-ox55-pack-renderer`
Roadmap items: OX-55
Objective: Instantiate one built and validated data-only pack in the existing renderer with shared selected-time weather.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "164-ox55-pack-renderer",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-55",
      "depends_on": [
        "OX-91",
        "OX-19",
        "OX-92"
      ],
      "status": "DONE",
      "exit": "An installed device capture of a real loaded package shows an alternate authored world/variant under the same selected time, place and catalog as the bundled scene; unsupported renderer requirements do not silently execute.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.dot",
          "approved": true,
          "approved_by": "product owner (selected option 1)",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.dot",
              "sha256": "8a7ee5af8cd41fdd822408ce9c82587c4d69f05f375cc91ec5ae9b985fd2b191"
            },
            {
              "path": ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.svg",
              "sha256": "b972985abe76785f33048efd80775c5bfca73d4e4200fdfe8702c07f331e1b27"
            },
            {
              "path": ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.png",
              "sha256": "956f980aae7c7b9973e5196aea259b47ed239ec1bf904fdf9a07286cda2ba447"
            },
            {
              "path": ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.md",
              "sha256": "5aed2c4db8f63ffae54be8c1ffe890ab8136802094e19255d4a25ce1c9c1ee2d"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-55-pack-renderer-ui-board.svg",
          "approved": true,
          "approved_by": "product owner (selected option 1)",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-55-pack-renderer-ui-board.svg",
              "sha256": "179922d77dd0da45e04c5fbbc596848e7ad15805ad1492931a6630c5214da899"
            },
            {
              "path": ".codex/concepts/OX-55-pack-renderer-ui-board.md",
              "sha256": "b40ac962e6859dca7398cabc8f4bcfa1bcd7bdd2abd5bdedd4758e93d09beb4a"
            }
          ]
        }
      },
      "outcome": "Validated OX-91 olive pack loaded through existing First City CANOPY renderer under the 8 MiB gate; approved debug-only bundled BASELINE proof comparison showed alternate foliage at the same selected time/place/catalog; unsupported requirements and decode/budget failures rejected before commit.",
      "evidence": [
        ".codex/test-artifacts/164-ox55-pack-renderer/README.md",
        ".codex/test-artifacts/164-ox55-pack-renderer/installed-baseline-proof.png",
        ".codex/test-artifacts/164-ox55-pack-renderer/installed-olive-final.png",
        ".codex/test-artifacts/164-ox55-pack-renderer/olive-candidate-tests.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-20",
    "summary": "OX-55 DONE: the OX-91 accepted First City olive pack loads through the existing CANOPY renderer with a predecode and precommit 8 MiB gate. The owner-approved debug-only bundled BASELINE proof and loaded olive captures show distinct foliage at identical selected time, Chicago catalog, and forecast facts; normal First City remains unchanged.",
    "verification": [
      "python scripts/dev.py build: passed.",
      "./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.OlivePackCandidateTest: passed, 3 tests, 0 failures/errors/skips.",
      "python scripts/dev.py run --serial 8AKY0M32X: passed; installed on Pixel 3 XL Android 12/API 31.",
      "Installed baseline-proof and loaded-olive captures inspected; both show selected 2:37 PM Chicago and matching forecast facts; lower foliage differs.",
      "git diff --check: passed; final diff and new source/board files inspected.",
      "python scripts/dev.py workflow: passed at CLOSEOUT."
    ],
    "evidence_directory": ".codex/test-artifacts/164-ox55-pack-renderer/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "164-ox55-pack-renderer",
  "slice_ids": [
    "OX-55"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.dot",
    ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.svg",
    ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.png",
    ".codex/concepts/OX-55-pack-renderer-behavior-board-v2.md",
    ".codex/concepts/OX-55-pack-renderer-ui-board.svg",
    ".codex/concepts/OX-55-pack-renderer-ui-board.md"
  ],
  "completed_gates": [
    "Both OX-55 board packages are owner approved with SHA-256 fingerprints recorded; the approved UI artwork omits the yellow dotted rectangles. No implementation authorization is recorded.",
    "OX-55_behavior_board_approved",
    "OX-55_ui_artwork_board_approved",
    "Focused OX-55 renderer-load measurement passed on the connected Pixel 3 XL; the evidence-based 8 MiB cap and measurement limits are recorded.",
    "The deterministic OX-55 candidate-load accounting rule is resolved from the focused measurement evidence.",
    "Product owner reviewed and approved the OX-55 v2 behavior-board package on 2026-11-20; its current package fingerprints are recorded in the cycle manifest.",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Owner selected option 1 on 2026-11-20: add debug-only bundled BASELINE proof choice, preserve normal First City OLIVE default, and refresh affected board and plan records."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Revised and rendered behavior/UI boards, displayed both with feh, refreshed approved package fingerprints, and reviewed plan relationship for the baseline proof route.",
    "Closed slice OX-55 as DONE.",
    "OX-55 completed with final installed baseline-proof/loaded-olive comparison, focused candidate tests, 8 MiB budget enforcement, and owner-approved revised boards.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/164-ox55-pack-renderer/installed-baseline.png",
    ".codex/test-artifacts/164-ox55-pack-renderer/installed-olive.png",
    ".codex/test-artifacts/164-ox55-pack-renderer/README.md",
    ".codex/test-artifacts/164-ox55-pack-renderer/installed-baseline-proof.png",
    ".codex/test-artifacts/164-ox55-pack-renderer/installed-olive-final.png",
    ".codex/test-artifacts/164-ox55-pack-renderer/olive-candidate-tests.xml",
    ".codex/history/164-ox55-pack-renderer.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/164-ox55-pack-renderer.md",
    ".codex/test-artifacts/164-ox55-pack-renderer/README.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Inspect git diff --check and the final complete diff, run workflow validation, then close cycle 164 with exact evidence and limits."
}
```
<!-- cycle-checkpoint:end -->

## SLICE intake — OX-55

The owner selected OX-55 after OX-91 closed. The ordered cycle roster contains
only OX-55; its external dependencies OX-91, OX-19, and OX-92 are all DONE.
The roadmap objective, exclusions, and observable exit are retained verbatim.

### Verified dependency outcomes

- OX-91 produced accepted OX-53 baseline and olive manifest-v2 packs and a
  complete validated asset set. Its validator enforces exact member inventory,
  confined paths, registered media, 1.5 MiB resource and aggregate byte caps,
  PNG structure/CRC, exact asset digests, and OX-54 content identity.
- OX-91 leaves PNG decompression/pixel decoding, candidate renderer-load
  memory, and activation to OX-55. OX-52 assigns OX-55 the finite decoded-asset
  and candidate-scene budget before activation. Unknown or unsupported
  required renderer capabilities reject the candidate.
- OX-19's installed scene choice preserved location, catalog identity, exact
  selected instant, forecast facts, timeline state, and gestures without an
  observed fetch or process/Activity restart.
- OX-92 established the shared typed scene declaration shape for First City
  CANOPY and the Minimal Open Landscape tree group, with each using the shared
  selected-instant weather decision.

### Board scope and approval state

Use the actually built and OX-91-validated OX-53 First City olive variant for
the proof. Its required capability is
`first-city.canopy.image-png`, supported by the existing First City CANOPY
path. The approved scope makes this authored variant available from the existing
in-session World control as a temporary validation-build-only choice. A second
debug-only bundled BASELINE proof choice supplies the comparison reference,
because the normal bundled First City already draws OLIVE. The candidate consumes
the complete OX-91 validated result, decodes one bounded candidate asset, and
passes it to the existing CANOPY renderer path. The owner approved validation-
build visibility as drawn; no app-wide catalog or persisted choice is in
scope. The active selected-time weather decision, place, catalog identity,
facts, geometry, layer depth, and bundled First City remain unchanged. The UI
artwork names the lower-foliage shift and contains no yellow dotted rectangles.
There is no installer, persistent catalog, Settings, Park, entitlement, new
weather interpretation, or pack-provided executable behavior. A failed or
unsupported candidate does not execute; detailed missing/invalid package
recovery remains OX-56. The owner-approved v2 behavior-board package records
the validation-build choice and measured deterministic budget. The former v1
behavior package is preserved for audit but is superseded in the roster; the
UI artwork package was revised for the owner-selected proof route; both updated
packages require refreshed fingerprints in the cycle manifest.

### Verification and evidence authorization

Focused OX-55 slice tests are authorized. Other test scopes require separate
owner approval. The roadmap's installed-device capture remains the required
visual exit evidence. The previous full review finalized the plan; this focused
reconciliation does not authorize implementation, builds, installs, or capture.

Planned evidence target: `.codex/test-artifacts/164-ox55-pack-renderer/README.md`.
The pre-finalization memory measurement is retained there. No integrated
renderer capture or slice acceptance evidence exists yet.

## Implementation plan — OX-55

This bounded plan preserves the approved scope and owner-selected debug-only
comparison route. The updated behavior and UI artwork packages are approved
on 2026-11-20; their current file fingerprints are recorded in the roster.
The validation-build view keeps the
written green-to-muted-olive foliage callout and has no dotted highlight
rectangles. Cycle 164 is executing this bounded proof.

### Integration sequence

1. Add the OX-91 accepted OX-53 olive archive as a validation-build-only app
   asset under the debug source set, at
   `app/src/debug/assets/first-city-canopy-olive-v2.zip`. The source is
   `.codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip`
   (1,387,012 archive bytes). Do not add an installer, user file picker, or
   persistent pack catalog. Keep the archive out of release assets.
2. Add temporary `First City · Olive pack` and `First City · Baseline proof`
   entries to the existing `CatalogWorld` choice in the validation build only.
   The baseline proof uses the existing bundled BASELINE drawable in the same
   CANOPY slot. Normal `First City` remains the default and keeps its current
   OLIVE drawable; `Minimal Open Landscape` remains unchanged. Keep the choice
   session-local. Starting a choice prepares the candidate first; the current
   visible world remains selected until a complete candidate is ready. Treat
   the olive request as pending, separate from `CatalogWorldChoice.selected`,
   which remains the committed World-control label and scene choice. Close the
   chooser as usual, but do not call `choose(OLIVE_PACK)` when preparation
   starts. A later World choice supersedes an unfinished olive request; its
   eventual result cannot commit after that later choice.
3. Stage the packaged archive to a file in app-private cache because the
   existing `ScenePackResourceValidator.validate(File)` boundary accepts a
   `File`. Do not read the complete ZIP into one heap array. Pass the staged
   file to the validator and delete the temporary file after validation. Use
   its accepted manifest, exact asset-byte map, and OX-54 content identity as
   one result; rejection exposes no partial candidate. The fixed proof artifact must
   match `packId=first-city-canopy-olive`, `worldDefinitionRef=first-city`,
   `manifestVersion=2`, `engineCompatibility=1`, `rendererPolicyVersion=1`,
   and required capability `first-city.canopy.image-png`.
4. Dispatch only that required capability to the First City CANOPY path.
   Preflight the fixed PNG's 1024 × 1536 dimensions before allocation. With
   checked wide arithmetic compute
   `retainedPngBytes + width * height * 4 + 524288`; reject if the result
   exceeds `8388608` before pixel allocation or decode. Decode one candidate
   image in `ARGB_8888` only after it passes. Keep Android `Bitmap`/Compose `ImageBitmap`
   values in the UI renderer boundary; keep manifest and validator DTOs inside
   the data boundary and do not add image objects to `FirstCityComposition`.
5. Prepare one UI-layer CANOPY candidate from the existing typed CANOPY
   declaration/input and the decoded image. Supply its image to the existing
   CANOPY draw slot. Reuse the existing `FirstCityComposition` and its shared
   `SceneWeatherDecision`; do not recalculate or reinterpret weather for the
   pack. Commit the candidate only after validation, capability checks,
   dimension checks, decode, and budget checks all succeed. On rejection,
   keep the currently displayed world and its committed selection intact.
   Commit the prepared image and `CatalogWorldChoice.selected` together for
   the still-current olive request. Existing bundled choices may commit
   immediately and supersede any pending olive request. The World control
   and chooser selected state always reflect the committed choice.
6. Update `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` with the measured OX-55
   renderer-load cap, its counted allocations, enforcement point, rejection
   behavior, and a link to the measurement evidence.
7. Capture the debug-only bundled baseline proof and loaded olive pack on the installed
   validation build at the same selected instant, place, and catalog.

### Renderer-load budget

Use a fixed **8,388,608-byte (8 MiB)** cap for the OX-55 validation-build
olive candidate. The deterministic admission formula is
`retainedPngBytes + decodedPixelBytes + 524288`, where
`decodedPixelBytes = width * height * 4` for one `ARGB_8888` candidate bitmap.
Compute the sum with checked wide arithmetic after PNG-header dimension
validation and before pixel allocation or decode. Admit only if the sum is at
most `8388608`; apply the same cap before candidate commit. The staged archive
is disk-backed and is not part of this retained candidate-load sum. The
`ImageBitmap` wrapper shares the decoded bitmap and is not counted as another
pixel buffer.

For the fixed OX-91 accepted asset, the deterministic estimate is
1,386,266 + 6,291,456 + 524,288 = **8,202,010 bytes**, leaving 186,598 bytes
below the cap. The authorized measurement used asset digest
`60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d` on a
Pixel 3 XL running Android 12/API 31. It recorded 1,386,266 retained encoded
PNG bytes, a 6,291,456-byte bitmap, 159,088 bytes of native peak above the
bitmap allocation, and a 49,152-byte Java heap peak for decode and typed
CANOPY/ImageBitmap candidate preparation. The measured load is 7,885,962
bytes, leaving 502,646 bytes below the cap. Native plus Java overhead is
208,240 bytes, within the 524,288-byte reserve with 316,048 bytes remaining.
Do not add the sampled peaks again on top of the reserve.

The measurement is specific to this artifact and one device. It does not
measure the integrated screen's compositor/GPU allocations or establish
behavior across devices; this gate bounds retained OX-55 candidate load rather
than total process memory. Execution must enforce the formula before
allocation and candidate commit on the integrated path and keep rejection
atomic. Use checked arithmetic and one candidate image only. The full
measurement command, device record, raw output, and scope limitations are
preserved in `.codex/test-artifacts/164-ox55-pack-renderer/README.md`. Update
`docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` during implementation with this
formula, fixed reserve, counted allocations, enforcement boundary, rejection
outcome, and the evidence link.

### Functional invariants

- Preserve the exact selected instant, place, catalog identity, forecast facts,
  shared weather decision, timeline state, and gesture behavior across the
  choice.
- Preserve First City CANOPY element identity, authored bounds, pass, geometry,
  draw position, and layer depth. Only the authored canopy image changes.
- Keep the bundled First City available. Unsupported policy versions,
  unsupported required capabilities, invalid PNG decode, and budget rejection
  never activate a candidate or partially replace the displayed scene.
- Treat pack identity, digest, and OX-54 content identity as separate from
  renderer support, trusted origin, and entitlement. Packs supply no weather
  rules or executable behavior.

### Scope exclusions

No local installation, persistent catalog, general hot swapping, Settings,
Park, entitlement, arbitrary world/asset mapping, optional capability
fallbacks, new weather interpretation, broad accessibility/system matrix, or
OX-56 recovery UI. Keep the proof choice inside the validation build.

### Authorized verification and evidence

- Add and run only focused OX-55 tests for accepted validated bytes mapping to
  the supported CANOPY renderer input, preservation of shared selected-time
  weather and scene geometry, and rejection before activation for unsupported
  requirements, decode failure, or budget excess. The pre-finalization
  Android measurement is retained as a baseline; focused execution checks must
  enforce the 8 MiB cap on the integrated candidate path. Reuse OX-91's
  existing validator coverage; do not duplicate its archive-validation
  suite.
- Other test scopes require separate owner approval. Do not add full-suite,
  RTL, TalkBack, accessibility, or broad environment matrices to this slice.
- The required installed visual evidence is a baseline/loaded-olive comparison
  showing the same selected instant, place, catalog identity, and visible
  forecast facts. Record the device/build, loaded pack identity, capture paths,
  focused checks, results, and limitations in
  `.codex/test-artifacts/164-ox55-pack-renderer/README.md`.
- The capture is slice acceptance evidence; this plan records no
  integrated-renderer execution result and does not activate the cycle.
