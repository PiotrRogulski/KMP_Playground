package common

import androidx.compose.runtime.*
import androidx.window.core.layout.*

val LocalWindowSizeClass =
    compositionLocalOf<WindowSizeClass> { error("No WindowSizeClass provided") }
