#!/usr/bin/env python3
"""Render the proposed OX-99 behavior and UI review boards from supplied sources."""
from __future__ import annotations

from base64 import b64encode
from html import escape
from pathlib import Path
import subprocess

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / ".codex/concepts"


def render() -> None:
    OUT.mkdir(parents=True, exist_ok=True)
    dot = OUT / "ox-99-park-behavior-board.dot"
    subprocess.run(["dot", "-Tsvg", str(dot), "-o", str(OUT / "ox-99-park-behavior-board.svg")], check=True)
    subprocess.run(["dot", "-Tpng", str(dot), "-o", str(OUT / "ox-99-park-behavior-board.png")], check=True)
    ui_svg = OUT / "ox-99-park-ui-board.svg"
    ui_png = OUT / "ox-99-park-ui-board.png"
    make_ui_board(ui_svg)
    subprocess.run(["rsvg-convert", "-o", str(ui_png), str(ui_svg)], check=True)


def make_ui_board(destination: Path) -> None:
    def embedded(relative_path: str) -> str:
        data = (ROOT / relative_path).read_bytes()
        return "data:image/png;base64," + b64encode(data).decode("ascii")

    svg = ['''<svg xmlns="http://www.w3.org/2000/svg" width="1800" height="1880" viewBox="0 0 1800 1880">
<defs>
 <pattern id="checker" width="24" height="24" patternUnits="userSpaceOnUse"><rect width="24" height="24" fill="#fff"/><rect width="12" height="12" fill="#e8edf2"/><rect x="12" y="12" width="12" height="12" fill="#e8edf2"/></pattern>
 <filter id="shadow" x="-10%" y="-10%" width="120%" height="130%"><feDropShadow dx="0" dy="3" stdDeviation="4" flood-color="#173b56" flood-opacity=".12"/></filter>
</defs>
<rect width="1800" height="1880" fill="#f2f5f8"/><rect width="1800" height="150" fill="#173b56"/>
''']

    def txt(x: int, y: int, value: str, size: int = 16, color: str = "#21384a", weight: str = "400", anchor: str = "start") -> None:
        svg.append(
            f'<text x="{x}" y="{y}" font-family="DejaVu Sans,Arial,sans-serif" '
            f'font-size="{size}" font-weight="{weight}" fill="{color}" text-anchor="{anchor}">'
            f"{escape(value)}</text>"
        )

    def lines(x: int, y: int, values: list[str], size: int = 13, color: str = "#334e61", weight: str = "400", step: int | None = None) -> None:
        line_step = step or size + 6
        for index, value in enumerate(values):
            txt(x, y + index * line_step, value, size, color, weight)

    def panel(x: int, y: int, width: int, height: int, title: str, subtitle: str) -> None:
        svg.append(
            f'<rect x="{x}" y="{y}" width="{width}" height="{height}" rx="16" '
            'fill="#fff" stroke="#c6d2dc" stroke-width="2" filter="url(#shadow)"/>'
        )
        txt(x + 24, y + 40, title, 23, "#173b56", "700")
        txt(x + 24, y + 68, subtitle, 14, "#53697b")

    # Header
    txt(48, 55, "OX-99 · PARK IMAGE INTEGRATION", 34, "#ffffff", "700")
    txt(48, 95, "OWNER REVIEW DRAFT — gated path selected; composition choices remain open", 18, "#d9e7f0", "600")
    svg.append('<rect x="1385" y="36" width="365" height="52" rx="26" fill="#ffe2ad"/>')
    txt(1567, 69, "NOT APPROVED · NOT DEVICE EVIDENCE", 14, "#603d12", "700", "middle")

    # Source sheets
    panel(40, 180, 900, 650, "Supplied sources — shown on transparency checkerboard", "Source bytes and alpha are unchanged. Visual labels are observations, not authority for plane mapping.")
    thumbnails = [
        ("background.png", "references/background.png", ["Observed: canopy and skyline/buildings,", "banner/lamp, and path fragments."], "Binary alpha; unchanged."),
        ("middleground.png", "references/middleground.png", ["Observed: water tower, gazebo,", "and several trees; alpha is partial."], "Partial alpha; max 254."),
        ("foreground.png", "references/foreground.png", ["Observed: mural wall, one bench,", "blank daily board, paving/water."], "Binary alpha; unchanged."),
    ]
    for index, (name, path, description, alpha) in enumerate(thumbnails):
        x = 76 + index * 284
        svg.append(f'<rect x="{x}" y="265" width="250" height="375" fill="url(#checker)" stroke="#9badbb" stroke-width="1.5"/>')
        svg.append(f'<image x="{x}" y="265" width="250" height="375" preserveAspectRatio="xMidYMid meet" href="{embedded(path)}"/>')
        txt(x, 674, name, 19, "#173b56", "700")
        lines(x, 704, description, 13, "#425a6c", step=19)
        txt(x, 752, alpha, 12, "#754b16" if index == 1 else "#53697b", "600")

    # Owner-designated Concept 01 placement mockup and selected Streetcar variant.
    panel(965, 180, 795, 650, "Owner-designated Park placement mockups", "Both references show intended visual-data placement; displayed values are mock data under OX-41.")
    for x, path, label in [
        (995, "design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png", "CONCEPT 01"),
        (1240, "design_draft/OxygenWX Streetcar Stop at Golden Hour.png", "STREETCAR STOP"),
    ]:
        svg.append(f'<rect x="{x}" y="265" width="225" height="338" fill="url(#checker)" stroke="#9badbb" stroke-width="1.5"/>')
        svg.append(f'<image x="{x}" y="265" width="225" height="338" preserveAspectRatio="xMidYMid meet" href="{embedded(path)}"/>')
        txt(x, 630, label, 14, "#173b56", "700")
    txt(1490, 296, "Concept 01 roles", 14, "#173b56", "700")
    lines(1490, 320, ["Tower: location.", "Mural: current temp + icon;", "owner adds wind chill.", "Banner: wind.", "Daily board: date, HI/LO,", "precipitation.", "Bottom strip: daily cards."], 11, "#334e61", step=16)
    svg.append('<line x1="1490" y1="438" x2="1735" y2="438" stroke="#d2dce4" stroke-width="2"/>')
    txt(1490, 462, "Owner-selected allocation", 13, "#173b56", "700")
    lines(1490, 484, ["Shelter: hourly NEXT HOURS.", "Separate board keeps daily", "date/HI/LO/precipitation.", "Streetcar mockup shows three", "sample rows; exact range is open."], 11, "#334e61", step=16)
    svg.append('<rect x="990" y="680" width="745" height="112" rx="10" fill="#fff1da" stroke="#c3ced7"/>')
    lines(1010, 708, ["Source gap: none of the three supplied transparent PNGs contains a shelter.",
        "The current Park renderer draws a procedural shelter, but it has no forecast sign or rows.",
        "Choose its disposition/source and sign treatment before OX-102/OX-99; exact hourly count/range also remains open."], 12, "#754b16", "600", step=23)

    # Nine independent element rows, so no distinct dispositions are bundled.
    panel(40, 850, 1720, 590, "Existing Park pass map — decisions deliberately left open", "For every element, choose a plane and RETAIN / REPLACE / SUPPRESS. Source overlap does not establish equivalence.")
    x0, y0 = 64, 938
    widths = [190, 300, 640, 270, 270]
    headers = ["CURRENT PASS", "CURRENT DRAW / ID", "OBSERVED SOURCE OVERLAP", "TARGET PLANE", "OWNER DISPOSITION"]
    x = x0
    for width, header in zip(widths, headers):
        svg.append(f'<rect x="{x}" y="{y0}" width="{width}" height="44" fill="#dce8f0" stroke="#aebdca"/>')
        txt(x + 10, y0 + 28, header, 13, "#173b56", "700")
        x += width
    rows = [
        ("BACKGROUND", ["DISTANT_BACKDROP"], ["Procedural horizon/skyline; source background has", "skyline/buildings, canopy, banner/lamp, path fragments."]),
        ("MURAL_WALL", ["MURAL_WALL"], ["Owner: current temp/wind chill/icon. Foreground has", "mural wall and blank board for daily summary."]),
        ("PARK_LANDMARKS", ["STREETCAR_SHELTER"], ["No shelter in supplied PNGs. Procedural shelter", "exists but has no hourly sign; source is open."]),
        ("PARK_LANDMARKS", ["TREE_LEFT"], ["Procedural left tree overlaps tree/canopy forms in", "the source layers."]),
        ("PARK_LANDMARKS", ["TREE_RIGHT"], ["Procedural right tree overlaps foliage in background", "and middleground sources."]),
        ("PATH", ["CURVED_PATH"], ["Procedural curve overlaps paving, hopscotch markings,", "wet-looking surfaces, and water-edge details."]),
        ("FOREGROUND", ["ENTRANCE"], ["Procedural entrance threshold; no matching entrance", "element has been identified in the source layers."]),
        ("FOREGROUND", ["BENCH_LEFT"], ["Procedural left bench overlaps the single visible bench", "in the foreground source."]),
        ("FOREGROUND", ["BENCH_RIGHT"], ["No matching second bench visible; source has daily", "board and water feature in this area."]),
    ]
    for row_index, (current_pass, draw_id, overlap) in enumerate(rows):
        y = y0 + 44 + row_index * 50
        fill = "#ffffff" if row_index % 2 == 0 else "#f7f9fb"
        cells = [[current_pass], draw_id, overlap, ["[ ] Background", "[ ] Middleground", "[ ] Foreground"], ["[ ] Retain", "[ ] Replace", "[ ] Suppress"]]
        x = x0
        for column, (width, cell) in enumerate(zip(widths, cells)):
            svg.append(f'<rect x="{x}" y="{y}" width="{width}" height="50" fill="{fill}" stroke="#c3ced7"/>')
            if column in (3, 4):
                lines(x + 10, y + 12, cell, 10, "#425a6c", step=13)
            else:
                lines(x + 10, y + 19, cell, 11, "#21384a", "700" if column < 2 else "400", step=15)
            x += width

    # State review
    panel(40, 1460, 1720, 315, "State review — static-art behavior needs an explicit owner choice", "Current app behavior and source appearance are separated. This board does not select a fixed-art policy.")
    states = [
        ("DAYLIGHT / SUPPORTED VALUE", ["Current: shared sky and facts", "follow app inputs."], ["Source: art is sunlit; no", "forecast text is baked in."], ["Show supplied art or suppress?", "Alternates need separate scope."]),
        ("NIGHT / SUPPORTED VALUE", ["Current: shared sky follows", "the selected light phase."], ["Source: art stays sunlit;", "lamps are lit."], ["Show supplied art or suppress?", "Alternates need separate scope."]),
        ("SUPPORTED RAIN", ["Current: rain renderer follows", "supported weather input."], ["Source: wet-looking paving", "and water details."], ["Keep with rain or suppress?", "Alternates need separate scope."]),
        ("SUPPORTED SNOW", ["Current: snow renderer follows", "supported weather input."], ["Source: no visible snow;", "art remains sunlit."], ["Keep with snow or suppress?", "Alternates need separate scope."]),
        ("UNAVAILABLE VALUE", ["Current: center and MURAL", "surface show unavailable."], ["Source: no forecast text", "is baked into the art."], ["Choose reading surface and", "image fallback together."]),
    ]
    for index, (title, current, source, question) in enumerate(states):
        x = 64 + index * 340
        svg.append(f'<rect x="{x}" y="1538" width="320" height="205" rx="10" fill="#f9fbfc" stroke="#c3ced7"/>')
        txt(x + 12, 1565, title, 11, "#173b56", "700")
        lines(x + 12, 1592, current, 11, "#334e61", step=17)
        lines(x + 12, 1643, source, 11, "#334e61", step=17)
        svg.append(f'<rect x="{x}" y="1694" width="320" height="49" fill="#fff1da" stroke="#c3ced7"/>')
        lines(x + 14, 1713, question, 11, "#754b16", "700", step=15)

    # Footer
    txt(46, 1815, "Owner confirmed ownership and authorized uses (2026-11-24); creator/source and separate terms were not supplied. Preserve source bytes/alpha.", 13, "#425a6c", "600")
    txt(1750, 1842, "Mockups available as placement references · sample values are not forecast truth", 13, "#9a3e2d", "700", "end")
    svg.append("</svg>")
    destination.write_text("".join(svg), encoding="utf-8")


if __name__ == "__main__":
    render()
