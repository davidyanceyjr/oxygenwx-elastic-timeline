# Cycle plan — 073-wx27-release-boundary

State: PLANNED
Cycle ID: `073-wx27-release-boundary`
Roadmap: WX.27
Objective: Adopt approved OxygenWX release labels, align source/privacy/permission disclosures and debug-release packaging with shipped capabilities, and record the application identity decision without changing application ID or signing.

Owner-directed reprioritization on 2026-10-30: WX.27 is DEFERRED until the product is finished and polished and release-readiness work is appropriate. This cycle was not activated; release APK build/install work is premature. Retain this draft as reference for re-entry and review its scope and evidence requirements against the then-current product.

## Readiness and authority

- At initial planning, `.codex/current.md` was IDLE and `python scripts/dev.py workflow` reported `Workflow scaffold: OK`. WX.26 is DONE in cycle 072; WX.1 is DONE; WX.27 was then the sole NEXT eligible slice and WX.28 its follow-up. Both WX.27 dependencies were complete. The owner later deferred WX.27/WX.28 and made SG.0 NEXT; this draft is parked and is not ready for activation.
- `docs/SPECIFICATION.md` controls prototype semantics and requires the experimental `OXYGEN / TIME LAB` title and `DEMO DATA` treatment to be removed only when backed by accurate production provenance. `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` requires source/freshness information to remain accessible without obscuring the atmospheric scene, and keeps the timeline primary. Do not claim complete product acceptance.
- Owner clarification on 2026-10-30: Version One will be free and provide basic features. A more fully featured paid Pro version is a later phase; comparison/testing of commercial weather providers and their licenses belongs to planning for that phase. Open-sourcing the application is under consideration, not yet a decision. Ads, Version One promotion of Pro, an open-source license, and publication timing remain unspecified. Treat code licensing/open publication separately from upstream API/data terms.
- The owner approved the combined behavior/UI board on 2026-10-30: `.codex/concepts/WX.27-release-identity.svg` and `.codex/concepts/WX.27-release-identity.png`. It covers candidate OxygenWX identity placement, LIVE/CACHED/UNAVAILABLE source states, provider/freshness copy, privacy/permission and attribution review, debug/release separation, and the app-ID/signing decision flow. It does not decide final provider/legal wording, privacy-policy publication, the coexist/replace outcome, commercial API entitlement, or authorize any app-ID/signing migration.
- Board constraints governing this plan: preserve the immersive timeline and readable text facts; keep provider and freshness context truthful and compact; distinguish cached/offline and failed-refresh states; keep synthetic sources and the picker debug-only; do not claim official alerts; retain the existing separate successor package identity unless an explicit owner decision authorizes a future migration. The board is design input, not installed acceptance evidence.
- Deferred `P.4.1` and `P.4.2` are scoped to the deprecated oxygenWX app; this cycle handles only successor branding/provenance and does not close or modify those legacy tasks.
- Owner direction on 2026-10-30: defer licensing and related provider-eligibility determinations until OxygenWX is a finished, working, polished product. WX.27 will record truthful source/provenance attribution and data-modification facts for the sources currently implemented, but will not decide commercial eligibility, obtain provider licenses, or claim distribution clearance. The paid Pro provider and license evaluation remains future work after product polish. Open-Meteo's terms pages were consulted during planning but are not a WX.27 approval or release gate.

## Verified baseline

- WX.26 cycle 072 wired normal startup to the selected saved location and LIVE catalog, with cached/offline restore and foreground refresh. Its evidence is `.codex/history/072-wx26-production-startup.md` and `.codex/test-artifacts/072-wx26-production-startup/README.md`.
- The app's manifest label is `Oxygen · Time Lab`; `MainActivity` also renders `OXYGEN / TIME LAB`. `CatalogTimeLabScreen` currently displays selected place, product meaning, source, and a status string that can include origin, delivery, freshness, storage time, issue time, and refresh state. Keep only accurate, supported values and do not add persistent chrome that competes with forecast facts or the timeline.
- `app/build.gradle.kts` sets both namespace and application ID to `com.oxygen.weather.timeline`; its comment explicitly says the separate ID supports side-by-side installation with the deprecated oxygenWX app. No release signing configuration is present. WX.1 names this repository as the successor app and keeps the deprecated app outside this workstream. Use coexistence under the existing ID as the documented baseline, record the owner-selected disposition explicitly, and make no ID/key migration.
- `app/src/main/AndroidManifest.xml` currently requests only `INTERNET`, disables backup, and supports RTL. Manual geocoding uses Open-Meteo; weather requests send selected coordinates. Saved places and catalog data are stored locally in Room. No GPS/background-location permission is present. Confirm these details against the live tree before writing privacy or permission statements.
- Debug picker activities and `CatalogPickerDelegate` are in `app/src/debug`, but `MainActivity`'s main source directly references `SampleForecast` preview paths and registers simulated/recorded catalog sources; `SampleForecast`, `ForecastSource`, and `FixtureCatalogAdapters` are in `app/src/main`. A release DEX/source-set inspection has not been performed. Isolate executable synthetic fixtures, debug preview entry paths, and simulated/replay source registration from the release artifact while preserving their debug behavior. Generic canonical origin/provenance types may remain in main if no synthetic fixture content or debug entry point is packaged.
- The Pixel 3 XL reference device `8AKY0M32X` was attached during planning. `$DISPLAY` was unset in this shell. `python scripts/dev.py` only builds/installs the debug variant; no `apksigner` or `zipalign` executable was found on `PATH` during preflight. Recheck device, display, Android SDK build-tools, and a local test-signing method at activation. Any release-variant test signature must be clearly identified as non-production; do not add or expose production signing credentials.

