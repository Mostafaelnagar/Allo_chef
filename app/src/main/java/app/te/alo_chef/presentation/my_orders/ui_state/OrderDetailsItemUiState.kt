package app.te.alo_chef.presentation.my_orders.ui_state

import android.content.Context
import app.te.alo_chef.R
import app.te.alo_chef.data.my_orders.dto.OrderMealsItem

class OrderDetailsItemUiState(val orderMealsItem: OrderMealsItem) {
    fun orderNumber(): String = "#".plus(orderMealsItem.orderId)
    fun itemPrice(context: Context): String =
        orderMealsItem.price.plus(" ${context.getString(R.string.coin)}")

    fun itemQuantity(): String =
        orderMealsItem.quantity.plus(" X")

}