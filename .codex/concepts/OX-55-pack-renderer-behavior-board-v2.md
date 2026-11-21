# OX-55 behavior board companion

**Approval record:** The product owner approved this behavior-board package on
2026-11-20 after review. Cycle 164 records the package's current file
fingerprints. Approval does not activate implementation.

**Focused revision:** On 2026-11-20 the owner selected a debug-only bundled
BASELINE comparison choice after installed captures established that normal
First City already draws OLIVE. Normal First City remains unchanged. The
approved comparison is BASELINE proof choice versus the loaded olive pack.

## Outcome and review boundary

Instantiate the already built and OX-91-validated OX-53 First City olive pack
through the existing First City CANOPY render path. Preserve the currently
selected place, catalog identity, exact instant, shared weather decision,
forecast facts, and all scene geometry and layer depth. The visible comparison
changes only the authored canopy asset. Unsupported requirements never execute.

The proposed interaction exposes temporary `First City · Baseline proof` and
`First City · Olive pack` options
from the existing in-session World control in the validation build only. It is
a proof route only: no app-wide exposure, persistent catalog, local
installation, Settings, or hot-swap contract is introduced.

## Established inputs

- OX-53 olive manifest: `docs/scene-pack-examples/first-city-canopy-olive/manifest.json`.
- Built and accepted archive: `.codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip`.
- It declares `packId: first-city-canopy-olive`, `worldDefinitionRef: first-city`,
  `manifestVersion: 2`, `engineCompatibility: 1`,
  `rendererPolicyVersion: 1`, and required capability
  `first-city.canopy.image-png`.
- OX-91 returns a complete accepted manifest, asset-byte map, and OX-54 content
  identity after its bounded resource, PNG structure/CRC, digest, and archive
  checks. It does not decompress PNG pixels or set the OX-55 renderer-load
  memory limit.
- The evidenced PNG is 1024 × 1536 and 1,386,266 encoded bytes. Renderer policy
  v1 supports this required capability on the existing First City CANOPY path.
- The current renderer chooses a bundled baseline or olive drawable in the
  CANOPY slot. OX-19's existing World control is session-local. OX-92 and OX-19
  establish that scene rendering reuses the shared selected-time weather and
  leaves location, catalog identity, selected instant, and forecast facts
  unchanged during a world choice.

## Proposed flow

1. From the existing session, select the debug-only bundled BASELINE proof
   choice for the reference capture, then the temporary olive-pack proof choice.
2. Pass the built archive through OX-91. The OX-91 result must be complete;
   rejection exposes no candidate asset set.
3. Dispatch only the declared `first-city.canopy.image-png` capability to the
   existing First City CANOPY path. Unsupported required capabilities and
   unsupported renderer-policy versions fail closed before rendering.
4. Validate PNG dimensions before pixel allocation and compute the candidate
   load estimate with checked arithmetic:
   `retained PNG bytes + (width × height × 4) + 524,288-byte reserve`.
   Admit only when the sum is at most `8,388,608` bytes. This accounts for the
   retained encoded PNG, one `ARGB_8888` bitmap, and a fixed 512 KiB allowance
   for decode/candidate overhead. Decode and prepare one complete candidate
   CANOPY input only after this preflight passes.
5. Reuse the existing `FirstCityComposition` and its shared weather decision.
   Supply the decoded pack image to the existing CANOPY draw position without
   changing element identity, geometry, pass, depth, readout, or timeline.
6. Commit the prepared candidate and capture the bundled baseline and loaded
   olive state at the same selected time, place, and catalog. Rejection keeps
   the currently displayed bundled scene and cannot partially replace it.

OX-56 owns detailed missing/invalid already-referenced package recovery. OX-57
owns local installation; OX-31 owns the installed-pack catalog; OX-32 owns
general hot swapping. OX-55 does not absorb those outcomes.

## Verification proposal

- Focused OX-55 tests only are authorized. Cover accepted validated bytes
  reaching the known CANOPY renderer input, preservation of the selected-time
  weather decision and scene geometry, and rejection before candidate
  activation for unsupported renderer requirements or failed/budget-exceeding
  decode.
- The roadmap exit requires an installed-device baseline/olive comparison with
  matching selected time, place, catalog identity, and visible forecast facts.
- Other test scopes require separate owner approval. This board claims no
  implementation, test result, build, or installed acceptance evidence.

## Decision record

- The proof choice is validation-build-only; app-wide visibility is outside
  OX-55.
- The OX-55 cap is `8,388,608` bytes. The integrated candidate gate is the
  retained PNG bytes plus `width × height × 4` decoded bytes plus a fixed
  `524,288`-byte reserve, using checked arithmetic before allocation and
  candidate commit.
- The measured native and Java overhead totals 208,240 bytes, below the
  524,288-byte reserve. For the fixed olive asset, the deterministic estimate
  is 8,202,010 bytes, leaving 186,598 bytes below the cap. The `ImageBitmap`
  wrapper references the same bitmap and adds no second pixel buffer.
- These decisions reconcile the prior open-decision wording with cycle 164's
  review record. Product-owner approval is recorded in cycle 164 with this
  package's current file fingerprints.

## Source records

- `docs/ROADMAP.md#OX-55` — objective, dependencies, exit, and exclusions.
- `.codex/history/163-ox91-pack-resource-validation.md` and
  `.codex/test-artifacts/163-ox91-pack-resource-validation/README.md` — actual
  built/validated artifacts and OX-91 limits.
- `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` — renderer policy and memory
  ownership.
- `.codex/history/155-ox19-scene-choice-independence.md` — selected-time and
  scene-choice invariants.
- `.codex/history/153-ox92-second-world-adapter.md` — shared declaration and
  weather-decision invariant.
