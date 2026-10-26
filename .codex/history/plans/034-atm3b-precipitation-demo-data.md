# Cycle plan — 034-atm3b-precipitation-demo-data

State: ACTIVE
Roadmap: ATM.3b
Objective: Add deterministic hourly demo precipitation inputs for visual design while keeping chance, amount, and precipitation type distinct.

## Production boundary

This is an isolated fixture and forecast-presentation change in Oxygen Time Lab.
Do not change production oxygenWX, add a provider, or add network/location flows.

Allowed application files:

- `app/src/main/java/com/oxygen/weather/timeline/data/SampleForecast.kt`
- `app/src/main/java/com/oxygen/weather/timeline/presentation/ForecastProjection.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` only to use a generic precipitation-chance label
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` only for any mechanical update to the changed typed field name

Allowed records: this plan, `docs/ROADMAP.md` only for the required active/done status and evidence lifecycle, `.codex/test-artifacts/034-atm3b-precipitation-demo-data/`, and `.codex/history/034-atm3b-precipitation-demo-data.md` at closeout.

Do not modify `AtmosphericScene.kt`, timeline rendering/gesture code, fixture horizon, build configuration, or dependencies. Do not change the accepted atmosphere artwork in this cycle.

## Functional invariants

- Owner resolution during execution: preserve the accepted atmosphere artwork by keeping `ForecastHour.condition` as a renderer-compatibility signal with its current deterministic fixture mapping. Add independent cloud-derived `baseSkyCondition`, explicit `precipitationType`, and tagged `precipitationAmount` fields. User-facing condition wording uses precipitation type when present and otherwise the base sky condition. This compatibility signal is not the canonical precipitation identity.

- The fixture remains deterministic, local, synthetic, and labeled `DEMO DATA`.
- Hourly precipitation chance, explicit precipitation identity, and hourly amount are separate signals. Probability does not create an amount or select rain heaviness.
- Missing hourly amount remains `null`; it is never converted to zero.
- Rain and snow fixture cases have explicit typed precipitation identity, separate from the base sky condition. Precipitation identity is not inferred from chance or temperature.
- Keep the current displayed rain periods as explicit `RAIN` precipitation identity: Oct 23 00:00–23:00, Oct 24 00:00–01:00, and Oct 27 00:00–23:00 (local fixture time). The current fixture produces no `STORM` hours; do not invent storm periods in this cycle. All other hours use `NONE` except the explicit snow hours below.
- Explicit `SNOW` precipitation identity is assigned to Oct 26 00:00–02:00 (local fixture time), independently of chance, temperature, and base sky condition. Those hours retain their base sky condition in the typed projection while the visible condition wording clearly says `Snow`.
- Rain amount means liquid precipitation accumulated during that one-hour forecast interval, in millimeters (mm).
- Snow amount means fresh snowfall depth accumulated during that one-hour forecast interval, in centimeters (cm). It is not liquid-water equivalent and is not inferred from rain amount.
- Represent these with distinct amount variants (`RainLiquidMm` and `SnowDepthCm`) under a nullable hourly amount field. Keep precipitation identity as a separate field so rain/snow remains known when amount is missing. Amounts must be finite and nonnegative; do not compare rain and snow amounts directly or convert one phase's amount into the other phase's meaning. They are synthetic design inputs, not observations or live measurements.
- Assign the only non-null rain amounts as follows: Oct 23 10:00 = 0.6 mm, 11:00 = 1.4 mm, and 12:00 = 2.8 mm; Oct 23 13:00 is an explicit missing-amount rain case. All other hours not listed with a non-null amount have `null` amount, including the remaining rain and `NONE` hours. Assign snow depths Oct 26 00:00 = 0.3 cm, 01:00 = 0.8 cm, and 02:00 = 1.6 cm.
- Preserve the existing hourly chance values without modification. Use these existing samples in the fixture-to-projection table: dry 0% at Oct 24 11:00; low 12% at Oct 28 00:00; medium 50% at Oct 23 00:00; high 78% at Oct 23 10:00; and near-certain 82% at Oct 27 12:00. These are representative design tiers, not new probability thresholds or rules for assigning precipitation identity.
- The existing forecast horizon, selected timestamp, daily/hourly navigation, interpolation policy for existing displayed facts, and gesture ownership remain unchanged.
- The visible chance label and spoken forecast summary refer to precipitation chance for both rain and snow.
- This cycle changes forecast data and its projection only. It does not implement stepped moisture artwork or falling-precipitation heaviness; those belong to ATM.4.

## Hour projection semantics

- At an exact hour, use that `ForecastHour`'s typed precipitation identity and optional amount.
- At a fractional hour, choose precipitation identity and amount from the nearest actual fixture hour, using the existing `weight < 0.5` categorical boundary. Do not interpolate an hourly accumulation into a fabricated subhour total. Preserve `null` if the selected source hour has no amount.
- Keep base sky condition and precipitation identity as separate projected fields. For explicit snow, the existing visible condition wording must clearly identify `Snow` while the base sky-condition field remains available unchanged; do not make the screen infer snow from temperature, chance, or the base sky condition. Existing rain and non-precipitation condition wording remains consistent with the explicit fixture assignments.
- Existing numeric interpolation of chance and other continuous display inputs remains unchanged. This is presentation smoothing, not a minute-level forecast.
- In daily overview, use the existing representative `ForecastHour`; do not sum hourly totals into a daily total in this slice.

## Unit policy

- Keep fixture and projected amount inputs in canonical metric units: rain accumulation in mm and snowfall depth in cm. The renderer consumes these source-like values; do not convert them to drive the artwork.
- Use US customary units for user-facing measurements. If rain amount is displayed in a later slice, convert mm to inches in presentation. If snowfall depth is displayed, use inches, with feet and inches for larger supported accumulations. Wind remains mph (miles per hour), as in the current UI; temperature remains °F.
- ATM.3b adds no visible numeric amount field, so it does not add amount formatting or conversion code. Any later amount text must convert only in presentation and keep its units explicit.

## Steps

1. Inspect the current deterministic hourly fixture and projection. Record exact, normally navigable samples (local fixture time; index is hour offset from Oct 23 00:00): 0% at Oct 24 11:00 / index 35, 12% at Oct 28 00:00 / index 120, 50% at Oct 23 00:00 / index 0, 78% at Oct 23 10:00 / index 10, and 82% at Oct 27 12:00 / index 108. Record differing rain accumulations at Oct 23 10:00–12:00 / indices 10–12, missing rain amount at Oct 23 13:00 / index 13, and snow depths at Oct 26 00:00–02:00 / indices 72–74.
2. Add independent base cloud-derived sky category, nullable tagged hourly amount (`RainLiquidMm` for liquid rain accumulation; `SnowDepthCm` for fresh snowfall depth), and explicit typed precipitation identity for every fixture hour. Keep `ForecastHour.condition` as the legacy renderer-compatibility signal with its exact existing mapping so accepted scene artwork is unchanged; do not use it as projected precipitation identity. Assign `RAIN` at indices 0–23, 24–25, and 96–119; assign `SNOW` at indices 72–74; assign `NONE` to all remaining indices. Do not create `STORM` precipitation identity or legacy `STORM` condition hours. Assign only the non-null amounts and values listed in the invariants; all other amount values are null. Explicit precipitation assignments are independent of chance and temperature; preserve unrelated temperature/cloud/wind samples and the fixture horizon. Preserve the legacy visible rain periods.
3. Carry generic chance, legacy scene-compatibility condition, base sky condition, precipitation identity, and tagged amount independently through the selected-hour projection. For explicit rain/snow, provide visible condition wording `Showers`/`Snow`; otherwise use base sky condition wording. Retain the base sky condition separately. At fractional hours, use the nearest source hour for categorical identity and amount; preserve amount missingness. Daily overview keeps the existing representative-hour behavior.
4. Change the visible `RAIN CHANCE` label to `PRECIP CHANCE`. Confirm the existing accessible browser wording remains `precipitation chance`; do not add a permanent control, panel, or status row.
5. Build and inspect the normal installed hourly states on the Pixel 3 XL for representative rain and snow fixture hours. Capture the selected time, condition, DEMO DATA label, and chance text; do not assess or claim precipitation-renderer acceptance. Use portrait/default font; RTL and reduced-motion review are not applicable because this slice changes no directionality or animation, and the label remains a short static secondary fact.
6. Record a fixture-to-projection table with exact source hour/index, chance, base sky condition, visible condition wording, precipitation type, tagged amount, and missingness in `.codex/test-artifacts/034-atm3b-precipitation-demo-data/README.md`. Include the five chance-tier samples and every non-null/missing amount sample specified above. Installed screenshots verify the selected time, visible condition/chance label, and `DEMO DATA`; they do not expose the non-visible hourly amount. Inspect the final diff and run `git diff --check`.

## Acceptance and evidence

Acceptance requires all of the following:

- Normal hourly navigation reaches the exact chance-tier, rain-total, missing-total, and snow samples specified in the invariants without changing fixture bounds. The legacy rain periods remain Oct 23 00:00–23:00, Oct 24 00:00–01:00, and Oct 27 00:00–23:00; no storm period is added.
- Projection preserves precipitation chance and type separately; rain totals are interval millimeters, snowfall totals are interval centimeters, and missing amounts remain missing. Each amount carries the matching phase tag. No rain or snow identity is inferred from chance or temperature.
- At fractional selection, type and amount come from the nearest actual hourly sample; at daily overview, values come from the representative hour. No synthetic subhour or daily accumulation is created.
- Visible and accessible forecast wording identifies the percentage as precipitation chance for rain and snow states; explicit snow hours visibly say `Snow`, with base sky condition still independently projected.
- A successful debug build and `python scripts/dev.py contract` confirm the application still builds and remains within prototype boundaries.
- The fixture-to-projection table and source review establish amount values, phase units, selected-hour projection, and missingness; screenshots do not claim to show the projected amount because ATM.3b adds no amount display.
- Installed Pixel 3 XL portrait/default-font captures show representative rain and snow fixture hours with readable forecast facts, generic chance wording, selected time, and `DEMO DATA`. RTL and reduced motion are not applicable to this static data/presentation slice; no other font/device matrix is claimed. These captures verify condition/chance presentation only; they do not establish ATM.4 visual acceptance.
- `python scripts/dev.py workflow` and `git diff --check` pass; the artifact README and cycle history record exact results and limitations.

Evidence target: `.codex/test-artifacts/034-atm3b-precipitation-demo-data/`.
Closeout target: `.codex/history/034-atm3b-precipitation-demo-data.md`.

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build` using the configured Android SDK and supported JDK, recording exact command and result
- `python scripts/dev.py run --serial <confirmed-pixel-3-xl-serial>` and `python scripts/dev.py screenshot --serial <confirmed-pixel-3-xl-serial> --output <artifact-path>` for the representative installed states
- Manual source review of the fixture cases, tagged amount units, finite/nonnegative values, exact selected sample indices, nearest-hour projection, and projected values
- `git diff --check`

