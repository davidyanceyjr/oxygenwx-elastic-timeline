---
name: compact-history
description: Review and safely compact stale project workflow context while preserving active work, durable authority, auditability, and recovery paths.
---

# Compact project history

Reduce the routinely loaded project context that accumulates in roadmaps,
plans, blockers, notes, evidence indexes, and agent instructions. Apply this to
the repository at hand; do not assume particular filenames, directories, or a
specific planning tool.

The governing principle is: **active context should contain information that
can change the next decision; completed execution detail belongs in durable
history.** The goal is to materially reduce the routine working set without
losing decision-relevant meaning, evidence, or recovery paths. Use concise
summaries and durable links so completed detail can remain available on demand
without loading it routinely. Treat compaction as context hygiene, not feature
work.

## Inspect and classify

First discover the repository's own instructions and workflow. Inspect its
agent guidance, startup/read instructions, state pointers, roadmap or ordered
work, plans, blocker lists, decision records, implementation notes, verification
summaries, cycle/history records, archives, and evidence indexes. Use references
and content to infer each area's role; do not classify by age or filename
alone. Check the working tree and available version-control status before
proposing changes.

Running this skill always begins with a **read-only analysis phase**. During
this phase, do not edit, create, move, rename, truncate, or delete repository
files; do not change metadata or invoke commands that can mutate the repository,
device, or external state. Read-only inspection and demonstrably read-only
checks are allowed; identify any such checks in the report.

Produce a reviewable analysis that identifies relevant files or sections and
classifies each as:

- **ACTIVE** — current or near-future work, unresolved matters, or information
  that could still change a planning or implementation decision.
- **AUTHORITY** — durable requirements, invariants, architecture, interfaces,
  policies, or decisions that continue to govern future work.
- **HISTORICAL** — completed execution details, resolved blockers, closed
  plans, verification outcomes, evidence references, or reconstruction notes.
- **SUPERSEDED** — explicitly replaced material whose successor is identifiable.
- **DUPLICATED** — material already preserved adequately in another durable,
  recoverable location.

State proposed actions, protected material, authority that must be promoted,
the recovery path for every proposed relocation or reduction, and an estimate of
active-context reduction when practical. Identify any policy prerequisites,
unresolved status conflicts, and read-only validation that is still needed.
Prefer a dry run if repository tools provide one.

Present this analysis as a proposed change report, then **stop and wait for the
user's explicit approval before applying any compaction**. Running the skill,
requesting an audit, or asking for recommendations is not approval to edit.
Approval authorizes only the specific changes listed in the report. If new
scope or necessary changes arise during application, stop and present a revised
report for approval before proceeding. If approval is not given, leave the
repository unchanged. This approval gate controls whether and what to change; it
does not replace the compaction objective. After approval, carry out the listed
changes to reduce routine context while preserving its meaning and recovery.

When applying changes, follow the repository's required planning, approval,
and evidence workflow, including for documentation-only work where it applies.
Preserve required workflow rules, commands, build configuration, and cycle
allocation semantics. Do not change application code, build files, or workflow
tools as part of context compaction. If a proposed read-guidance change could
weaken a required workflow gate, retain the existing instruction and report the
constraint instead.

## Protect ongoing work and authority

Never remove, truncate, relocate, or summarize away active or planned but
incomplete work, unresolved blockers or decisions, unsatisfied acceptance
criteria, live dependencies, or requirements that still govern future behavior.
Preserve ordering and dependency meaning. When status is ambiguous, retain the
material in active context and report the uncertainty for owner judgment.

Completed records may contain lasting rules or lessons. Before compacting such
records, promote those points into the appropriate durable authority (for
example, architecture guidance, specifications, interface documentation,
contributor instructions, or decision records). Verify the promoted statement
is present and applicable before reducing its source narrative. Do not keep an
entire completed narrative in routine context solely to retain one enduring
rule.

## Choose a safe compaction

Keep the routine working set focused on applicable authority, current state,
the active plan, unresolved blockers, and the current roadmap frontier. Keep
completed execution detail in durable history and load it only when needed for
reconstruction, provenance, dependency understanding, regression analysis, or
an explicit audit.

