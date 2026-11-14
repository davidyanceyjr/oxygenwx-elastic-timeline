# OX-01 behavior board — baseline the current world

**Visual board:** `OX-01-baseline-the-current-world-behavior-board.svg` (editable `.dot`, PNG review image alongside).

**Status:** Product-owner approved on 2026-11-14 as shown, with no additional constraints stated. This board covers documentation and current-state evidence only; a UI artwork board is required if the approved scope changes visible output.

## Purpose and observable path

The selected catalog instant and supported source facts lead to typed scene state/composition and the current First City frame. OX-01 records that path as it actually exists. A representative set captures still states and selected-time transitions so later Scene Engine slices have a trustworthy preservation reference. The inventory distinguishes observed behavior to keep, proposed later changes, and facts that remain unknown.

## Baseline questions

| Area | Record for this slice | Preservation question |
|---|---|---|
| Scene state and frame | Representative supported weather, light, cloud, precipitation, and unavailable states; current layer composition and visible facts. | Which observed states and facts must later renderers preserve? |
| Scrubbing | Day/hour selection and transitions across meaningful scene changes; exact selected timestamp during zoom/animation. | Does scene and readout follow the same selected instant without a time jump? |
| Astronomy | Sun/Moon cues, phase and rise/set states where current records support them. | Which cues are supported facts and which are illustrative? |
| Accessibility | Current text/semantics for important forecast facts and selected condition; scoped evidence and gaps. | Can the user obtain the same important weather meaning without relying on decoration? |
| Evidence | Existing WX/ATM/SG/DFM baseline records, with exact paths and applicability; only explicitly authorized new checks. | Which claims are verified, unverified, or contradicted? |

## Exit and boundary

Produce a representative snapshot/transition reference set and a written keep/change/unknown inventory. Link each material claim to existing scoped evidence or an explicitly authorized check; record gaps and their disposition. Installed captures are required for any visual acceptance claim. Do not refactor the renderer, add new artwork, change app behavior, or imply app-wide accessibility acceptance in OX-01.

**Authority:** `docs/ROADMAP.md` OX-01 and `docs/ROADMAP_SPECIFICATION.md` board/evidence rules. The Scene Engine proposal is supporting rationale; existing product authority and completed slice decisions control the baseline.
