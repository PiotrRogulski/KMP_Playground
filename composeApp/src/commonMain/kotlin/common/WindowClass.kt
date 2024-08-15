package common

import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.*

val LocalWindowSizeClass =
    compositionLocalOf<WindowSizeClass> { error("No WindowSizeClass provided") }
