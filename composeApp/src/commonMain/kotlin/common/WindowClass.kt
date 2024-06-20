package common

import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*

val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> { error("No WindowSizeClass provided") }
