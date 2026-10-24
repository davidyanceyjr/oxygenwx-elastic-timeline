---
name: audit-planned-context
description: Audit a PLANNED Oxygen Time Lab slice for context pressure and split over-budget work into ordered partial plans before activation.
---

# Audit planned context

Audit the current Oxygen Time Lab implementation plan for expected execution context use.
This is a planning operation only. Do not activate a cycle or edit app code.

## Workflow

1. Read `AGENTS.md`, `docs/SPECIFICATION.md`,
   `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/current.md`,
   `.codex/README.md`, and the referenced plan. Run
   `python scripts/dev.py workflow`.
2. Require `.codex/current.md` to be `PLANNED` and point to a plan with
   `State: PLANNED`. If it is IDLE or ACTIVE, report that this audit cannot
   change a plan and stop. Do not replace the current plan or create a new
   roadmap slice.
3. Establish the execution context budget from the model and context-window
   size explicitly named in the plan or confirmed by the active environment.
   Use the context limit, not an output-token limit. If the available window
   cannot be established, do not invent a percentage: report the missing input
   and leave the plan unchanged.
4. Estimate context consumed by execution, excluding this planning/audit turn
   and any context guaranteed to be reset before implementation. Include
   repository discovery, plan/spec reading, implementation reasoning, tests,
   debugging/integration, documentation, and evidence reporting. Count input
   and generated tokens against the same window. Use a conservative estimate
   and report the estimate in tokens, the available window, the percentage,
   and the main assumptions. Where only a range is credible, use its upper end
   for the threshold decision.
5. If estimated usage is **65% or less**, record the audit result in the plan
   under a `## Context audit` heading (create it if absent): model/window,
   estimated execution tokens, percentage, estimate basis, date, and result
   `PASS — no split required`. Keep the plan and roadmap ordering otherwise
   unchanged. Run `python scripts/dev.py workflow` again.
6. If estimated usage is **greater than 65%**, divide the implementation into
   the smallest series of coherent, sequential slices whose conservative
   estimates are each at or below 65%; prefer comfortably below 65% when a
   clean boundary exists. Do not target exactly 65% merely to maximize scope.
   Preserve all original acceptance obligations across the series. For each
   slice, document its expected execution-token estimate, percentage, scope,
   outcome, boundary, invariants, steps, acceptance criteria, verification and
   evidence, dependencies, risks, and exclusions. Each slice must leave a
   coherent, reviewable state; later slices may depend on completed earlier
   slices, but earlier slices must not require unfinished later work to
   function correctly. Avoid duplicated work; repeat only context needed to
   make the dependency explicit.

## Split identity and files

- Preserve the original plan's numeric cycle identifier and base plan ID for
  every generated sub-slice. Name them with tracker suffixes in sequence:
  `<original-id>-partial-A`, `<original-id>-partial-B`, and onward. For
  example, `103-weather-source-mapping-partial-A` and
  `103-weather-source-mapping-partial-B`. Keep the same numeric prefix; do not
  allocate unrelated cycle numbers.
- Write one plan file per sub-slice under `.codex/plans/`, using the original
  ID plus its `partial-*` suffix. Do not overwrite or discard the unsplit
  source plan: preserve it as the audit input (rename it with `-original` if
  needed) and clearly cross-reference all generated plans.
- Each partial plan is a planned implementation, not a completed outcome.
  State its sequence, predecessor/successor, prerequisite condition, and
  individual context estimate. Keep statuses `Planned` until the normal
  workflow selects and executes them.
- Do not create fake independent cycle records that collide on the shared
  numeric ID. Explain in the audit record that roadmap insertion and normal
  cycle activation happen one partial at a time.

## Roadmap and current-cycle handling

- Do not silently rewrite roadmap order. Prepare a concise insertion block for
  the generated partial entries, preserving existing dependencies and exit
  criteria, and identify the exact roadmap section where the owner-requested
  head insertion belongs. If the roadmap schema is clear and this skill run is
  explicitly asked to perform the split, insert the partial sequence at the
  head of the eligible work for that track; do not move unrelated entries.
  Otherwise include the ready-to-apply block and state that insertion remains
  pending.
- Point `.codex/current.md` to the first eligible partial only if it is the
  next planned implementation under the normal lifecycle. Keep it `PLANNED`;
  never activate it. Later partials remain in `.codex/plans/` and the roadmap
  but are selected only after their predecessor closes.
- When the first partial takes over the current pointer, update its plan path,
  cycle ID, roadmap item, evidence path, and objective consistently. Do not
  leave `current.md` pointing at the original broad plan.
- If the split reveals an unresolved product, architecture, or ordering choice
  that cannot be derived from repository authority, stop before changing the
  plan/roadmap/current pointer. Report the specific decision needed; do not
  invent scope to force the split.

## Split method

Partition by a real implementation boundary, in dependency order. Suitable
boundaries may include a contract/design decision followed by implementation,
a shared foundation followed by consumers, or a coherent subsystem followed
by an independently verifiable next subsystem. Do not split by arbitrary task
count, file count, or equal prose length. For each boundary, verify:

- the work has a distinct observable result and an explicit handoff;
- dependencies flow forward and no circular dependency is introduced;
- the predecessor has meaningful acceptance criteria and verification of its
  own;
- deferred work is removed from the predecessor's steps and criteria;
- all original invariants and final outcomes remain covered across the full
  series;
- every slice's context estimate includes its required project context,
  validation, likely failure investigation, and evidence work.

If no defensible boundary can put the workload below the threshold, report the
specific coupling and leave the plan unchanged for owner review.

## Finalization

1. Add an audit record to the preserved source plan or a companion audit note.
   Include the window basis, estimate/range and assumptions, threshold result,
   split rationale, generated plan paths, sequence, roadmap insertion state,
   and current-cycle pointer result.
2. Check that every generated plan contains the required headings from
   `.codex/README.md` and carries explicit context estimate metadata.
3. Run `python scripts/dev.py workflow` and `git diff --check`; inspect the
   final diff. Do not run app tests or activate a plan as part of this
   planning audit.
4. Report the exact window estimate, whether a split occurred, plan paths,
   roadmap/current pointer changes, verification, and any remaining owner
   action. Do not claim the work is implementation-ready beyond what was
   actually reviewed.
