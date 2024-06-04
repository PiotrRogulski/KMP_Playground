package features.endpoints

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import common.widgets.*
import navigation.*

@Composable
fun EndpointList() {
    val navController = LocalNavController.current

    AppScaffold(title = "Endpoints") {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            for ((name, route) in endpoints) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(route.path) },
                ) {
                    Text(
                        name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

data class Endpoint(val name: String, val route: Route)

val endpoints = listOf(
    Endpoint("Users", Route.Endpoints.Users),
)
