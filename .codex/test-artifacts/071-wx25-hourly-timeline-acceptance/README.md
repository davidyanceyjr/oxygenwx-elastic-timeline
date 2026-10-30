# Cycle 071 evidence — hourly timeline acceptance

This directory records the still-active WX.25 installed acceptance. The owner-approved SIMULATED value-completeness amendment was implemented as part of this cycle: every hourly SIMULATED fact now carries precipitation chance and sustained wind speed, with explicit zero for previously missing values. Existing non-null anchor values are preserved. UI behavior for displaying zero remains a future roadmap item per the owner.

## Required-value amendment verification

- `python scripts/dev.py workflow` — PASS.
- `python scripts/dev.py contract` — PASS.
- `python scripts/dev.py build` — PASS (`BUILD SUCCESSFUL`).
- `python scripts/dev.py run --serial 8AKY0M32X` — PASS; debug APK installed and launched on Pixel 3 XL.
- Read-only installed database audit — PASS; details in `data-completeness-v3.txt`. SIMULATED v3 and derived REPLAY v3 each contain 1,057 hourly facts across 44 local dates. Chance and sustained-wind null counts are both zero; all 44 dates declare point support for both fields at each daily hourly instant. Existing non-null chance/wind anchor values at matching Instants are unchanged.
- Installed picker identity is captured in `data-v3-installed-picker.png` and `.xml`; it shows Chicago and active `wx22-debug-simulated-v3`.
- Installed SIMULATED main screen at font scale 1.3 is captured in `main-font-1.3.png` and `.xml`; it shows populated chance and wind facts, including explicit `0 mph`.
- `git diff --check` — PASS.
- Automated tests — NOT RUN, per repository instructions.

## Remaining WX.25 acceptance

The owner confirmed TalkBack verification and approved it for this slice; the exact spoken transcript was not provided and is not fabricated. See `talkback-check-status.txt`. Font scale and accessibility were restored to 1.0 and disabled after the large-font capture and TalkBack probe. This cycle remains open while the remaining scrub/browser acceptance is completed.
