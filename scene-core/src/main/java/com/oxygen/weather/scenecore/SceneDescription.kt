package com.oxygen.weather.scenecore

import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Theme-owned drawing callbacks in back-to-front order.
 *
 * The caller owns the order and contents of each entry. The shared core does not inspect forecast
 * values, select a theme, or reorder entries. It snapshots the supplied list so later mutations
 * to the caller's list cannot change this description's draw sequence.
 */
public class SceneDescription(entries: List<SceneEntry>) {
    public val entries: List<SceneEntry> = entries.toList()
}

/**
 * One theme-owned drawing callback in a [SceneDescription].
 *
 * The callback owns only the drawing for this entry and is invoked with the active [DrawScope].
 */
public class SceneEntry(
    public val draw: DrawScope.() -> Unit,
)

/**
 * Executes the scene entries exactly once each, in the order supplied by the owning theme.
 *
 * Callback ownership stays with the theme; this runner only provides the common ordered execution
 * contract. Forecast interpretation and selected-time decisions remain outside the scene core.
 */
public fun DrawScope.drawScene(description: SceneDescription) {
    description.entries.forEach { entry -> entry.draw(this) }
}
