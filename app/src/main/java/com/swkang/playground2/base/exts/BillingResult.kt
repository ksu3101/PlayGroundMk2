package com.swkang.playground2.base.exts

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult

fun BillingResult.isOk(): Boolean =
    responseCode == BillingClient.BillingResponseCode.OK

fun BillingResult.isError(): Boolean =
    responseCode == BillingClient.BillingResponseCode.ERROR || responseCode == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED
