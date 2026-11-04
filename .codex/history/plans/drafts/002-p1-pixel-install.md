# Bounded plan — P.1 Pixel 3 XL install gate

State: ACTIVE
Scope: build and run the existing standalone prototype on the attached Pixel 3 XL; capture installed UI evidence. No Compose/source changes unless launch reveals a build or runtime defect.

## Steps

- Run the isolated app's debug build and install/launch command with the host Android SDK.
- Capture the running application from the device into `.codex/test-artifacts/002-p1-pixel-install/`.
- Record device/build outcome and evidence limits, then close this cycle.

## Acceptance

- App installs and launches on Pixel 3 XL.
- Installed screenshot is saved locally.
- Build/install/launch errors are reported precisely.
