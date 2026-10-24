# Roadmap specification and migration — cycle 008

## Outcome

Added `docs/ROADMAP_SPECIFICATION.md` with a lightweight roadmap status vocabulary, mandatory leaf-slice fields, evidence and exit rules, deferred-work re-entry, and failure/replanning behavior. Rewrote `docs/ROADMAP.md` so all 18 leaf slices follow that format, retained completed evidence and unresolved acceptance, and identified one NEXT slice. Updated `AGENTS.md` authority to include the new specification.

## Checks

- `python scripts/dev.py workflow` — passed.
- `git diff --check` — passed.
- A one-off structural review counted all roadmap leaf headings and checked required fields and status counts; 18 slices, one ACTIVE (`G.1`, matching `.codex/current.md`) and one NEXT (`P.1.3`).
- No app source, build, test, or device behavior was changed or verified in this documentation-only cycle.

## Preserved context

The existing uncommitted visual hard rule in `docs/ROADMAP.md` and `docs/SPECIFICATION.md` was retained. The cycle-007 device baseline and its later hourly long-press capture remain under `.codex/test-artifacts/007-visual-baseline/`.
