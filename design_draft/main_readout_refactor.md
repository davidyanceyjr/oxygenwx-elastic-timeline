# Main Weather Readout — Refactor Specification (v2)

## Objective

Refactor the main weather readout to use the Florence night dashboard style as the new primary layout, simplifying the center display and consolidating the most important supporting values into a bottom 3-indicator row.

## Main layout

### 1. Hero readout
Display the following in the center of the screen:

- Location name and location icon
- Thin gold semicircular arc
- Weather condition icon inside the arc
- Large primary temperature
- Feels-like temperature directly beneath the main temperature
- Condition description beneath the feels-like value

### 2. Feels-like formatting
Display apparent temperature as:

`≈ 53°`

- Glyph: `≈` (U+2248)
- Glyph visually larger than the temperature text
- Glyph color: accent cyan
- Temperature color: white
- No “Feels Like” label required

### 3. Bottom indicator row
Replace side indicators with a single bottom overlay row containing exactly three supporting metrics:

- **Precipitation chance**
- **Wind speed**
- **Humidity**

These should be visually balanced and evenly spaced across the lower portion of the main readout area.

Example:
- `0%` Precip
- `6 mph` Wind
- `58%` Humidity

No opaque cards, boxes, or filled containers.

## Remove from main readout

Remove the following from the main-screen readout:

- Side indicator columns
- Sunrise / sunset
- Moonrise / moonset
- Daily high / low
- Visibility
- UV
- Clouds
- Air quality
- Dew point
- Gusts

These values remain available for detail views and mini-graphs.

## Detail-view allocation

The following values are reserved for detail views / scrub-linked mini-graphs:

- Gusts
- Dew point
- Pressure
- Visibility
- UV index
- Cloud cover
- Air quality
- Solar / sun & moon values
- Daily high / low context

## Behavior

- All displayed values are driven by the currently focused timeline timestamp
- Primary temperature, feels-like, condition, precip, wind, and humidity update during scrubbing
- Decorative elements may animate subtly, but value updates should remain immediate

## Compose refactor targets

Recommended composables:

- `WeatherHeroReadout`
- `TemperatureArc`
- `PrimaryTemperature`
- `FeelsLikeIndicator`
- `ConditionSummary`
- `MainSupportIndicatorRow`
- `SupportIndicatorItem`

## Acceptance criteria

- [ ] Gold semicircular dashboard becomes the main-screen hero readout
- [ ] Side indicators are removed
- [ ] Bottom row shows only Precip, Wind, and Humidity
- [ ] Feels-like value appears beneath the primary temperature using `≈`
- [ ] Condition text remains in the hero area
- [ ] No opaque background cards are introduced
- [ ] Main readout remains transparent over the interactive skyline
- [ ] Values update correctly while scrubbing the timeline
- [ ] Secondary weather metrics are reserved for detail-view mini-graphs