## Scope and production boundary

Within WX.27:

- Replace user-visible experimental Time Lab branding with the owner-approved OxygenWX text naming in the app label and main catalog experience. Keep `com.oxygen.weather.timeline` unchanged; record the coexistence disposition and current signing state. A replacement migration or new signing key is not authorized.
- Align visible origin, product, provider attribution, modification notice, delivery, freshness, and update-time copy with the actual selected catalog and sources currently implemented. Keep missing provenance unavailable; never relabel a cached value as a fresh response or archived model data as station observation. Record source/licensing questions for the later polished-product review without deciding them here.
- Inventory data sent and retained by the shipped app, check actual requested permissions and exported components, and record concise, factual privacy statements for manual search, coordinate-based weather requests, and local saved-location/catalog storage. Do not invent a policy or claim distribution/legal clearance; defer broader legal and provider-license decisions until the product is finished and polished.
- Do not compare, select, integrate, or purchase commercial weather providers for the later paid Pro phase in WX.27; record that evaluation as future work after product polish.
- Ensure the release APK contains no debug picker activities, synthetic fixture/profile content, debug source entry points, or simulated/replay source implementations. Keep the existing debug picker and established synthetic scenarios available in the debug variant.
- Audit release manifest/package contents, attribution obligations, source references, credentials/secrets exposure, and basic Android component/network/backup boundaries. This is a focused release-boundary review, not a penetration test or broad security certification.

Allowed change areas, confirmed again at activation:

- `app/src/main/AndroidManifest.xml`, `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt`, `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`, and the smallest presentation strings/state mapping required for approved labels.
- Existing variant boundary files and fixture paths, including `ActivityVariantDelegate.kt`, `app/src/debug/`, `app/src/main/java/com/oxygen/weather/timeline/data/SampleForecast.kt`, `app/src/main/java/com/oxygen/weather/timeline/presentation/ForecastSource.kt`, and `app/src/main/java/com/oxygen/weather/timeline/data/catalog/FixtureCatalogAdapters.kt`, only as needed to isolate synthetic/debug code from release.
- `app/build.gradle.kts` only for non-identity build-variant packaging changes; no application ID, namespace, version/signing identity, or production signing configuration changes.
- `.codex/test-artifacts/073-wx27-release-boundary/README.md` and captures/logs; update `docs/ROADMAP.md` only at closeout when the roadmap exit is supported by evidence.

Do not modify the deprecated oxygenWX checkout or introduce unrelated dependencies, navigation, or timeline redesign.

## Functional invariants

- Release app naming is OxygenWX; data claims match the active catalog's origin, product semantics, provider, delivery, freshness, and retrieval/issue times.
- Missing facts remain unavailable. Synthetic data never appears as LIVE or as a production forecast. `DEMO DATA` remains truthful wherever the debug fixture experience remains visible; it must not mislabel production catalog data.
- Manual place search remains the location path. Do not add GPS, background tracking, or permissions unsupported by shipped capabilities.
- No official-weather-alert or warning feature/claim is added. The debug-only `STORM_WARNING_PREVIEW` is not an official warning.
- Release and debug variants are distinguishable by actual packaged contents, not only hidden UI. The debug source picker and supported profiles remain usable after source-set isolation.
- Preserve selected-time authority, timeline responsiveness, gesture ownership, readability, accessible forecast facts, RTL meaning, and reduced-motion meaning. Source/privacy presentation must not obscure the atmosphere or timeline.
- Current application ID and signing identity remain unchanged. Record the owner-selected application identity/coexistence disposition. Record licensing and provider-eligibility review as explicitly deferred until the product is finished and polished; make no claim of final distribution clearance in WX.27.

