# Cycle 048-p2-hourly-scrub-responsiveness

Date: 2026-10-27
Roadmap: P.2.16

## Outcome

Implemented continuous hourly scrubbing by preserving fractional pointer deltas and fixing hourly drag sensitivity at the uniform precision scale. Three matched installed Pixel 3 XL trials moved from Sunday 12 PM to 3 PM on 200 px leftward strokes and returned to 12 PM on equal rightward strokes. Hold entry, sticky precision, main/timeline tap behavior, fixture bounds, forecast values, and readability remained intact. Evidence and limits are recorded under .codex/test-artifacts/048-p2-hourly-scrub-responsiveness/.

## Verification actually performed

PASS: python3 scripts/dev.py workflow before activation and after implementation. PASS: python3 scripts/dev.py contract. PASS: JAVA_HOME=/home/opsman/Android/jdk-17 ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/Android/Sdk/platform-tools:/home/opsman/.codex/packages/app-server-daemon/releases/local-fcaec97e0ad7f104efe9c188d67010b3bb13e9470518b523a587490b47307eaa-x86_64-unknown-linux-gnu/codex-path:/home/opsman/.nix-profile/bin:/nix/var/nix/profiles/default/bin:/home/opsman/.codex/tmp/arg0/codex-arg0yDMQcb:/home/opsman/.codex/packages/app-server-daemon/releases/local-fcaec97e0ad7f104efe9c188d67010b3bb13e9470518b523a587490b47307eaa-x86_64-unknown-linux-gnu/codex-path:/home/opsman/.codex/tmp/arg0-linux/codex-arg00b6vxT:/usr/lib/openai-codex/codex-path:/home/opsman/go/bin:/home/opsman/bin:/home/opsman/scripts:/home/opsman/.fzf/bin:/home/opsman/.local/bin:/home/opsman/.cabal/bin:/home/opsman/.cargo/bin:/nix:/home/opsman/.nix-profile/bin:/usr/local/sbin:/usr/local/bin:/usr/bin:/opt/cuda/bin:/opt/cuda/nsight_compute:/opt/cuda/nsight_systems/bin:/opt/ActiveTcl-8.6/bin:/opt/ActiveTcl-8.6/man:/usr/bin/site_perl:/usr/bin/vendor_perl:/usr/bin/core_perl:/home/opsman/.ghcup/bin:/var/lib/flatpak/exports/bin:/usr/lib/jvm/default/bin:/usr/lib/rustup/bin:/home/opsman/.local/bin:/home/opsman/.cabal/bin:/home/opsman/.cargo/bin python3 scripts/dev.py run --serial 8AKY0M32X (debug build, install, launch). PASS: installed Pixel 3 XL matched directional scrub trials, entry/settled scale movement, tap exits/re-entry, and earliest/latest fixture bounds with screenshots. PASS: git diff --check. NOT RUN: tests, lint, TalkBack, RTL/reduced-motion device checks, large-font/landscape, and frame-time profiling; limits are recorded in cycle evidence.

## Follow-up

The bounded P.2.16 exit is met for the installed Pixel 3 XL portrait/default-font state. No further scrub correction was indicated by the matched trials. Human tactile latency and frame time were not measured. TalkBack, RTL, reduced-motion, large-font, and landscape acceptance remain outside this cycle.
