# Installed theme catalog contract

The installed theme catalog is a read-only app-managed description of the
bundled-free role and explicitly registered app-owned themes. It contains
identity and display metadata plus a known availability disposition. It does
not load, validate, select, or activate a theme.

## Entry fields

| Field | Meaning |
| --- | --- |
| `id` | Stable catalog identity. The bundled-free role uses the app-defined `living-urban-park` ID. An app-owned entry copies its OX-20 `packId`, which stays stable across content revisions. |
| `version` | Non-empty opaque app-managed content/release revision. Equal values name the same declared revision; the catalog defines no numeric, lexical, or semantic-version ordering. Change it when the installed content/release revision changes, not for display-name or compatibility metadata changes. |
| `displayName` | Catalog metadata supplied by the app. It is not inferred from `id` or `packId`. |
| `origin` | `BUNDLED_FREE` for the OX-41 role or `APP_OWNED` for an explicitly registered pack. |
| `validity` | `ALWAYS_AVAILABLE` for the never-removable bundled-free entry; otherwise the known `COMPATIBLE` or `INCOMPATIBLE` app-owned disposition. |

`InstalledThemeVersion` intentionally does not reuse `manifestVersion`,
`engineCompatibility`, `rendererPolicyVersion`, `contentIdentity`, or an asset
digest. Those values describe separate contracts or exact bytes, not the
app-managed content/release revision.

## Catalog inputs and query

`InstalledThemeCatalog.query` requires `BundledFreeThemeMetadata` containing
the bundled-free display name and version. It always returns that metadata as
the OX-41 role with the fixed `living-urban-park` identity and
`ALWAYS_AVAILABLE`, including when no app-owned registrations are supplied.
OX-31 defines no concrete Park revision because the Park content is not yet
implemented; the app integration supplies its app-managed revision when that
bundled content and metadata exist.

The query accepts only explicit `AppOwnedThemeRegistration` inputs. Each
registration carries the OX-20 `packId`, its app-managed content/release
version, catalog-owned display name, and a compatible or incompatible result
already determined upstream. The catalog maps that result to its validity
field without repeating OX-91 resource validation or OX-55 renderer
compatibility evaluation.

The composer reads only its arguments. It performs no filesystem scan,
persistence, import, package validation, rendering, forecast interpretation,
scene selection, or state mutation. It does not discover unregistered files
or seed the OX-55 debug Olive candidate as an installed entry.

## Handoffs and exclusions

OX-57 owns installation and durable population of app-owned registrations.
OX-33 owns Settings presentation, OX-32 owns in-session theme switching, and
OX-56 owns resolution recovery. The catalog model does not change the current
`CatalogWorld` selection or implement the Living Urban Park renderer. It also
does not define a purchase provider, global discovery, migration, or a live
installed-pack source.
