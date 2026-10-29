# Cycle plan — 058-wx17-location-search-source

State: ACTIVE
Cycle ID: 058-wx17-location-search-source
Roadmap: WX.17
Objective: Define provider-neutral place-search results and an initial Open-Meteo geocoding adapter that can produce a valid `CatalogLocation` with app-assigned local identity.

## Status and authority

- WX.17 is the sole `NEXT` roadmap slice; WX.3 and WX.12 are `DONE`. This cycle is an initial draft only. It has not been activated and authorizes no application implementation.
- The product owner approved the WX.17 logic/code concept board as shown on 2026-10-28: `.codex/concepts/WX.17-location-search-source.png` and editable `.svg`. Approval covers the depicted search, ambiguity, identity, failure, and later-slice boundaries. The board is design input, not implementation evidence.
- The separately approved WX.19 “selector open” UI board and installed baseline capture are future UI design inputs. The owner has separate plans for main-screen access. WX.17 does not implement or decide that access route.

## Verified baseline and proposed affected areas

- `data/catalog/CatalogIdentity.kt` already defines opaque nonblank `LocationId`, finite bounded `GeoCoordinates`, and `CatalogLocation` with an IANA `ZoneId`. `CatalogLocation` has no display-name or provider-ID field.
- `data/openmeteo/OpenMeteoTransport.kt` is a forecast-endpoint-specific, injectable OkHttp transport with cancellation, explicit timeouts, a response-byte limit, and typed failures. It is a pattern to reuse, not a geocoding endpoint to modify in place.
- `data/openmeteo/OpenMeteoCatalogSource.kt` is dormant and consumes an explicit `CatalogLocation`; search must not implicitly start catalog loading or change the current demo screen.
- Expected implementation areas, subject to plan review before activation: one provider-neutral search contract under `app/src/main/java/com/oxygen/weather/timeline/data/catalog/`; a bounded geocoding transport/mapper or adapter under `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/`; focused fixtures and host checks under the matching `app/src/test/` packages. The owner authorized locally scoped WX.17 tests on 2026-10-28. Changes to the existing identity type should be minimal and only if the agreed conversion cannot be expressed alongside it.
- Planned evidence directory: `.codex/test-artifacts/058-wx17-location-search-source/`. Planned closeout: `.codex/history/058-wx17-location-search-source.md` and archived plan `.codex/history/plans/058-wx17-location-search-source.md`.

## Functional contract and invariants

1. A provider-neutral search interface accepts manual place text and returns distinct typed outcomes for matches, no matches, and request/response failure. The provider DTO and endpoint details remain inside `data/openmeteo/`.
2. Each usable result carries a human-readable place name and enough region/country context to distinguish duplicates, exact returned latitude/longitude without display rounding, a validated IANA timezone, and source/attribution metadata. Display labels are descriptive, never identity keys.
3. Mapping a chosen result to `CatalogLocation` requires an app-assigned, opaque `LocationId`. A provider result ID, place name, or rounded coordinate pair cannot become the local storage key. The later saved-location layer retains the assigned ID; WX.17 does not persist it.
4. Invalid or absent coordinates or timezone cannot produce a `CatalogLocation`. Preserve an explicit unusable-result or invalid-response disposition; do not guess a timezone from coordinates, use a device timezone, substitute zero, or silently select a similarly named place.
5. Keep empty results distinct from timeout, connectivity, HTTP, malformed/oversized response, and cancellation. Cancellation propagates; no search failure should initiate a weather fetch or replace a catalog.
6. Preserve the installed prototype's DEMO DATA labeling, fixture values, startup, timeline gestures, selected time, rendering, accessibility semantics, and app permissions. No Android device-location permission is required for manual search.

### Resolved search and identity semantics

