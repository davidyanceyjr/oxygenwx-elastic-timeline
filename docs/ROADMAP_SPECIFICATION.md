# Roadmap Specification

This document defines how roadmaps order and close work in Oxygen Time Lab. It adopts the useful control structure from oxygenWX while keeping this prototype's roadmap small. Its slice-board requirement is project-wide: it applies to every roadmap, workstream, and slice in this repository, including future or newly introduced roadmaps and slices.

## Purpose and relationship to cycle records

The roadmap is the ordered, durable list of bounded work slices. A roadmap slice states the outcome and the evidence required to close it. It does not replace the active cycle plan, implementation notes, or history:

- A **slice ID** (such as `P.2.9`) identifies a stable roadmap outcome. Keep it unchanged when ordering or inserting other slices.
- A **cycle ID** (such as `021-p2-acceptance-closure`) identifies one unique, chronological bounded execution. It is independent of the slice ID and roadmap position; never derive it from the slice number.
- Each cycle names the one or more slices it addresses. A later bounded attempt at the same slice receives a new cycle ID; an inserted or split slice gets its own slice ID and receives a cycle ID only when work is planned.
- `.codex/current.md` identifies the open cycle, its ordered slice roster, current stage, active slice when one exists, and plan.
- `.codex/plans/` holds the bounded execution plan for an active or planned cycle.
- `.codex/test-artifacts/<cycle-id>/` holds actual evidence, including device captures.
- `.codex/history/` holds closed cycle outcomes, verification performed, and limitations.

Assign the next cycle number after the latest cycle record. Do not reuse a cycle
ID or renumber existing cycles when inserting roadmap work. Include the cycle ID
and every slice ID in plan and history metadata, and use the cycle ID in the
plan, evidence, and history paths. This keeps roadmap state distinct from the
execution chronology while preserving a traceable record across the Git diff.

A cycle contains one or more stable slice IDs in dependency order. A single
slice remains the default when outcomes are unrelated. Each roster entry keeps
its own objective, dependencies, approved board package, exit, authorized
verification, status, outcome, and evidence. In-cycle dependencies must appear
earlier in the roster; dependencies outside it must already be resolved. At
most one roster slice is `ACTIVE`. Later slices remain `PLANNED` until all
dependencies are complete and they are selected as the next eligible item.
Cycle 117 is the initial single-slice compatibility example; it does not imply
that unrelated existing roadmap work belongs in one cycle.

## Cycle state and workflow checkpoints

Cycle lifecycle and workflow stage are separate fields:

- **IDLE** — no open cycle.
- **PLANNED** — slice intake, boards, first draft, or review is underway; no
  roadmap slice is `ACTIVE`.
- **ACTIVE** — an eligible slice is being executed, or the cycle is in
  closeout. At most one roster slice and one roadmap slice are `ACTIVE`.
- **PAUSED** — the same cycle is paused at a saved stage. Record `Resume state`
  as `PLANNED` or `ACTIVE`; keep the cycle ID, roster, active slice, plan, and
  roadmap statuses unchanged.

The stage values are `SLICE`, `BOARDS`, `DRAFT`, `REVIEW`, `EXECUTE`, and
`CLOSEOUT`. Starting a cycle begins at `SLICE`. Advance only after saving the
current stage output. Activation requires a final plan and approved required
boards for every roster slice, then marks the first dependency-eligible slice
`ACTIVE` and enters `EXECUTE`. After a slice closes, the tracker may activate
the next eligible roster member. Enter `CLOSEOUT` only after each included
slice is complete or has an exact terminal outcome; a cycle is not `DONE` just
because a stage or command ran.

Every stage boundary stores a durable checkpoint in the current plan with:
cycle ID and ordered slice IDs; current stage and prior cycle state; accepted
boards and completed gates; decisions and open questions; completed outputs;
actual evidence; exact next-stage read-set paths; and the next action. The
current pointer repeats the state, cycle ID, roster, active slice, stage, and
plan path so `python scripts/dev.py workflow` can reject mismatches. A
`PAUSED` pointer also records its prior state. Resume restores that state at
the saved stage without replacing identifiers, losing accepted gates, or
repeating completed work. Reopen an earlier gate only if its approved input
changes or new evidence contradicts it. A pending owner answer is a pause, not
a failed cycle or roadmap blocker.

The cycle plan carries a versioned JSON roster and checkpoint between explicit
`cycle-manifest` and `cycle-checkpoint` markers. The tracker validates unique
IDs, dependency order, one active slice, per-slice approvals and exits, pointer
consistency, checkpoint fields, and roadmap statuses. It preserves readability
of existing single-slice records; closed historical records are not rewritten.
Record a content fingerprint for every file in an approved board package. A
changed or missing approved input invalidates that approval until it is reviewed
and recorded again.

