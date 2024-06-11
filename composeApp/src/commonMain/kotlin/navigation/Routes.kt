package navigation

import androidx.compose.runtime.*
import androidx.navigation.*
import features.endpoints.*
import features.endpoints.screens.user_by_id.*
import features.endpoints.screens.users.*
import features.home.*
import features.settings.*

sealed class Route(val path: String) {
    data object Home : Route("home") {
        @Composable
        fun screen() = HomeScreen()
    }

    data object Endpoints : Route("data") {
        data object List : Route("list") {
            @Composable
            fun screen() = EndpointList()
        }

        data object Users : Route("users") {
            @Composable
            fun screen() = UsersScreen()
        }

        data object UserByID : Route("user_by_id/{userID}") {
            fun createRoute(userID: String) = "user_by_id/$userID"

            @Composable
            fun screen(entry: NavBackStackEntry) =
                UserByID(entry.arguments?.getString("userID") ?: error("No userID provided"))
        }
    }

    data object Settings : Route("settings") {
        @Composable
        fun screen() = SettingsScreen()
    }
}
