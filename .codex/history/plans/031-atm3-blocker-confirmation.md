# Cycle plan — 031-atm3-blocker-confirmation

State: CLOSED
Roadmap: ATM.3a
Objective: Reuse completed ATM.3 evidence, capture the missing Monday Oct 26 7:30 PM cloud-phase sample and the owner-authorized replacement 8 PM sample, order the remaining corrective work, and close this evidence cycle without changing application source.

## Owner-directed scope

On 2026-10-25 the owner directed this cycle to reuse existing installed evidence and capture only 7:30 PM. During evidence review, the cycle 030 files named for 8 PM were found to contain a day overview and a 7 PM state. The owner then authorized one replacement 8 PM capture. This supersedes the earlier draft's request to repeat the font, RTL, release, idle, and 7 PM checks. The owner reports TalkBack tested and PASS; record that result without repeating it.

Reuse:
- Cycle 029 RTL evidence: `.codex/test-artifacts/029-atm3-evidence-retry/rtl-default.png` and `.xml`, plus its closeout.
- Cycle 030 large-font and reduced-motion evidence: `.codex/test-artifacts/030-atm3-residual-evidence/README.md` and its cited captures/hierarchies.
- Cycle 030 release and selected-hour observability evidence: `reduced-motion-release.png` and `.xml`.
- Owner-reported TalkBack PASS, recorded in this cycle's evidence matrix.

New installed captures are limited to:
- One positive-cloud Monday Oct 26 half-hour scrub near 7:30 PM at the reference Pixel viewport. Keep its actual screenshot and UI hierarchy. The screen rounds the half-hour time label to an hour; record the calibrated gesture and that limitation.
- One owner-authorized replacement Monday Oct 26 8 PM capture at zero animation scales, with screenshot and hierarchy, to repair the cycle 030 file/README mismatch.

Do not claim the exact half-hour is independently visible or that phase movement is perceptible from these captures.

## Scope and production boundary

Evidence consolidation and planning only. No application source, layout, interaction, or rendering changes.

May change:
- `docs/ROADMAP.md` — update ATM.3a's completed scope/evidence and correct ATM.3 evidence references.
- `.codex/plans/031-atm3-blocker-confirmation.md` and `.codex/current.md` — bounded plan and cycle pointer.
- `.codex/test-artifacts/031-atm3-blocker-confirmation/` — the 7:30 and owner-authorized replacement 8 PM screenshots/hierarchies, plus the consolidated evidence record.
- `.codex/history/031-atm3-blocker-confirmation.md` only at closeout.

Do not modify app source, reopen completed acceptance checks, rewrite prior cycle findings, or mark ATM.3 accepted.

## Functional invariants

- `selectedHour` remains authoritative; scale and release do not silently select a different forecast moment.
- Forecast facts and DEMO DATA provenance remain truthful; cloud artwork is decorative.
- Timeline responsiveness, gesture ownership, large-font readability, RTL chronology, and accessibility remain acceptance gates.
- Keep the Pixel 3 XL Developer Options master enabled. Restore font and animation scales, locale, orientation, and app foreground to the recorded baseline.

## Cloud-phase contract for evidence interpretation

Use the existing ATM.3 mapping; do not choose another phase model or retune rates. Cycle 026's plan and implementation establish:

- Far group: `positiveModulo(selectedHour * 0.42 dp/hour * density, 1.5 * canvasWidthPx)`.
- Mid group: `positiveModulo(selectedHour * 0.88 dp/hour * density, 1.5 * canvasWidthPx)`.
- Geometry and rates are stable. The selected fractional fixture hour is the phase input; no wall-clock loop or autonomous drift advances it. Reduced motion suppresses autonomous effects and retains this selected-time mapping.
- At 560 dpi (density 3.5), a half-hour changes far displacement by 0.21 dp / 0.735 px and mid displacement by 0.44 dp / 1.54 px. One hour changes them by 0.42 dp / 1.47 px and 0.88 dp / 3.08 px. This amount may be difficult to distinguish in screenshots.
- Adjacent copies of the 1.5-viewport repeating field make modulo wrap visually continuous.

Authority: `.codex/history/plans/026-atm3-cloud-renderer.md`, `.codex/history/026-atm3-cloud-renderer.md`, and `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`.

## Steps

1. Review the current authority and cycles 026–030, retaining prior evidence by exact path rather than repeating its installed interactions.
2. Record the Pixel build, viewport, locale, font scale, animation scales, orientation, fixture, and app state for the one additional capture.
3. Capture the Monday Oct 26 fractional evening sample while scrubbing, alongside its UI hierarchy. Record the input geometry and what the visible and accessible labels disclose. Do not infer exact half-hour observability from a rounded label.
4. Capture Monday Oct 26 8 PM once at zero animation scales to replace the mismatched cycle 030 artifact, then restore scales. Verify the date, exact hour, forecast facts, and current app state from the hierarchy.
5. Compare both new captures with cycle 030's 7 PM and idle images at the same viewport. Record the scale-setting difference and whether the intermediate phase is perceptible; keep ATM.3 blocked if it is not established.
6. Build a dependency map from the reused findings and source ownership. Recommend the first bounded corrective slice and explain its dependent acceptance checks. Do not implement fixes.
7. Verify font scale 1.0, all animation scales 1.0, `en-US`, portrait/free rotation, app foreground, and authorized ADB.
8. Run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, and `git diff --check`; inspect the complete diff and close the evidence cycle with exact results and limitations.

## Acceptance and evidence

Cycle artifacts: `.codex/test-artifacts/031-atm3-blocker-confirmation/`.
Closeout: `.codex/history/031-atm3-blocker-confirmation.md`.

Complete this evidence cycle when:
- The evidence matrix cites completed font, RTL, release/selection, idle, 7 PM, and 8 PM dispositions from cycles 029/030; TalkBack is recorded as owner-reported PASS without retest.
- The 7:30 PM screenshot/hierarchy and the single corrected 8 PM screenshot/hierarchy are the only new installed acceptance captures. Their selection method, visible facts, viewport, and animation settings are explicit.
- Cloud-phase motion remains unaccepted unless the intermediate displacement is perceptible and the fractional selected time is attributable from evidence. Keep ATM.3 and ATM.4 BLOCKED pending corrective work and the full ATM.3 exit.
- The dependency map identifies the recommended first corrective slice and its source/acceptance ownership.
- Device settings and ADB are restored; workflow, contract, and diff checks pass.

## Verification limits

- Evidence-only. Tests, build, and lint are outside this plan.
- The new 7:30 capture was taken with system animation scales at 1.0 after the owner-directed baseline restoration. Existing 7 PM/8 PM/idle screenshots were taken at zero scales. Source inspection shows cloud phase is a direct selected-time function with no animation clock; record the difference and do not claim a matched reduced-motion capture for 7:30.
- The app rounds the selected time label to an hour. Gesture geometry estimates the intermediate input, but the screenshot/hierarchy cannot independently read back the exact fractional `selectedHour`.
- System RTL behavior and TalkBack PASS are carried forward from the recorded RTL evidence and the owner's report respectively.

## Out of scope

- Repeating the completed font, RTL, release, idle, 7 PM, or TalkBack checks. The owner-authorized replacement 8 PM capture repairs the artifact mismatch and is the only repeat of that state.
- Implementing layout, RTL, selected-time, cloud-phase, or rendering fixes.
- Retuning cloud rates, timeline physics, or gestures.
- ATM.4 precipitation work, environment-pack work, production integration, or data-source changes.
