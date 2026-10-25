# Cycle 024-atm1-scene-projection

Date: 2026-10-24
Roadmap: ATM.1

## Outcome

ATM.1 implemented: added pure immutable atmosphere input and scene state mapping with normalized optional values and condition-based precipitation semantics; routed the existing screen through a typed adapter that forwards the original SkyFrame and localHour to the unchanged renderer. Focused tests and build passed. The bounded exit is met; no installed visual acceptance is claimed.

## Verification actually performed

Passed: focused SceneVisualStateMapperTest via :app:testDebugUnitTest; python scripts/dev.py build; python scripts/dev.py contract; python scripts/dev.py workflow; git diff --check. The successful Gradle commands used JAVA_HOME=/usr/lib/jvm/java-27-openjdk and ANDROID_HOME/ANDROID_SDK_ROOT=/home/opsman/Android/Sdk. Initial attempts failed first on the default Java 8 runtime, then on missing ANDROID_HOME; both passed after configuring the host JDK/SDK. Evidence: .codex/test-artifacts/024-atm1-scene-projection/README.md. No screenshot or installed visual/device/accessibility/font/RTL/system reduced-motion check was required or performed. Pure reducedMotion mapping was unit tested. Canvas source and visible layout were unchanged; existing legacy rain-hint probability opacity remains behind the adapter.

## Follow-up

No ATM.1 implementation follow-up remains. No installed visual acceptance is
claimed or required for this compatibility seam; later visual slices must run
their own installed acceptance matrix. ATM.2 is the next eligible roadmap
slice.
