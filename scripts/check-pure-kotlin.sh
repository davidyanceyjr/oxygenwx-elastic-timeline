#!/usr/bin/env sh
# Optional SDK-free regression test on a host with Kotlin CLI installed.
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
TMP_DIR=$(mktemp -d)
trap 'rm -rf "$TMP_DIR"' EXIT
SRC="$ROOT/app/src/main/java/com/oxygen/weather/timeline"
kotlinc "$SRC/data/SampleForecast.kt" "$SRC/presentation/ElasticTimeAxis.kt" \
    "$SRC/presentation/ForecastProjection.kt" "$ROOT/scripts/PureCoreSmoke.kt" \
    -include-runtime -d "$TMP_DIR/smoke.jar"
java -jar "$TMP_DIR/smoke.jar"
