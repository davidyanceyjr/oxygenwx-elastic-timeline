# SE.0 — Scene Engine specification reconciliation board

**Status:** Draft for product-owner review. Documentation-only behavior/content map; not an approved implementation plan or UI artwork.

## Owner decisions already given

1. The current default scene's mechanics are an acceptable behavior baseline for the refactor. Its architecture may be reorganized. The scene has no product name yet; `FirstCityTheme` is an internal implementation identifier.
2. The MR.2 readout layout need not be preserved permanently. Changing it is unnecessary in the first engine slice.

## Wording map for `docs/Oxygen_Scene_Engine_Refactor_Spec.md`

| Passage | Required reconciliation | Acceptance reading |
| --- | --- | --- |
| Status, §§1–3, 20–21, 38 | Mark this document as a proposal subordinate to `docs/SPECIFICATION.md` and the adopted experience contract. The scene is the primary visual field, while critical selected-time forecast facts remain immediately readable as text with accessible meaning. Scene-embedded text can serve that role only when legible and accessible. Inspect flows can hold additional precision, explanations, and advanced facts. The current MR.2 arrangement is the current implementation, not a permanent engine requirement; its redesign is outside the first engine slice. | No sentence implies artwork or inspect-only disclosure can replace critical text, or that the first engine slice changes the readout. |
| §§2, 12–16 | Supported supplied categories and independently supported values drive meaning. Aesthetic palette, light, cloud pose, and other visual parameters may transition. Categorical changes, point/interval support, source times, and selected time remain discrete. A scene transition does not establish an exact event minute or a measured minute-by-minute forecast. Probability is not rate; amounts do not silently become intensity. | Scrubbing and scene preparation remain functions of the authoritative selected instant and typed source facts. |
| §§14–16 | The 44-local-date catalog is not a promise of 1,056 hourly records: DST, partial coverage, and provider gaps remain explicit. A scene-ready representation may be unavailable for an hourly weather claim while preserving independently supported facts. | No 24-hours-per-day coverage guarantee or invented weather interval. |
| §§12–16, 20–22 | Missing, empty, unsupported, and leading/trailing-gap conditions make no selected-hour condition claim. Only an eligible interior absent expected hour bracketed by supplied same-date conditions may show the preceding scene, visibly disclosing original source time and selected-hour unavailability. A supplied last expected hourly point may occupy its eligible same-date cell through exclusive midnight with source time disclosed, without crossing into the next date or carrying an absent final field. | No numeric zero substitution, hidden carry, or misleading destination scene. |
| §§2, 18, 22 | Current solar inputs support supplied local-date sunrise/sunset event crossings; current lunar inputs support moonrise/moonset instants and daily phase fraction/stage. Between-event paths and cue scaling are illustrative. Exact solar/lunar azimuth or elevation, lunar orientation, angular size, measured illumination, exact phase transition time, and twilight measurements need new supported data and bounded work. | No claim that the current cue follows an actual measured path or offers unsupported Moon inspect values. |
| §§10–11, 36 | Call the current product scene the unnamed default scene. Preserve its approved selected-time, source-support, weather-meaning, layering, cloud-region, gesture, and readability behavior as the first refactor baseline. The `FirstCityTheme` symbol need not be renamed in this slice. Observatory, packs, and a different default remain proposals requiring their own decisions and boards. | Architecture can change; no silent default-scene replacement or loss of established behavior. |

## Scope and verification boundary

- Edit only the scene draft and the necessary roadmap/cycle records for this documentation slice.
- Cite the controlling passages and completed SG/ATM/MR decisions near affected draft rules.
- Review the wording diff against this map; run `git diff --check` and workflow validation. Do not run tests or builds.
- This board does not approve a first Scene Engine implementation slice, readout redesign, new astronomy data, scene packs, or a default-scene replacement.
