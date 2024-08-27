import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.window.core.layout.*

fun main() = application {
    val state = rememberWindowState(size = DpSize(1280.dp, 720.dp))

    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP_Playground",
        state = state,
    ) {
        val size = state.size
        App(WindowSizeClass.compute(size.width.value, size.height.value))
    }
}
