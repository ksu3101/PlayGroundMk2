package com.swkang.playground2.domain.billing.option

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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

data class PaymentMethod(
    val title: String,
    @DrawableRes val icResId: Int,
    val payMethodList: List<Int>
)

/**
 * @author burkdog
 */
@Composable
fun SelectPaymentMethod(
    onBackgroundDimClicked: () -> Unit,
    onMoreInfosClicked: () -> Unit,
    onSelectThridParty: () -> Unit,
    onSelectGoogeInAppPurchase: () -> Unit
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
                    onBackgroundDimClicked()
                },
            color = Color.Black.copy(alpha = 0.6f) // dimm bg
        ) {
            SelectPaymentMethodColumn(
                isLandScape = isLandScape,
                onMoreInfosClicked = onMoreInfosClicked,
                onSelectThridParty = onSelectThridParty,
                onSelectGoogeInAppPurchase = onSelectGoogeInAppPurchase
            )
        }
    }
}

@Composable
private fun SelectPaymentMethodColumn(
    isLandScape: Boolean,
    onMoreInfosClicked: () -> Unit,
    onSelectThridParty: () -> Unit,
    onSelectGoogeInAppPurchase: () -> Unit
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
                .clickable { onMoreInfosClicked() }
                .padding(bottom = 30.dp)
        )
        ThirdPaymentMethodColumn(
            onSelectThridParty,
            onSelectGoogeInAppPurchase
        )
    }
}

@Composable
private fun ThirdPaymentMethodColumn(
    onSelectThridParty: () -> Unit,
    onSelectGoogeInAppPurchase: () -> Unit
) {
    Column {
        PaymentMethodContainer(
            PaymentMethod(
                stringResource(id = R.string.app_name),
                R.drawable.ic_launcher_background,
                listOf()
            ),
            onSelectThridParty
        )
        Spacer(modifier = Modifier.size(12.dp))
        PaymentMethodContainer(
            PaymentMethod(
                stringResource(id = R.string.payment_sel_guide_google_play),
                R.drawable.ic_launcher_foreground,
                listOf()
            ),
            onSelectGoogeInAppPurchase
        )
    }
}

/**
 * https://developer.android.com/google/play/billing/billing-choice?hl=ko
 */
@Composable
private fun PaymentMethodContainer(
    data: PaymentMethod,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val roundedCornerShape = RoundedCornerShape(4.dp)
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
                contentDescription = "",
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
                        .height(60.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSelectPaymentMethod() {
    SelectPaymentMethod(
        {}, {}, {}, {}
    )
}
