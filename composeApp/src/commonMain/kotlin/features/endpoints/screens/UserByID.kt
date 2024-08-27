package features.endpoints.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import common.widgets.*
import features.endpoints.*
import org.koin.compose.*

@Composable
fun UserByID(userID: Int, api: ExampleApi = koinInject()) {
    val userController = remember { SingleQueryController { api.getUser(userID) } }

    val snackHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) { userController.load() }

    val data by userController.data
    val loading by userController.loading
    val error by userController.error

    LoadingOverlay(loading)

    LaunchedEffect(error) {
        if (error != null) {
            snackHostState.showSnackbar(
                "An error occurred: $error",
                duration = SnackbarDuration.Short,
            )
        }
    }

    AppScaffold(title = "User by ID ($userID)", snackbarHost = { SnackbarHost(snackHostState) }) {
        data?.let {
            item {
                Text(
                    "${it.firstName} ${it.lastName}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
