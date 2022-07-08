package com.swkang.playground2.domain.billing.option

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.playground2.R
import com.swkang.playground2.base.exts.isLandscape
import com.swkang.playground2.theme.GoogleBillingContainerBorderGray
import com.swkang.playground2.theme.GoogleBillingSubGray
import com.swkang.playground2.theme.PlayGroundTheme

/**
 * 결제 방법 데이터
 */
data class PaymentMethod(
    /**
     * 타이틀 문구
     */
    val title: String,
    /**
     * 좌측 아이콘 이미지의 리소스 아이디
     * 1. 앱 아이콘 -> 3자 결제
     * 2. 구글 플레이 아이콘 -> 구글에서 제공하는 결제 방식
     */
    @DrawableRes val icResId: Int,
    /**
     * 결제 방법 아이콘들
     * (예, 카카오, 각 은행, 토스 등)
     */
    val payMethodList: List<Int>
)

/**
 * 클릭 이벤트 - 3자 결제 방신 선택
 */
object SelectThirdPartyMethod : OnPaymentMethodClicked()

/**
 * 클릭 이벤트 - 구글 인앱 결제 선택
 */
object SelectGoogleInAppPurchase : OnPaymentMethodClicked()

/**
 * 결제 방식 선택 레이아웃
 * @author burkdog
 */
@Composable
fun SelectPaymentMethod(
    onClicked: (clicked: OnPaymentMethodClicked) -> Unit
) {
    val isLandScape = LocalConfiguration.current.isLandscape()
    val interactionSource = remember { MutableInteractionSource() }
    PlayGroundTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(0.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null // disable Ripple effect.
                ) {
                    onClicked(OnPaymentMethodClicked.BackgroundDimm)
                },
            color = Color.Black.copy(alpha = 0.6f) // dimm bg
        ) {
            SelectPaymentMethodColumn(
                isLandScape = isLandScape,
                onClicked = onClicked
            )
        }
    }
}

@Composable
private fun SelectPaymentMethodColumn(
    isLandScape: Boolean,
    onClicked: (clicked: OnPaymentMethodClicked) -> Unit
) {
    OptionGuideColumn(isLandScape) {
        GuideTitleText(stringResource(id = R.string.payment_opt_guide_title))
        GuideSubText(
            stringResource(id = R.string.payment_sel_guide_sub),
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = stringResource(id = R.string.payment_sel_guide_moreinfos),
            fontSize = 14.sp,
            color = GoogleBillingSubGray,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .clickable { onClicked(OnPaymentMethodClicked.NeedMoreInfos) }
                .padding(bottom = 30.dp)
        )
        ThirdPaymentMethodColumn(onClicked)
    }
}

@Composable
private fun ThirdPaymentMethodColumn(
    onClicked: (clicked: OnPaymentMethodClicked) -> Unit
) {
    Column {
        PaymentMethodContainer(
            data = PaymentMethod(
                stringResource(id = R.string.app_name),
                R.drawable.ic_launcher_background,
                listOf(
                    // for test color resources.
                    R.color.purple_200,
                    R.color.teal_200,
                    R.color.purple_700,
                    R.color.primaryColor,
                    R.color.payment_opt_guide_title
                )
            ),
            onClicked = { onClicked(SelectThirdPartyMethod) }
        )
        Spacer(modifier = Modifier.size(12.dp))
        PaymentMethodContainer(
            data = PaymentMethod(
                stringResource(id = R.string.payment_sel_guide_google_play),
                R.drawable.ic_launcher_foreground,
                listOf(
                    // for test color resources.
                    R.color.purple_200,
                    R.color.teal_200,
                    R.color.purple_700,
                    R.color.primaryColor,
                    R.color.payment_opt_guide_title
                )
            ),
            onClicked = { onClicked(SelectGoogleInAppPurchase) }
        )
    }
}

/**
 * https://developer.android.com/google/play/billing/billing-choice?hl=ko
 */
@Composable
private fun PaymentMethodContainer(
    modifier: Modifier = Modifier,
    data: PaymentMethod,
    onClicked: () -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(4.dp)
    val paymentMethodRoundedCornerShape = RoundedCornerShape(1.dp)
    Column(
        modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomCenter)
            .clip(roundedCornerShape)
            .background(Color.White)
            .border(
                1.dp,
                GoogleBillingContainerBorderGray,
                shape = roundedCornerShape
            )
            .clickable { onClicked() }
            .padding(1.dp),
    ) {
        Row(
            modifier.fillMaxWidth()
                .padding(18.dp)
        ) {
            Image(
                painterResource(id = data.icResId),
                contentDescription = "left_icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(24.dp)
            )
            Column(
                Modifier.fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = data.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 18.dp
                        )
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyRow(
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        items(
                            items = data.payMethodList,
                            itemContent = { item ->
                                Box(
                                    modifier = Modifier.size(
                                        width = 30.dp,
                                        height = 20.dp
                                    ).clip(paymentMethodRoundedCornerShape)
                                        .background(colorResource(id = item))
                                        .border(
                                            1.dp,
                                            GoogleBillingContainerBorderGray,
                                            shape = paymentMethodRoundedCornerShape
                                        )
                                )
                                Spacer(
                                    modifier = Modifier.size(
                                        width = 6.dp,
                                        height = 12.dp
                                    )
                                )
                            }
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.payment_sel_guide_another_pay_methods),
                        color = Color(R.color.payment_opt_guide_title),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSelectPaymentMethod() {
    SelectPaymentMethod {
    }
}
