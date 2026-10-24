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
history.** Treat compaction as context hygiene, not feature work.

## Inspect and classify

First discover the repository's own instructions and workflow. Inspect its
agent guidance, startup/read instructions, state pointers, roadmap or ordered
work, plans, blocker lists, decision records, implementation notes, verification
summaries, cycle/history records, archives, and evidence indexes. Use references
and content to infer each area's role; do not classify by age or filename
alone. Check the working tree and available version-control status before
proposing changes.

Before modifying files, produce a reviewable analysis that identifies relevant
files or sections and classifies each as:

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
active-context reduction when practical. Prefer a dry run if repository tools
provide one. The default is analysis only: do not edit until the user explicitly
asks to apply the compaction. An explicit apply request authorizes the scoped
context changes described by the analysis; it does not authorize unrelated
product work or destruction of repository history.

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
Use a compact status and history reference where the local convention permits;
do not silently reorder or change scope.

For plans, archive or otherwise separate only plans that are verifiably
complete. Never archive active, planned, blocked, partially complete, or
current-pointer plans. Preserve a historical copy and keep active plan
discovery unambiguous.

Keep only unresolved blockers in the active blocker view. Record resolved
outcomes durably and promote any continuing constraint or decision before
removing resolved blocker narrative from routine context.

Treat screenshots, logs, test output, and generated reports as on-demand
context unless project policy says otherwise. Do not delete required evidence
to reduce what agents read; adjust read policy or add precise references where
safe.

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

When asked to apply, make changes in this order:

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

Report what was compacted and archived, what was retained, what authority was
promoted, which files remain routine context, and which historical areas should
be read on demand. Include validation performed, unresolved risks or owner
decisions, and before/after line or byte counts when available. Treat these
counts as context-size proxies; do not claim exact token savings unless they
were measured.
