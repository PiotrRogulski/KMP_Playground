import androidx.compose.material.*
import androidx.compose.runtime.*
import navigation.*
import org.jetbrains.compose.ui.tooling.preview.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavHost()
    }
}
