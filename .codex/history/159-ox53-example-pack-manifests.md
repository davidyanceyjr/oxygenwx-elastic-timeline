# Cycle 159-ox53-example-pack-manifests

Date: 2026-11-19
Roadmap items: OX-53

## Outcome

Completed OX-53 example pack manifests: added the v2 contract, registered the evidence-backed First City canopy capability, added baseline and olive example roots with exact-byte assets, and added focused manifest contract checks. Runtime pack loading is not claimed.

## Slice outcomes

- **OX-53 — DONE:** Delivered the closed manifest v2 contract and two self-contained First City sample packs; preserved the v1 envelope, registered the evidence-backed required capability, and passed focused checks for exact shapes, duplicate member names, relative asset paths, and exact source bytes. Evidence: .codex/test-artifacts/159-ox53-example-pack-manifests/README.md.

## Verification actually performed

- python -m unittest discover -s scripts/tests -p 'test_scene_pack_manifest_v2_examples_contract.py' — PASS (6 focused checks)
- python -m json.tool docs/scene-pack-examples/first-city-canopy-baseline/manifest.json > /dev/null — PASS
- python -m json.tool docs/scene-pack-examples/first-city-canopy-olive/manifest.json > /dev/null — PASS
- python scripts/dev.py workflow — PASS at ACTIVE/CLOSEOUT before close
- git diff --check — PASS

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/159-ox53-example-pack-manifests/`. Archived plan: `.codex/history/plans/159-ox53-example-pack-manifests.md`. See the plan for stage checkpoints and exact boundaries.
