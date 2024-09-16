import androidx.compose.runtime.*
import di.*
import navigation.*
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.compose.KoinApplication
import org.koin.dsl.*

@Composable
@Preview
fun App() {
    KoinApplication({ koinApplication { modules(appModule()) } }) { AppTheme { AppNavHost() } }
}
