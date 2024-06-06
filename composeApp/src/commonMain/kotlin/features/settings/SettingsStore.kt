package features.settings

import androidx.compose.runtime.*
import com.materialkolor.*

// TODO: add persistence
class SettingsStore {
    val themeMode = mutableStateOf(ThemeMode.System)

    val themeStyle = mutableStateOf(PaletteStyle.Fidelity)

    val contrastLevel = mutableStateOf(Contrast.Default)
}

enum class ThemeMode {
    System,
    Light,
    Dark,
}
