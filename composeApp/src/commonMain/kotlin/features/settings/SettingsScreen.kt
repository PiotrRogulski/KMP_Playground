package features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.materialkolor.*
import common.*
import common.widgets.*
import kmp_playground.composeapp.generated.resources.*
import kmp_playground.composeapp.generated.resources.Res
import org.koin.compose.*

@Composable
fun SettingsScreen(settingsStore: SettingsStore = koinInject()) {
    var themeMode by settingsStore.themeMode
    var themeStyle by settingsStore.themeStyle
    var contrastLevel by settingsStore.contrastLevel
    var pureBlack by settingsStore.pureBlack

    AppScaffold(title = Res.string.settings_title.str) {
        chipSection(
            title = { Res.string.settings_themeMode.str },
            items = ThemeMode.entries,
            isSelected = { it == themeMode },
            onItemSelected = { themeMode = it },
            itemContent = { Text(it.name) },
        )
        item { Spacer(Modifier.height(16.dp)) }
        switchSection(
            title = { Res.string.settings_pureBlack.str },
            checked = pureBlack,
            onCheckedChange = { pureBlack = it },
        )
        item { Spacer(Modifier.height(16.dp)) }
        chipSection(
            title = { Res.string.settings_themeStyle.str },
            items = PaletteStyle.entries,
            isSelected = { it == themeStyle },
            onItemSelected = { themeStyle = it },
            itemContent = { Text(it.name) },
        )
        item { Spacer(Modifier.height(16.dp)) }
        chipSection(
            title = { Res.string.settings_contrastLevel.str },
            items = Contrast.entries.sortedBy { it.value },
            isSelected = { it == contrastLevel },
            onItemSelected = { contrastLevel = it },
            itemContent = { Text(it.name) },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
private fun <T> LazyListScope.chipSection(
    title: @Composable () -> String,
    items: List<T>,
    isSelected: (T) -> Boolean,
    onItemSelected: (T) -> Unit,
    itemContent: @Composable (T) -> Unit,
) {
    item {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(title())
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

private fun LazyListScope.switchSection(
    title: @Composable () -> String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    item {
        Card(modifier = Modifier.fillMaxWidth(), onClick = { onCheckedChange(!checked) }) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(title())
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                )
            }
        }
    }
}
