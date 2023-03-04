package app.te.alo_chef.data.my_orders.repository

import app.te.alo_chef.data.my_orders.data_source.OrdersDataSource
import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.domain.orders.repository.OrdersRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(private val remoteDataSource: OrdersDataSource) :
    OrdersRepository {

    override suspend fun orders(type: String): Resource<BaseResponse<List<MyOrdersData>>> =
        remoteDataSource.getOrders(type)

    override suspend fun orderDetails(orderId: Int): Resource<BaseResponse<MyOrdersData>> =
        remoteDataSource.getOrderDetails(orderId)

}