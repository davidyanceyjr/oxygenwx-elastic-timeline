# Cycle 058-wx17-location-search-source

Date: 2026-10-28
Roadmap: WX.17

## Outcome

Implemented the provider-neutral manual place-search contract and dormant Open-Meteo geocoding adapter. Valid results preserve returned coordinate precision, IANA timezone, country and administrative context, and source metadata. Chosen candidates convert to CatalogLocation only with a caller-supplied local LocationId; the provider ID never supplies storage identity. The WX.17 exit is met. The adapter remains unwired to UI, saved-location state, and catalog activation.

## Verification actually performed

Passed: python scripts/dev.py workflow; python scripts/dev.py contract; python scripts/dev.py build; ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.openmeteo.OpenMeteoPlaceSearcherTest (9 tests); git diff --check. Provider API, attribution, and terms rechecked 2026-10-28. Evidence: .codex/test-artifacts/058-wx17-location-search-source/. No full unit suite, lint, live network, UI, installation, visual, font-scale, RTL, TalkBack, or reduced-motion checks were performed; those are outside this data-only slice.

## Follow-up

WX.17 is complete. The adapter remains dormant until a later bounded UI/controller slice calls it. WX.18 is now the sole NEXT slice; its concept artwork and plan remain prerequisites before that work begins.
