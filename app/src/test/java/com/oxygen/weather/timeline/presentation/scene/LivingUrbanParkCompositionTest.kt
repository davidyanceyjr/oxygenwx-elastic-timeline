package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class LivingUrbanParkCompositionTest {
    @Test fun `park composition preserves selection and optional data-surface metadata`() {
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
                LivingUrbanParkElementId.MURAL_WALL,
                LivingUrbanParkElementId.STREETCAR_SHELTER,
                LivingUrbanParkElementId.TREE_LEFT,
                LivingUrbanParkElementId.TREE_RIGHT,
                LivingUrbanParkElementId.CURVED_PATH,
                LivingUrbanParkElementId.ENTRANCE,
                LivingUrbanParkElementId.BENCH_LEFT,
                LivingUrbanParkElementId.BENCH_RIGHT,
            ),
            composition.elements.map { it.id },
        )
        assertEquals(
            listOf(
                LivingUrbanParkElementRole.DISTANT_BACKDROP,
                LivingUrbanParkElementRole.MURAL_WALL,
                LivingUrbanParkElementRole.STREETCAR_SHELTER,
                LivingUrbanParkElementRole.VEGETATION,
                LivingUrbanParkElementRole.VEGETATION,
                LivingUrbanParkElementRole.PATH,
                LivingUrbanParkElementRole.ENTRANCE,
                LivingUrbanParkElementRole.BENCH,
                LivingUrbanParkElementRole.BENCH,
            ),
            composition.elements.map { it.role },
        )
        assertEquals(
            listOf(
                LivingUrbanParkDepthPass.BACKGROUND,
                LivingUrbanParkDepthPass.MURAL_WALL,
                LivingUrbanParkDepthPass.PARK_LANDMARKS,
                LivingUrbanParkDepthPass.PARK_LANDMARKS,
                LivingUrbanParkDepthPass.PARK_LANDMARKS,
                LivingUrbanParkDepthPass.PATH,
                LivingUrbanParkDepthPass.FOREGROUND,
                LivingUrbanParkDepthPass.FOREGROUND,
                LivingUrbanParkDepthPass.FOREGROUND,
            ),
            composition.elements.map { it.pass },
        )
        assertTrue(composition.elements[0].geometry is LivingUrbanParkGeometry.Backdrop)
        assertTrue(composition.elements[1].geometry is LivingUrbanParkGeometry.MuralWall)
        assertTrue(composition.elements[2].geometry is LivingUrbanParkGeometry.StreetcarShelter)
        assertTrue(composition.elements[3].geometry is LivingUrbanParkGeometry.Tree)
        assertTrue(composition.elements[4].geometry is LivingUrbanParkGeometry.Tree)
        assertTrue(composition.elements[5].geometry is LivingUrbanParkGeometry.CurvedPath)
        assertTrue(composition.elements[6].geometry is LivingUrbanParkGeometry.Entrance)
        assertTrue(composition.elements[7].geometry is LivingUrbanParkGeometry.Bench)
        assertTrue(composition.elements[8].geometry is LivingUrbanParkGeometry.Bench)

        val expectedFrames = mapOf(
            LivingUrbanParkElementId.STREETCAR_SHELTER to ParkElementGeometry(
                position = ParkPoint(.679167f, .573684f),
                size = ParkElementSize(.341667f, .145614f),
                anchor = ParkPoint(.5f, 1f),
            ),
            LivingUrbanParkElementId.TREE_LEFT to ParkElementGeometry(
                position = ParkPoint(.115f, .825f),
                size = ParkElementSize(.20f, .16f),
                anchor = ParkPoint(.5f, 1f),
            ),
            LivingUrbanParkElementId.TREE_RIGHT to ParkElementGeometry(
                position = ParkPoint(.885f, .68f),
                size = ParkElementSize(.20f, .16f),
                anchor = ParkPoint(.5f, 1f),
            ),
            LivingUrbanParkElementId.BENCH_LEFT to ParkElementGeometry(
                position = ParkPoint(.145f, .835f),
                size = ParkElementSize(.28f, .065f),
                anchor = ParkPoint(.5f, 1f),
            ),
            LivingUrbanParkElementId.BENCH_RIGHT to ParkElementGeometry(
                position = ParkPoint(.855f, .835f),
                size = ParkElementSize(.28f, .065f),
                anchor = ParkPoint(.5f, 1f),
            ),
        )
        expectedFrames.forEach { (id, frame) ->
            val declaration = composition.elements.single { it.id == id }
            val actual = when (val geometry = declaration.geometry) {
                is LivingUrbanParkGeometry.Tree -> geometry.frame
                is LivingUrbanParkGeometry.Bench -> geometry.frame
                is LivingUrbanParkGeometry.StreetcarShelter -> geometry.frame
                else -> error("Unexpected geometry for $id: ${declaration.geometry}")
            }
            assertEquals(frame, actual)
            assertEquals(frame.bounds, actual.bounds)
            assertEquals(null, declaration.dataSurface)
        }
        assertParkBounds(
            ParkElementBounds(ParkPoint(.508333f, .428070f), ParkPoint(.850000f, .573684f)),
            expectedFrames.getValue(LivingUrbanParkElementId.STREETCAR_SHELTER).bounds,
        )
        assertParkBounds(
            ParkElementBounds(ParkPoint(.015f, .665f), ParkPoint(.215f, .825f)),
            expectedFrames.getValue(LivingUrbanParkElementId.TREE_LEFT).bounds,
        )
        assertParkBounds(
            ParkElementBounds(ParkPoint(.785f, .52f), ParkPoint(.985f, .68f)),
            expectedFrames.getValue(LivingUrbanParkElementId.TREE_RIGHT).bounds,
        )
        assertParkBounds(
            ParkElementBounds(ParkPoint(.005f, .77f), ParkPoint(.285f, .835f)),
            expectedFrames.getValue(LivingUrbanParkElementId.BENCH_LEFT).bounds,
        )
        assertParkBounds(
            ParkElementBounds(ParkPoint(.715f, .77f), ParkPoint(.995f, .835f)),
            expectedFrames.getValue(LivingUrbanParkElementId.BENCH_RIGHT).bounds,
        )

        val mural = composition.elements[1]
        val surface = requireNotNull(mural.dataSurface)
        assertEquals(LivingUrbanParkDataSurfaceId.MURAL, surface.id)
        assertEquals(
            LivingUrbanParkExactBindingReference.CANONICAL_SELECTED_VALUE,
            surface.bindingReference,
        )
        assertEquals(surface.id, (mural.geometry as LivingUrbanParkGeometry.MuralWall).surfaceId)
        assertTrue(composition.elements.filter { it.id != LivingUrbanParkElementId.MURAL_WALL }
            .all { it.dataSurface == null })
    }

    private fun assertParkBounds(expected: ParkElementBounds, actual: ParkElementBounds) {
        val tolerance = 0.000001f
        assertEquals(expected.topLeft.x, actual.topLeft.x, tolerance)
        assertEquals(expected.topLeft.y, actual.topLeft.y, tolerance)
        assertEquals(expected.bottomRight.x, actual.bottomRight.x, tolerance)
        assertEquals(expected.bottomRight.y, actual.bottomRight.y, tolerance)
    }
}
