package features.endpoints.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import common.widgets.*
import features.endpoints.*
import features.endpoints.models.*
import kotlinx.coroutines.*
import navigation.*
import navigation.routes.*
import org.koin.compose.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Resources(api: ExampleApi = koinInject()) {
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    val resourcesController = remember {
        PaginatedQueryController(perPage = 5, callback = api::getResources)
    }

    LaunchedEffect(Unit) { resourcesController.loadNextPage() }

    val data by resourcesController.data
    val page by resourcesController.page
    val total by resourcesController.total
    val totalPages by resourcesController.totalPages
    val loading by resourcesController.loading
    val error by resourcesController.error
    val hasMore by resourcesController.hasMore

    LoadingOverlay(loading)

    AppScaffold(title = "Resources") {
        stickyHeader {
            Surface(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Page ${page ?: "—"} of ${totalPages ?: "—"} (${data.size} of ${total ?: "—"} total resources)"
                    )
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
        itemsIndexed(data) { index, resource ->
            Box(modifier = Modifier.padding(top = if (index == 0) 0.dp else 16.dp)) {
                ResourceCard(
                    resource,
                    onClick = {
                        navController.navigate(Endpoints.Resources.ResourceByID(resource.id))
                    },
                )
            }
        }
        if (hasMore) {
            item {
                Button(
                    onClick = { scope.launch { resourcesController.loadNextPage() } },
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
private fun ResourceCard(resource: Resource, onClick: () -> Unit) {
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
            val color = Color(resource.color.removePrefix("#").toLong(16) or 0xFF000000)
            Box(Modifier.size(64.dp).clip(RoundedCornerShape(32.dp)).background(color)) {
                Text(
                    resource.id.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge,
                    color =
                        when (color.luminance()) {
                            in 0.0..0.3 -> Color.White
                            else -> Color.Black
                        },
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "${resource.name} from ${resource.year}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(resource.pantoneValue, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
