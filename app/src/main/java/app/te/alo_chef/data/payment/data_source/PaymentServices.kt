package app.te.alo_chef.data.payment.data_source

import app.te.alo_chef.data.payment.dto.PaymentData
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.PaymentBaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentServices {
    @GET("go-to-payment")
    suspend fun getPaymentData(
        @Query("full_name") full_name: String,
        @Query("invoice_value") invoice_value: Float,
    ): PaymentBaseResponse<PaymentData>

}