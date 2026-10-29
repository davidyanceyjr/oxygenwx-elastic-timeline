package com.oxygen.weather.timeline.data.catalog

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SavedLocationContractTest {
    @Test fun canonicalPlaceIdsUsePositiveGeoNamesFeatureIdentity() {
        assertEquals(CanonicalPlaceId("geonames:2950159"), CanonicalPlaceId.geonames(2_950_159))
        assertEquals(CanonicalPlaceId.geonames(2_950_159), CanonicalPlaceId.geonames(2_950_159))
        assertNotEquals(CanonicalPlaceId.geonames(2950159), CanonicalPlaceId.geonames(4250542))
        listOf("2950159", "openmeteo:2950159", "geonames:0", "geonames:-1", "geonames:01", "geonames:999999999999999999999999")
            .forEach { invalid ->
                try {
                    CanonicalPlaceId(invalid)
                    throw AssertionError("Expected invalid canonical ID: $invalid")
                } catch (_: IllegalArgumentException) { }
            }
    }
}
