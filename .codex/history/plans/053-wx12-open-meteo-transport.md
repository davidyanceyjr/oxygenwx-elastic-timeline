# Cycle plan — 053-wx12-open-meteo-transport

State: ACTIVE
Cycle ID: 053-wx12-open-meteo-transport
Roadmap: WX.12
Objective: Add bounded Open-Meteo HTTP transport and revise prototype isolation guard

## Production boundary

Implement transport only under `app/src/main/java/com/oxygen/weather/timeline/data/`: injectable HTTP client, explicit request configuration, bounded timeouts, cancellation, response-size limit, and typed HTTP/transport/parse failures. Touch `app/build.gradle.kts` only for a necessary dependency; add only `android.permission.INTERNET` to the main manifest if needed. Revise `scripts/dev.py contract` to allow approved Internet while rejecting unintended location, background, debug, or release exposure. Preserve its package and pure-projection assertions. Add mocked fixtures/checks under `app/src/test/` only if separately authorized. Review manifest overlays and network security configuration as part of this boundary.

WX.1 and WX.9 are DONE (cycles 043 and 050). WX.9 supplies canonical source results and cancellation semantics; WX.12 does not construct a catalog or implement `WeatherCatalogSource`. WX.13 owns provider mapping. The objective-specific board `.codex/concepts/WX.12-transport-guards.png` (editable source: `.codex/concepts/WX.12-transport-guards.svg`) was shown in an X window and **approved by the owner on 2026-10-28**, without additional constraints.

## Agreed transport and provider contract (owner, 2026-10-28)

- Use an injectable OkHttp client with an asynchronous call wrapped for coroutine cancellation. Cancelling the coroutine cancels its HTTP call; cancellation propagates as cancellation, never as an ordinary transport failure. Set explicit connect, read, and whole-call timeouts, cap response bytes before JSON parsing, and close the response on every completion path. Choose and record the numeric limits during implementation after checking the expected 44-day payload size; keep them configurable for mocked boundary checks.
- Permit only HTTPS `api.open-meteo.com/v1/forecast` in this slice. Build encoded query parameters from typed coordinates and the location's IANA timezone. Set `past_days=30`, `forecast_days=14`, `timeformat=unixtime`, metric units, and a documented allowlist of hourly/daily variables; fix the exact list from the WX.13 canonical-field needs before coding, without mapping values in WX.12. Reject caller-supplied hosts, paths, free-form parameters, credentials, and redirects outside the approved endpoint.
- Return success payload or distinct HTTP status, timeout, connectivity, invalid JSON/envelope, and oversized-response outcomes. Preserve the HTTP status and a bounded, sanitized provider error reason when present; do not publish a catalog. Open-Meteo documents JSON error objects with HTTP 400 for invalid parameters. No automatic retry or request scheduling is added here.
- Keep transport dormant: no startup, UI, background, or live provider call. The free Open-Meteo API is for noncommercial use subject to published call limits; CC BY 4.0 requires attribution and indication of modifications. Record these obligations for later product UI and require an appropriate commercial API arrangement before commercial use. Do not add a customer key or endpoint in this slice.
- Allow `android.permission.INTERNET` only. Reject coarse/fine/background location, cleartext traffic, new background components, and debug fixtures or entry points in release. Preserve package and time-projection assertions. Inspect both merged debug and release manifests, not only source XML, and save the inspection result.

