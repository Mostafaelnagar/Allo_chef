package app.te.alo_chef.presentation.my_orders.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.domain.orders.use_case.OrdersUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.my_orders.adapters.OrdersAdapter
import app.te.alo_chef.presentation.my_orders.ui_state.OrderItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersUseCase: OrdersUseCase,
) : ViewModel() {
    lateinit var selectedItemUiState: OrderItemUiState

    lateinit var ordersAdapter: OrdersAdapter
    private val _ordersResponse =
        MutableStateFlow<Resource<BaseResponse<List<MyOrdersData>>>>(Resource.Default)
    val ordersResponse = _ordersResponse

    fun getMyOrders() {
        viewModelScope.launch {
            _ordersResponse.value = Resource.Loading
            _ordersResponse.value = ordersUseCase.invoke(Dispatchers.IO)
        }
    }
}