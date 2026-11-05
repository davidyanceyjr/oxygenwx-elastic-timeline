package com.oxygen.weather.timeline.data.catalog

import java.time.Instant

/** Exact successful provider response, kept with the catalog built from it. */
class RawResponseCapture(
    bytes: ByteArray,
    val requestIdentity: String,
    val provider: String,
    val retrievedAt: Instant,
    val captureContractVersion: Int = 1,
) {
    private val body = bytes.clone()
    val bytes: ByteArray get() = body.clone()

    init {
        require(body.isNotEmpty() && body.size <= 2 * 1024 * 1024)
        require(requestIdentity.isNotBlank() && provider.isNotBlank())
        require(captureContractVersion == 1)
    }

    override fun equals(other: Any?): Boolean = other is RawResponseCapture &&
        body.contentEquals(other.body) && requestIdentity == other.requestIdentity &&
        provider == other.provider && retrievedAt == other.retrievedAt &&
        captureContractVersion == other.captureContractVersion

    override fun hashCode(): Int = listOf(body.contentHashCode(), requestIdentity, provider,
        retrievedAt, captureContractVersion).hashCode()
}
