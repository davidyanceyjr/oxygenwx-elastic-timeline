# Scene-pack manifest v1: identity contract

This is the normative, design-time identity envelope for a non-executable scene pack. It describes which existing authored world a pack names; it does not load, install, render, authorize, or interpret a pack. The examples in `scene-pack-manifest-v1-examples.json` are contract examples, not runtime decoder results.

## Closed v1 envelope

A v1 manifest is a JSON object with exactly these four required members. Member names and string values are case-sensitive. Duplicate member names, missing members, `null`, and additional members are invalid. No implicit defaults or coercions apply.

| Member | v1 type and valid value | Meaning |
| --- | --- | --- |
| `packId` | Nonempty JSON string of ASCII lowercase letters, digits, and internal `-` or `.`; first and last characters must be a letter or digit. | Stable opaque logical identity of the pack across revisions. It is not a display name. |
| `manifestVersion` | JSON integer `1` | Version of this envelope and its required-field rules. |
| `worldDefinitionRef` | JSON string `first-city` or `minimal-open-landscape` | Exact reference to one currently authored bundled world definition, as mapped below. |
| `engineCompatibility` | JSON integer `1` | Requires the scene-pack identity contract version 1. This is a compatibility marker for the contract described here, not a renderer capability list or an assertion that runtime loading exists. |

The `packId` syntax makes the identity portable as a data value; its spelling carries no weather, author, trust, or entitlement meaning. A revision of the same logical pack keeps its `packId`. A different logical pack uses a different `packId`. A hash of an archive, its transport filename, its source or trusted origin, and any entitlement are separate concerns and cannot be inferred from this field. This contract does not define a revision number or storage rule.

The exact v1 world mapping is:

| `worldDefinitionRef` | Existing authored definition | Current UI evidence |
| --- | --- | --- |
| `first-city` | `FirstCityComposition` | `CatalogWorld.FIRST_CITY` |
| `minimal-open-landscape` | `MinimalOpenLandscapeComposition` | `CatalogWorld.OPEN_LANDSCAPE` |

The values in the first column are manifest contract values. `CatalogWorld` is an internal chooser enum used only to confirm which worlds currently exist; its enum names and labels are not pack API values. This mapping does not migrate either world or create a public authoring interface.

## Contract disposition and evolution

A record is a valid **v1 contract example** only when every member satisfies the table and the closed-envelope rule. An absent or malformed identity, unknown world reference, unsupported manifest version, or unsupported engine compatibility value is invalid. An extra member containing executable instructions or a weather interpretation is invalid under the same closed-envelope rule. No record may alter place, catalog revision, exact selected instant, forecast facts, canonical projection, or timeline ownership. The bundled free experience remains available independently of pack identity.

Future envelope fields or changed required-field rules need a new `manifestVersion`; a reader of v1 must not silently treat an unknown required field as supported. A future engine contract value or capability policy needs an explicitly defined compatibility rule before it can be accepted. This document defines only the current value `1` for each version member. It does not define renderer capability IDs or optional-capability fallback behavior.

This contract contains no asset paths, digests, resource types, import budgets, package builder, decoder, runtime validation, trust decision, installation, hot swap, UI, or Park reference. OX-51 owns assets and integrity references; OX-52 owns renderer capability and trust policy; OX-53 owns complete asset-bearing manifests; OX-30 owns building; OX-26 owns runtime decoding; OX-91 owns resource validation; OX-55 owns renderer instantiation; OX-57 owns installation. Their later rules must preserve this slice's separation of weather and scene identity.

## Renderer policy relationship

See [Scene-pack renderer compatibility and data-only trust policy](SCENE_PACK_RENDERER_COMPATIBILITY.md)
for the separately versioned renderer capability registry and its trust and
budget boundaries. This reference does not add a field to manifest v1 or
change its closed four-field envelope; OX-53 owns integration under a later
manifest version.
