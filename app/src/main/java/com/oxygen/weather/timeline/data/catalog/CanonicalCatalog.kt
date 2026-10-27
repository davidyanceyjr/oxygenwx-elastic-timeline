package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.util.ArrayList
import java.util.Collections

/**
 * A complete catalog snapshot with one schema shared by live, simulated, and replay origins.
 * `asOf` is when this snapshot was assembled or recorded, not a forecast initialization time.
 */
class CanonicalCatalog(
    val location: CatalogLocation,
    val datasetId: DatasetId,
    val provenance: CatalogProvenance,
    val asOf: Instant,
    facts: List<CanonicalWeatherFacts>,
) {
    /** Defensive, read-only snapshot so callers cannot mutate catalog contents after construction. */
    val facts: List<CanonicalWeatherFacts> = Collections.unmodifiableList(ArrayList(facts))

    override fun equals(other: Any?): Boolean = other is CanonicalCatalog &&
        location == other.location &&
        datasetId == other.datasetId &&
        provenance == other.provenance &&
        asOf == other.asOf &&
        facts == other.facts

    override fun hashCode(): Int = listOf(location, datasetId, provenance, asOf, facts).hashCode()

    override fun toString(): String =
        "CanonicalCatalog(location=$location, datasetId=$datasetId, provenance=$provenance, asOf=$asOf, facts=$facts)"
}
