# SG.9 cloud-height behavior board — draft for owner review

Status: **DRAFT — not approved**

## Owner-requested rule

Clouds are restricted to the top 33% of the atmospheric scene viewport. Treat the lower edge of that region as `y = 0.33 × viewport height`, measured from the top of the scene canvas. Every visible cloud pixel must remain at or above that boundary in every supported cloud state, including day/night, rain, snow, storm, and transitions. The rule applies regardless of cloud cover or theme composition.

## Behavior contract

```text
selected-time cloud support
          │
          ├─ absent/zero ───────────────► no cloud artwork
          │
          └─ positive ─► compose cloud shape within y ∈ [0, 0.33H]
                                   │
                                   └─ clip/check visible output at 0.33H
```

- `H` is the atmospheric scene canvas height, excluding overlaid forecast text and timeline controls.
- The cloud shape, blur, shadow, and antialiased edge are all part of the visible cloud output and must not bleed below `0.33H`.
- Cloud movement and scene transitions preserve the bound at every intermediate frame; reduced motion uses a still pose within the same bound.
- Existing cloud support and opacity semantics remain unchanged. The cap changes placement only; it does not reinterpret cloud-cover values.
- Precipitation may continue below the cloud region under the existing SG.4 composition rule. This board does not change precipitation coverage or forecast facts.
- Forecast text, timeline labels, city layers, solar cues, and selected-time behavior retain their existing owners and acceptance requirements.

## Review questions for approval

1. Is the atmospheric scene viewport the intended reference for “screen”?
2. Does the 33% bound apply to the entire cloud silhouette, including blur and shadow, as stated?
3. Does the same bound apply in every supported scene state and during transitions?

Approval is pending. Do not finalize an SG.9 plan or implement the rule until the owner approves the behavior and artwork boards.
