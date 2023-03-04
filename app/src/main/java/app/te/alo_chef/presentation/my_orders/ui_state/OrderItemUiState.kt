package app.te.alo_chef.presentation.my_orders.ui_state

import android.content.Context
import androidx.annotation.Keep
import app.te.alo_chef.R
import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.presentation.my_orders.adapters.OrderDetailsAdapter
import app.te.alo_chef.presentation.my_orders.listener.OrdersListener

@Keep
class OrderItemUiState(val myOrdersData: MyOrdersData, val ordersListener: OrdersListener?) {
    val orderDetailsAdapter = OrderDetailsAdapter()

    init {
        orderDetailsAdapter.differ.submitList(myOrdersData.orderMeals.map {
            OrderDetailsItemUiState(
                it
            )
        })
    }

    fun orderNumber(): String = "#".plus(myOrdersData.orderNumber)
    fun orderPrice(context: Context): String =
        myOrdersData.totalAfter.plus(" ${context.getString(R.string.coin)}")

    fun orderStatus(context: Context): String = if (myOrdersData.delivered?.isNotEmpty() == true)
        context.getString(R.string.delivered)
    else if (myOrdersData.onWay?.isNotEmpty() == true)
        context.getString(R.string.on_way)
    else
        context.getString(R.string.order_received)

    fun openDetails() {
        ordersListener?.openOrderDetails(this)
    }

    fun onWayDate(): String =
        if (myOrdersData.onWay?.isNotEmpty() == true) myOrdersData.onWay else "-----"

    fun onWayIcon(): Int =
        if (myOrdersData.onWay?.isNotEmpty() == true) R.drawable.ic_delivery_motorbike_success else R.drawable.ic_delivery_motorbike

    fun deliveredDate(): String =
        if (myOrdersData.delivered?.isNotEmpty() == true) myOrdersData.delivered else "-----"

    fun onDeliveredIcon(): Int =
        if (myOrdersData.delivered?.isNotEmpty() == true) R.drawable.ic_confirm_success else R.drawable.ic_confirm

    fun onDeliveredLineIcon(): Int =
        if (myOrdersData.delivered?.isNotEmpty() == true) R.drawable.dotted_line else R.drawable.dotted_line_gray

}