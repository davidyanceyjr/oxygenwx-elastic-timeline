# OX-108 UI artwork board — Urban Park cloudy event mapping

**Status:** Draft for owner review; not approved. The PNG is the review render; the SVG is its editable source. The board uses the connected Pixel 3 XL screen capture as current-state reference and includes an asset-based concept composite. No installed after screenshot is fabricated.

The capture shows the current Urban Park screen in a cloudy/overcast selected-time state. The old atmospheric artwork is the pair of repeated bands across the upper screen. The cloudy forecast symbol over the Park photograph is app-owned UI and stays unchanged.

- Full capture: `.codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png` — 1440 × 2960 PNG, captured 2026-11-24; SHA-256 `cbdbb4f89e409a4ba1086a644509c715800b089958f03eb2c72af9ef0e727e2f`.
- Detail crop: `.codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png` — upper scene crop, 1440 × 650 PNG; SHA-256 `74cd87db4b5329f96a77f572f250c1f7ab67a00cefbfdde7015cfcdb5364d653`.

## Proposed appearance

Replace the inherited repeated atmospheric bands with three new Urban Park cloud forms:

1. Layer 1: separate, broken cumulus forms for PARTLY_SUNNY_CLOUDY and PARTLY_CLOUDY_NIGHT.
2. Layer 2: a broad, low, broken cloud deck added for MOSTLY_CLOUDY.
3. Layer 3: a continuous soft overcast veil added for CLOUDY_OVERCAST.

The active sets are Layer 1; Layers 1 and 2; or Layers 1, 2, and 3. Map from the canonical selected event identity. Do not infer cloud style from cloud-cover percent. The proposed concept composite shows all three layers for CLOUDY_OVERCAST.

The clouds are drawn immediately after the Background image plane. Middleground and Foreground remain later in the stack and can occlude cloud artwork. Keep the full visible cloud output within the top 33% of the scene canvas. Existing sky/day-night treatment, Sun/Moon cues, precipitation, supplied plane images and transforms, forecast UI, controls, other worlds, and timeline remain unchanged.

The concept composite combines the current Park Background, Middleground, and Foreground assets with vector cloud forms. It is explicitly labeled as a concept, not a screenshot or installed acceptance evidence. The capture and crop document only the current screen and the artwork to replace.

## Review question

Approve the three cloud forms, the event-to-layer mapping, and the proposed placement directly above Background and behind Middleground and Foreground?
