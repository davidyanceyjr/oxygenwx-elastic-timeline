# Cycle plan — 166-ox57-atomic-pack-install

State: CLOSED
Plan maturity: FINAL
Cycle ID: `166-ox57-atomic-pack-install`
Roadmap items: OX-57
Objective: Install one validated local scene pack atomically into app-owned storage and the installed catalog.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "166-ox57-atomic-pack-install",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-57",
      "depends_on": [
        "OX-31",
        "OX-91"
      ],
      "status": "DONE",
      "exit": "On device, a valid generated artifact becomes selectable in the installed catalog. A deliberately invalid/oversized/escaped package or interrupted import leaves no partial install or displaced previously valid package; weather data is unchanged.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-57-atomic-pack-install-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-57-atomic-pack-install-behavior-board.dot",
              "sha256": "a3944872c3b3205b648528b2a7a871a7eb5a9818e6f422da6c8dc55140dbb780"
            },
            {
              "path": ".codex/concepts/OX-57-atomic-pack-install-behavior-board.svg",
              "sha256": "b93a4976b49a3fe9d51fb942de66702fd2130bc169765ae7b9d813ec498de57d"
            },
            {
              "path": ".codex/concepts/OX-57-atomic-pack-install-behavior-board.png",
              "sha256": "7dcd241440329a4c1909154fa122be7463bbad30df7e6cfa1a64fdfbead331dd"
            },
            {
              "path": ".codex/concepts/OX-57-atomic-pack-install-behavior-board.md",
              "sha256": "aab32dd3fb6e0797fe80c3b1460aa7b195efd8b40c64102fb3c3b07960eb1c24"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "A generated local pack installed as an APP_OWNED/COMPATIBLE catalog entry on Pixel 3 XL; an interrupted same-pack v2 replacement left v1 and its committed generation current, kept the candidate invisible, and preserved the non-empty OX-05 weather facts and selected instant. Focused installer tests, build, and execution documentation completed.",
      "evidence": [
        ".codex/test-artifacts/166-ox57-atomic-pack-install/README.md",
        ".codex/test-artifacts/166-ox57-atomic-pack-install/device-baseline-install-log.txt",
        ".codex/test-artifacts/166-ox57-atomic-pack-install/device-import-log.txt"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-21",
    "summary": "OX-57 delivered a bounded app-private scene-pack installer and debug-only local File handoff. On Pixel 3 XL/API 31, the generated baseline became an APP_OWNED/COMPATIBLE catalog entry. An injected interruption after the first resource write during same-pack v2 replacement left v1 and its committed generation current, kept v2 invisible, and preserved the non-empty OX-05 weather catalog, its 1,057 facts, fact digest, and selected instant across both imports. Added install budget/failure documentation and retained exact artifacts, logs, hashes, and package-state evidence. Cold-relaunch recovery, process-death/power-loss behavior, renderer loading, broad system acceptance, and visual acceptance remain outside this slice.",
    "verification": [
      "./gradlew :app:compileDebugKotlin — passed.",
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackInstallerTest — passed, 6 tests.",
      "python scripts/dev.py build — passed.",
      "Both python scripts/build_scene_pack.py artifact-generation commands — passed; artifact hashes are recorded in cycle evidence.",
      "adb -s 8AKY0M32X install -r app/build/outputs/apk/debug/app-debug.apk — passed.",
      "Pixel 3 XL/API 31 baseline device import — outcome INSTALLED; APP_OWNED/COMPATIBLE candidate visible; weather/time snapshots identical with 1,057 facts.",
      "Pixel 3 XL/API 31 interrupted same-pack replacement — outcome INTERRUPTED; v1 current, v2 invisible, prior generation only on disk, and weather/time snapshots identical.",
      "git diff --check — passed.",
      "python scripts/dev.py workflow — passed at EXECUTE and CLOSEOUT; closeout validation returns the cycle to IDLE."
    ],
    "evidence_directory": ".codex/test-artifacts/166-ox57-atomic-pack-install/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "166-ox57-atomic-pack-install",
  "slice_ids": [
    "OX-57"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-57-atomic-pack-install-behavior-board.dot",
    ".codex/concepts/OX-57-atomic-pack-install-behavior-board.svg",
    ".codex/concepts/OX-57-atomic-pack-install-behavior-board.png",
    ".codex/concepts/OX-57-atomic-pack-install-behavior-board.md"
  ],
  "completed_gates": [
    "OX-57_behavior_board_approved",
    "OX-57_dependencies_OX-31_OX-91_done",
    "OX-57_numeric_install_limits_owner_approved",
    "OX-57_full_plan_review_passed",
    "OX-57_device_proof_path_resolved",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-57_baseline_install_visible_as_APP_OWNED_COMPATIBLE",
    "OX-57_interrupted_replacement_preserves_prior_generation",
    "OX-57_nonempty_weather_facts_and_selected_time_preserved",
    "OX-57_install_documentation_completed",
    "OX-57_slice_exit_met",
    "OX-57_diff_review_passed",
    "OX-57_execution_evidence_complete",
    "OX-57_workflow_validation_passed"
  ],
  "decisions": [
    "Owner selected the existing OX-05 synthetic debug catalog fixture on 2026-11-21 as the pre-import weather state. Activate its stable dataset at 2026-10-28T17:00:00Z before OX-57 baseline and interrupted-replacement proofs; this adds no product metadata or production UI.",
    "Owner selected the debug-only synthetic bundled-free metadata fixture on 2026-11-21; composer metadata is test-only and transient, while the installed entry is read from the committed OX-57 registration."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented the bounded app-private installer and debug-only File handoff; focused installer tests passed (6), debug build passed, and the baseline device install composed as APP_OWNED/COMPATIBLE.",
    "Recorded the initial empty active-weather state as an acceptance gap; owner resolved it by selecting the existing OX-05 debug fixture and the device proof was rerun.",
    "On Pixel 3 XL/API 31, installed the generated baseline as APP_OWNED/COMPATIBLE and proved unchanged non-empty weather facts, dataset, and selected instant before and after install.",
    "Injected interruption after the first staged resource of a same-pack v2 replacement; v1 remained current, v2 stayed invisible, and the weather snapshot remained identical.",
    "Added OX-57 installation behavior/budget/failure documentation and exact device evidence, logs, hashes, and package-state captures.",
    "Closed slice OX-57 as DONE.",
    "Recorded OX-57 as DONE after completing its device acceptance, focused checks, documentation, evidence, and final diff review.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/166-ox57-atomic-pack-install/README.md",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/baseline-install-log.txt",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/baseline.ox30pack",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/replacement.ox30pack",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/device-baseline-install-log.txt",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/device-import-log.txt",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/device-post-interruption-files.txt",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/device-post-interruption-pointer-hex.txt",
    ".codex/history/166-ox57-atomic-pack-install.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/166-ox57-atomic-pack-install.md",
    "docs/ROADMAP.md",
    "docs/SCENE_PACK_INSTALLATION.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    ".codex/test-artifacts/166-ox57-atomic-pack-install/README.md",
    "scripts/codex_cycle.py"
  ],
  "next_action": "Archive cycle 166 with the exact verification results and recorded limitations, then validate the closed workflow state."
}
```
<!-- cycle-checkpoint:end -->

## SLICE intake — OX-57

The owner selected OX-57 as the sole global `NEXT` for cycle 166 board and
plan preparation. The roster remains one slice. Its direct dependencies,
OX-31 and OX-91, are DONE; no implementation or verification work is
authorized by this planning cycle.

The roadmap objective and observable exit are recorded verbatim in the cycle
roster. OX-31 supplies the read-only installed-theme catalog contract: app-owned
entries use `packId`, catalog-owned display name, and an opaque non-empty
app-managed content/release version. OX-54 content identity is not that version.
OX-91 supplies a complete validated result containing the typed manifest,
validated asset bytes, and content identity; it does not extract or write
files. OX-57 owns bounded staged extraction, app-owned storage, and making the
app-owned registration visible only after commit. OX-55 retains renderer load,
decode, and scene activation. Weather facts and selected time remain unchanged.

The objective-specific behavior/contract board package is
`.codex/concepts/OX-57-atomic-pack-install-behavior-board.dot` with retained
SVG/PNG renders and a companion contract. The product owner approved this
package on 2026-11-20. UI artwork does not apply because the approved
developer-only local `File` handoff makes no app-visible presentation change.
The board and plan record the explicit app-owned catalog metadata inputs. The
approved companion retains its proposal and pending-review wording; the owner
approval and resolved decisions recorded in this plan govern OX-57. Keep the
approved board package and fingerprints unchanged unless an approved input
changes or evidence contradicts it.

Evidence targets are `.codex/test-artifacts/166-ox57-atomic-pack-install/README.md`
and `.codex/history/166-ox57-atomic-pack-install.md`. They are targets only;
no tests, build, install, screenshots, or device checks have been run for this
cycle.

## FINAL execution plan — OX-57

This is a bounded execution plan based on the product-owner-approved behavior
board and the owner's 2026-11-21 approval of its evidence-backed numeric
limits. The cycle remains `PLANNED / REVIEW`; finalization does not activate
the slice or authorize implementation or verification.

### Approved scope and constraints

- Accept one device-local OX-30 artifact through the developer-only `File`
  adapter, then call OX-91. Do not add an in-app file picker, Settings screen,
  storefront, or UI artwork.
- Continue only from the complete OX-91 accepted value: typed manifest,
  canonical-path-to-byte-array resources, and OX-54 `contentIdentity`. Do not
  reopen or recopy the ZIP after validation; materialize the accepted bytes.
- Require app-managed `displayName` and opaque content/release `version`
  separately from the manifest. Derive catalog `packId` from the accepted
  manifest. Never substitute `contentIdentity` for catalog version.
- Keep the staged generation private and absent from catalog queries until
  every accepted member and bounded install record is complete. Publish the
  generation reference and its registration through one atomic per-pack commit
  record. The committed record is the only current-generation selector used
  to supply app-owned registrations to the OX-31 composer; do not scan staging
  directories. Resolve staged member paths beneath the private generation
  root.
- OX-91 acceptance is the upstream resource/capability compatibility result
  used by the OX-31 registration. OX-55 remains responsible for image decode,
  candidate load, and scene activation; OX-32 remains responsible for later
  in-session switching.
- Installation changes only app-owned pack storage and registration. Preserve
  the active scene, weather facts, selected time, and bundled-free catalog
  entry.
- OX-31 has no persisted app-owned registry to migrate. OX-57 introduces the
  first app-private installed generation and registration record; OX-58 owns
  cold-relaunch restoration.

### Dependencies and handoffs

| Dependency / consumer | Contract used by OX-57 | Boundary retained |
| --- | --- | --- |
| OX-31 (DONE) | Supply an explicit `AppOwnedThemeRegistration` with manifest `packId`, owner-supplied display name and opaque content/release version, and the upstream compatibility disposition. | Catalog composition stays read-only; OX-57 supplies durable app-owned registration data. |
| OX-91 (DONE) | Validate the local archive before staging and pass only its complete accepted manifest, exact referenced asset bytes, and OX-54 identity. Rejection returns no partial asset set. | OX-91 owns archive structure, manifest/resource policy and identity checks; it does not extract or persist. Its 64-entry, 65,536-byte central-directory, 16-KiB manifest, and 1.5-MiB resource limits remain independent. |
| OX-55 (later) | Consume an installed candidate after catalog visibility when renderer loading is implemented. | Decode, pixel allocation, and scene activation are not OX-57 acceptance. The existing 8-MiB renderer candidate-load budget is not an install budget. |
| OX-58 (later) | No dependency for same-process commit visibility. | OX-58 proves restoration of a successfully installed catalog entry after cold relaunch; OX-57's interrupted-import check is limited to protecting the previously committed generation. |
| OX-32 (later) | No switch is triggered by import. | In-session scene switching and weather-preserving hot swap remain out of scope. |

### Bounded install contract

The installer writes the already-validated referenced resources and one
internal record containing the typed manifest representation, returned
`contentIdentity`, app-owned catalog metadata, and the `COMPATIBLE` disposition
established by OX-91 acceptance. The committed generation supplies the full
OX-31 registration from that record. It carries the returned identity forward
verbatim; it does not derive identity from a reserialized manifest. The
internal record is an app-private persistence format, not a new scene-pack
manifest version or a public pack field. Because OX-91 does not return
original manifest bytes, the install record must not claim to preserve those
exact bytes.

Before writing, compute the canonical output paths and the record size, and
reject a candidate that exceeds any approved limit below. Stage in a fresh
private generation. On any pre-commit rejection, limit failure, write failure,
or deterministic interruption, do not change the per-pack current-generation
record or catalog result. Remove the incomplete stage when the process remains
alive. Before a later attempt for that pack, remove any abandoned stage; if
cleanup fails, abort before allocating another candidate. Commit only after
all staged outputs have been flushed and checked. If replacing the same
`packId`, retain the old generation until the new commit record is in place;
cleanup of the superseded generation is post-commit housekeeping and cannot
roll back a completed commit.

#### Owner-approved evidence-backed numeric limits

The owner approved these inclusive limits on 2026-11-21. They use the existing
OX-91 and OX-26 ceilings and the measured OX-30 examples.

| OX-57 budget | Approved inclusive limit | Basis and accounting |
| --- | ---: | --- |
| Materialized filesystem nodes | 64 nodes per generation | Reuse OX-91's 64-entry ceiling. Count files and required ancestor directories, including the internal install record; do not materialize unused empty ZIP directories. The OX-91 64-entry test includes a 62-directory nested-path fixture, root manifest, and asset, showing why directory nodes belong in the count. |
| Expanded generation bytes | 1,605,632 bytes | 1,572,864 accepted resource bytes (OX-91's aggregate cap) plus a 32,768-byte maximum internal install record. The record cap is two 16-KiB envelopes: the existing OX-26 manifest-input ceiling and a separately bounded app-managed registration/identity envelope. If the typed record exceeds the cap, reject before commit. |
| Candidate plus commit bookkeeping | 1,638,400 bytes | One 1,605,632-byte candidate generation plus at most two 16,384-byte copies of the current-generation commit record while an atomic replacement is in progress. This is the new storage written by one attempt; unrelated packs and filesystem allocation overhead are excluded. |
| Same-pack replacement peak | 3,244,032 bytes | One old maximum generation plus one candidate maximum generation plus the two bounded commit-record copies: `2 × 1,605,632 + 2 × 16,384`. This is a per-target-pack logical-byte peak, not a global app storage quota. |
| Package-specific working data | 1,622,016 bytes | OX-91 accepted resource arrays (1,572,864) plus one serialized install record (32,768) plus one 16-KiB bounded write buffer. Do not make a second complete asset or archive copy. Excludes validator transients, object/runtime overhead and OX-55 renderer loading. |

OX-91's 65,536-byte central-directory ceiling and 16-KiB raw-manifest ceiling
remain upstream checks, not OX-57 output budgets. The builder evidence records
baseline and olive archives at 1,260,645 and 1,387,012 ZIP bytes, with assets
of 1,259,884 and 1,386,266 bytes. The olive resource is 186,598 bytes under
OX-91's 1,572,864-byte aggregate resource limit. No general ZIP transport-size
limit is assigned here: OX-57 writes the bounded accepted byte set rather than
copying or extracting archive transport bytes.

### Observable acceptance and planned verification

The roster exit remains the controlling acceptance statement:

> On device, a valid generated artifact becomes selectable in the installed
> catalog. A deliberately invalid/oversized/escaped package or interrupted
> import leaves no partial install or displaced previously valid package;
> weather data is unchanged.

Planned slice-scoped proof, to perform only in a separately authorized
execution stage:

1. Build or use the evidenced OX-30 baseline artifact. Install the
   baseline from the developer-only local-file adapter and query the OX-31
   catalog after commit. Record its `packId`, display name, opaque app version,
   `COMPATIBLE` validity and returned content identity.
2. Reject OX-91-invalid candidates (including escaped path and oversized
   resource cases) before app-owned writes. At the installer boundary, verify
   destination confinement for canonical nested paths and rejection of a
   path that would resolve outside the stage root. Exercise inclusive and
   one-over node-count and install-record limits. Verify the inclusive
   expanded-generation, candidate-plus-commit, same-pack replacement-peak,
   and package-working-data calculations, and reject any computed total over
   its cap before commit. These totals are derived from the resource, record,
   and commit-record component caps, so do not fabricate an OX-91-accepted
   input that exceeds a total while all of its component caps remain at their
   maxima. In every rejection case, show no partial entry and no change to a
   prior installed generation.
3. With a valid prior generation present, attempt a same-`packId` replacement
   using a distinct app-managed version, then interrupt staging after at least
   one member is written but before commit. Verify the old generation and its
   catalog registration/version remain current, the candidate is not
   queryable, and weather facts and selected time match their pre-import
   values. Trigger this deterministically with a versioned debug-only import
   intent extra, `interruptAfterFirstResourceWrite=true`. After the first
   accepted resource write has completed in the private generation, and
   before the current-generation record is published, the installer injects an
   interruption through its normal pre-commit failure and cleanup path. A
   focused test uses an internal staging-writer failure seam at the same
   boundary. This is an in-process injected failure; it does not simulate
   process death or power loss. OX-58 separately covers cold-relaunch
   restoration of a successful new install.
4. On an installed Android target, record the catalog metadata and weather
   facts/selected time before and after both a successful first install and
   the failed same-`packId` replacement. Keep checks scoped to the import; do
   not claim OX-55 rendering, OX-32 switching, broad system acceptance, or
   visual acceptance.

#### Device invocation and catalog observation

Use the repository's existing debug-only `ActivityVariantDelegate` handoff to
invoke the developer-only installer from a versioned intent delivered to the
already-running `MainActivity`. The intent supplies an app-readable device-local
artifact path, `displayName`, and opaque app-managed `version`; the debug
adapter wraps the path in a `File` and passes it to the installer. Route the
request through `onMainActivityNewIntent` so the current activity and its
weather/time state remain in place. Keep this adapter out of release behavior
and add no visible picker or catalog UI.

For the planned failed-replacement proof, set the versioned debug intent extra
`interruptAfterFirstResourceWrite=true`. This debug-only flag injects a
deterministic in-process interruption after the first accepted resource is
written to staging and before commit-record publication. The installer uses
the same pre-commit cleanup path as a write failure. The resulting
`OX57Install` record reports `outcome=INTERRUPTED`, the still-current
app-owned registration/version, and `candidateVisible=false`; the post-request
catalog query confirms that the prior version remains current and the
incomplete candidate stage has been removed. This hook is
verification-only: it adds no release behavior or visible presentation and
does not claim process-death or power-loss recovery.

After commit, read the committed generation's registration and query
`InstalledThemeCatalog`. Emit a debug-only structured result under the
`OX57Install` log tag with the outcome and, on success, the catalog `packId`,
display name, opaque version, `COMPATIBLE` validity, and returned content
identity. Capture the filtered device log in the cycle evidence. For OX-57,
“selectable in the installed catalog” means present as an `APP_OWNED`,
`COMPATIBLE` query result; Settings presentation and scene selection remain
with OX-33 and OX-32.

For this debug verification query only, supply a synthetic
`BundledFreeThemeMetadata` fixture with display name `Debug bundled-free
fixture` and version `ox57-debug-fixture-v1`. The fixture exists only to call
the OX-31 composer and is neither persisted nor represented as product
metadata or a Living Urban Park revision. Assert the installed pack's actual
registration appears as `APP_OWNED` and `COMPATIBLE`; any bundled-free result
must be labeled fixture-only in the structured log and evidence.

No tests, builds, installs, device checks, or screenshot captures are
authorized or performed by this review. The focused test set and
device proof above are planned acceptance work; obtain explicit execution
authorization before running verification commands. Since OX-57 adds no
visible presentation, no installed visual board or visual-acceptance claim is
planned.

### Evidence and documentation outputs

Execution evidence target: `.codex/test-artifacts/166-ox57-atomic-pack-install/README.md`.
It should record exact commands and results, artifact hashes, each package's
catalog metadata and content identity, the candidate/old-generation state
around a failed replacement, device model/API, and weather/time observations.
Keep any generated artifacts or focused logs in the same directory. Do not
record unrun checks as passes.

Documentation targets for execution are a new
`docs/SCENE_PACK_INSTALLATION.md` describing input, staging, commit, budgets,
and failure outcomes, plus the OX-57 row in
`docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` with owner-approved numeric
limits. Update `docs/INSTALLED_THEME_CATALOG.md` only if the actual durable
registration handoff adds a catalog contract fact; do not change the OX-31
read-only composer contract. Closeout target remains
`.codex/history/166-ox57-atomic-pack-install.md`, with archived plan
`.codex/history/plans/166-ox57-atomic-pack-install.md`.

### Risks and limits

- OX-91's accepted value carries typed manifest data and exact asset bytes but
  not original raw manifest bytes. Persist the returned identity unchanged;
  do not hash a reserialized manifest and call it OX-54 identity.
- The commit primitive's Android/filesystem guarantees must be verified in
  implementation and the installed failure proof. The plan requires one
  catalog-visible commit point and does not prescribe a specific directory
  naming scheme or Android API before that inspection.
- The approved 32-KiB internal-record limit may reject an otherwise OX-91
  accepted manifest plus app metadata. That is an intentional bounded
  installer rejection; no broader OX-91 or manifest cap changes are included.
- This plan does not set ZIP transport-size, decoded-pixel, renderer-memory,
  successful-install cold-restore, active-theme persistence, routine update,
  removal, or distribution limits.
- The OX-57 board and approval fingerprints are fixed in the cycle manifest.
  Plan review must not reopen them unless their approved input changes or new
  evidence contradicts the board.

### Plan status and authorization

The behavior board is approved, all included dependencies are DONE, and the
owner-approved numeric limits are recorded above. The cycle is `PLANNED` at
`REVIEW` with a `FINAL` plan. Execution commands and tests remain unauthorized
by this review; activation requires a separate owner instruction.
