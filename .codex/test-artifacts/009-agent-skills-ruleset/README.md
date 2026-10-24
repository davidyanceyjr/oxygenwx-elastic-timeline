# Cycle 009 — Skills and agent rules

## Installed skills

Adapted the ten repository-local oxygenWX skills into `.agents/skills/`:

- `audit-planned-context`
- `compact-history`
- `execute-plan`
- `oxygen-execute-plan`
- `oxygen-plan-next-slice`
- `oxygen-review-plan`
- `rate-plan`
- `resolve-plan-ambiguity`
- `review-planned-draft`
- `slice-select`

Project-local rules are in the repository root `AGENTS.md`. Planning and execution skills now use this repository's authority files, cycle commands, verification entry points, and product boundaries. Device-fleet, production-integration, and automatic-delegation assumptions were not carried over.

## Verification

- `/usr/bin/python3 /home/opsman/.codex/skills/.system/skill-creator/scripts/quick_validate.py <skill-dir>` passed for all ten skills.
- Ruby YAML parsing checked all ten `SKILL.md` frontmatters and all present `agents/openai.yaml` files; names match their directories.
- A local Markdown-link scan found no missing skill references.
- `python scripts/dev.py workflow` passed with cycle `009-agent-skills-ruleset` ACTIVE and mapped to G.2.
- `git diff --check` passed.
- A review for stale production-only paths, device-fleet names, connected-test assumptions, and required delegation found no matches.

No app tests, build, installation, or device capture were run; this cycle changes agent instructions and roadmap records only.

## Limits

Metadata and references were validated mechanically. The skills were not behaviorally exercised against separate planning or implementation tasks in this cycle.
