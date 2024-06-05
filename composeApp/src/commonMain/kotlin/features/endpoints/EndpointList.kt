package features.endpoints

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
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
            UserByID()
        }
    }
}

@Composable
private fun EndpointCard(label: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Text(
            label,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun UserByID() {
    val navController = LocalNavController.current

    var dialogOpen by remember { mutableStateOf(false) }
    var userId by remember { mutableStateOf("") }

    fun goToUser() {
        if (userId.isNotEmpty()) {
            dialogOpen = false
            navController.navigate(Route.Endpoints.UserByID.createRoute(userId))
        }
    }

    if (dialogOpen) {
        Dialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false,
            ),
            onDismissRequest = { goToUser() },
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column {
                    TextField(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        value = userId,
                        onValueChange = { userId = it },
                        label = { Text("User ID") },
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TextButton(
                            modifier = Modifier.padding(8.dp),
                            onClick = { dialogOpen = false },
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            modifier = Modifier.padding(8.dp),
                            onClick = { goToUser() },
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }

    EndpointCard("User by ID") { dialogOpen = true }
}
