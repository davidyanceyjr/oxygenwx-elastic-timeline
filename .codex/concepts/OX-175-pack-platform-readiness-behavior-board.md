# OX-175 pack platform and cross-world readiness behavior contract

**Status:** Proposed package for product-owner review. Approval is pending. It
does not finalize the cycle, authorize implementation, or claim acceptance.

The companion Graphviz diagram is
`.codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot`; retain its
SVG and PNG renders with this file. The package covers the complete 17-slice
cycle 175 roster. Each row below has a separate trigger, boundary, and exit;
sharing a board does not combine slice acceptance or evidence.

## Shared invariants

- Preserve the selected place, active catalog revision, exact selected instant,
  displayed forecast facts, timeline state, and gesture ownership across
  theme changes. A theme change does not fetch weather or restart the
  Activity/process. Prepare and activate a complete renderer candidate
  atomically. These are inherited from OX-19 and OX-32.
- Keep stable catalog identity, opaque app-managed content/release version,
  manifest version, renderer compatibility, and content-byte identity
  distinct. The catalog is app-owned; it does not scan shared storage or add
  purchase/entitlement behavior.
- Consume the existing selected-instant weather decision. Preserve supported,
  carried, and unavailable inputs, source-valid times, units, and exact
  selected-time facts. Do not add meteorological plausibility rejection or a
  second weather interpreter.
- The OX-41 Living Urban Park remains the bundled free default. OX-62's
  cold-launch fallback returns to that default. OX-56's First City fallback
  remains the narrower approved response to its device-local scene-resolution
  failure.
- OX-93 compiles only its roadmap dependencies. OX-59 and OX-60 are separate
  switch-specific outcomes ordered after OX-93 at the owner's request; OX-93
  does not imply either of their exits.

## Slice-by-slice behavior and exit map

