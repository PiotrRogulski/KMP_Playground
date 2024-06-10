package features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.materialkolor.*
import common.widgets.*
import org.koin.compose.*

@Composable
fun SettingsScreen(settingsStore: SettingsStore = koinInject()) {
    var themeMode by settingsStore.themeMode
    var themeStyle by settingsStore.themeStyle
    var contrastLevel by settingsStore.contrastLevel

    AppScaffold(title = "Settings") {
        chipSection(
            title = "Theme Mode",
            items = ThemeMode.entries,
            isSelected = { it == themeMode },
            onItemSelected = { themeMode = it },
            itemContent = { Text(it.name) },
        )
        item { Spacer(Modifier.height(16.dp)) }
        chipSection(
            title = "Theme Style",
            items = PaletteStyle.entries,
            isSelected = { it == themeStyle },
            onItemSelected = { themeStyle = it },
            itemContent = { Text(it.name) },
        )
        item { Spacer(Modifier.height(16.dp)) }
        chipSection(
            title = "Contrast Level",
            items = Contrast.entries.sortedBy { it.value },
            isSelected = { it == contrastLevel },
            onItemSelected = { contrastLevel = it },
            itemContent = { Text(it.name) },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
private fun <T> LazyListScope.chipSection(
    title: String,
    items: List<T>,
    isSelected: (T) -> Boolean,
    onItemSelected: (T) -> Unit,
    itemContent: @Composable (T) -> Unit,
) {
    item {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(title)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items.forEach { item ->
                        ElevatedFilterChip(
                            selected = isSelected(item),
                            onClick = { onItemSelected(item) },
                            label = { itemContent(item) },
                        )
                    }
                }
            }
        }
    }

}
