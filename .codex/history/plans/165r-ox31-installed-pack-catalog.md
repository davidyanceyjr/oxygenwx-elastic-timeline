# Cycle plan — 165r-ox31-installed-pack-catalog

State: CLOSED
Plan maturity: FINAL
Cycle ID: `165r-ox31-installed-pack-catalog`
Roadmap items: OX-31
Objective: Define the application-managed installed-theme catalog abstraction for built-in free themes and compatible local packs, including stable ID/version, display name and validity status. No global filesystem scan, purchase provider or Settings UI.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "165r-ox31-installed-pack-catalog",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-31",
      "depends_on": [
        "OX-55",
        "OX-20"
      ],
      "status": "DONE",
      "exit": "Catalog queries represent the never-removable bundled free world and known compatible/incompatible app-owned entries distinctly, independent of weather interpretation.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.dot",
              "sha256": "7c03a4f3d0cde1c9ac357a78ba840da8f1097319a4934a9849edad656e255c76"
            },
            {
              "path": ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.svg",
              "sha256": "a512268c4efae40b0ce147e82cb7f65c0be372d5170a568a8bc96d337ecafdd1"
            },
            {
              "path": ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.png",
              "sha256": "d1aa736e797bdf3bc3045e97b5d62861e9cd5fc3dcee68300228cd306235fce9"
            },
            {
              "path": ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.md",
              "sha256": "329731737747d725fb6a1082b761ee1391855842859d694eeeaef137e1e454cb"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Defined and compiled the data-only installed-theme catalog composer: it always represents the OX-41 bundled-free role from explicit app-managed metadata and preserves explicit compatible and incompatible app-owned registrations with stable IDs and opaque content/release versions.",
      "evidence": [
        ".codex/test-artifacts/165r-ox31-installed-pack-catalog/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-20",
    "summary": "OX-31 completed: added the data-only installed-theme catalog contract and read-only composer for the always-available bundled-free role plus explicit compatible and incompatible app-owned registrations. The bundled content/release version is explicit app-managed input; no concrete Living Urban Park revision or renderer was invented. Evidence records build, manual review, and limits. OX-57 is NEXT for board and plan preparation; its board remains pending.",
    "verification": [
      "python scripts/dev.py workflow — PASS at CLOSEOUT; cycle 165r active, OX-31 DONE, no active slice",
      "python scripts/dev.py build — PASS; BUILD SUCCESSFUL in 43s, 39 actionable tasks (5 executed, 34 up-to-date), including :app:assembleDebug; no test task or install ran",
      "git diff --check — PASS; no whitespace errors reported in tracked changes",
      "awk whitespace scan of the cycle plan, evidence, new source, and new documentation — PASS; no trailing whitespace",
      "Manual contract review and exact approved-board fingerprint review — PASS as recorded in .codex/test-artifacts/165r-ox31-installed-pack-catalog/README.md",
      "Tests not added or run per plan authorization; no installed visual acceptance claimed or required for this non-visible slice"
    ],
    "evidence_directory": ".codex/test-artifacts/165r-ox31-installed-pack-catalog/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "165r-ox31-installed-pack-catalog",
  "slice_ids": [
    "OX-31"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.dot",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.svg",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.png",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.md"
  ],
  "completed_gates": [
    "OX-31 sole NEXT slice; direct dependencies OX-55 and OX-20 are DONE.",
    "Owner-approved OX-31 behavior-board package is exact and all four fingerprints match; UI artwork is not required.",
    "Catalog-version owner decision and exact-package approval confirmation recorded.",
    "final_plan_reviewed",
    "all_slice_boards_approved",
    "OX-31 exit, boundary, dependencies, invariants, consumers, compatibility, documentation, verification authorization, evidence, and exclusions recorded.",
    "OX-31 exit met: bundled-free metadata remains always available, explicit compatible/incompatible app-owned entries remain distinct, and the catalog has no weather or activation behavior."
  ],
  "decisions": [
    "After the user-specified 60-second no-response window, applied the recommended resolution: require explicit BundledFreeThemeMetadata at the catalog composer boundary; preserve content/release version semantics; define no concrete Park revision; keep the approved behavior board package unchanged.",
    "The user subsequently confirmed the explicit app-managed bundled-free metadata input resolution; no change to the approved behavior-board package was requested.",
    "Owner directed cycle identity 165r for OX-31 after cycle 165 closed with OX-56 BLOCKED; 166 remains the next standard numeric cycle.",
    "Owner decision (2026-11-20): catalog entry version identifies installed theme content/release revision from app-managed metadata, as an opaque non-orderable value separate from schema, compatibility, policy, and byte identity.",
    "Owner confirmed the existing four-file board package is approved as fingerprinted; its companion Markdown pending-status line is stale, and approved files remain unchanged.",
    "OX-31 has no UI artwork gate under its approved non-visible scope.",
    "OX-41 already records the Living Urban Park as bundled free default; OX-31 represents that role without changing current scene selection.",
    "Test authorization remains absent; the plan excludes all test additions and test execution and specifies compile-only build plus manual contract review."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Recorded OX-31 SLICE intake, sole NEXT status, DONE dependencies, exit, evidence targets, exclusions, and owner-directed cycle identity.",
    "Recorded exact board approval, package fingerprints, owner version decision, board-status clarification, and OX-41 role boundary.",
    "Drafted OX-31 catalog types, query semantics, compatibility and metadata ownership, scope exclusions, implementation steps, verification authorization, acceptance, and evidence targets.",
    "Completed the full OX-31 plan review, finalized the cycle manifest, and saved the handoff at REVIEW.",
    "Resolved the bundled-free version-source gap by documenting the required app-managed metadata input, then reviewed that contract against the unchanged approved behavior-board package and slice exit.",
    "Implemented InstalledThemeCatalog.kt and docs/INSTALLED_THEME_CATALOG.md within the approved non-visible scope.",
    "Recorded manual contract review, approved board fingerprints, workflow validation, debug build result, and verification limits in cycle evidence.",
    "Closed slice OX-31 as DONE.",
    "Recorded OX-31 as DONE after reviewing its exit against the approved behavior board and saving implementation/build/manual-review evidence.",
    "Updated roadmap evidence and the bundled-free metadata resolution; selected OX-57 as the sole NEXT slice for board and plan preparation under the existing OX-31 → OX-57 → OX-32 sequence, without activation.",
    "Inspected the final tracked diff and OX-31 artifacts; final workflow, tracked diff check, and new-file whitespace scan passed.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/165r-ox31-installed-pack-catalog/README.md",
    ".codex/history/165r-ox31-installed-pack-catalog.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/165r-ox31-installed-pack-catalog.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/InstalledThemeCatalog.kt",
    "docs/INSTALLED_THEME_CATALOG.md",
    ".codex/test-artifacts/165r-ox31-installed-pack-catalog/README.md",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.dot",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.svg",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.png",
    ".codex/concepts/OX-31-installed-pack-catalog-behavior-board.md"
  ],
  "next_action": "Close cycle 165r with the exact recorded verification and limits; archive the plan and history and reset the current pointer."
}
```
<!-- cycle-checkpoint:end -->

## SLICE intake — OX-31

OX-31 remains the sole eligible `NEXT` slice. Its direct dependencies OX-55 and OX-20 are DONE. The owner directed cycle identifier `165r` after cycle 165 closed with OX-56 BLOCKED; the next standard numeric cycle remains 166.

The objective-specific behavior board package governs a non-visible, app-managed catalog query. Product-owner approval applies to the exact four-file package recorded in the manifest. The package fingerprints were checked and match. Its companion Markdown still contains a pending-review status line; the owner confirmed that line is stale. The companion also calls the OX-41 role decision future, although the current roadmap records OX-41 as DONE with that decision made. This plan follows the current OX-41 record and makes no new default choice. Preserve the approved files and fingerprints unchanged. UI artwork is not applicable because this plan changes no visible output.

The owner resolved the board's version question on 2026-11-20: a catalog entry version identifies the installed theme content/release revision stored in app-managed metadata. It is distinct from manifest schema, engine compatibility, renderer policy, and exact-byte identity. The existing OX-41 owner decision records the Living Urban Park as the bundled free-default role. OX-31 represents that existing role; it makes no new default choice and does not change the current `CatalogWorld` selection or implement Park rendering.

**Bundled-free metadata resolution (2026-11-20):** The user instructed execution to continue with the recommended option if no answer arrived within 60 seconds; the fallback was applied after that window, and the user subsequently confirmed the same option. The catalog composer will require an explicit `BundledFreeThemeMetadata` input containing the catalog-owned display name and non-empty app-managed content/release revision. It always emits this metadata as the OX-41 bundled-free role under its fixed app-defined identity and `ALWAYS_AVAILABLE` disposition. OX-31 defines no concrete revision value for the not-yet-implemented Park content; the app integration supplies the real value when that bundled content and its metadata exist. This preserves the approved content/release meaning without inventing a Park revision or changing the approved board package.

**Evidence targets:** `.codex/test-artifacts/165r-ox31-installed-pack-catalog/` and `.codex/history/165r-ox31-installed-pack-catalog.md`. These are targets, not completed evidence.

## Approved board and dependencies

The behavior board governs the following contract: a read-only catalog query always includes the app-defined bundled-free entry, then represents only explicitly registered app-owned entries. App-owned records remain distinct when their known compatibility disposition is compatible or incompatible. The query does not enumerate unregistered files, validate or activate packs, select a scene, or interpret weather. The board's companion contract supplies the catalog-owned display-name rule and separates `packId` identity from the still-open-at-board-authoring version question, which the owner has resolved as recorded above.

OX-20 supplies `packId` as stable logical identity across pack revisions. Its `manifestVersion` is only the manifest envelope version. OX-53's `rendererPolicyVersion` is a separate capability-registry version. OX-54's `contentIdentity` and manifest asset hashes identify bytes; they are not release labels. OX-55 supplies the current validated-pack renderer boundary. OX-31 consumes known app-managed metadata and compatibility disposition without adding another validator or renderer.

OX-41 records the existing product decision that the Living Urban Park is the bundled free default and First City and Open Landscape remain other bundled worlds. The current app still selects First City, as OX-41 explicitly did not implement a default change. OX-31 keeps that runtime behavior unchanged and represents the free role in catalog metadata only. OX-57 owns installation and durable population of app-owned records; OX-33 owns Settings presentation; OX-32 owns in-session theme switching; OX-56 owns later resolution recovery.

## Bounded result

Add a typed, read-only app-managed installed-theme catalog contract in `app/src/main/java/com/oxygen/weather/timeline/data/scenepack/InstalledThemeCatalog.kt` and its integration description in `docs/INSTALLED_THEME_CATALOG.md`.

The catalog entry contract contains:

- A stable catalog ID. The bundled-free entry uses an app-defined identity for the OX-41 free role. An app-owned pack uses its OX-20 `packId`; content revisions keep the same ID.
- A version value sourced from app-managed metadata. For an app-owned pack, it identifies the installed content/release revision. It is an opaque, non-empty string: equality identifies the same declared revision, but OX-31 defines no lexical, numeric, or semantic-version ordering. Change it when the installed content/release revision changes, not when only display-name or compatibility metadata changes. The bundled-free entry uses the app-managed revision of its bundled theme content, supplied explicitly to the catalog composer; OX-31 defines no concrete Park revision while that content is not implemented.
- A display name supplied by catalog metadata, never inferred from `packId`.
- An origin distinguishing the bundled-free role from app-owned packs.
- A validity disposition: the bundled entry is always available and non-removable; app-owned entries carry a known compatible or incompatible disposition supplied by upstream app-managed pack evaluation. OX-91 resource validation and OX-55 renderer compatibility remain upstream; OX-31 carries their known disposition without repeating those checks.

The query requires bundled-free metadata and includes that entry even when no app-owned records are registered. It returns only app-owned records explicitly passed by the app-managed registry. The app-owned input is a data contract for OX-57 to populate when installation exists; OX-31 does not seed the OX-55 debug Olive candidate as an installed pack. The query has no filesystem, persistence, renderer, or forecast dependency and causes no state mutation.

## Invariants and compatibility

- Keep stable pack identity (`packId`), installed content/release version, manifest schema version, engine-contract compatibility, renderer-policy version, and exact-byte identity separate.
- Preserve the bundled-free entry independently of app-owned records. Incompatible known records remain visible as incompatible catalog data; the catalog does not activate or silently drop them.
- The catalog does not change place, weather catalog revision, exact selected instant, forecast facts, scene-weather decisions, timeline gesture ownership, or current scene choice.
- Keep the current `CatalogWorld` chooser and the debug-only `OlivePackCandidate` renderer path outside the new catalog model. Do not make either UI type or the manifest decoder's DTO the public catalog contract.
- Keep app-owned registration explicit and app-managed. Installation, persistence, replacement, and package-root handling remain OX-57 responsibilities.
- A missing app-owned registration yields the bundled-free entry only. OX-31 has no file-read or import failure path because it reads no files and owns no installation.
- No current persistent installed-pack catalog or migration source exists; OX-55's Olive candidate remains validation-build-only and is not a catalog registration.

## Exclusions

No global filesystem scan; package import or installation; durable storage; download or purchase provider; Settings or other UI; theme selection, hot swap, renderer activation, or OX-56 recovery; new manifest fields or manifest-version changes; new validation or renderer capability policy; Park composition/rendering; weather interpretation; or app-wide accessibility, RTL, TalkBack, device, or environment matrices.

## Ordered implementation plan

1. Add data-only catalog ID, non-empty opaque version, entry, origin, validity, bundled-free metadata, and registered app-owned metadata types. Use OX-20 `packId` for app-owned stable identity and keep catalog version metadata separate from manifest fields and hashes.
2. Add the read-only catalog query/composer. It requires explicit `BundledFreeThemeMetadata`, always maps it to the OX-41 bundled-free role, and returns only explicit app-managed registrations with their supplied display name and compatibility disposition. Keep it independent of filesystem and UI APIs.
3. Add `docs/INSTALLED_THEME_CATALOG.md` describing field ownership, version meaning, validity, query input, no-scan/no-mutation limits, and the OX-57/OX-33 handoffs. Record OX-41's already-decided free role without changing current selection.
4. Inspect the final implementation and documentation against the approved board and acceptance list. Build the app and record the exact result; no UI/device acceptance is applicable.

## Acceptance and authorized verification

The exit is met when the typed query contract can represent the never-removable bundled-free entry from explicit bundled metadata and explicitly registered app-owned compatible and incompatible entries distinctly, with stable ID, content/release version, catalog-owned display name, origin, and validity. The same pack keeps its ID across content revisions while its app-managed version identifies the installed revision. The query reads only the required bundled metadata and explicit app-owned registrations and has no weather, scene-selection, filesystem-scan, validation, or activation behavior. The bundled entry's `ALWAYS_AVAILABLE` disposition means the catalog always includes the role and it cannot be removed; it does not claim that OX-31 implements the Park renderer.

This plan does not authorize adding or running tests. Do not run `python scripts/dev.py test`, `check`, or any Gradle test task. Verification for a later execution is limited to `python scripts/dev.py workflow`, `python scripts/dev.py build` (compile only), `git diff --check`, and a recorded manual review of the catalog contract against the accepted board and the cases above. A build alone does not establish the behavior; the review record must state the inspected code paths and limitations. `python scripts/dev.py contract` is not applicable because it does not validate the catalog paths. No installed screenshot or visual acceptance is claimed.

## Evidence, risks, and limits

Record the implementation review, exact commands and results, final diff inspection, and limitations under `.codex/test-artifacts/165r-ox31-installed-pack-catalog/`; closeout belongs at `.codex/history/165r-ox31-installed-pack-catalog.md`.

OX-20 and OX-53 define no content/release field, so OX-57 must populate the catalog version through app-managed metadata when installation is implemented. OX-54's content identity must not be promoted to a version. OX-31 defines the input contract but does not add persistent storage or a live installed-pack source. No existing installed-theme catalog tests were found, and this plan carries no test authorization. The catalog represents the OX-41 free role without claiming that OX-31 implements its scene renderer or changes the current First City selection.
