package features.endpoints

import common.*
import kotlinx.coroutines.flow.*

class ExampleRepository(private val api: ExampleApi) {
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
