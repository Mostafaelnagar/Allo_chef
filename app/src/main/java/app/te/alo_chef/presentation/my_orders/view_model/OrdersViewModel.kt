package app.te.alo_chef.presentation.my_orders.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.domain.orders.use_case.OrderDetailsUseCase
import app.te.alo_chef.domain.orders.use_case.OrdersUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.my_orders.adapters.OrdersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersUseCase: OrdersUseCase,
    private val orderDetailsUseCase: OrderDetailsUseCase
) : ViewModel() {

    lateinit var ordersAdapter: OrdersAdapter
    private val _ordersResponse =
        MutableStateFlow<Resource<BaseResponse<List<MyOrdersData>>>>(Resource.Default)
    val ordersResponse = _ordersResponse
    private val _orderDetailsResponse =
        MutableStateFlow<Resource<BaseResponse<MyOrdersData>>>(Resource.Default)
    val orderDetailsResponse = _orderDetailsResponse

    fun getMyOrders(type: String) {
        viewModelScope.launch {
            _ordersResponse.value = Resource.Loading
            _ordersResponse.value = ordersUseCase.invoke(Dispatchers.IO, type)
        }
    }

    fun getOrderDetails(orderId: Int) {
        viewModelScope.launch {
            _orderDetailsResponse.value = Resource.Loading
            _orderDetailsResponse.value = orderDetailsUseCase.invoke(Dispatchers.IO, orderId)
        }
    }

}