package features.endpoints.screens.user_by_id

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import common.widgets.*

@Composable
fun UserByID(userID: Int) {
    AppScaffold(title = "User by ID") {
        item {
            Text("User ID: $userID", modifier = Modifier.padding(16.dp))
        }
    }
}
