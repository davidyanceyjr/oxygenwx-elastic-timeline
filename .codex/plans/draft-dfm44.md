# Draft cycle plan — DFM.4.4: Local synthetic catalog storage and replay snapshots

State: PLANNED — UNFINALIZED DRAFT
Cycle ID: not assigned; allocate the next chronological ID when this draft is selected.
Roadmap: DFM.4.4
Depends on: DFM.4.1

## Shared authority and handoff

Read `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/README.md`, and the archived [cycle 084 plan](../history/plans/084-dfm4-debug-workbench.md). The original plan's functional invariants, owner decisions, exclusions, restoration contract, canonical 44-local-date horizon, field meaning, and original evidence obligations remain controlling within the assigned scope. The split does not waive an obligation. Cycle 084's partial code is unaccepted working-tree input; inspect and correct it rather than assuming it is complete. Do not overwrite unrelated working-tree changes.

The existing DFM.4 behavior board `.codex/concepts/DFM.4-debug-session-flow-board.md`, overlay artwork `.codex/concepts/DFM.4-normal-debug-overlay-board.png`, catalog artwork `.codex/concepts/DFM.4-catalog-picker-board.png`, and baseline `.codex/concepts/DFM.4-debug-workbench.png` were owner-approved for the original objective. Their scope and constraints remain applicable as parent references. The objective-specific behavior board `.codex/concepts/DFM.4.4-local-catalog-storage-board.md` was owner-approved as written on 2026-11-02. UI artwork is not applicable because this slice is explicitly independent of editor UI; DFM.4.5 owns that presentation. Approval covers canonical SIMULATED materialization, editable SIMULATED round-trip, immutable REPLAY snapshot behavior, explicit-preview revalidation, fail-closed behavior, and LIVE/provider/cache/location isolation.

Review only directly affected behavior; no broad system/RTL/TalkBack matrix. The owner authorized the focused DFM.4 automated tests; no broad test suite. Record exact commands and actual results under the cycle-specific evidence directory. The connected Pixel may be used, and the owner authorized clearing this app's data if necessary, not the device. Preserve the app data until its loss is necessary, and record any clearing and its effect. No unverified exit is DONE.

## Objective and boundary

Build the debug-local catalog authoring and persistence foundation independently of editor UI. Preserve canonical catalog, codec, storage namespaces, selection path and full D-30..D+13 local-date window; do not mutate LIVE/provider/location state or change shared schema.

## Work and acceptance

- Implement debug-local names, authored event-period model and metadata; assign distinct identities avoiding fixture IDs. Materialize supported typed events on the existing synthetic baseline with valid times, timezone identity, DST gaps/overlaps and correct expected support. Outside the authored period retain full-horizon generated coverage. Missing fields stay unavailable; probability and amount stay distinct; no unsupported conditions or weather semantics are invented.
- Save and reopen editable SIMULATED recordings and immutable REPLAY snapshots, with captured SIMULATED origin and distinct replay ID. Verify encoding/window/storage round trips and metadata consistency; failures cannot claim success or activate. Do not import LIVE solar summaries or change storage compatibility.
- Provide a narrow safe activation boundary for explicit saved previews. Missing, invalid, inconsistent or changed-after-validation records fail without activation, fallback generation, replacement writes or false success. If this cannot be achieved using the authorized additive coordinator/debug seam, stop and replan under the original plan's boundary.
- Run focused local lifecycle, missingness, support, DST, identity/provenance, snapshot immutability and changed-after-validation checks; record commands and results. UI/editor and installed visual acceptance belong to later slices.

## Verification and closeout

Run `python3 scripts/dev.py workflow` before work and closeout, `python3 scripts/dev.py build` for changed app code, and only the directly affected focused tests with `./gradlew :app:testDebugUnitTest --tests '<fully-qualified focused test class or method>'`. Identify the exact new or affected test names during implementation; record each command and result. Where installed review is required above, use `python3 scripts/dev.py run --serial <verified-serial>` and `python3 scripts/dev.py screenshot --serial <verified-serial> --output .codex/test-artifacts/<cycle-id>/<state>.png`; DFM.4.6 additionally uses `./gradlew :app:assembleRelease` and records the locally signed release install and checks. Preserve real evidence under `.codex/test-artifacts/<cycle-id>/` (for DFM.4.1, `.codex/test-artifacts/085-dfm41-coordinator-lifecycle/`); inspect `git diff --check` and the full relevant diff, and close only if this slice's exit is met. Record failed or NOT RUN checks and follow-up. Do not activate before prerequisite and board gates are resolved.
