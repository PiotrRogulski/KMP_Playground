import androidx.compose.runtime.*
import di.*
import navigation.*
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.compose.*

@Composable
@Preview
fun App() {
    KoinApplication({
        modules(appModule())
    }) {
        AppTheme {
            AppNavHost()
        }
    }
}
