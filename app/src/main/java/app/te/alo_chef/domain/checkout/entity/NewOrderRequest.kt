package app.te.alo_chef.domain.checkout.entity

import androidx.annotation.Keep
import app.te.alo_chef.domain.cart.entity.MealCart
import com.google.gson.annotations.SerializedName
@Keep
data class NewOrderRequest(
    @SerializedName("period_id")
    var deliveryTime: Int = 0,

    var deliveryTimeText: String = "",
    @SerializedName("delivery_note")
    var deliveryNote: String? = null,

    @SerializedName("promo_id")
    var promoId: Int? = null,

    @SerializedName("location_id")
    var locationId: Int = 0,

    @SerializedName("total_delivery")
    var totalDelivery: String = "",

    @SerializedName("total_meals")
    var totalMeals: Float = 0f,

    @SerializedName("package")
    var paymentMethod: Int = 0,

    @SerializedName("meals")
    var mealCarts: List<MealCart> = mutableListOf()
)
