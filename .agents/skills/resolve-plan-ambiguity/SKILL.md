---
name: resolve-plan-ambiguity
description: Reduce execution-time reasoning by resolving material ambiguity and avoidable unknowns in the current Oxygen Time Lab PLANNED cycle. Preserves the plan's full obligations and leaves it PLANNED.
---

# Resolve ambiguity in the current Oxygen Time Lab plan

Follow `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, and `.codex/README.md`. Use `docs/SPECIFICATION.md` and `docs/ROADMAP.md` as product and scope authorities, plus applicable detailed authority documents. Do not implement app code.

1. Read `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/current.md`, its referenced plan, `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, `.codex/README.md`, and this repository's plan review, rating, and execution skill guidance as relevant. Run `python scripts/dev.py workflow` before modifying the plan.
2. Confirm `.codex/current.md` identifies a cycle in `PLANNED` state and points to the plan being edited. If it is `IDLE`, `ACTIVE`, malformed, or the referenced plan is missing, do not modify another plan; report the mismatch. Do not activate or close the cycle.
3. Inspect relevant implementation, tests, documentation, roadmap dependencies, and recent `.codex/history/` records when needed to resolve uncertainty. Distinguish facts established by repository evidence from assumptions.
4. Identify reasoning hotspots where execution would otherwise require substantial architectural, semantic, state-management, failure-handling, verification, or integration decisions. Classify each meaningful hotspot as one of:
   - **Resolvable ambiguity** — the plan or authorities admit multiple interpretations, and repository authority selects one.
   - **Resolvable unknown** — repository evidence can establish the relevant behavior or constraint through inspection.
   - **Intrinsic implementation complexity** — required work remains difficult after its contracts are clear.
   - **Owner decision required** — repository authority does not support choosing among materially different outcomes.
5. Resolve ambiguity and avoidable unknowns from repository authority. Encode the result in the current plan with explicit contracts where useful: ownership and layer boundaries; state transitions or tables; input/output semantics; missing-data behavior; provenance, freshness, and validity; fallback and failure behavior; allowed and forbidden dependencies; deterministic test cases; authoritative verification mechanisms; and escalation conditions if assumptions prove false. Reference concrete files, symbols, tests, or history where that makes the contract verifiable.
6. Preserve intrinsic complexity as required work. Leave unsupported owner decisions unresolved, clearly mark the decision and its impact, and do not imply owner approval. Do not prescribe incidental implementation details when multiple implementations satisfy the resolved contract.
7. Rewrite only the portions needed to remove material executor ambiguity. Keep the objective, production boundary, required behavior, invariants, acceptance criteria, verification and evidence rigor, risks, assumptions, and exclusions intact. Never move difficult required work to out-of-scope or otherwise lower expectations to make execution easier.
8. Before finishing, compare the edited plan with its prior version and perform the expectation-preservation self-check below. Run `python scripts/dev.py workflow` after plan modification. Confirm `.codex/current.md` still reports `PLANNED` and references the same cycle and plan. Do not activate or close the cycle.

## Expectation-preservation self-check

Explicitly verify and report that:

- production scope remains intact;
- the intended observable outcome remains intact;
- product, architecture, accessibility, provenance, meteorological, and other applicable invariants remain intact;
- acceptance criteria remain intact;
- verification and evidence requirements remain intact and no required verification became optional;
- exclusions have not been used to remove difficult required work;
- resolved decisions are supported by repository authority;
- unresolved owner decisions remain visible;
- the cycle remains `PLANNED` and was not activated or closed.

If the comparison reveals any accidental expectation reduction, restore the original obligation before finishing. If an issue cannot be resolved without changing an expectation or making an unsupported owner decision, preserve the obligation and report the blocker or decision needed.

## Response

Report four sections, keeping each concise and concrete:

1. **Resolved ambiguity** — meaningful executor decisions converted into explicit planning contracts.
2. **Remaining uncertainty** — material unresolved ambiguity, unavoidable implementation discovery, intrinsic complexity, and owner decisions.
3. **Preserved expectations** — confirm objective, production boundary, invariants, acceptance criteria, verification rigor, and exclusions remain intact; include the self-check result and PLANNED state.
4. **Execution-readiness assessment** — say whether execution is now more deterministic and why. Do not select an implementation model. Recommend running `rate-plan` again so the next rating measures residual implementation complexity after ambiguity resolution.
