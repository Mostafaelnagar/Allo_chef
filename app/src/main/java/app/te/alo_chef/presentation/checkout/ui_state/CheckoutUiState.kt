package app.te.alo_chef.presentation.checkout.ui_state

import android.content.Context
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.data.checkout.dto.promo.PromoData
import app.te.alo_chef.domain.checkout.entity.NewOrderRequest
import app.te.alo_chef.presentation.base.BaseUiState
import app.te.alo_chef.presentation.base.PaymentTypes
import com.structure.base_mvvm.DefaultLocation

class CheckoutUiState(val context: Context) : BaseUiState() {
    private var cartItemTotal: Float = 0f
    var totalPoints: Int = 0
    var totalWallet: Float = 0f
    val newOrderRequest = NewOrderRequest()

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
    var deliveryTime: String = context.getString(R.string.change_delivery_time)
        set(value) {
            notifyPropertyChanged(BR.deliveryTime)
            field = value
        }

    @Bindable
    var orderTotalText: String = ""
        set(value) {
            notifyPropertyChanged(BR.orderTotalText)
            field = value
        }
    var orderTotal: Float = 0f

    fun updateCartItemTotal(cartItemTotal: Float) {
        this.cartItemTotal = cartItemTotal
        cartItemTotalText = cartItemTotal.toString().plus(" ${context.getString(R.string.coin)}")
        newOrderRequest.totalMeals = cartItemTotal
        updateTotal()
    }

    fun updateDiscount(discount: PromoData) {
        this.discount = discount.discount.toFloat()
        discountText = discount.discount.plus(" ${context.getString(R.string.coin)}")
        // update promo id for creating order
        newOrderRequest.promoId = discount.id
        updateTotal()
    }

    private fun updateDeliveryFees(deliveryFees: Float) {
        this.deliveryFees = deliveryFees
        deliveryFeesText = deliveryFees.toString().plus(" ${context.getString(R.string.coin)}")
        newOrderRequest.totalDelivery = deliveryFees.toString()
        updateTotal()
    }

    fun updateLocation(defaultLocation: DefaultLocation, dateSize: Int) {
        updateDeliveryFees(defaultLocation.delivery * dateSize)
        newOrderRequest.locationId = defaultLocation.id
        deliveryRegion =
            defaultLocation.regionName.ifEmpty { context.getString(R.string.pickup_your_location) }
    }

    private fun updateTotal() {
        orderTotal = (cartItemTotal + deliveryFees) - discount
        orderTotalText = orderTotal.toString().plus(" ${context.getString(R.string.coin)}")
    }

    fun updateDeliveryTimeText() {
        deliveryTime =
            newOrderRequest.deliveryTimeText.ifEmpty { context.getString(R.string.change_delivery_time) }
    }

    fun checkoutPreValidation(
        showValidationError: (String) -> Unit,
        openTime: () -> Unit,
        openPayment: () -> Unit,
        finishOrder: () -> Unit
    ) {
        if (newOrderRequest.deliveryTime == 0) {
            showValidationError(context.getString(R.string.tv_select_time))
            openTime()
        } else if (newOrderRequest.paymentMethod == 0) {
            showValidationError(context.getString(R.string.choose_payment))
        } else if (newOrderRequest.locationId == 0)
            showValidationError(context.getString(R.string.pickup_your_location))
//        if (newOrderRequest.paymentMethod == PaymentTypes.ONLINE.paymentType)
//            openPayment()
        else
            finishOrder()

    }
}