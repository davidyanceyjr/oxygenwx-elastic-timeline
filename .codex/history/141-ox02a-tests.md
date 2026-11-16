# Cycle 141-ox02a-tests

Date: 2026-11-15
Roadmap items: OX-02A-TESTS

## Outcome

Completed the focused OX-02A-TESTS duplicate on the connected Pixel 3 XL using existing #DEBUG replays. Supported rain and eligible carried rain appear in composition but their rain streaks are absent in the reading fallback; the condition-unavailable state retains its supplied temperature, precipitation chance, wind, and unavailable condition in both routes. The reading timeline interaction updated the selected time. No production code or test fixture changed; the state-only scene-present runtime appearance remains unverified.

## Slice outcomes

- **OX-02A-TESTS — DONE:** Completed all three matched #DEBUG device comparisons, the reading-route timeline interaction, and state-only caller recheck. Recorded the supported-rain and eligible carried-rain scene mismatch between composition and reading routes; condition-unavailable facts and disclosures matched. No production behavior changed. Evidence: .codex/test-artifacts/141-ox02a-tests/README.md, .codex/test-artifacts/141-ox02a-tests/supported-composition.png, .codex/test-artifacts/141-ox02a-tests/supported-reading.png, .codex/test-artifacts/141-ox02a-tests/unavailable-composition.png, .codex/test-artifacts/141-ox02a-tests/unavailable-reading.png, .codex/test-artifacts/141-ox02a-tests/carried-composition.png, .codex/test-artifacts/141-ox02a-tests/carried-reading.png, .codex/test-artifacts/141-ox02a-tests/reading-interaction.png.

## Verification actually performed

- python3 scripts/dev.py run --serial 8AKY0M32X — PASS; debug build assembled, installed, and launched on Pixel 3 XL.
- python3 scripts/dev.py screenshot --serial 8AKY0M32X — PASS; seven installed screenshots captured under .codex/test-artifacts/141-ox02a-tests/. Seven corresponding UIAutomator dumps are present and non-empty.
- Matched #DEBUG captures: supported rain at 2026-10-04T15:30:00Z showed rain streaks in composition and none in reading; carried rain at 2026-10-04T16:30:00Z showed the same scene mismatch while preserving the 10:00 AM CDT source disclosure; unavailable condition at 2026-10-28T20:00:00Z retained matching supplied values.
- adb -s 8AKY0M32X shell input swipe 720 2550 760 2550 500 — PASS; reading-route selection changed from 10:30 AM to 8:54:14 AM CDT with the fact sample at 9:00 AM CDT.
- rg -n 'CatalogTimeLabScreen\s*\(' app/src/main/java app/src/androidTest — PASS; production presentation caller found, only view-only caller is scene=null in CatalogForecastBrowserErrorAccessibilityTest; scene-present route appearance remains unverified.
- python scripts/dev.py workflow — PASS at CLOSEOUT; codex_cycle.py close validated the archived state and returned the cycle pointer to IDLE.
- git diff --check — PASS, exit code 0.
- No app test suite, lint, TalkBack, RTL, localization, or broad device/environment matrix was run; only the authorized Pixel 3 XL debug slice was exercised.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/141-ox02a-tests/`. Archived plan: `.codex/history/plans/141-ox02a-tests.md`. See the plan for stage checkpoints and exact boundaries.
