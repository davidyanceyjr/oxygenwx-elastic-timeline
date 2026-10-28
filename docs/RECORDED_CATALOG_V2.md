# Canonical catalog recording v2

V2 extends the deterministic, UTF-8 JSON recording contract in [`RECORDED_CATALOG_V1.md`](RECORDED_CATALOG_V1.md). Encoding and decoding remain pure; persistence belongs to WX.15. The writer selects v1 for a v1-shaped catalog (no product segments, provider daily summaries, or daily support), preserving the approved v1 fixture byte-for-byte. It selects v2 when any of those values are present.

## Root shape

V2 has the v1 root fields—`schemaVersion`, `location`, `datasetId`, `provenance`, `asOf`, `facts`, and `expectedSupport`—plus `productSegments`, `dailySummaries`, and `dailyExpectedSupport`. `schemaVersion` is integer `2`. V1 records continue to decode with no invented segments, daily summaries, or daily support.

`productSegments` is an ordered array of `{startDateInclusive, endDateExclusive, productKind, sourceId, productId, issuedAt}`. Dates define half-open local calendar spans. Segments are nonempty, disjoint, and within D−30 through D+13. Adjacent segments with identical product kind and verified source/product/issue identity must be coalesced. A gap means no row was returned for those dates. Segment product kinds cannot be `MIXED` or synthetic. At catalog level, one segment kind is repeated as the envelope product kind; multiple kinds use envelope kind `MIXED`. `MIXED` describes the aggregate only; segments define date-level meaning. Replay preserves segment meaning and changes delivery origin only.

`dailySummaries` is ordered by unique local date. Each row contains `date`, true IANA `support` (`start`, `end` instants), `temperatureMaxC`, `temperatureMinC`, `precipitationProbabilityMaxPercent`, `conditions`, `phenomena`, `rainAmountMillimeters`, `totalPrecipitationMillimeters`, `snowfallCentimeters`, and `origin`. Current canonical summaries use `PROVIDER_SUPPLIED`. Null values remain JSON null; known enum sets are ordered by enum name. Daily support is the location's actual `[date.atStartOfDay(zone), nextDate.atStartOfDay(zone))` interval, including 23/25-hour DST dates.

`dailyExpectedSupport` is an array of `{date, field}` entries, one per requested daily field/date. Fields are ordered by date and enum name. Missing rows and null/unknown field values remain expected and therefore lower daily coverage. V2 keeps this independent from hourly `expectedSupport` and from hourly-derived daily amounts; derivation never replaces provider-supplied summaries.

## Determinism, validation, and replay

V2 ordering is deterministic: facts by valid time; support by date and field name; product segments in date order; daily summaries by date; daily support by date and field name; enum sets by enum name; instants and intervals by start/end. Decode rejects duplicate dates/fields, invalid local-day support, overlapping/out-of-order or unnecessarily split segments, inconsistent envelope product kind, unsupported versions, malformed identities, and invalid canonical ranges. Unknown fields remain invalid.

V1 replay retains its original uniform product meaning and has no synthesized date segments or daily values. V2 replay preserves units, timestamp precision, source metadata, segment identities, supplied daily values, daily coverage declarations, hourly support, and missingness. Only delivery `origin` becomes `REPLAY`; the captured original origin remains in replay metadata. A usable result is `Success` only when every declared hourly and daily field/date support is complete; otherwise it is `Partial`.
