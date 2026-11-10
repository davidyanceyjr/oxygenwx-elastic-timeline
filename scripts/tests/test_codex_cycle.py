import hashlib
import pathlib
import sys
import tempfile
import unittest

sys.path.insert(0, str(pathlib.Path(__file__).resolve().parents[1]))
import codex_cycle as cycle


class CycleWorkflowTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.root = pathlib.Path(self.temp.name)
        (self.root / ".codex/plans").mkdir(parents=True)
        (self.root / ".codex/history/plans").mkdir(parents=True)
        (self.root / ".codex/current.md").write_text(
            "# Current Development Cycle\n\nState: IDLE\nLast updated: 2026-11-07\n",
            encoding="utf-8",
        )
        self.write_roadmap()
        (self.root / ".codex/concepts/behavior.svg").parent.mkdir(parents=True)
        (self.root / ".codex/concepts/behavior.svg").write_text("<svg/>", encoding="utf-8")
        (self.root / ".codex/concepts/ui.svg").write_text("<svg/>", encoding="utf-8")
        (self.root / ".codex/test-artifacts/cycle").mkdir(parents=True)
        (self.root / ".codex/test-artifacts/cycle/evidence.txt").write_text("evidence", encoding="utf-8")

    def tearDown(self):
        self.temp.cleanup()

    def write_roadmap(self, statuses=None, depends_first="None", depends_second="WF.1"):
        statuses = statuses or {"WF.1": "NEXT", "WF.2": "PLANNED", "WF.3": "PLANNED"}
        text = "# Roadmap\n\n## WF\n\n"
        text += f"### WF.1 — First task — {statuses['WF.1']}\n\n**Depends on:** {depends_first}.\n**Exit:** first exit\n\n"
        text += f"### WF.2 — Second task — {statuses['WF.2']}\n\n**Depends on:** {depends_second}.\n**Exit:** second exit\n\n"
        text += f"### WF.3 — Independent task — {statuses['WF.3']}\n\n**Depends on:** None.\n**Exit:** third exit\n"
        (self.root / "docs").mkdir(exist_ok=True)
        (self.root / "docs/ROADMAP.md").write_text(text, encoding="utf-8")

    def start(self, slices=("WF.1",), cycle_id="001-test-cycle"):
        cycle.start_cycle(self.root, cycle_id, list(slices), "Focused workflow cycle tests")
        return self.root / ".codex/plans" / f"{cycle_id}.md"

    def finalize_plan(self, plan, approved=True, stage="REVIEW"):
        text, manifest, checkpoint = cycle.load_plan_records(plan)
        manifest["plan_maturity"] = "FINAL"
        for entry in manifest["slices"]:
            entry["exit"] = f"Observable exit for {entry['id']}"
            behavior = entry["boards"]["behavior"]
            behavior.update({
                "path": ".codex/concepts/behavior.svg",
                "approved": approved,
                "approved_by": "product owner" if approved else None,
                "approved_on": "2026-11-07" if approved else None,
                "artifacts": [{
                    "path": ".codex/concepts/behavior.svg",
                    "sha256": hashlib.sha256((self.root / ".codex/concepts/behavior.svg").read_bytes()).hexdigest(),
                }] if approved else [],
            })
            entry["boards"]["ui_artwork"] = {
                "required": False,
                "path": None,
                "approved": False,
                "reason": "Workflow tooling has no UI output.",
            }
        checkpoint["stage"] = stage
        checkpoint["next_stage_read_set"] = [".codex/current.md", ".codex/README.md"]
        checkpoint["next_action"] = "Continue with the saved stage."
        text = text.replace("Plan maturity: DRAFT", "Plan maturity: FINAL")
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        if stage in cycle.PLANNING_STAGES:
            cycle._write_current(self.root, "PLANNED", manifest["cycle_id"],
                                 [entry["id"] for entry in manifest["slices"]], None,
                                 stage, str(plan.relative_to(self.root)))
        return manifest, checkpoint

    def activate(self, plan):
        self.finalize_plan(plan)
        cycle.activate_cycle(self.root)

    def test_new_single_slice_cycle_retains_legacy_start_interface(self):
        plan = self.start()
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["Stage"], "SLICE")
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["Cycle slices"], "WF.1")
        cycle.validate_workflow(self.root)
        self.assertIn("cycle-manifest:start", plan.read_text(encoding="utf-8"))

    def test_start_requires_current_frontier_and_planned_later_slices_before_writing(self):
        self.write_roadmap({"WF.1": "PLANNED", "WF.2": "NEXT", "WF.3": "PLANNED"})
        with self.assertRaisesRegex(cycle.WorkflowError, "first cycle slice must be.*NEXT"):
            cycle.start_cycle(self.root, "001-frontier-cycle", ["WF.1"], "Must start at the frontier")
        self.assertFalse((self.root / ".codex/plans/001-frontier-cycle.md").exists())
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["State"], "IDLE")

        self.write_roadmap({"WF.1": "NEXT", "WF.2": "DONE", "WF.3": "PLANNED"})
        with self.assertRaisesRegex(cycle.WorkflowError, "Later cycle slices must be PLANNED"):
            cycle.start_cycle(self.root, "001-frontier-cycle", ["WF.1", "WF.2"], "No completed slice in a new roster")
        self.assertFalse((self.root / ".codex/plans/001-frontier-cycle.md").exists())
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["State"], "IDLE")

    def test_new_cycle_uses_next_unique_chronological_number(self):
        (self.root / ".codex/history/004-previous.md").write_text("closed", encoding="utf-8")
        with self.assertRaisesRegex(cycle.WorkflowError, "Next cycle number is 005"):
            cycle.start_cycle(self.root, "006-skipped-cycle", ["WF.1"], "Must not skip")
        self.assertFalse((self.root / ".codex/plans/006-skipped-cycle.md").exists())
        cycle.start_cycle(self.root, "005-next-cycle", ["WF.1"], "Use the next cycle ID")

    def test_activation_requires_final_plan_and_every_approved_board(self):
        plan = self.start(("WF.1", "WF.2"))
        with self.assertRaisesRegex(cycle.WorkflowError, "FINAL"):
            cycle.activate_cycle(self.root)
        self.finalize_plan(plan, approved=False)
        with self.assertRaisesRegex(cycle.WorkflowError, "approval"):
            cycle.activate_cycle(self.root)
        self.finalize_plan(plan, approved=True)
        text, manifest, checkpoint = cycle.load_plan_records(plan)
        second_board = manifest["slices"][1]["boards"]["behavior"]
        second_board.update({"approved": False, "approved_by": None, "approved_on": None, "artifacts": []})
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        with self.assertRaisesRegex(cycle.WorkflowError, "behavior board approval for WF.2"):
            cycle.activate_cycle(self.root)
        self.finalize_plan(plan, approved=True)
        cycle.activate_cycle(self.root)
        fields = cycle._read_fields(self.root / ".codex/current.md")
        self.assertEqual(fields["State"], "ACTIVE")
        self.assertEqual(fields["Active slice"], "WF.1")
        self.assertEqual(fields["Stage"], "EXECUTE")
        self.assertEqual(cycle._roadmap_items(self.root)["WF.1"]["status"], "ACTIVE")
        cycle.validate_workflow(self.root)

    def test_changed_approved_board_input_invalidates_the_saved_gate(self):
        plan = self.start()
        self.finalize_plan(plan)
        board = self.root / ".codex/concepts/behavior.svg"
        board.write_text("<svg changed='true'/>", encoding="utf-8")
        with self.assertRaisesRegex(cycle.WorkflowError, "changed after approval"):
            cycle.validate_workflow(self.root)

    def test_paused_changed_board_requires_reapproval_and_plan_review_before_resume(self):
        plan = self.start()
        self.activate(plan)
        cycle.pause_cycle(self.root)
        board = self.root / ".codex/concepts/behavior.svg"
        board.write_text("<svg changed='true'/>", encoding="utf-8")
        with self.assertRaisesRegex(cycle.WorkflowError, "changed after approval"):
            cycle.validate_workflow(self.root)
        cycle.approve_board(self.root, "WF.1", "behavior", [".codex/concepts/behavior.svg"], "product owner", "2026-11-07")
        with self.assertRaisesRegex(cycle.WorkflowError, "open owner question"):
            cycle.resume_cycle(self.root)
        cycle.update_checkpoint(
            self.root,
            [".codex/current.md", str(plan.relative_to(self.root))],
            "Resume execution after recording the board/plan review.",
            outputs=["Re-reviewed the changed board against the approved plan."],
            gates=["board_plan_relationship_rechecked"],
            decisions=["The approved plan remains in scope after the updated board review."],
            open_questions=[],
        )
        cycle.resume_cycle(self.root)
        fields = cycle._read_fields(self.root / ".codex/current.md")
        self.assertEqual(fields["State"], "ACTIVE")
        self.assertEqual(fields["Active slice"], "WF.1")

    def test_dependency_order_pause_resume_progression_and_closeout(self):
        plan = self.start(("WF.1", "WF.2"))
        self.activate(plan)
        first_checkpoint = cycle.load_plan_records(plan)[2]
        cycle.pause_cycle(self.root)
        paused = cycle._read_fields(self.root / ".codex/current.md")
        self.assertEqual(paused["State"], "PAUSED")
        self.assertEqual(paused["Resume state"], "ACTIVE")
        self.assertEqual(paused["Stage"], "EXECUTE")
        self.assertEqual(paused["Active slice"], "WF.1")
        self.assertEqual(cycle.load_plan_records(plan)[2], first_checkpoint)
        cycle.resume_cycle(self.root)
        cycle.finish_slice(self.root, "WF.1", "DONE", "First exit met", [".codex/test-artifacts/cycle/evidence.txt"])
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["Active slice"], "WF.2")
        cycle.finish_slice(self.root, "WF.2", "DONE", "Second exit met", [".codex/test-artifacts/cycle/evidence.txt"])
        cycle.advance_stage(self.root, [".codex/current.md", plan.name], "Review final evidence.", outputs=["Both ordered slices closed."])
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["Stage"], "CLOSEOUT")
        cycle.pause_cycle(self.root)
        cycle.resume_cycle(self.root)
        history, archive = cycle.close_cycle(self.root, "Both slices passed.", ["python -m unittest scripts/tests/test_codex_cycle.py — passed"])
        self.assertTrue(history.is_file())
        self.assertTrue(archive.is_file())
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["State"], "IDLE")
        self.assertEqual(cycle._roadmap_items(self.root)["WF.1"]["status"], "DONE")
        self.assertEqual(cycle._roadmap_items(self.root)["WF.2"]["status"], "DONE")
        cycle.validate_workflow(self.root)

    def test_lifecycle_advances_through_every_stage_with_approved_board_package(self):
        plan = self.start()
        text, manifest, checkpoint = cycle.load_plan_records(plan)
        manifest["slices"][0]["boards"]["ui_artwork"] = {
            "required": False, "path": None, "approved": False,
            "reason": "Workflow tooling has no UI output.",
        }
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        cycle.advance_stage(self.root, ["AGENTS.md", "docs/ROADMAP_SPECIFICATION.md"],
                            "Prepare the WF.1 behavior board.", outputs=["WF.1 roster and dependency checked."],
                            gates=["slice_intake_complete"])
        package = [".codex/concepts/behavior.svg"]
        cycle.approve_board(self.root, "WF.1", "behavior", package, "product owner", "2026-11-07")
        cycle.advance_stage(self.root, [".codex/plans/001-test-cycle.md", ".codex/concepts/behavior.svg"],
                            "Draft the bounded cycle plan.", outputs=["Behavior board approved."],
                            gates=["behavior_board_approved"])
        text, manifest, checkpoint = cycle.load_plan_records(plan)
        manifest["plan_maturity"] = "FINAL"
        manifest["slices"][0]["exit"] = "WF.1 exit is observable and recorded."
        text = text.replace("Plan maturity: DRAFT", "Plan maturity: FINAL")
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        cycle.advance_stage(self.root, [".codex/current.md", str(plan.relative_to(self.root))],
                            "Review and finalize the cycle plan.", outputs=["First draft completed."],
                            gates=["draft_complete"])
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["Stage"], "REVIEW")
        cycle.activate_cycle(self.root)
        cycle.finish_slice(self.root, "WF.1", "DONE", "WF.1 exit met", [".codex/test-artifacts/cycle/evidence.txt"])
        cycle.advance_stage(self.root, [".codex/current.md", str(plan.relative_to(self.root))],
                            "Inspect evidence and close the cycle.", outputs=["WF.1 completed."],
                            gates=["slice_exit_met"])
        _, _, checkpoint = cycle.load_plan_records(plan)
        self.assertEqual(checkpoint["stage"], "CLOSEOUT")
        self.assertIn("WF.1_behavior_board_approved", checkpoint["completed_gates"])
        cycle.close_cycle(self.root, "WF.1 completed every planned stage.", ["Cycle workflow passed."])

    def test_incomplete_slice_outcome_must_be_recorded_before_close(self):
        plan = self.start()
        self.activate(plan)
        cycle.finish_slice(self.root, "WF.1", "BLOCKED", "External prerequisite unavailable", [".codex/test-artifacts/cycle/evidence.txt"])
        cycle.advance_stage(self.root, [".codex/current.md", plan.name], "Document blocker and closeout.", outputs=["Blocker outcome recorded."])
        self.assertEqual(cycle._roadmap_items(self.root)["WF.1"]["status"], "BLOCKED")
        with self.assertRaisesRegex(cycle.WorkflowError, "non-empty exact verification"):
            cycle.close_cycle(self.root, "Cycle closed with a recorded blocker.", [""])
        history, _ = cycle.close_cycle(self.root, "Cycle closed with a recorded blocker.", ["Workflow validation passed."])
        self.assertIn("BLOCKED", history.read_text(encoding="utf-8"))

    def test_blocked_dependency_records_incomplete_outcomes_for_unstarted_slices(self):
        plan = self.start(("WF.1", "WF.2"))
        self.activate(plan)
        evidence = [".codex/test-artifacts/cycle/evidence.txt"]
        cycle.finish_slice(self.root, "WF.1", "BLOCKED", "Required external condition unavailable", evidence)
        self.assertEqual(cycle._read_fields(self.root / ".codex/current.md")["Active slice"], "None")
        cycle.finish_slice(self.root, "WF.2", "BLOCKED", "Blocked by WF.1 prerequisite", evidence)
        cycle.advance_stage(self.root, [".codex/current.md", str(plan.relative_to(self.root))],
                            "Record both blocked outcomes in closeout.", outputs=["All cycle slices have terminal outcomes."])
        history, _ = cycle.close_cycle(self.root, "Both slices were blocked with recorded reasons.", ["Lifecycle validator passed."])
        result = history.read_text(encoding="utf-8")
        self.assertIn("Required external condition unavailable", result)
        self.assertIn("Blocked by WF.1 prerequisite", result)

    def test_pause_resume_preserves_each_planning_stage_and_roadmap_frontier(self):
        for stage in cycle.PLANNING_STAGES:
            with self.subTest(stage=stage):
                self.tearDown()
                self.setUp()
                plan = self.start(("WF.1",), f"001-{stage.lower()}-cycle")
                self.finalize_plan(plan, stage=stage)
                before = (plan.read_text(encoding="utf-8"), (self.root / "docs/ROADMAP.md").read_text(encoding="utf-8"))
                cycle.pause_cycle(self.root)
                cycle.resume_cycle(self.root)
                fields = cycle._read_fields(self.root / ".codex/current.md")
                self.assertEqual(fields["State"], "PLANNED")
                self.assertEqual(fields["Stage"], stage)
                self.assertEqual((plan.read_text(encoding="utf-8"), (self.root / "docs/ROADMAP.md").read_text(encoding="utf-8")), before)

    def test_validator_rejects_pointer_checkpoint_roster_and_dependency_errors(self):
        plan = self.start(("WF.1", "WF.2"))
        self.finalize_plan(plan)
        self.write_roadmap(depends_first="WF.2", depends_second="None")
        text, manifest, checkpoint = cycle.load_plan_records(plan)
        manifest["slices"][0]["depends_on"] = ["WF.2"]
        manifest["slices"][1]["depends_on"] = []
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        with self.assertRaisesRegex(cycle.WorkflowError, "later/equal"):
            cycle.validate_workflow(self.root)

        text, manifest, checkpoint = cycle.load_plan_records(plan)
        manifest["slices"][1]["id"] = "WF.1"
        checkpoint["slice_ids"] = ["WF.1", "WF.1"]
        text = text.replace("Roadmap items: WF.1, WF.2", "Roadmap items: WF.1, WF.1")
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        cycle._write_current(self.root, "PLANNED", "001-test-cycle", ["WF.1", "WF.1"], None, "REVIEW", str(plan.relative_to(self.root)))
        with self.assertRaisesRegex(cycle.WorkflowError, "Duplicate slice ID"):
            cycle.validate_workflow(self.root)

        text, manifest, checkpoint = cycle.load_plan_records(plan)
        manifest["slices"][1]["id"] = "WF.2"
        checkpoint["slice_ids"] = ["WF.1", "WF.2"]
        checkpoint.pop("next_action")
        text = text.replace("Roadmap items: WF.1, WF.1", "Roadmap items: WF.1, WF.2")
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        cycle._write_current(self.root, "PLANNED", "001-test-cycle", ["WF.1", "WF.2"], None, "REVIEW", str(plan.relative_to(self.root)))
        with self.assertRaisesRegex(cycle.WorkflowError, "next_action"):
            cycle.validate_workflow(self.root)

        checkpoint["next_action"] = "Continue with the saved stage."
        self.write_roadmap()
        manifest["slices"][0]["depends_on"] = []
        manifest["slices"][1]["depends_on"] = ["WF.1"]
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        cycle._write_current(self.root, "PLANNED", "001-test-cycle", ["WF.1", "WF.2"], "WF.1", "REVIEW", str(plan.relative_to(self.root)))
        with self.assertRaisesRegex(cycle.WorkflowError, "Active slice"):
            cycle.validate_workflow(self.root)

    def test_validator_rejects_missing_external_dependency_and_checkpoint(self):
        self.write_roadmap({"WF.1": "PLANNED", "WF.2": "NEXT", "WF.3": "PLANNED"}, depends_second="None")
        plan = self.start(("WF.2",))
        self.finalize_plan(plan)
        text, manifest, checkpoint = cycle.load_plan_records(plan)
        self.write_roadmap({"WF.1": "PLANNED", "WF.2": "NEXT", "WF.3": "PLANNED"}, depends_second="WF.3")
        manifest["slices"][0]["depends_on"] = ["WF.3"]
        cycle.save_plan_records(plan, text, manifest, checkpoint)
        with self.assertRaisesRegex(cycle.WorkflowError, "unresolved"):
            cycle.validate_workflow(self.root)

        text, manifest, checkpoint = cycle.load_plan_records(plan)
        text = text.replace(cycle.CHECKPOINT_START, "<!-- removed checkpoint -->")
        plan.write_text(text, encoding="utf-8")
        with self.assertRaisesRegex(cycle.WorkflowError, "requires cycle manifest and checkpoint"):
            cycle.validate_workflow(self.root)

    def test_legacy_single_slice_pointer_remains_readable(self):
        self.write_roadmap({"WF.1": "ACTIVE", "WF.2": "PLANNED", "WF.3": "PLANNED"})
        (self.root / ".codex/plans/legacy.md").write_text("# Legacy\n\nState: ACTIVE\n", encoding="utf-8")
        (self.root / ".codex/current.md").write_text(
            "# Current Development Cycle\n\nState: ACTIVE\nCycle ID: 119-legacy\nRoadmap item: WF.1\nPlan: .codex/plans/legacy.md\n",
            encoding="utf-8",
        )
        cycle.validate_workflow(self.root)


if __name__ == "__main__":
    unittest.main()
