package features.endpoints.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import coil3.compose.AsyncImage
import features.endpoints.models.*

@Composable
fun UserCard(
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
