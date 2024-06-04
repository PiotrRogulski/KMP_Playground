package common

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.*

sealed class UiState<T> {
    class Initial<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val error: Any? = null) : UiState<T>()
}

@Composable
fun <T> collectUiState(flow: () -> Flow<UiState<T>>): UiState<T> {
    val state by remember { flow() }.collectAsState(UiState.Initial())
    return state
}
