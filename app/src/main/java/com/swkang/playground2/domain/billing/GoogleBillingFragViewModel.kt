package com.swkang.playground2.domain.billing

import com.android.billingclient.api.Purchase
import com.swkang.model.base.BaseViewModel
import com.swkang.model.base.helper.ResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author burkdog
 */
@HiltViewModel
class GoogleBillingFragViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper
) : BaseViewModel() {

    private fun handle(state: Purchase.PurchaseState) {
    }
}
