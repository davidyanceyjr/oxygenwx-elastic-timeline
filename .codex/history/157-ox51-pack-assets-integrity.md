# Cycle 157-ox51-pack-assets-integrity

Date: 2026-11-19
Roadmap items: OX-51

## Outcome

OX-51 completed: documented a standalone scene-pack asset-reference contract and accepted/rejected examples, copied and verified the OX-24 olive PNG inside a sample pack, and completed the authorized focused contract checks. Manifest v1 remains closed; OX-53 owns versioned manifest integration, OX-52 trust/capability/budget policy, and OX-91 runtime resource validation and limits.

## Slice outcomes

- **OX-51 — DONE:** Defined the standalone asset-reference contract and labeled accept/reject examples; the exact OX-24 olive PNG is contained at its canonical sample-pack path with the declared digest. Focused checks cover every approved path rejection class, symlink entries, content type, missing resources, and digest dispositions. Evidence: .codex/test-artifacts/157-ox51-pack-assets-integrity/README.md, .codex/test-artifacts/157-ox51-pack-assets-integrity/example-pack/assets/first-city/canopy/olive.png.

## Verification actually performed

- python -m unittest discover -s scripts/tests -p 'test_scene_pack_asset_reference_contract.py' — PASS; 5 focused tests.
- python -m json.tool docs/scene-pack-asset-reference-examples.json — PASS.
- python scripts/dev.py workflow — PASS at ACTIVE / CLOSEOUT; close validation passed at IDLE.
- git diff --check — PASS.
- Source and sample PNG are 1024 × 1536 RGBA; both SHA-256 digests equal 60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d.
- Limit: focused checks are test-only and do not exercise a production resolver or OX-91 runtime validation. No app build, installed screenshot, device matrix, or numeric resource budgets were in scope; OX-52 and OX-91 retain their stated handoffs.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/157-ox51-pack-assets-integrity/`. Archived plan: `.codex/history/plans/157-ox51-pack-assets-integrity.md`. See the plan for stage checkpoints and exact boundaries.
