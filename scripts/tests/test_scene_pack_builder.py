"""Focused command-level acceptance checks for the OX-30 local scene-pack builder."""

import json
import shutil
import subprocess
import sys
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
BUILDER = ROOT / "scripts/build_scene_pack.py"
BASELINE = ROOT / "docs/scene-pack-examples/first-city-canopy-baseline"
ASSET_PATH = "assets/first-city/canopy/baseline.png"


def _copy_source(parent: Path, name: str = "source") -> Path:
    source = parent / name
    shutil.copytree(BASELINE, source)
    return source


def _load_manifest(source: Path) -> dict:
    return json.loads((source / "manifest.json").read_text(encoding="utf-8"))


def _write_manifest(source: Path, manifest: dict) -> None:
    (source / "manifest.json").write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")


def _run_builder(source: Path, output: Path) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        [sys.executable, str(BUILDER), "--source", str(source), "--output", str(output)],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )


class ScenePackBuilderTests(unittest.TestCase):
    def assert_rejected(self, source: Path, output: Path, reason: str) -> None:
        result = _run_builder(source, output)
        self.assertNotEqual(result.returncode, 0, result.stdout)
        self.assertIn("Rejected:", result.stderr)
        self.assertIn(reason, result.stderr)
        self.assertNotIn("Built scene pack:", result.stdout + result.stderr)
        self.assertFalse(output.exists(), result.stderr)

    def test_baseline_build_preserves_exact_payload_bytes_and_has_no_executable_entries(self) -> None:
        manifest_bytes = (BASELINE / "manifest.json").read_bytes()
        asset_bytes = (BASELINE / ASSET_PATH).read_bytes()
        with tempfile.TemporaryDirectory() as temporary:
            output = Path(temporary) / "first-city-baseline-v2.zip"
            result = _run_builder(BASELINE, output)

            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertIn(str(output.resolve()), result.stdout)
            self.assertIn("manifestVersion=2", result.stdout)
            self.assertTrue(output.is_file())
            with zipfile.ZipFile(output) as archive:
                self.assertEqual(set(archive.namelist()), {"manifest.json", ASSET_PATH})
                self.assertEqual(archive.read("manifest.json"), manifest_bytes)
                self.assertEqual(archive.read(ASSET_PATH), asset_bytes)
                for info in archive.infolist():
                    self.assertEqual(info.compress_type, zipfile.ZIP_STORED)
                    mode = info.external_attr >> 16
                    self.assertEqual(mode & 0o111, 0)

    def test_duplicate_and_extra_manifest_members_are_rejected_without_an_artifact(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            parent = Path(temporary)

            duplicate_source = _copy_source(parent, "duplicate")
            raw = (duplicate_source / "manifest.json").read_text(encoding="utf-8")
            raw = raw.replace(
                '"packId": "first-city-canopy-baseline",',
                '"packId": "first-city-canopy-baseline",\n  "packId": "first-city-canopy-baseline",',
                1,
            )
            (duplicate_source / "manifest.json").write_text(raw, encoding="utf-8")
            self.assert_rejected(duplicate_source, parent / "duplicate.zip", "duplicate JSON object member: packId")

            extra_source = _copy_source(parent, "extra")
            manifest = _load_manifest(extra_source)
            manifest["unexpected"] = True
            _write_manifest(extra_source, manifest)
            self.assert_rejected(extra_source, parent / "extra.zip", "unsupported members: unexpected")

    def test_invalid_types_versions_and_required_capability_are_rejected(self) -> None:
        cases = (
            ("boolean-version", lambda manifest: manifest.__setitem__("manifestVersion", True), "manifestVersion"),
            ("engine-version", lambda manifest: manifest.__setitem__("engineCompatibility", 2), "engineCompatibility"),
            ("renderer-version", lambda manifest: manifest.__setitem__("rendererPolicyVersion", 2), "rendererPolicyVersion"),
            ("capability", lambda manifest: manifest.__setitem__("requiredCapabilities", ["unknown.capability"]), "unsupported required capability"),
        )
        with tempfile.TemporaryDirectory() as temporary:
            parent = Path(temporary)
            for name, mutate, reason in cases:
                with self.subTest(name=name):
                    source = _copy_source(parent, name)
                    manifest = _load_manifest(source)
                    mutate(manifest)
                    _write_manifest(source, manifest)
                    self.assert_rejected(source, parent / f"{name}.zip", reason)

    def test_noncanonical_and_missing_asset_paths_are_rejected(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            parent = Path(temporary)

            noncanonical_source = _copy_source(parent, "noncanonical")
            manifest = _load_manifest(noncanonical_source)
            manifest["assetReferences"][0]["path"] = "assets/first-city/canopy/../canopy/baseline.png"
            _write_manifest(noncanonical_source, manifest)
            self.assert_rejected(noncanonical_source, parent / "noncanonical.zip", "parent segment")

            missing_source = _copy_source(parent, "missing")
            manifest = _load_manifest(missing_source)
            manifest["assetReferences"][0]["path"] = "assets/first-city/canopy/missing.png"
            _write_manifest(missing_source, manifest)
            self.assert_rejected(missing_source, parent / "missing.zip", "referenced asset is missing")

    def test_symlinked_asset_and_unsupported_media_type_are_rejected(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            parent = Path(temporary)

            symlink_source = _copy_source(parent, "symlink")
            asset = symlink_source / ASSET_PATH
            target = parent / "outside.png"
            target.write_bytes(asset.read_bytes())
            asset.unlink()
            asset.symlink_to(target)
            self.assert_rejected(symlink_source, parent / "symlink.zip", "symlink source entries are rejected")

            unsupported_source = _copy_source(parent, "unsupported")
            manifest = _load_manifest(unsupported_source)
            manifest["assetReferences"][0]["mediaType"] = "image/jpeg"
            _write_manifest(unsupported_source, manifest)
            self.assert_rejected(unsupported_source, parent / "unsupported.zip", "unsupported asset mediaType")

    def test_digest_mismatch_and_undeclared_or_executable_files_are_rejected(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            parent = Path(temporary)

            digest_source = _copy_source(parent, "digest")
            manifest = _load_manifest(digest_source)
            manifest["assetReferences"][0]["sha256"] = "0" * 64
            _write_manifest(digest_source, manifest)
            self.assert_rejected(digest_source, parent / "digest.zip", "SHA-256 does not match")

            extra_source = _copy_source(parent, "extra-file")
            (extra_source / "notes.txt").write_text("extra", encoding="utf-8")
            self.assert_rejected(extra_source, parent / "extra-file.zip", "undeclared files: notes.txt")

            executable_source = _copy_source(parent, "executable")
            executable = executable_source / "run.sh"
            executable.write_text("#!/bin/sh\n", encoding="utf-8")
            executable.chmod(0o755)
            self.assert_rejected(executable_source, parent / "executable.zip", "run.sh: executable source files are rejected")

    def test_output_inside_source_root_is_rejected(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            source = _copy_source(Path(temporary))
            output = source / "pack.zip"
            self.assert_rejected(source, output, "output must be outside the source root")


if __name__ == "__main__":
    unittest.main()
