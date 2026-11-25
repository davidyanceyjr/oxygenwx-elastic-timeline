# OX-108 behavior board — Urban Park cloudy event mapping

**Status:** Draft for owner review; not approved. This behavior board defines the proposed event-to-layer mapping and depth boundary. It does not authorize implementation.

## Proposed behavior

Use the canonical selected weather event for the selected forecast instant as the Park cloud mapping input. Preserve existing event selection: an explicit primary event remains authoritative, and the existing WeatherEventPriority fallback remains in force. Use only the legacy fallback already permitted by canonical projection. Preserve the selected event's source-valid time and carried-state meaning. Do not infer a cloudy event from cloud-cover percentage or an unsupported source code.

| Selected event identity | Active Park cloud layers | Proposed distinct artwork |
| --- | --- | --- |
| PARTLY_SUNNY_CLOUDY | Layer 1 | Broken, separated cumulus forms |
| PARTLY_CLOUDY_NIGHT | Layer 1 | Same form, with existing night sky treatment |
| MOSTLY_CLOUDY | Layers 1 + 2 | Broken cumulus plus a broad, low, broken cloud deck |
| CLOUDY_OVERCAST | Layers 1 + 2 + 3 | Cumulus, broad deck, and a continuous soft overcast veil |

The mapping is event-identity based. Cloud-cover percentage does not choose a shape or add a layer. If the selected event is another ID or no selected event is available, Urban Park emits no OX-108 themed cloud layers. Existing precipitation remains rendered by its existing decision and renderer.

## Proposed Park depth

Back to front:

1. Existing sky palette and optional rain wash.
2. Background image plane.
3. The event-selected cloud stack, in order Layer 1, Layer 2, Layer 3; inactive layers emit no artwork.
4. Existing Sun and Moon cues.
5. Middleground image plane.
6. Foreground image plane.
7. Existing precipitation.

The three cloud layers sit directly above the Background image plane and before Middleground and Foreground. Opaque pixels in those later planes occlude clouds; the Background plane is below the cloud group. Every cloud edge, blur, movement, and transition stays inside the top 33% of the atmospheric scene canvas.

## Preserved behavior and boundaries

- Replaces the two inherited Urban Park scene cloud passes with three new, distinct Urban Park cloud layers.
- Changes Urban Park only. First City and Living Urban Park retain their current rendering.
- Keeps canonical selected-time ownership, primary-event/priority behavior, source-time/carry semantics, sky and Sun/Moon treatment, and existing precipitation depth and behavior.
- Keeps the supplied Park image bytes and transforms, app-owned forecast symbol and text, other forecast values, controls, and timeline behavior unchanged.
- Cloudy forecast symbol artwork in the screen capture is app-owned UI context. It is not one of the atmospheric cloud layers being replaced.

## Reference files

The UI artwork board uses the connected Pixel 3 XL capture at `.codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png` and an upper-screen crop at `.codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png`. The artwork concept composites resized previews of the existing Background, Middleground, and Foreground plane assets with the proposed vector clouds. That composite is a concept, not an installed screenshot or acceptance evidence.

## Review question

Approve this event mapping, the three proposed cloud forms, and their placement immediately above the Background image plane and below later scene content?
