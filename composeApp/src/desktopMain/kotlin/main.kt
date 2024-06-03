import androidx.compose.ui.window.*

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP_Playground",
    ) {
        App()
    }
}
