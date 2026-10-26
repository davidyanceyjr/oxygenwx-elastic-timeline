# DFM.1 — Debug forecast matrix and transition contract

**Status:** Owner approved 2026-10-25 as the bounded DFM.2 fixture implementation contract.

**Purpose:** Provide a small, deterministic set of synthetic debug scenarios for later weather-scene prototyping and forecast-interface wiring. The rows are not live forecasts, historical observations, climatological frequencies, a provider mapping, or a production schema. Each value is illustrative or explicitly absent.

## Scope and vocabulary

The matrix covers clear, partly cloudy, cloudy, rain, snow, and storm; representative day/night states; dry and wet generic precipitation probabilities; present and absent precipitation amounts; three deterministic transitions; independent illustrative thunderstorm probabilities of 27% and 90%; and an independent debug warning placeholder.

`time` below is a synthetic forecast valid time in the fixture's local display calendar. It does not identify a location or timezone and does not claim solar accuracy. Scenario sequences use one-hour steps to exercise transitions. The `condition` vocabulary is debug scoped and includes `SNOW`; it does not extend `data.SkyCondition`. The separate `precipitationType` continues to identify rain/snow precipitation, not sky cover or snow cover.

## Scenario inventory

| ID | Valid time / scene period | Debug condition | Generic precipitation probability | Precipitation type | Precipitation amount | Thunderstorm probability | Debug warning | Case exercised |
|---|---|---|---:|---|---|---:|---|---|
| `CLEAR-DAY` | 2026-10-23 12:00 / day | `CLEAR` | 0% | `NONE` | absent | absent | `NONE` | Dry clear daytime baseline; absence of amount is not a measured zero. |
| `CLEAR-NIGHT` | 2026-10-23 23:00 / night | `CLEAR` | 0% | `NONE` | absent | absent | `NONE` | Clear night appearance, without astronomical accuracy claims. |
| `PARTLY-DAY` | 2026-10-24 12:00 / day | `PARTLY_CLOUDY` | absent | `NONE` | absent | absent | `NONE` | Partial cloud scene with probability intentionally unknown. |
| `CLOUDY-NIGHT` | 2026-10-24 23:00 / night | `CLOUDY` | 20% | `NONE` | absent | absent | `NONE` | Cloudy night and nonzero generic chance do not assert precipitation identity or amount. |
| `RAIN-AMOUNT` | 2026-10-25 12:00 / day | `RAIN` | 60% | `RAIN` | `RainLiquidMm(0.8)` | absent | `NONE` | Wet rain case with an explicit illustrative liquid amount in mm. |
| `RAIN-UNKNOWN-AMOUNT` | 2026-10-25 18:00 / evening | `RAIN` | 70% | `RAIN` | absent | absent | `NONE` | Wet rain case whose amount remains unknown. |
| `SNOW-DAY` | 2026-10-26 12:00 / day | `SNOW` (debug-only condition) | 80% | `SNOW` | absent | absent | `NONE` | Snow precipitation with unknown amount; does not imply snow cover. |
| `SNOW-NIGHT` | 2026-10-26 23:00 / night | `SNOW` (debug-only condition) | 80% | `SNOW` | absent | absent | `NONE` | Snow scene at night, with amount still unknown. |
| `STORM-LOW-THUNDER` | 2026-10-27 12:00 / day | `STORM` | 20% | `NONE` | absent | 27% | `NONE` | Low illustrative thunderstorm probability remains distinct from generic probability and precipitation identity. |
| `STORM-HIGH-THUNDER` | 2026-10-27 13:00 / day | `STORM` | 70% | `RAIN` | absent | 90% | `NONE` | High illustrative thunderstorm probability with independently stated generic probability; no measured rain amount. |
| `STORM-WARNING-PREVIEW` | 2026-10-27 14:00 / day | `STORM` | absent | `NONE` | absent | absent | `PRESENT` | Warning-related preview input independent of all probability and precipitation fields. It is synthetic, not an alert. |
| `CLEAR-ENDPOINT` | 2026-10-27 15:00 / day | `CLEAR` | 0% | `NONE` | absent | absent | `NONE` | Clear endpoint; no probability or warning carries forward implicitly. |

Scenario values are chosen for separation and rendering coverage, not statistical plausibility. In particular, the 27% and 90% values are fixed examples only, not estimated from observations or calibrated forecast probabilities. `PRESENT` does not encode severity, source, issue time, affected area, or official alert status.

## Typed field contract proposed for DFM.2

