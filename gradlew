#!/bin/sh
# Oxygen Time Lab bootstrap launcher; pinned original Gradle wrapper JAR is fetched on first use.
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if ! command -v python3 >/dev/null 2>&1; then
  echo "Python 3 is required to bootstrap the pinned Gradle wrapper JAR." >&2
  exit 1
fi
python3 "$APP_HOME/scripts/bootstrap_wrapper.py" || exit $?
exec python3 "$APP_HOME/scripts/gradle_launcher.py" "$@"
