package features.endpoints.models

import kotlinx.serialization.*

@Serializable
data class Resource(
    val id: Int,
    val name: String,
    val year: Int,
    val color: String,
    val pantoneValue: String,
)
