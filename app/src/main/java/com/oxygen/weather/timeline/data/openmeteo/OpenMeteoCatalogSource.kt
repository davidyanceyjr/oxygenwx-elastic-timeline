package com.oxygen.weather.timeline.data.openmeteo

import com.oxygen.weather.timeline.data.catalog.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import okhttp3.OkHttpClient

/** Dormant provider source. It has no application wiring or clock/location ownership. */
class OpenMeteoCatalogSource(
    private val transport: OpenMeteoTransport = OpenMeteoTransport(OkHttpClient()),
) : WeatherCatalogSource {
    override val origin: CatalogOrigin = CatalogOrigin.LIVE

    override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
        currentCoroutineContext().ensureActive()
        return when (val response = transport.fetch(request.location)) {
            is OpenMeteoTransport.Result.Success -> when (val mapped = OpenMeteoCatalogMapper.map(response.payload, request)) {
                is OpenMeteoMappingResult.Success -> {
                    val value = mapped.value
                    val window = CatalogWindow.create(value.catalog, value.expectedSupport, dailyExpectedSupport = value.dailyExpectedSupport)
                    if (window !is CatalogWindowResult.Success) {
                        CatalogLoadResult.TransportFailure(TransportFailureReason.INVALID_RESPONSE, CatalogLoadMetadata(detail = "open_meteo:invalid_window"))
                    } else {
                        val complete = window.window.days.all { day ->
                            day.fieldCoverage.values.all { it == CoverageStatus.COMPLETE } &&
                                day.dailyCoverage.values.all { it == CoverageStatus.COMPLETE }
                        }
                        val metadata = CatalogLoadMetadata(rawCapture = response.capture)
                        if (complete) CatalogLoadResult.Success(value.catalog, value.expectedSupport, metadata, value.dailyExpectedSupport)
                        else CatalogLoadResult.Partial(value.catalog, value.expectedSupport, metadata, value.dailyExpectedSupport)
                    }
                }
                OpenMeteoMappingResult.NoData -> CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE)
                is OpenMeteoMappingResult.Invalid -> CatalogLoadResult.TransportFailure(
                    TransportFailureReason.INVALID_RESPONSE,
                    CatalogLoadMetadata(detail = "open_meteo:${mapped.reason.take(180)}"),
                )
            }
            OpenMeteoTransport.Result.Timeout -> CatalogLoadResult.TransportFailure(TransportFailureReason.TIMEOUT, CatalogLoadMetadata(detail = "open_meteo:timeout"))
            OpenMeteoTransport.Result.Connectivity, is OpenMeteoTransport.Result.HttpError -> CatalogLoadResult.TransportFailure(TransportFailureReason.UNAVAILABLE, CatalogLoadMetadata(detail = "open_meteo:unavailable"))
            OpenMeteoTransport.Result.InvalidResponse, OpenMeteoTransport.Result.OversizedResponse -> CatalogLoadResult.TransportFailure(TransportFailureReason.INVALID_RESPONSE, CatalogLoadMetadata(detail = "open_meteo:transport_invalid_response"))
        }
    }
}
