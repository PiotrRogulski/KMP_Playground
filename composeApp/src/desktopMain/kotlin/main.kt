import androidx.compose.ui.window.*

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMM_Playground",
    ) {
        App()
    }
}
