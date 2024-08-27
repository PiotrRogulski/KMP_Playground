package features.endpoints

import androidx.compose.runtime.*
import features.endpoints.models.*

class SingleQueryController<T : Any>(private val callback: suspend () -> SingleResponse<T>) {
    private val _data = mutableStateOf<T?>(null)
    private val _loading = mutableStateOf(false)
    private val _error = mutableStateOf<Throwable?>(null)

    val data: State<T?> = _data
    val loading: State<Boolean> = _loading
    val error: State<Throwable?> = _error

    suspend fun load() {
        if (_loading.value) return

        _loading.value = true

        try {
            val response = callback()
            _data.value = response.data
        } catch (e: Throwable) {
            _error.value = e
        } finally {
            _loading.value = false
        }
    }
}
