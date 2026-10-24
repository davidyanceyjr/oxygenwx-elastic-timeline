# Suggested Codex intake: adopt Oxygen's experience specification

You are working **only** in the `oxygenwx-elastic-timeline` repository, not the original `oxygenWX` repository.

1. Read `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/current.md`, the relevant active plan, and the newly provided `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`.
2. Compare the proposed experience specification against the **latest working tree**, not merely the public snapshot. Produce a concise discrepancy report before adopting. In particular: verify current uniform-hourly expansion, current center-swipe and double-tap behavior, and whether any controls have been removed since the cited snapshot.
3. Follow the repository's existing `python3 scripts/dev.py workflow` and `.codex` bounded-cycle process. Create a **documentation/specification adoption** cycle (not an unapproved UI refactor).
4. Decide and record the authority relationship between `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`. Do not silently overwrite conflicting requirements; flag design decisions needing owner approval.
5. Update minimal related roadmap/README references only if they are actually stale. Preserve historical `.codex/history/` facts.
6. Run the project's appropriate workflow/contract/documentation checks; inspect the diff and record commands that could not run. Do not claim Android UI verification unless an installed device check actually runs.
7. Present the final adopted document paths, an authority summary, changes made, evidence, and any unresolved owner decisions. Do not implement candidate UX enhancements during this adoption cycle.
