# Cycle plan — 158-ox52-renderer-capability-trust

State: CLOSED
Plan maturity: FINAL
Cycle ID: `158-ox52-renderer-capability-trust`
Roadmap items: OX-52
Objective: Define OX-52's versioned renderer-capability policy, data-only pack trust boundary, safe-rejection matrix, and finite import/load budget ownership.

## Cycle roster and board approvals

The structured roster records the dependency order, observable exit, approved behavior-board
package and fingerprints, and explicit UI-artwork applicability for this single-slice cycle.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "158-ox52-renderer-capability-trust",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-52",
      "depends_on": [
        "OX-20",
        "OX-51",
        "OX-36"
      ],
      "status": "DONE",
      "exit": "A version/capability/trust matrix names allowed resource classes, rejection of unsafe or unsupported constructs and ownership of finite import/load budgets; free bundled scene is not entitlement-gated.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.dot",
              "sha256": "437faf49d7d1daf0f00dc45f48cb8527aee84c9b1262e10c93e5e933659af8c9"
            },
            {
              "path": ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.svg",
              "sha256": "4951319471c28c4d16a7a1a668e03f03c56bebceeb08c84a14d64898f5066c63"
            },
            {
              "path": ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.png",
              "sha256": "f08566350a14a1ebf856986ade223d7306f7014e32ba75ecbb91e99be8f61aca"
            },
            {
              "path": ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.md",
              "sha256": "6b26c6e0dd2439fb5ef69269498a7f05f7a92fe60d4b250315d0b8e9d0cb1c3e"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Published the OX-52 versioned renderer capability, data-only trust, safe-rejection, resource-class, and finite-budget policy; added test-only examples and focused contract checks; linked the policy from the OX-20 and OX-51 contracts. The approved exit is met; runtime implementation remains a later-slice responsibility.",
      "evidence": [
        ".codex/test-artifacts/158-ox52-renderer-capability-trust/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-19",
    "summary": "Completed OX-52's renderer capability compatibility and data-only trust policy. The policy defines separate version boundaries, supported and rejected capability/resource outcomes, identity/integrity/origin/entitlement distinctions, finite budget owners, and no entitlement gate for the bundled free scene. Added test-only policy examples and focused consistency checks, linked the policy from the OX-20 and OX-51 contracts, and recorded evidence. No runtime loading behavior or numeric budget thresholds were implemented.",
    "verification": [
      "python -m unittest discover -s scripts/tests -p 'test_scene_pack_renderer_capability_policy_contract.py' — PASS; 2 focused test-only checks",
      "python -m json.tool docs/scene-pack-renderer-capability-policy-examples.json > /dev/null — PASS",
      "python scripts/dev.py workflow — PASS at PLANNED/REVIEW, ACTIVE/EXECUTE, and ACTIVE/CLOSEOUT; cycle close also validated the final IDLE state",
      "git diff --check — PASS after implementation and at CLOSEOUT"
    ],
    "evidence_directory": ".codex/test-artifacts/158-ox52-renderer-capability-trust/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "158-ox52-renderer-capability-trust",
  "slice_ids": [
    "OX-52"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.dot",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.svg",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.png",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.md"
  ],
  "completed_gates": [
    "OX-52 exit met: policy matrix, rejection outcomes, budget owners, contract links, and agreeing focused policy examples are present.",
    "OX-52 exit and evidence recorded; no owner questions or unfinished roster slices remain."
  ],
  "decisions": [
    "Product owner explicitly requested execution of cycle 158 on 2026-11-19; this is the owner request to activate OX-52 recorded as the prior checkpoint next action."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Authored the normative renderer compatibility, data-only trust, resource-class, and finite-budget policy.",
    "Added labeled test-only policy examples and a focused test that checks version, capability, payload, resource, trust, and budget dispositions.",
    "Linked the policy from the OX-20 manifest v1 and OX-51 asset-reference contracts without changing either contract boundary.",
    "Reviewed the final policy against the approved OX-52 board and OX-20/OX-51/OX-36 dependencies.",
    "Closed slice OX-52 as DONE.",
    "All included slices reached terminal outcomes: OX-52 is DONE with its evidence record.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/158-ox52-renderer-capability-trust/README.md",
    ".codex/history/158-ox52-renderer-capability-trust.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/158-ox52-renderer-capability-trust.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.dot",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.svg",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.png",
    ".codex/concepts/OX-52-renderer-capability-trust-behavior-board.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    "docs/scene-pack-renderer-capability-policy-examples.json",
    "docs/SCENE_PACK_MANIFEST_V1.md",
    "docs/SCENE_PACK_ASSET_REFERENCES.md",
    "scripts/tests/test_scene_pack_renderer_capability_policy_contract.py",
    ".codex/test-artifacts/158-ox52-renderer-capability-trust/README.md",
    ".codex/history/156-ox20-pack-identity-manifest.md",
    ".codex/history/150-ox36-weather-input-slots.md",
    ".codex/history/157-ox51-pack-assets-integrity.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SceneElementDeclaration.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/MinimalOpenLandscapeComposition.kt",
    "scripts/tests/test_scene_pack_asset_reference_contract.py"
  ],
  "next_action": "Inspect the complete final diff and run the authorized closeout workflow and diff checks, then archive cycle 158 with the exact outcome, verification, evidence, and limits."
}
```
<!-- cycle-checkpoint:end -->

## Objective and approved board

Define OX-52's versioned renderer-capability policy, data-only pack trust boundary, safe-rejection matrix, and finite import/load budget ownership. The product owner approved the four-file behavior-board package on 2026-11-19; cycle 158 records its fingerprints. The board requires OX-20 manifest v1 to remain closed; separates renderer-policy versioning from manifest schema and `engineCompatibility`; rejects unknown required capabilities and unknown optional capabilities without an explicit registry policy; limits accepted resources to schema-defined data and the evidence-backed `image/png` type; rejects executable content; keeps identity, content integrity, origin trust, and entitlement distinct; and assigns budget enforcement to OX-26/OX-91/OX-57/OX-55. The board leaves the future manifest field spelling to OX-53 and does not expose internal Kotlin names as pack IDs. UI artwork does not apply because no visible output changes.

## Scope and delivery

OX-52 is the only roster item. Its external dependencies OX-20, OX-51, and OX-36 are `DONE`. OX-53 remains a separate follow-up because it depends on this policy and owns the complete example manifests.

Deliver the following contract and focused verification artifacts:

1. Add `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` as the normative matrix for manifest and renderer-policy versioning; required and optional capability dispositions; accepted resource classes; data-only rejection rules; identity, integrity, origin, and entitlement distinctions; and finite budget categories, enforcement owners, and reject outcomes.
2. Add `docs/scene-pack-renderer-capability-policy-examples.json` with clearly labeled contract examples for supported and unsupported versions, known/unknown required capabilities, optional capabilities with and without an explicit policy, accepted/unsupported resource types, executable payload rejection, independent trust signals, and budget-owner outcomes. Use a labeled test-only fixture envelope for policy inputs and expected dispositions; its keys are not proposed manifest fields. These are policy examples, not runtime decoder or loader results and not the final OX-53 manifest examples.
3. Add `scripts/tests/test_scene_pack_renderer_capability_policy_contract.py` as a focused test-only check that the labeled examples match the documented policy. It must not claim to exercise a production decoder, resource validator, importer, or renderer.
4. Link the new policy from `docs/SCENE_PACK_MANIFEST_V1.md` and `docs/SCENE_PACK_ASSET_REFERENCES.md`, preserving their existing contract boundaries and the closed manifest v1.

The policy assigns separate meanings to `manifestVersion`, OX-20 `engineCompatibility`, and renderer capability-policy version. OX-20 v1 continues to accept exactly its existing fields. The new document defines a versioned registry and the rule that only explicitly listed, renderer-supported IDs are accepted; unknown required IDs reject. A known optional ID may be omitted or defaulted only when its registry entry states that exact behavior. An unknown optional ID with no policy rejects. Registry version 1 has no optional capability entries; future optional entries require an explicit fallback/default. The current internal `VEGETATION` role is a candidate only, not a public capability ID. OX-53 selects the exact external IDs and supported behavior from renderer paths demonstrated by its examples; the manifest field that carries the registry version also remains OX-53's decision.

The initial resource policy accepts only schema-defined declarative metadata plus explicitly registered asset media types. `image/png` is the evidence-backed asset type. No font type is established by the current real variant. Unknown media types and all pack-supplied scripts, bytecode, native libraries, callbacks, or other executable behavior reject. Container encoding and transport remain outside this slice.

Budget categories and enforcement ownership are:

- **Decode size and structure:** OX-26 bounds manifest input and structural parsing before returning a typed result.
- **Referenced resources:** OX-91 enforces pack-root confinement, allowed media, and finite per-resource and aggregate resource limits before activation.
- **Package extraction:** OX-57 bounds entries, expanded data, staged storage, and extraction working memory before commit.
- **Renderer load memory:** OX-55 bounds decoded asset and candidate-scene memory before activation; OX-91 validates resource metadata against its assigned limits before handoff.

Every budget is finite, but OX-52 sets no numeric thresholds. Each enforcing slice selects values from its runtime evidence. Exceeded limits reject the whole candidate at the owning boundary without partial activation or commit. Content digest establishes byte integrity only; origin trust and entitlement remain separate inputs and cannot be inferred from pack identity, capabilities, or digest. The bundled free scene remains available without entitlement gating.

## Dependencies and invariants

- **OX-20:** Preserve the closed four-field manifest v1 identity envelope. Its `engineCompatibility: 1` is not a renderer capability list.
- **OX-51:** Preserve canonical pack-root-relative references and exact-byte SHA-256 semantics. OX-91 owns path and per-resource-size enforcement; OX-52 names the cross-layer budget categories.
- **OX-36:** Keep weather interpretation in `SceneWeatherDecision`; capability and resource policy cannot reinterpret or alter supplied, carried, or unavailable weather meaning.
- Preserve the bundled free experience independently from pack or entitlement outcomes.
- Keep capability IDs declarative and data-only. Pack data cannot invoke code, change forecast facts, or claim source trust.

## Exclusions and handoffs

No application/runtime decoder, archive reader, resource resolver, installer, renderer-loading implementation, network transport, signature/trusted-origin provider, entitlement integration, storefront, UI, numeric budget thresholds, Park content, or OX-53 example manifests. No changes to weather projection, selected-time behavior, world selection, or visual rendering. OX-26 implements decoding; OX-91 implements resource validation; OX-57 implements staged import/extraction; OX-55 implements renderer instantiation; OX-27 and later slices decide distribution and entitlement. This cycle defines their policy handoffs only.

## Execution sequence

1. Write the normative compatibility policy from the approved board and OX-20/OX-51/OX-36 contracts. Preserve the closed v1 envelope and distinguish established facts from future OX-53 manifest field choices.
2. Add labeled policy examples for version, known/unknown required capabilities, optional capabilities with and without explicit policy, resource, trust-signal, and budget outcomes. Use a labeled test-only fixture envelope for policy inputs and expected dispositions; fixture keys and synthetic IDs do not propose OX-53 manifest fields or OX-52 registry entries. OX-53 retains the public manifest field spelling decision. Link the normative policy from the two existing scene-pack contracts without changing their existing examples or semantics.
3. Add the focused test-only check for those examples. Keep any capability tokens used only to exercise generic policy branches explicitly labeled as fixtures; do not present them as final OX-53 manifest IDs unless they are independently supported by the existing renderer path.
4. Run only the authorized focused unittest, JSON parse, workflow, and diff checks. Inspect the contract against the approved board and record exact results and test-only limitations under the cycle evidence directory.

## Exit and authorized verification

Close OX-52 only when the normative matrix names allowed resource classes, capability and trust outcomes, unsafe/unsupported rejection dispositions, and the finite budget ownership boundaries above; the two dependency contracts link to the new policy without changing their meanings; and the focused policy examples/checks agree with the approved board.

Authorized verification is limited to:

- `python -m unittest discover -s scripts/tests -p 'test_scene_pack_renderer_capability_policy_contract.py'` — focused test-only policy/example checks.
- `python -m json.tool docs/scene-pack-renderer-capability-policy-examples.json` — validate the policy-example JSON.
- `python scripts/dev.py workflow` and `git diff --check`.
- Inspect the final policy, links, examples, focused test, and diff against the approved board and the OX-20/OX-51/OX-36 boundaries.

No app build, visual/device acceptance, system matrix, or broad test suite is in scope. User-authorized focused slice tests do not imply runtime loader acceptance. Any unperformed command must be recorded as not run in the evidence README.

## Evidence, risks, and limitations

Evidence target: `.codex/test-artifacts/158-ox52-renderer-capability-trust/README.md`. Closeout target: `.codex/history/158-ox52-renderer-capability-trust.md`. The evidence README records exact commands/results, policy review, and limitations; these paths are targets until execution.

The focused examples prove only that the documented policy cases are internally consistent. They do not prove OX-26 decoding, OX-91 resource enforcement, OX-57 extraction, OX-55 load-memory behavior, trusted origin, entitlement, or numeric thresholds. Those remain explicit later-slice handoffs. OX-53 must use only capability IDs mapped to evidenced renderer paths and must add future manifest fields under a new manifest version.
