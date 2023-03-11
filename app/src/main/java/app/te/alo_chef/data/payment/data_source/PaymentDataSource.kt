package app.te.alo_chef.data.payment.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.payment.entity.PaymentRequest
import javax.inject.Inject

class PaymentDataSource @Inject constructor(private val apiService: PaymentServices) :
    BaseRemoteDataSource() {

    suspend fun getPaymentData(paymentRequest: PaymentRequest) =
        safeApiCall {
            apiService.getPaymentData(
                invoice_value = paymentRequest.invoice_value,
                id = paymentRequest.id,
                type = paymentRequest.type
            )
        }

    suspend fun paymentCallBack(paymentId: String) =
        safeApiCall {
            apiService.paymentCallBack(
                paymentId
            )
        }

}