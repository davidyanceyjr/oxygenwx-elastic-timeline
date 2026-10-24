---
name: rate-plan
description: Assess the current Oxygen Time Lab implementation plan and recommend the cheapest suitable model, with confidence and a fallback. Does not edit or activate the plan.
---

# Recommend a model for the current plan

Read `.codex/current.md` and the plan it references. Consult the relevant
roadmap, implementation, and verification requirements as needed. If there is
no current plan, say so rather than assessing an invented one.

The primary outcome is a practical recommendation for the **cheapest model
available for this job that is likely to meet the plan's acceptance criteria**.
Do not change, activate, expand, or implement the plan.

## Assess the work

Estimate model needs from the work itself, not from a difficulty score alone.
Consider:

- how bounded and implementation-ready the plan is;
- ambiguity, design decisions, unfamiliar APIs, and cross-cutting changes;
- coding, research, testing, integration, and review demands;
- cost of a subtle error and strength of deterministic verification;
- likely input context, generated work, and debugging/retry effort.

Use repository evidence and, when available, results from comparable Oxygen
tasks to estimate which capability tier is likely to pass. Treat past outcomes
as calibration, not a guarantee. If no comparable results exist, state that
the fit estimate is provisional and explain the main uncertainty.

## Compare models and cost

1. Identify the candidate models actually available in the current Codex
   runtime, or use a model shortlist explicitly supplied by the owner. Do not
   assume API model availability implies availability in Codex.
2. For metered API use, check current official pricing for the candidate models
   and distinguish input, cached-input, output, and separately billed tool
   costs where applicable. Browse official provider documentation because
   model availability and prices change. Do not invent prices.
3. Estimate execution workload as a range when uncertain: input tokens,
   output tokens, and likely retry/debugging turns. Estimate expected cost to
   completion from those amounts and the applicable rates, including the
   effect of likely retries. Context-window capacity is a fit constraint only;
   it is not a proxy for cost or capability.
4. If the task runs through a fixed-subscription Codex product without
   exposed per-task marginal pricing, say that a dollar comparison is
   unavailable. Compare the available models by relative cost/usage tier and
   expected completion reliability instead; do not apply API prices as if
   they were Codex charges.
5. Recommend the lowest-cost candidate with a credible path to passing the
   plan. If a model is likely to need significant supervision or rework, count
   that in its expected cost rather than choosing it solely for a lower
   nominal token rate. Name a stronger fallback and the concrete condition
   that should trigger escalation.

If current model availability, pricing, or runtime billing cannot be
established, report that limitation and give only a qualified relative
recommendation based on verified information. Separate facts, estimates, and
assumptions.

## Response

Keep the recommendation concise. Separate reasoning-heavy decisions from
mechanical implementation work, and use this order:

- **Raw implementation difficulty:** score 1–10 for the whole plan as written,
  including design ambiguity, implementation, integration, and verification.
- **Reasoning concentration:** identify where the difficult judgment is
  concentrated (for example, a contract boundary, semantic decision, or
  unfamiliar integration). If no single hotspot dominates, say so.
- **Mechanical implementation difficulty after resolving those decisions:**
  score 1–10 for the remaining execution once the named decisions are settled.
- **Recommendation:** state whether to resolve ambiguity before execution or
  during it, name the cheapest suitable model for the work, and give confidence
  with its key evidence or uncertainty. When useful, say whether one model can
  handle the full implementation after the decisions are resolved.
- **Fallback:** name the next model to try and a specific escalation trigger,
  such as a failed contract decision, repeated test/debug loop, or an
  instrumentation result that exposes an architectural mismatch.
- **Cost basis:** expected input/output workload range and expected cost range
  when prices and billing are available; otherwise state what is missing and
  compare relative cost/usage tiers.
- **Fit risks:** the one to three aspects most likely to exceed the suggested
  model's capability.
- **Context fit:** report context-window capacity only when it could constrain
  execution. Keep it separate from workload and cost estimates.

Treat both difficulty scores as comparative judgment, not as a substitute for
the work-based capability and cost assessment. Keep them calibrated to the
plan: do not inflate raw difficulty just because one decision is subtle, and
do not call the mechanical work easy if integration or installed verification
remains substantial. Name the specific ambiguity hotspots so the owner can
resolve them before execution when that is likely to reduce retries. Do not
recommend escalation solely because a plan contains an ambiguity that can be
settled before coding.
