package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class LivingUrbanParkCompositionTest {
    @Test fun `park retains its three local elements in back to front order`() {
        val selected = Instant.parse("2026-11-22T16:17:18.123456789Z")
        val reading = SceneReading(
            selectedInstant = selected,
            lightInterval = LightInterval(selected.minusSeconds(60), selected.plusSeconds(60), LightPhase.DAY),
        )
        val weather = SceneWeatherDecision.resolve(reading, ZoneId.of("America/Chicago"))

        val composition = LivingUrbanParkTheme.compose(weather)

        assertSame(weather, composition.weatherDecision)
        assertSame(reading, composition.weatherDecision.reading)
        assertEquals(selected, composition.selectedInstant)
        assertEquals(
            listOf(
                LivingUrbanParkElementId.DISTANT_BACKDROP,
                LivingUrbanParkElementId.CURVED_PATH,
                LivingUrbanParkElementId.ENTRANCE,
            ),
            composition.elements.map { it.id },
        )
        assertEquals(
            listOf(
                LivingUrbanParkElementRole.DISTANT_BACKDROP,
                LivingUrbanParkElementRole.PATH,
                LivingUrbanParkElementRole.ENTRANCE,
            ),
            composition.elements.map { it.role },
        )
        assertEquals(
            listOf(
                LivingUrbanParkDepthPass.BACKGROUND,
                LivingUrbanParkDepthPass.PATH,
                LivingUrbanParkDepthPass.FOREGROUND,
            ),
            composition.elements.map { it.pass },
        )
        assertTrue(composition.elements[0].geometry is LivingUrbanParkGeometry.Backdrop)
        assertTrue(composition.elements[1].geometry is LivingUrbanParkGeometry.CurvedPath)
        assertTrue(composition.elements[2].geometry is LivingUrbanParkGeometry.Entrance)
    }
}
