package app.te.alo_chef.presentation.checkout.ui_state


data class ItemPayment(
    val type: Int,
    val name: String,
    val icon: Int,
    val amount: String,
)