package app.te.alo_chef.data.checkout.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.checkout.entity.NewOrderRequest
import javax.inject.Inject

class CheckoutDataSource @Inject constructor(private val apiService: CheckoutServices) :
    BaseRemoteDataSource() {

    suspend fun checkPromoCode(code: String) = safeApiCall {
        apiService.checkPromoCode(code)
    }

    suspend fun getDeliveryTimes() = safeApiCall {
        apiService.getDeliveryTimes()
    }

    suspend fun checkout(newOrderRequest: NewOrderRequest) = safeApiCall {
        apiService.checkout(newOrderRequest)
    }

}