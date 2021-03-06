package com.swkang.playground2.base.helper

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode.BILLING_UNAVAILABLE
import com.android.billingclient.api.BillingClient.BillingResponseCode.DEVELOPER_ERROR
import com.android.billingclient.api.BillingClient.BillingResponseCode.ERROR
import com.android.billingclient.api.BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED
import com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED
import com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_NOT_OWNED
import com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_UNAVAILABLE
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.android.billingclient.api.BillingClient.BillingResponseCode.SERVICE_DISCONNECTED
import com.android.billingclient.api.BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE
import com.android.billingclient.api.BillingClient.BillingResponseCode.USER_CANCELED
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryPurchasesAsync
import com.swkang.model.domain.billing.google.GoogleBillingHelper
import com.swkang.model.domain.billing.google.GoogleBillingHelper.BillingState
import com.swkang.model.domain.billing.google.GoogleBillingHelper.BillingState.GemProductsReceived
import com.swkang.playground2.PRODUCT_GEM_100
import com.swkang.playground2.PRODUCT_GEM_1000
import com.swkang.playground2.PRODUCT_GEM_200
import com.swkang.playground2.PRODUCT_GEM_50
import com.swkang.playground2.PRODUCT_GEM_500
import com.swkang.playground2.base.exts.isError
import com.swkang.playground2.base.exts.isOk
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class GoogleBillingHelperImpl(
    @ApplicationContext private val context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : GoogleBillingHelper,
    DefaultLifecycleObserver,
    PurchasesUpdatedListener,
    BillingClientStateListener,
    ProductDetailsResponseListener {
    private lateinit var billingClient: BillingClient
    private val _statePublisher = MutableSharedFlow<BillingState>()
    private val consumedPurchases: MutableMap<String, ProductDetails> by lazy { HashMap() }

    override fun getBillingStateListener(): Flow<BillingState> =
        _statePublisher.asSharedFlow()

    override suspend fun queryGemProducts() {
        Log.d(TAG, ">> queryGemProducts()")
        val params = QueryProductDetailsParams.newBuilder()
        val productList = arrayListOf<QueryProductDetailsParams.Product>()
        PRODUCTS.forEach {
            productList.add(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(it)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            )
        }
        params.setProductList(productList)
        billingClient.queryProductDetailsAsync(params.build(), this)
    }

    override fun launchBillingFlow(activity: Activity, productDetails: ProductDetails) {
        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        // .setOfferToken(offerToken) - ????????? ???????????? ????????? ??????.
                        .build()
                )
            )
        val billingResult = billingClient.launchBillingFlow(activity, params.build())
        billingResult.printLog()
        if (billingResult.isError()) {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * `launchBillingFlow()`?????? ??? ??? ??????.
     */
    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        billingResult.printLog()
        if (billingResult.isOk()) {
            // ?????? ?????? -> ???????????? ?????? ?????? ??????.
            coroutineScope.launch { processPurchases(purchases) }
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * ?????? ????????? ???????????? ???????????? ?????? ??????.
     */
    override fun onProductDetailsResponse(
        billingResult: BillingResult,
        products: MutableList<ProductDetails>
    ) {
        billingResult.printLog()
        if (billingResult.isOk()) {
            _statePublisher emmitting GemProductsReceived(products)
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * BillingClient??? ??????????????? ?????? ?????? ?????? ????????? ??? ??????????????? ??????.
     */
    override fun onBillingSetupFinished(billingResult: BillingResult) {
        billingResult.printLog()
        if (billingResult.isOk()) {
            // ?????? ????????????,
            _statePublisher emmitting BillingState.IsBillingReady
            // ?????? ?????? ?????? ?????? ???????????? ?????? ?????? ??????.
            coroutineScope.launch { refreshPurchases() }
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * ?????? ???????????? ?????? ????????? ????????? ??????????????? ?????? ??????.
     */
    private suspend fun refreshPurchases() {
        val purchaseResult = billingClient.queryPurchasesAsync(
            BillingClient.ProductType.INAPP
        )
        val billingResult = purchaseResult.billingResult
        billingResult.printLog()
        if (billingResult.isOk()) {
            processPurchases(purchaseResult.purchasesList)
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * ?????? ????????? ??????????????? ?????? ?????? ?????? ????????? ?????? ??????.
     * @param purchases ?????? ????????? ?????? ??????.
     */
    private suspend fun processPurchases(purchases: List<Purchase>?) {
        if (purchases == null || purchases.isEmpty()) {
            Log.d(TAG, "processPurchase() >> ???????????? ??? ????????? ????????? ????????????.")
            return
        }
        purchases.forEach {
            consumePurchase(it)
        }
    }

    /**
     * ?????? ????????? ???????????? ?????? ?????? ?????? ????????? ?????? ??????.
     * @param purchase ?????? ????????? ??????.
     */
    private suspend fun consumePurchase(purchase: Purchase) {
        // ?????? ?????? ???????????? ????????? ?????????????????? ??????.
        if (consumedPurchases.contains(purchase.purchaseToken)) {
            return
        }
        val consumedResult = billingClient.consumePurchase(
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        )
        val billingResult = consumedResult.billingResult
        billingResult.printLog()
        if (billingResult.isOk()) {
            _statePublisher emmitting BillingState.PurchaseCompleted
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    override fun onBillingServiceDisconnected() {
        Log.d(TAG, ">> onBillingServiceDisconnected()")
    }

    private fun isBillingClientReady() =
        (::billingClient.isInitialized && billingClient.isReady)

    override fun onCreate(owner: LifecycleOwner) {
        Log.d(TAG, ">> onCreate()")
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()
        if (!isBillingClientReady()) {
            billingClient.startConnection(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d(TAG, ">> onDestroy()")
        if (isBillingClientReady()) {
            billingClient.endConnection()
            Log.d(TAG, ">> BillingClient ????????? ?????? ???????????????.")
        }
    }

    /**
     * `BillingResult`??? `responseCode`??? ?????? ????????? emit??????.
     */
    private fun emitErrorStates(responseCode: Int) {
        if (responseCode == OK) return
        _statePublisher emmitting when (responseCode) {
            USER_CANCELED -> {
                BillingState.Error.UserCanceled
            }
            else -> {
                BillingState.Error.Common(
                    getBillingResponseMessage(responseCode)
                )
            }
        }
    }

    private fun BillingResult.printLog() {
        val responseCodeText = getBillingResponseMessage(responseCode)
        Log.d(TAG, ">> response = [$responseCode] $responseCodeText, debugMessage = $debugMessage")
    }

    private fun getBillingResponseMessage(responseCode: Int): String {
        return when (responseCode) {
            ITEM_ALREADY_OWNED -> "ITEM_ALREADY_OWNED : ?????? ????????? ?????? ??????????????????."
            DEVELOPER_ERROR -> "DEVELOPER_ERROR : ????????? ??????????????????."
            ERROR -> "ERROR : ????????? ??????????????????."
            SERVICE_DISCONNECTED -> "SERVICE_DISCONNECTED : ????????? ??????????????????."
            SERVICE_UNAVAILABLE -> "SERVICE_UNAVAILABLE : ?????? ???????????? ????????? ??? ????????????."
            BILLING_UNAVAILABLE -> "BILLING_UNAVAILABLE : ?????? ???????????? ????????? ??? ????????????."
            ITEM_UNAVAILABLE -> "ITEM_UNAVAILABLE : ?????? ????????? ????????? ????????????."
            FEATURE_NOT_SUPPORTED -> "FEATURE_NOT_SUPPORTED : ?????? ????????? ????????? ????????????."
            ITEM_NOT_OWNED -> "ITEM_NOT_OWNED : ?????? ????????? ????????? ????????????."
            USER_CANCELED -> "USER_CANCELED : ???????????? ????????? ??????????????????."
            else -> ""
        }
    }

    private infix fun <T> MutableSharedFlow<T>.emmitting(data: T) {
        coroutineScope.launch {
            emit(data)
        }
    }

    companion object {
        private const val TAG = "GoogleBillingHelperImpl"
        private val PRODUCTS = arrayListOf(
            PRODUCT_GEM_50,
            PRODUCT_GEM_100,
            PRODUCT_GEM_200,
            PRODUCT_GEM_500,
            PRODUCT_GEM_1000
        )
    }
}
