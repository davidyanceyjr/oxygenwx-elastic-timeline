# OX-32 behavior board proposal

**Status:** Pending product-owner review and approval. The behavior board is a
proposal and does not authorize implementation.

## Outcome and review boundary

Switch from the current bundled free scene to one OX-57-installed,
OX-31-cataloged `APP_OWNED / COMPATIBLE` local theme and back during the same
running Activity and process. The visible scene changes only after OX-55 has
prepared a complete, supported renderer candidate within its existing 8 MiB
candidate-load bound. The current complete scene remains visible during
preparation; activation must not display a partial mix of two scenes.

Use the existing World chooser as a validation-build proof surface. It lists
the bundled free choice and the single installed compatible pack for this
slice. OX-69 remains responsible for the later Settings selection surface.
The installed entry is assumed to be present in the live process through the
OX-57/OX-31 same-session handoff; cold-relaunch restoration belongs to OX-58.

## Established dependency facts

- OX-19 established that a World choice can change the rendered composition
  while retaining the active location, catalog revision, exact selected
  instant, facts, timeline state, and gesture ownership, without a fetch or
  Activity/process restart.
- OX-55 established the First City canopy capability and an 8,388,608-byte
  candidate-load limit. Unsupported renderer requirements, a failed decode,
  or an over-budget candidate leave the committed World choice and visible
  scene intact. Its earlier proof choice did not exercise the installed
  OX-31/OX-57 catalog path.
- OX-57 installs a complete validated pack atomically and exposes an
  app-owned catalog entry in the installing process. OX-57 does not activate
  the rendered scene. Its device evidence includes a built baseline pack and
  a distinct olive replacement artifact.
- OX-31 defines bundled-free and app-owned entries with compatibility status.
  OX-58 owns finding those registrations again after a cold relaunch.
- The ordinary First City rendering path currently uses the olive canopy.
  OX-55's approved BASELINE proof choice supplies a visibly different bundled
  canopy for comparison. The artwork therefore proposes installing the
  baseline canopy as the alternate pack, so free → installed → free visibly
  changes and restores the scene.

## Proposed flow and invariants

1. Start with the bundled free First City visible in the active catalog
   session. Keep the existing selected place, active catalog revision, exact
   selected instant, displayed forecast facts, and timeline state.
2. Open the existing World chooser. It shows First City as bundled free and
   one OX-31 `APP_OWNED / COMPATIBLE` entry installed earlier through OX-57.
3. Select the installed entry. Resolve its complete current generation and
   prepare its supported OX-55 renderer candidate under the existing load
   limit. Do not request or refresh a weather catalog as part of this choice.
4. Keep the committed scene intact until the complete candidate is ready;
   then make the candidate active as one scene. Do not render half of each
   scene during activation. OX-55 candidate rejection leaves the currently
   committed scene visible.
5. Reopen World and choose bundled First City. Restore the free scene in the
   same Activity/process while retaining the same weather and timeline state.
6. Directly affected ordinary timeline gestures continue to work before and
   after each switch. Simultaneous scrubbing and switching is not covered by
   OX-32; OX-59 owns that separate proof.

The visible alternate uses the baseline canopy pack because the installed
candidate must visibly differ from the current free First City, whose normal
canopy is olive. The exact app-managed display name and version are test
metadata, not product identity or ordering rules.

## Scope exclusions

No import/picker UI, Settings route, persisted theme preference, cold-relaunch
restore, entitlement, general package browsing, broad accessibility/system
matrix, or concurrent scrub/switch proof. Do not implement OX-56's missing or
invalid already-referenced-package recovery in this slice. OX-57 owns package
installation; OX-55 owns renderer capability and candidate-load rejection;
OX-32 connects one installed compatible entry to the existing renderer.

## Verification proposal

The bounded plan should authorize focused checks for the live installed
catalog-to-renderer handoff and atomic selection, plus the scoped installed
free → alternate → free interaction. Record the exact active location,
catalog revision, selected instant, displayed facts, timeline state, fetch
count, and Activity/process observation before and after the switch. Capture
the bundled-free, chooser, installed-pack, and returned-free states. Exercise
the ordinary timeline interaction directly affected by the World control;
do not add OX-59's overlapping gesture scenario. No result is claimed by this
board.

## Source records

- `docs/ROADMAP.md#OX-32` — objective, dependency list, and observable exit.
- `.codex/history/plans/155-ox19-scene-choice-independence.md` and
  `.codex/history/155-ox19-scene-choice-independence.md` — established
  session-local choice and weather-preservation contract.
- `.codex/history/plans/164-ox55-pack-renderer.md`,
  `.codex/concepts/OX-55-pack-renderer-behavior-board-v2.md`, and
  `.codex/test-artifacts/164-ox55-pack-renderer/README.md` — validated
  renderer path, installed visual distinction, and candidate-load bound.
- `.codex/history/plans/166-ox57-atomic-pack-install.md` and
  `.codex/test-artifacts/166-ox57-atomic-pack-install/README.md` — installed
  generation and live-process catalog handoff.
- `.codex/concepts/OX-57-atomic-pack-install-behavior-board.md` — app-owned
  install boundary.
- `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` — ownership of renderer and
  installation resource bounds.
