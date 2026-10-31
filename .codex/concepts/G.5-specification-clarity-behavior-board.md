# G.5 specification clarity — approved behavior board

**Status:** Product-owner approved on 2026-10-30 in conversation after reviewing this board in `feh` on display `:0`. **Scope:** Documentation contract only. This board proposes no application or visual implementation.

## Selected-time contract

```text
Daily tap or daily drag release ──> target date at 12:00 local ──> select target instant
Hourly hold/drag ────────────────> touched/hourly target instant ──> select target instant
Hourly tape tap ────────────────> resolve under finger on hourly axis ──> select target instant ──> collapse
Main-area tap in hourly mode ────────────────────────────────────────────────> collapse, retain instant
Scale animation alone ───────────────────────────────────────────────────────> retain instant
Main-area day swipe ────────────> adjacent local date, same wall time, existing DST rule
Double-tap reset ───────────────> product reference instant ──> catalog-window clamp if needed
```

The selected instant remains the sole time authority. Daily presentation may use a day summary while showing the selected timestamp truthfully. A scale transition never selects a new instant by itself. Daily tap or daily drag release selects exactly 12:00 local on the target date. If that target is outside a partial first/last date's catalog window, clamp to the nearest window boundary and disclose the adjustment. An in-window missing fact does not move selection.

## Availability states and navigation

```text
Target inside catalog window + fact present  -> selected instant; show supported facts
Target inside catalog window + fact missing  -> selected instant; explicit unavailable facts;
                                                no weather imagery claiming a condition
Target outside catalog window                -> nearest window boundary; disclose clamp
```

Navigation across a missing hour or day remains possible. A calendar-day swipe targets one adjacent local date at the same wall time; the existing repeated/nonexistent local-time rules apply before availability is evaluated. The selected time and display label remain coherent even when facts are unavailable. A data refresh cannot silently retarget the selected instant.

## Reset references

| Product | Double-tap target |
|---|---|
| Live, including stale live catalog | Current instant at the time of activation |
| Simulated | Catalog `asOf` instant |
| Recorded replay | Catalog `asOf` instant |

An out-of-window target clamps to the nearest window boundary with a clear adjustment notice. Stale and unavailable provenance remain visible; reset never turns an old or missing fact into a current observation.

## Release and authority boundaries

- Real-data integration does not imply official-alert integration. Before an alert subsystem exists, avoid suggesting that an empty alert display means there are no alerts. If offered later, alerts require an authoritative source and remain separate from catalog weather conditions.
- The successor repository boundary is settled. The deprecated application is outside this slice.
- This board governs wording in `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`. It does not approve UI artwork, application behavior changes, or installed acceptance.

## Approval and constraints

The owner approved the board without additional constraints. The selection, availability, reset, and alert rules above govern the G.5 documentation scope and acceptance. A material change to these rules requires a new owner decision before the corresponding plan or specification wording is changed.
