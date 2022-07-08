package com.swkang.playground2.domain.billing.option

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.playground2.R
import com.swkang.playground2.base.exts.isLandscape
import com.swkang.playground2.theme.GoogleBillingMainGreen
import com.swkang.playground2.theme.PlayGroundTheme

/**
 * 클릭 이벤트 - 결제 옵션 변경 화면 / "계속" -> 결제 방법 선택 화면 이동
 */
object NextSelectPaymentMethods : OnPaymentMethodClicked()

/**
 * 결제 옵션 변경 가이드 화면
 * - https://developer.android.com/google/play/billing/billing-choice?hl=ko
 * @author burkdog
 */
@Composable
fun PaymentOptionGuide(
    onClicked: (clicked: OnPaymentMethodClicked) -> Unit
) {
    val isLandScape = LocalConfiguration.current.isLandscape()
    val interactionSource = remember { MutableInteractionSource() }
    PlayGroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(0.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null // disable Ripple effect.
                ) {
                    onClicked(OnPaymentMethodClicked.BackgroundDimm)
                },
            color = Color.Black.copy(alpha = 0.6f) // dimm bg
        ) {
            PaymentOptionGuideColumn(
                isLandScape,
                onClicked
            )
        }
    }
}

@Composable
private fun PaymentOptionGuideColumn(
    isLandScape: Boolean,
    onClicked: (clicked: OnPaymentMethodClicked) -> Unit
) {
    OptionGuideColumn(isLandScape) {
        GuideTitleText(stringResource(id = R.string.payment_opt_guide_title))
        GuideSubText(
            stringResource(id = R.string.payment_opt_guide_sub),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        DottedGuideText(stringResource(id = R.string.payment_opt_guide_list_sub1))
        DottedGuideText(stringResource(id = R.string.payment_opt_guide_list_sub2))
        DottedGuideText(stringResource(id = R.string.payment_opt_guide_list_sub3))
        DottedGuideText(stringResource(id = R.string.payment_opt_guide_list_sub4))
        Text(
            text = stringResource(id = R.string.payment_opt_guide_footer),
            color = Color(R.color.payment_opt_guide_sub),
            fontSize = 12.sp,
            lineHeight = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
        ) {
            OutlinedButton(
                onClick = { onClicked(OnPaymentMethodClicked.NeedMoreInfos) },
                border = BorderStroke(1.dp, Color(R.color.payment_opt_line)),
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(R.color.payment_opt_line)),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .padding(end = 6.dp)
            ) {
                Text(
                    color = GoogleBillingMainGreen,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.c_moreinfos),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Button(
                onClick = { onClicked(NextSelectPaymentMethods) },
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.textButtonColors(GoogleBillingMainGreen),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .padding(start = 6.dp)
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.c_continue),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun DottedGuideText(text: String) {
    val circleShape = CircleShape
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 2.dp, start = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(3.dp)
                .clip(circleShape)
                .background(Color(R.color.payment_opt_guide_sub))
        )
        Text(
            text = text,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            color = Color(R.color.payment_opt_guide_sub),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp)
        )
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFF // 0x80FFFFFF
)
@Composable
fun PreviewPaymentOptionGuide() {
    PaymentOptionGuide {
    }
}

@Preview(
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360,
    showBackground = true
)
@Composable
fun PreviewLandscapePaymentOptionGuide() {
    PaymentOptionGuide {
    }
}
