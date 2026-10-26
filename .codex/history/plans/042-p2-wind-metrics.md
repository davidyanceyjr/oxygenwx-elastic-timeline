# Cycle plan — 042-p2-wind-metrics

State: DONE
Roadmap: P.2.14
Objective: Replace the forecast summary precipitation and wind heading labels with the owner-approved visual indicators while keeping values visible, correctly grouped, and truthful to the selected sample.

## Production boundary

Work remains in the isolated Oxygen Time Lab application. Expected change areas:

- `app/src/main/java/com/oxygen/weather/timeline/data/SampleForecast.kt` — add an optional, validated wind-from bearing to the deterministic synthetic sample; the approved reference state uses 45° (northeast).
- `app/src/main/java/com/oxygen/weather/timeline/presentation/ForecastSource.kt` and `ForecastProjection.kt` — carry the optional bearing as a typed fact from the selected sample to `ForecastView`; do not add it to decorative `SkyFrame` and do not interpolate it.
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` — replace the visible metric headings with the rain-signal and compass indicators; keep the values paired with their indicators in the approved placement and preserve metric-specific semantic names and values.
- Focused presentation tests for bearing validation/projection, compass direction mapping, and unknown direction behavior.
- `.codex/test-artifacts/042-p2-wind-metrics/` for the installed target-state screenshot and exact slice-specific verification notes.
- `docs/ROADMAP.md`, `.codex/current.md`, this plan, and the eventual cycle history/archived plan.

Concept input: `docs/mockups/approved-precipitation-wind-metrics.png`. Installed baseline: `docs/mockups/app_screen_original.png`. Compare the metric area to identify which headings are removed, which indicators are added, and the intended spacing and grouping. These images are design input and baseline, not implementation acceptance evidence.

## Functional invariants

- Precipitation chance and wind speed remain visible text derived from the exact existing forecast sample. Preserve the current percent rounding and canonical-kph-to-display-mph conversion.
- The rain-signal droplets identify the precipitation-chance metric only. They do not represent precipitation amount/intensity or active rain; do not add the rejected ring or animated rain.
- Wind direction is an optional synthetic fact in degrees clockwise from true north, describing where wind comes from. The compass needle uses the selected exact sample; 45° points to NE. Use a neutral compass without a needle when the source direction is unknown. The direction convention follows the [National Weather Service glossary](https://forecast.weather.gov/glossary.php?word=WIND+DIRECTION).
- The compass face and its placement are static and north-up. The needle is a separate, non-animated overlay that changes immediately to reflect the selected sample's reported direction. Forecast changes may update text values and the needle; they do not move or redesign either indicator. Never mirror the compass face or reverse its bearing for layout direction.
- Daily summary facts continue to come from that date's noon sample; precision-mode facts continue to come from the selected hourly sample. Do not interpolate reported direction or other facts, and do not change selected-time authority.
- Remove the visible `PRECIP CHANCE` and `WIND` strings while preserving the metric's semantic names and values in implementation. Unknown direction must not be guessed. Application-wide accessibility and TalkBack validation are outside this slice and belong to their dedicated suite.
- Preserve the existing scene, temperature, condition, high/low, DEMO DATA provenance, timeline, navigation, and gesture ownership. No cards, new visible labels, touch controls, or production oxygenWX changes.

## Ordered steps

1. Review the actual Pixel baseline and approved mockup at full size. Record the metric-row delta only: headings removed, indicators added, values grouped beneath them, and target spacing. Inspect the existing selected-sample projection before editing.
2. Add the optional direction fact with range validation (`0 <= degrees < 360`) to the deterministic normal fixture and shared typed source/presentation path. Give the normal synthetic sample an explicit NE bearing for the approved state. Keep sources without a supplied bearing nullable.
3. Implement the two indicators in the current bottom metric row. Keep the rain signal and compass face static in size and placement, remove the visible headings, and keep each value grouped beneath its indicator in the approved reference composition. Draw the compass needle as a non-animated overlay only when direction is supplied; keep the compass north-up.
4. Add focused checks for bearing bounds, selected-sample projection in daily/noon and precision/hourly modes, NE mapping, and unknown-direction handling. Do not run application-wide accessibility, font-scale, RTL, reduced-motion, or TalkBack suites in this slice.
5. Build, install, and review the normal fixture on the Pixel 3 XL at the reference state against the approved mockup. Capture the target state and verify only this slice's visual outcome: removed headings, added indicators, fixed grouping/placement, visible values, and the sample-driven needle. Do not perform general timeline, gesture, accessibility, font, RTL, or motion testing.
6. Record exact commands, installed capture, focused results, and limitations under the cycle evidence path. Inspect `git diff --check` and the final diff. Do not close the cycle unless every slice-specific roadmap exit condition has evidence.

## Acceptance and evidence

P.2.14 is complete only when:

- Installed Pixel 3 XL reference-state capture shows the approved metric composition: rain signal above visible precipitation chance; compass above visible wind speed; the indicators and values occupy the approved metric area; neither visible heading remains. The rest of the screen and bottom timeline remain visually intact.
- For the normal synthetic sample, the compass points NE from the selected sample's 45° wind-from bearing. At a source without direction, the compass remains neutral and no directional claim is shown. Direction and mph/probability semantics are derived from the same sample as the visible values.
- Focused tests verify bearing validation and projection for daily/noon and precision/hourly facts, compass sector mapping, and unknown handling. Existing percent and wind-speed formatting remain unchanged. Source review confirms the compass face is north-up and only the needle overlay follows the reported bearing.
- No application-wide test suite is run or claimed here. Compact/large-font, RTL, reduced-motion, accessibility, and TalkBack validation remain for their dedicated suite cycle(s). General responsiveness and gesture-ownership testing is also outside this slice; the implementation does not change timeline or gesture code.
- `python scripts/dev.py workflow`, `python scripts/dev.py contract`, the focused direction test, `python scripts/dev.py build`, and the installed reference-state capture results are recorded. The normal app is installed on the attached Pixel 3 XL and the target screenshot is saved under `.codex/test-artifacts/042-p2-wind-metrics/`. `git diff --check` passes. No broad lint or application-wide test suite is run. No production oxygenWX file or release integration is changed.

## Risks / assumptions

- Wind direction is absent from the current fixture and shared forecast source. The approved NE needle therefore requires a new explicitly synthetic sample fact; it must stay under the existing DEMO DATA provenance and must not be described as an observation.
- Debug forecast sources that do not supply direction must remain valid and render a neutral compass. Do not retrofit guessed directions into those profiles as part of this slice.
- Three droplets next to `0%` could be misread as active rain. Keep the motif static, small, and clearly grouped with the numeric probability. Accessibility suite validation remains out of scope here.
- The approved mockup is a generated visual concept and slightly reinterprets some atmosphere shapes. Preserve the installed baseline artwork; acceptance focuses on the metric treatment and must not copy unrelated generated background changes.
- Do not animate the needle between selected samples. A changing bearing follows the selected exact sample and must not add drag latency or imply unreported intermediate direction.

## Out of scope

No precipitation ring, cards, additional visible labels, rain/snow/storm renderer changes, weather effects, new debug controls, forecast provider or location/network integration, production oxygenWX changes, timeline/navigation/gesture redesign, changes to temperature/date/high-low presentation, or application-wide test suites. Font-scale, RTL, reduced-motion, accessibility, and TalkBack suites remain separate planned work. ATM.8 scene-matrix work remains a separate planned slice.

## Verification commands planned

```text
python scripts/dev.py workflow
python scripts/dev.py contract
./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.ForecastWindDirectionTest
python scripts/dev.py build
python scripts/dev.py run --serial 8AKY0M32X
python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/042-p2-wind-metrics/installed-normal.png
```
