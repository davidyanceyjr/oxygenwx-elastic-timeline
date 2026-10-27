# OxygenWX 44-Day Weather Catalog Roadmap — Audit Record

The WX workstream from this source draft is now integrated into [`ROADMAP.md`](ROADMAP.md). This file records the pre-integration audit; `ROADMAP.md` is the canonical roadmap. The audit changed documentation and roadmap ordering only. No WX implementation or cycle has started.

## Audit result

- Reviewed all 28 WX leaves against [`ROADMAP_SPECIFICATION.md`](ROADMAP_SPECIFICATION.md). Each has a stable ID, one status, Objective/scope, Depends on, Exit, and Evidence fields. All dependency IDs resolve within the WX set, and the dependency graph has no cycles.
- Corrected the draft's Evidence fields to identify the planned artifact, closeout, plan, and archived-plan paths. Chronological cycle IDs remain unassigned until each slice is planned.
- Clarified WX.1's documentation-only boundary: the actual `scripts/dev.py` contract change belongs to WX.12.
- Strengthened WX.14's time rule to request Unix instants and convert through the location's IANA timezone across DST. Open-Meteo documents that its request-time fixed UTC offset ignores DST changes within the requested range; it supports `past_days` 0–92 and up to 16 forecast days. The 30+14 request is within those parameter limits, while actual per-location coverage remains to be measured. [Forecast API documentation](https://open-meteo.com/en/docs)
- Open-Meteo describes `past_days` as archived forecasts, and the Historical Forecast API as archived model forecasts. The draft correctly avoids labeling these as station observations. [Historical Forecast API](https://open-meteo.com/en/docs/historical-forecast-api)
- Preserved the existing prototype authorities as controlling until WX.1 closes. WX.1 is now `NEXT`; ATM.8 is `PLANNED`, with an explicit return to ATM.8 after WX.28 and no artificial dependency edge.

## Remaining planning checks

Provider fields, product coverage, attribution/license terms, and actual 44-date completeness must be revalidated in the bounded WX.12–WX.14 plans. The audit does not claim provider acceptance, implementation evidence, or app behavior changes.

## Follow-up test-scope audit

The roadmap now keeps slice-specific behavior checks in their implementation slices and assigns broad installed experience checks to two suite-wide slices: WX.29 after timeline integration, then WX.28 for final integration/release acceptance after startup and branding. WX.19, WX.25, and WX.26 no longer carry broad cross-app font/orientation/RTL/TalkBack/reduced-motion matrices.
