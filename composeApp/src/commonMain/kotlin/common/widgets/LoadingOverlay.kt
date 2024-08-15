package common.widgets

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.*

@Composable
fun LoadingOverlay(loading: Boolean) {
    if (loading) {
        Dialog(onDismissRequest = {}) { CircularProgressIndicator() }
    }
}
