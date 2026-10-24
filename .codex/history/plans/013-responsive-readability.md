# Cycle plan — 013-responsive-readability

State: CLOSED — BLOCKED
Roadmap: P.2.6

## Bounded objective

Review installed readability of the existing standalone app on the Pixel 3 XL at its normal portrait baseline, a larger system font scale, landscape orientation, and representative bright and dark fixture times. Record screenshots and condition-specific findings for essential forecast text and timeline labels, plus bounded observations of timeline responsiveness and gesture predictability. This is evidence gathering only; confirmed defects become separate planned slices.

## Boundary

- Review only the standalone app and the Pixel 3 XL (`crosshatch`) reference device.
- Use the existing 1260×2240 active display override at 560 dpi when available; record the actual viewport and any deviation.
- Use the default portrait state, a 1.3 system font scale, landscape at the device's available active resolution, and two deterministic fixture selections: 2026-10-25 12:00 (bright daytime) and 2026-10-25 23:00 (dark/nighttime). Record exact selected timestamps as displayed and use the same times for both sky captures where reachable.
- Restore all changed system settings after capture and record original and review values.
- No app, source, styling, layout, or behavior changes are authorized.

## Functional and product invariants

- Selected forecast time remains authoritative and centered on the tape playhead; changes of font scale or orientation do not change it unexpectedly.
- The summary and timeline labels remain legible and agree with the selected time.
- Essential forecast facts remain available as text; decorative weather rendering is not a substitute for their meaning.
- Daily/hourly semantics, gesture ownership, and the timeline accessibility action remain unchanged.
- The synthetic fixture remains clearly identified as DEMO DATA.
- Evaluate all three cross-cutting visual requirements in each relevant condition: timeline responsiveness, predictable gesture ownership, and readability.

## Ordered procedure

1. Run `python scripts/dev.py workflow`. Confirm the plan remains PLANNED and P.2.6 remains the current roadmap target. Confirm Pixel 3 XL identity and record serial, Android version/API, app/build version, physical and active display size/density, locale, font scale, and animation scales. If the reference device is unavailable, record the exact result and stop without substituting another device.
2. Build, install, and launch the existing debug app on the confirmed Pixel 3 XL. Capture the default portrait baseline at the existing font scale. Record the selected time and visible content.
3. Set system font scale to 1.3, keep portrait orientation, relaunch if required, and capture the same representative screen. Inspect heading, time, temperature/condition, high/low, rain chance, wind, DEMO DATA, and daily/hourly tape labels for clipping, overlap, and legibility.
4. Keep font scale at 1.3, rotate to landscape, capture the screen, and inspect the same essential text and tape. Record actual orientation and active viewport. If rotation is unavailable or the app cannot be reviewed in landscape, record the blocker and do not claim that condition passed.
5. Return to portrait and the original font scale. Select 2026-10-25 12:00 and 23:00 from the deterministic fixture, capture each installed state, and assess text/tape contrast over the bright and dark scenes. Record the exact selected timestamp and sky state shown for each capture.
6. In baseline and at least one constrained or sky condition, perform a short immediate horizontal tape drag and a stationary hold followed by drag. Record whether the tape responds promptly, the selected time stays centered, and gesture ownership matches the expected daily/hourly behavior. This is an observational sample, not frame-timing or statistical reliability measurement.
7. Restore original device settings. Review every screenshot at full resolution, write condition-by-condition pass/fail findings, and create separately bounded roadmap/plan follow-ups for confirmed defects. Do not implement fixes in this cycle.

## Acceptance criteria

- Actual installed screenshots exist for default portrait, 1.3 font scale portrait, 1.3 font scale landscape, bright daytime, and dark/nighttime states.
- Artifact README records capture date, device/app/build identity, Android version, viewport/density, locale, original and review font/motion settings, fixture timestamps, capture procedure, and setting restoration.
- Each of the five conditions has an explicit readability disposition for essential forecast values and timeline labels, including clipping/overlap observations.
- Responsiveness and gesture predictability each receive an evidence-backed pass/fail disposition based on the observed interactions; selected-time centering/stability is recorded.
- Any confirmed defect has a separately bounded follow-up; this cycle remains evidence-only.
- Actual outcomes, exact verification commands, and limitations are recorded in `.codex/history/013-responsive-readability.md` when this cycle is later executed and closed.

## Evidence targets

- Screenshots and run details: `.codex/test-artifacts/013-responsive-readability/`
- Artifact index and procedure: `.codex/test-artifacts/013-responsive-readability/README.md`
- Closed-cycle record: `.codex/history/013-responsive-readability.md`
- Evidence paths are targets only; no evidence is claimed before execution.

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py build`
- `python scripts/dev.py run --serial <confirmed-pixel-3-xl-serial>`
- `python scripts/dev.py screenshot --serial <confirmed-pixel-3-xl-serial> --output .codex/test-artifacts/013-responsive-readability/<state>.png`
- Read-only device inspection and the manual installed review/interactions specified above.

No tests, lint, contract checks, or unrelated verification are authorized or needed for this evidence-only slice. A successful build or preview alone does not satisfy acceptance.

## Risks and assumptions

- The Pixel 3 XL used in prior records is serial `8AKY0M32X`, Android 12/API 31, and had an active 1260×2240 override at 560 dpi. Reconfirm these values at execution; if the required device is unavailable, record the blocker and do not substitute another viewport.
- System font-scale setting and landscape orientation may affect system UI as well as the app; record the actual resulting conditions and restore settings.
- Fixture conditions are synthetic and support only a visual readability review, not claims about actual weather or general contrast compliance.
- Visual inspection is bounded to this device and these states; it does not establish broad device coverage, TalkBack, RTL, or reduced-motion acceptance.

## Exclusions

- Any source, layout, styling, or behavior fix; each confirmed issue requires a separately planned implementation slice.
- TalkBack acceptance, reduced-motion acceptance, RTL acceptance, and broader device-matrix testing (tracked by P.2.7–P.2.9).
- Production oxygenWX changes, integration, external data, and unrelated visual enhancements.
