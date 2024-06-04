package navigation

sealed class Route(val path: String) {
    data object Home : Route("home")

    data object Endpoints : Route("data") {
        data object List : Route("list")
        data object Users : Route("users")
    }

    data object Settings : Route("settings")
}
