package app.te.alo_chef.presentation.subscriptions.listener

import app.te.alo_chef.data.subscriptions.dto.SubscriptionData

interface SubscriptionsListener {
    fun subscribeNow(subscriptionData: SubscriptionData)
}