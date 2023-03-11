package app.te.alo_chef.data.general.dto.config


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Setting(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("meal_price_percentage")
    val mealPricePercentage: String = "",
    @SerializedName("point_equality_in_egp")
    val pointEqualityInEgp: Float = 0f,
    @SerializedName("delivery_cost_in_egp")
    val deliveryCostInEgp: Float = 0f

)