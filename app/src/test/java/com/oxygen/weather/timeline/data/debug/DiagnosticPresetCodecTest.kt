package com.oxygen.weather.timeline.data.debug

import org.junit.Assert.assertEquals
import org.junit.Test

class DiagnosticPresetCodecTest {
    @Test
    fun roundTripsOnlySelectedDisplayGroups() {
        val groups = setOf(DiagnosticGroup.CATALOG, DiagnosticGroup.MISSING)
        assertEquals(groups, DiagnosticPresetCodec.decode(DiagnosticPresetCodec.encode(groups)))
    }

    @Test
    fun ignoresUnknownStoredGroups() {
        assertEquals(setOf(DiagnosticGroup.SELECTION), DiagnosticPresetCodec.decode("SELECTION,UNKNOWN"))
    }
}
