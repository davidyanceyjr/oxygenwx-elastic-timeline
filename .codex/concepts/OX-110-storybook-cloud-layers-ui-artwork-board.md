# OX-110 UI artwork board — Urban Park storybook cloud layers

**Status:** Product-owner approved on 2026-11-25; conceptual artwork, not installed acceptance evidence.

## Board purpose

Show the proposed Kotlin/Compose cloud artwork in four affected event/celestial states:

- Sun-visible `PARTLY_SUNNY_CLOUDY`: one sparse cloud layer; the Sun remains visible through a gap.
- Moon-visible `PARTLY_CLOUDY_NIGHT`: the same one-layer mapping; the Moon remains visible through a gap.
- `MOSTLY_CLOUDY`: two overlapping broken cloud layers.
- Moon-visible `CLOUDY_OVERCAST`: all three layers obscure the Moon. The same overcast treatment covers the Sun when it is present.

The dashed line in each scene is the cloud-output bound at 40% of the composition viewport height. Back to front, the proposed order is weather-sky underlay (visible only through transparent Park Background pixels) → Park Background → stars → Sun → Moon → Clouds 1–3 → base/added Middleground, including the tree → Foreground. Clouds cover Sun, Moon, and stars where their pixels overlap; transparent gaps reveal them. The base Middleground tree and hanging teal banner above the wooden sign are shown in front of the clouds in every state. The cloudy and overcast examples let the cloud edge meet the tree silhouette so the Middleground occlusion is visible.

## Park sky treatment and plane crop

The weather-sky palette sits behind the Park Background plane and shows only through that image's transparent pixels. The photo supplies the sky where its pixels are opaque; there is no separate sky band above it. The Background, Middleground, and Foreground planes share one viewport-covering center-crop transform, preserving their registration and the source-space banner mask. The four review cards use a center-cropped portrait viewport to show this treatment.

## Visual references and rendered artwork

The board displays two actual application captures so the proposed art can be judged against the existing screen composition and scale:

- `.codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png` — Urban Park screen capture.
- `.codex/concepts/OX-109-current-sun-cloudy-reference.png` — Sun-visible, partly cloudy screen capture.

The cloud style reference is `design_draft/SunLitStorybookClouds.png`, mirrored for rendering at `.codex/concepts/OX-110-sunlit-storybook-clouds-reference.png`. The three painterly strata shown in the state cards are in `.codex/concepts/OX-110-generated-storybook-cloud-layers.png`. That image was generated using both application captures as scene-composition references and the supplied sheet as the cloud-style reference. The board clips the three transparent rows into the selected states; the scene cards use the established OX-108 Park planes to show their relative placement.

The generated PNG is board-only visual direction. It is not proposed as a runtime bitmap dependency: the slice objective remains Kotlin/Compose drawing using the palette, lighting, contour, and volume shown here.

The Park source preview places the hanging banner pixels in the Background image plane. To honor the owner-directed foreground depth, each scene card redraws a tight cutout of that same banner above the clouds and scene planes. This board overlay demonstrates intended occlusion; the implementation should declare or separate the banner as Foreground while preserving its existing artwork and transform. Every state also draws the base Middleground after the cloud pass, leaving the tree in front.

### Image-generation prompt

> Create a high-quality transparent PNG cloud artwork sheet for a weather-scene UI artboard. Reference roles: the first two supplied images are real app screen captures; use them only to understand the existing scene's photographic Urban Park background, atmospheric sky area, scale, and cloud placement. Do not reproduce any text, UI, panels, iconography, or screenshot artifacts. The third image (SunLitStorybookClouds) is the exact cloud-art style reference: match its painterly, softly volumetric storybook cumulus, rounded overlapping forms, warm cream and pale gold sunlit rims, blue-violet cool shaded undersides, soft nuanced brush texture and subtle ambient edge. Render exactly three separate, broad, low-profile cloud strata as a clean sprite sheet on fully transparent background, arranged in three horizontal rows with generous transparent gutters and no overlap between rows. Row 1: a sparse broken band with three small airy cloud clusters and large clear gaps (partly cloudy). Row 2: a medium broken deck that fills more width while retaining sky gaps (cloudy). Row 3: a dense, continuous layered overcast deck that conceals celestial objects when placed in front of them. Keep each row wide and horizontal, with irregular soft edges, natural variation, no outlined cartoon shapes, no icons, no sun, no moon, no sky/background, no ground, no text, no borders, no shadows cast onto a background. Clean alpha transparency around every cloud. Wide 3:2 landscape canvas, high detail, composed for scaling over a portrait weather scene.

## Proposed layer appearances

1. **Layer 1 — partly cloudy:** small, separated forms leave open sky around the Sun, Moon, and stars. Any celestial pixels under a cloud are covered by that cloud.
2. **Layer 2 — cloudy:** a broader broken deck fills more of the sky while preserving visible openings.
3. **Layer 3 — overcast:** a dense overlapping deck covers the Sun, Moon, and stars and most of the upper sky without a painted backing outside the cloud forms. The hanging banner remains visibly in front.

The proposed Moon-visible appearance keeps the warm cloud highlights from the reference over the existing night palette. The product owner may accept or revise this choice.

## Review prompt

Approve or revise the four state appearances, the warm highlight treatment in Moon-visible states, the cloud-only transparency, the top-40% viewport bound, the weather-sky underlay through Park Background alpha, the shared center crop, the Park Background → stars → Sun → Moon → Clouds → Middleground tree → Foreground banner order, and tree/banner-over-cloud occlusion.

This board does not approve implementation, change scene-generator mechanics, or replace installed screenshots and scoped interaction evidence required later.