## Visual objective and installed conditions

- **Visual objective:** replace the experimental title with restrained OxygenWX naming and make provider/source/freshness context understandable at the approved storyboard's placements without adding a dashboard, permanent control row, or competing status banner.
- **Functional invariants reviewed visually:** selected place and displayed facts refer to the same catalog; origin/provider and cache/freshness labels agree with the underlying state; debug fixtures remain visibly synthetic; release contains no synthetic controls; important forecast facts and timeline labels remain readable.
- Review installed reference states: release LIVE catalog; release matching cached/offline catalog; release no-data or failed-refresh state if reachable; debug SIMULATED picker and profile; debug LIVE/REPLAY source identity where the existing path permits.
- Check the Pixel 3 XL portrait at default and 1.3× font scale, plus one compact viewport if available. Check RTL ordering/meaning and reduced-motion behavior/disposition. Keep timeline responsiveness, gesture ownership, and readability in scope; the branding/source adjustment must not change timeline input or selected time.
- Capture actual installed screenshots for release and debug states. Record states that cannot be reached as NOT RUN with the reason. A concept board, APK build, source scan, or preview does not substitute for installed evidence.

## Ordered steps

1. At activation, rerun `python scripts/dev.py workflow`; confirm this plan and approved board; inspect the full working tree/diff; recheck the latest specifications, WX.26 closeout, actual source/provenance implementation, attached device, display, Android build-tools, and release test-signing options. Preserve unrelated working-tree changes; licensing review remains deferred.
2. Capture and record the current installed debug and release-variant baselines where possible. Inventory user-visible labels/status fields; API calls and attribution; permissions, exported activities, network/backup settings; local data retained; application ID/signing configuration; and runtime/source-set references to synthetic data and debug controls.
3. Confirm exact product-facing copy and source/data attribution against current provenance and implemented behavior. Record the owner's instruction to defer provider licensing/eligibility until the product is finished and polished, and record the application identity/coexistence disposition using current `com.oxygen.weather.timeline`. Do not change app ID or signing or claim distribution clearance.
4. Implement only the approved labels and truthful, compact source/freshness/privacy presentation needed by actual data states. Keep provider attribution and any modifications discoverable; retain explicit cached, stale, updating, unavailable, and archived-product distinctions where data supports them. Do not invent unsupported model credit or weather facts.
5. Isolate debug-only fixture previews, synthetic profiles, simulated/replay source registration and picker entry points from the release variant. Preserve the debug picker and all established synthetic IDs/scenarios. Keep generic canonical schema/provenance types in main where required by LIVE runtime.
6. Build debug and release variants. Inspect merged manifests and every release DEX for debug activities/entry points, `SampleForecast` or other fixture/profile IDs/content, and simulated/replay implementation classes. Inspect permissions, exported components, backup/cleartext/network settings, app label, application ID, signature, and embedded credentials. Any exception must be traced to an explicit generic/runtime necessity and must not expose synthetic data or controls.
7. Install the release variant on the reference Pixel using a clearly identified non-production test signature if available without changing committed signing configuration. Install debug separately and verify the established debug flow. Review LIVE, cached/offline, and error/source states; capture screenshots and verify text, responsive timeline, gesture behavior, large-font, RTL, and reduced-motion conditions. Restore device settings and prior app state after review.
8. Record each release scenario as PASS/FAIL/NOT RUN with exact build, APK, signature, device, commands, captures, permissions/package scans, observed attribution/provenance, explicit licensing deferral, app-ID decision, and limitations. Run `git diff --check`, inspect the complete diff, then close only if every WX.27 exit is evidenced. If a required release install or package boundary cannot be verified, record the blocker and leave WX.27 incomplete.

## Acceptance and evidence

WX.27 is complete only when all roadmap exit conditions are evidenced:

- Installed release app presents owner-approved OxygenWX naming and source/provider/freshness context accurately for the active catalog; visible attribution and modification statements reflect the currently implemented source and data path. This is not a commercial-eligibility or distribution-license decision.
- Actual release manifest/DEX/package evidence shows no debug picker entry point or synthetic fixture/profile data. Debug build retains the intended picker and scenario functionality.
- Permissions, exported components, local data handling, factual privacy statements, source attribution/provenance and relevant package-security boundaries are recorded against observed implementation. Provider and commercial licensing eligibility is explicitly deferred until the product is finished and polished.
- The app-ID/signing/coexistence disposition is explicit and consistent with the owner-approved successor direction. No application ID, namespace, or signing migration occurred.
- No unsupported alert/warning claim or misleading production `DEMO DATA` label appears. Installed review preserves the primary timeline and readable forecast facts.

