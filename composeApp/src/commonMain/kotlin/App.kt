import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.*
import common.*
import di.*
import navigation.*
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.compose.*

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
@Preview
fun App(windowSizeClass: WindowSizeClass = calculateWindowSizeClass()) {
    CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
        KoinApplication({ modules(appModule()) }) { AppTheme { AppNavHost(windowSizeClass) } }
    }
}
