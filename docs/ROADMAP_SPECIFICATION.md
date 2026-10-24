# Roadmap Specification

This document defines how `docs/ROADMAP.md` orders and closes work in Oxygen Time Lab. It adopts the useful control structure from oxygenWX while keeping this prototype's roadmap small.

## Purpose and relationship to cycle records

The roadmap is the ordered, durable list of bounded work slices. A roadmap slice states the outcome and the evidence required to close it. It does not replace the active cycle plan, implementation notes, or history:

- A **slice ID** (such as `P.2.9`) identifies a stable roadmap outcome. Keep it unchanged when ordering or inserting other slices.
- A **cycle ID** (such as `021-p2-acceptance-closure`) identifies one unique, chronological bounded execution. It is independent of the slice ID and roadmap position; never derive it from the slice number.
- Each cycle names the slice it addresses. A later bounded attempt at the same slice receives a new cycle ID; an inserted or split slice gets its own slice ID and receives a cycle ID only when work is planned.
- `.codex/current.md` identifies the active cycle, its slice, and plan.
- `.codex/plans/` holds the bounded execution plan for an active or planned cycle.
- `.codex/test-artifacts/<cycle-id>/` holds actual evidence, including device captures.
- `.codex/history/` holds closed cycle outcomes, verification performed, and limitations.

Assign the next cycle number after the latest cycle record. Do not reuse a cycle
ID or renumber existing cycles when inserting roadmap work. Include both the
cycle ID and slice ID in plan and history metadata, and use the cycle ID in the
plan, evidence, and history paths. This keeps roadmap state distinct from the
execution chronology while preserving a traceable record across the Git diff.

Roadmap changes do not silently expand an active slice. Scope or ordering changes are recorded in the active plan/history and reflected in the roadmap before dependent work proceeds.

## Status vocabulary

Every leaf slice has exactly one status:

- **NEXT** — the sole recommended slice to activate next. It has no unresolved dependency. A roadmap may retain one NEXT while another slice is ACTIVE.
- **ACTIVE** — currently being executed. At most one roadmap slice is ACTIVE, and it must correspond to the active cycle in `.codex/current.md`.
- **PLANNED** — ordered work that is not the next slice and has no active blocker.
- **DONE** — the defined exit was met and its evidence is recorded in `.codex/history/`.
- **BLOCKED** — the exit cannot currently be met because of a recorded external, host, or dependency condition. State the exact blocker and evidence; do not start dependent work.
- **DEFERRED** — intentionally outside the current execution path. State the reason and a concrete re-entry condition.

Use a workstream heading to group slices; workstream headings are not slices and do not receive statuses. There must be no more than one NEXT and no more than one ACTIVE across the roadmap. When a cycle is ACTIVE, the NEXT slice is the first eligible follow-up after it closes.

## Required format for every leaf slice

Use a heading with a stable identifier, concise name, and status, followed by these fields:

```markdown
### P.2.1 — Example bounded outcome — PLANNED

**Objective/scope:** One concrete result and the boundary of the work.
**Depends on:** A slice ID, or `None`.
**Exit:** Observable conditions that must all be met to call this slice done.
**Evidence:** Exact target artifact directory and history file; for completed work, link the existing records.
```

Identifiers remain stable after completion. Split a slice when it contains independently closable outcomes or cannot be completed within one bounded cycle. Ordered follow-ups are separate slices; do not create a dependency unless the later slice is genuinely blocked without the earlier outcome.

## Exit and evidence rules

- Every NEXT, ACTIVE, PLANNED, and BLOCKED slice has a testable or inspectable **Exit**. A DEFERRED slice states its exit to apply after re-entry.
- The exit says what observable evidence closes the work, not merely which actions to perform. For an audit, the exit can be a recorded finding and evidence-backed disposition, including a confirmed blocker.
- A DONE slice names the cycle history and applicable artifact path. History must say what was actually verified and identify material gaps. Never infer acceptance from a build or a guessed screenshot.
- Planned evidence paths are targets, not claims that evidence already exists.
- Device or visual acceptance requires installed screenshots and the applicable interaction/accessibility checks specified by the product and UI workflow authorities.

## Failure, blockers, and replanning

Attempt a slice's exit within one bounded cycle. If the exit fails or an external decision is missing, record the exact result, blocker, and evidence in the cycle history; close the cycle without marking the slice DONE. Mark it BLOCKED when progress depends on that unresolved condition, and stop dependent work. Resume only after the condition changes and the roadmap/plan explicitly records the bounded next action. Do not create an automatic retry chain or silently widen the slice.

## Deferred work

Deferred work is not an implied commitment or an active dependency. Give it a reason and a concrete re-entry condition, plus the exit that will govern it if reactivated. Re-entry requires an explicit roadmap update that assigns ordering and status.
