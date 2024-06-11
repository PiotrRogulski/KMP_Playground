package features.endpoints

import androidx.compose.runtime.*
import features.endpoints.models.*

class SingleQueryController<T : Any>(
    private val callback: suspend () -> SingleResponse<T>,
) {
    private val _data = mutableStateOf<T?>(null)
    val data: State<T?> = _data

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf<Throwable?>(null)
    val error: State<Throwable?> = _error

    suspend fun load() {
        if (_loading.value) return

        _loading.value = true

        try {
            val data = callback()
            _data.value = data.data
        } catch (e: Throwable) {
            _error.value = e
            return
        } finally {
            _loading.value = false
        }
    }
}
