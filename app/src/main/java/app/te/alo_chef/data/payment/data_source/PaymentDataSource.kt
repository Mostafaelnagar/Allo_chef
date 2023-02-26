package app.te.alo_chef.data.payment.data_source

import app.te.alo_chef.data.remote.PaymentRemoteDataSource
import javax.inject.Inject

class PaymentDataSource @Inject constructor(private val apiService: PaymentServices) :
    PaymentRemoteDataSource() {

    suspend fun getPaymentData(full_name: String, invoice_value: Float) =
        safeApiCall {
            apiService.getPaymentData(full_name, invoice_value)
        }

}