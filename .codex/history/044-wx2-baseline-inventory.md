# Cycle 044-wx2-baseline-inventory

Date: 2026-10-26
Roadmap: WX.2

## Outcome

Completed WX.2 as a source-grounded, read-only inventory and regression baseline. Recorded all twelve catalog/profile exact-tuple comparisons, compatibility owners, existing behavior and limitations, an installed normal-sample snapshot, and the approved concept board. At execution close, five exact-tuple misses were conservatively classified as potential discrepancies. In a post-closeout owner decision on 2026-10-26, the owner accepted all four rain/snow variants as intentional illustrative catalog cases and the isolated warning preview as an intentional independent case; all five are now classified `catalog-only by design` in the evidence. No application source, fixture, test, build configuration, or deprecated oxygenWX file changed. WX.2 exit evidence is complete; the historical test failure remains a follow-up.

## Verification actually performed

PASS: python scripts/dev.py workflow before activation, during execution, and after closeout (Workflow scaffold: OK). PASS: JDK 27 python scripts/dev.py run --serial 8AKY0M32X; debug assemble, streamed install, and MainActivity launch succeeded. PASS: python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/044-wx2-baseline-inventory/installed-normal-baseline.png. PASS: git diff --check after evidence and roadmap updates. FAIL: initial python scripts/dev.py run with JVM 8; Gradle required JVM 17 or later, then the same run passed with JDK 27. NOT RUN: app tests, contract, lint, release build, accessibility/TalkBack, RTL, large-font, reduced-motion, and gesture acceptance. Historical-only: cycle 040 test assertion failure; not rerun. Full inventory, baseline, screenshot, device details, and limits: .codex/test-artifacts/044-wx2-baseline-inventory/.

## Follow-up

The five catalog/profile differences were dispositioned by the product owner after closeout; see `.codex/test-artifacts/044-wx2-baseline-inventory/owner-disposition.md`. The cycle-040 time-label test failure is preserved as a bounded verification follow-up and was not rerun here. No TalkBack, RTL, large-font, reduced-motion, or gesture acceptance is claimed by this inventory.
