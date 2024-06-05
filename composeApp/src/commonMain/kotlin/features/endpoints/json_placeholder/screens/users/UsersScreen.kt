package features.endpoints.json_placeholder.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import common.*
import common.widgets.*
import features.endpoints.json_placeholder.*
import kotlinx.coroutines.*
import org.koin.compose.*

@Composable
fun UsersScreen(repository: JSONPlaceholderRepository = koinInject()) {
    val users = collectUiState { repository.users }

    val snackHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun showSnackbar(message: String) {
        scope.launch {
            snackHostState.currentSnackbarData?.dismiss()
            snackHostState.showSnackbar(message, withDismissAction = true)
        }
    }

    AppScaffold(
        title = "Users",
        snackbarHost = { SnackbarHost(hostState = snackHostState) },
    ) {
        when (users) {
            is UiState.Initial -> {}
            is UiState.Loading -> item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> item { Text(users.error.toString()) }
            is UiState.Success -> {
                itemsIndexed(users.data) { index, user ->
                    Box(modifier = Modifier.padding(top = if (index == 0) 0.dp else 16.dp)) {
                        UserCard(
                            user,
                            onCallPressed = { showSnackbar("Calling $it") },
                            onWebsitePressed = { showSnackbar("Opening $it") },
                            onAddressPressed = { showSnackbar("Navigating to $it") },
                        )
                    }
                }

            }
        }
    }
}
