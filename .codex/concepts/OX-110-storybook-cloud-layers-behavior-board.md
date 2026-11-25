# OX-110 behavior board — Urban Park storybook cloud layers

**Status:** Product-owner approved on 2026-11-25. This approved behavior board guides OX-110 scope; it does not authorize implementation.

**Baseline context:** The preserved pre-slice capture at `.codex/test-artifacts/179-ox110-storybook-cloud-layers/baseline-screen.png` shows the “Cloudy or overcast” label with one small generic cloud over the Park image and a fully visible Sun above. It motivates the artwork change but does not establish a generator defect or serve as acceptance evidence.

## Proposed behavior

Replace the three current Urban Park cloud placeholder forms with Kotlin/Compose-drawn forms inspired by `design_draft/SunLitStorybookClouds.png`. Add declared cloud-layer identities and their composite position to the Park composition data. Keep the forecast decision and scene-generator mechanics unchanged.

Use the existing selected-event mapping:

| Selected event | Active layers |
| --- | --- |
| `PARTLY_SUNNY_CLOUDY`, `PARTLY_CLOUDY_NIGHT` | Layer 1 |
| `MOSTLY_CLOUDY` | Layers 1 and 2 |
| `CLOUDY_OVERCAST` | Layers 1, 2, and 3 |
| Other or absent event | No Urban Park cloud layers |

Cloud-cover percentage does not add or remove a layer. Source-valid-time, carried-state meaning, and selected event identity remain governed by the existing presentation path.

## Proposed composition

Back to front: weather-sky underlay → Park Background plane → stars → selected-time Sun → supported Moon → active Cloud Layers 1, 2, and 3 → base and added Middleground (including the tree) → Foreground. The sky underlay is visible only through transparent pixels in the Park Background. Center-crop the Background, Middleground, and Foreground planes with one shared viewport-covering transform so their alignment and the banner mask remain registered. Clouds overlay Sun, Moon, and star pixels where their forms overlap. The Middleground tree and hanging teal banner above the wooden sign stay in front of the clouds. Existing precipitation remains a separate, unchanged effect pass.

Clouds sit above the Background, star field, Sun, and Moon, and immediately below the first Middleground layer. The base Middleground tree and the Foreground banner sit above the cloud pass. The supplied Background preview contains the banner pixels; the implementation must declare or separate that object as Foreground while preserving its existing artwork and source-space mask. Each active cloud layer is drawn separately and moves on its existing deterministic selected-time track. All cloud pixels and motion remain inside the top 40% of the atmospheric scene viewport, measured from its top edge. The forms paint no backing rectangle, screen-wide veil, or other non-cloud area. The sky underlay remains behind the Park planes and is exposed only through transparent Background pixels; it does not form a separate sky band above the photo.

## Preserved boundaries

- Scene-generator weather decisions, event selection, source-time/carry rules, and selected-instant ownership do not change.
- OX-109 cloud track mechanics and celestial paths remain unchanged.
- The weather-sky underlay is visible only through transparent pixels in the Park Background. The Background, Middleground, and Foreground planes use the same viewport-covering center crop; their source pixels and relative alignment are preserved.
- Sun, Moon, and stars remain behind the cloud group where their geometry overlaps; transparent cloud gaps preserve visible celestial pixels.
- The complete base Middleground, including its tree, is composited after every cloud layer so the tree remains in front.
- The hanging banner above the wooden sign remains in Foreground above the cloud group, even though its source pixels are present in the Background preview. Preserve its artwork and source-space mask.
- Keep the Park plane source artwork, precipitation, forecast UI, timeline behavior, and all other themes unchanged.
- `SunLitStorybookClouds.png` is a visual reference for Compose-authored artwork, not a planned runtime image asset.
- Automated tests, RTL, TalkBack, and broad device/system matrices are not authorized by this planning request.

## Proposed artwork interpretation to review

Use small separated warm-highlighted forms for Layer 1, a broader broken deck for Layer 2, and overlapping soft cloud masses for Layer 3. All three active layers create the overcast state while retaining visible cloud-only contours and transparent gaps. The proposal retains the reference's warm highlights over the Moon-visible state, with the weather-sky underlay visible through transparent Park Background pixels and the Moon treatment behind the clouds.

**Review questions:** Does the board preserve the intended mapping and sky underlay → cropped Park Background → stars → Sun → Moon → Clouds → Middleground tree → Foreground banner order, with the sky visible only through transparent Background pixels, clouds covering overlapping celestial pixels, and the tree and banner over the clouds? Approve or revise the warm cloud highlights in Moon-visible states and the proposed three-layer appearance.

## Reference boundaries

- Completed OX-108 preserves the event-to-layer mapping and source-time meaning.
- Approved OX-109 preserves independent selected-time tracks and cloud position above Background/Sun/Moon but below Middleground. OX-110 makes the Park banner's Foreground occlusion explicit.
- `docs/SPECIFICATION.md` requires all cloud output, including effects and movement, to remain within the top 40% of the atmospheric viewport.

These dependency outcomes constrain this proposed slice; their approvals do not approve the OX-110 board package.
