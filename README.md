# OxygenWX Park three-plane — complete adoption kit

**Status:** proposed roadmap replacement, not adopted and not executable application code. This kit includes the three uploaded transparent PNG images, illustrative composites, superseded OX-99 draft boards, roadmap proposals, and the patch generator. The owner-designated Park placement mockup is available at `design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png`; its sample values are illustrative under the OX-41 concept board.

## Contents

- `references/original-uploads/`: the **original three user-provided PNGs, byte-for-byte unchanged**, preserving the upload names.
- `references/background.png`, `middleground.png`, `foreground.png`: checksum-identical copies with short names used by the proposal.
- `references/reference-composite.png`, `theme-source-preview.png`: illustrative composites of supplied source art, not substitutes for the owner-designated full-scene mockup or a layer-separated source package.
- `design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png`: owner-designated full-scene Park placement mockup with sample visual data; the OX-41 concept board says those values are not literal forecast/UI data. The working tree keeps the reference at this path; the OX-99 UI board embeds it for archive review.
- `design_draft/OxygenWX Streetcar Stop at Golden Hour.png`: a separate mockup variant; it shows hourly NEXT HOURS values on the shelter and a daily date, high/low, and precipitation summary on the separate board. The owner selected this allocation; the visible three hourly rows and values remain mock content unless separately approved. The working-tree file is not copied into the ZIP as a standalone source; the UI board embeds it.
- `references/asset-ledger.json`: metadata and source checksums.
- `docs/PARK_THREE_PLANE_ROADMAP_INSERTION.md`: nine new roadmap leaves including revised OX-99.
- `docs/ID_RECONCILIATION.md`: existing Park slice dispositions.
- `docs/PARK_LAYER_CONTRACT_DRAFT.md`: painter-order rules.
- `docs/CODEX_ADOPTION_PROMPT.md`: Codex review/adoption request.
- `scripts/apply_park_roadmap.py`: deterministic fail-closed transformation of repository `docs/ROADMAP.md`.
- `scripts/build_ox99_owner_review_boards.py`: reproducible renderer for the new draft OX-99 behavior and UI boards.
- `prior-drafts/OX-99-boards/`: superseded, unapproved proposal boards retained for audit.
- `.codex/concepts/ox-99-park-behavior-board.*` and `ox-99-park-ui-board.*`: new draft owner-review boards; neither is approved. Their mapping and state choices remain open.
- `SHA256SUMS.txt`: hashes of all payload files apart from this list itself.

## Run (review first, no changes to canonical roadmap)

Extract this archive into a *staging directory*, not blindly over your working tree. From your OxygenWX repository root, copy these two files from the extracted folder into matching paths:

```bash
cp /path/to/kit/scripts/apply_park_roadmap.py scripts/apply_park_roadmap.py
cp /path/to/kit/docs/PARK_THREE_PLANE_ROADMAP_INSERTION.md docs/PARK_THREE_PLANE_ROADMAP_INSERTION.md
python3 scripts/apply_park_roadmap.py --repo .
diff -u docs/ROADMAP.md build/park-roadmap-review/ROADMAP.md || true
```

Codex should also read `docs/CODEX_ADOPTION_PROMPT.md`, `docs/ID_RECONCILIATION.md`, `docs/PARK_LAYER_CONTRACT_DRAFT.md`, the PNGs, and the repo's actual workflow authorities. Either point Codex to those files in the staging directory or copy those docs into your repo as unadopted review inputs.

**Only after an explicit owner approval and workflow eligibility check:**

```bash
python3 scripts/apply_park_roadmap.py --repo . --in-place
python3 scripts/dev.py workflow
python3 scripts/dev.py contract
git diff --check
```

`--in-place` is a one-time migration from the original roadmap: the generator refuses a second application and rejects ID collisions or unexpected slice statuses. Commit/reconcile the generated roadmap before any later transformations.

## Important limitations

The owner clarified that Concept 01 is the intended full-scene placement mockup: location on the tower, current temperature and condition on the mural (with wind chill added by owner direction), wind on the banner, daily date/HI/LO/precipitation on the freestanding board, and daily cards in the bottom strip. Its example values remain mock data under OX-41. The mockup is available as a composition reference; exact registration of the supplied transparent source layers against it has not been measured, and no layer-separated source package or exact source lineage is identified. The owner directed preserving the supplied middleground alpha unchanged and confirmed ownership of all three transparent sources, authorizing review-board reproduction, asset derivation, in-app display, and distribution. Creator/source and separate terms or attribution for those three sources remain unspecified. On 2026-11-24, the owner selected the recommended path of keeping OX-99/OX-102 gated until the owner mapping and state matrix are completed and approved on the draft boards. The owner selected the Streetcar Stop allocation: the shelter shows hourly NEXT HOURS values and the separate board shows the daily date, HI/LO, and precipitation summary. This supersedes the earlier three-day HI/LO assignment to the shelter and the earlier undecided/no-duplicate board-content direction. The Streetcar variant depicts three hourly rows, but the exact hourly range/count has not been separately confirmed; sample times and values remain illustrative. The three supplied PNGs contain no shelter; the existing Park renderer draws a procedural shelter but has no forecast sign. Its source/disposition and the sign treatment remain open. The nine elements' plane/disposition assignments, live-reading order, and fixed-art state behavior remain open. The existing v2 scene-pack contract cannot install this three-plane scene as a normal pack without further work. The boards are drafts, no board approval is recorded, no app implementation was performed, and no device acceptance is claimed.
