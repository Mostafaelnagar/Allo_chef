package app.te.alo_chef.domain.auth.entity.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    @SerializedName("image")
    var image: String = "",
    @SerializedName("lng")
    val lng: Double = 0.0,

    @SerializedName("jwt")
    val jwt: String = "",

    @SerializedName("verified_status")
    val verified: Int = 0,

    @SerializedName("status")
    val active: Int = 0,

    @SerializedName("token")
    val token: String = "",

    @SerializedName("phone")
    val phone: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("email")
    val email: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("lat")
    val lat: Double = 0.0,

    @SerializedName("points")
    val points: Long = 0,

    @SerializedName("wallet")
    val wallet: Float = 0f,

    @SerializedName("subscription_name")
    val subscriptionName: String = "",

    @SerializedName("subscription_expire")
    val subscriptionExpire: String = "",

    )
