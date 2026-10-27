package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class WeatherCatalogSourceTest {
    private val now = Instant.parse("2026-10-27T18:00:00Z")
    private val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.88, -87.63), ZoneId.of("America/Chicago"))
    private val datasetId = DatasetId("sample")
    private val request = CatalogLoadRequest.atNow(location, datasetId, Clock.fixed(now, ZoneId.of("UTC")))
    private val day = LocalDate.of(2026, 10, 27)
    private val first = day.atStartOfDay(location.timeZone).toInstant()
    private val support = mapOf(day to mapOf(CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(setOf(first, first.plusSeconds(3600)))))

    private class FakeSource(
        override val origin: CatalogOrigin,
        private val response: (CatalogLoadRequest) -> CatalogLoadResult,
    ) : WeatherCatalogSource {
        var received: CatalogLoadRequest? = null
        override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
            received = request
            return response(request)
        }
    }

    private fun catalog(origin: CatalogOrigin, facts: List<CanonicalWeatherFacts>) = CanonicalCatalog(
        location, datasetId,
        CatalogProvenance(origin, if (origin == CatalogOrigin.SIMULATED)
            CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT else CatalogProductKind.FORECAST),
        now, facts,
    )

    @Test fun threeOriginsUseSameRequestAndCanonicalOutcome() = runBlocking {
        val sources = CatalogOrigin.entries.map { origin ->
            FakeSource(origin) { CatalogLoadResult.Success(catalog(origin, listOf(
                CanonicalWeatherFacts(first, temperatureC = 0.0),
                CanonicalWeatherFacts(first.plusSeconds(3600), temperatureC = 2.0),
            )), support) }
        }
        val registry = CatalogSourceRegistry(sources)
        sources.forEach { source ->
            val result = registry.load(source.origin, request) as CatalogLoadResult.Success
            assertSame(request, source.received)
            assertEquals(now, result.catalog.asOf)
            assertEquals(source.origin, result.catalog.provenance.origin)
            assertSame(result.catalog, acceptedCatalog(null, result))
        }
        assertEquals(now, CatalogLoadRequest.atNow(location, datasetId, Clock.fixed(now, ZoneId.of("Asia/Tokyo"))).asOf)
    }

    @Test fun partialUsesDeclaredSupportAndPreservesMissingVersusUnsupported() = runBlocking {
        val partial = catalog(CatalogOrigin.LIVE, listOf(CanonicalWeatherFacts(first, temperatureC = 0.0)))
        val result = CatalogLoadResult.Partial(partial, support, CatalogLoadMetadata(CatalogCacheState.CACHED))
        val registry = CatalogSourceRegistry(listOf(FakeSource(CatalogOrigin.LIVE) { result }))
        assertSame(result, registry.load(CatalogOrigin.LIVE, request))
        val window = (CatalogWindow.create(partial, support) as CatalogWindowResult.Success).window
        val coverage = window.days.first { it.date == day }.fieldCoverage
        assertEquals(CoverageStatus.PARTIAL, coverage[CatalogField.TEMPERATURE_C])
        assertFalse(coverage.containsKey(CatalogField.VISIBILITY_KM))
        assertEquals(CatalogOrigin.LIVE, partial.provenance.origin)
        assertEquals(CatalogCacheState.CACHED, result.metadata.cacheState)
        assertSame(partial, acceptedCatalog(null, result))
    }

    @Test fun failedAndNoDataOutcomesCannotReplaceCurrentCatalog() = runBlocking {
        val current = catalog(CatalogOrigin.LIVE, emptyList())
        val results = listOf(
            CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE),
            CatalogLoadResult.InvalidRequest(InvalidRequestReason.UNSUPPORTED_DATASET),
            CatalogLoadResult.TransportFailure(TransportFailureReason.TIMEOUT),
        )
        results.forEach { result ->
            assertSame(current, acceptedCatalog(current, result))
            val registry = CatalogSourceRegistry(listOf(FakeSource(CatalogOrigin.LIVE) { result }))
            assertSame(result, registry.load(CatalogOrigin.LIVE, request))
        }
    }

    @Test fun cancellationPropagatesWithoutPublishingResult() = runBlocking {
        val current = catalog(CatalogOrigin.LIVE, emptyList())
        val registry = CatalogSourceRegistry(listOf(FakeSource(CatalogOrigin.LIVE) {
            throw CancellationException("cancelled")
        }))
        try {
            registry.load(CatalogOrigin.LIVE, request)
            fail("Cancellation should propagate")
        } catch (_: CancellationException) {
            assertEquals(CatalogOrigin.LIVE, current.provenance.origin)
        }
    }

    @Test fun registryRejectsIdentityMismatchAndIncorrectCompleteness() = runBlocking {
        val wrong = catalog(CatalogOrigin.REPLAY, listOf(CanonicalWeatherFacts(first, temperatureC = 1.0)))
        val registry = CatalogSourceRegistry(listOf(FakeSource(CatalogOrigin.LIVE) { CatalogLoadResult.Partial(wrong, support) }))
        try { registry.load(CatalogOrigin.LIVE, request); fail("wrong origin") } catch (_: IllegalArgumentException) { }
        val incomplete = CatalogSourceRegistry(listOf(FakeSource(CatalogOrigin.LIVE) {
            CatalogLoadResult.Success(catalog(CatalogOrigin.LIVE, listOf(CanonicalWeatherFacts(first, temperatureC = 1.0))), support)
        }))
        try { incomplete.load(CatalogOrigin.LIVE, request); fail("incomplete success") } catch (_: IllegalArgumentException) { }
    }
}
