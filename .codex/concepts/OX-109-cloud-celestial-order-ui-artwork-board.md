# OX-109 UI artwork board — Urban Park layered sky and scene depth

**Status:** Approved by the product owner on 2026-11-24. The SVG is the editable source and the PNG is its review render. The concept is not an installed after screenshot.

## Reference and proposed states

The left panel uses `.codex/concepts/OX-109-current-sun-cloudy-reference.png`, copied byte-for-byte from the installed OX-108 Sun-visible cloudy capture, as the current-screen reference. The center panels show proposed Sun-visible and supported Moon-visible cloudy composites using resized previews of the existing Background, base Middleground, and Foreground planes. Both selected events retain OX-108's mapping to Cloud type 1; the hierarchy panel shows all three independently painted cloud types and their tracks.

The far-to-near scene-artwork order is:

1. Background backdrop: blue sky or star-filled night sky.
2. Base Background Park image plane.
3. Sun on its selected-time sky path.
4. Supported Moon on its selected-time sky path.
5. Cloud type 1, type 2, and type 3 as separate painted transparent layers, active according to OX-108's cumulative event mapping.
6. Base Middleground image plane.
7. Additional Middleground layer(s), stacked above the base Middleground.
8. Foreground layer(s), closest to the viewer.

Existing precipitation remains a separate effect pass and is not depicted as a scene-artwork plane. The artwork preserves the existing OX-108 cloud forms, event mapping, sky region, cue paths/positions, and OX-99 plane transforms.

## Intended appearance

- Sun and Moon remain behind clouds where their pixels overlap.
- Each cloud type is drawn independently and has its own sky track. Cloud motion rates differ from the Sun and Moon paths; no numeric rates or speed ranking are prescribed here.
- Every cloud layer has alpha 0 outside its active cloud forms. The Park and sky remain visible through all gaps.
- Middleground additions sit above the base Middleground and all cloud layers. Foreground remains the closest scene artwork.
- The dotted celestial arcs and cloud arrows are schematic motion cues, not measured trajectories or rate values.
- The existing app-owned forecast symbol, text, settings, controls, and timeline remain unchanged and are not redrawn in the plane-only concept.

## Source assets and limits

The composition references `.codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png`, `...-park-middleground-preview.png`, and `...-park-foreground-preview.png`. Those preview images are resized copies of the already approved plane art. Added Middleground layers are represented in the hierarchy, not invented as new art. The concept does not alter asset bytes or transforms and does not stand in for OX-109 installed acceptance.

**Review question:** Does this board accurately show the complete scene hierarchy, three independent transparent cloud layers tracking differently from celestial paths, and the Sun-visible/Moon-visible compositions?
