package features.endpoints.models

import kotlinx.serialization.*

@Serializable
data class PaginatedResponse<T>(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<T>,
)

@Serializable data class SingleResponse<T>(val data: T)
