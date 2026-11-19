"""Focused, test-only checks for the OX-53 manifest v2 examples.

These checks validate design-time documents and copied assets. They do not
exercise a production decoder, resolver, importer, installer, or renderer.
"""

import copy
import hashlib
import json
import re
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
CAPABILITY_ID = "first-city.canopy.image-png"
MANIFEST_MEMBERS = {
    "packId",
    "manifestVersion",
    "worldDefinitionRef",
    "engineCompatibility",
    "rendererPolicyVersion",
    "requiredCapabilities",
    "assetReferences",
}
ASSET_REFERENCE_MEMBERS = {"refId", "path", "mediaType", "sha256"}
EXAMPLES = (
    {
        "pack_id": "first-city-canopy-baseline",
        "ref_id": "first-city-canopy-baseline",
        "manifest": Path("docs/scene-pack-examples/first-city-canopy-baseline/manifest.json"),
        "pack_root": Path("docs/scene-pack-examples/first-city-canopy-baseline"),
        "asset_path": "assets/first-city/canopy/baseline.png",
        "source_asset": Path("app/src/main/res/drawable-nodpi/sg2_city_canopy.png"),
        "sha256": "480dd857a7aa4ed7d3da62c991cf04056a364fbf04554027d9f6af6628791c89",
    },
    {
        "pack_id": "first-city-canopy-olive",
        "ref_id": "first-city-canopy-olive",
        "manifest": Path("docs/scene-pack-examples/first-city-canopy-olive/manifest.json"),
        "pack_root": Path("docs/scene-pack-examples/first-city-canopy-olive"),
        "asset_path": "assets/first-city/canopy/olive.png",
        "source_asset": Path("app/src/main/res/drawable-nodpi/sg2_city_canopy_olive.png"),
        "sha256": "60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d",
    },
)


class DuplicateManifestMember(ValueError):
    """A repeated JSON object member name in a design-time manifest."""


class ManifestContractError(ValueError):
    """A design-time example does not match the closed v2 contract."""


def _unique_object(pairs: list[tuple[str, object]]) -> dict[str, object]:
    result = {}
    for key, value in pairs:
        if key in result:
            raise DuplicateManifestMember(f"duplicate JSON object member: {key}")
        result[key] = value
    return result


def _parse_raw_json(raw: str) -> object:
    """Keep JSON member pairs visible so duplicate names cannot be erased."""
    return json.loads(raw, object_pairs_hook=_unique_object)


def _require(condition: bool, message: str) -> None:
    if not condition:
        raise ManifestContractError(message)


def _validate_manifest(manifest: object, example: dict[str, object]) -> dict[str, object]:
    _require(isinstance(manifest, dict), "manifest must be a JSON object")
    _require(set(manifest) == MANIFEST_MEMBERS, "manifest must have exactly seven members")

    pack_id = manifest["packId"]
    _require(isinstance(pack_id, str), "packId must be a JSON string")
    _require(
        re.fullmatch(r"[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?", pack_id) is not None,
        "packId must follow the OX-20 lowercase identifier grammar",
    )
    _require(pack_id == example["pack_id"], "packId must match the approved example")

    for member, value in (
        ("manifestVersion", 2),
        ("engineCompatibility", 1),
        ("rendererPolicyVersion", 1),
    ):
        actual = manifest[member]
        _require(type(actual) is int, f"{member} must be a JSON integer")
        _require(actual == value, f"{member} must equal {value}")

    world_ref = manifest["worldDefinitionRef"]
    _require(isinstance(world_ref, str), "worldDefinitionRef must be a JSON string")
    _require(world_ref == "first-city", "worldDefinitionRef must name First City")

    capabilities = manifest["requiredCapabilities"]
    _require(isinstance(capabilities, list), "requiredCapabilities must be a JSON array")
    _require(capabilities == [CAPABILITY_ID], "the example must require the registered capability")
    _require(all(isinstance(value, str) for value in capabilities), "capability IDs must be strings")

    references = manifest["assetReferences"]
    _require(isinstance(references, list), "assetReferences must be a JSON array")
    _require(len(references) == 1, "the example must contain exactly one asset reference")
    reference = references[0]
    _require(isinstance(reference, dict), "asset reference must be a JSON object")
    _require(set(reference) == ASSET_REFERENCE_MEMBERS, "asset reference must have exactly four members")
    _require(all(isinstance(value, str) for value in reference.values()), "asset reference values must be strings")
    _require(reference["refId"] == example["ref_id"], "refId must match the approved example")

    path = reference["path"]
    _require(path == example["asset_path"], "asset path must match the approved canonical path")
    _require(not path.startswith("/") and "\\" not in path, "asset path must be relative and slash-separated")
    segments = path.split("/")
    _require(
        all(segment not in {"", ".", ".."} for segment in segments),
        "asset path must not contain empty, dot, or parent segments",
    )
    _require(reference["mediaType"] == "image/png", "mediaType must be image/png")
    digest = reference["sha256"]
    _require(re.fullmatch(r"[0-9a-f]{64}", digest) is not None, "sha256 must be lowercase hexadecimal")
    _require(digest == example["sha256"], "sha256 must match the approved example")
    return manifest


