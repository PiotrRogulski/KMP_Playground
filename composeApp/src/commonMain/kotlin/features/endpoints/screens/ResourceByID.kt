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
fun ResourceByID(resourceID: Int, api: ExampleApi = koinInject()) {
    val resourceController = remember { SingleQueryController { api.getResource(resourceID) } }

    val snackHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        resourceController.load()
    }

    val state by resourceController.state
    val (data, loading, error) = state

    if (loading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(error) {
        if (error != null) {
            snackHostState.showSnackbar(
                "An error occurred: $error",
                duration = SnackbarDuration.Short,
            )
        }
    }

    AppScaffold(
        title = "Resource by ID ($resourceID)",
        snackbarHost = { SnackbarHost(snackHostState) },
    ) {
        data?.let {
            item {
                Text(
                    "${it.name} from ${it.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
