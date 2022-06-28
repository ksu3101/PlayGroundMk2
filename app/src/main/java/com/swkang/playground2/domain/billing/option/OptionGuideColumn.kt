package com.swkang.playground2.domain.billing.option

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author burkdog
 */
@Composable
fun OptionGuideColumn(
    isLandScape: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    val innerPadding = 16.dp
    val landScapeSidePadding = 24.dp
    val bgCornerSize = 8.dp
    val roundedCornerShape = RoundedCornerShape(
        topStart = bgCornerSize,
        topEnd = bgCornerSize
    )
    Column(
        modifier = if (isLandScape) {
            Modifier.requiredWidth(500.dp)
        } else {
            Modifier.fillMaxWidth()
        }
            .wrapContentSize(Alignment.BottomCenter)
            .clip(roundedCornerShape)
            .background(Color.White)
            .padding(
                start = if (isLandScape) landScapeSidePadding else innerPadding,
                top = innerPadding,
                end = if (isLandScape) landScapeSidePadding else innerPadding,
                bottom = innerPadding
            ),
        content = content
    )
}
