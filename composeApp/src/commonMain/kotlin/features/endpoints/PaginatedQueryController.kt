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

    val data: State<List<T>> = _data
    val page: State<Int?> = _page
    val total: State<Int?> = _total
    val totalPages: State<Int?> = _totalPages
    val loading: State<Boolean> = _loading
    val error: State<Throwable?> = _error
    val hasMore: State<Boolean> = derivedStateOf {
        _page.value?.let { page -> _totalPages.value?.let { total -> page < total } } ?: false
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
