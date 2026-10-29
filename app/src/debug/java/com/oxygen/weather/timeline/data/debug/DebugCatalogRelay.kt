package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Read-only status projection from MainActivity's sole catalog activation owner. */
object DebugCatalogRelay {
    data class State(
        val activation: CatalogActivationState = CatalogActivationState(),
        val requestError: String? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    @JvmStatic
    fun publish(activation: CatalogActivationState, requestError: String? = null) {
        mutableState.value = State(activation, requestError)
    }
}
