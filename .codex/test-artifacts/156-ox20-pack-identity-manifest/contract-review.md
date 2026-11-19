# OX-20 contract review

Source: `docs/SCENE_PACK_MANIFEST_V1.md` and `docs/scene-pack-manifest-v1-examples.json`.
Current-world evidence: `CatalogWorld.FIRST_CITY` and `CatalogWorld.OPEN_LANDSCAPE` in `CatalogTimeLabScreen.kt`; `FirstCityComposition` and `MinimalOpenLandscapeComposition` in the scene presentation files. The contract uses separate, documented reference values `first-city` and `minimal-open-landscape`.

| Example | `packId` | `manifestVersion` | `worldDefinitionRef` | `engineCompatibility` | Envelope / disposition |
| --- | --- | --- | --- | --- | --- |
| valid-first-city | Well-formed | 1 | `first-city` maps to `FirstCityComposition` | 1 | Exactly four fields; valid |
| valid-minimal-open-landscape | Well-formed | 1 | `minimal-open-landscape` maps to `MinimalOpenLandscapeComposition` | 1 | Exactly four fields; valid |
| invalid-missing-identity | Missing | 1 | Known | 1 | Missing required field; invalid |
| invalid-unknown-world | Well-formed | 1 | `unknown-world` is unmapped | 1 | Invalid world reference |
| invalid-unsupported-manifest-version | Well-formed | 2, unsupported | Known | 1 | Invalid version |
| invalid-unsupported-engine-contract | Well-formed | 1 | Known | 2, unsupported | Invalid compatibility |
| invalid-extra-weather-interpretation | Well-formed | 1 | Known | 1 | Extra weather field violates closed envelope; invalid |

The `label`, `expected`, and `manifest` keys are illustrative review-file metadata outside each manifest. No runtime decode, trust decision, installed scene, or device acceptance is claimed.

Verification: `python -m json.tool docs/scene-pack-manifest-v1-examples.json` passed. `python scripts/dev.py contract` is not applicable: its implementation reads build, transport, projection and Android manifest paths, not either OX-20 document. No tests were added or run.