All fields below belong to a debug-only immutable scenario type/catalog. DFM.1 adds no Kotlin types or production model fields.

| Field | Proposed type / domain | Meaning and missingness |
|---|---|---|
| `scenarioId` | Stable string/enum ID | Identifies one catalog row. |
| `validTime` | `LocalDateTime`-like synthetic fixture time | Forecast scenario time only; not an observation time, provider timestamp, location-aware instant, or source-validity claim. |
| `condition` | Debug enum: `CLEAR`, `PARTLY_CLOUDY`, `CLOUDY`, `RAIN`, `SNOW`, `STORM` | Illustrative scene/condition case. `SNOW` is debug scoped and must not be added to canonical `SkyCondition` by this matrix. |
| `precipitationProbabilityPercent` | Nullable numeric percentage in `[0,100]` | Generic chance of precipitation. `0` means an illustrative dry probability; `null` means unknown/not supplied. Neither means precipitation amount. |
| `precipitationType` | Existing conceptual values `NONE`, `RAIN`, `SNOW`, nullable only if the implementation later has a justified unknown identity | Precipitation identity, independent from condition and probability. `SNOW` means snow precipitation, not snow cover. For these rows it is explicit in every scenario. |
| `precipitationAmount` | Nullable existing tagged amount: `RainLiquidMm` or `SnowDepthCm` | Preserve units and variant. `RainLiquidMm` is an amount of liquid rain. `SnowDepthCm` is snow depth, not snowfall accumulation; no row uses it as a snowfall amount. `null` means unknown/not supplied, never zero. |
| `thunderstormProbabilityPercent` | Nullable numeric percentage in `[0,100]` | A distinct illustrative thunderstorm signal. Only the two labeled rows supply 27% and 90%; never infer it from generic probability, condition, amount, or observations. |
| `debugWarningState` | Debug enum `NONE` / `PRESENT` | Synthetic UI/scene preview switch only. No official-alert semantics, source, severity, timing, or production use. |
| `provenance` | Fixed debug provenance marker, e.g. `SYNTHETIC_DEBUG` | Must be visible in debug catalog/interface surfaces. Historical reference evidence below is not row provenance. |

Field handling rules:

- Preserve each `null` as unknown/not supplied. Do not fill from a neighboring hour, scene condition, another probability, amount, warning state, or historical observation.
- Do not interpret generic precipitation probability as intensity, rate, accumulation, or rain onset time.
- Do not interpret `precipitationType` as a sky condition or infer it from `condition`.
- Do not infer snow cover from snow precipitation. A `SnowDepthCm` value, if later added to a separately approved row, means snow on the ground and remains a separate quantity.
- These are placeholder fields for a debug fixture catalog. No live provider, API, mapping, runtime feed, or production forecast contract is selected.

## Transition map

Each sequence uses consecutive synthetic hourly steps when materialized by DFM.2. The table's per-row example valid times are illustrative and do not define those sequence-local step times. The map specifies deterministic input changes for later visual/interface review, not an inferred real-world forecast evolution. Scene interpolation may be decorative; catalog forecast facts remain the exact selected row.

| Sequence | Ordered scenario IDs | Expected input transition |
|---|---|---|
| Cloud build, rain, clearing | `CLEAR-DAY` → `PARTLY-DAY` → `CLOUDY-NIGHT` → `RAIN-AMOUNT` → `RAIN-UNKNOWN-AMOUNT` → `CLEAR-ENDPOINT` | Condition progresses clear → partly cloudy → cloudy → rain; explicit amount becomes missing while rain identity and probability remain independently stated; the final clear row resets probability/type/amount rather than inheriting them. |
| Snow entry and exit | `CLOUDY-NIGHT` → `SNOW-DAY` → `SNOW-NIGHT` → `CLEAR-ENDPOINT` | Debug snow condition and snow precipitation type appear together, persist across day/night variants, then both clear. Unknown amount stays unknown. |
| Graded storm and warning preview | `STORM-LOW-THUNDER` → `STORM-HIGH-THUNDER` → `STORM-WARNING-PREVIEW` → `CLEAR-ENDPOINT` | Thunderstorm probability changes 27% → 90% as an illustrative input; generic probability and type are independent. The warning preview toggles independently and is not an official alert. The final row clears all storm-specific fields. |

The sequences are keyed by their row IDs. DFM.2 should assign explicit contiguous timestamps within each materialized sequence, without changing the field values or representing an observation cadence.

