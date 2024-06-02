package features.endpoints

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

@Composable
fun EndpointList() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Endpoints") })
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Endpoints Screen")
        }
    }
}
