# Cycle plan — 077-bt1-configuration-cache

State: ACTIVE — reviewed; approved behavior board recorded; ready to activate
Cycle ID: `077-bt1-configuration-cache`
Roadmap: BT.1
Objective: Enable Gradle's configuration cache for this project and verify the common debug build and lint task graphs store and reuse entries.

## Readiness and board gate

- `.codex/current.md` was IDLE when this cycle was selected; `python scripts/dev.py workflow` reported `Workflow scaffold: OK`.
- Approved behavior board: `.codex/concepts/BT.1-configuration-cache-behavior-board.png` (editable `.svg`, detailed `.md` at the same stem). The product owner approved it in this conversation on 2026-10-31, with no additional constraints stated. The board reviews first-run storage, repeat-run reuse, invalidation, failure behavior, and a one-off opt-out. It governs this plan's scope and exit. No UI artwork board applies because no application UI or visual output changes.

## Production boundary

- Add `org.gradle.configuration-cache=true` to root `gradle.properties` after approval. No other production or build-script changes are planned.
- Preserve unrelated working-tree edits, including the closed SG.1 records and parked WX.27 work.

## Functional invariants

- Gradle's default configuration-cache problem handling remains fail, not warn. The cache may be disabled for a single diagnostic command with `--no-configuration-cache`.
- `scripts/dev.py` keeps using the wrapper; no task selection, build output, app package, or runtime behavior changes.
- No plugin updates, committed cache state, or application implementation changes.

## Steps after board approval and activation

1. Rerun `python scripts/dev.py workflow`; inspect the baseline diff and confirm the board approval is recorded in this plan.
2. Add the one property to `gradle.properties`.
3. Run `python scripts/dev.py build` twice and `python scripts/dev.py lint` twice. Record first-run storage and second-run reuse for both task graphs, including exact command output. Do not run automated tests unless separately requested.
4. Inspect `git diff --check`, the final scoped diff, and the cycle evidence. Close only if both task graphs work with the project setting and show reuse; otherwise record the exact blocker and leave BT.1 unfinished.

## Acceptance and evidence

- The root property is enabled without changing other Gradle settings.
- Build and lint both complete under the project property, store a configuration entry on first use, and report reuse on repeat use. The first invocation may reuse the exploratory command-line entry if Gradle considers it equivalent; in that case the saved output must still demonstrate the enabled setting and reuse, and a fresh-entry run must be obtained by a safe input change or targeted cache invalidation before closeout.
- Evidence target: `.codex/test-artifacts/077-bt1-configuration-cache/README.md` with exact outputs; closeout targets: `.codex/history/077-bt1-configuration-cache.md` and `.codex/history/plans/077-bt1-configuration-cache.md`.
- No device or UI acceptance is required; no tests are added or run.

## Risks and exclusions

- Plugin compatibility could differ for task graphs beyond debug build and lint; this cycle makes no unverified full-suite claim. A configuration-cache problem fails the build and is investigated within this bounded scope.
- No Gradle/plugin version upgrades, cache warning suppression, test task execution, release-task acceptance, or app behavior changes.