| Slice | Dependency record | Proposed behavior boundary | Independent observable exit |
| --- | --- | --- | --- |
| OX-09 | OX-38, OX-92 | Repeat the same selection at a fixed scene/world and selected instant; only correct demonstrated re-randomization with stable seeds. | Record the sampled repeat result and either prove no change is needed or show a scoped correction with equivalent motion and selection meaning. |
| OX-96 | OX-55, OX-32 | Separate selected-time evaluation, package/scene resolution, first frame, ordinary rendering, and one in-session switch. Measure on the reference device with a defined workload; report results, not an assumed bottleneck. | Reference-device results distinguish the required phases and record observed bottlenecks without claiming unmeasured performance. |
| OX-29 | OX-57, OX-55, OX-56, OX-32 | TEST the free bundled scene, deterministic local artifact, valid/invalid developer-local imports, alternate-world render, and first weather-safe switch. | An installed matrix records no-purchase availability, reproducible artifact/import, invalid rejection, alternate rendering, no-restart switching, unchanged weather truth, and pass/fail/not-run outcomes. |
| OX-58 | OX-57 | On cold launch, restore app-owned committed registrations from private installation state; keep the bundled free catalog entry present. | The same installed pack ID/version is rediscovered after relaunch, and the bundled free entry remains; process-memory-only visibility is insufficient. |
| OX-61 | OX-32, OX-58 | Persist a stable pack/variant selection key separately from weather location and forecast state. Restore only a still-available compatible choice. Settings controls remain out of scope. | A cold launch restores the installed selection and same world without changing weather facts or requiring a purchase provider. |
| OX-62 | OX-61, OX-56 | With a controlled missing/disabled/incompatible saved entry, restore the OX-41 bundled free default and retain truthful catalog status and forecast access. Do not require OX-83 update/removal UI. | Cold launch safely returns to the bundled free world with truthful status and unchanged forecast access, without a commerce dependency. |
| OX-33 | OX-31, OX-58 | Add the bounded Settings list from the app-managed catalog: bundled free, installed compatible names, and active/incompatible status. No selection mechanics, downloads, checkout, or shared-storage scan. | Installed Settings capture shows catalog-backed entries, correct status, and accessible text without scanning shared storage. |
| OX-69 | OX-33, OX-32, OX-61, OX-62 | Select through the existing OX-32 hot-swap path; persist through OX-61; show active state and disable invalid choices. Do not duplicate loading or hide the normal forecast. | On device, free and installed choices switch without restart, persist across relaunch, and expose clear fallback/disabled states with accessible actions. |
| OX-78 | OX-29, OX-61, OX-62, OX-69 | TEST repeated switches, cold restore, missing chosen packs, and recovery under the approved device/launch conditions. | Installed evidence reports selection continuity, no restart, and fallback with explicit pass/fail/not-run outcomes. |
| OX-98 | OX-69, OX-61, OX-62 | TEST Settings announcements, focus order, disabled/unavailable status, selection activation, and exact weather access in the bundled and one installed world. | Installed accessibility evidence covers the list, active selection, disabled status, switch, and recovery with actual outcomes. |
| OX-73 | OX-32, OX-61, OX-96 | Measure repeated valid switches and cold restore after the OX-96 baseline. Record memory, latency, observed issues, and disposition. | Measured switch/restore costs and a retain/follow-up decision are recorded; any optimization is a separate approved slice. |
| OX-39 | OX-24, OX-36 | Extend one existing rendering capability with one definition-declared alternate visual treatment. The First City CANOPY is the evidence-backed candidate; no plug-in renderer or unrestricted shader framework. | Two treatments use the same selected weather, element identity, and depth; installed captures distinguish the treatment. |
| OX-40 | OX-39, OX-92 | Compare the First City CANOPY with the adapted left Open Landscape tree group. Preserve only parity actually supported by those existing renderers. Do not require identical worlds or Park art. | Installed comparison preserves shared weather meaning, forecast access, and unknown-state handling; unsupported capability gaps are recorded. |
| OX-79 | OX-78, OX-36, OX-40, OX-12 | TEST supported, carried, and unavailable source cases across bundled/installed worlds at one exact selected instant, including source time and units. | A scoped matrix records instant, source validity, units, support/missing meaning, and unchanged weather/catalog during switching, with pass/fail/not-run per scenario. |
| OX-93 | OX-29, OX-78, OX-79, OX-98, OX-73 | TEST and reconcile only those build/import, switching, truth, accessibility, and cost records. Keep the platform milestone separate from Park artwork and paid distribution. | An owner-facing readiness matrix distinguishes proven, failed, not-run, and blocked capabilities; record an owner decision or leave it pending without claiming release approval. |
| OX-59 | OX-32 | TEST one bounded overlap of active timeline scrubbing and theme switching. OX-32's ordinary switch invariants are already required. | Installed focused interaction ends with one coherent selected instant and active scene, without stale selection or gesture theft. |
| OX-60 | OX-32 | Replace theme-owned image/animation/hit-region resources along with the scene. Do not introduce an unrelated cache or graphics framework. | Repeated free ↔ alternate switches leave no old visual layer or interactive target; resource ownership is inspectable on device. |

## Dependency order and board use

The cycle order is OX-09 → OX-96 → OX-29 → OX-58 → OX-61 → OX-62 →
OX-33 → OX-69 → OX-78 → OX-98 → OX-73 → OX-39 → OX-40 → OX-79 →
OX-93 → OX-59 → OX-60. Every in-cycle dependency is earlier in the roster;
all external dependencies are DONE. The diagram draws the in-cycle dependency
edges and groups the slices by independently reviewable behavior.

The shared UI artwork board is
`.codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg` with its
PNG render. Its nine gates are OX-09, OX-39, OX-40, OX-59, OX-60, OX-61,
OX-62, OX-33, and OX-69. The other eight slices do not change visible output
in their current roadmap scope and have no UI-artwork gate.

## Source records and constraints

- `docs/ROADMAP.md` contains each objective, dependency, exit, and evidence
  target. The cycle manifest copies the 17 exact exits.
- OX-19 and OX-32 establish same-session choice and weather/timeline
  invariants. OX-31 and OX-41 establish catalog metadata and the bundled-free
  role. OX-55, OX-57, and OX-56 establish complete candidate activation,
  local app-owned installation, bounded resource ownership, and their
  distinct recovery boundaries.
- OX-36 and OX-12 retain selected-instant weather evidence and unavailable
  semantics. OX-24 and OX-92 establish the CANOPY / Open Landscape vegetation
  candidates used to scope OX-39/40.
- The Pixel 3 XL (`8AKY0M32X`) is the recorded reference device. OX-96 and
  OX-73 plans must name their actual workload and measurement method; this
  board makes no latency, memory, frame-time, or pass-threshold claim.
- No implementation, test, build, install, or device check has been run for
  cycle 175. The current request authorizes planning, board preparation, and
  required workflow validation only.
