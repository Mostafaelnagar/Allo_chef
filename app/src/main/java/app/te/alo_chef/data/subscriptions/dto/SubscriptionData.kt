package app.te.alo_chef.data.subscriptions.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SubscriptionData(
    @SerializedName("price")
    var price: Float = 0f,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("days")
    val days: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("points")
    val points: Int = 0
)
