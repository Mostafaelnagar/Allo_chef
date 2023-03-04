package app.te.alo_chef.domain.payment.entity

data class PaymentRequest(
    val invoice_value: Float = 0f,
    val id: Int = 0,
    val type: String
)
