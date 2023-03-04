package app.te.alo_chef.data.my_orders.data_source

import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersServices {
    @GET("user/my-orders")
    suspend fun getOrders(@Query("type") type: String): BaseResponse<List<MyOrdersData>>

    @GET("user/order_details")
    suspend fun getOrderDetails(@Query("order_id") order_id: Int): BaseResponse<MyOrdersData>

}