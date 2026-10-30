# WX.25 concept amendment — complete simulated precipitation chance and wind speed

**Status:** Owner approved 2026-10-29. The owner clarified that null means no value, while numeric zero is a real value, and requested precipitation chance and wind to be present across all simulated dates. This amendment narrows the required-value rule to the two fields exposed as precipitation chance and sustained wind speed in the current timeline.

## Data rule

- Every SIMULATED hourly fact across the existing 44-date real-hour grid has non-null `precipitationProbabilityPercent` and `windSpeedKph`.
- Preserve every existing non-null anchor value at its current hour window.
- Where the existing synthetic anchor has no precipitation probability, emit explicit `0.0%`.
- Where the existing synthetic anchor has no sustained wind speed, emit explicit `0.0 kph`.
- Zero is a value. Do not translate it to null or “unknown” in the data layer.
- Gust and wind direction are separate optional fields and remain unchanged. Other precipitation measurements and unrelated fields retain their current null/explicit-zero semantics.
- Apply only to the SIMULATED source and SIMULATED-derived debug REPLAY recording. Do not change LIVE/provider catalog completeness.

## Identity and compatibility

Version the synthetic product, debug SIMULATED dataset, and fixed Chicago replay dataset so persisted catalogs with the prior sparse values cannot be treated as complete under this rule. Keep the deterministic event sequence, 9 AM/9 PM windows, weather event families, support boundaries, provenance, and hourly Instant grid.

## Owner approval and constraints

Owner decision, 2026-10-29: “for values, we can't have null; null=no-value. 0 = a value. I will adjust the UI for zero values in a future roadmap.” This explicitly supersedes the WX.10R concept's missingness preservation only for SIMULATED precipitation probability and sustained wind speed. It does not authorize UI changes, broader field defaults, or provider semantics.
