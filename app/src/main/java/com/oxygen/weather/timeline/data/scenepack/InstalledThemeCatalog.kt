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
    /** Null until bundled content has a real app-managed content/release revision. */
    val version: InstalledThemeVersion?,
)

/** Compatibility disposition already established by upstream app-managed pack evaluation. */
enum class AppOwnedThemeCompatibility {
    COMPATIBLE,
    INCOMPATIBLE,
}

/** App-managed selection availability, independent of renderer compatibility. */
enum class AppOwnedThemeAvailability {
    ENABLED,
    DISABLED,
}

/** Explicit app-owned registration supplied by the app-managed registry. */
data class AppOwnedThemeRegistration(
    val packId: String,
    val version: InstalledThemeVersion,
    val displayName: String,
    val compatibility: AppOwnedThemeCompatibility,
    val availability: AppOwnedThemeAvailability = AppOwnedThemeAvailability.ENABLED,
)

enum class InstalledThemeOrigin {
    BUNDLED_FREE,
    APP_OWNED,
}

enum class InstalledThemeValidity {
    ALWAYS_AVAILABLE,
    COMPATIBLE,
    INCOMPATIBLE,
    /** Registered by the app but currently unavailable by an explicit app-managed choice. */
    DISABLED,
}

/** Read-only catalog representation; this data does not activate or validate a theme. */
data class InstalledThemeCatalogEntry(
    val id: InstalledThemeId,
    /** Null only for the bundled-free role before that role has a content revision. */
    val version: InstalledThemeVersion?,
    val displayName: String,
    val origin: InstalledThemeOrigin,
    val validity: InstalledThemeValidity,
) {
    init {
        require(version != null || (origin == InstalledThemeOrigin.BUNDLED_FREE &&
            validity == InstalledThemeValidity.ALWAYS_AVAILABLE)) {
            "Only an unversioned bundled-free role may omit its catalog version"
        }
    }
}

/** Composes a catalog from explicit app-managed metadata without reading storage or the filesystem. */
object InstalledThemeCatalog {
    val BUNDLED_FREE_THEME_ID = InstalledThemeId("living-urban-park")
    val BUNDLED_FREE_ROLE_METADATA = BundledFreeThemeMetadata(
        displayName = "Living Urban Park",
        version = null,
    )

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
                    validity = when (registration.availability) {
                        AppOwnedThemeAvailability.DISABLED -> InstalledThemeValidity.DISABLED
                        AppOwnedThemeAvailability.ENABLED -> when (registration.compatibility) {
                            AppOwnedThemeCompatibility.COMPATIBLE -> InstalledThemeValidity.COMPATIBLE
                            AppOwnedThemeCompatibility.INCOMPATIBLE -> InstalledThemeValidity.INCOMPATIBLE
                        }
                    },
                ),
            )
        }
    }
}
