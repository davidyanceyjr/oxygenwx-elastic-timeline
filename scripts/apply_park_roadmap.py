#!/usr/bin/env python3
"""Generate a full drop-in ROADMAP.md by patching the canonical file safely.
Run from repository root. No repo mutation unless --in-place is supplied.
"""
from __future__ import annotations
import argparse, pathlib, re, sys

START = '## Proposed Park three-plane foundation — artwork-first insertion (owner approval pending)'

CHANGES = {
    'OX-21': (('OX-99', 'OX-107'), 'Portrait camera fitting follows the Park image integration and OX-107 disposition; measure actual mural text against the approved surface map.'),
    'OX-22': (('OX-99', 'OX-107'), 'Scope path/entrance material changes against the approved plane map; avoid redrawing accepted embedded elements.'),
    'OX-48': (('OX-107',), 'Preserve truthful source-time and carried-state disclosure in the approved live-reading surface and plane map.'),
    'OX-86': (('OX-107',), 'Reconcile theme-replaceable mural materials with embedded wall/signage and the approved live-text surface.'),
    'OX-63': (('OX-107',), 'Compare shelter artwork with the accepted source map; do not duplicate embedded structures.'),
    'OX-64': (('OX-107',), 'Account for embedded daylight and illumination; require an approved treatment or alternate source.'),
    'OX-65': (('OX-107',), 'Do not treat tinting fixed daylight artwork as approved night artwork without visual acceptance.'),
    'OX-66': (('OX-107',), 'Account for wet-looking source surfaces while preserving fact-driven rain presentation.'),
    'OX-67': (('OX-107',), 'Respect embedded foliage and surfaces when designing winter treatment.'),
    'OX-68': (('OX-107',), 'Ensure unavailable-state presentation remains explicit over static artwork.'),
    'OX-87': (('OX-107',), 'Use the accepted source map; skyline work must be visibly additive, replaceable, or intentionally modified.'),
    'OX-88': (('OX-107',), 'Review the actual wall source and approved live-text surface before procedural repainting.'),
    'OX-89': (('OX-107',), 'Use the approved asset board to distinguish embedded vegetation/props from drawable replacements.'),
    'OX-90': (('OX-107',), 'Review composed states against approved artwork and record embedded-asset limitations.'),
}

def transform(original: str, insertion: str) -> str:
    if START in original:
        raise ValueError('Three-plane insertion already present; refusing duplicate adoption')
    for i in ('OX-99', 'OX-100', 'OX-101', 'OX-102', 'OX-103', 'OX-104', 'OX-105', 'OX-106', 'OX-107'):
        if re.search(r'(?m)^### ' + re.escape(i) + r' —', original):
            raise ValueError(f'ID {i} is already present in canonical roadmap; reconcile manually')
    # Insert immediately ahead of the unfinished Park art-polish sequence.
    target = re.search(r'(?m)^### OX-21 —', original)
    if not target:
        raise ValueError('Expected OX-21 anchor missing; repo revision needs review')
    result = original[:target.start()] + insertion.rstrip() + '\n\n' + original[target.start():]
    # Amend only listed planned leaf entries; never edit DONE entries or completed history.
    for sid, (dependencies, note) in CHANGES.items():
        pattern = re.compile(r'(?ms)^(### ' + re.escape(sid) + r' —[^\n]* — PLANNED\n)(.*?)(?=^### |^## |\Z)')
        match = pattern.search(result)
        if not match: raise ValueError(f'Expected planned slice {sid} missing or changed')
        body = match.group(2)
        dep = re.search(r'(?m)^\*\*Depends on:\*\* ([^\n]+)$', body)
        if not dep: raise ValueError(f'{sid}: dependencies missing')
        old = dep.group(1).rstrip().rstrip('.')
        vals = [v.strip() for v in old.split(',')]
        for dependency in dependencies:
            if dependency not in vals: vals.append(dependency)
        body = body[:dep.start()] + '**Depends on:** ' + ', '.join(vals) + '.' + body[dep.end():]
        # After reconciliation use a design note, not a false claim of owner-approved polish.
        insertion_note = '**Three-plane reconciliation note (proposal):** ' + note + '\n'
        marker = '**Exit:**'
        if marker not in body: raise ValueError(f'{sid}: exit missing')
        body = body.replace(marker, insertion_note + marker, 1)
        result = result[:match.start()] + match.group(1) + body + result[match.end():]
    # Distinct NEXT statuses remain exactly as in original; no original headings are lost.
    orig_headers = re.findall(r'(?m)^### ([A-Z][A-Z0-9.\-]+) —', original)
    new_headers = re.findall(r'(?m)^### ([A-Z][A-Z0-9.\-]+) —', result)
    if len(new_headers) != len(orig_headers) + 9: raise ValueError('Heading count changed unexpectedly')
    if any(new_headers.count(x) != 1 for x in set(new_headers)): raise ValueError('Duplicate slice ID')
    if len(re.findall(r'(?m)^### .* — NEXT$', original)) != len(re.findall(r'(?m)^### .* — NEXT$', result)):
        raise ValueError('NEXT status count changed')
    if len(re.findall(r'(?m)^### .* — ACTIVE$', original)) != len(re.findall(r'(?m)^### .* — ACTIVE$', result)):
        raise ValueError('ACTIVE status count changed')
    return result

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument('--repo', type=pathlib.Path, default=pathlib.Path('.'))
    ap.add_argument('--out', type=pathlib.Path, default=pathlib.Path('build/park-roadmap-review/ROADMAP.md'))
    ap.add_argument('--in-place', action='store_true', help='explicitly replace docs/ROADMAP.md after review')
    args=ap.parse_args()
    base=args.repo/'docs/ROADMAP.md'
    ins=pathlib.Path(__file__).resolve().parent.parent/'docs/PARK_THREE_PLANE_ROADMAP_INSERTION.md'
    before=base.read_text(encoding='utf-8')
    after=transform(before, ins.read_text(encoding='utf-8'))
    dest=base if args.in_place else (args.repo/args.out)
    dest.parent.mkdir(parents=True,exist_ok=True)
    dest.write_text(after,encoding='utf-8')
    print(f'Wrote {dest} ({len(after)} characters); 9 new IDs, 14 planned slices annotated; original chronology retained')
    print('Review diff and workflow gates before adopting; this script does not approve boards or activate any slice')
if __name__=='__main__': main()
