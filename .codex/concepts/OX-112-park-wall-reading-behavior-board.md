# OX-112 Behavior Board

## Data flow

The authoritative selected instant continues to come from the timeline. The existing Meteo-backed presentation resolves the values for that instant and passes three items to a Park-owned mural renderer: the weather forecast icon, the forecast description (such as Rain, Drizzle, Clear, or Sunny), and Current Temp. Every scrub updates all three from the newly selected instant. The renderer consumes these values as supplied; it does not infer or derive weather facts.

## Painted surface

The mural renderer follows the reference's hierarchy and scale: oversized, weathered Current Temp as the hero; a large illustrated weather icon slightly overlapping its right edge; and bold, expressive forecast-description lettering beneath. Text becomes glyph paths and the weather symbol is supplied as vector paths. Map the complete group to one four-corner wall region so the lettering receives the wall's perspective skew and foreshortening together. Crop it to the brick face, expand the pigment around the glyph paths, and apply the wall texture and worn paint edge treatment. Do not rotate upright text independently or draw a flat overlay over the scene.

The reader is a separate `MURAL_READER` layer after the Urban Park `FOREGROUND` pass. It uses Park-owned wall geometry and style, while the scene-core painted-surface primitives provide reusable glyph layout, projective mapping, clipping, pigment expansion, and plane-texture sampling. Keep the location inscription on the tower in `MIDDLEGROUND`; its code and placement remain untouched.

Only the weather icon, forecast description, and Current Temp move to the mural. Leave the existing apparent-temperature/Wind chill reading and the precipitation, wind, and humidity values unchanged. Preserve the exact selected-time facts, source/carried disclosure, unavailable behavior, and existing accessible reading. The central visual readout no longer duplicates the three values painted on the wall.

## Review contract

- The Drizzle example uses an oversized, distressed `49°`, a large rain illustration slightly overlapping its right edge, then bold hand-painted `DRIZZLE` lettering beneath. The visible caps styling does not change the accessible description `Drizzle`.
- Sunny/clear, cloudy, rain, and drizzle retain the existing icon and description mapping.
- Missing or unsupported values remain explicit and never become zero.
- The reader sits below the lamp, left of the ivy, and above the bench; its full group follows the brick plane and is cropped to the wall face.
- No card, backing panel, guide marks, drop shadow, or overlay-style keyline appears in the mural.
- Wind chill and all other selected facts remain in their current presentation positions.
- Every timeline scrub updates the mural from the authoritative selected instant.

## Architecture boundary

The Park-facing renderer accepts a small value object from the existing typed, Meteo-backed presentation and owns the mural's element layout, wall geometry, symbol selection, and pigment style. Keep the four-corner surface math and material drawing generic in `:scene-core`; keep the theme-specific composition with Urban Park. In this checkout, Urban Park geometry and call-site code are in `:app`, and there is no separate `:urban-park` Gradle module.

## Authority and implementation references

- `docs/SPECIFICATION.md`: selected-time authority, supplied-value fidelity, explicit missingness, and readable forecast facts.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` §5.1: temperature with its matching weather symbol and selected-event description.
- OX-12: exact selected-time temperature binding, units, source-valid time, and unavailable behavior.
- OX-111E: generic plane-aware painted-data surface and Urban Park tower inscription. The left wall is a separate Foreground-plane surface.

The UI artwork board includes the installed capture and the Urban Living Park visual reference as design context. Neither is installed acceptance evidence.
