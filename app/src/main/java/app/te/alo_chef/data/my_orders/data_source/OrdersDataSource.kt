package app.te.alo_chef.data.my_orders.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import javax.inject.Inject

class OrdersDataSource @Inject constructor(private val apiService: OrdersServices) :
    BaseRemoteDataSource() {

    suspend fun getOrders() = safeApiCall {
        apiService.getOrders()
    }

}