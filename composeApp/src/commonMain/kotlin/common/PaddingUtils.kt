package common

import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*

operator fun PaddingValues.plus(that: PaddingValues) =
    object : PaddingValues {
        override fun calculateBottomPadding() =
            this@plus.calculateBottomPadding() + that.calculateBottomPadding()

        override fun calculateLeftPadding(layoutDirection: LayoutDirection) =
            this@plus.calculateLeftPadding(layoutDirection) +
                that.calculateLeftPadding(layoutDirection)

        override fun calculateRightPadding(layoutDirection: LayoutDirection) =
            this@plus.calculateRightPadding(layoutDirection) +
                that.calculateRightPadding(layoutDirection)

        override fun calculateTopPadding() =
            this@plus.calculateTopPadding() + that.calculateTopPadding()
    }