## Historical observation plausibility check (not fixture content)

NOAA NCEI describes GHCNh as hourly/synoptic observations from fixed land stations, with present-weather and precipitation elements where available. I inspected the station-year PSV file for station `USW00094846`, **CHICAGO OHARE INTL AP**, year 2023, retrieved 2026-10-25. These records are observations, not forecasts. The examples show why a visual prototype needs distinct condition/type/amount fields and explicit missingness; they do not supply any forecast probability or debug warning value.

| GHCNh station/date row (`DATE` as stored) | Raw observed fields present in the PSV row | Narrow plausibility note |
|---|---|---|
| `USW00094846`, `2023-01-01T08:51:00` | `temperature=3.3`; `precipitation=2.0`; `pres_wx_MW1=RA:65`; quality-code columns for those three fields are each `5`; `pres_wx_AU1=RA:02`; `pres_wx_AW1=RA:62` | Rain is represented by observed present-weather codes alongside a precipitation element. This supports testing separate condition/identity/amount concepts; it does not define the synthetic `0.8 mm` row or its probability. |
| `USW00094846`, `2023-01-01T10:51:00` | `temperature=3.3`; `precipitation=0.0`; `pres_wx_MW1=RA:61`; `pres_wx_AU1=-RA:02`; `pres_wx_AW1=RA:61`; temperature, precipitation, and `pres_wx_MW1` quality-code columns are `5` | A later report still encodes rain while the precipitation field is `0.0`; fields therefore must not be collapsed into one boolean. It is a distinct observed report, not a forecast transition rule. |
| `USW00094846`, `2023-12-31T14:10:00` | `temperature=-0.6`; `precipitation=0.7`; `pres_wx_MW1=SN:71`; `pres_wx_AU1=-FZDZ:01`; `pres_wx_AW1=FZDZ:54`; `snow_depth` is empty; quality-code columns for temperature, precipitation, and `pres_wx_MW1` are `5` (precipitation QC is `1` in this row) | Snow present-weather and liquid-precipitation fields can coexist in an observation; absent `snow_depth` must remain absent and cannot be fabricated from the snow code. Preserve the raw quality flag rather than treating it as a production acceptance judgment. |
| `USW00094846`, `2023-12-31T18:51:00` | `temperature=1.1`; `precipitation=0.0`; `pres_wx_MW1=SN:71`; `pres_wx_AU1=-SN:03`; `pres_wx_AW1=SN:71`; `snow_depth` is empty; temperature, precipitation, and `pres_wx_MW1` quality-code columns are `5` | This later report still carries snow present-weather with a zero precipitation value and no snow-depth value. The fields must remain distinct and missingness explicit. |

These are a small plausibility sample only. GHCNh source flags and report types vary by element; numeric quality codes above are quoted as stored and are not decoded here. No observed value, source code, time, or quality flag is copied into a synthetic scenario. Historical observations do not establish any generic or thunderstorm forecast probability, and this sample is not intended to validate the overall matrix distributions.

### Traceable source references

- Dataset overview and available elements: [NOAA NCEI, Global Historical Climatology Network hourly](https://www.ncei.noaa.gov/products/global-historical-climatology-network-hourly).
- Data specification, field definitions, codes, and station-file access pattern: [NOAA NCEI, GHCNh documentation PDF](https://www.ncei.noaa.gov/oa/global-historical-climatology-network/hourly/doc/ghcnh_DOCUMENTATION.pdf).
- Exact inspected station-year source: [GHCNh_USW00094846_2023.psv](https://www.ncei.noaa.gov/oa/global-historical-climatology-network/hourly/access/by-year/2023/psv/GHCNh_USW00094846_2023.psv).
- Dataset citation: Menne et al., *Global Historical Climatology Network-Hourly (GHCNh)*, NOAA NCEI, DOI [10.25921/jp3d-3v19](https://doi.org/10.25921/jp3d-3v19).

## Owner approval and DFM.2 gate

On 2026-10-25, the owner approved this matrix as drafted, including the illustrative per-row timestamps, the rule that each materialized transition advances one synthetic hour, the debug-only `SNOW` condition, the `NONE`/`PRESENT` warning placeholder, and the typed field contract and scenario inventory above. The owner approved it as DFM.2's implementation contract. This does not authorize changes to the production model, official-alert behavior, or provider selection.

DFM.2 must use this approved contract and remain isolated from normal `SampleForecast`; any production-model expansion, provider-backed alert, or provider selection requires a separate bounded decision.
