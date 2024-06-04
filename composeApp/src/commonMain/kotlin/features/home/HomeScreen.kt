package features.home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import common.widgets.*

@Composable
fun HomeScreen() {
    AppScaffold(title = "Home") {
        item {
            Text("Home Screen")
        }
    }
}
