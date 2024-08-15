package features.endpoints.models

import kotlinx.serialization.*

@Serializable
data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
)
