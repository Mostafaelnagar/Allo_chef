package app.te.alo_chef.presentation.subscriptions.ui_state

import android.content.Context
import app.te.alo_chef.R
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.presentation.subscriptions.listener.SubscriptionsListener

class SubscriptionItemUiState(
    val subscriptionData: SubscriptionData,
    private val subscriptionsListener: SubscriptionsListener
) {

    fun packagePrice(context: Context): String =
        subscriptionData.price.toString().plus(" ${context.getString(R.string.coin)}")

    fun points(context: Context): String =
        subscriptionData.points.toString().plus(" ${context.getString(R.string.point)}")

    fun days(context: Context): String =
        subscriptionData.days.plus(" ${context.getString(R.string.days)}")

    fun subscribeNow() {
        subscriptionsListener.subscribeNow(subscriptionData)
    }
}