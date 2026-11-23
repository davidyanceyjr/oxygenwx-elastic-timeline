# Cycle plan — 174-weather-rendering-across-worlds

State: CLOSED
Plan maturity: FINAL
Cycle ID: `174-weather-rendering-across-worlds`
Roadmap items: OX-07, OX-45, OX-08, OX-46, OX-47
Objective: Extract and reuse cloud and precipitation rendering across First City and Minimal Open Landscape while preserving shared weather decisions and declared composition depth.

## Cycle roster and board approvals

The structured roster records each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval. The activation
command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "174-weather-rendering-across-worlds",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-07",
      "depends_on": [
        "OX-36",
        "OX-38"
      ],
      "status": "DONE",
      "exit": "Installed First City day/rain comparison shows no unapproved visual change; the capability can be called from a declared cloud element.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
              "sha256": "f7dc400e17a1cb43738d32c929e5d01d928e5a06679a39cbcdd90d57e0205680"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
              "sha256": "dfdc331a7179d50ded298810360da1997864c5d26bced22e0c5bb7d98ce059e7"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
              "sha256": "2213766519d4476011dd5e6b3e80ff75d2b19310432c7bc40b09c06c3633a413"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
              "sha256": "c31963e8095d071476ca7481a2dc4e68e429e9c7109b2668e023ce1e62018966"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
              "sha256": "ceb107e6c351b6ece08f59feb3a44f85ab4dc3813b1b598fc35269c03b38011e"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
              "sha256": "0a174a0feb26f5c2351bf1a67826b95a74ab24de64a9b4736ee203b5c2f0a5cd"
            }
          ]
        }
      },
      "outcome": "Extracted First City cloud drawing behind a typed input resolved from the declared CLOUD_COVER element, preserving both original ordered passes and the shared selected-time cloud decision. Installed First City day and supported-rain comparisons on the Pixel 3 XL show no unapproved cloud or scene-output change; selected time remains 8:00 AM CDT for the day capture.",
      "evidence": [
        ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-before-day-sg11-sky-20261028T1300Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-after-day-sg11-sky-20261028T1300Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-before-rain-sg4-20261004T1530Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-after-rain-sg4-20261004T1530Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-before-day-picker.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-before-day-main.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-after-day-picker.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox07-after-day-main.xml"
      ]
    },
    {
      "id": "OX-45",
      "depends_on": [
        "OX-07",
        "OX-92",
        "OX-04"
      ],
      "status": "DONE",
      "exit": "Installed two-world samples share weather decisions; all visible cloud output in checked states remains in the top 33% of the atmospheric scene viewport; unknown cloud coverage is not fabricated.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
              "sha256": "f7dc400e17a1cb43738d32c929e5d01d928e5a06679a39cbcdd90d57e0205680"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
              "sha256": "dfdc331a7179d50ded298810360da1997864c5d26bced22e0c5bb7d98ce059e7"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
              "sha256": "2213766519d4476011dd5e6b3e80ff75d2b19310432c7bc40b09c06c3633a413"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
              "sha256": "c31963e8095d071476ca7481a2dc4e68e429e9c7109b2668e023ce1e62018966"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
              "sha256": "ceb107e6c351b6ece08f59feb3a44f85ab4dc3813b1b598fc35269c03b38011e"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
              "sha256": "0a174a0feb26f5c2351bf1a67826b95a74ab24de64a9b4736ee203b5c2f0a5cd"
            }
          ]
        }
      },
      "outcome": "Declared Minimal Open Landscape cloud cover through the OX-07 capability while preserving its authored cloud-bank placement. Installed Pixel 3 XL checks at SG4 2026-10-04T15:30:00Z show supported cloud/rain in both worlds with clouds in the top third; at 16:30Z both worlds retain carried rain from 10:00 AM CDT and show no cloud cover. World switching preserves the selected 11:30 AM CDT input.",
      "evidence": [
        ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-supported-rain-first-city-20261004T1530Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-supported-rain-open-landscape-20261004T1530Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-carried-rain-missing-cover-first-city-20261004T1630Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-carried-rain-missing-cover-open-landscape-20261004T1630Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-replay-picker-sg4-canonical-gaps-v8.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-carried-rain-picker-selection-20261004T1630Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-carried-rain-missing-cover-first-city-20261004T1630Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-carried-rain-missing-cover-open-landscape-20261004T1630Z.xml"
      ]
    },
    {
      "id": "OX-08",
      "depends_on": [
        "OX-07",
        "OX-36",
        "OX-04"
      ],
      "status": "DONE",
      "exit": "Focused installed First City supported-rain and dry comparisons preserve baseline behavior and selected-time reading; rain is callable from the scene declaration.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
              "sha256": "f7dc400e17a1cb43738d32c929e5d01d928e5a06679a39cbcdd90d57e0205680"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
              "sha256": "dfdc331a7179d50ded298810360da1997864c5d26bced22e0c5bb7d98ce059e7"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
              "sha256": "2213766519d4476011dd5e6b3e80ff75d2b19310432c7bc40b09c06c3633a413"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
              "sha256": "c31963e8095d071476ca7481a2dc4e68e429e9c7109b2668e023ce1e62018966"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
              "sha256": "ceb107e6c351b6ece08f59feb3a44f85ab4dc3813b1b598fc35269c03b38011e"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
              "sha256": "0a174a0feb26f5c2351bf1a67826b95a74ab24de64a9b4736ee203b5c2f0a5cd"
            }
          ]
        }
      },
      "outcome": "Extracted First City rain drawing behind its declared PRECIPITATION_FIELD input. Installed supported-rain, carried-rain/missing-cover, and clear/dry comparisons preserve the existing cloud-base origin, no-cloud fallback, selected-time reading, and no-rain dry state; rain is invoked through the declaration-backed renderer. Snow and other-world paths remain outside this slice.",
      "evidence": [
        ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-before-supported-rain-sg4-20261004T1530Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-supported-rain-sg4-20261004T1530Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-before-supported-rain-main-20261004T1530Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-supported-rain-main-20261004T1530Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-carried-rain-missing-cover-first-city-20261004T1630Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-carried-rain-missing-cover-sg4-20261004T1630Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-carried-rain-main-20261004T1630Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-before-dry-sg4-20261004T1700Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-dry-sg4-20261004T1700Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-before-dry-main-20261004T1700Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-dry-main-20261004T1700Z.xml"
      ]
    },
    {
      "id": "OX-46",
      "depends_on": [
        "OX-08",
        "OX-36"
      ],
      "status": "DONE",
      "exit": "Installed snow and no-snow comparisons show correct OX-18-driven state and unchanged essential data access in the existing world.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
              "sha256": "f7dc400e17a1cb43738d32c929e5d01d928e5a06679a39cbcdd90d57e0205680"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
              "sha256": "dfdc331a7179d50ded298810360da1997864c5d26bced22e0c5bb7d98ce059e7"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
              "sha256": "2213766519d4476011dd5e6b3e80ff75d2b19310432c7bc40b09c06c3633a413"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
              "sha256": "c31963e8095d071476ca7481a2dc4e68e429e9c7109b2668e023ce1e62018966"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
              "sha256": "ceb107e6c351b6ece08f59feb3a44f85ab4dc3813b1b598fc35269c03b38011e"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
              "sha256": "0a174a0feb26f5c2351bf1a67826b95a74ab24de64a9b4736ee203b5c2f0a5cd"
            }
          ]
        }
      },
      "outcome": "Added a separate declared First City snow renderer consuming OX-18's shared SceneWeatherDecision. Installed SG11 snow and supported-clear comparisons at 2026-10-28T13:00:00Z preserve the existing snow marks, sunny/no-snow state, selected 8:00 AM CDT reading, and forecast facts. Snow renderer adds no accumulation, intensity, interpretation, or world migration.",
      "evidence": [
        ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-before-snow-sg11-20261028T1300Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-after-snow-sg11-20261028T1300Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-before-snow-main-20261028T1300Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-after-snow-main-20261028T1300Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-before-clear-sg11-20261028T1300Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-after-clear-sg11-20261028T1300Z.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-before-clear-main-20261028T1300Z.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-after-clear-main-20261028T1300Z.xml"
      ]
    },
    {
      "id": "OX-47",
      "depends_on": [
        "OX-45",
        "OX-46",
        "OX-92",
        "OX-04"
      ],
      "status": "DONE",
      "exit": "Installed two-world supplied rain/snow, dry and unavailable cases preserve depth, source truth, responsiveness and essential exact forecast access; no Park composition dependency.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
              "sha256": "f7dc400e17a1cb43738d32c929e5d01d928e5a06679a39cbcdd90d57e0205680"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
              "sha256": "dfdc331a7179d50ded298810360da1997864c5d26bced22e0c5bb7d98ce059e7"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
              "sha256": "2213766519d4476011dd5e6b3e80ff75d2b19310432c7bc40b09c06c3633a413"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
              "sha256": "c31963e8095d071476ca7481a2dc4e68e429e9c7109b2668e023ce1e62018966"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
              "sha256": "ceb107e6c351b6ece08f59feb3a44f85ab4dc3813b1b598fc35269c03b38011e"
            },
            {
              "path": ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
              "sha256": "0a174a0feb26f5c2351bf1a67826b95a74ab24de64a9b4736ee203b5c2f0a5cd"
            }
          ]
        }
      },
      "outcome": "Added Open Landscape-local typed rain and snow declarations in the PRECIPITATION pass after both tree groups, routing through the existing shared renderers and SceneWeatherDecision. Installed before/after captures in First City and Open Landscape show unchanged rain, snow, dry, and unavailable scene output and forecast facts; independent clouds remain supported and unavailable condition evidence produces no precipitation marks. Qualitative scrubbing updates the selected-time reading in both worlds, and the exact selection survives world switching. No Park path or dependency changed.",
      "evidence": [
        ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-first-city.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-first-city.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-world-switch-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-world-switch-open-landscape.xml",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-open-landscape.png",
        ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-open-landscape.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-23",
    "summary": "Completed cycle 174 across OX-07, OX-45, OX-08, OX-46, and OX-47. First City and Minimal Open Landscape now use the approved typed cloud and precipitation render paths while retaining their scene-local composition. OX-47 installed comparisons cover supplied rain, supplied snow, supported dry, and unavailable condition states in both worlds; source facts remain readable, unavailable condition evidence produces no precipitation marks, and qualitative scrubbing updates the selected-time state without changing the exact selection on world switch. The owner explicitly confirmed choice A on 2026-11-23: the recorded board approval stands and the approval-pending banner is stale; board artifacts and fingerprints were left unchanged. No Park path was added.",
    "verification": [
      "PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py workflow — PASS in EXECUTE and CLOSEOUT; closeout validation also passed after archiving.",
      "PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py contract — PASS: isolated package and pure projection contract.",
      "PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py run --serial 8AKY0M32X — PASS; Gradle BUILD SUCCESSFUL, install succeeded, MainActivity launched.",
      "PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/174-weather-rendering-across-worlds/<capture-name>.png — PASS for the 16 before/after matrix screenshots (eight before and eight after) and three scrub/world-switch screenshots; all planned state pairs were visually inspected.",
      "adb shell uiautomator dump /sdcard/<capture-name>.xml and adb pull /sdcard/<capture-name>.xml .codex/test-artifacts/174-weather-rendering-across-worlds/<capture-name>.xml — PASS for every planned matrix capture and scrub interaction.",
      "git diff --check — PASS.",
      "No tests were added or run; the plan and repository instructions require separate owner authorization for tests."
    ],
    "evidence_directory": ".codex/test-artifacts/174-weather-rendering-across-worlds/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "174-weather-rendering-across-worlds",
  "slice_ids": [
    "OX-07",
    "OX-45",
    "OX-08",
    "OX-46",
    "OX-47"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.png",
    ".codex/concepts/OX-174-reusable-weather-rendering-behavior-board.md",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg",
    ".codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.png"
  ],
  "completed_gates": [
    "OX-07 is the sole eligible NEXT; OX-45, OX-08, OX-46, and OX-47 remain PLANNED behind declared dependencies.",
    "OX-07_behavior_board_approved",
    "OX-07_ui_artwork_board_approved",
    "OX-45_behavior_board_approved",
    "OX-45_ui_artwork_board_approved",
    "OX-08_behavior_board_approved",
    "OX-08_ui_artwork_board_approved",
    "OX-46_behavior_board_approved",
    "OX-46_ui_artwork_board_approved",
    "OX-47_behavior_board_approved",
    "OX-47_ui_artwork_board_approved",
    "All five slices have approved behavior and UI artwork packages with complete fingerprints.",
    "Reviewed each slice's scope, dependencies, callers, compatibility boundaries, evidence targets, and exit against the roadmap and current interfaces.",
    "Confirmed OX-45 and OX-08 are both after OX-07 without a dependency between them; OX-47 follows both OX-45 and OX-46.",
    "Finalized the plan with all required board and exit records; cycle remains PLANNED and unactivated.",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "OX-45 and OX-46 are DONE; OX-47's declared dependencies are satisfied. The OX-08 rain and OX-46 snow interfaces are the accepted in-cycle reusable capabilities.",
    "On 2026-11-23 the owner explicitly chose A: the recorded 2026-11-22 board approval stands and the approval-pending banner is stale. The approved board package and its recorded fingerprints remain unchanged."
  ],
  "open_questions": [],
  "completed_outputs": [
    "After 60 seconds without a reply to the board discrepancy prompt, applied recommended choice A and resumed; the owner later explicitly confirmed A on 2026-11-23. The approved board package and fingerprints remain unchanged.",
    "Closed slice OX-08 as DONE.",
    "Closed slice OX-46 as DONE.",
    "Closed slice OX-47 as DONE.",
    "Completed OX-47 cross-world precipitation declarations and installed acceptance; all cycle roster slices have terminal DONE outcomes.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox45-supported-rain-first-city-20261004T1530Z.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox08-after-supported-rain-sg4-20261004T1530Z.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox46-after-snow-sg11-20261028T1300Z.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-rain-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-rain-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-snow-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-snow-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-dry-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-dry-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-before-unavailable-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-after-unavailable-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-first-city.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-first-city.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-world-switch-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-world-switch-open-landscape.xml",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-open-landscape.png",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/ox47-scrub-after-drag-open-landscape.xml",
    ".codex/history/174-weather-rendering-across-worlds.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/174-weather-rendering-across-worlds.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/174-weather-rendering-across-worlds/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/MinimalOpenLandscapeComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SceneElementDeclaration.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SceneWeatherDecision.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "scripts/codex_cycle.py"
  ],
  "next_action": "Review git diff and diff checks, run final workflow validation, then archive the completed cycle with exact verification results and limitations."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Board decision and planning basis

The product owner approved both complete shared board packages on 2026-11-22. The cycle manifest stores the same complete file sets and SHA-256 fingerprints independently on each of OX-07, OX-45, OX-08, OX-46, and OX-47. The owner reviewed each package once; the shared packages make every roster slice's behavior and exit independently reviewable.

- **Behavior board:** `.codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot`, `.svg`, `.png`, and `.md`. It governs the selected-time decision path, each slice's declared rendering boundary and state coverage, OX-03 rain-origin behavior, the cloud upper-third envelope, and the OX-47 world-local depth proof.
- The behavior board's rendered heading and Markdown contract retain their submission-stage “owner review pending” wording. The product owner approved the exact unchanged package with that wording present; the current approval state and fingerprints are recorded in this manifest and in `docs/ROADMAP.md`. Do not edit or regenerate approved package files within this cycle without renewing approval.
- **UI artwork board:** `.codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg` and `.png`. It shows the affected First City and Minimal Open Landscape states for all five slices. It is conceptual artwork for scope review, not an installed screenshot or acceptance evidence.
- **Approval constraints:** Preserve existing world appearance/composition outside the approved rendering extraction and wiring, retain the full cloud-output bound, keep rain and snow illustrative, and keep unavailable condition states distinct from clear. The conceptual artwork does not replace per-slice installed acceptance.

The shared objective is bounded to extracting/reusing existing cloud and precipitation rendering in First City and Minimal Open Landscape. The ordered roster is OX-07 → OX-45 → OX-08 → OX-46 → OX-47. OX-45 and OX-08 are both eligible after OX-07; their roster order does not make OX-45 a prerequisite of OX-08. OX-46 follows OX-08, and OX-47 follows both OX-45 and OX-46. External dependencies remain the exact resolved outcomes listed on the roadmap and below.

### Current renderer callers and compatibility boundary

The target is the selected-time catalog composition path used by `CatalogTimeLabScreen` and the debug-only OX-18 scene review activity. The separate `AtmosphericScene(AtmosphereInput/SceneVisualState)` route is outside this cycle. No world picker, product split-screen, or selection route change is included.

- **OX-07:** `CatalogAtmosphericScene(FirstCityComposition)` currently calls `drawCityDepthCloudLayer` from both `BACKGROUND_CLOUDS` and `MIDDLE_GROUND_CLOUDS`. Extract one reusable cloud capability while preserving the existing two First City passes, their order, the shared `CityCloudDecision`, and First City-authored style.
- **OX-45:** `CatalogOpenLandscapeScene(MinimalOpenLandscapeComposition)` currently calls `drawOpenLandscapeClouds`. Route that declared cloud element through the capability from OX-07 with Open Landscape-authored style and placement; keep the scene's decision input and local composition.
- **OX-08 and OX-46:** The First City `PRECIPITATION` pass currently calls `drawCityRainField` or `drawCitySnowField`. Add the rain and snow capability entries in their own slices. Those drawing helpers also have direct Open Landscape call sites today; preserve that existing output and call behavior until OX-47 performs the scene-local declaration wiring.
- **OX-47:** Update `CatalogOpenLandscapeScene` to use the reusable precipitation capabilities through its own declaration after the two tree groups. Keep First City's established pass order and both worlds' selected-time decision path unchanged.

There are no persisted-data, provider, catalog-schema, or public API migrations. During execution, inspect the named callers and any directly affected tests; no other route or consumer is migrated by implication.

Dependency outcomes governing this plan:

- **OX-36 DONE:** the First City cloud-cover slot pairs the `SceneWeatherDecision` result with selected instant and typed source evidence; supported cover and missing cover remain distinct, and `SceneWeatherDecision` remains the interpreter.
- **OX-38 DONE:** First City `CANOPY` consumes a typed declaration, with pixel-identical installed fixed-scene comparison and unchanged draw order/shared weather decision.
- **OX-92 DONE:** First City `CANOPY` and one Minimal Open Landscape tree group share a typed declaration shape while retaining world-local drawing paths and the same selected-instant decision.
- **OX-18 DONE:** both existing worlds consume one selected-time `SceneWeatherDecision`. Its evidence includes separate captures for the supported-rain state at `2026-10-04T15:30:00Z`; focused source tests also cover eligible carried rain at `2026-10-04T16:30:00Z` and unavailable conditions at `2026-10-28T20:00:00Z`.
- **OX-03 / OX-04 DONE:** visible rain starts at the visible cloud base and retains the established no-cloud fallback; missing/unsupported/probability-only condition evidence creates no condition-derived precipitation, and independent supported fields remain independent.

### Shared scope and invariants

- All render inputs originate in the existing exact selected-time projection and one `SceneWeatherDecision`. Keep the selected instant authoritative and unchanged when changing world or rendering. World-specific style and placement stay with each world.
- Preserve supplied, carried, probability-only, expected-missing, and supplied-empty evidence meaning. Do not infer rain or snow from probability, rate, accumulation, unrelated fields, or plausibility. Condition-derived artwork is absent when condition evidence is unavailable; independently supported cloud cover may still render.
- Treat rain and snow marks as illustrative. Do not label their artwork as a rate, amount, accumulation, or measurement.
- Keep the entire visible cloud output inside the top 33% of the atmospheric scene viewport, including edges, blur, shadow, movement, and transitions. Rain and snow may extend below that region.
- Preserve First City composition/order and the existing Minimal Open Landscape order (sky/clouds, terrain, first tree group, second tree group, precipitation). OX-47 may declare the existing open-landscape precipitation depth locally; it does not create a universal layer executor.
- Keep forecast facts, labels, source provenance, exact-time reading, timeline ownership, gesture behavior, and accessibility semantics available and unobscured in the affected states.

### Per-slice plan

#### OX-07 — Extract the First City cloud rendering capability

- **Dependencies:** OX-36 and OX-38, both DONE.
- **Scope:** Put existing First City cloud drawing behind a typed reusable renderer entry that consumes the already-resolved cloud decision plus First City-authored style. Make the entry callable through a declared cloud element. Keep the overall Canvas composition and current visual result.
- **Exclusions:** No overall Canvas rewrite, cloud decision logic, raw provider inputs, new cloud facts, second-world wiring, asset pack, or weather/timeline changes.
- **Exit:** The roadmap's installed First City day/rain comparison has no unapproved visual change, and the capability is callable from a declared cloud element.
- **Proposed installed states:** A daytime First City state from the existing `sg11-sky-typed-v2` replay at `2026-10-28T13:00:00Z` (Chicago 08:00 local) and supported rain from `sg4-canonical-gaps-v8` at `2026-10-04T15:30:00Z`. Capture the same input before and after extraction for comparison.

#### OX-45 — Use clouds in Minimal Open Landscape and verify the upper bound

- **Dependencies:** OX-07, OX-92, and OX-04; OX-07 is earlier in this cycle and the external dependencies are DONE.
- **Scope:** Call the reusable cloud capability from the existing Minimal Open Landscape composition, retaining the landscape's own style and placement. Verify all visible cloud output in the checked states stays inside the top-third envelope.
- **Exclusions:** No Park work, change to shared weather decisions, fabricated fallback cloud, or general scene framework.
- **Exit:** Installed two-world samples use the same weather decision, all checked cloud output including effects and movement stays in the top 33%, and unknown cloud coverage is not fabricated.
- **Proposed installed states:** Compare First City and Minimal Open Landscape separately at the same selected instant for supported cloud cover and supported rain at `2026-10-04T15:30:00Z`, then for the missing-cover/carried-rain case at `2026-10-04T16:30:00Z`. At the latter instant, verify the absent cover remains absent in both worlds while the independently eligible carried-rain decision retains its source time. Inspect the full rendered cloud envelope in each checked state.

#### OX-08 — Extract the reusable rain rendering capability

- **Dependencies:** OX-07, OX-36, and OX-04; OX-07 is earlier in the roster, while OX-36 and OX-04 are DONE.
- **Scope:** Move existing First City rain drawing behind a declared precipitation-element renderer. Preserve OX-03's visible-cloud-base origin and the already-established fallback when no cloud is visible.
- **Exclusions:** No snow extraction, rain-rate inference, probability-to-rate conversion, or new no-cloud behavior.
- **Exit:** Focused installed First City supported-rain and dry comparisons preserve baseline behavior and selected-time reading, and rain is callable from the scene declaration.
- **Proposed installed states:** Supported rain at `2026-10-04T15:30:00Z`, eligible carried rain with missing cloud cover at `2026-10-04T16:30:00Z`, and supported clear/dry conditions at `2026-10-04T17:00:00Z`, all using the SG4 replay. Capture matching pre-/post-extraction supported-rain and dry states. Check cloud-base placement when clouds are visible, the existing fallback when they are not, and no rain marks for the dry state.

#### OX-46 — Extract the reusable snow rendering capability

- **Dependencies:** OX-08 and OX-36; OX-08 is earlier in the roster and OX-36 is DONE.
- **Scope:** Give snow its own compatible precipitation capability, reusing only interface behavior evidenced by OX-08. Continue to use `SceneWeatherDecision` as the state owner.
- **Exclusions:** No generalized particle runtime, weather interpretation, new accumulation or intensity claim, or world migration.
- **Exit:** Installed snow and no-snow comparisons show the OX-18-driven state and unchanged essential data access in the existing world.
- **Proposed installed states:** Use `sg11-snow-mixed-typed-v2` at `2026-10-28T13:00:00Z` for supplied snow and `sg11-sky-typed-v2` at the same selected instant for supported clear/no-snow. Render through the normal catalog scene path, not a standalone synthetic-snow preview.

#### OX-47 — Prove cross-world precipitation depth and unknown states

- **Dependencies:** OX-45, OX-46, OX-92, and OX-04; OX-45 and OX-46 are earlier in the roster and the external dependencies are DONE.
- **Scope:** Wire the reusable rain and snow capabilities into the existing Minimal Open Landscape through scene-local precipitation declarations. Preserve each world's composition and compare each world separately using the same catalog and selected instant for each state pair.
- **Exclusions:** No Park composition or occlusion polish, world-picker change, general particle runtime, provider/catalog change, or product split-screen.
- **Exit:** The roadmap's installed two-world rain, snow, dry, and unavailable cases preserve depth, source truth, responsiveness, and essential exact forecast access, with no Park dependency.
- **Proposed installed state matrix:** Capture both First City and Minimal Open Landscape at `2026-10-04T15:30:00Z` for supplied rain; at `2026-10-28T13:00:00Z` using the snow replay; at `2026-10-28T13:00:00Z` using the clear replay for supported dry; and at `2026-10-28T20:00:00Z` using `ox01f-condition-unavailable-typed-v1` for unavailable condition evidence. For every pair, confirm the same selected instant and decision/source evidence, scene-local draw depth, no condition-derived marks in the unavailable state, and continued access to the exact forecast facts. Independent supported cloud cover may remain in the unavailable-condition state.

### Verification, authorization, and evidence

This review authorizes plan finalization, workflow validation, and diff inspection only. It does not authorize app implementation, adding or running tests, building/installing the app, or collecting installed screenshots. A later execution request must authorize implementation and installed acceptance work; tests remain separately authorized only when the owner explicitly asks for them.

For a later owner-authorized execution, the proposed scoped acceptance is:

- At implementation boundaries, validate with `python scripts/dev.py workflow` and run `python scripts/dev.py contract` for the affected architecture paths. Use `python scripts/dev.py run --serial <serial>` to build/install the debug app for installed review, then capture with `python scripts/dev.py screenshot --serial <serial> --output .codex/test-artifacts/174-weather-rendering-across-worlds/<state>.png`. Record the selected replay, instant, device/build, and actual command results in the cycle README. These proposed commands do not authorize tests.
- Capture and inspect the installed states and before/after comparisons listed under each slice on the existing debug catalog path. The visual acceptance must come from the installed app; board artwork, source inspection, or preview output is not a substitute.
- Review only affected in-scope interactions: exact selected-time retention, ordinary world switching where used for separate captures, and readable/accessibly exposed forecast facts in those states. For OX-47, scrub through the sampled interval in both worlds and record whether scene updates remain responsive while the selected instant stays authoritative; this is a qualitative scoped observation, not a numeric performance claim. Do not expand this into RTL, TalkBack, or a broad system/device matrix; those belong to dedicated TEST slices.
- Inspect the changed declarations and renderer paths against the exact shared decision and depth invariants. Add or run focused tests only if the owner separately authorizes them; no test command is pre-approved by this plan.
- Record actual captures, selected replay IDs/instants, interaction results, and exact verification commands under `.codex/test-artifacts/174-weather-rendering-across-worlds/README.md`. The cycle closeout target is `.codex/history/174-weather-rendering-across-worlds.md`.

The expected installed evidence is at least the named per-slice states above: OX-07 day and rain before/after; OX-45 supported and missing-cover cloud states in both worlds; OX-08 supported rain, carried rain with no visible cloud, and dry; OX-46 snow and no-snow; and OX-47 rain/snow/dry/unavailable captures in both worlds. These are acceptance targets, not evidence already collected.

### Compatibility, risks, and open questions

The plan changes only the existing rendering boundaries in the two named worlds. It leaves selection, forecast projection, provider/catalog semantics, timeline gestures, world controls, and saved data unchanged. The existing debug replays provide exact review inputs; during execution, evidence must record the active replay and selected instant so visually similar states cannot be confused. The snow acceptance uses the ordinary catalog scene route and must not be replaced by a standalone preview.

No material scope or board decision remains open. The exact selected review inputs above are sourced from existing OX-18/OX-36 evidence and debug catalog fixtures. This plan is **FINAL** at `PLANNED` / `REVIEW`; it is not activated and does not authorize implementation or tests.
