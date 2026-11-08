# WX.30.1 execution evidence

- `python scripts/dev.py workflow` before activation: passed.
- `python scripts/dev.py build`: passed (debug APK, 37 tasks; 5 executed).
- Focused Gradle command:
  `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinatorTest.transientViewAndRefreshNeverWriteAndRestartRestoresPersistedChoice' --tests 'com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinatorTest.failedAndSupersededTransientViewsRetainPreviousPublication' --tests 'com.oxygen.weather.timeline.ui.LocationSelectorControllerTest.resultViewUsesCanonicalIdentityWithoutStoreCalls' --tests 'com.oxygen.weather.timeline.ui.LocationSelectorControllerTest.searchResultIsSavedThenSelectedAndClosesOnlyAfterCommit'`
- First focused run: 3 passed, 1 failed because the fixture used an invalid canonical place ID. Corrected the test fixture to `CanonicalPlaceId.geonames(1)`.
- Final focused run: passed, 4 tests.
- `git diff --check`: passed.
- Source-only transient View and refresh avoid the writing LIVE repository and dataset storage. Tests cover no dataset reads/writes, canonical controller handoff with no saved-location store calls, durable result selection, prior selection retention on failed/mismatched/superseded requests, restart restoration, and committed switch away.
- Limits: No installed UI state, device check, or app-wide accessibility/system matrix; visible View control belongs to WX.30.2. Activity callback is exposed but not yet connected to visible controls.
