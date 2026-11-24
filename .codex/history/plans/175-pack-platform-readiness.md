# Cycle plan — 175-pack-platform-readiness

State: CLOSED
Plan maturity: FINAL
Cycle ID: `175-pack-platform-readiness`
Roadmap items: OX-09, OX-96, OX-29, OX-58, OX-61, OX-62, OX-33, OX-69, OX-78, OX-98, OX-73, OX-39, OX-40, OX-79, OX-93, OX-59, OX-60
Objective: Establish local scene-pack platform and cross-world readiness across repeatability, measured costs, local build/import, durable catalog and theme restoration, Settings selection, visual/weather truth, switching/resource behavior, and readiness evidence.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "175-pack-platform-readiness",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-09",
      "depends_on": [
        "OX-38",
        "OX-92"
      ],
      "status": "DONE",
      "exit": "Evidence records the sampled repeat-selection result and either a proven no-change disposition or a scoped stable-seed correction with visually equivalent motion/selection meaning.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-behavior-board.dot",
              "sha256": "f14cd14e03c508020c27daa3907868f61ec7fc8a7ff212e56e0b2f0bf408b018"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-behavior-board.svg",
              "sha256": "31e56774bb7b874d8461fec3046b20536db22777c694ea31e128d777d780d385"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-behavior-board.png",
              "sha256": "560ced8a04157ad1eb6173298f93fd230a6bb8308aed681d51c0deac49f034a5"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-behavior-board.md",
              "sha256": "aa40ec9dda774b12237c19ea6e9ac88a3f7fd0b5b5fe7f7cd9945d3cd49fccd1"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-ui-artwork-board.svg",
              "sha256": "c875e90f2c7173e88fedb9d05b8f471f3b0de876e1736d441bf2620417267e4e"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-ui-artwork-board.png",
              "sha256": "175f2de5910a1f1ff8697bb2123870fa588cafc2c27a2b32a84395afdc246592"
            },
            {
              "path": ".codex/concepts/OX-175-OX-09-repeatability-ui-artwork-board.md",
              "sha256": "c125791979837fff82dad721f444eb9f7efda888ef464f6ec09c5ac80d3841a2"
            }
          ]
        }
      },
      "outcome": "On the Pixel 3 XL, the approved A/A′/B/B′/A″ sequence kept Chicago forecast-44d LIVE at selected instant 2026-11-23T17:43:57.698631Z with unchanged visible weather facts. Runtime logs retained generation 1 and one coordinator request/source load. App-content RMSE was 0 for First City A/A′ and A/A″ and Open Landscape B/B′; no same-world re-randomization was observed, so no stable-seed or cache change was warranted.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-repeatability.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-first-city-initial.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-first-city-repeat.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-open-landscape-initial.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-open-landscape-repeat.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-first-city-return.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-09-world-probe.txt"
      ]
    },
    {
      "id": "OX-96",
      "depends_on": [
        "OX-55",
        "OX-32"
      ],
      "status": "DONE",
      "exit": "Reference-device measurements distinguish initial package/scene resolution, first frame and in-session switch from ordinary selected-time rendering; any bottleneck is recorded as observed, not assumed.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "On a fresh single MainActivity in Pixel 3 XL process 32119, the 500 ms selected-time swipe reached its first MainActivity frame 511.775 ms after first input (8.836 ms after the final touch callback). The installed-pack choice committed at 63.342 ms and its first valid MainActivity frame arrived at 72.327 ms after input (8.984 ms after commit). Both first frames were FrameTimeline Late Present / App Deadline Missed and each scoped gfxinfo capture recorded one janky frame. PSS/RSS were 239843/294379 KiB before and 244098/299467 KiB after the first valid pack frame. The internal resolver-only span was unavailable; no threshold or cause was inferred.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-baseline.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-timings.csv",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-device.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-gfxinfo.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-meminfo.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-system-trace.perfetto-trace",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-trace.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-runtime-logcat-epoch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-setup-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-initial.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-after-time-move.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-before-pack-selection.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-after-pack-selection.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-commands.txt"
      ]
    },
    {
      "id": "OX-29",
      "depends_on": [
        "OX-57",
        "OX-55",
        "OX-56",
        "OX-32"
      ],
      "status": "DONE",
      "exit": "Installed matrix records free/no-purchase availability, reproducible local artifact import/validation, invalid rejection, alternate-world rendering and no-restart weather-truth-preserving switch; pass/fail/not-run cases are explicit.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Built the same local First City artifact twice with identical archive bytes, SHA-256, and OX-54 content identity. On Pixel 3 XL, OX-57 reported the valid import as APP_OWNED/COMPATIBLE; a controlled OX-91 path-escape import was REJECTED without changing the committed generation or weather snapshot. The bundled free entry was available without purchase, and the installed alternate rendered and was selected in the same MainActivity/process with unchanged OX-05 selected time, visible forecast values, fact digest, coordinator requests, and source-load count.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-local-pack-proof.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-local-pack.zip",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-local-pack-rebuild.zip",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-artifact-hashes.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-invalid-path-escape.zip",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-make-invalid-path-escape.py",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-valid-import.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-invalid-import.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-generation-check.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-pointer-before-import.hex",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-pointer-after-valid.hex",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-pointer-after-invalid.hex",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-installed-tree-after-invalid.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-device-copy-hashes.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-device.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-weather-time.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-world-probe-before-switch.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-world-probe-after-switch.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-pid-before-switch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-pid-after-switch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-activity-before-switch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-activity-after-switch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-world-chooser.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-world-chooser-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-world-chooser-ready-to-switch.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-world-chooser-ready-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-bundled-before-switch.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-alternate-world.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-alternate-world-transition.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-alternate-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-commands.txt"
      ]
    },
    {
      "id": "OX-58",
      "depends_on": [
        "OX-57"
      ],
      "status": "DONE",
      "exit": "After a cold relaunch, the same installed pack identity/version is discovered and the always-available bundled free entry remains; no transient memory-only catalog suffices.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Cold relaunch on Pixel 3 XL reconstructed app-owned registration first-city-canopy-baseline at version ox29-v1 and displayed OX29-baseline-test as Installed · Compatible. The chooser retained the bundled-free default and First City free row; committed pointer and install record were byte-identical before and after.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-catalog-restoration.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-catalog-before.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-catalog-after.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-restored-catalog.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-restored-catalog-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-restoration-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-pointer-before.bin",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-pointer-after.bin",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-install-record-before.bin",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-install-record-after.bin",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-commands.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-build-device.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-scoped-verification.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-58-workflow-before-close.txt"
      ]
    },
    {
      "id": "OX-61",
      "depends_on": [
        "OX-32",
        "OX-58"
      ],
      "status": "DONE",
      "exit": "An installed compatible app-owned theme selected before process death is restored after a cold launch, and forecast access remains available through normal startup. Same-session switching retains the OX-32 place, catalog, selected-instant, facts, and timeline invariants; OX-61 does not persist those weather/time values across process death or require a purchase provider.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-behavior-board.dot",
              "sha256": "4edee131092da6814bf6503ec7576b3a1d3b80e3e1d439ef376198a0d0fbaa0b"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-behavior-board.svg",
              "sha256": "1f9c3582551fedec07c0e982a2c6d098ca54c60306f01f35c9bcfd4e9a297a34"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-behavior-board.png",
              "sha256": "d2abb69a740cb32bdadde47d06651d7ef5f43b2537a98babf69355adc1ec8b0d"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-behavior-board.md",
              "sha256": "ad8e8256743592ae399e6fec01dad155a4f7492aab4d0f437daac83c3f82cb29"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-ui-board.svg",
              "sha256": "4403a5c244f4c94452b61c858e95aeb886fe72e64b5b1e22484c892abd9ea507"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-ui-board.png",
              "sha256": "e3ed15f72e3ce1e3e7b123b4347c11d4c4e893e88244ffbe21039089abe7d044"
            },
            {
              "path": ".codex/concepts/OX-175-OX-61-theme-persistence-ui-board.md",
              "sha256": "983e519c93dee2ff7b1e8f9e2f2b6ca436153ae82769480078edc52eea37d606"
            }
          ]
        }
      },
      "outcome": "Pixel 3 XL accepted app-private persistence and cold restoration of the compatible installed theme; forecast access remained available and normal startup reinitialized weather/time state.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-61-selection-restoration.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-61-selection.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-61-before-relaunch.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-61-after-relaunch.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-61-restore-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-61-preferences-after.xml"
      ]
    },
    {
      "id": "OX-62",
      "depends_on": [
        "OX-61",
        "OX-56"
      ],
      "status": "DONE",
      "exit": "Cold launch with a previously selected theme unavailable shows safe free-world fallback, truthful catalog status and unchanged forecast access, with no commerce dependency.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            }
          ]
        }
      },
      "outcome": "Pixel 3 XL cold launches with missing, disabled, and incompatible saved-theme catalog states all fell back to Living Urban Park, retained the saved key, and preserved forecast and timeline access.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-62-unavailable-selection.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-62-catalog-states.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-62-missing.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-62-disabled.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-62-incompatible.png"
      ]
    },
    {
      "id": "OX-33",
      "depends_on": [
        "OX-31",
        "OX-58"
      ],
      "status": "DONE",
      "exit": "Installed Settings capture lists catalog-backed theme entries with correct compatible/active labels and accessible text; it does not scan shared device storage.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-behavior-board.dot",
              "sha256": "bd9b215f88ddf836e99ae260d37b1b36eed5b08a46ebf74ed6187f8b10327e04"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-behavior-board.svg",
              "sha256": "b07d17729522fb3bcf2be5de77ed56532f66dbabe3e9eac5298c0d58e3e78b6d"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-behavior-board.png",
              "sha256": "a77e168e9a3a2983955db778cf61c60133931a869bf6ad7169cc2650c80fd038"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-behavior-board.md",
              "sha256": "1aeee5751859b21f0803153e3118beda8181b52cad95638dd523fd2a773d9bb1"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-ui-artwork-board.svg",
              "sha256": "fbee625a2d37186f51c43b7f3ab532ac972370fff9d28bd72e8ba70e11995e22"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-ui-artwork-board.png",
              "sha256": "16b172a9533113d5495a3fe5bca3e77dfd60b55f137ba77005dcf5497c21d92b"
            },
            {
              "path": ".codex/concepts/OX-175-OX-33-settings-entry-ui-artwork-board.md",
              "sha256": "8f10c8ec331622db9bc1c5a1d99e896a7085b56ac953db8943d22c35143ac522"
            }
          ]
        }
      },
      "outcome": "Added the Settings gear and read-only app-managed catalog list. Pixel 3 XL captures show the versionless bundled role, active and compatible installed entry, and controlled Disabled/Incompatible statuses. Gear and back controls are labeled clickable buttons; underlying World taps do not pass through; visible/system back preserve the exact selected-time forecast hierarchy. Catalog input remains explicit app-managed registrations with no shared-storage scan.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-settings-catalog.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-settings-catalog.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-catalog-state.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-forecast-before.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-forecast-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-settings-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-row-tap-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-after-visible-back.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-after-visible-back-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-after-system-back.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-after-system-back-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-settings-disabled.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-disabled-settings-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-disabled-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-settings-incompatible.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-incompatible-settings-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-incompatible-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-selection-setup-prefs.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-disabled-prefs.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-incompatible-prefs.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-disabled-launch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-33-incompatible-launch.txt"
      ]
    },
    {
      "id": "OX-69",
      "depends_on": [
        "OX-33",
        "OX-32",
        "OX-61",
        "OX-62"
      ],
      "status": "DONE",
      "exit": "On device, selecting free and installed themes from Settings switches the displayed world without restart, persists across relaunch and makes fallback/disabled status clear with accessible actions.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            }
          ]
        }
      },
      "outcome": "Implemented Settings theme selection through the existing OX-32 switch and OX-61 persistence paths; fixed a demonstrated duplicate restore on the first installed selection from an unsaved Park session; verified same-process forecast/time continuity, active and disabled row states, persisted cold restore, and unavailable fallback on the Pixel 3 XL.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-settings-switch.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-interaction.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-settings-active.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-free-world.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-installed-world.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-after-relaunch.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-settings-unavailable.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-first-select-no-key-after-fix-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-69-world-probe-logcat.txt"
      ]
    },
    {
      "id": "OX-78",
      "depends_on": [
        "OX-29",
        "OX-61",
        "OX-62",
        "OX-69"
      ],
      "status": "DONE",
      "exit": "Installed evidence records selection continuity, no restart and fallback behavior with explicit pass/fail/not-run outcomes.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "On the Pixel 3 XL, four in-session Settings switches preserved the exact selected instant, Chicago forecast facts, process, and MainActivity. Cold restore returned the installed theme; the controlled OX-62 missing-catalog launch returned to Park while retaining the saved key and durable install; a normal cold launch recovered the installed theme. Physical package removal was not run.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-switch-recovery.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-scenarios.csv",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-recovery.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-process-launch.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-world-probe-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-missing-fixture-logcat.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-after-cold.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-recovered-installed.png"
      ]
    },
    {
      "id": "OX-98",
      "depends_on": [
        "OX-69",
        "OX-61",
        "OX-62"
      ],
      "status": "DONE",
      "exit": "Installed accessibility evidence covers the app-managed Settings theme list, active selection, disabled status, switch and recovery with actual pass/fail/not-run outcomes.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Completed the owner-scoped Pixel accessibility pass: active and disabled Settings states, enabled-control focus order, installed selection and return, bundled and installed weather facts, and normal cold recovery are recorded. By owner decision, spoken-label/action verification is out of scope for OX-98 and remains NOT VERIFIED; no audible-output claim is made. No app code changed; original accessibility settings and the saved installed theme were restored.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-accessibility.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-focus-log.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-talkback-transcript.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-active.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-unavailable.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-after-select-installed-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-bundled-facts-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-talkback-screen-recording-app-pass.mp4",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-talkback-mic-retry.mp4",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-normal-cold-restore-preferences.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-accessibility-after.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-enabled-services-after.txt"
      ]
    },
    {
      "id": "OX-73",
      "depends_on": [
        "OX-32",
        "OX-61",
        "OX-96"
      ],
      "status": "DONE",
      "exit": "Measured same-session switches and cold restore produce observed costs with a retain/follow-up decision; any new optimization requires its own bounded approved slice.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Measured five same-session First City ↔ installed-pack round trips and the separate OX-61 cold restore on the Pixel 3 XL. The exit is met with observational latency, FrameTimeline and process-memory evidence. Retain current code in this slice; flag the measured installed-switch and cold-restore costs for follow-up before any optimization, with no numeric threshold or inferred cause.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-switch-costs.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-timings.csv",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-memory.csv",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-cold-restore.csv",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-device.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-attempt-2/OX-73-switches.perfetto-trace",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-cold-retry/OX-73-cold-restore.perfetto-trace"
      ]
    },
    {
      "id": "OX-39",
      "depends_on": [
        "OX-24",
        "OX-36"
      ],
      "status": "DONE",
      "exit": "Two styles render from the same selected weather input with unchanged weather facts, element identity and depth; installed captures distinguish the treatment.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            },
            {
              "path": ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.dot",
              "sha256": "79f2df545bb3ffb46e1d6083821277b9f4b84f0ef4543e0503c39c7f7b3af502"
            },
            {
              "path": ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.svg",
              "sha256": "cd04e25a1bff038f492f66373caba4960e5efd12f8a4bb15f9a762e851cea8cf"
            },
            {
              "path": ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.png",
              "sha256": "dfeee60437ac0d542b1231cc62393d7f1d704378ad5d79ec0378887c2fcefa2e"
            },
            {
              "path": ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.md",
              "sha256": "4a5236768c560e0e7238950cd0a29b7cae3181e51b2f76cf24ba33fd39321d64"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            }
          ]
        }
      },
      "outcome": "Implemented a scene-definition CANOPY asset choice using the existing baseline and olive PNGs on the same CANOPY draw path. Installed Pixel 3 XL captures at the fixed OX-05 Chicago instant show distinct foliage while the accessibility hierarchies and upper scene crop remain identical; weather facts, element identity, foreground pass, and geometry are unchanged.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-39-visual-treatment.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-39-baseline.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-39-alternate-treatment.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-39-baseline-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-39-alternate-hierarchy.xml"
      ]
    },
    {
      "id": "OX-40",
      "depends_on": [
        "OX-39",
        "OX-92"
      ],
      "status": "DONE",
      "exit": "Installed comparisons of the affected scene element(s) preserve shared weather meaning, forecast access and unknown-state handling in both existing worlds; unsupported capabilities are documented rather than silently assumed.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            }
          ]
        }
      },
      "outcome": "Installed First City and Open Landscape comparisons at the same two Chicago SG4 replay instants retain the same selected-time weather facts, rain meaning, and unavailable/carried interpretation while preserving the separate CANOPY and left-tree-group artwork. The standard-screen cloud-bank visibility difference is recorded as an observation under the owner-selected OX-40 scope; no missing-capability claim or renderer change was made.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-cross-world-comparison.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-first-city-supported-rain.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-first-city-supported-rain-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-open-landscape-supported-rain.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-open-landscape-supported-rain-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-first-city-carried-rain-missing-cover.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-first-city-carried-rain-missing-cover-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-open-landscape-carried-rain-missing-cover.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-open-landscape-carried-rain-missing-cover-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-commands.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-device.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-world-chooser-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-world-chooser-carried-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-40-missing-cover-current-hierarchy.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-supported-rain-open-landscape-20261004T1530Z.png"
      ]
    },
    {
      "id": "OX-79",
      "depends_on": [
        "OX-78",
        "OX-36",
        "OX-40",
        "OX-12"
      ],
      "status": "DONE",
      "exit": "A scoped installed cross-pack matrix identifies exact selected instant, source validity, units, supported/missing interpretation and unchanged weather/catalog during switching with pass/fail/not-run per scenario.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "All six approved fixture/world cells passed on the Pixel 3 XL: displayed weather facts and availability matched between bundled First City and the installed OX29 baseline. Within each pair the selected instant, Chicago location, replay/catalog provenance, process PID, coordinator request count, and source-load count stayed unchanged. Restored bundled First City and the initially empty installed-theme preference; evidence only, with no app code changes.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-weather-truth.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-weather-matrix.csv",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-weather-snapshots.json",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-hierarchy-comparison.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-bundled-supported.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-bundled-supported-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-installed-supported.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-installed-supported-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-bundled-carried.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-bundled-carried-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-installed-carried.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-installed-carried-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-bundled-unavailable.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-bundled-unavailable-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-installed-unavailable.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-installed-unavailable-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-supported-world-probe.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-carried-world-probe.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-unavailable-world-probe.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-supported-before-world-switch.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-carried-before-world-switch.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-unavailable-before-world-switch.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-device.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-commands.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-restored-first-city.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-restored-first-city-hierarchy.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-restored-preferences.xml",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-world-chooser-initial.xml"
      ]
    },
    {
      "id": "OX-93",
      "depends_on": [
        "OX-29",
        "OX-78",
        "OX-79",
        "OX-98",
        "OX-73"
      ],
      "status": "DONE",
      "exit": "Owner-facing readiness matrix distinguishes proven core platform capabilities, failed/not-run items and outstanding blockers; an owner decision is explicitly recorded or remains pending without claiming release approval.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Compiled the owner-facing readiness matrix and linked dependency index from the approved scoped evidence. Core local build/import, catalog/theme restoration, Settings selection, switching/recovery, cross-pack weather truth, bounded accessibility semantics, and measured costs are reported with their limits. OX-98 speech remains NOT VERIFIED and owner-excluded from that slice; no audible-output claim or performance threshold is made. The overall first-generation milestone decision is explicitly PENDING owner review; OX-59 and OX-60 remain independent later exits.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-93-readiness.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-93-dependency-index.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-29-local-pack-proof.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-78-switch-recovery.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-79-weather-truth.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-98-accessibility.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-73-switch-costs.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-96-baseline.md"
      ]
    },
    {
      "id": "OX-59",
      "depends_on": [
        "OX-32"
      ],
      "status": "DONE",
      "exit": "An installed focused scrub/switch sequence produces one coherent final selected instant and one active scene with no stale selection or gesture theft; the same contract already holds on ordinary OX-32 swaps.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            }
          ]
        }
      },
      "outcome": "PASS for one owner-selected installed overlap on the Pixel 3 XL: pointer 0 scrubbed to 2026-10-27T20:25:42.857142857Z and remained held while pointer 1 selected OX29-baseline-test; the installed scene committed at that exact instant, and pointer 0 release preserved it. Temporary UiAutomation driver removed after the successful run. Evidence is one focused device sequence only.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-scrub-switch.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-event-order.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-world-probe.log",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-before.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-before-hierarchy.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-scrub-held.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-scrub-held-hierarchy.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-chooser-while-scrub-held.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-chooser-while-scrub-held-hierarchy.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-installed-while-scrub-held.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-installed-while-scrub-held-hierarchy.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-after.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-after-hierarchy.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-59-pack-import.log"
      ]
    },
    {
      "id": "OX-60",
      "depends_on": [
        "OX-32"
      ],
      "status": "DONE",
      "exit": "Repeated free ↔ alternate switching leaves no stale visual layers or old-theme interactive targets; affected resource ownership is inspectable on device.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
              "sha256": "5479ba18b6137fbdc55eb9b9b5d3aedebaa50b6cb11f928b90d69978356849d7"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
              "sha256": "23ad67cdff6d63922fc40fecde49faa53c7c7dfbe47c537acad430a748020c31"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
              "sha256": "5f61c7220337b3f92c8c491aac6efdbb45dd11b4484cdaf9b08d2ddfa8430577"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
              "sha256": "a903de634df87e5403376134ac75a40324a24e24804f8f58193fef3740f0a9e3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-23",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
              "sha256": "09ad48cf1702f3664f584a27eb3b3336751215617abb4361f174fd0e65bcd109"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
              "sha256": "4c9beaa4b9223d92f3467dc87ab9a32e470d7e5bcba5f753a0a93007cad6b1ef"
            },
            {
              "path": ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
              "sha256": "8a31a04e2fa32c4a8c20c9dbb51904697f9b1ebebca34625d6172ed2c9c6f5b1"
            }
          ]
        }
      },
      "outcome": "PASS on the owner-selected five Living Urban Park ↔ installed OX29-baseline-test round trips (10 transitions) on one Pixel 3 XL. Ten screenshot/hierarchy pairs show only the active scene and retain the exact OX-05 forecast and shared controls. The inspected scene path has no scene-owned hit target (not applicable per the owner decision); active candidate and Canvas ownership are documented. Heap/cache release is not measured. The planned MP4 is a screenshot montage, and the original 117.72-second partial screen recording is preserved.",
      "evidence": [
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-resource-lifecycle.md",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-resource-ownership.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-target-audit.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-event-order.txt",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-free-alternate-free.mp4",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-final-state.png",
        ".codex/test-artifacts/175-pack-platform-readiness/OX-60-screenrecord-partial-117s.mp4"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-23",
    "summary": "Cycle 175 completed all 17 ordered slices with their separate outcomes and evidence. Results establish local scene-pack, selection, cross-world, weather, accessibility, measurement, and switch readiness within their recorded device and fixture boundaries. OX-93 remains a pending owner milestone decision, so this closeout makes no release or platform approval claim. OX-98 speech is not verified and was excluded by the owner. OX-60 completed five installed Park/OX29 round trips; its scene-target check is not applicable on the inspected path, and the report distinguishes the screenshot montage from the preserved partial continuous recording.",
    "verification": [
      "python scripts/dev.py workflow — passed at CLOSEOUT with all slices terminal and no active slice.",
      "git diff --check — passed with no output.",
      "OX-59 focused Pixel 3 XL UiAutomation interaction — adb shell am instrument -w -e class com.oxygen.weather.timeline.ui.Ox59ScrubSwitchDriverTest com.oxygen.weather.timeline.test/androidx.test.runner.AndroidJUnitRunner — OK (1 test).",
      "OX-60 Pixel 3 XL installed sequence — five Park ↔ OX29 round trips completed; ten transition screenshots and accessibility hierarchies captured at the same OX-05 instant.",
      "OX-60 media inspection — ffprobe reports the screenshot montage as H.264, 1440x2960, 30 fps, 23.966667 seconds; the 117.72-second partial continuous recording is kept under its diagnostic name.",
      "No OX-60 build or test was run; final verification was limited to workflow validation, git diff --check, installed screenshots/hierarchies, and media metadata."
    ],
    "evidence_directory": ".codex/test-artifacts/175-pack-platform-readiness/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "175-pack-platform-readiness",
  "slice_ids": [
    "OX-09",
    "OX-96",
    "OX-29",
    "OX-58",
    "OX-61",
    "OX-62",
    "OX-33",
    "OX-69",
    "OX-78",
    "OX-98",
    "OX-73",
    "OX-39",
    "OX-40",
    "OX-79",
    "OX-93",
    "OX-59",
    "OX-60"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.dot",
    ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.svg",
    ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.png",
    ".codex/concepts/OX-175-pack-platform-readiness-behavior-board.md",
    ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.svg",
    ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.png",
    ".codex/concepts/OX-175-pack-platform-readiness-ui-artwork-board.md",
    ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.dot",
    ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.svg",
    ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.png",
    ".codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.md"
  ],
  "completed_gates": [
    "OX-62 cold-launch fallback/status evidence captured for MISSING, DISABLED, and INCOMPATIBLE; saved key and durable installation retained in each fixture.",
    "OX-33_behavior_board_approved",
    "OX-33_ui_artwork_board_approved",
    "OX-39_behavior_board_approved"
  ],
  "decisions": [
    "Owner-selected 2026-11-23: OX-60 uses five bundled Living Urban Park ↔ installed OX29-baseline-test round trips (10 transitions). Record the theme-owned target check as not applicable because no scene-owned click handler or hit-region action exists in the current scene path; inspect replaced scene resources and availability of shared controls.",
    "2026-11-23 owner selected option 1 for OX-60 video evidence: preserve the partial continuous recording and create the planned MP4 as a screenshot montage from the existing captures; do not add more round trips."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Closed OX-59 DONE with the owner-selected one-overlap UiAutomation run, device captures, hierarchy, event trace, and world-probe record. OX-60 read-only source inspection finds no scene-owned click handler or hit-region interaction; theme visuals are drawn under CatalogTimeLabScreen while current gestures/controls live at the screen/timeline level. The approved OX-60 plan also specifies 'repeated' without a count. No OX-60 device interaction has begun.",
    "Closed slice OX-60 as DONE.",
    "OX-60 closed DONE with ten installed state captures and hierarchies, ownership and target audits, selected-theme cleanup, and the partial-recording/montage limitation documented.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/175-pack-platform-readiness/OX-59-scrub-switch.md",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-59-event-order.txt",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-resource-lifecycle.md",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-resource-ownership.txt",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-target-audit.txt",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-event-order.txt",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-free-alternate-free.mp4",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-final-state.png",
    ".codex/test-artifacts/175-pack-platform-readiness/OX-60-screenrecord-partial-117s.mp4",
    ".codex/test-artifacts/175-pack-platform-readiness/README.md",
    ".codex/history/175-pack-platform-readiness.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md; .codex/plans/175-pack-platform-readiness.md; docs/ROADMAP.md; .codex/test-artifacts/175-pack-platform-readiness/README.md; OX-59 and OX-60 reports, screenshots, hierarchies, and closeout evidence"
  ],
  "next_action": "Inspect git diff --check and the complete final diff; then close the cycle with exact verification results, evidence paths, and limitations."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Intake and approved boards

- The owner requested all 17 related platform/readiness slices in the order stored in the manifest. OX-09 is the sole eligible `NEXT`; the remaining slices are `PLANNED`. Every in-cycle dependency appears earlier in the roster, and the recorded external dependencies are `DONE`.
- The complete behavior package was approved on 2026-11-23 for every roster slice. Its DOT source, SVG and PNG renders, and companion contract are fingerprinted separately on all 17 manifest entries.
- The complete UI artwork package was approved on 2026-11-23 for OX-09, OX-39, OX-40, OX-59, OX-60, OX-61, OX-62, OX-33, and OX-69. Its SVG, PNG, and companion contract are fingerprinted separately on each of those nine entries. The other eight slices have no visible-output change in their approved scope, so their UI artwork gate is not applicable.
- The product owner approved the OX-09-only behavior and UI artwork supplements on 2026-11-23. They cover same-world repeat comparisons in First City and Open Landscape, including returning to First City after switching worlds. After LIVE-state device preflight, the owner selected and approved a revision to the OX-09 UI supplement. The exact shared-plus-supplement package and refreshed fingerprints are recorded on OX-09 only; behavior approval and all other roster-slice approvals remain unchanged.
- The approved behavior contract and UI artwork contract map each slice to its own triggers, affected states, scope limits, and exit. The exact approved files and fingerprints are in the structured roster above. The contracts retain their review-time draft status wording; the manifest is the approval record. Changing any approved board file requires refreshed review and fingerprints.
- The OX-41 Living Urban Park is the bundled free default and OX-62's cold-launch fallback. OX-56's First City fallback remains limited to its approved device-local scene-resolution failure. The two fallback decisions have different triggers and are not interchangeable.

### Shared constraints and exclusions

- **World-switch continuity:** Preserve the selected place, weather-catalog revision, exact selected instant, displayed forecast facts, timeline state, and gesture ownership. A switch makes no forecast request and causes no Activity/process restart. Prepare and activate a complete renderer candidate atomically, following OX-19/OX-32.
- **Weather truth:** All scenes consume the existing typed selected-instant weather decision. Preserve supplied, carried, and unavailable states, carried-source validity time, units, and visible forecast facts. Do not add plausibility rejection or another weather interpreter.
- **Catalog identity:** Keep stable catalog/pack identity, app-managed content/release revision, manifest schema, engine compatibility, renderer-policy version, and content-byte identity distinct. The catalog is app-owned and explicit; do not scan shared storage.
- **Renderer boundary:** Reuse the existing supported scene paths and declared capabilities. Do not introduce a general plug-in renderer, unrestricted shader framework, or unrelated graphics/cache layer. Any new pack-facing capability or compatibility-version change must be called out and reviewed before it is made.
- **Experience boundary:** Keep the immersive scene, timeline, forecast values, and truthful DEMO DATA disclosure intact. UI slices verify their scoped states and direct interactions. Broad accessibility/system acceptance belongs to OX-98, not to each design slice.
- **Exclusions:** No commerce provider, checkout, paid distribution, shared-storage discovery, Park polish, Moon detail, or release approval. OX-93 decides only whether the first local theming-platform milestone is supported by the recorded evidence; it does not approve a release.

### Verification authorization and evidence convention

The original plan-preparation request authorized planning, board preparation, and workflow validation only. On 2026-11-23, the owner authorized execution by invoking `$oxygen-execute-plan`; this authorizes the bounded implementation, verification, installs, screenshots, and device acceptance required by the plan. Run only plan-required checks, and add or run tests only within this requested execution. Each slice keeps its own exit and evidence even where the package is shared.

The evidence root is `.codex/test-artifacts/175-pack-platform-readiness/`. Each named Markdown file below is the per-slice record and should include the exact commands, device/build/fixture, raw observations, pass/fail/not-run result, limitations, and relevant artifact hashes. The root `README.md` will index the 17 records. Screenshots, recordings, traces, generated packs, and raw measurement files remain separate artifacts next to that record. These are targets, not evidence already collected. Cycle closeout belongs at `.codex/history/175-pack-platform-readiness.md`.

For device checks, use the recorded Pixel 3 XL (`8AKY0M32X`, Android 12/API 31) if it remains available at execution time. Record device unavailability as `not run`; do not silently substitute a different device. Installed screenshots and focused interaction evidence are required wherever the slice exit calls for them. A build or preview alone does not establish visual acceptance.

### Slice-by-slice execution plan

The manifest's `exit` field is the exact roadmap exit for each slice and remains controlling. The work and checks below make that exit reviewable; they do not combine exits or expand them.

#### OX-09 — Repeat selection

- **Scope:** Hold place, catalog revision, exact selected instant, supplied weather decision, build, device, and viewport fixed. Repeat the same selection in First City, switch to Open Landscape at that instant and repeat there, then return to First City and repeat again. Compare samples within each world; different world compositions need not match. Apply stable seeds only to a demonstrated same-world re-randomization. Do not add cache machinery.
- **Planned verification:** Capture First City A/A′/A″ and Open Landscape B/B′, compare each set within its world, and distinguish animation phase from a re-randomized layout. If results are repeatable, record the sampled result and make no correction. If a correction is needed, compare before/after captures and verify that motion and selection meaning remain equivalent while the selected instant and forecast facts remain coherent.
- **Evidence:** `OX-09-repeatability.md`, `OX-09-first-city-initial.png`, `OX-09-first-city-repeat.png`, `OX-09-open-landscape-initial.png`, `OX-09-open-landscape-repeat.png`, `OX-09-first-city-return.png`, and `OX-09-motion.mp4` only if still images cannot show the observed motion.
- **Compatibility/docs:** No compatibility-policy change is expected. Record the sampled inputs and disposition in evidence.

#### OX-96 — First loaded-pack performance baseline

- **Scope:** Establish a focused baseline before Park polish. On the reference device, separate ordinary selected-time evaluation/rendering, initial package and scene resolution, first valid packaged-world frame, and one in-session theme switch. Do not add caching or broad performance refactoring.
- **Fixed workload:** Use the debuggable `com.oxygen.weather.timeline` app and the Pixel 3 XL (`8AKY0M32X`, Android 12/API 31) when available. Activate the existing OX-05 synthetic astronomy fixture (`ox05-depth-review-synthetic-astronomy-v1`, Chicago) at `2026-10-28T17:00:00Z`; its second fixed review instant is `2026-10-29T17:00:00Z`. Use the bundled First City and the compatible `first-city-canopy-baseline` pack built from `docs/scene-pack-examples/first-city-canopy-baseline/` with `python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/175-pack-platform-readiness/OX-96-baseline.ox30pack`. In one `MainActivity`/process, install the pack through the existing OX-57 debug handoff as setup, make one selected-time move between those fixture instants with bundled First City active, then select the installed pack once while holding the latter instant and weather facts fixed. The timed pack event is runtime candidate resolution/activation, not ZIP generation or installation. Record the generated package hash/content identity and actual device/build details. If the reference device is unavailable, mark the device measurement `not run`; do not substitute another device.
- **Measurement method:** Capture an Android Studio Profiler System Trace (Perfetto) spanning the selected-time move and the single pack selection. Use its input event and first presented `FrameTimeline` frame as the repeatable external timing boundaries; save the trace and raw frame observations. Before each action run `adb shell dumpsys gfxinfo com.oxygen.weather.timeline reset`, then collect `adb shell dumpsys gfxinfo com.oxygen.weather.timeline` after its first presented frame for per-frame duration/jank data. Collect `adb shell dumpsys meminfo com.oxygen.weather.timeline` immediately before pack selection and after its first valid frame as process snapshots, not peak-memory claims. Retain the existing `OX57Install`/`OX19WorldProbe` logs and installed captures to identify the pack, selected instant, and visible committed state. No app trace spans for the internal candidate resolver were found in the current code: report chooser-selection-to-first-presented-frame as the end-to-end initial load/switch measurement, and report a resolver-only subspan as unavailable unless the trace provides a repeatable marker. If a required boundary cannot be observed repeatably, record that limitation before proposing instrumentation; do not add instrumentation or infer a bottleneck in this measurement slice.
- **Planned verification:** Record the device/build, exact world/package, selected instant, fixture, measurement boundaries, raw elapsed observations, frame-time/jank data, and any memory/frame trace. Keep selected-time rendering separate from the first package load and first valid packaged-world frame; report observed bottlenecks without a pass threshold or assumed cause.
- **Evidence:** `OX-96-baseline.md`, `OX-96-timings.csv`, `OX-96-device.txt`, `OX-96-gfxinfo.txt`, `OX-96-meminfo.txt`, and `OX-96-system-trace.perfetto-trace`; include `OX-96-trace.txt` if a text export is useful.
- **Compatibility/docs:** No renderer behavior changes are expected. Keep the baseline method and limits in evidence; do not present one-device results as a cross-device guarantee.

#### OX-29 — Free-world and local-pack build/import proof

- **Scope:** Independently exercise the bundled free scene, reproducible local artifact generation, OX-91 validation, valid and invalid developer-local import, one alternate-world render, and the first weather-preserving switch. No purchase provider or Park artwork is required.
- **Planned verification:** Build the same local artifact twice and compare recorded content identity/hash; import the valid artifact and a controlled invalid artifact; verify invalid rejection; confirm the free entry requires no purchase; render the alternate world and switch to it in the same Activity/process while comparing selected instant and forecast facts before/after. Record every matrix row as pass, fail, or not run.
- **Evidence:** `OX-29-local-pack-proof.md`, `OX-29-local-pack.zip`, `OX-29-artifact-hashes.txt`, `OX-29-valid-import.log`, `OX-29-invalid-import.log`, `OX-29-alternate-world.png`, and `OX-29-weather-time.json`.
- **Compatibility/docs:** Update `docs/SCENE_PACK_INSTALLATION.md` only for a confirmed local build/import instruction or contract change. Keep OX-57 atomic-install guarantees and OX-56 recovery ownership intact.

#### OX-58 — Restore installed catalog after relaunch

- **Scope:** Restore explicit app-owned registrations from the private installed-pack state after a cold relaunch. Keep catalog discovery distinct from restoring the currently selected theme.
- **Planned verification:** Install a known pack, cold-launch the app, and confirm the same stable ID and app-managed version return from durable state alongside the always-available OX-41 free entry. Do not accept process-memory-only visibility or shared-storage discovery.
- **Evidence:** `OX-58-catalog-restoration.md`, `OX-58-catalog-before.json`, `OX-58-catalog-after.json`, and `OX-58-restored-catalog.png` if the resulting state is visible.
- **Compatibility/docs:** Update `docs/SCENE_PACK_INSTALLATION.md` and `docs/INSTALLED_THEME_CATALOG.md` only to document the implemented app-owned cold-restore boundary; do not add migration or global scan behavior.

#### OX-61 — Persist and restore selected theme

- **Scope:** Persist the stable app-owned pack ID after a compatible installed theme commits successfully, independently of place and forecast state. Restore only a matching available compatible entry using its current committed generation. Explicit selection of a non-installed world clears the installed-theme key; a missing or incompatible saved key remains for OX-62. A different installed candidate that fails preparation leaves the current world and saved key intact. If reloading the already-active installed entry fails, preserve the existing OX-32 behavior: fall back to bundled First City and retain the saved key for retry. Settings UI remains OX-33/OX-69. Same-session switching retains OX-32 invariants.
- **Planned verification:** Select an installed compatible theme, terminate and cold-launch, then verify the same installed world returns and forecast access is available through normal startup. Verify the stored stable pack ID and current catalog registration; do not assert that place, catalog revision, selected instant, forecast facts, or timeline scale survive process death. Confirm no purchase provider is required.
- **Evidence:** `OX-61-selection-restoration.md`, `OX-61-selection.json`, `OX-61-before-relaunch.png`, and `OX-61-after-relaunch.png`.
- **Compatibility/docs:** Keep catalog identity/version meanings from OX-31. Document the persistence owner and restore boundary in the relevant catalog/installation guide. The selected-theme-only cold-launch boundary was chosen by the owner on 2026-11-23 and is covered by OX-61 behavior and UI-artwork supplements; the shared cycle boards remain unchanged.

#### OX-62 — Fallback when selected theme is unavailable

- **Scope:** On cold launch with the saved theme missing, disabled, or incompatible, show the bundled free Living Urban Park and retain truthful catalog availability. This is separate from OX-56's First City scene-resolution failure. Do not require OX-83 update/removal UI.
- **Planned verification:** Use controlled missing, disabled, and incompatible entries as separate evidence rows. For each, cold-launch, confirm the free-world fallback and continued forecast access, and record the unavailable entry's catalog status. Do not silently erase or invent a new meaning for the stored selection key.
- **Evidence:** `OX-62-unavailable-selection.md`, `OX-62-missing.png`, `OX-62-disabled.png`, `OX-62-incompatible.png`, and `OX-62-catalog-states.json`.
- **Owner resolution (2026-11-23):** Keep all three evidence rows and add a distinct catalog `DISABLED` validity plus a debug-only in-memory fixture. The fixture requires the saved baseline pack key, does not edit durable install data or the saved key, and records availability at the catalog boundary. Settings presentation remains OX-33.
- **Compatibility/docs:** Keep the OX-41 and OX-56 fallback triggers distinct in `docs/INSTALLED_THEME_CATALOG.md` or `docs/SCENE_PACK_INSTALLATION.md` if implementation changes their documented handoff.

#### OX-33 — Catalog-backed Settings list

- **Scope:** Add a visible Settings gear beside World that opens a read-only list backed by the app-managed catalog: the bundled-free role, installed compatible entries, active state, and incompatible/disabled status. Return by back/system back without changing the forecast world, location, facts, or selected time. Do not add selection mechanics, downloads, checkout, or storage scans.
- **Planned verification:** On the installed screen, capture the reachable Settings list and verify the gear label, back interaction, catalog-backed entries, correct active/compatibility/unavailable labels, readable text, and accessible semantics. Confirm app-owned entries correspond only to explicit registrations; the bundled-free role has no content version until an actual Park revision exists.
- **Evidence:** `OX-33-settings-catalog.md`, `OX-33-settings-catalog.png`, and `OX-33-catalog-state.json`.
- **Owner resolution (2026-11-23):** The owner selected a visible gear beside World. The bundled-free Living Urban Park role keeps its truthful name/status but has no content-version value until real bundled Park content supplies a revision; app-owned pack versions remain required. The catalog model and guide must express this boundary without showing debug placeholder metadata.
- **Boards:** OX-33 is governed by the shared cycle 175 behavior/UI package plus `.codex/concepts/OX-175-OX-33-settings-entry-behavior-board.dot`, its SVG/PNG renders and contract, and `.codex/concepts/OX-175-OX-33-settings-entry-ui-artwork-board.svg`, its PNG render and contract. These supplements cover the gear route, read-only return, truthful bundled-role revision state, and distinct catalog validity labels. OX-33's roster entry records the refreshed approval/fingerprints; other slice approvals remain unchanged.
- **Compatibility/docs:** No pack-format or renderer-policy change is expected. Document the optional bundled-role revision only; app-owned registration versions remain non-empty. Keep broad TalkBack/focus/system acceptance in OX-98.

#### OX-69 — Select themes through Settings

- **Scope:** Connect Settings selection to the existing OX-32 atomic switch and OX-61 persistence paths. Show active state and unavailable/disabled status; preserve the normal forecast experience. Do not duplicate scene loading.
- **Planned verification:** On device, select bundled free → installed compatible → bundled free, checking the displayed world, active row, selected instant, forecast facts, and one Activity/process throughout; cold-launch and confirm persistence. Inspect the enabled/disabled states and direct action semantics. Leave broad screen-reader matrix checks to OX-98.
- **Evidence:** Recorded in `.codex/test-artifacts/175-pack-platform-readiness/OX-69-settings-switch.md` and `OX-69-interaction.txt`, with installed captures and hierarchies for active/free/installed/disabled states, cold restore, process continuity, selection persistence, and the corrected single-commit startup path.
- **Compatibility/docs:** Keep the control as a caller of OX-32. Update UI interaction documentation only if a user-visible contract changes.

#### OX-78 — Switch, persistence, and recovery verification

- **Scope:** TEST the integrated free/installed switch, cold restore, missing-selection fallback, and recovery behavior under the recorded device/launch conditions. Do not repeat OX-29 artifact/import acceptance or OX-98 broad accessibility acceptance.
- **Planned verification:** Use a compact scenario matrix for repeated valid switches, cold restoration, and unavailable saved choice. Record selection continuity, process/Activity continuity, fallback result, and pass/fail/not-run for each scenario.
- **Evidence:** Recorded in `.codex/test-artifacts/175-pack-platform-readiness/OX-78-switch-recovery.md`, `OX-78-scenarios.csv`, `OX-78-recovery.png`, and `OX-78-process-launch.txt`, with same-process switch, cold restore, controlled missing-catalog, and recovery screenshots, hierarchies, logs, PIDs, Activity records, and preference snapshots.
- **Compatibility/docs:** Test-only unless observed behavior requires a separately scoped contract correction. Do not add resilience behavior for hypothetical cases.

#### OX-98 — Installed-theme accessibility verification

- **Scope:** TEST the app-managed Settings list and one installed world for accessible labels/status, focus order, active selection, disabled/unavailable status, selection activation, recovery, and access to exact weather facts.
- **Owner scope decision (2026-11-23):** Spoken labels/actions are out of scope for OX-98. They remain NOT VERIFIED; hierarchy and focus evidence do not substantiate audible output. OX-93 must preserve that limitation without claiming a speech check.
- **Planned verification:** Run the focused installed accessibility interactions and inspect the exposed labels/status and focus sequence in the bundled and one installed world. Do not represent the accessible hierarchy or focus sequence as evidence of audible speech. Keep the matrix limited to the states required by this exit; do not broaden to unrelated RTL, localization, devices, or app-wide screens.
- **Evidence:** `OX-98-accessibility.md`, `OX-98-focus-log.txt`, `OX-98-active.png`, and `OX-98-unavailable.png`.
- **Compatibility/docs:** No broad design-slice accessibility expansion. Record only the acceptance findings and any concrete scoped defect.

#### OX-73 — Theme-switch and restore cost

- **Scope:** After OX-96, measure repeated valid switches and cold restore. Record memory, latency, and observed resource issues. Do not automatically redesign catalog compilation or optimize without a separate approved slice.
- **Planned verification:** Reuse the OX-96 reference device and, where comparable, the same build identity, First City ↔ `first-city-canopy-baseline` world/package pair and content identity, OX-05 fixture/selected-instant setup, System Trace/`gfxinfo` frame boundaries, `meminfo` capture method, and single-switch timing definition. Collect new raw samples for every repeated switch and its memory observations. Measure cold restore separately from OX-96's first in-session pack load, using the OX-61 persisted selection and a cold process launch to first restored frame; run it on the OX-61-enabled build and record that build against OX-96. OX-96 timing values are baseline context, not OX-73 repeated-switch or restore observations. Identify any changed build, device, fixture, or timing boundary and state an evidence-based retain/follow-up disposition without inventing a threshold.
- **Evidence:** `OX-73-switch-costs.md`, `OX-73-timings.csv`, `OX-73-memory.csv`, and linked OX-96 baseline context.
- **Compatibility/docs:** Measurements and limits belong in evidence. Any optimization proposal is a separate slice and is outside this plan.

#### OX-39 — Definition-declared visual treatment

- **Scope:** Extend one existing renderer capability with one definition-declared alternate treatment. Use First City CANOPY, supported by OX-24/OX-55. The owner selected the existing `FirstCityCanopyAsset.BASELINE` (`sg2_city_canopy`) and `FirstCityCanopyAsset.OLIVE` (`sg2_city_canopy_olive`) assets as Treatment A and Treatment B. Preserve the CANOPY identity, role, foreground pass, geometry/bounds, selected weather, and forecast facts. Use the existing CANOPY draw/crop path; do not build a plug-in renderer, unrestricted style system, or new asset.
- **Planned verification:** Render the definition-declared baseline and olive treatments at the same OX-05 place, selected instant, weather input, and depth; inspect installed captures and compare visible foliage treatment and unchanged weather meaning. The OX-39-specific behavior supplement `.codex/concepts/OX-175-OX-39-canopy-treatment-behavior-board.dot` selects baseline/olive independently; the shared UI artwork remains schematic and does not choose those asset variants. Limit implementation to this approved capability/treatment pair.
- **Evidence:** `.codex/test-artifacts/175-pack-platform-readiness/OX-39-visual-treatment.md`, `OX-39-baseline.png`, `OX-39-alternate-treatment.png`, and the matching installed-state hierarchy captures.
- **Compatibility/docs:** If the change alters a pack-facing capability or renderer-policy contract, stop and record the required compatibility decision before changing it. Update `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` only for a concrete contract change; do not silently version or broaden the public registry.

#### OX-40 — Substitution parity between existing worlds

- **Scope:** Compare First City CANOPY with the adapted left Open Landscape tree group from OX-92 for a supported equivalent role. Preserve shared weather meaning and forecast access without requiring identical artwork or Park implementation.
- **Planned verification:** Use the same selected instant and representative supported, unknown/unavailable, or carried source states where the existing inputs allow. Compare the two installed worlds, including exact visible facts and the affected scene element; list unsupported capabilities as gaps rather than assuming parity.
- **Owner scope decision (2026-11-23):** Keep OX-40 focused on the approved vegetation-role comparison and forecast facts. Record the standard timeline screen's Open Landscape cloud-bank visibility difference as an observation, with OX-45 scene-only evidence as context; do not treat it as a capability failure or make a missing-capability claim.
- **Evidence:** `.codex/test-artifacts/175-pack-platform-readiness/OX-40-cross-world-comparison.md`; supported-rain captures `OX-40-first-city-supported-rain.png` and `OX-40-open-landscape-supported-rain.png`; carried-rain/missing-cover captures `OX-40-first-city-carried-rain-missing-cover.png` and `OX-40-open-landscape-carried-rain-missing-cover.png`; matching hierarchy captures, replay/device records, and chooser hierarchies in the same evidence directory.
- **Compatibility/docs:** Record capability gaps and any concrete renderer compatibility change in the existing renderer guide. Do not add a generalized cross-world abstraction.

#### OX-79 — Weather-truth matrix across packs

- **Scope:** TEST supplied/supported, carried, and unavailable weather semantics across the bundled and installed worlds at one exact selected instant. Use the established existing-world route; no Moon or Park-art prerequisite.
- **Planned verification:** Run a six-row matrix (three source states × two worlds) where those source states are available from the accepted fixtures. Record selected instant, source-valid time when carried, units, shown/unavailable meaning, catalog identity, and visible forecast facts before and after switching. Mark any unavailable fixture or device case not run rather than substituting an invented value.
- **Evidence:** `OX-79-weather-truth.md`, `OX-79-weather-matrix.csv`, `OX-79-weather-snapshots.json`, `OX-79-bundled-supported.png`, `OX-79-bundled-carried.png`, `OX-79-bundled-unavailable.png`, `OX-79-installed-supported.png`, `OX-79-installed-carried.png`, and `OX-79-installed-unavailable.png`.
- **Compatibility/docs:** Do not change forecast projection, units, weather-source interpretation, or catalog revision. Evidence may identify a separate follow-up but does not authorize it.

#### OX-93 — First-generation local-platform readiness

- **Scope:** Reconcile the local build/import, catalog and selection restoration, Settings, switching/recovery, cross-pack truth, accessibility, and measured-cost records within the dependencies. Carry OX-98's owner-directed speech-verification exclusion as NOT VERIFIED and make no audible-output claim. Decide only the first-generation local theming-platform milestone.
- **Planned verification:** Create an owner-facing matrix linking each row to its source slice and artifact; distinguish proven, failed, not-run, and blocked. Record the owner's readiness decision or leave it pending. No aggregate score, hidden threshold, release approval, paid-distribution judgment, or Park/Moon completion claim.
- **Evidence:** `OX-93-readiness.md`, its linked dependency evidence index, and any owner decision recorded in the cycle history.
- **Compatibility/docs:** Update only the readiness record and relevant roadmap status after evidence exists. Do not change independent slice exits based on an aggregate conclusion.

#### OX-59 — Scrub and switch overlap

- **Scope:** TEST one bounded overlap between active timeline scrubbing and a theme switch. OX-32's ordinary switch contract remains the baseline; do not add controls or permanent chrome.
- **Planned verification:** Perform a focused installed interaction with an explicit scrub/switch sequence. Record event ordering and confirm the final scene and exact selected instant agree, with no stale selection or gesture theft.
- **Evidence:** `OX-59-scrub-switch.md`, `OX-59-scrub-switch.mp4` or `OX-59-event-order.txt`, plus `OX-59-before.png` and `OX-59-after.png`.
- **Compatibility/docs:** No gesture-system refactor is in scope unless the focused interaction demonstrates an in-scope defect. Keep OX-32's ownership contract unchanged.

#### OX-60 — Theme-specific visual-resource lifecycle

- **Scope:** Replace active theme image/animation/hit-region resources together with the scene. Do not add an unrelated cache or graphics framework.
- **Planned verification:** Repeat installed free ↔ alternate switches and inspect that only current-theme layers, targets, and animation state remain. Verify old targets no longer respond and identify the owner/lifetime of replaced resources from the affected code path.
- **Evidence:** `OX-60-resource-lifecycle.md`, `OX-60-resource-ownership.txt`, `OX-60-free-alternate-free.mp4`, and `OX-60-final-state.png`.
- **Compatibility/docs:** Keep resource ownership inside the existing scene/theme boundary. Record a separate follow-up for any broader memory/cache redesign.

### Documentation and compatibility work

- Preserve existing contracts in `docs/SCENE_PACK_INSTALLATION.md` and `docs/INSTALLED_THEME_CATALOG.md` while implementing durable catalog restoration, persisted selection, and fallback. Update only the specific ownership/restore behavior that actually changes.
- Preserve the registry boundaries in `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md`. OX-39 must not change public capability meaning or version without a recorded, reviewed compatibility decision.
- OX-29 may clarify the existing developer-local artifact build/import procedure; test records and artifact hashes remain the source for what actually passed. OX-96/OX-73 measurements and limits stay in their cycle evidence unless a stable product contract is intentionally changed.
- Any documentation change must stay within the active slice's approved scope and be reviewed against its own exit. No document in this plan authorizes commercial, release, or cross-application behavior.

### Review findings and execution risks

- **Resolved gate — OX-09 board coverage:** The OX-09-only behavior supplement and revised UI artwork supplement cover same-world repeats in First City and Open Landscape, a world switch at the same selected instant, and return to First City. The revised LIVE-state artwork does not require a `DEMO DATA` badge or add provenance UI. The roadmap objective and exit remain unchanged. After the revised board was displayed, the owner approved the complete shared-plus-supplement UI package on 2026-11-23; its fingerprints are recorded on OX-09 only.
- **Context audit limitation:** This cycle has 17 slices and a broad named read set. The active environment does not expose a context-window size or compatible tokenizer, so no per-slice percentage or exact-token estimate is recorded and no context-driven split is claimed. Keep execution reads and checks scoped to the active slice; revisit the split question if measurable context pressure appears.
- **Reference-device availability:** Use the recorded Pixel 3 XL for device acceptance. If it is unavailable at execution time, mark the affected device checks `not run` rather than substituting another device.
- **Resolved repository-grounded ambiguity — OX-96 workload and method:** The prior wording did not pin the fixture, world/package pair, or timing boundaries, risking incomparable observations across selected-time rendering, first pack resolution, and switching. Use the fixed OX-05 fixture and its two review instants, the OX-32 First City ↔ `first-city-canopy-baseline` pair, and the recorded Pixel 3 XL. Android Studio System Trace plus `gfxinfo` frame observations measure input-to-present and frame/jank behavior; `meminfo` captures before/after process snapshots. OX-55's instrumentation test measures isolated decode/candidate allocation and does not stand in for the integrated screen. Current app code has no internal candidate-resolver trace span, so resolver-only duration remains unmeasured unless a repeatable runtime marker is present; preserve that limitation before proposing instrumentation. Repository basis: `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugOx05DepthOrderingFixture.kt`, `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt`, `app/src/androidTest/java/com/oxygen/weather/timeline/presentation/scene/Ox55RendererLoadMemoryMeasurementTest.kt`, `scripts/dev.py`, `.codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md`, `.codex/test-artifacts/164-ox55-pack-renderer/README.md`, and `docs/SPECIFICATION.md`. The existing OX-96 exit is observational, so no numeric threshold or owner decision is added.
- **OX-73 reuse:** OX-73 can reuse the OX-96 device/build identity, pack pair and content identity, fixture/selected-instant setup, and comparable trace/frame boundaries. It must capture its own repeated-switch samples and measure cold restore from the OX-61 persisted selection as a separate process-launch-to-restored-frame condition; the one-off OX-96 timings are baseline context only.
- **Measurement and fixture limits:** If OX-96/OX-73 measurement boundaries cannot be collected repeatably, record the observed limit without adding an unmeasured threshold. If OX-79 source-state fixtures are unavailable, record those rows `not run` rather than substituting invented weather facts.
- **Resolved source/disclosure ambiguity — OX-09:** Preflight found that the connected Pixel 3 XL runs `CatalogTimeLabScreen` over a `LIVE` Open-Meteo catalog (`forecast-44d`, Chicago), with no `DEMO DATA` label in its screenshot or accessibility hierarchy. The owner selected and approved revision of the OX-09 UI artwork for LIVE states. The approved board keeps the repeatability sequence and visible forecast facts, removes the LIVE-state badge requirement, and adds no provenance UI. No application source was changed. The revised package was displayed and its complete fingerprints are recorded; execution stopped before any repeatability sample. Preflight evidence is under `.codex/test-artifacts/175-pack-platform-readiness/OX-09-preflight.md`.
- **Resolved implementation-contract ambiguity — OX-61:** The owner selected the existing failure contract after inspection showed `CatalogWorldChoice` falls back to bundled First City when reloading the already-active installed entry fails, while existing focused tests assert this behavior. OX-61 saves the stable pack ID only after a valid candidate commit; a failed reload retains the saved key for retry. The updated behavior supplement distinguishes that case from a different failed candidate, which leaves the previous world and key unchanged. No OX-32 fallback behavior is changed.
- **Resolved board ambiguity — OX-39:** On 2026-11-23 the owner selected the existing olive CANOPY asset as the alternate. The OX-39 behavior supplement maps Treatment A to the baseline asset and Treatment B to the olive asset, with identity, role, pass, geometry, weather input, and forecast facts held constant. The supplement was displayed and approved for OX-39 only; the shared board package remains unchanged for other slices.
- **Renderer compatibility boundary:** If OX-39 requires a change to a public capability or renderer-policy meaning/version, stop before that change and record the compatibility decision needed for separate review.

### Planning limits and handoff

OX-09 is closed DONE with installed comparison evidence and a no-change disposition. OX-73 is closed DONE with repeated-switch and cold-restore measurements. OX-39's owner-resolved baseline/olive treatment pair is installed and captured at the fixed OX-05 state. The next dependency-eligible slice is OX-40; its cross-world comparison retains its own exit and evidence requirements.
