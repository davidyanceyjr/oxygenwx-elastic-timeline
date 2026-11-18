---
name: rate-plan
description: Read-only assessment of each Oxygen Time Lab plan slice's reasoning, mechanical, and overall difficulty, with gaps and focused recommendations for later plan review.
---

# Rate a slice plan

Assess the current plan as written before suggesting changes. This is a
read-only report gate. Do not edit the plan, checkpoint, roadmap, boards, or
code; do not advance or activate a cycle or run tests. Give recommendations
for the owner to consider one issue at a time through `oxygen-review-plan`
and `resolve-plan-ambiguity`.

## Read set

Read `.codex/current.md`, `.codex/README.md`, the referenced plan and
checkpoint, its ordered slice roster, the exact roadmap entries and dependency
records, and the relevant board and authority passages. Run
`python scripts/dev.py workflow`. Read affected code or prior evidence only
when needed to estimate a named boundary or uncertainty. If there is no
current plan, report that state without inventing one.

## Estimate the workload

Rate each roster slice as its own execution unit. Forecast the tokens likely
to be consumed across its complete execution: necessary input
context, generated implementation and documentation, authorized verification
and evidence, integration, and likely correction turns. Count repeated context
when a step will likely require rereading it. Use a range and a central
estimate; label the estimates as forecasts, not measured usage.

Partition the estimate once, without double counting:

- **Reasoning tokens (R):** reading and reconciling rules, deciding contracts
  and boundaries, interpreting results, and diagnosing expected integration
  issues. Include the input and output involved in those decisions.
- **Mechanical tokens (M):** routine edits, commands, scoped verification,
  evidence assembly, and straightforward implementation after decisions are
  settled. Include their input and output.

Use the plan's named tasks, files, decisions, and checks as the estimate basis.
If a count is based only on file size or analogy, say so; do not present bytes
or lines as measured tokens. Keep a separate allowance for likely correction
turns and allocate it to R or M. The overall estimate is `R + M`.

Convert each central token estimate to a score with the same fixed bands:

| Score | Forecast tokens |
| --- | ---: |
| 1 | up to 2,000 |
| 2 | 2,001–4,000 |
| 3 | 4,001–8,000 |
| 4 | 8,001–16,000 |
| 5 | 16,001–32,000 |
| 6 | 32,001–64,000 |
| 7 | 64,001–128,000 |
| 8 | 128,001–256,000 |
| 9 | 256,001–512,000 |
| 10 | above 512,000 |

Report R, M, and overall token ranges, central estimates, and scores per
slice. Do not turn the sum of slice tokens into a cycle difficulty score or
optimize for it. If a range crosses score bands, report that span as
uncertainty. These are workload scores, not quality judgments or a model's
context-window percentage. Use the
same bands when rerating a revised plan so before/after comparisons are
meaningful.

## Reduce avoidable difficulty

For any slice with an overall score of 8 or higher, audit its concurrent
reasoning demands before execution. Name the decision domains, interfaces,
states, and acceptance obligations the executor must hold together. Identify
which decisions can be resolved from existing repository evidence, which
require an owner decision, and which complexity is intrinsic. Propose the
smallest clarification that reduces ambiguity or drift risk. Recommend
`resolve-plan-ambiguity` for a repository-grounded question when its stage
requirements are met. Keep owner decisions open until answered.

If independently closable outcomes exist, show a concrete dependency-ordered
split, including when three or five slices are the natural boundaries. Choose
boundaries that narrow the number of decisions, interfaces, and acceptance
obligations handled at once, so each execution has a clear focus. Give each
proposed slice its own outcome, exit, board needs, verification, and
evidence. Preserve every original invariant and acceptance obligation. Do not
split merely to lower a numeric score or push hard work into an unnamed later
slice. A focused slice may remain an 8 or higher; that is acceptable when its
contract is clear and its concurrent scope is smaller. Extra tokens are
acceptable when the split reduces likely hallucination or drift. If no coherent
split exists, say why. A context-window threshold audit belongs to
`audit-planned-context` and remains a separate gate.

Estimate the revised R, M, and overall scores for each proposed slice or
clarification, but judge the proposal by reduced concurrent complexity and
preserved acceptance, not lower token totals or scores. State what evidence
would make the estimate change. Report possible gaps separately from confirmed
conflicts and explain their effect on the rating. Recommend the single next
issue to resolve, then rerate only after a later review changes the plan.
Any actual plan or roadmap restructuring belongs to the review workflow and
its board, owner approval, checkpoint, and review rules. Leave all repository
records unchanged regardless of cycle state.

## Response

Give a compact per-slice table with R, M, and overall token ranges and scores;
name the main estimate assumptions. For any slice scoring 8 or higher, report
the complexity audit, the proposed clarification or split, how it narrows
simultaneous demands, revised per-slice ratings, and any owner decision needed.
List possible gaps and the highest-priority issue for the next review pass.
