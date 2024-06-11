package features.endpoints.models

import kotlinx.serialization.*

@Serializable
data class PaginatedResponse<T>(
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    val data: List<T>,
)
