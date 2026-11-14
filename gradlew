#!/bin/sh
# Oxygen Time Lab launcher for the wrapper JAR tracked in this repository.
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if ! command -v python3 >/dev/null 2>&1; then
  echo "Python 3 is required to launch the Gradle wrapper." >&2
  exit 1
fi
exec python3 "$APP_HOME/scripts/gradle_launcher.py" "$@"
