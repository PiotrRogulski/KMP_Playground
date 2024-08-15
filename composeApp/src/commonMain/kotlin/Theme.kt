import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import com.materialkolor.*
import features.settings.*
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
    val pureBlack by settingsStore.pureBlack

    val themeState =
        rememberDynamicMaterialThemeState(
            // Maybe TODO: allow configuring the seed color?
            seedColor,
            isDark =
                when (themeMode) {
                    ThemeMode.System -> isSystemInDarkTheme()
                    ThemeMode.Light -> false
                    ThemeMode.Dark -> true
                },
            isAmoled = pureBlack,
            style = style,
            contrastLevel = contrastLevel.value,
        )

    DynamicMaterialTheme(
        state = themeState,
        animate = true,
        content = content,
    )
}
