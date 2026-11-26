package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.scenecore.ScenePaintedSurfaceGeometry
import com.oxygen.weather.scenecore.ScenePlane
import com.oxygen.weather.scenecore.ScenePlanePoint
import com.oxygen.weather.scenecore.ScenePlaneQuadrilateral

/** Urban Park's tower face and its inset lettering area, measured in the Middleground plane. */
internal object UrbanParkLocationSurface {
    val geometry = ScenePaintedSurfaceGeometry(
        plane = ScenePlane.MIDDLEGROUND,
        face = ScenePlaneQuadrilateral(
            topLeft = ScenePlanePoint(.542f, .239f),
            topRight = ScenePlanePoint(.706f, .239f),
            bottomRight = ScenePlanePoint(.715f, .300f),
            bottomLeft = ScenePlanePoint(.535f, .300f),
        ),
        textRegion = ScenePlaneQuadrilateral(
            topLeft = ScenePlanePoint(.557f, .246f),
            topRight = ScenePlanePoint(.693f, .246f),
            bottomRight = ScenePlanePoint(.700f, .294f),
            bottomLeft = ScenePlanePoint(.550f, .294f),
        ),
    )
}