Primary provider references checked 2026-10-28: [Forecast API](https://open-meteo.com/en/docs) (request ranges, Unix time, archived `past_days`, JSON errors), [Terms](https://open-meteo.com/en/terms) (free-use limits and noncommercial restriction), [pricing and attribution](https://open-meteo.com/en/pricing) (commercial endpoint/license and CC BY attribution). [Android manifest merging](https://developer.android.com/build/manage-manifests) explains why variant and dependency manifests must be inspected. Recheck these sources at execution and record exact observed wording/limits in cycle evidence.

## Resolved execution contract (planning decision, 2026-10-28)

- Request exactly these hourly variables: `temperature_2m`, `relative_humidity_2m`, `precipitation_probability`, `precipitation`, `rain`, `snowfall`, `snow_depth`, `weather_code`, `pressure_msl`, `surface_pressure`, `cloud_cover`, `visibility`, `wind_speed_10m`, `wind_direction_10m`, `wind_gusts_10m`. Request exactly these daily variables: `weather_code`, `temperature_2m_max`, `temperature_2m_min`, `precipitation_sum`, `rain_sum`, `snowfall_sum`, `precipitation_probability_max`. This supports `CanonicalWeatherFacts.kt` and WX.13's daily summary needs. Do not request thunderstorm, hail, tornado, or official-alert probability fields: the Forecast API does not document them for this endpoint; WX.13 preserves them as unavailable. Do not interpret `rain` as including `showers`; `precipitation` remains the separately supplied total. Reverify provider variable support before implementation.
- Use `past_days=30`, `forecast_days=14`, `timeformat=unixtime`, IANA `timezone`, `temperature_unit=celsius`, `wind_speed_unit=kmh`, and `precipitation_unit=mm`. WX.13 resolves dates with IANA rules, not a fixed response offset. Supply one typed coordinate pair; no caller-selected host, path, variable set, or extra query parameter.
- Proposed defaults: connect timeout **5 s**, read timeout **15 s**, whole-call timeout **30 s**, and maximum decompressed JSON response **2 MiB**. Enforce the byte cap while reading, before parsing, stopping at cap + 1 bytes; reject oversized data distinctly and close the response. Keep limits configurable for mocked boundary checks. Before fixing numbers in code, measure or bound a representative 44-day response for this exact allowlist and record size and method in cycle evidence. If the cap or timeout is too small, revise with evidence inside this bounded transport objective; do not silently truncate or make limits unlimited. A live provider request is not implied by this planning decision.
- `scripts/dev.py contract` should parse XML instead of checking string absence in the main manifest. Retain app ID and `ElasticTimeAxis` assertions. Inspect source and merged debug and release manifests/configuration after applicable Gradle manifest tasks. Permit `android.permission.INTERNET` as the sole new permission; reject coarse/fine/background location, cleartext, and new services, receivers, providers, or debug-only entry points in release. Record an explicit limitation if merged artifacts cannot be obtained; the source manifest alone is not release evidence. Android's merger can import library and variant declarations.
- The WX.12 exit still requires recorded mocked request/failure tests. Repository `AGENTS.md` requires separate authorization to add or run tests; retain that gate and do not mark WX.12 DONE without tests and evidence.

## Functional invariants

- Use documented HTTPS Open-Meteo endpoints with explicit, bounded request options. No arbitrary URL, cleartext, hidden credential, background fetch, or app startup/UI invocation. Do not hard-code a license assumption into the transport until the provider policy review is recorded.
- Keep the HTTP client replaceable for mocked responses. Bound connection/read/overall waits and response bytes. Propagate cancellation.
- Return raw payload or transport-level failure. Validate response syntax/envelope only; do not interpret weather variables, invent missing fields, or describe archived model forecast as observation.
- Preserve app ID, demo startup and labels, legacy fixtures, selected-time authority, zoom, gesture ownership, accessible facts, RTL, reduced motion, and readability.
- Keep package isolation and pure time-projection contract checks. Enforce an explicit permission/provider allowlist and denials for location/background/debug exposure; inspect main, debug, and release merged manifests so overlays cannot silently add a capability.

## Ordered steps

1. [x] Create and obtain owner approval for the WX.12 transport/security boundary board reflecting the agreed contract above; record date, approved artifact, and constraints. The owner approved `.codex/concepts/WX.12-transport-guards.png` on 2026-10-28 after X-window review, without additional constraints.
2. Reverify current primary Open-Meteo documentation for Forecast/Archive capabilities, exact variables and units, `past_days` product meaning, response/error behavior, rate limits, attribution, license, and commercial-use requirements. Record dated direct links and any change from the agreed contract. Do not infer that archived model data are station observations.
3. Fix the exact variable allowlist, numeric timeout/size limits, and fixture payload shape against the WX.13 field needs and expected 44-day response size. Document them before coding; keep the agreed OkHttp, endpoint, cancellation, error, and guard boundaries. Escalate only a material provider-policy or product-scope conflict.
4. Implement isolated transport and required manifest/build changes. Revise `scripts/dev.py contract`, retaining unrelated assertions.
5. If separately authorized to add/run tests, check request encoding, HTTP errors, cancellation, timeout, malformed/truncated/oversized responses, and error bodies with mocked transport. Otherwise record the test exit as unmet and do not mark WX.12 DONE.
6. Run workflow, contract, applicable build/lint, `git diff --check`, and final diff inspection. Record exact results and limitations. Close only if the WX.12 exit is met; otherwise record the blocker and leave the roadmap outcome open.

## Acceptance and evidence

- Mocked request/failure tests cover success and encoded query parameters; non-2xx status and provider error body; connection failure, read/overall timeout, and cancellation propagation; malformed/truncated JSON, oversized body, and unsupported content shape. Assert that failures never publish a catalog. Use a fake client with no live network. Tests require separate user authorization under `AGENTS.md`.
- Contract accepts only the approved Internet permission and rejects unintended coarse/fine/background location, background components/fetch behavior, cleartext traffic, and debug-only exposure in release while retaining package and projection checks. Inspect debug and release merged manifests/configuration where tooling permits; record exact evidence or limitation. Keep existing gesture/isolation assertions wherever present.
- Dated primary-source links substantiate endpoint capability, product meaning, attribution, rate, and license assumptions.
- Evidence target: `.codex/test-artifacts/053-wx12-open-meteo-transport/`. Closure targets: `.codex/history/053-wx12-open-meteo-transport.md` and `.codex/history/plans/053-wx12-open-meteo-transport.md`. These are targets, not existing evidence.
- Planned commands: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, `python scripts/dev.py lint` as applicable, authorized focused tests only, `git diff --check`, and final diff review. This transport-only slice has no installed visual acceptance claim; reassess compact/large-font/RTL/reduced-motion evidence if UI scope changes.

## Risks / assumptions

- Provider endpoints, variables, and terms may change; verify current sources before implementation.
- WX.9 has `TransportFailureReason`, but mapping transport errors to source results belongs to WX.13.
- Text checks can miss merged permissions; pair contract checks with release manifest inspection and record tooling limits.
- Owner concept approval was recorded on 2026-10-28. Separate test authorization remains unresolved; planning does not itself authorize implementation.

## Out of scope

Provider-to-catalog mapping, live `WeatherCatalogSource`, 44-day ingestion, source selection, app wiring or live requests, geocoding/device location, persistence/cache, UI/atmosphere changes, official alerts, and the deprecated oxygenWX app.
