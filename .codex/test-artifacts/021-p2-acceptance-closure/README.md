# Cycle 021 — P.2.9 RTL review and acceptance closure

- Cycle ID: `021-p2-acceptance-closure`.
- Slice ID: `P.2.9`.

## Device and configuration

- Device: Pixel 3 XL (`crosshatch`), Android 12 / API 31.
- Display: portrait, 1260 × 2240 at 560 dpi; system font scale 1.0 (Default).
- App: installed `com.oxygen.weather.timeline`, synthetic DEMO DATA fixture.
- Original system locale: `en-US`.
- RTL locale used: Android system locale `ar-XB` (Arabic, Pseudo-Bidi); while active, `system_locales=ar-XB,en-US` and `persist.sys.locale=ar-XB`.
- Original and restored motion scales: `window_animation_scale=1`, `transition_animation_scale=1`, `animator_duration_scale=1`.
- TalkBack was not enabled or tested in this cycle, per product-owner direction. Initial/restored values remained `enabled_accessibility_services=null` and `accessibility_enabled=0`.

## Installed RTL observations

Actual captures in this directory show the English baseline and the installed app under `ar-XB`.

- Android system UI mirrored under `ar-XB`; the app's forecast content and timeline remained laid out left-to-right, visually matching the English baseline. Timeline dates progressed left-to-right (Friday, Saturday, Sunday, Monday, Tuesday, Wednesday), and the selected date remained explicit in the central summary.
- Tapping Sunday selected Sunday, Oct 25. A leftward immediate drag advanced to Monday, Oct 26; a rightward drag returned to Sunday, Oct 25. The same physical direction mapping as the English timeline was observed: left advances to later dates and right moves to earlier dates.
- A 650 ms stationary hold followed by a 200 px rightward scrub entered hourly mode and showed Sunday, Oct 25, 4 AM. Release collapsed to daily overview and retained Sunday. At the later fixture endpoint, stationary re-entry showed Friday, Oct 30, 12 PM.
- Overdrags at both fixture ends remained clamped: the earlier end stayed at Friday, Oct 23, and the later end stayed at Friday, Oct 30. No dates outside the displayed fixture range were observed.
- Main-screen forecast values, DEMO DATA, selected date/hour, and timeline labels remained visible and legible in the captured portrait/default-font states.

### RTL disposition requiring owner review

The Android system UI was RTL, but the app body and time tape did not mirror. The dates and selected forecast stayed understandable in the exercised interactions, with the same LTR timeline direction mapping as the English baseline. The product owner monitored the review live and accepted the left-to-right app layout and time axis for this prototype; no RTL implementation follow-up is required.

The new RTL sequence observed the selected Sunday 4 AM in hourly mode, then the Sunday daily overview after release. It did not immediately re-enter hourly mode on that same selected date before scrubbing to the fixture endpoints. Exact hourly time continuity for that RTL-specific collapse/re-entry sequence is therefore not independently established here; the earlier P.2.5 and P.2.8 records contain the existing continuity evidence.

## TalkBack disposition

P.2.7 and P.2.13 remain accepted based on their existing evidence: `.codex/history/019-talkback-scale-action.md`, `.codex/test-artifacts/019-talkback-scale-action/`, `.codex/history/018-talkback-forecast-browser.md`, and `.codex/test-artifacts/018-talkback-forecast-browser/`. The product owner states TalkBack works, does not want further TalkBack tests in this cycle, and will revisit testing before final production release. No RTL-specific spoken interaction was tested or is claimed here.

## Cross-cutting visual-quality dispositions

- **Responsiveness:** Pass, qualitative for the exercised adb-injected interactions; visible selected-date/hour changes followed the inputs without an obvious pause. This is not a frame-time or direct finger-feel measurement.
- **Gesture predictability:** Pass for the exercised tap, immediate left/right drag, stationary hold-then-drag, release/collapse, and re-entry sequence. Left advanced time and right moved earlier, consistently with the visible LTR chronology.
- **Readability:** Pass for the captured portrait/default-font states. Forecast values, selected date/hour, scale labels, and DEMO DATA remained visible and legible.

## Existing P.2 evidence reconciliation

- **P.2.5 gestures/time stability:** DONE — `.codex/history/012-gesture-acceptance-recheck.md` and `.codex/test-artifacts/012-gesture-acceptance-recheck/`.
- **P.2.6 constrained/large-font readability:** DONE — `.codex/history/015-responsive-readability-review.md` and `.codex/test-artifacts/015-responsive-readability-review/`.
- **P.2.7 TalkBack scale action:** DONE — `.codex/history/019-talkback-scale-action.md` and `.codex/test-artifacts/019-talkback-scale-action/`; exact speech was not fully transcribed, as recorded there.
- **P.2.8 reduced motion:** DONE — `.codex/history/020-reduced-motion.md` and `.codex/test-artifacts/020-reduced-motion/`; installed evidence is Pixel 3 XL portrait/default font, with no frame-time measurement.
- **P.2.11 landscape high/low readability:** DONE — `.codex/history/014-landscape-high-low.md` and `.codex/test-artifacts/014-landscape-high-low/`.
- **P.2.13 screen-reader forecast browser:** DONE — `.codex/history/018-talkback-forecast-browser.md` and `.codex/test-artifacts/018-talkback-forecast-browser/`; RTL-specific browser semantics were not retested here.

## Verification actually performed

- `python scripts/dev.py workflow` — passed before activation and after activation.
- `adb devices -l` — confirmed Pixel 3 XL serial `8AKY0M32X`.
- `python scripts/dev.py run --serial 8AKY0M32X` using the default shell Java 8 — failed because Gradle requires JVM 17 or later.
- The same run command with `JAVA_HOME=/usr/lib/jvm/java-27-openjdk`, `PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH`, `ANDROID_HOME=/home/opsman/Android/Sdk`, and `ANDROID_SDK_ROOT=/home/opsman/Android/Sdk` — `BUILD SUCCESSFUL`, streamed install succeeded, and `MainActivity` launched.
- Installed RTL screenshots and direct-touch interactions were captured and manually reviewed. Interactions used `adb shell input tap`, `input swipe`, and `input motionevent`; no TalkBack service was enabled.
- Original system locale and settings were restored and read back: `system_locales=en-US`, `persist.sys.locale=en-US`, `font_scale=1.0`, all animation scales `1`, `enabled_accessibility_services=null`, `accessibility_enabled=0`, portrait (`SurfaceOrientation: 0`).
- The app was relaunched under the restored English locale; `english-restored-daily.png` captures the final device state.
- No tests, lint, contract check, or TalkBack test was run in this cycle.

## Capture index

- Baseline and locale setup/restoration: `baseline-en-daily.png`, `rtl-locale-added.png`, `rtl-locale-active.png`, `rtl-primary-check.png`, `locale-restored-verified.png`, `english-restored-daily.png`.
- RTL overview and tap/drag: `rtl-daily-relaunch.png`, `rtl-tap-position.png`, `rtl-drag-left.png`, `rtl-drag-right.png`.
- RTL hourly scale and collapse: `rtl-hourly-active.png`, `rtl-collapse-after-hourly.png`, `rtl-hourly-reentry-at-end.png`.
- Fixture bounds: `rtl-start-bound-recheck.png`, `rtl-drag-right-at-left-bound.png`, `rtl-end-bound.png`, `rtl-end-bound-recheck.png`, `rtl-end-bound-after-overdrag.png`.

Other locale-settings captures document the Android settings interaction used to add and remove the pseudolocale; they are not app acceptance captures.
