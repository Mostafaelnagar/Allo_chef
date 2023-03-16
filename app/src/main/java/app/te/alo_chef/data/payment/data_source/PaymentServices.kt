package app.te.alo_chef.data.payment.data_source

import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentServices {
    @GET("go-to-payment")
    suspend fun getPaymentData(
        @Query("invoice_value") invoice_value: Float,
        @Query("id") id: Int,
        @Query("type") type: String,
    ): BaseResponse<PaymentResponse>

    @GET("check-payment-status")
    suspend fun paymentCallBack(
        @Query("paymentId") paymentId: String,
    ): BaseResponse<UserResponse>

}