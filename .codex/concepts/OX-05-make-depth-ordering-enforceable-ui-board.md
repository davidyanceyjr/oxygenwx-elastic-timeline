# OX-05 UI artwork board — First City depth overlaps

**Approval record:** Current approval status and package fingerprints are recorded in `.codex/plans/144-ox05-depth-ordering.md` and `docs/ROADMAP.md`. The product owner approved this artwork package for cycle `144-ox05-depth-ordering`; the precipitation-depth choice remains open and approval does not authorize implementation.

**Artwork:** `OX-05-make-depth-ordering-enforceable-ui-board.svg` (editable SVG source) and `.png` (review render).

## Visual objective

Show a 2D view into the world selected by the current theme. Outer space and stars form the far Background; the Sun is farther than the Moon, so render Sun → Moon within Background. Nearer atmosphere and cloud layers may occlude both cues. The complete scene follows `Background < Middle-Ground < Foreground` with ordered sublayers inside each group. Preserve the existing First City readout, forecast facts, and timeline.

## Target and open choice

- **Sun and Moon target:** Outer space/stars → `TIME_CUE` (Sun) → `LUNAR_CUE` (Moon) within Background. The Moon may draw in front of the Sun. Nearer background clouds/architecture, Middle-Ground atmosphere/clouds, and foreground city content draw later and may occlude the celestial cues.
- **Moderate-rain target:** Overlapping small-to-medium cloud clusters span the full sky, with cloud layers in Background and Middle-Ground. The single precipitation field begins below the cloud cover and spans the scene. The two options change only whether near buildings occlude that field; no rain is shown under clear sky in these visible-cloud examples.
- **Rain A — Middle-Ground field:** near buildings occlude the existing single precipitation field.
- **Rain B — Foreground field:** the single field crosses in front of near buildings, matching the current draw sequence.

The celestial depth is owner-directed and is not a remaining choice. Select rain option A or B, or request a bounded revision. OX-05 adds no near/far particle split. The examples are schematic vectors, not installed screenshots.

## Preserved visual behavior

- Cloud and precipitation meanings remain those established by OX-03/OX-04. Visible-cloud rain begins at the visible cloud base; eligible carried rain without a visible cloud decision retains the existing field origin and source-time disclosure.
- Keep the current cloud upper-third bound, cue position and artwork, city geometry, forecast foreground, and timeline layout. Use overlapping small-to-medium cloud clusters across Background and Middle-Ground in the shown moderate-rain state; cloud cover and rain span the scene.
- `TIME_WASH` and `READOUT_QUIET_ZONE` remain named compositing passes outside the physical three-group stack.
- The board approves no new theme, element identity system, control, or layout.

## Review request

Review the space → stars → Sun → Moon Background order, cloud occlusion, and overlapping small-to-medium cloud clusters across Background and Middle-Ground. Approve the revised target and select rain-field option A or B, or request a bounded revision. Approval of this board will settle the visual overlap target only; installed device evidence remains required for OX-05 acceptance.

## Source anchors

- `.codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg` — upstream cloud-origin rain target.
- `.codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg` — current First City presentation and evidence-state reference.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` and `FirstCityComposition.kt` — current ordered layer list.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — current physical draw sequence and separate visual treatments.
