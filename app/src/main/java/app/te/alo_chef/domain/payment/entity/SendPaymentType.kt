package app.te.alo_chef.domain.payment.entity

enum class SendPaymentType(val type: String) {
    ORDER("order"),
    SUBSCRIPTION("subscription")
}