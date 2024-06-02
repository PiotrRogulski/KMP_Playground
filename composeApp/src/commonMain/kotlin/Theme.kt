import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import com.materialkolor.*

val seedColor = Color(0xFFFF00FF)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    // TODO: add theme settings
    val themeState = rememberDynamicMaterialThemeState(
        seedColor,
        isDark = isSystemInDarkTheme(),
        style = PaletteStyle.Fidelity,
        contrastLevel = Contrast.Default.value,
    )

    DynamicMaterialTheme(
        state = themeState,
        animate = true,
        content = content,
    )
}
