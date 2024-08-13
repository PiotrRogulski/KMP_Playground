package navigation.routes

import androidx.navigation.*
import androidx.navigation.compose.*
import features.endpoints.*
import features.endpoints.screens.*
import features.home.*
import features.settings.*
import kotlinx.serialization.*

@Serializable
data object Main {
    infix fun navIn(builder: NavGraphBuilder) = builder.nav<Main>(Home)
}

@Serializable
data object Home {
    infix fun routeIn(builder: NavGraphBuilder) = builder.composable<Home> { HomeScreen() }
}

@Serializable
data object Endpoints {
    infix fun navIn(builder: NavGraphBuilder) = builder.nav<Endpoints>(List)

    @Serializable
    data object List {
        infix fun routeIn(builder: NavGraphBuilder) = builder.composable<List> { EndpointList() }
    }

    @Serializable
    data object Users {
        infix fun navIn(builder: NavGraphBuilder) = builder.nav<Users>(List)

        @Serializable
        data object List {
            infix fun routeIn(builder: NavGraphBuilder) = builder.composable<List> { Users() }
        }

        @Serializable
        data class UserByID(val userID: Int) {
            companion object {
                infix fun routeIn(builder: NavGraphBuilder) = builder.composable<UserByID> {
                    features.endpoints.screens.UserByID(it.toRoute<UserByID>().userID)
                }
            }
        }
    }

    @Serializable
    data object Resources {
        infix fun navIn(builder: NavGraphBuilder) = builder.nav<Resources>(List)

        @Serializable
        data object List {
            infix fun routeIn(builder: NavGraphBuilder) = builder.composable<List> { Resources() }
        }

        @Serializable
        data class ResourceByID(val resourceID: Int) {
            companion object {
                infix fun routeIn(builder: NavGraphBuilder) = builder.composable<ResourceByID> {
                    features.endpoints.screens.ResourceByID(it.toRoute<ResourceByID>().resourceID)
                }
            }
        }
    }
}

@Serializable
data object Settings {
    infix fun routeIn(builder: NavGraphBuilder) = builder.composable<Settings> { SettingsScreen() }
}

private inline fun <reified T : Any> NavGraphBuilder.nav(startDestination: Any) =
    { builder: NavGraphBuilder.() -> Unit -> navigation<T>(startDestination) { builder() } }
