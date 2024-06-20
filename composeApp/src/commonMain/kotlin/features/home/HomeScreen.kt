package features.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import common.widgets.*

@Composable
fun HomeScreen() {
    AppScaffold(title = "Home") {
        item {
            Text("Home Screen")
        }
        item {
            val angleX by rememberInfiniteTransition().animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(4000, easing = LinearEasing),
                ),
            )
            val angleY by rememberInfiniteTransition().animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(5300, easing = LinearEasing),
                ),
            )
            Box(Modifier.padding(64.dp).size(250.dp).graphicsLayer {
                rotationX = angleX
                rotationY = angleY
            }) {
                Box(Modifier.background(Color.Green).fillMaxSize())
                Text(
                    "Box with graphicsLayer",
                    Modifier.align(Alignment.Center),
                    Color.Black,
                )
            }
        }
    }
}
