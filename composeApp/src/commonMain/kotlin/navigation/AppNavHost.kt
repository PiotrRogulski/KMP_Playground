package navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation

val LocalNavController = compositionLocalOf<NavController> { error("No NavController provided") }

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(bottomBar = {
            BottomAppBar {
                val stackEntry by navController.currentBackStackEntryAsState()
                BottomNavEntry.entries.forEach { entry ->
                    val selected = stackEntry?.destination?.hierarchy?.any { it.route == entry.route.path } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(entry.route.path)
                            }
                        },
                        label = { Text(entry.label) },
                        icon = entry.icon,
                    )
                }
            }
        }) { padding -> NavContent(navController, modifier = Modifier.padding(padding)) }
    }
}

@Composable
private fun NavContent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        modifier = modifier,
        startDestination = Route.Home.path,
        enterTransition = { slideIn { IntOffset(it.width / 2, 0) } + fadeIn() },
        exitTransition = { slideOut { IntOffset(-it.width / 2, 0) } + fadeOut() },
        popEnterTransition = { slideIn { IntOffset(-it.width / 2, 0) } + fadeIn() },
        popExitTransition = { slideOut { IntOffset(it.width / 2, 0) } + fadeOut() },
    ) {
        with(Route.Home) { composable(path) { screen() } }
        with(Route.Endpoints) {
            navigation(route = path, startDestination = Route.Endpoints.List.path) {
                with(Route.Endpoints.List) { composable(path) { screen() } }
                with(Route.Endpoints.Users) { composable(path) { screen() } }
                with(Route.Endpoints.UserByID) { composable(path) { screen(it) } }
            }
        }
        with(Route.Settings) { composable(path) { screen() } }
    }
}

enum class BottomNavEntry(
    val route: Route,
    val label: String,
    val icon: @Composable () -> Unit,
) {
    Home(
        Route.Home,
        "Home",
        { Icon(imageVector = Icons.Rounded.Home, contentDescription = null) },
    ),
    Endpoints(
        Route.Endpoints,
        "Endpoints",
        { Icon(imageVector = Icons.AutoMirrored.Rounded.List, contentDescription = null) },
    ),
    Settings(
        Route.Settings,
        "Settings",
        { Icon(imageVector = Icons.Rounded.Settings, contentDescription = null) },
    ),
}
