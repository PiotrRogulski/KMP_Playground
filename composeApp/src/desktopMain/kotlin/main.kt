import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import common.*

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1280.dp, 720.dp))

    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP_Playground",
        state = windowState,
    ) {
        App(windowClass = WindowClass.fromWidth(windowState.size.width))
    }
}
