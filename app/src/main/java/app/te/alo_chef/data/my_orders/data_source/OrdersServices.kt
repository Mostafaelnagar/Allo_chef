package app.te.alo_chef.data.my_orders.data_source

import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET

interface OrdersServices {
    @GET("user/my-orders")
    suspend fun getOrders(): BaseResponse<List<MyOrdersData>>
}