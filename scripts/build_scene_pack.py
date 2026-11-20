#!/usr/bin/env python3
"""Build a local, data-only ZIP from an asset-bearing scene-pack manifest."""

import argparse
import hashlib
import json
import os
import re
import stat
import sys
import tempfile
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_SOURCE = ROOT / "docs/scene-pack-examples/first-city-canopy-baseline"
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
SUPPORTED_CAPABILITY = "first-city.canopy.image-png"
CONTENT_IDENTITY_DOMAIN = b"oxygen-scene-pack-content-v1\0"


class BuildError(ValueError):
    """A source or output does not satisfy the local builder contract."""


def _unique_object(pairs: list[tuple[str, object]]) -> dict[str, object]:
    result: dict[str, object] = {}
    for key, value in pairs:
        if key in result:
            raise BuildError(f"duplicate JSON object member: {key}")
        result[key] = value
    return result


def _reject_json_constant(value: str) -> None:
    raise BuildError(f"invalid JSON numeric constant: {value}")


def _parse_manifest(raw: bytes) -> dict[str, object]:
    try:
        text = raw.decode("utf-8")
        manifest = json.loads(
            text,
            object_pairs_hook=_unique_object,
            parse_constant=_reject_json_constant,
        )
    except UnicodeDecodeError as exc:
        raise BuildError(f"manifest.json: invalid UTF-8: {exc}") from exc
    except json.JSONDecodeError as exc:
        raise BuildError(f"manifest.json: invalid JSON at line {exc.lineno}, column {exc.colno}: {exc.msg}") from exc
    except BuildError as exc:
        raise BuildError(f"manifest.json: {exc}") from exc

    if not isinstance(manifest, dict):
        raise BuildError("manifest.json: top-level value must be a JSON object")
    if set(manifest) != MANIFEST_MEMBERS:
        missing = sorted(MANIFEST_MEMBERS - set(manifest))
        extra = sorted(set(manifest) - MANIFEST_MEMBERS)
        details = []
        if missing:
            details.append("missing members: " + ", ".join(missing))
        if extra:
            details.append("unsupported members: " + ", ".join(extra))
        raise BuildError("manifest.json: expected exactly seven members (" + "; ".join(details) + ")")

    pack_id = manifest["packId"]
    if not isinstance(pack_id, str) or re.fullmatch(r"[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?", pack_id) is None:
        raise BuildError("manifest.json: packId must follow the lowercase OX-20 identifier grammar")

    if type(manifest["manifestVersion"]) is not int or manifest["manifestVersion"] != 2:
        raise BuildError("manifest.json: manifestVersion must be JSON integer 2")
    if manifest["worldDefinitionRef"] != "first-city" or not isinstance(manifest["worldDefinitionRef"], str):
        raise BuildError("manifest.json: worldDefinitionRef must be the string first-city")
    if type(manifest["engineCompatibility"]) is not int or manifest["engineCompatibility"] != 1:
        raise BuildError("manifest.json: engineCompatibility must be JSON integer 1")
    if type(manifest["rendererPolicyVersion"]) is not int or manifest["rendererPolicyVersion"] != 1:
        raise BuildError("manifest.json: rendererPolicyVersion must be JSON integer 1")

    capabilities = manifest["requiredCapabilities"]
    if not isinstance(capabilities, list) or any(not isinstance(item, str) for item in capabilities):
        raise BuildError("manifest.json: requiredCapabilities must be an array of strings")
    if capabilities != [SUPPORTED_CAPABILITY]:
        raise BuildError("manifest.json: unsupported required capability declaration")

    references = manifest["assetReferences"]
    if not isinstance(references, list) or len(references) != 1:
        raise BuildError("manifest.json: assetReferences must contain exactly one asset reference")
    reference = references[0]
    if not isinstance(reference, dict) or set(reference) != ASSET_REFERENCE_MEMBERS:
        raise BuildError("manifest.json: asset reference must have exactly refId, path, mediaType, and sha256")
    if any(not isinstance(value, str) for value in reference.values()):
        raise BuildError("manifest.json: all asset-reference members must be strings")
    if not reference["refId"].strip():
        raise BuildError("manifest.json: asset refId must be non-empty")
    if reference["mediaType"] != "image/png":
        raise BuildError("manifest.json: unsupported asset mediaType; expected image/png")

    digest = reference["sha256"]
    if re.fullmatch(r"[0-9a-f]{64}", digest) is None:
        raise BuildError("manifest.json: asset sha256 must be lowercase 64-character hexadecimal")

    relative_path = reference["path"]
    if not relative_path or relative_path.startswith("/"):
        raise BuildError("manifest.json: asset path must be a non-empty relative path")
    if "\\" in relative_path:
        raise BuildError("manifest.json: backslashes are not canonical asset path separators")
    if re.match(r"^[A-Za-z]:", relative_path) or re.match(r"^[A-Za-z][A-Za-z0-9+.-]*:", relative_path):
        raise BuildError("manifest.json: drive-form and URI-form asset paths are rejected")
    segments = relative_path.split("/")
    if any(segment in {"", ".", ".."} for segment in segments):
        raise BuildError("manifest.json: asset path contains an empty, dot, or parent segment")
    if relative_path == "manifest.json":
        raise BuildError("manifest.json: asset path cannot replace the manifest")

    return manifest


