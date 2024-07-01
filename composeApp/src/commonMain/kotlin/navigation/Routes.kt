package navigation

import androidx.compose.runtime.*
import androidx.navigation.*
import features.endpoints.*
import features.endpoints.screens.*
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
            fun screen() = Users()
        }

        data object UserByID : Route("user_by_id/{userID}") {
            fun createRoute(userID: Int) = "user_by_id/$userID"

            val arguments = listOf(arg<Int>("userID"))

            @Composable
            fun screen(entry: NavBackStackEntry) =
                UserByID(entry.arguments?.getInt("userID") ?: error("No userID provided"))
        }

        data object Resources : Route("resources") {
            @Composable
            fun screen() = Resources()
        }

        data object ResourceByID : Route("resource_by_id/{resourceID}") {
            fun createRoute(resourceID: Int) = "resource_by_id/$resourceID"

            val arguments = listOf(arg<Int>("resourceID"))

            @Composable
            fun screen(entry: NavBackStackEntry) =
                ResourceByID(entry.arguments?.getInt("resourceID") ?: error("No resourceID provided"))
        }
    }

    data object Settings : Route("settings") {
        @Composable
        fun screen() = SettingsScreen()
    }
}

private inline fun <reified T> arg(name: String) = navArgument(name) {
    type = when (val klass = T::class) {
        Int::class -> NavType.IntType
        IntArray::class -> NavType.IntArrayType
        Long::class -> NavType.LongType
        LongArray::class -> NavType.LongArrayType
        Float::class -> NavType.FloatType
        FloatArray::class -> NavType.FloatArrayType
        Boolean::class -> NavType.BoolType
        BooleanArray::class -> NavType.BoolArrayType
        String::class -> NavType.StringType
        else -> throw IllegalArgumentException(
            "${klass.simpleName} is not supported for navigation arguments.",
        )
    }
}
