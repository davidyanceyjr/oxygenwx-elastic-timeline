# SE.3 — Scene Engine proposal-roadmap alignment behavior board

**Owner direction (2026-11-12):** The owner agreed to align the proposed Scene Engine roadmap with three recommendations: keep Moon inspection conditional, account explicitly for the scene-graph/keyframe/event-expansion candidates, and preserve the approved top-33% cloud boundary in OX-07. This board specifies that documentation-only change; it does not adopt the OX workstream or approve an implementation slice.

| Proposed-roadmap area | Approved content rule | Acceptance reading |
|---|---|---|
| OX-10 | Evaluate the Moon as a candidate first inspect element. The later OX-10 boards select the Moon or record another first element and corresponding follow-up. | The proposal does not preselect the Moon or imply an interaction has been approved. |
| §36 candidate coverage | Add a compact map from the spec's candidate outcomes to proposed OX slices or a deferred, separately bounded decision. Clarify that OX-03/OX-05 address scene-graph decisions and OX-17 measures whether day compilation/keyframe restructuring is needed; weather-event expansion remains separate and deferred. | No omitted candidate silently becomes an OX acceptance obligation. No new engine work is authorized by the map. |
| OX-07 | State that cloud extraction preserves SG.4a's top-33%-of-scene viewport boundary, including visible edges, shadow, blur, and motion poses, with scoped evidence. | The existing approved cloud rule is carried forward, not reopened or weakened. |
| Source basis | Update the proposal's review date/basis to include the 2026-11-12 SE.1 spec clarification. | Readers can see which spec wording the proposal reconciles with. |

**Scope:** Edit only `docs/Oxygen_Scene_Engine_Refactor_Roadmap.md` and required SE.3 workflow records. No app code, UI artwork, tests, builds, or installed visual acceptance.