def _source_files(source_root: Path) -> set[str]:
    files: set[str] = set()

    def visit(directory: Path) -> None:
        try:
            entries = sorted(os.scandir(directory), key=lambda entry: entry.name)
        except OSError as exc:
            raise BuildError(f"{directory}: cannot inspect source directory: {exc}") from exc

        for entry in entries:
            path = Path(entry.path)
            relative = path.relative_to(source_root).as_posix()
            if entry.is_symlink():
                raise BuildError(f"{relative}: symlink source entries are rejected")
            try:
                if entry.is_dir(follow_symlinks=False):
                    visit(path)
                    continue
                if not entry.is_file(follow_symlinks=False):
                    raise BuildError(f"{relative}: source entry must be a regular file or directory")
                mode = entry.stat(follow_symlinks=False).st_mode
            except OSError as exc:
                raise BuildError(f"{relative}: cannot inspect source entry: {exc}") from exc
            if mode & (stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH):
                raise BuildError(f"{relative}: executable source files are rejected")
            files.add(relative)

    visit(source_root)
    return files


def _validate_source(source: Path) -> tuple[Path, bytes, bytes, int]:
    if source.is_symlink():
        raise BuildError(f"{source}: source root cannot be a symlink")
    try:
        source_root = source.resolve(strict=True)
    except OSError as exc:
        raise BuildError(f"{source}: source root cannot be resolved: {exc}") from exc
    if not source_root.is_dir():
        raise BuildError(f"{source}: source root must be a directory")

    source_files = _source_files(source_root)
    if "manifest.json" not in source_files:
        raise BuildError(f"{source_root / 'manifest.json'}: required manifest is missing")

    manifest_path = source_root / "manifest.json"
    try:
        manifest_bytes = manifest_path.read_bytes()
    except OSError as exc:
        raise BuildError(f"{manifest_path}: cannot read manifest: {exc}") from exc
    manifest = _parse_manifest(manifest_bytes)
    reference = manifest["assetReferences"][0]
    asset_name = reference["path"]
    asset_path = source_root.joinpath(*asset_name.split("/"))

    cursor = source_root
    for segment in asset_name.split("/"):
        cursor = cursor / segment
        if cursor.is_symlink():
            raise BuildError(f"{asset_name}: symlink asset path entries are rejected")
    try:
        resolved_asset = asset_path.resolve(strict=True)
    except OSError as exc:
        raise BuildError(f"{asset_name}: referenced asset is missing or cannot be resolved: {exc}") from exc
    try:
        resolved_asset.relative_to(source_root)
    except ValueError as exc:
        raise BuildError(f"{asset_name}: resolved asset escapes the source root") from exc
    if not resolved_asset.is_file():
        raise BuildError(f"{asset_name}: referenced asset must be a regular file")
    try:
        asset_bytes = resolved_asset.read_bytes()
    except OSError as exc:
        raise BuildError(f"{asset_name}: cannot read referenced asset: {exc}") from exc

    actual_digest = hashlib.sha256(asset_bytes).hexdigest()
    if actual_digest != reference["sha256"]:
        raise BuildError(f"{asset_name}: SHA-256 does not match the exact asset bytes")

    expected_files = {"manifest.json", asset_name}
    if source_files != expected_files:
        unexpected = sorted(source_files - expected_files)
        missing = sorted(expected_files - source_files)
        details = []
        if unexpected:
            details.append("undeclared files: " + ", ".join(unexpected))
        if missing:
            details.append("missing files: " + ", ".join(missing))
        raise BuildError(f"{source_root}: source inventory must contain only manifest.json and its declared PNG ({'; '.join(details)})")

    return source_root, manifest_bytes, asset_bytes, manifest["manifestVersion"]


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


