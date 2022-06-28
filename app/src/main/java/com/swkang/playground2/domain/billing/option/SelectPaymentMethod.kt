package com.swkang.playground2.domain.billing.option

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swkang.playground2.base.exts.isLandscape
import com.swkang.playground2.theme.PlayGroundTheme

/**
 * @author burkdog
 */
@Composable
fun SelectPaymentMethod(
    onBackgroundDimClicked: () -> Unit,
    onSelectThridParty: () -> Unit,
    onSelectGoogeInAppPurchase: () -> Unit
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
                    onBackgroundDimClicked()
                },
            color = Color.Black.copy(alpha = 0.6f) // dimm bg
        ) {
            SelectPaymentMethodColumn(
                isLandScape = isLandScape,
                onSelectThridParty = onSelectThridParty,
                onSelectGoogeInAppPurchase = onSelectGoogeInAppPurchase
            )
        }
    }
}

@Composable
private fun SelectPaymentMethodColumn(
    isLandScape: Boolean,
    onSelectThridParty: () -> Unit,
    onSelectGoogeInAppPurchase: () -> Unit
) {
    OptionGuideColumn(isLandScape) {
        Text("asdf")
    }
}

@Preview
@Composable
fun PreviewSelectPaymentMethod() {
    SelectPaymentMethod(
        {}, {}, {}
    )
}
