# Cycle 046-p2-hourly-mode-retention

Date: 2026-10-27
Roadmap: P.2.15

## Outcome

Implemented persistent hourly precision after hold-and-drag release. A main forecast-area tap returns to daily mode while preserving the selected time; a timeline tap returns to daily mode at the tapped hour. Hour-level dragging remains available after release, and double-tap-to-now remains functional. Installed interaction captures and limits are recorded under .codex/test-artifacts/046-p2-hourly-mode-retention/. The UI artwork requirement was waived by the owner for P.2.15 only. The scale animation timing and changing sensitivity during its transition were outside this slice.

## Verification actually performed

PASS: python scripts/dev.py workflow before activation, after activation, and after implementation; python scripts/dev.py contract; ANDROID_HOME=/home/opsman/Android/Sdk JAVA_HOME=/home/opsman/Android/jdk-17 PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/.codex/packages/app-server-daemon/releases/local-fcaec97e0ad7f104efe9c188d67010b3bb13e9470518b523a587490b47307eaa-x86_64-unknown-linux-gnu/codex-path:/home/opsman/.nix-profile/bin:/nix/var/nix/profiles/default/bin:/home/opsman/.codex/tmp/arg0/codex-arg0yDMQcb:/home/opsman/.codex/packages/app-server-daemon/releases/local-fcaec97e0ad7f104efe9c188d67010b3bb13e9470518b523a587490b47307eaa-x86_64-unknown-linux-gnu/codex-path:/home/opsman/.codex/tmp/arg0-linux/codex-arg00b6vxT:/usr/lib/openai-codex/codex-path:/home/opsman/go/bin:/home/opsman/bin:/home/opsman/scripts:/home/opsman/.fzf/bin:/home/opsman/.local/bin:/home/opsman/.cabal/bin:/home/opsman/.cargo/bin:/nix:/home/opsman/.nix-profile/bin:/usr/local/sbin:/usr/local/bin:/usr/bin:/opt/cuda/bin:/opt/cuda/nsight_compute:/opt/cuda/nsight_systems/bin:/opt/ActiveTcl-8.6/bin:/opt/ActiveTcl-8.6/man:/usr/bin/site_perl:/usr/bin/vendor_perl:/usr/bin/core_perl:/home/opsman/.ghcup/bin:/var/lib/flatpak/exports/bin:/usr/lib/jvm/default/bin:/usr/lib/rustup/bin:/home/opsman/.local/bin:/home/opsman/.cabal/bin:/home/opsman/.cargo/bin python scripts/dev.py run --serial 8AKY0M32X (debug build, install, launch); installed Pixel 3 XL direct-touch checks for hold/drag/release persistence, main-area tap preserving 10 AM, timeline tap selecting 11 AM, subsequent hourly drag changing 10 AM to 9 AM, and double-tap-to-now; screenshot captures listed in .codex/test-artifacts/046-p2-hourly-mode-retention/README.md; git diff --check. NOT RUN: tests (per user instruction), lint, TalkBack, RTL device review, reduced-motion setting, compact landscape, or large-font review.

## Follow-up

This slice did not change the 260 ms scale animation or address the possible sensitivity shift while that animation is in progress; investigate separately if the scrubber still feels delayed during entry. Tests, lint, TalkBack, RTL, reduced-motion, compact-landscape, and large-font checks were not run. The installed review was limited to the Pixel 3 XL portrait/default-font state described in the evidence README.
