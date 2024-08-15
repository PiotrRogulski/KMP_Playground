package features.endpoints

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import common.widgets.*
import navigation.*
import navigation.routes.*

@Composable
fun EndpointList() {
    val navController = LocalNavController.current

    AppScaffold(title = "Endpoints") {
        item { EndpointCard("Users") { navController.navigate(Endpoints.Users) } }
        item { Spacer(Modifier.height(16.dp)) }
        item {
            EndpointCardWithId(cardLabel = "User by ID", fieldLabel = "User ID") {
                navController.navigate(Endpoints.Users.UserByID(it))
            }
        }
        item { Spacer(Modifier.height(16.dp)) }
        item { EndpointCard("Resources") { navController.navigate(Endpoints.Resources) } }
        item { Spacer(Modifier.height(16.dp)) }
        item {
            EndpointCardWithId(cardLabel = "Resource by ID", fieldLabel = "Resource ID") {
                navController.navigate(Endpoints.Resources.ResourceByID(it))
            }
        }
    }
}

@Composable
private fun EndpointCard(
    label: String,
    trailing: @Composable () -> Unit = {},
    onClick: () -> Unit
) {
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

@Composable
private fun EndpointCardWithId(cardLabel: String, fieldLabel: String, onClick: (Int) -> Unit) {
    var id by remember { mutableStateOf("") }

    EndpointCard(
        cardLabel,
        trailing = {
            TextField(
                modifier = Modifier.width(150.dp),
                value = id,
                onValueChange = { id = it },
                placeholder = { Text(fieldLabel) },
                singleLine = true,
            )
        },
        onClick = { id.toIntOrNull()?.also(onClick) },
    )
}
