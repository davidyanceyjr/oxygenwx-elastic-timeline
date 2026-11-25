# OX-109 behavior board — Urban Park layered sky and scene depth

**Status:** Approved by the product owner on 2026-11-24. The companion SVG and PNG show the approved intended appearance. This package does not authorize implementation.

## Owner-directed change

In Urban Park, render the scene back-to-front as:

1. Background backdrop: blue daytime sky or star-filled night sky.
2. Base Background Park image plane.
3. Selected-time Sun cue on its sky path, when visible.
4. Supported Moon cue on its sky path, when visible.
5. Active OX-108 cloud type 1, painted as its own layer.
6. Active OX-108 cloud type 2, painted as its own layer.
7. Active OX-108 cloud type 3, painted as its own layer.
8. Base Middleground image plane.
9. Additional Middleground layers, stacked above the base Middleground.
10. Foreground image layer(s), the closest scene artwork to the viewer.

The Background group contains the sky backdrop and base Background image plane, followed by Sun, Moon, and the three cloud layers. Clouds are in front of both celestial cues and the Background, and behind the base and added Middleground layers. Foreground artwork remains closest to the viewer. The existing precipitation effect pass is outside this scene-artwork stack and retains its current behavior.

The Sun and Moon continue their selected-time paths. Each cloud type is drawn independently and tracks across the sky at a rate distinct from celestial movement. This board specifies the relative motion contract, not numeric rates or a speed ranking.

## Cloud overlay contract

- Each cloud type has its own painted layer; only the active OX-108 cloud layers are rendered.
- Pixels outside those forms are fully transparent; the layer paints no solid backing rectangle, broad band, or cloud wash.
- Existing authored alpha inside the cloud forms is retained.
- The Park/sky content below remains visible through all non-cloud pixels.
- Cloud motion is independent per type and uses the selected-time scene context; its rate differs from the Sun and Moon paths. Zoom does not change forecast time or cloud position for a selected instant.
- OX-108's event selection, cumulative layer mapping, source-time/carry behavior, and no-cloud output for other/absent events remain unchanged.

This board references rather than redesigns OX-108's canonical event mapping: `PARTLY_SUNNY_CLOUDY` / `PARTLY_CLOUDY_NIGHT` use Layer 1; `MOSTLY_CLOUDY` uses Layers 1 and 2; `CLOUDY_OVERCAST` uses Layers 1, 2, and 3. The mapping is not inferred from cloud-cover percentage.

## Boundaries and acceptance intent

This change applies to Urban Park only. Preserve the supplied plane assets and transforms, selected-time continuity, existing cue paths/positions, sky treatment, precipitation, app-owned forecast UI, controls, other themes, and timeline behavior. Keep the OX-108 cloud artwork within its existing top-third region. Do not prescribe numerical motion rates in this board.

Installed review should show Sun-visible and Moon-visible cloudy states at multiple selected instants. Where a cue intersects a cloud form, the cloud is composited in front; in visible gaps, the Park/sky beneath each cloud layer remains visible. Review the three cloud types as separately painted layers, verify their relative travel differs from the celestial paths, and confirm additional Middleground art overlays its base plane while Foreground art remains closest. A direct timeline scrub checks selected-time continuity and readable forecast UI.

## Reference and approval

The current installed OX-108 capture at `.codex/test-artifacts/177-ox108-urban-park-cloud-depth/04-overcast.png` and the Sun/Moon captures at `01-partly-sunny-cloudy.png` and `02-partly-cloudy-night.png` are current-state references only. The companion UI board uses existing Park plane previews and schematic vector cues/clouds as a composition proposal, not as an installed after capture. Motion arrows show relationships, not measured rates.

**Review question:** Does this package show the Background → Sun → Moon → three Clouds → base/added Middleground → Foreground hierarchy, the cloud-only transparent layers and their distinct motion relative to celestial paths, and the stated preserved behavior and exclusions?
