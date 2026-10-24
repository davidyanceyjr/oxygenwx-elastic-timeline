# AGENTS.md — Oxygen Time Lab

This is an isolated experimental application based on oxygenWX's workflow and build scaffold, not the production oxygenWX repository. The production navigation contract is unchanged.

## Authority

1. `docs/SPECIFICATION.md` — this experiment's product and semantic boundaries.
2. `docs/ROADMAP.md` — bounded ordered follow-ups.
3. `docs/UI_DEVELOPMENT_WORKFLOW.md` — installed visual acceptance.
4. `.codex/current.md` and referenced bounded plan — cycle status.

## Cycle lifecycle

Before changing production-like Compose code: read the docs above, then `python scripts/dev.py workflow`. Maintain a bounded plan under `.codex/plans/`, preserve evidence under `.codex/test-artifacts/<cycle>/`, and close into `.codex/history/`. Do not claim visual/device acceptance without installed screenshots and applicable tests.

## Architecture rules

- Data fixtures use canonical metric units; only presentation performs display conversion.
- UI depends on typed projection values, never provider DTOs.
- Zoom is not forecast time. The selected timestamp is authoritative and must not jump because of a zoom animation.
- Preserve honest DEMO DATA labels and distinguish source-supplied observations from interpolated visual values.
- No network, location, alert, caching, or production app integration in this prototype.
- A prototype horizontal timeline must not be introduced into oxygenWX's outer pager without explicit redesign and gesture ownership review.

## Verification

`python scripts/dev.py workflow`, `contract`, `test`, `build`, then install/capture actual UI on a device. Follow separate full-suite verification policy when integration becomes production work. `git diff --check` and inspect the code before closing a cycle.
