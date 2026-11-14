# SE.1 — First-slice wording behavior board

**Owner direction, 2026-11-12:** Apply the two exact wording clarifications below to `docs/Oxygen_Scene_Engine_Refactor_Spec.md`. This board records the owner's requested behavior/content boundary for a documentation-only follow-up.

| Passage | Before | Approved target | Meaning |
| --- | --- | --- | --- |
| §22 first proof | “Preferred first proof of concept: the Moon.” | “The Moon is a candidate first inspect interaction.” | No first interactive element is selected by this draft. Existing supported lunar facts and data limits stay intact. |
| §36 priority introduction | “Possible priorities for a future bounded first engine implementation slice …” | “The following are candidate outcomes for separate bounded Scene Engine slices … The first implementation slice's scope will be chosen later.” | The 13-item list is a workstream candidate list, not one slice's acceptance scope or a fixed sequence. |

**Scope:** Only these two passages and necessary roadmap/cycle records. Preserve the SE.0 reconciliation, controlling forecast semantics, default-scene baseline, and MR.2 boundary. No app, UI, provider, astronomy, or engine implementation change. Verify the exact diff, workflow, and `git diff --check`; do not run tests or builds.
