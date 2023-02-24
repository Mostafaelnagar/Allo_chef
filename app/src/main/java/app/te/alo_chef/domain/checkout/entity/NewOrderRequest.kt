package app.te.alo_chef.domain.checkout.entity

import app.te.alo_chef.domain.cart.entity.MealCart
import com.google.gson.annotations.SerializedName

data class NewOrderRequest(
    @SerializedName("period_id")
    var deliveryTime: Int = 0,

    var deliveryTimeText: String = "",
    @SerializedName("delivery_note")
    var deliveryNote: String = "",

    @SerializedName("promo_id")
    var promoId: String = "",

    @SerializedName("location_id")
    var locationId: String = "",

    @SerializedName("total_delivery")
    var totalDelivery: String = "",

    @SerializedName("total_meals")
    var totalMeals: String = "",

    @SerializedName("package")
    var paymentMethod: Int = 0,

    @SerializedName("meals")
    var mealCarts: List<MealCart> = mutableListOf()
)
