package features.endpoints.screens.users

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import common.widgets.*
import features.endpoints.*
import kotlinx.coroutines.*
import navigation.*
import org.koin.compose.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsersScreen(api: ExampleApi = koinInject()) {
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    val usersController =
        remember { PaginatedQueryController(perPage = 5, callback = api::getUsers) }

    LaunchedEffect(Unit) {
        usersController.loadNextPage()
    }

    val page by usersController.page
    val total by usersController.total
    val totalPages by usersController.totalPages

    val loading by usersController.loading
    val hasMore by usersController.hasMore
    val error by usersController.error

    val users by usersController.data

    val snackHostState = remember { SnackbarHostState() }

    if (loading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }

    AppScaffold(
        title = "Users",
        snackbarHost = { SnackbarHost(hostState = snackHostState) },
    ) {
        stickyHeader {
            Surface(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Page ${page ?: "—"} of ${totalPages ?: "—"} (${users.size} of ${total ?: "—"} total users)")
                    error?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "An error occurred: $it",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
        itemsIndexed(users) { index, user ->
            Box(modifier = Modifier.padding(top = if (index == 0) 0.dp else 16.dp)) {
                UserCard(
                    user,
                    onClick = { navController.navigate(Route.Endpoints.UserByID.createRoute(user.id)) },
                )
            }
        }
        if (hasMore) {
            item {
                Button(
                    onClick = { scope.launch { usersController.loadNextPage() } },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                ) {
                    Text("Load more")
                }
            }
        }
    }
}
