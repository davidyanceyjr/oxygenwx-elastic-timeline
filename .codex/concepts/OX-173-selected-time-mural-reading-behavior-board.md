# OX-173 selected-time mural temperature behavior board

**Status:** Proposed shared board package; product-owner approval pending.

## Covered slices and independent exits

- **OX-12 — Exact temperature binding:** a field-scoped canonical temperature projection retains the authoritative selected instant, temperature support state, `°F` display unit when supported, and original source-valid time. Supported and unavailable temperature results share one typed contract. Temperature is not carried or interpolated.
- **OX-12A — Park mural rendering:** the authored Park mural consumes that same binding. Installed supported and unavailable portrait states match the central binding's value, unit, source-valid time, and unavailable meaning. The central readout remains primary and accessible.

The shared board makes both flows and exits independently reviewable. OX-12 remains a presentation/data-contract slice. OX-12A remains the visible Park surface slice and depends on OX-12, OX-42, and OX-37. Neither approval nor the shared package merges the exits.

## Established inputs

- The selected `Instant` is authoritative. A zoom or decorative scene transition does not retarget it. (`docs/SPECIFICATION.md` and the adopted time-selection invariants.)
- `supportedPointFactAt` selects a supported field point using that field's expected slots and representative cells; it returns only facts whose `supportsValue(field)` is true (`CatalogTimelinePresentation.kt`).
- `CanonicalForecastProjection` preserves a `PresentedMeasurement`'s canonical value, display value, and display unit. Its Imperial temperature projection maps canonical Celsius to Fahrenheit and labels the unit `°F` (`CanonicalForecastProjection.kt`).
- The current catalog presenter already has separate point-source timestamps for temperature. `CatalogTimelineView` uses the selected instant as its selection and carries source-time metadata separately (`CatalogTimelinePresentation.kt`).
- Existing expected-missing carry logic is limited to eligible condition points in `SemanticScenePlan.pointTrack`. Missing temperature is not forward-filled. The governing specification also keeps condition carry separate from other missing values.
- OX-37 declares a Park-local `MURAL` data surface with opaque `CANONICAL_SELECTED_VALUE`; OX-42 reserves the wall region. The current Park renderer paints the wall and blank region in the mural depth pass (`LivingUrbanParkComposition.kt`, `AtmosphericScene.kt`).

## Proposed contract and visual choices

1. OX-12 binds `CatalogField.TEMPERATURE_C` from the field's own support at the authoritative selected instant. For a supported sample, project its canonical value using the current Imperial unit and retain the sample's original `sourceValidTime`; for absent or unsupported temperature, expose unavailable.
2. A supported temperature result carries the display unit as data (`°F`). The mural paints the projected value, unit, and source-valid time. It does not convert Celsius, search another field, pick another time, or manufacture a fallback.
3. An unavailable temperature result paints explicit unavailable text on the wall. It never paints zero, a carried temperature, or an interpolated value.
4. Eligible carried condition evidence remains independent and keeps its existing source-time disclosure. It never supplies or changes the mural temperature.
5. Keep the large central reading as the primary accessible forecast path; the mural is a secondary visual echo. Keep the timeline, selected instant, central facts, and existing mural geometry/reservation intact.

The accompanying UI artwork is a proposal for the supported and unavailable wall states in the existing Park composition. Its `48°F`/`12:00 PM` example is layout specimen content only; installed values must come from the approved deterministic catalog fixture and the canonical binding. The artwork is not installed acceptance evidence and does not set the later OX-21 measured portrait typography exit.

## Scope limits

No temperature carry or interpolation; no weather-condition binding on the mural; no forecast lookup or unit conversion in the renderer; no other-world data surface; no final material/theme system; no OX-21 camera/typography polish; no OX-48 carried-state disclosure redesign; and no broad RTL, TalkBack, or system-acceptance matrix.

## Board approval

The DOT, SVG, PNG, companion contract, editable UI artwork SVG, and artwork PNG are one proposed package. Product-owner approval is pending. Review the rendered behavior and UI artwork together; after approval, record the complete same file list and fingerprints separately on OX-12 and OX-12A.
