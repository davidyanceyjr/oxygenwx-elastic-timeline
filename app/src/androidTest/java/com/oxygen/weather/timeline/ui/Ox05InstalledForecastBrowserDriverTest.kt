package com.oxygen.weather.timeline.ui

import android.content.Intent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oxygen.weather.timeline.data.debug.DebugCatalogActivity
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Ox05InstalledForecastBrowserDriverTest {
    @Test
    fun selectExactModerateRainDayFromDebugFixture() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val automation = instrumentation.uiAutomation

        instrumentation.runOnMainSync {
            instrumentation.targetContext.startActivity(
                Intent(instrumentation.targetContext, DebugCatalogActivity::class.java)
                    .putExtra(DebugCatalogActivity.EXTRA_DEBUG_SESSION, true)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK),
            )
        }
        val fixtureAction = waitForClickableNode(automation) {
            it.text?.toString() == "Load OX-05 review fixture"
        }
        assertTrue("dedicated OX-05 debug action was clicked", fixtureAction.performAction(AccessibilityNodeInfo.ACTION_CLICK))

        val timeline = waitForNode(automation) {
            it.contentDescription?.contains("DEMO DATA") == true &&
                it.contentDescription?.contains("Activate to browse") == true
        }
        assertTrue("OX-05 timeline is exposed as an accessibility click target", timeline.isClickable)
        assertTrue("forecast browser action was accepted", timeline.performAction(AccessibilityNodeInfo.ACTION_CLICK))

        val oct29 = waitForClickableNode(automation) {
            it.text?.contains("Thu, Oct 29") == true
        }
        assertTrue("Oct 29 daily forecast row was clicked", oct29.performAction(AccessibilityNodeInfo.ACTION_CLICK))

        val done = waitForClickableNode(automation) { it.text?.toString() == "Done" }
        assertTrue("forecast browser was dismissed", done.performAction(AccessibilityNodeInfo.ACTION_CLICK))

        val selected = waitForNode(automation) {
            it.contentDescription?.contains("Selected 12:00:00 PM CDT") == true &&
                it.contentDescription?.contains("rain", ignoreCase = true) == true
        }
        assertTrue(selected.contentDescription.toString(), selected.isClickable)
    }

    private fun waitForNode(
        automation: android.app.UiAutomation,
        predicate: (AccessibilityNodeInfo) -> Boolean,
    ): AccessibilityNodeInfo {
        val deadline = System.currentTimeMillis() + 15_000
        while (System.currentTimeMillis() < deadline) {
            automation.rootInActiveWindow?.let { root ->
                findNode(root, predicate)?.let { return it }
            }
            Thread.sleep(200)
        }
        throw AssertionError("Timed out waiting for accessibility node")
    }

    private fun waitForClickableNode(
        automation: android.app.UiAutomation,
        predicate: (AccessibilityNodeInfo) -> Boolean,
    ): AccessibilityNodeInfo {
        val deadline = System.currentTimeMillis() + 15_000
        while (System.currentTimeMillis() < deadline) {
            automation.rootInActiveWindow?.let { root ->
                findClickableAncestor(root, null, predicate)?.let { return it }
            }
            Thread.sleep(200)
        }
        throw AssertionError("Timed out waiting for clickable accessibility node")
    }

    private fun findNode(
        node: AccessibilityNodeInfo,
        predicate: (AccessibilityNodeInfo) -> Boolean,
    ): AccessibilityNodeInfo? {
        if (predicate(node)) return node
        for (index in 0 until node.childCount) {
            node.getChild(index)?.let { child -> findNode(child, predicate)?.let { return it } }
        }
        return null
    }

    private fun findClickableAncestor(
        node: AccessibilityNodeInfo,
        nearestClickable: AccessibilityNodeInfo?,
        predicate: (AccessibilityNodeInfo) -> Boolean,
    ): AccessibilityNodeInfo? {
        val current = if (node.isClickable) node else nearestClickable
        if (predicate(node)) return current
        for (index in 0 until node.childCount) {
            node.getChild(index)?.let { child ->
                findClickableAncestor(child, current, predicate)?.let { return it }
            }
        }
        return null
    }
}
