package com.oxygen.weather.timeline.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeCatalogEntry
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeOrigin
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeValidity

@Composable
internal fun CatalogSettingsScreen(
    catalogEntries: List<InstalledThemeCatalogEntry>,
    activeThemeId: String?,
    bundledFreeThemeActive: Boolean,
    onBack: () -> Unit,
    onSelectTheme: (InstalledThemeCatalogEntry) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9F8))
            .statusBarsPadding()
            .navigationBarsPadding()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent(PointerEventPass.Final).changes
                            .filterNot { it.isConsumed }
                            .forEach { it.consume() }
                    }
                }
            }
            .testTag("catalogSettingsScreen"),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .clearAndSetSemantics {
                            contentDescription = "Back to forecast"
                            role = Role.Button
                            onClick(label = "Return to forecast") {
                                onBack()
                                true
                            }
                        }
                        .testTag("catalogSettingsBack"),
                ) {
                    Text("‹", fontSize = 36.sp, color = Color(0xFF173044))
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    "Settings",
                    color = Color(0xFF173044),
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                "Weather world",
                modifier = Modifier.padding(start = 4.dp, top = 18.dp, bottom = 14.dp),
                color = Color(0xFF55717D),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(catalogEntries, key = { it.id.value }) { entry ->
                    val active = when (entry.origin) {
                        InstalledThemeOrigin.BUNDLED_FREE -> bundledFreeThemeActive
                        InstalledThemeOrigin.BUNDLED_SELECTABLE,
                        InstalledThemeOrigin.APP_OWNED -> activeThemeId == entry.id.value
                    }
                    CatalogSettingsThemeRow(entry, active, onSelectTheme)
                }
            }
        }
    }
}

@Composable
private fun CatalogSettingsThemeRow(
    entry: InstalledThemeCatalogEntry,
    active: Boolean,
    onSelect: (InstalledThemeCatalogEntry) -> Unit,
) {
    val kindLabel = when (entry.origin) {
        InstalledThemeOrigin.BUNDLED_FREE -> "Bundled free"
        InstalledThemeOrigin.BUNDLED_SELECTABLE -> "Bundled"
        InstalledThemeOrigin.APP_OWNED -> "App-owned registration"
    }
    val statusLabel = when (entry.validity) {
        InstalledThemeValidity.ALWAYS_AVAILABLE -> null
        InstalledThemeValidity.COMPATIBLE -> "Compatible"
        InstalledThemeValidity.INCOMPATIBLE -> "Incompatible"
        InstalledThemeValidity.DISABLED -> "Disabled"
    }
    val availabilityLabel = when {
        entry.validity == InstalledThemeValidity.INCOMPATIBLE ||
            entry.validity == InstalledThemeValidity.DISABLED -> "Unavailable"
        active -> null
        else -> "Available"
    }

    val canSelect = entry.validity == InstalledThemeValidity.ALWAYS_AVAILABLE ||
        entry.validity == InstalledThemeValidity.COMPATIBLE

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 108.dp)
            .selectable(
                selected = active,
                enabled = canSelect,
                role = Role.RadioButton,
                onClick = { onSelect(entry) },
            )
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFFC7D4D8), RoundedCornerShape(20.dp))
            .padding(horizontal = 18.dp, vertical = 18.dp)
            .testTag("catalogSettingsThemeRow-${entry.id.value}"),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                entry.displayName,
                color = Color(0xFF173044),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                kindLabel,
                modifier = Modifier.padding(top = 7.dp),
                color = Color(0xFF55717D),
                fontSize = 15.sp,
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.End) {
            if (active) {
                CatalogSettingsStatusChip(
                    text = "Active",
                    color = Color(0xFF2B6D53),
                    contentColor = Color.White,
                )
                Spacer(Modifier.size(6.dp))
            }
            statusLabel?.let { label ->
                val (background, foreground) = when (entry.validity) {
                    InstalledThemeValidity.COMPATIBLE -> Color(0xFFE6F1F4) to Color(0xFF173044)
                    InstalledThemeValidity.INCOMPATIBLE -> Color(0xFFFFF0DC) to Color(0xFF6D4A19)
                    InstalledThemeValidity.DISABLED -> Color(0xFFF1E7ED) to Color(0xFF654352)
                    InstalledThemeValidity.ALWAYS_AVAILABLE -> Color.Transparent to Color(0xFF173044)
                }
                CatalogSettingsStatusChip(label, background, foreground)
            }
            availabilityLabel?.let { label ->
                Text(
                    label,
                    modifier = Modifier.padding(top = 6.dp),
                    color = Color(0xFF55717D),
                    fontSize = 13.sp,
                )
            }
        }
    }
}

@Composable
private fun CatalogSettingsStatusChip(text: String, color: Color, contentColor: Color) {
    Box(
        modifier = Modifier
            .background(color, CircleShape)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, color = contentColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}
