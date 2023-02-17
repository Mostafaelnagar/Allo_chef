package app.te.alo_chef.data.subscriptions.dto

import com.google.gson.annotations.SerializedName

data class SubscriptionData(
    @SerializedName("price")
    var price: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("days")
    val days: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("points")
    val points: String = ""
)