def _is_within(path: Path, parent: Path) -> bool:
    try:
        path.relative_to(parent)
        return True
    except ValueError:
        return False


def build_pack(source: Path, output: Path) -> tuple[Path, int, str]:
    source_root, manifest_bytes, asset_bytes, manifest_version = _validate_source(source.expanduser())
    output_path = output.expanduser().resolve(strict=False)
    if _is_within(output_path, source_root):
        raise BuildError(f"{output_path}: output must be outside the source root")

    reference = _parse_manifest(manifest_bytes)["assetReferences"][0]
    asset_name = reference["path"]
    payload_members = {"manifest.json": manifest_bytes, asset_name: asset_bytes}
    content_identity = _canonical_content_identity(payload_members)
    try:
        output_path.parent.mkdir(parents=True, exist_ok=True)
    except OSError as exc:
        raise BuildError(f"{output_path.parent}: cannot create output directory: {exc}") from exc

    temporary_path: Path | None = None
    try:
        with tempfile.NamedTemporaryFile(
            mode="wb", prefix=output_path.name + ".", suffix=".tmp", dir=output_path.parent, delete=False
        ) as temporary_file:
            temporary_path = Path(temporary_file.name)
        with zipfile.ZipFile(temporary_path, "w", compression=zipfile.ZIP_STORED) as archive:
            for name, payload in payload_members.items():
                entry = zipfile.ZipInfo(name)
                entry.compress_type = zipfile.ZIP_STORED
                entry.create_system = 3
                entry.external_attr = (stat.S_IFREG | 0o644) << 16
                archive.writestr(entry, payload)
        os.replace(temporary_path, output_path)
        temporary_path = None
    except OSError as exc:
        raise BuildError(f"{output_path}: cannot write pack artifact: {exc}") from exc
    finally:
        if temporary_path is not None:
            try:
                temporary_path.unlink(missing_ok=True)
            except OSError:
                pass

    return output_path, manifest_version, content_identity


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Build a local data-only ZIP scene pack from a validated manifest v2 source.")
    parser.add_argument("--source", type=Path, default=DEFAULT_SOURCE, help="source pack root (default: committed First City baseline example)")
    parser.add_argument("--output", type=Path, required=True, help="ZIP artifact path outside the source root")
    args = parser.parse_args(argv)

    try:
        artifact, manifest_version, content_identity = build_pack(args.source, args.output)
    except BuildError as exc:
        print(f"Rejected: {exc}", file=sys.stderr)
        return 2

    print(f"Built scene pack: {artifact} (manifestVersion={manifest_version}, contentIdentity={content_identity})")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
