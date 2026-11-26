# Cycle 181-ox112-park-wall-reading

Date: 2026-11-26
Roadmap items: OX-112

## Outcome

OX-112 completed: added the selected-time icon, forecast description, and Current Temp as perspective-mapped, cropped, weathered paint on Urban Park's left wall. The new Park reader shares generic scene-core painted-path projection and pigment mechanics; installed checks show it updates during timeline scrubbing and remains accessible. Existing tower location placement code was untouched. Recorded cloudy, Drizzle, Sunny, and unavailable-condition captures with focused UI hierarchies.

## Slice outcomes

- **OX-112 — DONE:** Installed Urban Park captures show the selected-time weather icon, description, and Current Temp painted in a perspective-mapped, clipped and textured left-wall mural for cloudy, Drizzle, Sunny, and unavailable-condition states. A direct timeline scrub changed the mural to the new selected Sunny reading. Focused hierarchy captures expose the same mural values accessibly; central duplicates of the moved three values are absent, while Wind chill and other facts remain. The tower location inscription is centered and its placement implementation remains untouched. Final Sunny, Drizzle, cloudy scrub, and unavailable captures are recorded; no guide marks appear in the artwork. Evidence: .codex/test-artifacts/181-ox112-park-wall-reading/README.md, .codex/test-artifacts/181-ox112-park-wall-reading/12-urban-park-drizzle-final.png, .codex/test-artifacts/181-ox112-park-wall-reading/12-urban-park-drizzle-final-ui.xml, .codex/test-artifacts/181-ox112-park-wall-reading/13-urban-park-sunny-final.png, .codex/test-artifacts/181-ox112-park-wall-reading/13-urban-park-sunny-final-ui.xml, .codex/test-artifacts/181-ox112-park-wall-reading/06-urban-park-cloudy-scrub-08-18.png, .codex/test-artifacts/181-ox112-park-wall-reading/06-urban-park-cloudy-scrub-08-18-ui.xml, .codex/test-artifacts/181-ox112-park-wall-reading/11-urban-park-unavailable.png, .codex/test-artifacts/181-ox112-park-wall-reading/11-urban-park-unavailable-ui.xml.

## Verification actually performed

- python scripts/dev.py workflow — passed in CLOSEOUT.
- python scripts/dev.py contract — passed.
- python scripts/dev.py build — passed (57 tasks up to date).
- python scripts/dev.py run --serial 8AKY0M32X — debug build installed and launched successfully.
- Installed Pixel 3 XL captures and focused UI hierarchy dumps — cloudy, direct scrub, Sunny, Drizzle, and unavailable condition; final Sunny and Drizzle captures inspected with feh at DISPLAY=:0.
- git diff --check — passed.
- Automated tests were not added or run, per the authorized OX-112 plan.
- Scope limit: installed acceptance was limited to Urban Park states on Pixel 3 XL; no broad RTL, TalkBack, font-scale, motion, or device matrix was run.
- Tower location placement source was not modified; the existing inscription and Wind chill remain visible in inspected scene captures.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/181-ox112-park-wall-reading/`. Archived plan: `.codex/history/plans/181-ox112-park-wall-reading.md`. See the plan for stage checkpoints and exact boundaries.
