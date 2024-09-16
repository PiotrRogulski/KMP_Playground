import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*

fun main() = application {
    val state = rememberWindowState(size = DpSize(1280.dp, 720.dp))

    Window(onCloseRequest = ::exitApplication, title = "KMP_Playground", state = state) { App() }
}
