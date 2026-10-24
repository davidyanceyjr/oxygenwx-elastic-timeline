# Oxygen Time Lab — Elastic Forecast Timeline

**An installable, standalone Jetpack Compose UI experiment based on the development scaffold and engineering conventions of [oxygenWX](https://github.com/davidyanceyjr/oxygenWX), pinned at commit `ec3f93976f037fbb206089d8b9d9fa1f6f93918b`.**

This is intentionally **not** a full copy of the production weather app. It installs alongside oxygenWX as `com.oxygen.weather.timeline`, uses no location/network permissions, and cannot change production preferences, saved locations, providers, cache, or navigation.

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
3. **Hold for ~480ms** over a day on the timeline. That day expands in-place into an hour-spaced scale; adjacent days remain compressed. Slide to scrub individual forecast hours.
4. **Release** and the selected hour remains the same moment while the tape contracts to daily resolution. The forecast changes accordingly.
5. The subtle **HOURS / DAYS** action gives a discoverable alternative to the hold gesture, especially for accessibility testing.
6. **RESET** goes back to the first demo day.

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

Time mapping is handled by a **monotonic piecewise function**, not stateful item indices. The focused day has `16dp/hour` when fully expanded; neighbors stay at `55dp/day`. The selected hour maps to exactly the same center-playhead pixel at any expansion fraction. The interface never needs to display all 24 hourly labels simultaneously; only ticks near the playhead are drawn.

- `selectedHour` (Double) is forecast time, independent of zoom.
- `focusDay` chooses which section expands.
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

## Production integration decision (not included)

oxygenWX currently requires the four-page `Now -> Hourly -> Daily -> Details` Home pager to be the sole horizontal swipe owner. This prototype is an **explicit experimental exception in a separate application**, so it must not simply be pasted into the production Home pager. Integrating it requires an owner-approved presentation/navigation change and clear gesture arbitration with the outer pager. No production source files were changed.

## Known limitations

- Not built or installed in the creation environment (Android SDK, Gradle cache, and device unavailable); actual on-device visual acceptance is **not claimed**.
- Fixture rather than production repository/cache; no location, network, alerts, preferences, or unit toggle.
- Effects are lightweight/procedural, not true 3D or physically accurate clouds.
- Large-font, TalkBack, RTL, compact viewport, device performance, and reduced-motion behavior still require installed review. The HOURS/DAYS control exists as a fallback, but full accessibility acceptance is outstanding.
- The scene uses a fixed sample landscape and continuous decorative transitions; not a scientifically validated sky simulation.

See `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, and `.codex/history/001-elastic-timeline-prototype.md` for specific scope and evidence.
