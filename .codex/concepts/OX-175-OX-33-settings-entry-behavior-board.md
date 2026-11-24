# OX-33 Settings entry and catalog truth supplement

**Status:** Product-owner approved on 2026-11-23. The paired DOT,
SVG, and PNG preserve the route and catalog boundary added after reviewing the
approved shared package.

## Flow

1. On the existing forecast screen, a visible gear beside the World control
   opens Settings. Its accessible name is “Settings”.
2. Settings presents a read-only list derived from explicit app-managed catalog
   entries. It shows the bundled-free Living Urban Park role, compatible
   installed entries, and registered incompatible or disabled entries. Text
   communicates status; color and icon shape are supplementary. App-owned
   versions stay in catalog data; version display is not part of OX-33.
3. The Living Urban Park catalog row uses the OX-41 role name and free status.
   It has no content revision until bundled Park content has an actual
   app-managed revision. App-owned registrations continue to require and retain
   their own app-managed version.
4. Active state follows the actually committed world. Opening and dismissing
   Settings leave the active world, location, selected forecast, facts, and
   timeline state unchanged.

## Boundaries

- The rows do not select or load themes. OX-69 owns selection from Settings.
- Settings reads explicit catalog registrations only; it does not scan shared
  storage or show an absent saved ID as a catalog entry.
- The OX-57/OX-58 debug bundled-free fixture name and placeholder version are
  not user-facing metadata.
- No downloads, checkout, update, or removal controls are added.

The owner selected the gear entry point and the versionless bundled-role
representation after the execution review identified the missing navigation
route and the absence of a real Park content revision. The approved shared
cycle boards continue to govern other slices; this supplement applies only to
OX-33.
