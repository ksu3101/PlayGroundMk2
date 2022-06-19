package com.swkang.playground2.view.billing.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.playground2.R
import com.swkang.playground2.base.BaseFragment
import com.swkang.playground2.base.exts.isLandscape
import com.swkang.playground2.theme.PlayGroundTheme

/**
 * @author burkdog
 * @see https://developer.android.com/google/play/billing/billing-choice?hl=ko
 */
class PaymentOptionGuideFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        setContent {
            PlayGroundTheme {
                PaymentOptionGuide(
                    onMoreInfomationsBtnClicked = { },
                    onContinueBtnClicked = { }
                )
            }
        }
    }
}

@Composable
fun PaymentOptionGuide(
    onMoreInfomationsBtnClicked: () -> Unit,
    onContinueBtnClicked: () -> Unit
) {
    val isLandScape = LocalConfiguration.current.isLandscape()
    val innerPadding = 16.dp
    val landScapeSidePadding = 24.dp
    Surface(
        modifier = Modifier
            .width(if (isLandScape) 500.dp else 200.dp)
            .background(Color.White)
            .padding(
                start = if (isLandScape) landScapeSidePadding else innerPadding,
                top = innerPadding,
                end = if (isLandScape) landScapeSidePadding else innerPadding,
                bottom = innerPadding
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.payment_opt_guide_title),
                color = Color(R.color.payment_opt_guide_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Row {
                Text(
                    text = stringResource(id = R.string.c_moreinfos),
                    modifier = Modifier
                        .clickable { onMoreInfomationsBtnClicked() }
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(Color.Black),
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.c_continue),
                    modifier = Modifier
                        .clickable { onContinueBtnClicked() }
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(Color.Black),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPaymentOptionGuide() {
    PaymentOptionGuide({}, {})
}
