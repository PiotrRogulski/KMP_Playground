package features.endpoints

import androidx.compose.runtime.*
import features.endpoints.models.*

class SingleQueryController<T : Any>(
    private val callback: suspend () -> SingleResponse<T>,
) {
    private val _data = mutableStateOf<T?>(null)
    private val _loading = mutableStateOf(false)
    private val _error = mutableStateOf<Throwable?>(null)

    val state = derivedStateOf {
        SingleQueryControllerState(
            data = _data.value,
            loading = _loading.value,
            error = _error.value,
        )
    }

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

data class SingleQueryControllerState<T>(
    val data: T?,
    val loading: Boolean,
    val error: Throwable?,
)
