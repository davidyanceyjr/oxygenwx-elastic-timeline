package com.oxygen.weather.timeline.presentation

/** Eight-point compass sector for a reported wind-from bearing. */
enum class CompassDirection(val spokenName: String) {
    NORTH("north"),
    NORTH_EAST("northeast"),
    EAST("east"),
    SOUTH_EAST("southeast"),
    SOUTH("south"),
    SOUTH_WEST("southwest"),
    WEST("west"),
    NORTH_WEST("northwest");

    companion object {
        /** Returns null when direction is not supplied. */
        fun fromBearing(degrees: Double?): CompassDirection? {
            if (degrees == null) return null
            require(degrees.isFinite() && degrees >= 0.0 && degrees < 360.0)
            val sector = ((degrees + 22.5) / 45.0).toInt() % entries.size
            return entries[sector]
        }
    }
}
