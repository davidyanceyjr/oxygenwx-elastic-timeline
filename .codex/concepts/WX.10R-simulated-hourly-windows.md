# WX.10R concept — one synthetic fact at every real local hour

**Status:** Owner approved 2026-10-29. The owner selected full real-hour coverage and deterministic within-day event windows, then approved the 9 AM/9 PM anchor schedule below. This data-shape board translates those decisions into a reviewable generation contract. It does not claim observed weather or define the behavior of LIVE/provider catalogs.

## Data flow

```text
request(location, datasetId, asOf)
  -> anchor D = asOf in location IANA timezone
  -> 44 local date windows: D-30 ... D+13
  -> iterate each date's true [local midnight, next local midnight) as Instants
     step by one elapsed hour
  -> one fact at each instant (23, 24, or 25 facts for that date)
  -> attach deterministic event/measurement state
  -> report field support by the fact instant or accumulation interval's date
```

## Within-day event windows

Each date retains the existing 9 AM day anchor and 9 PM night anchor, including their existing condition, phenomenon, measurement, null, and explicit-zero values.

| Local-hour window | State used |
| --- | --- |
| 9:00 AM through 8:00 PM | Existing morning/day anchor |
| 9:00 PM through 8:00 AM | Existing evening/night anchor, including explicit night overrides |

The active state's condition and phenomenon sets form the event window. Its nullable measurements are held at the approved anchor values for those hours. No additional weather field is inferred, no missing value becomes zero, and accumulated rain/snow values remain one-hour amounts on the corresponding one-hour interval. This deterministic step profile is explicitly synthetic.

## DST and support boundaries

- Generate the instant grid from `date.atStartOfDay(zone).toInstant()` up to (but not including) the next local date's start, adding exactly one elapsed hour each step. Never construct each local hour independently with `LocalDateTime.atZone`, which would collapse the repeated fall-back hour.
- The repeated fall-back wall hour produces two distinct `Instant` facts. A spring-forward gap produces no fact for the nonexistent wall hour.
- Point-field expected support is assigned by `validTime`'s local date. One-hour accumulation support is assigned to the local date containing the complete `[start,end)` interval; an interval ending at local midnight belongs to the preceding date. The midnight fact's point support remains on the new date.
- Preserve the fixed 44-date horizon and true local-day boundaries. This SIMULATED completeness commitment does not imply complete support for provider or unrelated replay catalogs.

## Preservation and identity

- Keep deterministic request output, the one fixed 44-day event sequence, all existing event families, and the current 9 AM/9 PM anchor values.
- Keep synthetic product/provenance labels truthful and version any catalog/product identity required so old 88-fact cached data cannot masquerade as hourly output.
- Do not change UI layout, provider ingestion, official alerts, the canonical schema, or decorative weather rendering in this slice. WX.25 consumes the denser facts and owns installed timeline/browser acceptance.

## Owner approval and constraints

The owner approved on 2026-10-29: a full date's every-real-hour Instant grid, morning anchor values from 9 AM through 8 PM, night anchor values from 9 PM through 8 AM, step-held synthetic values, preserved missingness and one-hour accumulation semantics, and date support assigned by point time versus complete accumulation intervals. Constraints: retain synthetic provenance; do not imply hourly provider completeness, interpolate unsupported fields, or add UI/alert behavior in WX.10R.
