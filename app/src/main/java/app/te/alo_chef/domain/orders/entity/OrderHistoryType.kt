package app.te.alo_chef.domain.orders.entity

enum class OrderHistoryType(val type: String) {
    CURRENT("current"),
    PREVIOUS("previous")
}