# Cycle 061-wx20-catalog-activation

Date: 2026-10-28
Roadmap: WX.20

## Outcome

Implemented WX.20 catalog activation. MainActivity now owns a lifecycle-scoped coordinator that restores the saved location, activates LIVE forecast-44d on startup and committed location changes, keeps one typed active-selection state, and rejects superseded requests. LIVE uses LiveCatalogRepository; SIMULATED and REPLAY use isolated storage namespaces and source loading. REPLAY storage preserves captured LIVE/SIMULATED provenance while the active in-memory catalog carries REPLAY provenance. Selected instants are retained or moved to the nearest valid sample with earlier tie-breaking; failures preserve the prior active selection. Renderer migration remains out of scope.

## Verification actually performed

Passed: python scripts/dev.py workflow; python scripts/dev.py contract; ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinatorTest --tests com.oxygen.weather.timeline.data.catalog.LiveCatalogRepositoryTest --tests com.oxygen.weather.timeline.data.catalog.CatalogDatasetStorageTest --tests com.oxygen.weather.timeline.data.catalog.WeatherCatalogSourceTest --tests com.oxygen.weather.timeline.data.catalog.SavedLocationContractTest --tests com.oxygen.weather.timeline.ui.LocationSelectorControllerTest (31 tests, 0 failures); python scripts/dev.py build; git diff --check. Console logs and six JUnit XML reports are preserved under .codex/test-artifacts/061-wx20-catalog-activation/. No installed visual/device checks were required or claimed for this data/state slice.

## Follow-up

WX.22 is the next slice and owns debug-only dataset selection/replay controls. WX.20 deliberately does not connect active catalogs to `SampleForecast` or alter the renderer; WX.25/WX.26 own those later integration outcomes. The app currently registers an empty `RecordedCatalogSource` until an explicit recording is selected through a future debug flow. No UI/device acceptance was required for this data/state cycle.
