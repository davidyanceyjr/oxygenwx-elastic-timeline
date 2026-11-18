---
name: oxygen-build-slice-boards
description: Build reviewable behavior and, when visible output changes, UI artwork boards for the Oxygen Time Lab NEXT roadmap slice using its scope and exact dependency records. Use during the BOARDS stage; do not plan or implement the slice.
---

# Build boards for the NEXT slice

Use this skill when the owner asks to create or revise boards for the roadmap's
NEXT slice. Board creation is a proposal for owner review, not approval or
implementation.

1. Read `.codex/current.md`, `.codex/README.md`, and run
   `python scripts/dev.py workflow`. Follow the open cycle's checkpoint if one
   exists. Read the NEXT slice's full roadmap entry, its exact `Depends on`
   records, and the relevant passages of `docs/ROADMAP_SPECIFICATION.md`.
   Read product or UI authority and affected files only where the slice's
   objective, dependencies, or a concrete conflict calls for them.
2. Confirm that there is exactly one NEXT slice and that its dependencies are
   resolved. If there is no NEXT slice, or an open cycle points elsewhere,
   report that state and leave selection or cycle changes to
   `oxygen-plan-next-slice`. Do not promote a PLANNED slice implicitly.
3. Use the NEXT slice's objective, exit, evidence target, and board field as
   the scope boundary. Treat dependency outcomes and approved boards as
   constraints and references. Trace each inherited rule to its source;
   distinguish established behavior from a proposed decision. Do not copy a
   dependency's whole design into the new board or assume its approval covers
   this slice.
4. Create an objective-specific behavior board that makes the proposed flow,
   rules, boundaries, and relevant states reviewable against the slice exit.
   Use Graphviz DOT as editable source and retain SVG and PNG renders. Add a
   concise companion contract when the diagram alone cannot express a rule or
   open decision. Mark unresolved choices visibly instead of inventing them.
5. If the slice changes UI or visual output, also create UI artwork in SVG or
   PNG showing the intended appearance and affected states. Keep both formats
   when they have distinct source and review roles. A combined board is fine
   if the behavior and artwork are each reviewable. If no visual change is in
   scope, record why UI artwork does not apply.
6. Check the proposed package against the slice exit and dependency
   constraints. Show each non-terminal visual artifact with
   `DISPLAY=:0 feh --auto-zoom <path>` before requesting owner approval.
   Present the board paths, source-to-proposal decisions, open questions, and
   any UI-artwork disposition. Revise from owner feedback.
7. Record approval and package fingerprints only after the owner approves the
   actual files, using the cycle workflow and `approve-board` procedure in
   `oxygen-plan-next-slice`. Keep pending boards pending; do not finalize or
   activate a plan, change app behavior, or claim installed acceptance here.

Save board artifacts under `.codex/concepts/` unless the active checkpoint or
owner specifies another path. Preserve the cycle's stage checkpoint at every
boundary; resume a paused cycle without resetting accepted gates.
