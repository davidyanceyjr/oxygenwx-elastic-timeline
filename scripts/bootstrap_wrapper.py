#!/usr/bin/env python3
"""Download the exact upstream Gradle wrapper jar with git-blob SHA verification.

The source archive omits the binary wrapper JAR; first use populates it from a
commit-pinned URL, not the mutable branch head.
"""
import hashlib
import pathlib
import sys
import tempfile
import urllib.request

ROOT = pathlib.Path(__file__).resolve().parents[1]
TARGET = ROOT / "gradle" / "wrapper" / "gradle-wrapper.jar"
UPSTREAM_SHA = "ec3f93976f037fbb206089d8b9d9fa1f6f93918b"
EXPECTED_BLOB = "e6441136f3d4ba8a0da8d277868979cfbc8ad796"
URL = (
    "https://raw.githubusercontent.com/davidyanceyjr/oxygenWX/"
    f"{UPSTREAM_SHA}/gradle/wrapper/gradle-wrapper.jar"
)


def blob_hash(data: bytes) -> str:
    return hashlib.sha1(f"blob {len(data)}\0".encode() + data).hexdigest()


def main() -> int:
    if TARGET.is_file() and blob_hash(TARGET.read_bytes()) == EXPECTED_BLOB:
        return 0
    try:
        with urllib.request.urlopen(URL, timeout=25) as response:
            data = response.read()
        if blob_hash(data) != EXPECTED_BLOB:
            raise ValueError("Gradle wrapper JAR failed source-blob verification")
        TARGET.parent.mkdir(parents=True, exist_ok=True)
        with tempfile.NamedTemporaryFile(dir=TARGET.parent, delete=False) as handle:
            handle.write(data)
            staged = pathlib.Path(handle.name)
        staged.replace(TARGET)
        print("Verified and installed pinned Gradle wrapper JAR", file=sys.stderr)
        return 0
    except Exception as exc:
        print(f"Wrapper bootstrap failed: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
