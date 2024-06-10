package common

import androidx.compose.runtime.*
import org.jetbrains.compose.resources.*

val StringResource.str
    @Composable
    get() = stringResource(this)
