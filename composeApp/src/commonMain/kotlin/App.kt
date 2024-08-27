import androidx.compose.runtime.*
import androidx.window.core.layout.*
import common.*
import di.*
import navigation.*
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.compose.*

@Composable
@Preview
fun App(windowSizeClass: WindowSizeClass) {
    CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
        KoinApplication({ modules(appModule()) }) { AppTheme { AppNavHost(windowSizeClass) } }
    }
}
