package app.te.alo_chef.domain.orders.entity

import androidx.annotation.Keep

@Keep
enum class OrderHistoryType(val type: String) {
    CURRENT("current"),
    PREVIOUS("previous")
}