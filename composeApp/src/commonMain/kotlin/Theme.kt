import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import com.materialkolor.*
import features.settings.*
import kotlinx.serialization.json.JsonNull.content
import org.koin.compose.*

val seedColor = Color(0xFFFF00FF)

@Composable
fun AppTheme(
    settingsStore: SettingsStore = koinInject(),
    content: @Composable () -> Unit,
) {
    val themeMode by settingsStore.themeMode
    val style by settingsStore.themeStyle
    val contrastLevel by settingsStore.contrastLevel

    val themeState = rememberDynamicMaterialThemeState(
        // Maybe TODO: allow configuring the seed color?
        seedColor,
        isDark = when (themeMode) {
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        },
        style = style,
        contrastLevel = contrastLevel.value,
    )

    DynamicMaterialTheme(
        state = themeState,
        animate = true,
        content = content,
    )
}
