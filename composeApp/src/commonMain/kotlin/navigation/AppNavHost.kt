package navigation

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation

val LocalNavController = compositionLocalOf<NavController> { error("No NavController provided") }

@Composable
fun AppNavHost(windowClass: WindowSizeClass) {
    val widthClass = windowClass.widthSizeClass
    val navController = rememberNavController()
    val stackEntry by navController.currentBackStackEntryAsState()

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            bottomBar = {
                if (widthClass == WindowWidthSizeClass.Compact) {
                    BottomNavBar(navController, stackEntry)
                }
            },
        ) { padding ->
            Row {
                if (widthClass == WindowWidthSizeClass.Medium) {
                    NavRail(navController, stackEntry)
                }
                if (widthClass == WindowWidthSizeClass.Expanded) {
                    NavDrawer(navController, stackEntry)
                }
                NavContent(navController, modifier = Modifier.padding(padding).clipToBounds())
            }
        }
    }
}

@Composable
private fun BottomNavBar(navController: NavHostController, stackEntry: NavBackStackEntry?) {
    BottomAppBar {
        NavEntry.entries.forEach { entry ->
            val selected = entry.isSelected(stackEntry)
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
}

@Composable
private fun NavRail(navController: NavHostController, stackEntry: NavBackStackEntry?) {
    NavigationRail {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            NavEntry.entries.forEachIndexed { index, entry ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                val selected = entry.isSelected(stackEntry)
                NavigationRailItem(
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
    }
}

@Composable
private fun NavDrawer(navController: NavHostController, stackEntry: NavBackStackEntry?) {
    PermanentDrawerSheet(modifier = Modifier.width(240.dp)) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            NavEntry.entries.forEach { entry ->
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    selected = entry.isSelected(stackEntry),
                    onClick = {
                        navController.navigate(entry.route.path)
                    },
                    label = { Text(entry.label) },
                    icon = entry.icon,
                )
            }
        }
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
        navigation(route = Route.Endpoints.path, startDestination = Route.Endpoints.List.path) {
            with(Route.Endpoints.List) { composable(path) { screen() } }
            with(Route.Endpoints.Users) { composable(path) { screen() } }
            with(Route.Endpoints.UserByID) { composable(path, arguments) { screen(it) } }
            with(Route.Endpoints.Resources) { composable(path) { screen() } }
            with(Route.Endpoints.ResourceByID) { composable(path, arguments) { screen(it) } }
        }
        with(Route.Settings) { composable(path) { screen() } }
    }
}

private enum class NavEntry(
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
    );

    fun isSelected(stackEntry: NavBackStackEntry?) =
        stackEntry?.destination?.hierarchy?.any { it.route == route.path } == true
}
