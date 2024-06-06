import androidx.compose.runtime.*
import common.*
import di.*
import navigation.*
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.compose.*

@Composable
@Preview
fun App(windowClass: WindowClass) {
    CompositionLocalProvider(LocalWindowClass provides windowClass) {
        KoinApplication({
            modules(appModule())
        }) {
            AppTheme {
                AppNavHost(windowClass)
            }
        }
    }
}
