# Cycle 072-wx26-production-startup

Date: 2026-10-30
Roadmap: WX.26

## Outcome

Completed WX.26 production startup wiring and installed acceptance on Pixel 3 XL. Verified first launch, saved/offline restore, Chicago-to-Miami cached switch, Chicago-to-Denver uncached offline switch, foreground refresh success, failed refresh with active catalog, no-cache failure and retry recovery. Removed temporary Android user 10. A preliminary user 0 attempt to save Denver added it to user 0's saved locations; Miami remained selected. Evidence is under .codex/test-artifacts/072-wx26-production-startup/.

## Verification actually performed

PASS — python scripts/dev.py workflow (activation and closeout); python scripts/dev.py contract; python scripts/dev.py build; python scripts/dev.py run --serial 8AKY0M32X (build, streamed install, launch); installed Pixel flows and captures documented in the cycle README; git diff --check. Device font scale, animator duration, Force RTL, Wi-Fi, and mobile data restored. Automated tests NOT RUN under the plan/repository authorization boundary. Selector-dismissal selected-time/scale comparison and runtime stale-generation callback injection NOT RUN; the generation guard was confirmed by code inspection. Broad TalkBack/orientation/performance/compact-viewport suites remain outside scope. User 0 presented its PIN screen after switching back; no actions were taken in that user during the temporary-profile run.

## Follow-up

No focused WX.26 roadmap exit flow remains open. Automated tests were not run; selector dismissal state retention and runtime injection of a former-generation callback were not verified. The generation comparison was confirmed by code inspection. The Pixel returned to user 0's PIN screen after temporary-user cleanup, preventing removal of the extra saved Denver entry; user 0's selected Miami catalog was not changed. This cycle does not claim broad accessibility or full application acceptance.
