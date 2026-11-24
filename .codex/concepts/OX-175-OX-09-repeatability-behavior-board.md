# OX-09 repeatability across scene/world changes — behavior supplement

**Status:** OX-09 supplement; product owner pre-approved this bounded board
scope on 2026-11-23. This is a planning artifact, not implementation or
installed acceptance.

This supplement extends the shared Cycle 175 behavior package for OX-09 only.
The existing shared package continues to govern the other roster slices.
The OX-09 roadmap objective and exit remain controlling.

## Controlled comparison

Hold place, active catalog revision, exact selected instant, selected-time
weather decision, build, device, and viewport constant. Sample the current
First City renderer output, repeat the same selection in First City, switch to
Open Landscape at the same instant and repeat the selection there, then return
to First City and repeat once more. Compare A/A′/A″ within First City and B/B′
within Open Landscape. The different worlds are expected to have different
compositions; cross-world pixel parity is not required.

Use the existing world-selection and rendering paths from OX-38 and OX-92.
The inspected renderer source contains authored geometry and index-derived
rain/snow placement; that source observation does not establish an observed
selection defect or require a correction. Record the actual affected elements
and distinguish a changed animated frame from a re-randomized layout.

## Disposition

- If repeated output is stable within each world, record the sampled result
  and make no code change.
- If the same-world samples show actual re-randomization, a correction may
  apply a stable seed only to the affected element. Compare before and after,
  preserving the element's motion and the meaning of selection.
- If the cause cannot be established, record the limitation and make no
  speculative correction.

Do not add cache machinery, new scene art, or a cross-world visual-parity
requirement. Preserve the shared switch invariants: exact selected instant,
weather facts, timeline state, and gesture ownership remain coherent.

## Planned evidence

The slice evidence record remains
`.codex/test-artifacts/175-pack-platform-readiness/OX-09-repeatability.md`.
Capture First City initial/repeat/return and Open Landscape initial/repeat
states. Use a sequence recording only when still captures cannot establish
motion or switch ordering. These are future targets; no acceptance evidence
has been collected.
