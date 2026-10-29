#!/usr/bin/env python3
"""Oxygen-style cross-platform developer entry point for the isolated UI prototype."""
import argparse
import os
import pathlib
import shutil
import subprocess
import sys
import xml.etree.ElementTree as ET

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
    build = (ROOT / "app/build.gradle.kts").read_text()
    projection = (ROOT / "app/src/main/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxis.kt").read_text()
    transport = (ROOT / "app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoTransport.kt").read_text()
    assert 'applicationId = "com.oxygen.weather.timeline"' in build
    assert "fun xForHour" in projection and "fun scrub" in projection
    assert 'https://api.open-meteo.com/v1/forecast' in transport
    assert 'followRedirects(false)' in transport and 'followSslRedirects(false)' in transport
    assert 'newCall(request(location))' in transport

    android = "{http://schemas.android.com/apk/res/android}"
    manifests = [ROOT / "app/src/main/AndroidManifest.xml", ROOT / "app/src/debug/AndroidManifest.xml"]
    manifests += sorted((ROOT / "app/build/intermediates/merged_manifests").glob("*/process*Manifest/AndroidManifest.xml"))
    manifests += sorted((ROOT / "app/build/intermediates/merged_manifest").glob("*/process*Manifest/AndroidManifest.xml"))
    for path in manifests:
        tree = ET.parse(path)
        root = tree.getroot()
        permissions = {node.get(android + "name") for node in root if node.tag.startswith("uses-permission")}
        baseline_permission = PACKAGE + ".DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION"
        allowed = {"android.permission.INTERNET"}
        if "build/intermediates" in str(path):
            allowed.add(baseline_permission)
        assert permissions <= allowed, (path, permissions)
        if path.name == "AndroidManifest.xml" and ("src/main" in str(path) or "build/intermediates" in str(path)):
            assert "android.permission.INTERNET" in permissions, path
        app = root.find("application")
        assert app is not None
        assert app.get(android + "usesCleartextTraffic") != "true", path
        assert app.get(android + "networkSecurityConfig") is None, path
        baseline_components = {
            ("provider", "androidx.startup.InitializationProvider"),
            ("receiver", "androidx.profileinstaller.ProfileInstallReceiver"),
            ("service", "androidx.room.MultiInstanceInvalidationService"),
        } if "build/intermediates" in str(path) else set()
        allowed_activities = {".MainActivity", PACKAGE + ".MainActivity"}
        if "debug" in str(path):
            allowed_activities |= {
                ".data.debug.DebugForecastActivity",
                PACKAGE + ".data.debug.DebugForecastActivity",
                ".data.debug.DebugCatalogActivity",
                PACKAGE + ".data.debug.DebugCatalogActivity",
                "androidx.activity.ComponentActivity",
                "androidx.compose.ui.tooling.PreviewActivity",
            }
        for node in app:
            if node.tag in {"service", "receiver", "provider"}:
                assert (node.tag, node.get(android + "name")) in baseline_components, (path, node.tag, node.get(android + "name"))
            if node.tag == "activity":
                assert node.get(android + "name") in allowed_activities, (path, node.get(android + "name"))
            if "release" in str(path):
                assert "debug" not in (node.get(android + "name") or "").lower(), path
        if "release" in str(path):
            assert all("debug" not in (node.get(android + "name") or "").lower() for node in app), path
    print("Transport contract: isolated package, approved Internet only, no location/background/cleartext/release debug exposure, pure projection — OK")


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
