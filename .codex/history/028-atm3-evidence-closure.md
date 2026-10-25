# Cycle 028-atm3-evidence-closure

Date: 2026-10-25
Roadmap: ATM.3

## Outcome

Closed incomplete at the device setup gate. After a tap on the Developer Options master switch, the Pixel disappeared from ADB and remained visible only in MTP mode. The setup screenshot appears to show the switch enabled, so the tap likely disabled it; this cause could not be confirmed after the disconnect. Its settings could not be restored or verified, so no installed app matrix or interactions were run. ATM.3 remains BLOCKED and not accepted; re-entry requires physically restoring authorized ADB access and planning a new bounded cycle.

## Verification actually performed

Passed: `python scripts/dev.py workflow`; `python scripts/dev.py contract`; `ANDROID_HOME=/home/opsman/Android/Sdk JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH python scripts/dev.py build` (`BUILD SUCCESSFUL`); the same environment with `python scripts/dev.py lint` (`BUILD SUCCESSFUL`); `git diff --check`. Install attempt: the same environment with `python scripts/dev.py run --serial 8AKY0M32X` built successfully but failed at install because ADB reported device not found. Device RTL/landscape gates, screenshots, interaction matrix, and restoration verification: not run because the Pixel was unavailable in ADB. Tests: not run, as unauthorized.

## Follow-up

Re-entry requires physically re-enabling Developer Options and USB debugging, then reconnecting/authorizing the Pixel so `adb devices -l` lists serial `8AKY0M32X`. Only then plan a new bounded cycle for the remaining setup gates, installed matrix, timestamp-backed interactions, and final restoration check. ATM.3 remains BLOCKED.
