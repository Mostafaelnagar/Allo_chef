package app.te.alo_chef.presentation.base

enum class PaymentTypes(val paymentType: Int) {
    WALLET(1),
    POINTS(2),
    ONLINE(3),
    CASH(4)
}