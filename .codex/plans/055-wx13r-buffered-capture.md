# Cycle plan — 055-wx13r-buffered-capture

State: ACTIVE
Cycle ID: 055-wx13r-buffered-capture
Roadmap: WX.13R
Objective: Add a one-day provider forecast buffer while preserving exactly the canonical D−30..D+13 window, then produce and review a replacement Chicago v2 recording with full target-window hourly support where the provider supplies it.

## Boundary and compatibility

WX.13 is closed and its approved artifact must remain unchanged:

- `.codex/test-artifacts/054-wx13-open-meteo-catalog-source/openmeteo-chicago-canonical-v2.json`
- SHA-256 `759197a75c2004bcb90e1e654f359443f5c9951ea12d37a379323c98d2aea700`

Change the Open-Meteo request from `forecast_days=14` to `forecast_days=15`; keep `past_days=30`, the documented variable lists, units, `asOf` handling, timezone semantics, and catalog target dates unchanged. Use the extra provider date only as a buffer. Do not place D+14 facts, support, daily summaries, or product segments in the 44-date canonical catalog. The original v2 approval remains valid for its original exact hash and limitations; a new recording is a candidate until separately reviewed and approved.

No UI, app activation, location selection, cache, persistence, commercial-use approval, or WX.15 storage work. Source remains dormant. No interpolation, synthetic fills, or reduced expected support based on response rows.

## Concept board

Proposed annotated boundary board: `.codex/concepts/WX.13R-buffered-capture.png`, editable source `.codex/concepts/WX.13R-buffered-capture.svg`. It shows the 15-day request and 44-date clipping boundary. Concept approval does not approve the replacement recording.

**Owner approval, 2026-10-28:** The owner approved `.codex/concepts/WX.13R-buffered-capture.png` as shown. No additional constraints were supplied.

## Steps

1. [x] Obtain owner approval of the objective-specific concept board; approval and absence of additional constraints are recorded above.
2. [x] Update the transport request and fixture to ask for 15 forecast days. Current primary [Open-Meteo Forecast API documentation](https://open-meteo.com/en/docs), checked 2026-10-28, permits 0–16 forecast days and describes hourly-range controls. Preserve the 44-date catalog semantics.
3. [x] Add focused mapper/request coverage proving D+14 response rows are ignored for facts, expected support, daily summaries, and product segments, while D+13 support remains based on the full IANA local-date grid. Preserve DST-safe support and existing `asOf` validation.
4. [x] Capture a fresh public Chicago response using the exact request. Preserve raw bytes and its timestamp, query, units, row counts, response hash, and attribution manifest. No meteorological values were redacted or edited.
5. [x] Produce a new canonical v2 candidate with the approved codec. Compare expected target support against returned facts and intervals; record the coverage inventory. The extra provider date remains only in raw evidence, not the canonical target catalog.
6. [x] Run scoped data tests, workflow, contract, build, lint, and `git diff --check`. Verify the cycle 054 canonical still has its approved SHA-256. Exact outputs and reports are retained under `.codex/test-artifacts/055-wx13r-buffered-capture/`.
7. [ ] Present the new canonical hash, provenance, transformations, coverage inventory, limitations, and deterministic codec evidence for separate owner approval. Do not close WX.13R until approval is recorded for this exact candidate hash.

## Acceptance and evidence

- The transport sends `forecast_days=15`; request tests assert the exact query.
- The catalog remains exactly D−30 through D+13, with D+14 excluded from all canonical values and support.
- Expected support is still derived from the fixed request policy and IANA local grid, not returned response density.
- Raw capture, manifest, coverage report, new v2 candidate, hashes, scoped test reports, exact verification output, and owner decision are retained under `.codex/test-artifacts/055-wx13r-buffered-capture/`.
- Existing cycle 054 canonical v2 bytes and review remain unchanged.

## Verification and risk

Planned checks: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, focused Gradle tests for `data.catalog.*` and `data.openmeteo.*`, `python scripts/dev.py build`, `python scripts/dev.py lint`, `git diff --check`, artifact hash verification, and final diff inspection. These are planned, not yet run.

Context estimate: low (one request parameter, focused mapping boundary, one external capture, and evidence packaging). Difficulty: 3/10. The main risk is that an additional requested forecast date may not yield complete support through D+13 for every field; the mapper must continue to surface any actual gaps honestly.

Closure targets: `.codex/history/055-wx13r-buffered-capture.md` and `.codex/history/plans/055-wx13r-buffered-capture.md`.
