package com.oxygen.weather.timeline

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinator
import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.data.catalog.SavedLocationRepository
import java.util.ServiceLoader

/** Optional build-variant behavior. Release has no provider; debug behavior stays out of main DEX. */
interface ActivityVariantDelegate {
    fun onMainActivityCreated(host: Host, intent: Intent): Boolean = false
    fun onMainActivityNewIntent(host: Host, intent: Intent): Boolean = false
    fun onCatalogActivationState(host: Host, state: CatalogActivationState) = Unit
    fun createFixturePlaceSearcher(host: Host): PlaceSearcher? = null
    fun openCatalogPicker(host: Host): Boolean = false
    fun onSearchSubmission(host: Host, query: String): Boolean = false
    suspend fun beforeOrdinaryAction(host: Host) = Unit
    fun isDebugSessionActive(host: Host): Boolean = false
    @Composable fun DebugSessionMenu(host: Host) = Unit

    interface Host {
        val activity: Activity
        val activityScope: LifecycleCoroutineScope
        val savedLocations: SavedLocationRepository
        val activationCoordinator: CatalogActivationCoordinator
        fun restoreStartupCatalog()
        fun setDebugSessionMenuVisible(visible: Boolean) = Unit
        fun closeLocationSelector() = Unit
    }

    companion object {
        fun load(classLoader: ClassLoader): ActivityVariantDelegate? = runCatching {
            ServiceLoader.load(ActivityVariantDelegate::class.java, classLoader).firstOrNull()
        }.getOrNull()
    }
}
