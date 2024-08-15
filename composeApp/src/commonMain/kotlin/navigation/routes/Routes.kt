package navigation.routes

import kotlinx.serialization.*

@Serializable data object Main

@Serializable data object Home

@Serializable
data object Endpoints {
    @Serializable data object List

    @Serializable
    data object Users {
        @Serializable data object List

        @Serializable data class UserByID(val userID: Int)
    }

    @Serializable
    data object Resources {
        @Serializable data object List

        @Serializable data class ResourceByID(val resourceID: Int)
    }
}

@Serializable data object Settings
