package features.endpoints

import androidx.compose.runtime.*
import features.endpoints.models.*

class PaginatedQueryController<T>(
    private val perPage: Int,
    private val callback: suspend (page: Int, perPage: Int) -> PaginatedResponse<T>,
) {
    private val _data = mutableStateOf(emptyList<T>())
    val data: State<List<T>> = _data

    private val _page = mutableStateOf<Int?>(null)
    val page: State<Int?> = _page

    private val _total = mutableStateOf<Int?>(null)
    val total: State<Int?> = _total

    private val _totalPages = mutableStateOf<Int?>(null)
    val totalPages: State<Int?> = _totalPages

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf<Throwable?>(null)
    val error: State<Throwable?> = _error

    val hasMore = derivedStateOf {
        val page = _page.value
        val totalPages = _totalPages.value
        if (page == null || totalPages == null) {
            true
        } else {
            page < totalPages
        }
    }

    suspend fun loadNextPage() {
        if (!hasMore.value || _loading.value) return

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
