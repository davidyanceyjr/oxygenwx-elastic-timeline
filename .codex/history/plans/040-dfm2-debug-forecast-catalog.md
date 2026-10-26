# Cycle plan — 040-dfm2-debug-forecast-catalog

State: ACTIVE
Roadmap: DFM.2
Objective: Implement the owner-approved DFM.1 matrix as a deterministic, immutable typed catalog available only to debuggable builds, preserving each scenario field and intentional missing value without changing the normal forecast fixture.

## Production boundary

Implementation is limited to the isolated Oxygen Time Lab repository. Expected changes:

- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugForecastCatalog.kt` — debug-build scenario types, immutable catalog, transition definitions, and deterministic inventory projection.
- `app/src/testDebug/java/com/oxygen/weather/timeline/data/debug/DebugForecastCatalogTest.kt` — focused catalog, projection, field, missingness, and sequence checks.
- `.codex/test-artifacts/040-dfm2-debug-forecast-catalog/README.md` and `forecast-catalog-inventory.md` — actual verification record and reviewable inventory output.
- `.codex/history/040-dfm2-debug-forecast-catalog.md` and `.codex/history/plans/040-dfm2-debug-forecast-catalog.md` at closeout.
- `.codex/current.md` while the cycle is planned/active.

Make no application UI, renderer, normal forecast fixture, provider, dependency, production oxygenWX, or unrelated roadmap changes. Avoid build configuration changes unless source-set inspection during execution proves them necessary; if so, record the exact reason and keep the debug catalog excluded from release behavior.

## Verified baseline and invariants

- Before planning, `.codex/current.md` was IDLE and `python scripts/dev.py workflow` reported `Workflow scaffold: OK`. DFM.2 is the sole NEXT roadmap slice; DFM.1 and its dependency ATM.6 are DONE.
- The owner-approved implementation contract is `.codex/concepts/DFM.1-debug-forecast-matrix.md`. It specifies twelve stable scenario IDs, typed fields, explicit nulls, three transition sequences, provenance, and the illustrative per-row times. Its sequence rule is one synthetic hour per adjacent transition step; sequence-local times are distinct from the table's illustrative row times.
- Owner clarification on 2026-10-26: each of the three sequences starts at the synthetic local time `2026-10-20T00:00`; each subsequent step advances by one hour. Sequence time remains a separate field and never replaces scenario `validTime`.
- Owner approval on 2026-10-26: the owner approved the completed DFM.2 catalog and generated inventory for cycle closeout. This approval does not approve or widen the separate DFM.3 browser UI.
- `SampleForecast.kt` defines the normal fixture and canonical `SkyCondition`, `PrecipitationType`, and tagged `PrecipitationAmount` types. Do not add debug-only snow condition, thunderstorm probability, warning state, or debug provenance to those canonical types or mutate normal fixture output.
- `ForecastProjection.kt` derives the normal `ForecastView` from `SampleForecast`; it does not currently accept a debug scenario. DFM.2 has no visual mapping or browser scope. Keep catalog-to-inventory projection pure and separate from the normal selected-time/presentation path; do not force debug-only fields through `ForecastView` or omit them to fit it.
- The module currently has `main` and `test` sources and no `debug` or `testDebug` trees. It uses Gradle 9.7 and Android Gradle Plugin 9.4. Android's documented build-type and unit-test source layouts support `src/debug/java` and `src/testDebug/java`; the current task list includes `compileDebugUnitTestKotlin` and `testDebugUnitTest`. This confirms the planned source-set shape without running tests. During execution, the catalog test must import the debug catalog from `testDebug`, and the release artifact must be checked for absence of `DebugForecastCatalog`.
- `app/build.gradle.kts` declares no custom `buildTypes` or `sourceSets`, so this cycle relies on AGP's conventional `debug`, `release`, and debug unit-test source-set wiring. The existence of Gradle tasks alone is not proof of class isolation; the direct `testDebug` import and release APK DEX inspection below are the acceptance evidence.
- The shell's default `java` is OpenJDK 8, which Gradle 9.7 rejects. A Gradle task-list inspection succeeded when invoked with the installed JDK 27. Use a Gradle-supported JDK 17+ for cycle verification; if none is available or the selected JDK fails app compilation, record the exact host blocker and stop rather than moving debug types into canonical production models.
- Fixture rows are synthetic, deterministic, immutable, and visibly marked `SYNTHETIC_DEBUG`. Historical observations in DFM.1 are plausibility references only and supply no catalog fields. This catalog is not a live forecast or official warning feed.
- Preserve the existing time authority: zoom/scale never changes selected forecast time. DFM.2 adds no timeline selection or UI access path.

## Bounded implementation decisions

- Put debug-only scenario definitions and the catalog in the Android `debug` source set, with focused unit checks in `testDebug`. Verify that the catalog is absent from release compilation/package surface as supported by the project build. Do not add a main-source accessor or release fallback; DFM.2 has no UI caller.
- Treat source-set isolation as a bounded verification gate: `:app:testDebugUnitTest` must compile and run a test that directly imports `DebugForecastCatalog` from `src/testDebug/java`; `:app:assembleRelease` must produce the release APK; inspect every `classes*.dex` entry in that APK for the catalog class descriptor/name and record the APK path, inspection command, and result in the cycle evidence. A successful test task proves debug/testDebug inclusion; the release DEX scan proves the catalog is absent from the packaged release artifact.
- If the direct debug test import fails, first record the exact task/compiler output and inspect the configured Gradle variant/source-set wiring. If conventional wiring is confirmed broken and a bounded `sourceSets` adjustment is demonstrably required, make only that build-configuration change, document the evidence and rationale, then repeat both the debug test and release APK checks. If the release scan finds the class, or the source-set failure cannot be corrected within this boundary, stop, preserve the evidence, and record the cycle as incomplete for a bounded plan revision; do not move the catalog into `main`, add a release fallback, or proceed to UI work. A host/JDK failure is a host blocker, not evidence that source-set configuration needs changing.
- Represent debug condition vocabulary independently (`CLEAR`, `PARTLY_CLOUDY`, `CLOUDY`, `RAIN`, `SNOW`, `STORM`); retain precipitation identity (`NONE`, `RAIN`, `SNOW`) as its own field. Model generic precipitation chance and thunderstorm chance as separate nullable percentages; preserve typed nullable amount (`RainLiquidMm` or `SnowDepthCm`) and warning placeholder (`NONE` / `PRESENT`) independently.
- Preserve each DFM.1 row's approved illustrative `validTime` in the scenario definition. Each immutable sequence step is a separate `(scenarioId, sequenceTime)` reference; `sequenceTime` starts from an explicit synthetic local time and advances exactly one hour per adjacent step. It never replaces, copies over, or mutates the referenced row's `validTime` or other fields. The inventory report displays both time roles separately. Neither time is a location-aware instant or an observation cadence.
- Expose a deterministic pure inventory projection/report that includes every scenario's stable ID, valid time, debug condition, generic probability, precipitation type and typed amount, thunderstorm probability, warning state, provenance, and every absent value explicitly. Include sequence order/times so a reviewer can compare the output to DFM.1. This is data review output, not a UI or scene projection.
- Do not derive, normalize, interpolate, or carry fields between rows or sequence steps. Range-check supplied percentages in `[0,100]`; preserve `null` as unknown/not supplied and explicit `0` as zero. Preserve units and amount variants.

## Ordered steps

1. Reconfirm the workflow/current state, DFM.1 approval and all twelve scenario rows; inspect the existing canonical model/projection boundary, configured variant/source-set wiring, and available Gradle tasks. Treat task-list output as discovery only; step 4's direct debug test import and release APK DEX scan are the isolation proofs. Run Gradle with a supported JDK 17+.
2. Implement immutable debug-only types and the catalog for all approved rows and sequences. Match every literal field and null to DFM.1; use scenario references and separate hourly sequence-step timestamps as defined above.
3. Implement the deterministic inventory projection/report with explicit absent markers and provenance. Keep this independent of `SampleForecast`, `ForecastView`, UI, and atmosphere rendering.
4. Add focused debug unit checks for stable row IDs/count, deterministic repeated reads, exact field values/types/nulls, percentage bounds, transitions and one-hour sequence timing, separate row/sequence timestamps, report coverage, and unchanged representative `SampleForecast` outputs. Run `:app:testDebugUnitTest` with a direct catalog import, build `:app:assembleRelease`, and inspect the produced release APK's DEX entries for absence of the catalog class as the source-set isolation gate described above.
5. Run only the verification authorized by this plan, save actual command outcomes and the rendered inventory report under the cycle evidence directory, inspect `git diff --check` and the complete diff, then close or record any failed exit and limitations. Leave DFM.2 not DONE unless every roadmap exit condition and owner review are satisfied.

## Acceptance and authorized verification

DFM.2 is ready for owner review only when:

- All twelve approved DFM.1 scenario IDs are addressable in a stable immutable debug catalog, and the three ordered transition sequences reference the approved IDs without changing scenario field values.
- Each supplied value has the approved type and exact value; every intentional absence remains `null`/absent; explicit zero remains distinct from absence. Condition, precipitation type, amount, generic probability, thunderstorm probability, warning state, time, and provenance remain independent.
- Every transition step has an explicit sequence-local time that advances by one hour, and the inventory output shows it separately from the referenced scenario's approved illustrative time; no step mutates scenario values.
- A deterministic inventory report matches the complete approved matrix, exposes all fields and absent values, and labels the values `SYNTHETIC_DEBUG`.
- Focused debug catalog/projection checks pass; representative normal `SampleForecast` results remain unchanged; a release variant compiles without the debug catalog source set.
- The owner reviews and approves the completed catalog before DFM.3 UI/browser work begins. No installed or visual acceptance is claimed for this data-only slice.

Authorized future-cycle commands: `python scripts/dev.py workflow`, `./gradlew :app:tasks --all`, `python scripts/dev.py test`, `python scripts/dev.py build`, `./gradlew :app:assembleRelease`, and `git diff --check`. Run Gradle commands using an installed Gradle-supported JDK 17+; the current shell default JDK 8 is insufficient. Test additions/runs and builds are authorized only after this plan is reviewed and activated; none are run during this planning cycle. Preserve exact outputs, the generated inventory, and the release DEX inspection result (including APK path, inspection command/tool, and result) at `.codex/test-artifacts/040-dfm2-debug-forecast-catalog/`. Inspect all DEX files in the APK with the available Android SDK APK analyzer or by extracting each `classes*.dex` and searching its strings for `DebugForecastCatalog`; record which method was used. Do not run install, screenshot, UI, accessibility, RTL, large-font, reduced-motion, or device acceptance for this data-only cycle.

## Risks and assumptions

- The two approved time roles are explicit: scenario `validTime` belongs to the catalog row, while `sequenceTime` belongs to its sequence reference. Tests and inventory output must protect this separation and prevent copying sequence time into the scenario data.
- Android's `debug` source set is not currently present. The module relies on AGP's conventional source-set wiring; the direct `testDebug` import and release APK DEX scan are required evidence. If those checks fail beyond the single bounded source-set correction described above, stop and document evidence for a plan revision; do not widen into production models or UI.
- The current shell defaults to JDK 8, while Gradle 9.7 requires JDK 17 or newer. A JDK 27 task-list inspection succeeded; execution must explicitly use a supported JDK, and a host without one is a recorded blocker.
- The concept artifact's amount examples and typed field rules govern units. In particular, `SnowDepthCm` means snow depth, not snowfall accumulation; the current rows do not use it as snowfall.
- The DFM.1 note requiring an owner review after catalog implementation is an approval gate before DFM.3, not permission to add a browser or visual mapping in this cycle.

## Out of scope

No matrix browser, app entry point, selected-time integration, mapping into `ForecastView`, atmospheric mapping, visual redesign, provider selection/integration, network/location/alerts, production model changes, migration, canonical `SkyCondition` changes, modifications to `SampleForecast`, build dependency changes, broad/full-suite acceptance, install/device evidence, or DFM.3/ATM implementation. Do not infer snow, amounts, thunderstorm probabilities, or warnings from another field, neighboring time, or historical observation. Do not treat the synthetic warning placeholder as an official alert.
