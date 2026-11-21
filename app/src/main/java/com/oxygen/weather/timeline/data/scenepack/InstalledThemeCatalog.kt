package com.oxygen.weather.timeline.data.scenepack

/** Opaque app-managed revision of installed theme content. It has no ordering semantics. */
@JvmInline
value class InstalledThemeVersion(val value: String) {
    init {
        require(value.isNotEmpty()) { "Installed theme version must not be empty" }
    }
}

/** Stable catalog identity. App-owned entries use their OX-20 packId verbatim. */
@JvmInline
value class InstalledThemeId(val value: String)

/** App-managed metadata for the OX-41 bundled-free role. */
data class BundledFreeThemeMetadata(
    val displayName: String,
    val version: InstalledThemeVersion,
)

/** Compatibility disposition already established by upstream app-managed pack evaluation. */
enum class AppOwnedThemeCompatibility {
    COMPATIBLE,
    INCOMPATIBLE,
}

/** Explicit app-owned registration supplied by the app-managed registry. */
data class AppOwnedThemeRegistration(
    val packId: String,
    val version: InstalledThemeVersion,
    val displayName: String,
    val compatibility: AppOwnedThemeCompatibility,
)

enum class InstalledThemeOrigin {
    BUNDLED_FREE,
    APP_OWNED,
}

enum class InstalledThemeValidity {
    ALWAYS_AVAILABLE,
    COMPATIBLE,
    INCOMPATIBLE,
}

/** Read-only catalog representation; this data does not activate or validate a theme. */
data class InstalledThemeCatalogEntry(
    val id: InstalledThemeId,
    val version: InstalledThemeVersion,
    val displayName: String,
    val origin: InstalledThemeOrigin,
    val validity: InstalledThemeValidity,
)

/** Composes a catalog from explicit app-managed metadata without reading storage or the filesystem. */
object InstalledThemeCatalog {
    val BUNDLED_FREE_THEME_ID = InstalledThemeId("living-urban-park")

    /**
     * Always includes the bundled-free role, even when [appOwnedRegistrations] is empty.
     * App-owned compatibility is carried through from upstream evaluation without revalidation.
     */
    fun query(
        bundledFreeMetadata: BundledFreeThemeMetadata,
        appOwnedRegistrations: Iterable<AppOwnedThemeRegistration>,
    ): List<InstalledThemeCatalogEntry> = buildList {
        add(
            InstalledThemeCatalogEntry(
                id = BUNDLED_FREE_THEME_ID,
                version = bundledFreeMetadata.version,
                displayName = bundledFreeMetadata.displayName,
                origin = InstalledThemeOrigin.BUNDLED_FREE,
                validity = InstalledThemeValidity.ALWAYS_AVAILABLE,
            ),
        )
        appOwnedRegistrations.forEach { registration ->
            add(
                InstalledThemeCatalogEntry(
                    id = InstalledThemeId(registration.packId),
                    version = registration.version,
                    displayName = registration.displayName,
                    origin = InstalledThemeOrigin.APP_OWNED,
                    validity = when (registration.compatibility) {
                        AppOwnedThemeCompatibility.COMPATIBLE -> InstalledThemeValidity.COMPATIBLE
                        AppOwnedThemeCompatibility.INCOMPATIBLE -> InstalledThemeValidity.INCOMPATIBLE
                    },
                ),
            )
        }
    }
}
