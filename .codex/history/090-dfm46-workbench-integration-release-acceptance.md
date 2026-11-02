# Cycle 090-dfm46-workbench-integration-release-acceptance

Date: 2026-11-02
Roadmap: DFM.4.6

## Outcome

Completed DFM.4.6 integrated debug-workbench and isolated release runtime acceptance. All approved DFM.4.6 acceptance criteria are evidenced; no in-scope app-source defect was reproduced and no app source was changed. The product owner directed retaining the test-created Madison saved location and catalog for debugging; Chicago is the active debug location.

## Verification actually performed

python3 scripts/dev.py workflow: PASS before activation, before close, and post-close (`State: IDLE`); python3 scripts/dev.py contract: PASS after restoring the standard release manifest and post-close (one intermediate final attempt failed because the temporary .dfm46test application ID remained in generated merged-manifest state); python3 scripts/dev.py build: PASS; python3 scripts/dev.py run --serial 8AKY0M32X: PASS; ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinatorTest --tests com.oxygen.weather.timeline.data.debug.DebugAuthoredCatalogTest --tests com.oxygen.weather.timeline.ui.LocationSelectorControllerTest --tests com.oxygen.weather.timeline.data.debug.DiagnosticPresetCodecTest: PASS, 42 tests; ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.catalog.CatalogDebugLifecycleTest: PASS, 13 tests; ./gradlew :app:assembleRelease: PASS on initial and final standard release builds; locally signed test-suffixed release APK installed by streamed install after incremental install was disallowed; Pixel debug/release installed captures and ordinary-action/timeline checks: PASS under .codex/test-artifacts/090-dfm46-workbench-integration-release-acceptance/README.md; release manifest, DEX, source registration, and README manual inspection: PASS; git diff --check: PASS after closeout updates. Broad RTL, TalkBack, reduced-motion, and system/environment matrices were not run under the approved integration scope.

## Follow-up

No DFM.4.6 acceptance work remains. The retained Madison saved location and catalog were created during the ordinary-selection integration check at the product owner's direction for debugging; Chicago is active. Broad RTL, TalkBack, reduced-motion, and cross-device/system/environment acceptance remain assigned to dedicated TEST work. Production signing and release-readiness acceptance are outside DFM.4.6.
