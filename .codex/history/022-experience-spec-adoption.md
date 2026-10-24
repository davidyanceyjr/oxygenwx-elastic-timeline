# Cycle 022-experience-spec-adoption

Date: 2026-10-24
Roadmap: G.3

## Outcome

Adopted OXY-UX-001 as the experience-level companion to the prototype specification. Recorded the authority precedence and source-based discrepancy report. Corrected stale README claims about focused-day expansion, visible controls, and reset behavior. No app behavior changed. Candidate enhancements and production gates remain separately scoped decisions. Installed acceptance was not part of this documentation cycle.

## Verification actually performed

PASS: python scripts/dev.py workflow (before and after adoption; scaffold OK; cycle 022 recognized ACTIVE). PASS: python scripts/dev.py contract (isolated package, no network/location permission, pure time projection). PASS: git diff --check. Final diff inspected; changes limited to documentation/cycle records and newly supplied docs. NOT RUN: tests, lint, build, install, screenshot, or device/accessibility checks; excluded by the bounded documentation-only plan.

## Follow-up

No application or installed acceptance was attempted in this documentation cycle. The app's existing visual/accessibility acceptance remains limited to the evidence and conditions recorded in earlier cycle history. UX.1–UX.5 work is not activated by this adoption; candidate enhancements and production integration require separate bounded owner decisions.
