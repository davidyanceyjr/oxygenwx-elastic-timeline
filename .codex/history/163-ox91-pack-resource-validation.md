# Cycle 163-ox91-pack-resource-validation

Date: 2026-11-20
Roadmap items: OX-91

## Outcome

Completed OX-91 with a bounded, read-only manifest-v2 resource validator, focused tests, and runtime validation documentation. The baseline and olive builder packs are accepted with exact source assets, matching declared SHA-256 digests, and builder-matching OX-54 identities. All focused rejection and exact-limit cases pass. The slice exit is met; no UI, schema, builder, decoder, extraction, or activation behavior changed. PNG decompression/pixel validity, extraction/install, renderer memory/activation, and broader system/device acceptance remain outside OX-91.

## Slice outcomes

- **OX-91 — DONE:** Implemented bounded read-only resource validation and completed the accepted manifest, archive, PNG structure/CRC, digest, and content-identity checks. Baseline and olive packs are accepted with exact assets and builder-matching identities; focused rejection and inclusive-limit coverage passes. OX-91 exit is met. PNG decompression/pixel validity, extraction, renderer memory, installation, activation, and system/device acceptance remain outside this slice. Evidence: .codex/test-artifacts/163-ox91-pack-resource-validation/README.md, .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip, .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip, .codex/test-artifacts/163-ox91-pack-resource-validation/builder-baseline.txt, .codex/test-artifacts/163-ox91-pack-resource-validation/builder-olive.txt, .codex/test-artifacts/163-ox91-pack-resource-validation/focused-tests.txt, .codex/test-artifacts/163-ox91-pack-resource-validation/validation-inspection.txt, .codex/test-artifacts/163-ox91-pack-resource-validation/inspect_artifacts.py, app/src/main/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidator.kt, app/src/test/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidatorTest.kt, docs/SCENE_PACK_RESOURCE_VALIDATION.md, docs/SCENE_PACK_RENDERER_COMPATIBILITY.md, docs/SCENE_PACK_MANIFEST_V2.md, docs/SCENE_PACK_CONTENT_IDENTITY.md.

## Verification actually performed

- python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip: exit 0; baseline asset 1,259,884 bytes.
- python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-olive --output .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip: exit 0; olive asset 1,386,266 bytes.
- OX91_PACK_BASELINE_ARTIFACT and OX91_PACK_OLIVE_ARTIFACT set to the two cycle ZIPs; ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidatorTest: exit 0; 13 tests, 0 failures, 0 skipped.
- python .codex/test-artifacts/163-ox91-pack-resource-validation/inspect_artifacts.py: exit 0; both source assets, declared digests, and builder OX-54 identities matched; both archive inventory bounds were within limits.
- python scripts/dev.py workflow: exit 0; Workflow scaffold OK at ACTIVE/CLOSEOUT with OX-91 DONE and no active slice.
- git diff --check: exit 0, no output.
- Focused new-file whitespace check: exit 0, no trailing whitespace in six new files.
- The cycle close operation's post-archive workflow validation passed; full unit suite, Android build, lint, instrumentation, and system/device/visual checks were not run because they are outside the authorized OX-91 verification.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/163-ox91-pack-resource-validation/`. Archived plan: `.codex/history/plans/163-ox91-pack-resource-validation.md`. See the plan for stage checkpoints and exact boundaries.
