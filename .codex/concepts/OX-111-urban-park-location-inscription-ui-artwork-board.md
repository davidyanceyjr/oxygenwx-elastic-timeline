# OX-111 UI artwork board — location painted on the Urban Park water tower

**Status:** Proposed for product-owner review. The SVG is the editable artwork source; the PNG is its review render. Neither is installed acceptance evidence.

## Board purpose and references

The left panel shows the current Pixel screen as the before-state. The two magnified views propose how the same tank surface could read with the selected place name painted onto it:

- Chicago, fitted on one line.
- San Francisco, wrapped into two lines at the word boundary.

The full-screen screenshot and tower crop are copied from the connected Pixel capture for composition reference. The location still appears in the central readout in that baseline image. The target close-ups are photo-based paint-integration studies generated from that tower crop: OX-111-urban-park-location-inscription-painted-chicago.png and OX-111-urban-park-location-inscription-painted-san-francisco.png. They demonstrate lettering that belongs to the tank surface rather than text placed over the photo.

## Proposed visual treatment

Use the tower’s pale tank face as the writing surface. The lettering must bend with the cylinder and integrate with its surface grain, original highlights, shadow, paint texture, and natural pigment wear. The Chicago and San Francisco studies show brush-painted blue-green enamel with slightly varied edges and opacity. A crisp, uniformly filled text overlay does not meet the visual exit. Size the full name to the face and wrap multiword names as needed. The same renderer handles each location value; a null or blank value leaves the face unlettered.

The target lettering is shown as paint on the tank, not as a UI card or new panel. The generated photos are material references only; runtime lettering remains dynamically drawn from the selected location, not a raster word asset. The visible city line moves from the central readout onto the tower. The location-picker action, remaining meteo facts, forecast timeline, and selected-time indicator remain in the main view. Accessible location text continues to come from presentation state.

## Proposed layer order

Back to front: weather-sky underlay → Park Background → stars → Sun → Moon → clouds → Middleground/tower → tower inscription → Foreground/banner → existing precipitation pass.

The weather-sky underlay remains visible only through transparent Park Background pixels. The inscription is clipped to the tower face, follows the Middleground plane transform, and remains behind the Foreground art and banner.

## Architecture question

The board leaves open whether the Urban Park Kotlin library is a dedicated :urban-park Gradle module or a Park-specific Kotlin package inside :app. The visible scene contract is identical either way; the dependency boundary needs owner direction because current Park composition consumes app-owned presentation types.

## Review prompt

Confirm that the lettering reads as paint integrated into the curved tank rather than a clean overlay. Approve or revise the blue-green brush treatment, surface texture, lighting integration, face clipping, one-line Chicago, two-line San Francisco, and the full Park pass order. Confirm whether the library boundary should be a separate Gradle module or a package within :app.

This board guides the later bounded plan only. It does not authorize implementation, remove the remaining data viewer, or replace installed visual and interaction evidence.
