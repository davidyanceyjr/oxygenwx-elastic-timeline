# WX.10 approved fixed sequence fixture

Cycle: `051-wx10-simulated-catalog` (PLANNED). Owner approved the one linear sequence, sample/support rules, and narrowly bounded `WeatherPhenomenon.HAZE` addition on 2026-10-27. This is the frozen synthetic diagnostic fixture, not observed weather or an official alert.

## Time and field contract

`D = request.asOf.atZone(request.location.timeZone).toLocalDate()`. For every date D−30 through D+13, emit samples at **09:00 and 21:00 local time**, resolving each through the requested IANA zone to an absolute instant. They have the same fields and values shown below unless the row gives `09 → 21` overrides. Even the night rows retain two samples; the 21:00 sample is the named night example. These local times are valid and unambiguous across ordinary DST transitions. Sort all facts by absolute valid time. Do not assume 24 hours per date or use a fixed UTC offset.

The table is exhaustive. `C` is the exact condition set, `P` the exact phenomenon set, `T` temperature °C, `Cl` cloud cover %, `Pr` precipitation probability %, `Th` thunderstorm probability %, `R` point precipitation rate mm/h, `Ra` rain amount mm, `Sn` new snowfall cm, `Sd` snow depth cm, `W` wind speed km/h, `G` gust km/h, and `V` visibility km. Every omitted field is **null/absent**, including total precipitation, humidity, wind direction, pressure, and any field not listed for that sample. An empty `P` means no phenomenon is asserted; do not declare PHENOMENA support for it. `C` is always present. `Ra` and `Sn`, including explicit zeros, have exactly one-hour accumulation intervals `[validTime − 1 hour, validTime)` and end at the sample instant. `R` is a point rate with `meanInterval = null`. No daily total is implied by two samples.

