# OX-31 behavior/contract board proposal

Status: Pending product-owner review and approval.

## Scope represented

The board defines the observable boundary of an app-managed installed-theme
catalog query. It includes the never-removable bundled free entry and known
app-owned entries with distinct compatible and incompatible validity states.
Only registered, app-owned records are query inputs; unregistered filesystem
content is not globally scanned or enumerated.

Each catalog entry carries a stable identity, version, display name, origin,
and validity state. The proposed display name is catalog-owned metadata; it is
not inferred from OX-20's opaque `packId`. OX-20 `packId` remains the stable
logical identity for app-owned packs. Built-in entries use an app-defined
stable identity and remain present independently of pack records.

## Dependency constraints

- OX-20 defines `packId` as a stable logical identity. Its `manifestVersion`
  versions the closed manifest envelope; it does not identify pack content
  revisions.
- OX-53's `rendererPolicyVersion` is an independent renderer-capability
  registry version. It is also not a pack content revision.
- OX-55 supplies the current validated-pack renderer boundary. OX-31 exposes
  known compatibility state; it does not add another validator or activate a
  scene.
- OX-57 retains package installation, OX-32 retains in-session hot swap, and
  OX-56 retains failed-resolution recovery. OX-31 adds no Settings or purchase
  provider UI and makes no free-default selection, preserving OX-41's future
  product decision.
- Catalog queries do not interpret weather or mutate place, catalog revision,
  selected instant, forecast facts, or timeline state.

## Owner decision needed before plan finalization

The roadmap requires a stable ID/version, but OX-20 and OX-53 define schema and
renderer-policy versions rather than a pack release version. Should OX-31's
entry version identify (a) an installed pack content/release revision supplied
by app-managed metadata, or (b) only the app-managed catalog record revision,
with no claim about pack-content ordering? `manifestVersion` must not be used
as a content version because its approved meaning is the manifest envelope.

## UI artwork applicability

Not applicable: the approved OX-31 scope defines a non-visible catalog
contract and makes no UI or visual change.
