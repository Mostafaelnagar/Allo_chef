package app.te.alo_chef.domain.payment.entity

import androidx.annotation.Keep

@Keep
enum class SendPaymentType(val type: String) {
    ORDER("order"),
    SUBSCRIPTION("subscription")
}