package features.settings

import com.materialkolor.*
import kotlinx.coroutines.flow.*

// TODO: add persistence
class SettingsStore {
    val themeMode = MutableStateFlow(ThemeMode.SYSTEM)

    val themeStyle = MutableStateFlow(PaletteStyle.Fidelity)

    val contrastLevel = MutableStateFlow(Contrast.Default)
}

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
}
