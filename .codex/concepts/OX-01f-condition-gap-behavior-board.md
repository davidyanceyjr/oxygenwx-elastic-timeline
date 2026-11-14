# OX-01f — Continuous debug condition, behavior board

Owner decisions: give the 3 PM SG.11 replay a supported condition, keep the unavailable diagnostic separate, use the existing typed-event label, and author the same typed event at 4 PM.

## Source and selected-time contract

| Selected Florence local time | Authored/source condition | Common visible condition decision | Source-time disclosure |
|---|---|---|---|
| 3:00 PM | 3 PM `PARTLY_CLOUDY`, typed `PARTLY_SUNNY_CLOUDY` event | Partly sunny and cloudy; matching symbol, scene, overlay, center, and semantics | 3:00 PM |
| 3:30 PM exact midpoint | 3 PM authored fact owns the tie | Same 3 PM decision | 3:00 PM |
| Just after 3:30 PM | 4 PM supplied `PARTLY_CLOUDY` fact with typed `PARTLY_SUNNY_CLOUDY` event owns the representative cell | Partly sunny and cloudy across all condition surfaces | 4:00 PM |
| 4:00 PM | 4 PM supplied `PARTLY_CLOUDY` with typed `PARTLY_SUNNY_CLOUDY` event | Partly sunny and cloudy across all condition surfaces | 4:00 PM |

The selected instant remains exact. Representative-cell boundaries describe display ownership, not a reported weather transition minute or continuous source observation. All condition surfaces use one support-aware selected condition decision. Typed events retain their source record; broad conditions are not falsely reported as typed events. Temperature, wind, probability, and other fields keep their independent source times and support.

## Diagnostic separation and scope

- The existing 3 PM empty-event/empty-condition row is removed from the storm sequence replay. Supported partly cloudy and its matching typed event are authored at both 3 PM and 4 PM. The seven SG.11 storm event samples stay as authored.
- A separate debug replay or explicit debug route retains a supplied-empty condition for unavailable-state inspection. It is labeled diagnostic and cannot replace the continuous storm replay by accident.
- OX-01f changes only this fixture/replay and the directly affected selected-condition display path. It does not perform OX-02 renderer fallback consolidation or alter general catalog support rules.
- Installed review scrubs 3:00, 3:30, just after 3:30, and 4:00; checks overlay, center, symbol, scene, accessible condition semantics, selected instant, and source-time labels. The separate unavailable route is captured. No tests are added or run without owner authorization.
