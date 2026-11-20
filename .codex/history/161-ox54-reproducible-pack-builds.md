# Cycle 161-ox54-reproducible-pack-builds

Date: 2026-11-19
Roadmap items: OX-54

## Outcome

OX-54 is complete. The builder computes and reports a domain-versioned SHA-256 content identity from the exact validated manifest and declared resource bytes before ZIP creation. Two independent First City baseline builds reported sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f; the supported olive variant reported sha256:f985286099435fe8aab7d2d31beb8c702006b2241ba8c2f71ddd05f507dd665a. The standalone contract documents byte framing, archive metadata exclusions, signal distinctions, and future reader/installer use. System and device checks were outside scope and are not claimed.

## Slice outcomes

- **OX-54 — DONE:** Two independent builds from identical First City baseline sources with manifest version 2 reported the same canonical content identity sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f; the OX-53 olive variant reported the distinct identity sha256:f985286099435fe8aab7d2d31beb8c702006b2241ba8c2f71ddd05f507dd665a. The exact payload identity rule, archive metadata exclusions, and future reader/installer handoff are documented. Focused tests passed and all three artifacts were inspected for exact source-byte preservation. Evidence: .codex/test-artifacts/161-ox54-reproducible-pack-builds/README.md, .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1.zip, .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2.zip, .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant.zip, .codex/test-artifacts/161-ox54-reproducible-pack-builds/focused-builder-tests.txt, .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1-output.txt, .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2-output.txt, .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant-output.txt, .codex/test-artifacts/161-ox54-reproducible-pack-builds/zip-payload-inspection.txt, docs/SCENE_PACK_CONTENT_IDENTITY.md, docs/SCENE_PACK_BUILDER.md.

## Verification actually performed

- python -m unittest discover -s scripts/tests -p 'test_scene_pack_builder.py' — PASS; 11 focused tests.
- python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1.zip — PASS; manifestVersion=2, contentIdentity=sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.
- python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2.zip — PASS; manifestVersion=2, contentIdentity=sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.
- python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-olive --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant.zip — PASS; manifestVersion=2, contentIdentity=sha256:f985286099435fe8aab7d2d31beb8c702006b2241ba8c2f71ddd05f507dd665a.
- Direct ZIP payload inspection — PASS; exact member sets, source-byte matches, and non-executable entries verified; output preserved in zip-payload-inspection.txt.
- python scripts/dev.py workflow — PASS at ACTIVE / CLOSEOUT; Workflow scaffold: OK.
- git diff --check — PASS.
- Final scoped diff review — PASS; system/device checks not run per slice scope.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/161-ox54-reproducible-pack-builds/`. Archived plan: `.codex/history/plans/161-ox54-reproducible-pack-builds.md`. See the plan for stage checkpoints and exact boundaries.
