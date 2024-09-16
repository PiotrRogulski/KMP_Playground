package navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.*
import androidx.compose.material3.adaptive.navigationsuite.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.window.core.layout.*
import features.endpoints.*
import features.endpoints.screens.*
import features.home.*
import features.settings.*
import navigation.routes.*

val LocalNavController = compositionLocalOf<NavController> { error("No NavController provided") }

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val stackEntry by navController.currentBackStackEntryAsState()
    val windowInfo = currentWindowAdaptiveInfo()
    val sizeClass = windowInfo.windowSizeClass.windowWidthSizeClass
    val layoutType =
        when (sizeClass) {
            WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationDrawer
            else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowInfo)
        }

    val padding = 12.dp

    CompositionLocalProvider(LocalNavController provides navController) {
        NavigationSuiteScaffold(
            layoutType = layoutType,
            navigationSuiteItems = {
                NavEntry.entries.forEachIndexed { index, entry ->
                    item(
                        modifier =
                            when (layoutType) {
                                NavigationSuiteType.NavigationDrawer ->
                                    Modifier.width(240.dp)
                                        .padding(horizontal = padding)
                                        .padding(top = if (index == 0) padding else 0.dp)

                                NavigationSuiteType.NavigationRail ->
                                    Modifier.padding(top = padding)
                                else -> Modifier
                            },
                        selected = entry.isSelected(stackEntry),
                        onClick = { entry.navigate(navController, stackEntry) },
                        label = { Text(entry.label) },
                        icon = { entry.icon() },
                    )
                }
            },
        ) {
            NavContent(navController, modifier = Modifier.clipToBounds())
        }
    }
}

@Composable
private fun NavContent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        modifier = modifier,
        startDestination = Main,
        enterTransition = { slideIn { IntOffset(it.width / 2, 0) } + fadeIn() },
        exitTransition = { slideOut { IntOffset(-it.width / 2, 0) } + fadeOut() },
        popEnterTransition = { slideIn { IntOffset(-it.width / 2, 0) } + fadeIn() },
        popExitTransition = { slideOut { IntOffset(it.width / 2, 0) } + fadeOut() },
    ) {
        navigation<Main>(startDestination = Home) {
            composable<Home> { HomeScreen() }
            navigation<Endpoints>(startDestination = Endpoints.List) {
                composable<Endpoints.List> { EndpointList() }
                navigation<Endpoints.Users>(startDestination = Endpoints.Users.List) {
                    composable<Endpoints.Users.List> { Users() }
                    composable<Endpoints.Users.UserByID> {
                        UserByID(it.toRoute<Endpoints.Users.UserByID>().userID)
                    }
                }
                navigation<Endpoints.Resources>(startDestination = Endpoints.Resources.List) {
                    composable<Endpoints.Resources.List> { Resources() }
                    composable<Endpoints.Resources.ResourceByID> {
                        ResourceByID(it.toRoute<Endpoints.Resources.ResourceByID>().resourceID)
                    }
                }
            }
            composable<Settings> { SettingsScreen() }
        }
    }
}

private enum class NavEntry(val route: Any, val label: String, val iconData: ImageVector) {
    NavHome(Home, "Home", Icons.Rounded.Home),
    NavEndpoints(Endpoints, "Endpoints", Icons.AutoMirrored.Rounded.List),
    NavSettings(Settings, "Settings", Icons.Rounded.Settings);

    fun isSelected(stackEntry: NavBackStackEntry?) =
        stackEntry?.destination?.hierarchy.orEmpty().let { hierarchy ->
            hierarchy
                .elementAtOrNull(hierarchy.count() - 3)
                ?.route
                ?.removePrefix("navigation.routes.") == route.toString()
        }

    fun navigate(navController: NavController, stackEntry: NavBackStackEntry?) {
        if (!isSelected(stackEntry)) {
            navController.navigate(route) { popUpTo(Main) }
        }
    }

    @Composable fun icon() = Icon(iconData, contentDescription = label)
}
