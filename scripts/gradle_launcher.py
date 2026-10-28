#!/usr/bin/env python3
"""Launch the pinned Gradle wrapper with a compatible installed JDK."""
import os
from pathlib import Path
import re
import shutil
import subprocess
import sys


ROOT = Path(__file__).resolve().parents[1]


def java_major(executable: Path) -> int | None:
    try:
        result = subprocess.run(
            [str(executable), "-version"], capture_output=True, text=True, timeout=10
        )
    except (OSError, subprocess.TimeoutExpired):
        return None
    match = re.search(r'version "(?:1\.)?(\d+)', result.stderr + result.stdout)
    return int(match.group(1)) if match else None


def compatible_java() -> Path:
    suffix = "java.exe" if os.name == "nt" else "java"
    candidates = []
    if os.environ.get("JAVA_HOME"):
        candidates.append(Path(os.environ["JAVA_HOME"]) / "bin" / suffix)
    on_path = shutil.which("java")
    if on_path:
        candidates.append(Path(on_path))
    candidates.extend(sorted((Path.home() / "Android").glob("jdk-*/bin/" + suffix), reverse=True))
    candidates.extend(sorted(Path("/usr/lib/jvm").glob("*/bin/" + suffix), reverse=True))
    for candidate in candidates:
        major = java_major(candidate)
        if major is not None and major >= 17:
            return candidate
    raise RuntimeError("Gradle requires JDK 17 or newer. Set JAVA_HOME to a compatible JDK.")


def main() -> int:
    java = compatible_java()
    env = os.environ.copy()
    env["JAVA_HOME"] = str(java.parent.parent)
    env["PATH"] = str(java.parent) + os.pathsep + env.get("PATH", "")
    command = [str(java), "-Dorg.gradle.appname=gradlew", "-classpath",
               str(ROOT / "gradle/wrapper/gradle-wrapper.jar"),
               "org.gradle.wrapper.GradleWrapperMain", *sys.argv[1:]]
    return subprocess.call(command, cwd=ROOT, env=env)


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except RuntimeError as exc:
        print(f"Error: {exc}", file=sys.stderr)
        raise SystemExit(1)
