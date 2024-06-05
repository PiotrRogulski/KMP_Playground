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
import org.koin.compose.*

@Composable
fun Users(repository: JSONPlaceholderRepository = koinInject()) {
    val users = collectUiState { repository.users }

    AppScaffold(title = "Users") {
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
                        UserCard(user)
                    }
                }

            }
        }
    }
}