For roadmaps, preserve every unfinished item, its order, dependencies, blocked
state, and acceptance obligations. Keep a clear completed frontier and useful
identifiers/titles. Reduce completed prose only when completion and acceptance
are supported by the record and the detailed outcome is recoverable elsewhere.
Keep the canonical ordered roadmap when project policy requires one. Compact
completed slices to their stable IDs, titles, statuses, and useful history or
evidence references where the local convention permits; do not remove their
identity, silently reorder work, or change scope. Archive repeated DONE-slice
narrative only when the closed cycle record remains discoverable and supports
the completion claim.

For plans, archive or otherwise separate only plans that are verifiably
complete. Never archive active, planned, blocked, partially complete, or
current-pointer plans. Preserve a historical copy and keep active plan
discovery unambiguous. A stale copy under an open-plans directory may be
removed or relocated only after confirming its cycle is closed and its durable
archived plan and closeout exist. If a roadmap marks work DEFERRED while a plan
calls it PLANNED, retain it and report the mismatch for owner resolution; do
not infer that the plan is disposable.

One-time intake prompts and draft roadmaps are archive candidates only after
their task is complete or their proposal is explicitly superseded. Confirm that
the successor or closeout is identifiable, any continuing requirements have
been promoted to current authority, and references are updated or remain
resolvable. Preserve audit records on demand when they document how the
canonical roadmap or authority was established. Do not archive a still-used
specification merely because its proposal or workstream is old.

Do not archive prior board or artwork revisions based only on version numbers
or filenames. Check approval records and historical references first; preserve
the approved artifact and any earlier version needed to reconstruct a decision.
Byte-identical duplicates may be proposed for removal after hash and reference
checks, with the retained copy identified.

Keep only unresolved blockers in the active blocker view. Record resolved
outcomes durably and promote any continuing constraint or decision before
removing resolved blocker narrative from routine context.

Treat screenshots, logs, test output, and generated reports as on-demand
context unless project policy says otherwise. This skill changes context
organization, not evidence retention: do not delete, relocate, or offload actual
evidence required at its project-defined path. Adjust read policy or add precise
references where safe. Any offloading policy needs separate project authority
that defines an auditable, recoverable destination before evidence is moved.

Inspect startup and agent instructions for broad rules that repeatedly load
archives, closed plans, history, or evidence. Narrow routine reads to active
state and durable authority, while retaining explicit conditions for opening
historical material. Do not weaken governing instructions or omit a currently
applicable requirement.

## Establish recovery before reducing detail

Use version control as one recovery source when available, not as the only
historical record. Check whether affected material is committed or otherwise
recoverable; do not discard uncommitted information. Do not rewrite or destroy
version-control history. Without version control, require another durable,
discoverable recovery copy before removing detailed historical material from
an active record.

Prefer lossless structural changes: move completed plans, separate active and
historical sections, replace duplicated narrative with a stable history link,
or update read guidance. Summarize only when relocation or indexing is not
adequate. Keep references resolvable and preserve identifiers, dates, status,
verification limits, and evidence locations needed for audit or reconstruction.

## Apply and validate

Only after the user explicitly approves the proposed change report, apply the
listed changes in this order:

1. Promote enduring knowledge to durable authority.
2. Move or compact completed details while preserving their recovery path.
3. Reduce duplicated completed roadmap/history prose without changing future
   ordering or obligations.
4. Narrow routine read guidance where appropriate.
5. Update pointers and references affected by moves.

Then verify that:

- all active and unfinished work, ordering, criteria, and dependencies remain
  represented;
- unresolved blockers and decisions remain visible;
- current-state and active-plan pointers resolve and still identify the right
  work;
- promoted authority exists before its source detail was compacted;
- every reduced or relocated record has a valid recovery path, and required
  evidence remains available;
- no uncommitted information was lost.

Run the repository's workflow validator and applicable formatting/diff checks.
If no validator exists, perform the strongest structural checks available,
including checking references, status consistency, and document syntax. Review
the complete final diff. Do not add unrelated tests or implementation changes.

## Report the result

During analysis, report the proposed compaction and exact affected paths,
protected material, recovery paths, required approvals or prerequisites,
read-only checks performed, unresolved decisions, and estimated context-size
reduction when practical. Then stop for approval.

After approved application, report what was compacted and archived, what was
retained, what authority was promoted, which files remain routine context, and
which historical areas should be read on demand. Include validation performed,
unresolved risks or owner decisions, and before/after line or byte counts when
available. Treat these counts as context-size proxies; do not claim exact token
savings unless they were measured.
