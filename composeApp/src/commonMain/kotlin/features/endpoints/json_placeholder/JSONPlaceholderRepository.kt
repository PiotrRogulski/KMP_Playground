package features.endpoints.json_placeholder

import common.*
import features.endpoints.*
import kotlinx.coroutines.flow.*

class JSONPlaceholderRepository {
    private val api = JSONPlaceholderApi()

    private fun <T> createFlow(callback: suspend () -> T) = flow {
        emit(UiState.Loading())
        try {
            val data = callback()
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    val users
        get() = createFlow { api.getUsers() }
}