- A successful response with at least one usable candidate returns those candidates in provider order, including same-name candidates, plus an explicit count or typed disposition for rejected rows. A bad row does not erase valid peers. Each rejected row is unavailable for selection; do not repair its coordinates, timezone, name, or distinguishing region/country by inference. Do not expose raw provider DTOs in the provider-neutral contract.
- An absent or empty `results` array means no matches only when the response is otherwise a valid success envelope. A nonempty array with no usable candidates is an invalid/unusable-response outcome, not no matches. A malformed envelope, non-array `results`, or provider error is a response failure. Preserve the existing separate timeout, connectivity, HTTP, oversized-response, and cancellation dispositions. These rules prevent malformed provider data from appearing as an honest empty search.
- A usable candidate requires a nonblank place name, finite in-range returned coordinates, a valid IANA region timezone accepted by `CatalogLocation`, and at least one nonblank returned country name or country code. Administrative fields may be absent; their absence alone does not invalidate a row. A row with neither country field is unusable. Preserve available administrative context, coordinate precision, and provider order. The provider's result ID may be retained solely as optional source-reference metadata; neither it nor a display label is a `LocationId`. Carry Open-Meteo/GeoNames attribution as descriptive source metadata independent of `CatalogLocation`.
- The search adapter never creates a local ID. A pure conversion of a specifically chosen usable result accepts a caller-supplied `LocationId` and constructs `CatalogLocation` from that ID, the returned coordinates, and validated timezone. The accepting application layer creates a fresh opaque ID once when the user accepts a candidate; WX.18 retains that same ID if the location is saved. Repeating conversion with the same candidate and ID is stable; a new acceptance receives a new ID. WX.17 supplies the conversion seam but does not own acceptance state, ID persistence, or duplicate-save policy.
- Basis: `CatalogIdentity.kt` requires an explicit `LocationId`, validated `GeoCoordinates`, and IANA `ZoneId`; the approved WX.17 board says “Create a new local ID once; retain it for later saves.” Open-Meteo's geocoding documentation (checked 2026-10-28, https://open-meteo.com/en/docs/geocoding-api) documents the `results` array, optional omitted fields, result ID, WGS84 coordinates, timezone, and GeoNames attribution. Candidate rejection and aggregate-outcome rules above are this cycle's contract decisions, not provider guarantees.

### Provider recheck and pre-execution decision table (2026-10-28)

- Primary references: https://open-meteo.com/en/docs/geocoding-api, https://open-meteo.com/en/licence, and https://open-meteo.com/en/terms. The documented public endpoint is `https://geocoding-api.open-meteo.com/v1/search`; `name` is required, JSON is the default format, `count` defaults to 10 and allows at most 100, and `language` defaults to `en`. The provider documents that empty and one-character names return no results, two-character names match exact names, and names of three or more characters use normalized prefix matching. These are provider behaviors, not a reason to make a malformed response look empty. Keep request count and response bytes bounded; do not assume the weather forecast endpoint or its parser applies to geocoding.
- The documented success object may omit empty fields. A provider error object contains `error: true` and `reason`, ordinarily with HTTP 400. The result `id` is a provider reference only. Returned `latitude`/`longitude` are WGS84 decimal degrees and `timezone` uses the time-zone database. The geocoding page credits GeoNames for location data; the licence page specifies CC BY 4.0 credit, a licence link, indication of changes, and an Open-Meteo link next to displayed location data. WX.17 must carry enough descriptive attribution metadata for later display; WX.19 owns displaying it. The free API terms apply to noncommercial use, while commercial use requires the applicable paid access. Do not bake an unverified production access tier into this dormant adapter.

| Input or transport result | Provider-neutral disposition | Candidate handling |
| --- | --- | --- |
| Blank or one-character manual query | No matches without a network request, matching documented provider behavior | No candidate or local ID |
| Successful object with absent or empty `results`, no provider error | No matches | No candidate or local ID |
| Successful object with one or more usable rows | Matches, with provider order and an explicit rejected-row count or typed disposition | Keep same-name rows distinct; reject bad peers individually |
| Successful object with a nonempty `results` array but zero usable rows | Invalid/unusable response | No selectable candidate |
| Non-object JSON, non-array `results`, or provider `error: true` on a success HTTP response | Invalid/provider response failure, never no matches | No selectable candidate |
| Non-success HTTP status | HTTP failure, with a bounded sanitized reason if available | No selectable candidate |
| Timeout, connectivity error, or oversized body | Distinct failure matching the transport cause | No selectable candidate |
| Caller cancellation | Propagate cancellation | No candidate, catalog fetch, or catalog replacement |

Usability is checked per row before constructing a provider-neutral result. The owner confirmed on 2026-10-28 that a returned country name or country code is sufficient; retain any returned administrative fields without inferring absent fields. A chosen usable result plus a caller-supplied `LocationId` converts purely to `CatalogLocation`; the provider result ID, label, and rounded coordinates never supply that ID. The accepting application layer creates an opaque ID once per new acceptance; WX.18 later retains it. No acceptance state or persistence belongs to WX.17.

## Ordered draft steps

