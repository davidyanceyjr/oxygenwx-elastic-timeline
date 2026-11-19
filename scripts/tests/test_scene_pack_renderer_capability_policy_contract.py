"""Test-only consistency checks for the OX-52 policy examples.

These fixtures do not exercise a production decoder, resource validator,
importer, renderer, trust provider, or entitlement service.
"""

import json
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
EXAMPLES_PATH = ROOT / "docs/scene-pack-renderer-capability-policy-examples.json"
EXPECTED_BUDGET_OWNERS = {
    "manifest_decode_structure": "OX-26",
    "referenced_resources": "OX-91",
    "package_extraction": "OX-57",
    "renderer_load_memory": "OX-55",
}
SUPPORTED_VERSIONS = {
    "manifest_schema": 1,
    "engine_contract": 1,
    "renderer_policy": 1,
}
REQUIRED_CASE_IDS = {
    "supported-version-set",
    "unsupported-manifest-schema-version",
    "unsupported-engine-contract-version",
    "unsupported-renderer-policy-version",
    "known-required-fixture-capability-supported",
    "unknown-required-fixture-capability",
    "known-required-fixture-capability-unsupported-by-renderer",
    "optional-fixture-capability-explicitly-omitted",
    "optional-fixture-capability-explicit-default",
    "known-optional-fixture-capability-without-policy",
    "unknown-optional-fixture-capability-without-policy",
    "schema-defined-declarative-metadata",
    "evidence-backed-image-resource",
    "unsupported-font-resource",
    "unknown-resource-type",
    "pack-supplied-executable-payload",
    "identity-integrity-origin-entitlement-remain-separate",
    "all-finite-budget-owners-within-limits",
    "manifest-decode-budget-exceeded",
    "referenced-resource-budget-exceeded",
    "package-extraction-budget-exceeded",
    "renderer-load-budget-exceeded",
}


def _load_examples() -> list[dict]:
    with EXAMPLES_PATH.open(encoding="utf-8") as examples_file:
        document = json.load(examples_file)
    if "test-only envelope" not in document["$comment"]:
        raise AssertionError("examples must identify their fixture-only boundary")
    return document["examples"]


def _fixture_disposition(fixture_input: dict) -> str:
    """Apply OX-52 policy branches to a test-only fixture, not runtime data."""
    for version_name, check in fixture_input.get("version_checks", {}).items():
        if version_name not in SUPPORTED_VERSIONS:
            raise AssertionError(f"unexpected version dimension {version_name}")
        if not check["supported"] or check["value"] != SUPPORTED_VERSIONS[version_name]:
            return "reject"

    for capability in fixture_input.get("capability_checks", []):
        if capability["required"]:
            if not capability["listed_in_fixture_registry"] or not capability["renderer_supports"]:
                return "reject"
            continue

        if not capability["listed_in_fixture_registry"] or not capability.get("optional_policy"):
            return "reject"
        optional_policy = capability["optional_policy"]
        if optional_policy.get("action") == "omit":
            continue
        if optional_policy.get("action") == "default" and optional_policy.get("value") is not None:
            continue
        return "reject"

    if fixture_input.get("payload_class") == "pack_supplied_executable":
        return "reject"
    if fixture_input.get("payload_class") not in (None, "schema_defined_declarative_metadata"):
        return "reject"

    resource_media_type = fixture_input.get("resource_media_type")
    if resource_media_type is not None and resource_media_type != "image/png":
        return "reject"

    for budget in fixture_input.get("budget_checks", []):
        if EXPECTED_BUDGET_OWNERS.get(budget["category"]) != budget["owner"]:
            raise AssertionError(f"wrong owner for budget category {budget['category']}")
        if not budget["limit_is_finite"] or budget["over_limit"]:
            return "reject"

    return "accept"


class ScenePackRendererCapabilityPolicyContractTests(unittest.TestCase):
    def test_labeled_policy_cases_match_their_expected_dispositions(self) -> None:
        examples = _load_examples()
        self.assertGreaterEqual(len(examples), 1)
        example_ids = {example["case_id"] for example in examples}
        self.assertEqual(len(example_ids), len(examples))
        self.assertTrue(REQUIRED_CASE_IDS.issubset(example_ids))

        for example in examples:
            for capability in example["fixture_input"].get("capability_checks", []):
                self.assertTrue(capability["fixture_id"].startswith("test-only:"))

        for example in examples:
            with self.subTest(case_id=example["case_id"]):
                expected = example["expected_disposition"]
                fixture_input = example["fixture_input"]
                if expected == "classification_only":
                    signals = fixture_input["trust_signals"]
                    self.assertEqual(signals["logical_identity"]["establishes"], "logical_pack_identity")
                    self.assertEqual(signals["content_digest"]["establishes"], "exact_byte_integrity")
                    self.assertEqual(signals["origin_provenance"]["establishes"], "trusted_origin_only")
                    self.assertEqual(signals["entitlement_decision"]["establishes"], "entitlement_only")
                    self.assertEqual(fixture_input["implied_relationships"], [])
                    continue

                self.assertIn(expected, {"accept", "reject"})
                self.assertEqual(_fixture_disposition(fixture_input), expected)

    def test_all_budget_categories_have_finite_named_owners_and_rejections(self) -> None:
        examples = {example["case_id"]: example for example in _load_examples()}
        within_limits = examples["all-finite-budget-owners-within-limits"]["fixture_input"]["budget_checks"]
        self.assertEqual({budget["category"] for budget in within_limits}, set(EXPECTED_BUDGET_OWNERS))
        self.assertTrue(all(budget["limit_is_finite"] for budget in within_limits))

        exceeded_cases = (
            "manifest-decode-budget-exceeded",
            "referenced-resource-budget-exceeded",
            "package-extraction-budget-exceeded",
            "renderer-load-budget-exceeded",
        )
        for case_id in exceeded_cases:
            with self.subTest(case_id=case_id):
                example = examples[case_id]
                self.assertEqual(example["expected_disposition"], "reject")
                self.assertTrue(example["fixture_input"]["reject_whole_candidate"])
                self.assertEqual(_fixture_disposition(example["fixture_input"]), "reject")


if __name__ == "__main__":
    unittest.main()
