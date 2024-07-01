package features.endpoints.models

import kotlinx.serialization.*

@Serializable
data class Resource(
    val id: Int,
    val name: String,
    val year: Int,
    val color: String,
    @SerialName("pantone_value") val pantoneValue: String,
)
