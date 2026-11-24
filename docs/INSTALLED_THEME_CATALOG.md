# Installed theme catalog contract

The installed theme catalog is a read-only app-managed description of the
bundled-free role and explicitly registered app-owned themes. It contains
identity and display metadata plus a known availability disposition. It does
not load, validate, select, or activate a theme.

## Entry fields

| Field | Meaning |
| --- | --- |
| `id` | Stable catalog identity. The bundled-free role uses the app-defined `living-urban-park` ID. An app-owned entry copies its OX-20 `packId`, which stays stable across content revisions. |
| `version` | App-owned entries require a non-empty opaque content/release revision. The bundled-free role may omit its version until bundled content has a real app-managed revision. Equal values name the same declared revision; the catalog defines no numeric, lexical, or semantic-version ordering. Change it when content/release revision changes, not for display-name or compatibility metadata changes. |
| `displayName` | Catalog metadata supplied by the app. It is not inferred from `id` or `packId`. |
| `origin` | `BUNDLED_FREE` for the OX-41 role or `APP_OWNED` for an explicitly registered pack. |
| `validity` | `ALWAYS_AVAILABLE` for the never-removable bundled-free entry; app-owned entries may be `COMPATIBLE`, `INCOMPATIBLE`, or explicitly `DISABLED`. `DISABLED` is distinct from compatibility: the registration remains known, while app policy currently prevents activation. |

`InstalledThemeVersion` intentionally does not reuse `manifestVersion`,
`engineCompatibility`, `rendererPolicyVersion`, `contentIdentity`, or an asset
digest. Those values describe separate contracts or exact bytes, not the
app-managed content/release revision.

## Catalog inputs and query

`InstalledThemeCatalog.query` requires `BundledFreeThemeMetadata` containing
the bundled-free display name and an optional content revision. It always
returns that metadata as the OX-41 role with the fixed `living-urban-park`
identity and `ALWAYS_AVAILABLE`, including when no app-owned registrations are
supplied. OX-31 defines no concrete Park revision because the Park content is
not yet implemented; the current role metadata uses the owner-approved name
“Living Urban Park” and leaves its version absent until bundled content has a
real app-managed revision. The app-owned registration version remains
required. A missing version is valid only on the bundled-free role.

The query accepts only explicit `AppOwnedThemeRegistration` inputs. Each
registration carries the OX-20 `packId`, its required app-managed content/release
version, catalog-owned display name, a compatible or incompatible result
already determined upstream, and an enabled/disabled app-managed availability
state. The catalog maps those inputs to validity without repeating OX-91
resource validation or OX-55 renderer compatibility evaluation. Disabled
availability takes precedence in the single `validity` field while the entry
remains registered; it does not rewrite the underlying compatibility result.

`DISABLED` is an app-managed availability disposition. It is not inferred from
an incompatibility or a missing registration. OX-62's debug-only acceptance
fixture supplies this status in memory for a controlled cold launch and does
not change the durable install record or add a user-facing disable control.

The composer reads only its arguments. It performs no filesystem scan,
persistence, import, package validation, rendering, forecast interpretation,
scene selection, or state mutation. It does not discover unregistered files
or seed the OX-55 debug Olive candidate as an installed entry.

## Handoffs and exclusions

OX-57 owns installation and durable population of app-owned registrations.
OX-58 restores those registrations from the app-private per-pack committed
generation pointers on MainActivity creation. OX-61 separately stores the
selected app-owned `packId` in app-private preferences after a compatible
installed candidate commits. On cold launch it matches that key to the
restored compatible registration and activates the current committed
generation; it does not persist catalog version, content identity, location,
forecast facts, selected instant, or timeline scale. Selecting a non-installed
world clears the installed-theme key. A missing, disabled, or incompatible
saved key is retained for OX-62. OX-33 owns Settings presentation, OX-32 owns in-session
theme switching, and OX-56 owns resolution recovery. The catalog model does
not change the current `CatalogWorld` selection or implement the Living Urban
Park renderer. It also does not define a purchase provider, global discovery,
migration, or a shared-storage installed-pack source.
