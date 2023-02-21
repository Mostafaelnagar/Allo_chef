package app.te.alo_chef.data.checkout.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import javax.inject.Inject

class CheckoutDataSource @Inject constructor(private val apiService: CheckoutServices) :
    BaseRemoteDataSource() {

    suspend fun checkPromoCode(code: String) = safeApiCall {
        apiService.checkPromoCode(code)
    }

    suspend fun getDeliveryTimes() = safeApiCall {
        apiService.getDeliveryTimes()
    }

}