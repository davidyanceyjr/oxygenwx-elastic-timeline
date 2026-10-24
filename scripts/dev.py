#!/usr/bin/env python3
"""Oxygen-style cross-platform developer entry point for the isolated UI prototype."""
import argparse
import os
import pathlib
import shutil
import subprocess
import sys

ROOT = pathlib.Path(__file__).resolve().parents[1]
PACKAGE = "com.oxygen.weather.timeline"


def gradle(*tasks: str) -> None:
    exe = ROOT / ("gradlew.bat" if os.name == "nt" else "gradlew")
    subprocess.run([str(exe), *tasks], cwd=ROOT, check=True)


def workflow() -> None:
    required = [
        "AGENTS.md", "docs/SPECIFICATION.md", "docs/ROADMAP.md",
        "docs/UI_DEVELOPMENT_WORKFLOW.md", ".codex/current.md",
        ".codex/README.md", "app/build.gradle.kts",
        "app/src/main/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxis.kt",
    ]
    absent = [name for name in required if not (ROOT / name).is_file()]
    if absent:
        raise RuntimeError("Incomplete prototype scaffold: " + ", ".join(absent))
    print("Workflow scaffold: OK")
    print((ROOT / ".codex/current.md").read_text(encoding="utf-8").strip())


def contract() -> None:
    manifest = (ROOT / "app/src/main/AndroidManifest.xml").read_text()
    build = (ROOT / "app/build.gradle.kts").read_text()
    projection = (ROOT / "app/src/main/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxis.kt").read_text()
    assert 'applicationId = "com.oxygen.weather.timeline"' in build
    assert "android.permission.INTERNET" not in manifest
    assert "ACCESS_COARSE_LOCATION" not in manifest
    assert "fun xForHour" in projection and "fun scrub" in projection
    print("Prototype contract: isolated package, no network/location permission, pure time projection — OK")


def adb(*args: str) -> None:
    executable = shutil.which("adb")
    if not executable:
        raise RuntimeError("adb is not installed/on PATH")
    subprocess.run([executable, *args], cwd=ROOT, check=True)


def main() -> None:
    parser = argparse.ArgumentParser(description="Oxygen Time Lab development workflow")
    parser.add_argument("command", choices=[
        "workflow", "contract", "build", "test", "lint", "check", "run", "screenshot"
    ])
    parser.add_argument("--serial", help="Device/emulator serial for adb")
    parser.add_argument("--output", help="Screenshot destination path")
    args = parser.parse_args()
    if args.command == "workflow":
        workflow()
    elif args.command == "contract":
        contract()
    elif args.command == "build":
        gradle(":app:assembleDebug")
    elif args.command == "test":
        gradle(":app:testDebugUnitTest")
    elif args.command == "lint":
        gradle(":app:lintDebug")
    elif args.command == "check":
        workflow()
        contract()
        gradle(":app:testDebugUnitTest", ":app:lintDebug", ":app:assembleDebug")
    elif args.command == "run":
        gradle(":app:assembleDebug")
        device = ["-s", args.serial] if args.serial else []
        apk = ROOT / "app/build/outputs/apk/debug/app-debug.apk"
        adb(*device, "install", "-r", str(apk))
        adb(*device, "shell", "am", "start", "-n", f"{PACKAGE}/.MainActivity")
    elif args.command == "screenshot":
        if not args.output:
            parser.error("screenshot requires --output")
        dest = ROOT / args.output
        dest.parent.mkdir(parents=True, exist_ok=True)
        device = ["-s", args.serial] if args.serial else []
        exe = shutil.which("adb")
        if not exe:
            raise RuntimeError("adb not found")
        with dest.open("wb") as stream:
            subprocess.run([exe, *device, "exec-out", "screencap", "-p"],
                           stdout=stream, check=True)
        print(dest)


if __name__ == "__main__":
    try:
        main()
    except (RuntimeError, subprocess.CalledProcessError) as exc:
        print(f"Error: {exc}", file=sys.stderr)
        raise SystemExit(1)
