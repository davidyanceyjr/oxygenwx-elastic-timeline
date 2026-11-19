"""Focused, test-only checks for the OX-51 asset-reference contract examples."""

import hashlib
import json
import re
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
EXAMPLES_PATH = ROOT / "docs/scene-pack-asset-reference-examples.json"
PACK_ROOT = ROOT / ".codex/test-artifacts/157-ox51-pack-assets-integrity/example-pack"
SOURCE_SHA256 = "60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d"


class AssetReferenceReject(ValueError):
    """A test-only contract disposition; no production resolver is exercised."""


def _is_within_pack_root(pack_root: Path, candidate: Path) -> bool:
    resolved_root = pack_root.resolve()
    resolved_candidate = candidate.resolve()
    try:
        resolved_candidate.relative_to(resolved_root)
        return True
    except ValueError:
        return False


def _resolve_reference(pack_root: Path, reference: dict) -> tuple[Path, str]:
    ref_id = reference.get("refId")
    if not isinstance(ref_id, str) or not ref_id.strip():
        raise AssetReferenceReject("refId must be non-empty")

    path = reference.get("path")
    if not isinstance(path, str) or not path:
        raise AssetReferenceReject("path must be non-empty")
    if "\\" in path:
        raise AssetReferenceReject("backslashes are not canonical path separators")
    if path.startswith("/"):
        raise AssetReferenceReject("absolute paths are rejected")
    if re.match(r"^[A-Za-z]:", path):
        raise AssetReferenceReject("drive-form paths are rejected")
    if re.match(r"^[A-Za-z][A-Za-z0-9+.-]*:", path):
        raise AssetReferenceReject("URI-form paths are rejected")

    segments = path.split("/")
    if any(segment == "" for segment in segments):
        raise AssetReferenceReject("empty path segments are rejected")
    if any(segment == "." for segment in segments):
        raise AssetReferenceReject("dot path segments are rejected")
    if any(segment == ".." for segment in segments):
        raise AssetReferenceReject("parent path segments are rejected")

    if reference.get("mediaType") != "image/png":
        raise AssetReferenceReject("unsupported media type")

    expected_digest = reference.get("sha256")
    if not isinstance(expected_digest, str) or not re.fullmatch(r"[0-9a-f]{64}", expected_digest):
        raise AssetReferenceReject("sha256 must be lowercase 64-character hexadecimal")

    candidate = pack_root.joinpath(*segments)
    try:
        resolved = candidate.resolve(strict=True)
    except FileNotFoundError as exc:
        raise AssetReferenceReject("referenced file is missing") from exc

    if not _is_within_pack_root(pack_root, resolved):
        raise AssetReferenceReject("resolved path escapes the pack root")

    cursor = pack_root
    for segment in segments:
        cursor = cursor / segment
        if cursor.is_symlink():
            raise AssetReferenceReject("symlink entries are rejected")

    if not resolved.is_file():
        raise AssetReferenceReject("referenced path is not a file")

    digest = hashlib.sha256(resolved.read_bytes()).hexdigest()
    if digest != expected_digest:
        raise AssetReferenceReject("sha256 does not match the exact file bytes")
    return resolved, digest


def _load_examples() -> dict[str, dict]:
    with EXAMPLES_PATH.open(encoding="utf-8") as examples_file:
        examples = json.load(examples_file)
    return {example["label"]: example for example in examples}


class ScenePackAssetReferenceContractTests(unittest.TestCase):
    def test_real_olive_example_resolves_inside_pack_root_with_exact_digest(self) -> None:
        example = _load_examples()["valid-first-city-canopy-olive"]
        self.assertEqual(example["disposition"], "accept")
        resolved, digest = _resolve_reference(PACK_ROOT, example["assetReference"])
        sample = PACK_ROOT / "assets/first-city/canopy/olive.png"
        source = ROOT / "app/src/main/res/drawable-nodpi/sg2_city_canopy_olive.png"

        self.assertEqual(resolved, sample.resolve())
        self.assertTrue(_is_within_pack_root(PACK_ROOT, resolved))
        self.assertEqual(digest, example["assetReference"]["sha256"])
        self.assertEqual(digest, SOURCE_SHA256)
        self.assertEqual(hashlib.sha256(source.read_bytes()).hexdigest(), SOURCE_SHA256)

    def test_labeled_invalid_examples_are_rejected(self) -> None:
        examples = _load_examples()
        labels = (
            "invalid-absolute-path",
            "invalid-escaping-path",
            "invalid-unsupported-type",
            "invalid-missing-file",
            "invalid-missing-digest",
            "invalid-mismatched-digest",
        )
        for label in labels:
            with self.subTest(label=label):
                example = examples[label]
                self.assertEqual(example["disposition"], "reject")
                with self.assertRaises(AssetReferenceReject):
                    _resolve_reference(PACK_ROOT, example["assetReference"])

    def test_each_noncanonical_path_form_is_rejected(self) -> None:
        paths = (
            "/assets/first-city/canopy/olive.png",
            "C:/assets/first-city/canopy/olive.png",
            "https://example.invalid/olive.png",
            "assets\\first-city\\canopy\\olive.png",
            "assets//first-city/canopy/olive.png",
            "assets/first-city/./canopy/olive.png",
            "assets/first-city/canopy/../canopy/olive.png",
        )
        for path in paths:
            with self.subTest(path=path):
                reference = {
                    "refId": "path-rule-case",
                    "path": path,
                    "mediaType": "image/png",
                    "sha256": SOURCE_SHA256,
                }
                with self.assertRaises(AssetReferenceReject):
                    _resolve_reference(PACK_ROOT, reference)

    def test_escaping_path_is_outside_root_and_rejected(self) -> None:
        escaping_path = "../../outside/olive.png"
        normalized_candidate = PACK_ROOT / escaping_path
        self.assertFalse(_is_within_pack_root(PACK_ROOT, normalized_candidate))
        reference = {
            "refId": "escaping-path",
            "path": escaping_path,
            "mediaType": "image/png",
            "sha256": SOURCE_SHA256,
        }
        with self.assertRaises(AssetReferenceReject):
            _resolve_reference(PACK_ROOT, reference)

    def test_symlink_entries_are_rejected_inside_and_outside_pack_root(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            temp_root = Path(directory)
            pack_root = temp_root / "pack"
            assets = pack_root / "assets"
            assets.mkdir(parents=True)
            inside_target = assets / "inside.png"
            inside_target.write_bytes(b"inside")
            outside_target = temp_root / "outside.png"
            outside_target.write_bytes(b"outside")
            inside_link = assets / "inside-link.png"
            inside_link.symlink_to(inside_target)
            outside_link = assets / "outside-link.png"
            outside_link.symlink_to(outside_target)

            self.assertTrue(_is_within_pack_root(pack_root, inside_link))
            self.assertFalse(_is_within_pack_root(pack_root, outside_link))
            for path, target in (("assets/inside-link.png", inside_target),
                                 ("assets/outside-link.png", outside_target)):
                with self.subTest(path=path):
                    reference = {
                        "refId": "symlink-entry",
                        "path": path,
                        "mediaType": "image/png",
                        "sha256": hashlib.sha256(target.read_bytes()).hexdigest(),
                    }
                    with self.assertRaises(AssetReferenceReject):
                        _resolve_reference(pack_root, reference)


if __name__ == "__main__":
    unittest.main()
