# Cycle plan — 050-wx9-source-contract

State: ACTIVE
Cycle ID: 050-wx9-source-contract
Roadmap: WX.9
Objective: Introduce one source and load-result contract for live, simulated, and recorded catalog sources.

## Scope and production boundary

Define the provider-neutral boundary through which a source receives an explicit request and returns one canonical catalog or a typed load outcome. Use one app-scoped `java.time.Clock` as the shared current-time source in Kotlin and Compose wherever this slice needs it. Default to `Clock.systemUTC()`; use `Clock.fixed(...)` for deterministic checks. Read `clock.instant()` once at an operation boundary, then pass that captured `Instant` (`Now`) through the operation as the request's `asOf`; downstream functions do not read the clock again. Compose reads the clock only in an event/request handler, never during recomposition. Convert an `Instant` to local calendar time only with the relevant explicit `ZoneId`. Keep elapsed-time timers on a monotonic source, separate from wall-clock `Now`; do not add a timer/scheduler framework. Register/inject source implementations for the three origins without adding a source-selection UI, persistence, active catalog wiring, or a live network provider.

Expected affected areas, subject to source inspection before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/` — source request, source interface, typed result/failure metadata, and the smallest source registry/injection seam needed to exercise provider-independent outcomes.
- `app/src/main/java/com/oxygen/weather/timeline/` — only the minimal shared clock/`Now` access and injection point needed for Kotlin and Compose to use the same current-time value; no timeline behavior changes.
- `app/src/test/java/com/oxygen/weather/timeline/data/catalog/` — focused contract checks and independent fake live, simulated, and replay sources, if test changes are authorized before execution.
- `.codex/concepts/WX.9-source-interface.png` — objective-specific annotated source-boundary/outcome board; owner approval is required before implementation and must be recorded with its constraints in cycle evidence.
- `.codex/test-artifacts/050-wx9-source-contract/` — actual plan-stage findings, later authorized verification, and concept approval record.
- `.codex/plans/050-wx9-source-contract.md`, `.codex/current.md`, and the WX.9 evidence pointer in `docs/ROADMAP.md`.

No source loading in app startup or visible timeline/presentation/atmosphere behavior changes are in scope. Do not add provider DTOs, HTTP clients, persistence/cache, a simulated or replay dataset, location search, source-selection controls, or production active-source wiring. Do not alter canonical catalog facts or expand the weather vocabulary.

## Verified architecture and decisions for this plan

- `CanonicalCatalog`, `CatalogLocation`, `DatasetId`, `CatalogOrigin`, `CatalogProductKind`, and `CatalogProvenance` already define the shared provider-neutral payload and origin/product constraints.
- WX.5 defines calendar coverage and partial-data semantics; WX.6 added fixture adapters without connecting them to application startup. WX.5 and WX.6 are DONE, satisfying WX.9 dependencies.
- One app-scoped `java.time.Clock` is the source of current wall time, `Now`, for Kotlin and Compose wherever this slice needs it. Use `Clock.systemUTC()` by default and `Clock.fixed(...)` for deterministic checks; do not add a wrapper clock abstraction. At an operation boundary, read `clock.instant()` once and pass that `Instant` through as a value. A WX.9 source request carries explicit location, dataset identity, and `asOf: Instant`; capture `Now` once when creating the request and pass it as `asOf`. Sources and downstream request handling do not independently read wall time or a host-default timezone. Compose reads the clock only in event/request handlers, not during recomposition. Convert instants to local calendar values only with the relevant explicit `ZoneId`. Elapsed-time measurement/timers remain monotonic and separate from wall-clock `Now`; for example, three hours is `Duration.ofHours(3)` (10,800 seconds), while 108,000 seconds is 30 hours. Do not add timer scheduling or a separate clock abstraction in WX.9.
- Use coroutine cancellation for the source call: `load` is suspendable, and cancellation propagates as cancellation rather than becoming a transport failure or weather result. Reuse the existing coroutine support; do not add a runtime dependency for cancellation.
- A successful result contains a complete `CanonicalCatalog`; a partial result contains a usable canonical catalog and expected-support metadata that WX.5's existing `ExpectedFieldSupport` and `CoverageStatus` rules can interpret. Support is declared per local date and field: unsupported fields are absent from declared support, while supported but unreceived values count as partial/missing coverage. No-data, invalid-request, and transport-failure outcomes have typed details and no catalog payload. Cache state, when represented, is separate metadata; cached data retains `CatalogOrigin.LIVE`.
- WX.9 does not add or own an active catalog store. Prove the no-overwrite rule with a pure in-memory acceptance gate: success/partial may supply a candidate catalog; no-data or failure preserves the caller's existing catalog. Cancellation propagates without publishing a result. Do not create app state, persistence, or switching UI to prove this invariant.
- The owner approved the current objective-specific source contract/outcome board on 2026-10-27, as recorded in `.codex/test-artifacts/050-wx9-source-contract/board-approval.md`. Its request, shared-clock, outcome, coverage/provenance, cancellation, and no-overwrite constraints are design input for this plan, not implementation or acceptance evidence.

## Functional invariants

- Live, simulated, and replay sources implement one interface, consume the same request shape, and return the same canonical type and consumer-facing outcome semantics.
- Kotlin and Compose share one app-scoped `java.time.Clock` source for wall-clock `Now`, defaulting to UTC and using a fixed clock in deterministic checks. Each catalog request snapshots `clock.instant()` once as explicit `asOf`; request handling and sources do not independently read wall time or host-default timezone. Compose clock reads happen only at event/request boundaries, never during recomposition. Local-calendar conversion names an explicit `ZoneId`; elapsed timers use a monotonic source. Fixed-clock checks prove reproducibility.
- Every successful catalog's identity and provenance match the request and source origin according to existing canonical invariants. Cached LIVE data remains LIVE with cache state represented independently.
- Failure and no-data results cannot carry a candidate catalog and cannot replace a caller's valid catalog. Partial results remain explicitly partial and do not claim complete coverage.
- Coroutine cancellation stops/abandons the load distinctly from transport failure; transport/provider details do not cross the boundary as DTOs.
- No provider DTO, UI/presentation type, or source-specific weather model crosses into the canonical schema.
- Existing app startup, `SampleForecast`, fixture adapters, debug picker, selected-time behavior, and all visible/gesture/accessibility behavior remain unchanged.

## Ordered steps

1. Record source-grounded anchors from WX.2, WX.3, WX.5, WX.6, the canonical model files, and current dependency configuration in `.codex/test-artifacts/050-wx9-source-contract/README.md`.
2. Review the owner-approved WX.9 annotated board for request fields, source registration, cancellation, each result outcome, provenance/cache metadata, and the failure/no-overwrite boundary. Approval is recorded in cycle evidence; if the board changes the approved roadmap contract, stop for a bounded plan/roadmap revision.
3. Finalize the minimal request/result/interface shape against those approved constraints. Use one app-scoped `java.time.Clock` (`Clock.systemUTC()` by default; `Clock.fixed(...)` in deterministic checks) for Kotlin and Compose. Read `clock.instant()` once at the request/event boundary and pass that captured `Instant` as `asOf`; Compose must not read it during recomposition. Use explicit `ZoneId` for local-calendar conversion and a monotonic source for elapsed timers. Keep provider metadata opaque, use coroutine cancellation, and reuse WX.5 expected-support/coverage semantics. Do not introduce timer scheduling or another clock abstraction.
4. Add the common source interface and minimum registration/injection seam in `data/catalog`. Do not connect catalog loading to app startup or create concrete network/simulation/replay implementations.
5. Exercise independent live/simulated/replay fakes against the shared contract. Verify matching result semantics, provenance consistency, one captured `Now` propagated as `asOf`, partial coverage from declared expected support, no-data/failure handling, coroutine cancellation, and the pure no-overwrite acceptance gate.
6. Run only verification authorized for the implementation cycle, inspect `git diff --check` and the final diff, and preserve actual output under the cycle evidence directory. Record limitations and do not mark WX.9 DONE without evidence for its full roadmap exit.

## Acceptance and authorized verification

The WX.9 roadmap exit requires evidence that:

- Independent live, simulated, and replay providers use the same request, `CanonicalCatalog` payload, and consumer-facing result semantics.
- Kotlin and Compose obtain current wall-clock `Now` from one app-scoped `java.time.Clock` (`Clock.systemUTC()` by default). A request/event boundary captures `clock.instant()` once as `asOf`; location, `asOf`, and dataset identity propagate unchanged, and `Clock.fixed(...)` checks are deterministic. Compose does not read the clock during recomposition; local-calendar conversions use explicit `ZoneId`, and elapsed timers remain monotonic.
- Success, partial, no-data, invalid-request, and transport-failure outcomes are distinguishable and contain only permitted data; partial coverage follows WX.5 expected support; no-data/failure cannot replace an already valid catalog. Coroutine cancellation propagates separately and cannot publish a replacement.
- Cached LIVE origin is preserved separately from cache state, and no transport DTO crosses the source boundary.
- No app-startup catalog loading, source-selection UI, visible timeline behavior change, or persistent storage is introduced. Kotlin and Compose use the same shared `Now` source.

Planned verification for an implementation run: `python scripts/dev.py contract`, `python scripts/dev.py build`, and focused WX.9 contract checks using fixed inputs/fakes. The repository instruction prohibits adding or running tests unless the user asks; these focused checks are therefore proposed acceptance work, not authorization to execute them. Obtain that authorization before changing/running tests. No device or visual acceptance applies because this is a data-boundary slice with no UI behavior change. Run `git diff --check` at closeout.

Evidence targets:

- Plan: `.codex/plans/050-wx9-source-contract.md`
- Actual evidence: `.codex/test-artifacts/050-wx9-source-contract/`
- Closeout: `.codex/history/050-wx9-source-contract.md`
- Archived plan: `.codex/history/plans/050-wx9-source-contract.md`

Planning verification performed: `python scripts/dev.py workflow` passed with `Workflow scaffold: OK`; `.codex/current.md` was IDLE before planning; cycle 049/WX.6 is the latest closed cycle; WX.9 is the sole NEXT slice and its WX.5/WX.6 dependencies are DONE. No application tests, build, or implementation checks were run during planning.

## Risks / assumptions

- Compose needs access to the same app-scoped `java.time.Clock` as Kotlin. Read it only at event/request boundaries, capture `Now` once, and pass the resulting `Instant` downstream. Keep the seam limited to wall-clock access; do not add timer scheduling, timeline redesign, or a wrapper clock abstraction. Use explicit `ZoneId` for civil-time conversion and monotonic time for elapsed timers.
- The no-overwrite condition references an active dataset although WX.9 excludes active-source wiring. This plan limits proof to a pure acceptance gate/test harness; introducing application state or persistence would exceed scope.
- A partial outcome needs expected-support metadata alongside its catalog. WX.5 already defines per-date/per-field point or interval support and derives coverage status; stop if source results cannot carry that information without changing WX.5's contract.
- The source contract/outcome board approval is recorded. Implementation still follows the remaining plan review and activation steps.
- Tests are necessary to demonstrate several exit conditions, but repository instructions require an explicit user request before tests are added or run.

## Out of scope

- HTTP/network transport, Open-Meteo DTOs or source implementation, deterministic 44-day simulation, recorded replay loading, persistence/cache, refresh policy, location/geocoding, or active catalog storage/switching.
- App-startup catalog loading, UI/source selection, debug picker changes, presentation/projection migration, atmospheric rendering, timeline gestures, accessibility behavior, or visual redesign. Only the shared `Now` access required for the source request is in scope.
- Canonical schema/weather-fact changes, official alerts, inferred weather values, app identity/signing/release work, deprecated oxygenWX edits, and broad/full-suite verification.