Roadmap changes do not silently expand an active slice. Scope or ordering changes are recorded in the active plan/history and reflected in the roadmap before dependent work proceeds.

## Status vocabulary

Every leaf slice has exactly one status:

- **NEXT** — the sole recommended slice to activate next. It has no unresolved dependency. A roadmap may retain one NEXT while another slice is ACTIVE.
- **ACTIVE** — currently being executed. At most one roadmap slice is ACTIVE, and it must correspond to the active slice in `.codex/current.md`.
- **PLANNED** — ordered work that is not the next slice and has no active blocker.
- **DONE** — the defined exit was met and its evidence is recorded in `.codex/history/`.
- **CANCELLED** — the owner explicitly removed the slice from the roadmap. Preserve the ID and record the decision and reason; do not treat it as unfinished work or a dependency.
- **SUPERSEDED** — an owner-directed roadmap pivot replaced the unstarted outcome with a named successor slice or workstream. Preserve the ID and record the replacement and transition point; do not treat it as unfinished work or a dependency.
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
**Slice board(s):** Path(s) to the objective-specific behavior board and, for UI-related work, UI artwork board; a combined board is acceptable when it clearly covers both.
```

Every slice plan on every project roadmap requires an objective-specific
**slice board package** before the plan can be finalized. This is a project-wide
rule, not specific to any current workstream or roadmap. A **behavior board** makes the product flow and
rules reviewable. It can use finite-state-machine-like flowcharts with labeled
states, transitions, explanatory text, and illustrative images, or an annotated
content map, contract, schema, sequence, or boundary view. Any slice that
changes UI or visual output also requires a **UI artwork board** showing the
intended appearance and relevant states. A combined board is acceptable when
it clearly communicates both behavior and artwork. The product owner must
approve the required board(s); the plan records their paths, approval,
constraints, and how they govern scope and acceptance.
Reviewing flow and artwork together exposes missing states, transitions, and
visual intent before implementation begins.

A plan may be drafted while its board is pending, but it is not final or ready
for activation until the approved board and approval record are present. Mark
the missing board as a blocker in the draft; do not treat a prompt, artifact
target path, existing implementation, or installed screenshot as the board or
its approval. This gate applies to every new or unfinished slice; it does not
retroactively reopen completed historical slices.

Identifiers remain stable after completion. Split a slice when it contains independently closable outcomes or cannot be completed within one bounded cycle. Ordered follow-ups are separate slices; do not create a dependency unless the later slice is genuinely blocked without the earlier outcome.

## Exit and evidence rules

- Every NEXT, ACTIVE, PLANNED, and BLOCKED slice has a testable or inspectable **Exit**. A DEFERRED slice states its exit to apply after re-entry.
- The exit says what observable evidence closes the work, not merely which actions to perform. For an audit, the exit can be a recorded finding and evidence-backed disposition, including a confirmed blocker.
- A DONE slice names the cycle history and applicable artifact path. History must say what was actually verified and identify material gaps. Never infer acceptance from a build or a guessed screenshot.
- Planned evidence paths are targets, not claims that evidence already exists.
- Device or visual acceptance requires installed screenshots and the applicable interaction/accessibility checks specified by the product and UI workflow authorities.

### Slice-local verification

Design and implementation slices verify only behavior, interfaces, values, and presentation states within their stated scope. They do not run or claim app-wide RTL, TalkBack, accessibility, localization, device, or other system/environment matrices. Those cross-cutting checks belong to dedicated roadmap slices explicitly designated `TEST`. A focused interaction or visual check that directly establishes the slice's own exit remains in scope. Keep product-level acceptance obligations in the roadmap, but assign broad verification to the appropriate `TEST` slice rather than repeating it in every design cycle.

## Failure, blockers, and replanning

Attempt a slice's exit within one bounded cycle. If the exit fails or an external decision is missing, record the exact result, blocker, and evidence in the cycle history; close the cycle without marking the slice DONE. Mark it BLOCKED when progress depends on that unresolved condition, and stop dependent work. Resume only after the condition changes and the roadmap/plan explicitly records the bounded next action. Do not create an automatic retry chain or silently widen the slice.

## Deferred work

Deferred work is not an implied commitment or an active dependency. Give it a reason and a concrete re-entry condition, plus the exit that will govern it if reactivated. Re-entry requires an explicit roadmap update that assigns ordering and status.
