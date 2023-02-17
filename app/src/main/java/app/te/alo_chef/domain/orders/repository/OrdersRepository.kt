package app.te.alo_chef.domain.orders.repository

import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface OrdersRepository {
    suspend fun orders() :Resource<BaseResponse<List<MyOrdersData>>>
}