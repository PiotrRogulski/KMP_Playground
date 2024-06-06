package features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import com.materialkolor.*
import common.widgets.*
import org.koin.compose.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(settingsStore: SettingsStore = koinInject()) {
    var themeMode by settingsStore.themeMode
    var themeStyle by settingsStore.themeStyle
    var contrastLevel by settingsStore.contrastLevel

    AppScaffold(title = "Settings") {
        item {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeMode.entries.forEach { mode ->
                    ElevatedFilterChip(
                        selected = themeMode == mode,
                        onClick = { themeMode = mode },
                        label = { Text(mode.name) },
                    )
                }
            }
        }
        item {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PaletteStyle.entries.forEach { style ->
                    ElevatedFilterChip(
                        selected = themeStyle == style,
                        onClick = { themeStyle = style },
                        label = { Text(style.name) },
                    )
                }
            }
        }
        item {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Contrast.entries.sortedBy { it.value }.forEach { level ->
                    ElevatedFilterChip(
                        selected = contrastLevel == level,
                        onClick = { contrastLevel = level },
                        label = { Text(level.name) },
                    )
                }
            }
        }
    }
}
