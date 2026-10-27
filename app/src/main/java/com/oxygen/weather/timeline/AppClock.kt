package com.oxygen.weather.timeline

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.Composable
import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.DatasetId
import java.time.Clock

/** One app-scoped Clock instance can be supplied to Kotlin and Compose request handlers. */
object AppClock {
    val clock: Clock = Clock.systemUTC()
}

val LocalAppClock = compositionLocalOf<Clock> { error("App clock was not supplied") }

/** The returned callback captures Now only when an event invokes it. */
@Composable
fun catalogRequestAtEvent(): (CatalogLocation, DatasetId) -> CatalogLoadRequest {
    val clock = LocalAppClock.current
    return { location, datasetId -> CatalogLoadRequest.atNow(location, datasetId, clock) }
}
