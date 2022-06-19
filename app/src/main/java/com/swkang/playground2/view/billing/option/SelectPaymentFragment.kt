package com.swkang.playground2.view.billing.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.swkang.playground2.base.BaseFragment
import com.swkang.playground2.theme.PlayGroundTheme

/**
 * @author burkdog
 * @see https://developer.android.com/google/play/billing/billing-choice?hl=ko
 */
class SelectPaymentFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        setContent {
            PlayGroundTheme {
                SelectPayment()
            }
        }
    }
}

@Composable
fun SelectPayment() {
}
