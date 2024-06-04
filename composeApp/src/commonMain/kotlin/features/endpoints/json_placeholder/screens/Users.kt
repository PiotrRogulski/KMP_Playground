package features.endpoints.json_placeholder.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import common.*
import common.widgets.*
import features.endpoints.json_placeholder.*

@Composable
fun Users() {
    val users = collectUiState { JSONPlaceholderRepository().users }

    AppScaffold(title = "Users") {
        when (users) {
            is UiState.Initial -> {}
            is UiState.Loading -> item { CircularProgressIndicator() }
            is UiState.Error -> item { Text(users.error.toString()) }
            is UiState.Success -> {
                itemsIndexed(users.data) { index, user ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = if (index == 0) 0.dp else 16.dp),
                        onClick = {},
                    ) {
                        Text(
                            user.name,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

            }
        }
    }
}
