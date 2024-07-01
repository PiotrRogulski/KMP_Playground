package features.endpoints.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import coil3.compose.*
import common.widgets.*
import features.endpoints.*
import features.endpoints.models.*
import kotlinx.coroutines.*
import navigation.*
import org.koin.compose.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Users(api: ExampleApi = koinInject()) {
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    val usersController = remember { PaginatedQueryController(perPage = 5, callback = api::getUsers) }

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

    if (loading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }

    AppScaffold(title = "Users") {
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
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                ) {
                    Text("Load more")
                }
            }
        }
    }
}

@Composable
private fun UserCard(
    user: User,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() },
        shape = RoundedCornerShape(48.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(32.dp)),
                model = user.avatar,
                contentDescription = "User avatar",
                contentScale = ContentScale.Crop,
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "${user.firstName} ${user.lastName} (#${user.id})",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    user.email,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
