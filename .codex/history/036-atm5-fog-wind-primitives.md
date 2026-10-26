# Cycle 036-atm5-fog-wind-primitives

Date: 2026-10-25
Roadmap: ATM.5

## Outcome

INCOMPLETE. The existing working-tree preview modes provide fixed haze/turbulence drawings and an illustrative label, but they do not implement the required selectedHour-keyed profile, six specified sample offsets, linear interpolation for fractional hours, or endpoint clamping. ATM.5 is not done. Existing image files are retained, but without capture metadata or observations they are not claimed as installed acceptance evidence. The missing profile is assigned to planned cycle 037.

## Verification actually performed

PASS: python scripts/dev.py workflow (Workflow scaffold: OK). PASS: git diff --check. Source/diff inspection confirmed the missing profile. adb devices -l listed Pixel 3 XL serial 8AKY0M32X; this closeout did not install, launch, or review the app. Focused tests, contract, build, lint, and installed acceptance were not run. Evidence inventory: .codex/test-artifacts/036-atm5-fog-wind-primitives/README.md; pre-existing image files are unverified and are not acceptance evidence.

## Follow-up

State remaining gaps before declaring full acceptance.
