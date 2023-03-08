package app.te.alo_chef.presentation.checkout.ui_state

import android.view.View


data class ItemPayment(
    val type: Int,
    val name: String,
    val icon: Int,
    val amount: String,
    val points: String = "",
    val pointVisibility: Int = View.GONE
)