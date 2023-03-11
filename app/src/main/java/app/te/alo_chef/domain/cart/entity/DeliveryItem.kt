package app.te.alo_chef.domain.cart.entity

import androidx.annotation.Keep

@Keep
data class DeliveryItem(
    val date: String,
    val delivery: Float,
    val regionName: String
)
