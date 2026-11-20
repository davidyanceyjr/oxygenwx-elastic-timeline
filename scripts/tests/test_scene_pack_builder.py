"""Focused acceptance checks for the local builder and OX-54 content identity."""

import hashlib
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
OLIVE = ROOT / "docs/scene-pack-examples/first-city-canopy-olive"
ASSET_PATH = "assets/first-city/canopy/baseline.png"
CONTENT_IDENTITY_DOMAIN = b"oxygen-scene-pack-content-v1\0"


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


def _canonical_content_identity(members: dict[str, bytes]) -> str:
    digest = hashlib.sha256()
    digest.update(CONTENT_IDENTITY_DOMAIN)
    digest.update(len(members).to_bytes(4, "big"))
    for member_path, payload in sorted(members.items(), key=lambda member: member[0].encode("utf-8")):
        path_bytes = member_path.encode("utf-8")
        digest.update(len(path_bytes).to_bytes(4, "big"))
        digest.update(path_bytes)
        digest.update(len(payload).to_bytes(8, "big"))
        digest.update(payload)
    return f"sha256:{digest.hexdigest()}"


def _source_payload_members(source: Path) -> dict[str, bytes]:
    manifest_bytes = (source / "manifest.json").read_bytes()
    manifest = json.loads(manifest_bytes.decode("utf-8"))
    asset_name = manifest["assetReferences"][0]["path"]
    return {"manifest.json": manifest_bytes, asset_name: (source / asset_name).read_bytes()}


def _reported_identity(output: str) -> str:
    marker = "contentIdentity="
    start = output.find(marker)
    if start < 0:
        raise AssertionError(f"builder output lacks {marker!r}: {output}")
    return output[start + len(marker):].split(")", 1)[0]


class ScenePackBuilderTests(unittest.TestCase):
    def assert_rejected(self, source: Path, output: Path, reason: str) -> None:
        result = _run_builder(source, output)
        self.assertNotEqual(result.returncode, 0, result.stdout)
        self.assertIn("Rejected:", result.stderr)
        self.assertIn(reason, result.stderr)
        self.assertNotIn("Built scene pack:", result.stdout + result.stderr)
        self.assertFalse(output.exists(), result.stderr)

    def test_baseline_builds_preserve_exact_payload_bytes_report_same_identity_and_have_no_executable_entries(self) -> None:
        manifest_bytes = (BASELINE / "manifest.json").read_bytes()
        asset_bytes = (BASELINE / ASSET_PATH).read_bytes()
        expected_identity = _canonical_content_identity(_source_payload_members(BASELINE))
        with tempfile.TemporaryDirectory() as temporary:
            reported = []
            for index in (1, 2):
                output = Path(temporary) / f"first-city-baseline-v2-build-{index}.zip"
                result = _run_builder(BASELINE, output)

                self.assertEqual(result.returncode, 0, result.stderr)
                self.assertIn(str(output.resolve()), result.stdout)
                self.assertIn("manifestVersion=2", result.stdout)
                self.assertTrue(output.is_file())
                identity = _reported_identity(result.stdout)
                self.assertEqual(identity, expected_identity)
                reported.append(identity)
                with zipfile.ZipFile(output) as archive:
                    self.assertEqual(set(archive.namelist()), {"manifest.json", ASSET_PATH})
                    self.assertEqual(archive.read("manifest.json"), manifest_bytes)
                    self.assertEqual(archive.read(ASSET_PATH), asset_bytes)
                    for info in archive.infolist():
                        self.assertEqual(info.compress_type, zipfile.ZIP_STORED)
                        mode = info.external_attr >> 16
                        self.assertEqual(mode & 0o111, 0)
            self.assertEqual(reported[0], reported[1])

    def test_canonical_content_identity_fixed_vector_and_path_order_stability(self) -> None:
        members = {"b": b"xyz", "a": b"\x00"}
        expected = "sha256:c53850c96180989835f51f091ef841a2e2104ff9b26f132ad6b7cb421dfd1ca5"
        self.assertEqual(_canonical_content_identity(members), expected)
        self.assertEqual(_canonical_content_identity(dict(reversed(tuple(members.items())))), expected)

    def test_canonical_content_identity_is_sensitive_to_member_path_and_exact_bytes(self) -> None:
        members = {"manifest.json": b"{}", "assets/canopy.png": b"png-bytes"}
        identity = _canonical_content_identity(members)
        changed_bytes = {**members, "assets/canopy.png": b"png-byteS"}
        changed_path = {"manifest.json": b"{}", "assets/canopy-new.png": b"png-bytes"}
        self.assertNotEqual(_canonical_content_identity(changed_bytes), identity)
        self.assertNotEqual(_canonical_content_identity(changed_path), identity)

    def test_olive_variant_reports_distinct_identity(self) -> None:
        baseline_members = _source_payload_members(BASELINE)
        olive_members = _source_payload_members(OLIVE)
        self.assertNotEqual(_canonical_content_identity(baseline_members), _canonical_content_identity(olive_members))
        with tempfile.TemporaryDirectory() as temporary:
            baseline = _run_builder(BASELINE, Path(temporary) / "baseline.zip")
            olive = _run_builder(OLIVE, Path(temporary) / "olive.zip")
            self.assertEqual(baseline.returncode, 0, baseline.stderr)
            self.assertEqual(olive.returncode, 0, olive.stderr)
            self.assertEqual(_reported_identity(baseline.stdout), _canonical_content_identity(baseline_members))
            self.assertEqual(_reported_identity(olive.stdout), _canonical_content_identity(olive_members))
            self.assertNotEqual(_reported_identity(baseline.stdout), _reported_identity(olive.stdout))

    def test_archive_metadata_is_outside_builder_content_identity(self) -> None:
        members = _source_payload_members(BASELINE)
        expected_identity = _canonical_content_identity(members)
        with tempfile.TemporaryDirectory() as temporary:
            output = Path(temporary) / "baseline.zip"
            metadata_variant = Path(temporary) / "baseline-metadata-variant.zip"
            result = _run_builder(BASELINE, output)
            self.assertEqual(result.returncode, 0, result.stderr)
            reported_identity = _reported_identity(result.stdout)
            self.assertEqual(reported_identity, expected_identity)

            with zipfile.ZipFile(output) as original, zipfile.ZipFile(metadata_variant, "w") as changed:
                for info in original.infolist():
                    updated = zipfile.ZipInfo(info.filename, date_time=(2025, 6, 7, 8, 9, 10))
                    updated.compress_type = zipfile.ZIP_DEFLATED
                    changed.writestr(updated, original.read(info.filename))

            with zipfile.ZipFile(output) as original, zipfile.ZipFile(metadata_variant) as changed:
                original_members = {info.filename: original.read(info.filename) for info in original.infolist()}
                changed_members = {info.filename: changed.read(info.filename) for info in changed.infolist()}
                self.assertEqual(original_members, changed_members)
                self.assertNotEqual(original.infolist()[0].date_time, changed.infolist()[0].date_time)
                self.assertNotEqual(original.infolist()[0].compress_type, changed.infolist()[0].compress_type)
                self.assertEqual(_canonical_content_identity(changed_members), reported_identity)

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
