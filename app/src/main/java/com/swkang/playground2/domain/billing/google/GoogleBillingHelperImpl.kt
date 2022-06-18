package com.swkang.playground2.domain.billing.google

import android.content.Context
import com.swkang.model.domain.billing.google.GoogleBillingHelper
import dagger.hilt.android.qualifiers.ApplicationContext

class GoogleBillingHelperImpl(
    @ApplicationContext context: Context
): GoogleBillingHelper {
}