1. Reinspect the approved board, WX.3 identity contract, WX.12 transport contract, current Open-Meteo integration, and exact geocoding API documentation/terms. Record the endpoint, request/response fields, limits, attribution, and any mismatch with the board before coding.
2. Finalize the small provider-neutral result and outcome shapes against the resolved semantics above and a deterministic fixture matrix. Keep source-reference/attribution metadata on search results, outside `CatalogLocation`; escalate before activation if a verified provider requirement conflicts with that boundary.
3. Implement the isolated Open-Meteo geocoding request and mapping with injectable transport, bounded input/output, cancellation, and explicit failure results. Keep it dormant until a later UI/controller slice calls it.
4. Add and run locally scoped WX.17 fixture tests, as authorized by the owner on 2026-10-28. Cover duplicate names across regions/timezones; country-name-only and country-code-only usable rows; absent country name and code with or without administrative fields as unusable; coordinate precision; valid/invalid IANA zones; blank/no-result queries; malformed, mixed-row, all-invalid, and HTTP/error responses; metadata/attribution; and local-ID independence from provider IDs. No live network is required for these checks.
5. Run applicable authorized workflow, contract, build, and focused verification; inspect `git diff --check` and the final diff; record exact outputs and limits under the evidence directory. Close WX.17 only if every roadmap exit condition has evidence. Otherwise close the cycle with the precise remaining condition and retain WX.17 as open or blocked as appropriate.

## Completion criteria and evidence

- The documented search contract and mapper produce `CatalogLocation` values with returned coordinates, validated IANA timezone, and a caller-supplied local ID independent of any provider ID.
- Recorded fixtures and locally scoped test results demonstrate ambiguous same-name results in different regions/timezones, country-name-only and country-code-only candidates, missing country context, exact coordinate preservation, no matches, malformed/unusable and mixed-row results, provider failures, and source metadata/attribution behavior. The owner authorized these WX.17 tests on 2026-10-28; record exact commands and results under this cycle's evidence directory.
- Evidence records exact verification commands/results and provider-reference dates. `python scripts/dev.py workflow`, `python scripts/dev.py contract`, and `python scripts/dev.py build` are planned if implementation proceeds. `git diff --check` and final diff inspection are required for closeout. No installed visual acceptance is claimed for this data-only slice.
- The concept board approval is recorded, and no UI, saved-location state, device-location permission, catalog activation, or deprecated oxygenWX changes appear in the slice diff.

## Execution record — 2026-10-28

- Rechecked the primary Open-Meteo geocoding API, licence, and terms. Endpoint, result fields, omitted optional data, matching behavior, attribution, and noncommercial free-tier restriction remain consistent with this plan; see `.codex/test-artifacts/058-wx17-location-search-source/provider-recheck.md`.
- Added provider-neutral candidate/outcome types and a pure conversion using a caller-supplied `LocationId`; added an isolated bounded Open-Meteo transport/mapper. Provider references remain optional descriptive metadata, and no ID is created by the search adapter.
- Focused fixtures passed for ambiguous names, regions/timezones, country context, invalid and mixed rows, exact coordinates, local-ID independence, attribution, no-result and malformed responses, HTTP/timeout/connectivity/oversized failures, and cancellation.
- Exact verification results and scope limitations are recorded in `.codex/test-artifacts/058-wx17-location-search-source/README.md` and attached logs.
- The unrelated WX.16 working-tree changes present before implementation were left outside this slice.

## Risks and open draft assumptions

- Open-Meteo geocoding response fields, usage terms, and attribution can change; reverify current primary provider documentation during execution. Existing WX.12 forecast transport assumptions do not automatically apply to the geocoding endpoint.
- Exact provider-neutral field names and incidental representation remain implementation choices within the resolved candidate/outcome contract. Reverify provider response fields and terms during execution; escalate a material mismatch rather than silently changing search semantics.
- The later accepting application layer creates a local ID once per accepted candidate; WX.18 owns durable retention and duplicate-save policy. WX.17 only establishes a pure caller-ID conversion contract.
- The working tree already contained unrelated WX.16 closeout/source changes when this draft began. Do not absorb or overwrite them in WX.17 implementation evidence.

## Difficulty and model fit

Estimated implementation difficulty: **6/10**. The change is confined to the data/provider boundary, but it combines transport behavior, provider schema validation, identity semantics, and a fixture matrix. For relative model fit only, `gpt-6.1-sol` is the least costly available model with a credible path to the contract and evidence; use `gpt-6-astra` if provider response ambiguity or identity/lifecycle coupling requires a larger design decision. No runtime price comparison is claimed.

## Out of scope

Saved-location persistence or selection (WX.18); the approved “selector open” UI and main-screen access (WX.19 and owner-planned UI slices); weather loading/atomic activation (WX.20); startup integration (WX.26); Android GPS/location permissions; background search; provider-to-catalog weather mapping changes; official alerts; atmosphere/timeline changes; and changes to the deprecated oxygenWX app.
