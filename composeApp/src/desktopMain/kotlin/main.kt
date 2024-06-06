import androidx.compose.ui.window.*
import common.*

fun main() = application {
    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP_Playground",
        state = windowState,
    ) {
        App(windowClass = WindowClass.fromWidth(windowState.size.width))
    }
}
