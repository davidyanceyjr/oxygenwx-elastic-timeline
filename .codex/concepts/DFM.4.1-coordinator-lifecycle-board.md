# DFM.4.1 — Coordinator lifecycle behavior board

**Status:** Owner-approved 2026-11-01. Approval covers this behavior board as written and its stated constraints; it is not implementation or test evidence.
**Scope:** The nonvisual coordinator foundation for the debug session. The approved DFM.4 session board remains the governing product-level outcome. This board makes its startup, activation, checkpoint, and restoration boundary independently reviewable; it adds no new product behavior.

## Entry and settled capture

```text
Process starts
  ├─ saved-location restoration → optional initial activation ─┐
  └─ alternate debug cold launch → validation → activation    │
       ├─ failure → existing startup restoration fallback ───┤
       └─ success → required fixture time selection ───────────┤
                                                              ▼
#DEBUG received → consumed pending entry → startup settled AND current activation terminal
                                                  │
                           recheck both under coordinator lock
                              ├─ changed / busy → await and retry
                              └─ settled → capture opaque checkpoint → session may open
```

- An outstanding saved-location read is pending even while UI `loading` is false. Empty, invalid and failed restoration are settled outcomes, not invented catalogs.
- Alternate startup is registered before asynchronous work. A failed cold launch transfers readiness to its existing fallback without a ready gap. A superseding activation is allowed to settle; it is not canceled by debug entry. Host cancellation does not signal false readiness, open the picker or initiate fallback.
- Capture includes the coordinator-owned selected location/name, last request, active selection and interval, pending anchor, and full published state. It preserves the exact selected instant, including empty/error state; the delegate cannot construct or edit the checkpoint. On restoration, an active selection receives a fresh view generation as the sole exception to restoring the captured published state exactly. The owner approved this exception in the current cycle discussion.

## Activation completion and fixture selection

```text
Issued request → SUCCESS(selection + generation) → fixture may select using that generation
               → FAILURE(error)                 → existing failure / fallback path
               → SUPERSEDED                    → no fixture selection, no fallback
```

Every issued handle terminates when its request succeeds, fails, or is replaced, including replacement by checkpoint restoration. The coordinator alone owns sequencing and publication. A stale generation cannot select time in a replacement catalog. Activation completion does not itself mean alternate startup is ready: handler work and any fixture selection must finish first.

## Exit and stale work

```text
Exit session / before ordinary action
   → under coordinator lock: invalidate request sequence and cancel preview
   → restore checkpoint and publish saved state directly
   → discard checkpoint; ordinary action may then proceed
   → late preview / fixture result cannot overwrite restored state
```

Restore performs no provider request, catalog load, location persistence or storage write. It does not recompute the selected instant, rewind request/view-generation counters, or create a second activation owner. When restoring an active selection, allocate a fresh monotonic view generation so callbacks holding the captured generation cannot change the restored selection; callbacks using the new generation remain valid. Settled empty/error states with no active selection need no new generation. Session/ordinary-action UI wiring is DFM.4.2; DFM.4.1 supplies and verifies the coordinator boundary.

## Verification relationship and exclusions

Focused tests cover pending startup read, initial success/failure and empty/error outcomes, alternate launch and fallback, success/failure/supersession handles, cancellation, atomic capture races, exact checkpoint round trips, no-I/O restore and stale completion rejection. Preserve normal callers and SG.4 fixture behavior. No interface appearance changes are in this slice, so no UI artwork board is required. Device-wide accessibility, RTL, performance, provider behavior, storage schema and catalog editor are outside this slice. Board approval guides the plan; it is not implementation or test evidence.
