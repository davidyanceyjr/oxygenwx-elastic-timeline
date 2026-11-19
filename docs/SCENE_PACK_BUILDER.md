# Local scene-pack builder

`scripts/build_scene_pack.py` creates one local ZIP artifact from the committed
First City baseline scene-pack example. From the repository root, run:

```bash
python scripts/build_scene_pack.py \
  --source docs/scene-pack-examples/first-city-canopy-baseline \
  --output build/scene-packs/first-city-canopy-baseline-v2.zip
```

The builder validates the closed seven-member manifest v2 envelope, duplicate
JSON members, supported First City capability, canonical relative asset path,
the exact `image/png` declaration, exact-byte SHA-256, and the source file
inventory. The source root must contain only `manifest.json` and the one
referenced PNG. Symlink entries, executable files, undeclared files, and
invalid or missing referenced assets are rejected before an artifact is
written.

The ZIP contains `manifest.json` at its root and the referenced PNG at its
declared relative path. Both payloads are stored without compression and their
bytes are preserved exactly. Archive entries have non-executable file modes.
The output directory is created as needed, and the output path must be outside
the source root. A successful command reports the artifact path and manifest
version; a rejected source reports the offending input and reason.

The committed First City baseline is the required supported source. The olive
example follows the same manifest shape and may build with the same command by
changing `--source` and `--output`, but it is not required for OX-30 acceptance.
This local artifact is declarative data only. A successful build does not claim
canonical pack identity, reproducibility, trusted origin, entitlement,
renderer runtime acceptance, installation, or activation.
