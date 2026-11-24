# OX-61 theme persistence behavior supplement

**Scope:** Cycle 175, OX-61 only. This supplement refines the shared board's
cross-process continuity wording for OX-61. Same-session theme switches keep
the OX-32 place, catalog, selected-time, forecast-fact, and timeline
invariants. A cold process launch restores the saved compatible theme key;
weather and timeline state follow the existing startup path and are not
persisted by this slice.

## Stable selection key

For the current app-owned catalog model, `InstalledThemeCatalogEntry.id` is
the OX-20 stable `packId`. Persist that identity after an OX-32 installed
candidate commits successfully. Do not pin the key to the app-managed content
version or content identity; OX-58 supplies the current compatible registration
and committed generation at launch.

Persist the key only from the successful candidate-commit branch. If a
different installed choice fails preparation, the existing world and saved key
remain. If preparation of the already-active installed entry fails, retain the
existing `CatalogWorldChoice` behavior: show bundled First City and leave the
saved key unchanged for a later retry. This failure behavior is inherited from
OX-32 and does not change its fallback.

If a user explicitly selects a non-installed world in the existing validation
chooser, clear the saved installed-pack key so a stale installed choice does
not override the newer selection on the next launch. An unavailable saved key
is left intact for OX-62's fallback/status behavior.

## Cold-launch flow

1. The existing startup path restores an active forecast independently of the
theme key.
2. OX-58 reconstructs app-owned registrations from app-private committed
   pointers.
3. Resolve the saved stable `packId` only when the restored catalog contains
   that entry as app-owned and compatible.
4. Use the existing OX-32 candidate preparation and atomic activation path to
   make the saved world visible.
5. If the saved entry is unavailable or incompatible, leave that branch to
   OX-62. OX-61 does not erase or reinterpret the key.

## Acceptance boundary

- During a successful same-session world switch, the place, catalog revision,
  exact selected instant, facts, and timeline state remain unchanged, as
  established by OX-32.
- After a cold launch, the same still-available compatible installed world is
  restored and forecast access remains available.
- OX-61 does not promise that location, catalog revision, exact selected
  instant, forecast facts, or timeline scale survive process death. They are
  governed by the current weather startup behavior.
- No Settings UI, purchase provider, weather-state persistence, unavailable
  selection fallback, or broad accessibility/system matrix is added here.

The shared cycle behavior and artwork boards remain unchanged. This
supplement controls only OX-61 where their broad wording implies weather/time
continuity across process death. It is a scope decision, not installed
acceptance evidence.
