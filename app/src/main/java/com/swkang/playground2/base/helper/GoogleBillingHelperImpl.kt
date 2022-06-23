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
        Log.d(TAG, ">> requestGemProducts()")
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
                        // .setOfferToken(offerToken) - 사용자 혜택으로 구매시 추가.
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
     * `launchBillingFlow()`하고 난 뒤 콜백.
     */
    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        billingResult.printLog()
        if (billingResult.isOk()) {
            // 결제 성공 -> 영수증을 소비 처리 한다.
            coroutineScope.launch { processPurchases(purchases) }
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * 상품 목록을 가져오게 했을때에 대한 콜백.
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
     * BillingClient가 성공적으로 사용 준비 완료 되었을 때 불리어지는 콜백.
     */
    override fun onBillingSetupFinished(billingResult: BillingResult) {
        billingResult.printLog()
        if (billingResult.isOk()) {
            // 상태 내보내고,
            _statePublisher emmitting BillingState.IsBillingReady
            // 소비 처리 되지 않은 영수증을 처리 하게 한다.
            coroutineScope.launch { refreshPurchases() }
        } else {
            emitErrorStates(billingResult.responseCode)
        }
    }

    /**
     * 소비 처리되지 않은 영수증 목록을 구글로부터 가져 온다.
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
     * 구매 완료된 영수증들을 소비 처리 하여 결제를 종료 한다.
     * @param purchases 구매 영수증 정보 목록.
     */
    private suspend fun processPurchases(purchases: List<Purchase>?) {
        if (purchases == null || purchases.isEmpty()) {
            Log.d(TAG, "processPurchase() >> 처리해야 할 영수증 목록이 없습니다.")
            return
        }
        purchases.forEach {
            consumePurchase(it)
        }
    }

    /**
     * 구매 완료된 영수증을 소비 처리 하여 결제를 종료 한다.
     * @param purchase 구매 영수증 정보.
     */
    private suspend fun consumePurchase(purchase: Purchase) {
        // 이미 소비 처리되어 로컬에 저장되었는지 확인.
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
            Log.d(TAG, ">> BillingClient 연결이 종료 되었습니다.")
        }
    }

    /**
     * `BillingResult`의 `responseCode`에 따라 오류를 emit한다.
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
            ITEM_ALREADY_OWNED -> "ITEM_ALREADY_OWNED : 해당 상품은 이미 보유중입니다."
            DEVELOPER_ERROR -> "DEVELOPER_ERROR : 오류가 발생했습니다."
            ERROR -> "ERROR : 오류가 발생했습니다."
            SERVICE_DISCONNECTED -> "SERVICE_DISCONNECTED : 오류가 발생했습니다."
            SERVICE_UNAVAILABLE -> "SERVICE_UNAVAILABLE : 결제 서비스를 사용할 수 없습니다."
            BILLING_UNAVAILABLE -> "BILLING_UNAVAILABLE : 결제 서비스를 사용할 수 없습니다."
            ITEM_UNAVAILABLE -> "ITEM_UNAVAILABLE : 상품 정보에 문제가 있습니다."
            FEATURE_NOT_SUPPORTED -> "FEATURE_NOT_SUPPORTED : 상품 정보에 문제가 있습니다."
            ITEM_NOT_OWNED -> "ITEM_NOT_OWNED : 상품 정보에 문제가 있습니다."
            USER_CANCELED -> "USER_CANCELED : 사용자가 결제를 취소했습니다."
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