| Day | C | P | Exact fields at both samples unless overridden |
|---|---|---|---|
| D−30 | CLEAR | — | T 18, Cl 5, Ra 0 |
| D−29 | CLEAR | — | T 15, Cl 5, Ra 0; 21:00 T 11 |
| D−28 | PARTLY_CLOUDY | — | T 18, Cl 35 |
| D−27 | PARTLY_CLOUDY | — | T 17, Cl 60 |
| D−26 | CLOUDY | — | T 16, Cl 90 |
| D−25 | RAIN | — | T 15, Cl 95, Pr 55, R 1, Ra 1 |
| D−24 | RAIN | — | T 14, Cl 100, Pr 75, R 3, Ra 3 |
| D−23 | RAIN | — | T 14, Cl 95, Pr 70; Ra and R absent |
| D−22 | RAIN | — | T 13, Cl 100, Pr 85, R 8, Ra 8, W 20, G 35 |
| D−21 | RAIN | — | T 12, Cl 100, Pr 95, R 18, Ra 18, W 30, G 55; synthetic flash-flood **conditions** only |
| D−20 | RAIN | — | T 13, Cl 95, Pr 80, R 7, Ra 7, W 18, G 30 |
| D−19 | PARTLY_CLOUDY | — | T 16, Cl 45, Ra 0 |
| D−18 | PARTLY_CLOUDY | — | T 17, Cl 45, W 45, G 75 |
| D−17 | CLEAR | — | T 16, Cl 10, W 22, G 40 |
| D−16 | PARTLY_CLOUDY | HAZE | T 18, Cl 40, V 6 |
| D−15 | PARTLY_CLOUDY | HAZE | T 19, Cl 40, V 4 |
| D−14 | PARTLY_CLOUDY | — | T 18, Cl 40, V 10 |
| D−13 | CLOUDY | FOG | T 11, Cl 90, V 2 |
| D−12 | CLOUDY | FOG | T 10, Cl 100, V 0.5 |
| D−11 | CLOUDY → CLEAR | FOG → — | 09:00 T 9, Cl 95, V 1; 21:00 T 8, Cl 5, V 12 |
| D−10 | RAIN | — | T 3, Cl 100, Pr 70, R 2, Ra 2 |
| D−9 | RAIN | FREEZING_RAIN | T 0, Cl 100, Pr 80, R 2, Ra 2 |
| D−8 | RAIN | ICE_PELLETS | T 0, Cl 100, Pr 75, R 2; liquid amount absent |
| D−7 | SNOW | — | T −3, Cl 100, Sn 2, Sd 3 |
| D−6 | SNOW | — | T −5, Cl 100, Sd 6; Sn absent; 21:00 T −7 |
| D−5 | SNOW | — | T −2, Cl 95, Sn 0.5, Sd 7 |
| D−4 | CLOUDY | — | T −1, Cl 90, Sn 0, Sd 7 |
| D−3 | CLEAR | — | T −4, Cl 5, Sd 6 |
| D−2 | PARTLY_CLOUDY | — | T 2, Cl 35, Sd 4 |
| D−1 | CLOUDY | — | T 5, Cl 90, Pr 20; Ra absent |
| D | PARTLY_CLOUDY | — | T 9, Cl 40, Pr 25; Ra absent |
| D+1 | PARTLY_CLOUDY | — | T 10, Cl 35; 21:00 T 7 |
| D+2 | PARTLY_CLOUDY | — | T 13, Cl 55, Pr 35, Th 27 |
| D+3 | STORM, RAIN | — | T 16, Cl 100, Pr 95, Th 90, R 5; Ra absent |
| D+4 | STORM, RAIN | — | T 15, Cl 100, Pr 80, Th 55, R 4, Ra 4 |
| D+5 | RAIN | — | T 14, Cl 100, Pr 90, R 12, Ra 12, W 25, G 45 |
| D+6 | RAIN | — | T 14, Cl 95, Pr 75, R 4, Ra 4, W 20, G 40 |
| D+7 | PARTLY_CLOUDY | — | T 16, Cl 40, Ra 0, W 25, G 45 |
| D+8 | CLOUDY | FOG | T 12, Cl 95, V 1.5 |
| D+9 | CLOUDY → PARTLY_CLOUDY | FOG → — | 09:00 T 11, Cl 95, V 2; 21:00 T 13, Cl 40, V 12 |
| D+10 | PARTLY_CLOUDY | — | T 17, Cl 35 |
| D+11 | CLEAR | — | T 18, Cl 5, Ra 0 |
| D+12 | CLEAR | — | T 18, Cl 5, Ra 0 |
| D+13 | CLEAR | — | T 16, Cl 5, Ra 0; 21:00 T 12 |

## Expected support and result semantics

For each date and populated point field, declare `ExpectedFieldSupport.Points` containing exactly the sample instants where that field is populated. `CONDITIONS` is declared for both samples on every date. Declare `PHENOMENA` only at samples with nonempty `P`. For each populated `Ra` or `Sn`, declare `ExpectedFieldSupport.Intervals` containing exactly its one-hour accumulation intervals. No absent field gets support, and explicit zero is a populated field. Use distinct field support for `Pr`, `Th`, `R`, `Ra`, `Sn`, `Sd`, `W`, `G`, and `V`. A complete fixture returns WX.9 `CatalogLoadResult.Success`; do not declare unsupported hours or fields complete. Validate the assembled catalog through `CatalogWindow.create` before returning it.

D−30…D−1 are 30 synthetic historical dates, D…D+13 are 14 synthetic forecast dates. The four edge dates are explicit clear buffers; D−28…D+11 is the 40-date event core. All 44 dates have two explicit facts. `HAZE` is a supplied synthetic phenomenon on D−16 and D−15 only, not an inference from low visibility. D−21 is heavy rain with flash-flood conditions, with no flood occurrence, warning, or official alert. Historical facts retain `SIMULATED`/`SYNTHETIC_METEOROLOGICAL_PRODUCT` provenance and are never described as verified observations.
