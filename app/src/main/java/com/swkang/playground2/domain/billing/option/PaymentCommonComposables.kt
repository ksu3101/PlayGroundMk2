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

/**
 * 결제 옵션 변경 및 선택 화면들 공통 클릭 이벤트
 */
sealed class OnPaymentMethodClicked {
    /**
     * 배경 딤 선택 -> 현재 상태 취소
     */
    object BackgroundDimm : OnPaymentMethodClicked()

    /**
     * 더 많은 정보 보기 -> 외부 브라우저를 통해 아래 url이동
     * - https://support.google.com/googleplay/answer/11174377?hl=ko
     */
    object NeedMoreInfos : OnPaymentMethodClicked()
}

/**
 * 결제 옵션 변경 및 선택 화면 공통 뷰
 * - 하단 배경
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

/**
 * 결제 옵션 변경 및 선택 화면 공통 뷰
 * - 상단 타이틀
 * @author burkdog
 */
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

/**
 * 결제 옵션 변경 및 선택 화면 공통 뷰
 * - 타이틀 아래 서브 텍스트
 * @author burkdog
 */
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
