package com.swkang.model.domain.billing.google

import android.app.Activity
import com.android.billingclient.api.ProductDetails
import kotlinx.coroutines.flow.Flow

/**
 * @author burkdog
 */
interface GoogleBillingHelper {
    /**
     * 결제 상태를 구독하기 위한 `Flow`인스턴스를 얻는다.
     * @see BillingState
     */
    fun getBillingStateListener(): Flow<BillingState>

    /**
     * 보석 상품 목록을 가져 온다.
     */
    suspend fun queryGemProducts()

    /**
     * 결제를 시작 한다.
     * @param activity 활성화된 액티비티의 인스턴스가 필요함.
     * @param productDetails 구매할 상품의 정보
     */
    fun launchBillingFlow(activity: Activity, productDetails: ProductDetails)

    /**
     * 결제 상태
     */
    sealed class BillingState {
        /**
         * 초기 상태
         */
        object Initialized : BillingState()

        /**
         * BillingClient가 준비 되었으며, 상품 목록을 불러오길 기다리는 상태.
         * - 이 상태를 받으면 필요한 추가 작업을 하고 난 뒤, `requestGemProducts()`을 호출 하여 상품 목록을 가져 온다.
         */
        object IsBillingReady : BillingState()

        /**
         * 상품 목록을 받은 상태.
         */
        data class GemProductsReceived(
            val products: List<ProductDetails>
        ) : BillingState()

        /**
         * 구매 완료 상태.
         */
        object PurchaseCompleted : BillingState()

        /**
         * 구매중 발생한 오류.
         */
        sealed class Error : BillingState() {
            /**
             * (오류) 사용자가 결제를 취소함.
             */
            object UserCanceled : Error()
            data class Common(
                val errorMsg: String,
                val throwable: Throwable? = null
            ) : Error()
        }
    }
}
