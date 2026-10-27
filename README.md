# Oxygen Time Lab — Elastic Forecast Timeline

**The successor OxygenWX app, developed in stages from an installable, standalone Jetpack Compose prototype using the development scaffold and engineering conventions of the deprecated [oxygenWX application](https://github.com/davidyanceyjr/oxygenWX), pinned at commit `ec3f93976f037fbb206089d8b9d9fa1f6f93918b`.**

This repository is the successor product boundary, not a modification of the deprecated oxygenWX application's source tree. The current build remains a synthetic-data prototype that installs alongside it as `com.oxygen.weather.timeline`; it has no live provider, location selection, or real-data cache. Successor capabilities are staged through the WX roadmap. The deprecated application's production preferences, saved locations, providers, cache, and navigation are outside this repository's change boundary.

## Quick start

Extract `oxygenwx-elastic-timeline/` next to your existing Oxygen project, then:

```bash
cd oxygenwx-elastic-timeline
python3 scripts/dev.py workflow
python3 scripts/dev.py contract
python3 scripts/dev.py build
python3 scripts/dev.py run       # Android device or emulator connected through adb
```

Android Studio: **Open** the extracted `oxygenwx-elastic-timeline` folder. Allow Gradle sync, choose an emulator/device, and run `app`.

### Toolchain

Matches the referenced project's Android scaffold:

- JDK 17+; Gradle 9.7.0
- Android Gradle Plugin 9.4.0; Kotlin Compose plugin 2.4.20
- Android SDK API 37 / build tools available through the Android SDK manager
- Compose BOM `2026.09.00`, min SDK 26, target SDK 37
- Python 3.10+ and an Internet connection on the first build

**Binary wrapper note:** For portability of this source archive, `gradle-wrapper.jar` is bootstrapped *on first use* from the pinned oxygenWX commit and checked against its exact Git blob SHA-1 (`e6441136f3d4ba8a0da8d277868979cfbc8ad796`). The Gradle distribution has the upstream SHA-256 pin in `gradle/wrapper/gradle-wrapper.properties`. Once retrieved, Gradle handles its normal dependency resolution. No code in this archive requires access to your GitHub account.

## Try the interaction

1. **Tap** a short `FRI` / `SAT` / `SUN` area to jump to the corresponding day.
2. **Drag left** over the bottom strip to pull later days under the central playhead; drag right for earlier time.
3. **Hold for ~480ms** over the timeline. The time under your finger is selected, then the whole tape expands to a uniform hour-spaced scale. Slide to scrub individual forecast hours.
4. **Release** to return to daily navigation while retaining the selected hourly position.
5. The timeline exposes a screen-reader action to switch daily/hourly scales and a forecast browser for linear accessible navigation.
6. **Swipe horizontally** over the central forecast to move by one day while preserving the hour; **double-tap** the central forecast to return to the current fixture hour.

The background is a custom Compose Canvas weather scene. It changes with synthetic cloud cover, daylight, and an indicative rain texture; the texture **does not** claim rain rate from precipitation probability.

**Important data disclaimer:** All conditions are synthetic, generated deterministically for **2026-10-23 through 2026-10-30**. This app does **not** show real forecasts, live measurements, or official weather alerts. The hourly data is sampled at one-hour intervals. Between samples only presentation values are interpolated to help the scene transition smoothly.

## Interaction and architecture

```
SampleForecast (data/; metric fixture)
      |
ForecastProjection (presentation/; daily vs hourly meaning and unit conversion)
      |
TimeLabScreen (ui/; selectedHour as absolute timeline authority)
      |                         |
AtmosphericScene (Canvas)    ElasticForecastTimeline (Canvas + pointer gesture)
                                    |
                              ElasticTimeAxis (pure piecewise projection)
```

Time mapping is handled by a **monotonic piecewise function**, not stateful item indices. Precision mode uses a uniform hourly scale across the full forecast horizon. The selected hour maps to exactly the same center-playhead pixel at any expansion fraction. The interface never needs to display all 24 hourly labels simultaneously; only ticks near the playhead are drawn.

- `selectedHour` (Double) is forecast time, independent of zoom.
- The selected time remains fixed at the center playhead as the whole timeline changes scale.
- `precisionMode` activates an animated zoom; it never directly modifies time.
- Pointer input has a single gesture owner: tap, short drag, or stationary long-press followed by drag.
- A pure `ElasticTimeAxis` transforms timestamps to/from screen coordinates and clips to available forecast bounds.
- The presentation layer converts source-like Celsius and km/h to the prototype's °F and mph display.

## Developer commands

```bash
python3 scripts/dev.py test      # JUnit unit tests on Gradle
python3 scripts/dev.py lint
python3 scripts/dev.py check     # workflow + contract + test + lint + build
python3 scripts/dev.py screenshot --output .codex/test-artifacts/manual.png
```

The workflow files under `.codex/` preserve a bounded prototype plan, closure notes, and unverified installation/visual acceptance criteria, consistent with oxygenWX development discipline.

## Successor boundary and staged data path

The successor app keeps the immersive elastic timeline as its primary experience. The deprecated oxygenWX application's four-page `Now -> Hourly -> Daily -> Details` navigation is not inherited or modified. The WX roadmap stages a canonical 44-local-date catalog for live, simulated, and replay data, while retaining honest provenance, missing-data semantics, selected-time authority, accessibility, and predictable gesture ownership. Official alerts remain a separate future subsystem. No live data, location, real-data cache, or alert service is present in the current build, and no deprecated oxygenWX production files were changed.

## Known limitations

- Not built or installed in the creation environment (Android SDK, Gradle cache, and device unavailable); actual on-device visual acceptance is **not claimed**.
- Synthetic fixture in the successor app; the current build has no live network, location selection, real-data cache, alerts, preferences, or unit toggle.
- Effects are lightweight/procedural, not true 3D or physically accurate clouds.
- Large-font, TalkBack, RTL, compact viewport, device performance, and reduced-motion behavior have cycle-specific installed evidence and documented limitations under `.codex/history/`; review those records before making broader acceptance claims.
- The scene uses a fixed sample landscape and continuous decorative transitions; not a scientifically validated sky simulation.

See `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, and `.codex/history/001-elastic-timeline-prototype.md` for specific scope and evidence.
