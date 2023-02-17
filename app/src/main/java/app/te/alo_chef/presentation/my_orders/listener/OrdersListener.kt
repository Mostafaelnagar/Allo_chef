package app.te.alo_chef.presentation.my_orders.listener

import app.te.alo_chef.presentation.my_orders.ui_state.OrderItemUiState

interface OrdersListener {
    fun openOrderDetails(selectedItemUiState: OrderItemUiState)
    fun openOrderHistory() {
        return
    }
}