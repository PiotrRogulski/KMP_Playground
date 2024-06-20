import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP_Playground",
        state = rememberWindowState(size = DpSize(1280.dp, 720.dp)),
    ) {
        App()
    }
}
