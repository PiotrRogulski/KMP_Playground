package features.endpoints

import androidx.compose.runtime.*
import features.endpoints.models.*

class PaginatedQueryController<T>(
    private val perPage: Int,
    private val callback: suspend (page: Int, perPage: Int) -> PaginatedResponse<T>,
) {
    private val _data = mutableStateOf(emptyList<T>())
    private val _page = mutableStateOf<Int?>(null)
    private val _total = mutableStateOf<Int?>(null)
    private val _totalPages = mutableStateOf<Int?>(null)
    private val _loading = mutableStateOf(false)
    private val _error = mutableStateOf<Throwable?>(null)

    val state = derivedStateOf {
        PaginatedQueryControllerState(
            data = _data.value,
            page = _page.value,
            total = _total.value,
            totalPages = _totalPages.value,
            loading = _loading.value,
            error = _error.value,
        )
    }

    suspend fun loadNextPage() {
        if (!state.value.hasMore || _loading.value) return

        _loading.value = true
        val pageToLoad = (_page.value ?: 0) + 1

        try {
            val data = callback(pageToLoad, perPage)
            _data.value += data.data
            _page.value = pageToLoad
            _total.value = data.total
            _totalPages.value = data.totalPages
        } catch (e: Throwable) {
            _error.value = e
            return
        } finally {
            _loading.value = false
        }
    }
}

data class PaginatedQueryControllerState<T>(
    val data: List<T>,
    val page: Int?,
    val total: Int?,
    val totalPages: Int?,
    val loading: Boolean,
    val error: Throwable?,
) {
    val hasMore = page == null || totalPages == null || page < totalPages

    operator fun component7() = hasMore
}