Do not add or run tests in this cycle under the repository's current verification instruction. Do not run lint unless the bounded implementation exposes a relevant lint issue and its scope is recorded.

## Risks / assumptions

- The existing hourly fixture generates rain/storm identity from chance and cloud thresholds. This slice must replace that inference with deterministic explicit fixture assignments while preserving the existing numeric weather samples.
- Rain accumulation and snowfall depth have different units and meanings. Keep their tagged amount variants explicit throughout data and projection; keep type independent so amount may be absent; do not normalize them to a shared number.
- Hourly totals are fixture inputs for visual design. They must remain visibly synthetic under the existing DEMO DATA contract and must not be described as live measurements.
- The explicit snow condition wording appears before snow artwork is implemented. The atmosphere renderer remains unchanged in this slice.
- Device screenshots confirm fixture reachability and text readability only; visual precipitation quality remains the ATM.4 acceptance target.

## Out of scope

- Rain, snow, moisture, fog, or storm rendering and all particle/effect behavior.
- Probability-to-moisture threshold design or amount-to-heaviness mapping.
- Live weather provider integration, source normalization, caching, location, or provenance changes.
- Timeline, gesture, selected-time, fixture-horizon, or weather-scene layout redesign.
- RTL, TalkBack traversal, large-font matrix, reduced-motion, performance, and other app-wide acceptance; these remain separately planned.
- Adding or running unit, UI, lint, or full-suite tests.
