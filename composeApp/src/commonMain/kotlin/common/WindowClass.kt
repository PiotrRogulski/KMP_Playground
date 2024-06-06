package common

import androidx.compose.runtime.*
import androidx.compose.ui.unit.*

enum class WindowClass(val maxWidth: Dp) {
    Small(600.dp),
    Medium(840.dp),
    Large(Float.POSITIVE_INFINITY.dp);

    companion object {
        fun fromWidth(width: Dp) = entries.first { width <= it.maxWidth }
    }
}

val LocalWindowClass = compositionLocalOf<WindowClass> { error("No WindowClass provided") }
