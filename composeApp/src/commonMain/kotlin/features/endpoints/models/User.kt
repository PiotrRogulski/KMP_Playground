package features.endpoints.models

import kotlinx.serialization.*

@Serializable
data class User(
    val id: Int,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val avatar: String,
)
