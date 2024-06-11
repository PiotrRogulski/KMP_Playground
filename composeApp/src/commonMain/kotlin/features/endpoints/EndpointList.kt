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
        item {
            EndpointCard("Users") { navController.navigate(Route.Endpoints.Users.path) }
        }
        item { Spacer(Modifier.height(16.dp)) }
        item {
            var userId by remember { mutableStateOf("") }

            EndpointCard(
                "User by ID",
                trailing = {
                    TextField(
                        modifier = Modifier.width(150.dp),
                        value = userId,
                        onValueChange = { userId = it },
                        placeholder = { Text("User ID") },
                        singleLine = true,
                    )
                },
            ) {
                if (userId.isNotEmpty()) {
                    val parsedId = userId.toIntOrNull()
                    if (parsedId != null) {
                        navController.navigate(Route.Endpoints.UserByID.createRoute(parsedId))
                    }
                }
            }
        }
    }
}

@Composable
private fun EndpointCard(label: String, trailing: @Composable () -> Unit = {}, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            trailing()
        }
    }
}
