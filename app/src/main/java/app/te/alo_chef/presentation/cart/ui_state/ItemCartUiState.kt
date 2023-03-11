package app.te.alo_chef.presentation.cart.ui_state

import android.content.Context
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.presentation.base.BaseUiState
import app.te.alo_chef.presentation.cart.listener.CartListener

class ItemCartUiState(val mealCart: MealCart, private val cartListener: CartListener) :
    BaseUiState() {
    @Bindable
    var itemCount: Int = mealCart.quantity
        set(value) {
            notifyPropertyChanged(BR.itemCount)
            field = value
        }

    fun itemPrice(context: Context): String =
        mealCart.priceAfter.toString().plus(" ${context.getString(R.string.coin)}")

    fun plus() {
        itemCount = itemCount.plus(1)
        cartListener.updateProductQuantity(productId = mealCart.product_id, 1)
    }

    fun minus() {
        if (itemCount > 1)
            itemCount = itemCount.minus(1)
        cartListener.updateProductQuantity(productId = mealCart.product_id, -1)
    }

    fun deleteItem() {
        cartListener.deleteItem(mealCart.product_room_id)
    }
}