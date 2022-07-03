package com.swkang.playground2.domain.billing.option

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.playground2.R
import com.swkang.playground2.theme.GoogleBillingSubGray

sealed class OnPaymentMethodClicked {
    object BackgroundDimm : OnPaymentMethodClicked()
    object NeedMoreInfos : OnPaymentMethodClicked()
}

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

@Composable
fun GuideTitleText(text: String) {
    Text(
        text = text,
        color = Color(R.color.payment_opt_guide_title),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 18.dp,
                bottom = 20.dp
            )
    )
}

@Composable
fun GuideSubText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = GoogleBillingSubGray,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        modifier = modifier
            .fillMaxWidth()
    )
}
