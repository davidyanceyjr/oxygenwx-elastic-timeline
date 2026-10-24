# Persistent experiment record

Same development discipline as oxygenWX: scoped specification, ordered roadmap, one active cycle pointer, bounded plans, closed history, actual evidence not guessed screenshots.

- `current.md`: IDLE or active pointer.
- `plans/`: unresolved plans and checklists.
- `history/`: completed authoring cycles with evidence and limits.
- `test-artifacts/`: device captures/logs kept locally, normally gitignored.

Keep roadmap slice IDs and cycle IDs separate. A slice ID is the stable roadmap
outcome (for example, `P.2.9`); a cycle ID is the unique, chronological
execution record (for example, `021-p2-acceptance-closure`). Each cycle names
the slice it addresses. Assign the next cycle number when planning work,
regardless of the slice number or roadmap position. Adding or splitting a
slice does not reserve, skip, or renumber cycle IDs. Store its plan, evidence,
and closed history under that cycle ID so the records can be followed together.

Use `python scripts/dev.py workflow` to inspect context before editing.
