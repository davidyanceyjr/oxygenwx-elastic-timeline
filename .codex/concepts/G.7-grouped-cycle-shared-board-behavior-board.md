# G.7 grouped-cycle and shared-board behavior contract

**Status:** Owner review draft for cycle 170. This board package is a proposal; it is not approved and does not authorize implementation.

## Proposed workflow

1. Group slices only when they are related and share one bounded objective. Keep unrelated outcomes in separate cycles.
2. Keep the roster dependency-ordered. Its first slice is the sole eligible `NEXT`; later roster slices are `PLANNED`. Put in-cycle dependencies earlier. External dependencies must be resolved before activation.
3. Use one shared behavior or UI board package only if it lets the owner review each included slice's behavior and acceptance independently. Otherwise, prepare separate packages.
4. The owner reviews the complete package. Every covered roster entry keeps its own objective, dependency list, exit, authorization, status, evidence, and a verifiable approval/fingerprint record for the package covering it. For a shared package, the proposed tracker use is to record the same approved package files and hashes on each covered slice entry.
5. Draft and review one cycle plan while preserving each slice's acceptance. Activate only after every required board is approved and the plan is final. Execute in dependency order, with at most one `ACTIVE` slice. Close each slice on its own exit or exact terminal outcome; close the cycle after all entries have outcomes.
6. Keep existing single-slice cycle records readable and unchanged.

## Tracker facts checked for this proposal

- `scripts/codex_cycle.py start` accepts repeated `--slice`/`--roadmap` arguments. It requires the first roster item to be `NEXT` and later items to be `PLANNED`.
- Manifest validation checks unique IDs, exact roadmap dependency records, in-cycle dependency order, and resolved external dependencies (`DONE`, `CANCELLED`, or `SUPERSEDED`).
- Board approval is recorded per slice and kind. The tracker stores SHA-256 fingerprints for every supplied package file and validates them against the files at later workflow gates.
- The versioned manifest keeps separate per-slice exits, statuses, board records, outcomes, and evidence. Existing single-slice examples and workflow tests remain present in `scripts/tests/test_codex_cycle.py`.

These are source observations, not claims that tests were run for cycle 170.

## Owner decision requested

For one shared package, this proposal treats one owner review of the complete package as covering every slice clearly represented in it, then records that approval and the same file fingerprints separately on each covered roster entry. Please approve this relationship or specify a different approval rule.

## UI artwork disposition

No app UI or visual output changes are in G.7's roadmap scope. The behavior board covers project workflow and CLI guidance; no UI artwork board is proposed.
