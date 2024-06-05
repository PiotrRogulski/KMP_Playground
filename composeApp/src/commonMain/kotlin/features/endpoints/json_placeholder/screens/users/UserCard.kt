package features.endpoints.json_placeholder.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import features.endpoints.json_placeholder.models.*

@Composable
fun UserCard(
    user: User,
    onCallPressed: (String) -> Unit,
    onWebsitePressed: (String) -> Unit,
    onAddressPressed: (Address) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    user.name,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    user.email,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row {
                IconButton(onClick = { onCallPressed(user.phone) }) {
                    Icon(Icons.Rounded.Call, contentDescription = "Call")
                }
                IconButton(onClick = { onWebsitePressed(user.website) }) {
                    Icon(Icons.Rounded.AccountCircle, contentDescription = "Website")
                }
                IconButton(onClick = { onAddressPressed(user.address) }) {
                    Icon(Icons.Rounded.Place, contentDescription = "Address")
                }
            }
        }
    }
}
