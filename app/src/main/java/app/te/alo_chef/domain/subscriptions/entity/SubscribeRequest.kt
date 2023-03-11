package app.te.alo_chef.domain.subscriptions.entity

import androidx.annotation.Keep

@Keep
data class SubscribeRequest(
    val package_id: Int
)