class ScenePackManifestV2ExamplesContractTests(unittest.TestCase):
    def test_examples_match_closed_v2_envelope_and_approved_values(self) -> None:
        for example in EXAMPLES:
            with self.subTest(pack_id=example["pack_id"]):
                manifest_path = ROOT / example["manifest"]
                manifest = _parse_raw_json(manifest_path.read_text(encoding="utf-8"))
                _validate_manifest(manifest, example)

    def test_asset_references_resolve_inside_each_pack_and_match_exact_source_bytes(self) -> None:
        for example in EXAMPLES:
            with self.subTest(pack_id=example["pack_id"]):
                manifest = _parse_raw_json((ROOT / example["manifest"]).read_text(encoding="utf-8"))
                reference = _validate_manifest(manifest, example)["assetReferences"][0]
                pack_root = ROOT / example["pack_root"]
                asset_path = (pack_root / reference["path"]).resolve(strict=True)
                asset_path.relative_to(pack_root.resolve())
                self.assertTrue(asset_path.is_file())

                source_bytes = (ROOT / example["source_asset"]).read_bytes()
                copied_bytes = asset_path.read_bytes()
                self.assertEqual(copied_bytes, source_bytes)
                self.assertEqual(hashlib.sha256(copied_bytes).hexdigest(), reference["sha256"])

    def test_required_capability_is_in_policy_v1_with_design_time_limit(self) -> None:
        policy = (ROOT / "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md").read_text(encoding="utf-8")
        registry = policy.split("## Renderer policy version 1 registry", maxsplit=1)[1].split("\n## ", maxsplit=1)[0]
        self.assertIn("exactly one required public capability and no", registry)
        self.assertIn(f"| `{CAPABILITY_ID}` |", registry)
        self.assertIn("does not claim arbitrary pack files can be loaded at runtime", registry)

    def test_duplicate_object_members_are_rejected_from_raw_json(self) -> None:
        raw_documents = (
            '{"packId":"first-city-canopy-baseline","packId":"duplicate"}',
            '{"assetReferences":[{"refId":"first","refId":"duplicate"}]}',
        )
        for raw in raw_documents:
            with self.subTest(raw=raw):
                with self.assertRaises(DuplicateManifestMember):
                    _parse_raw_json(raw)

    def test_boolean_is_not_accepted_as_a_json_integer_version(self) -> None:
        example = EXAMPLES[0]
        manifest = _parse_raw_json((ROOT / example["manifest"]).read_text(encoding="utf-8"))
        manifest["manifestVersion"] = True
        with self.assertRaises(ManifestContractError):
            _validate_manifest(manifest, example)

    def test_noncanonical_path_media_type_and_digest_are_rejected(self) -> None:
        example = EXAMPLES[0]
        original = _parse_raw_json((ROOT / example["manifest"]).read_text(encoding="utf-8"))
        for member, value in (
            ("path", "../outside/baseline.png"),
            ("mediaType", "image/jpeg"),
            ("sha256", "0" * 64),
        ):
            with self.subTest(member=member):
                manifest = copy.deepcopy(original)
                manifest["assetReferences"][0][member] = value
                with self.assertRaises(ManifestContractError):
                    _validate_manifest(manifest, example)


if __name__ == "__main__":
    unittest.main()
