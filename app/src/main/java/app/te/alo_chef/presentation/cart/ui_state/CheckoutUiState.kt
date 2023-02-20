package app.te.alo_chef.presentation.cart.ui_state

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.presentation.base.BaseUiState

class CheckoutUiState(val context: Context) : BaseUiState() {

    private var cartItemTotal: Float = 0f

    @Bindable
    var cartItemTotalText: String = ""
        set(value) {
            notifyPropertyChanged(BR.cartItemTotalText)
            field = value
        }

    private var discount: Float = 0f

    @Bindable
    var discountText: String = "0.0".plus(" ${context.getString(R.string.coin)}")
        set(value) {
            notifyPropertyChanged(BR.discountText)
            field = value
        }

    private var deliveryFees: Float = 0f

    @Bindable
    var deliveryRegion: String = ""
        set(value) {
            notifyPropertyChanged(BR.deliveryRegion)
            field = value
        }

    @Bindable
    var deliveryFeesText: String = "0.0".plus(" ${context.getString(R.string.coin)}")
        set(value) {
            notifyPropertyChanged(BR.deliveryFeesText)
            field = value
        }

    @Bindable
    var deliveryTime: String =context.getString(R.string.change_delivery_time)
        set(value) {
            notifyPropertyChanged(BR.deliveryTime)
            field = value
        }

    @Bindable
    var orderTotal: String = ""
        set(value) {
            notifyPropertyChanged(BR.orderTotal)
            field = value
        }

    fun updateCartItemTotal(cartItemTotal: String) {
        this.cartItemTotal = cartItemTotal.toFloat()
        cartItemTotalText = cartItemTotal.plus(" ${context.getString(R.string.coin)}")
        updateTotal()
    }

    fun updateDiscount(discount: String) {
        this.discount = discount.toFloat()
        discountText = discount.plus(" ${context.getString(R.string.coin)}")
        updateTotal()
    }

    fun updateDeliveryFees(deliveryFees: Float) {
        Log.e("updateDeliveryFees", "updateDeliveryFees: " + deliveryFees)
        this.deliveryFees = deliveryFees
        deliveryFeesText = deliveryFees.toString().plus(" ${context.getString(R.string.coin)}")
        updateTotal()
    }

    private fun updateTotal() {
        orderTotal = ((cartItemTotal + deliveryFees) - discount).toString()
            .plus(" ${context.getString(R.string.coin)}")
    }

}