Evidence target paths:

- `.codex/test-artifacts/073-wx27-release-boundary/README.md`
- `.codex/test-artifacts/073-wx27-release-boundary/release-live.png`
- `.codex/test-artifacts/073-wx27-release-boundary/release-cached-offline.png`
- `.codex/test-artifacts/073-wx27-release-boundary/debug-picker.png`
- `.codex/history/073-wx27-release-boundary.md`
- `.codex/history/plans/073-wx27-release-boundary.md` after cycle close

The evidence README records the exact actual capture names, variant/APK hashes, application ID, test-signature identity, device/build details, build and scan commands, observed source attribution/provenance, the owner's licensing deferral, other owner decisions, PASS/FAIL/NOT RUN matrix, and limitations. Planned paths are not claims that evidence exists.

## Planned verification (not run during planning)

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build` (debug)
- `./gradlew :app:assembleRelease`
- `python scripts/dev.py run --serial 8AKY0M32X` and `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/073-wx27-release-boundary/<state>.png` after rechecking the device.
- Release-variant install and screenshot using a local non-production verification signature only if the existing SDK/test-key path supports this without changing repository signing configuration; otherwise record installed release acceptance NOT RUN and do not mark the release exit met.
- Inspect merged release manifest and all DEX entries/content; record exact commands, artifact hashes and results. Use no unreviewed production key or credential.
- `git diff --check`
- Do not add or run automated tests unless the owner separately authorizes them. No automated test suite is part of this plan.

## Risks and unresolved decisions

- **Licensing deferral:** The owner directs licensing and related provider-eligibility determinations to the point when the product is finished and polished. Do not treat API commercial classification or provider license selection as a WX.27 blocker or claim they are resolved. Preserve factual attribution/provenance in this slice and record the later review as an explicit product-finalization gate.
- **Later Pro phase:** Version One is free with basic features; paid Pro and comparative commercial-provider/license testing are future work after product polish. Open-sourcing remains under consideration. Do not start those evaluations or select an open-source code license in this cycle.
- **Open-source consideration:** If the owner chooses to publish the app source, record the desired code license and check that credentials, local artifacts, and user data are not included. That decision does not itself determine API usage rights or replace factual weather-data attribution/modification notices; licensing decisions remain deferred until the product is finished and polished. Selecting/publishing a license is outside this cycle.
- **Attribution:** Weather model/data credit can vary by selected product. Use source/provenance facts supplied by the currently implemented path and accurately describe modifications; record license-specific questions for the deferred polished-product review. Do not infer a model source that the catalog does not provide.
- **Privacy:** Search terms and selected coordinates are sent to provider endpoints; selected locations and catalogs are stored locally. Check actual network/storage behavior and any distribution-store privacy requirements before describing collection, retention, sharing, or deletion. Do not claim “no data leaves the device.”
- **Variant isolation:** Main-source `SampleForecast` references and simulated/replay registration may pull fixture data into release even though picker activities live in `src/debug`. Use actual release DEX evidence; preserve the debug experience while isolating only the synthetic/runtime paths.
- **Release installation/signature:** No release signing config or Android signing tools were available on PATH at planning time. Recheck installed SDK tools at activation. A test-signed release variant is not a production-signed artifact; keep that limitation explicit.
- **Brand scope:** The board approves OxygenWX identity placement and review flow, not a new logo, complete visual identity system, legal copy, privacy policy publication, package replacement, or signing-key transition. Obtain decisions explicitly if required for the exit.

## Out of scope

- Changing `applicationId`, namespace, signing keys/configuration, package migration, publication, store submission, or distribution credentials.
- Determining commercial eligibility, obtaining provider/API licenses, comparing/selecting commercial providers, testing commercial licenses, or implementing paid Pro features; defer these until the product is finished and polished and plan the Pro phase separately.
- Modifying the deprecated oxygenWX application, copying its navigation, or replacing the successor's immersive timeline.
- Adding GPS/background location, official alerts, analytics/accounts, broad security certification, new weather capabilities, or a full privacy-policy website.
- Broad UI redesign, a permanent control/status row, source-picker changes beyond preserving debug-only isolation, and unrelated dependency/build modernization.
- WX.28 application-wide integration/release acceptance, full accessibility/performance suites, or work assigned to deferred P.4 slices.
- Automated test creation or execution without separate owner authorization.
