package app.te.alo_chef.data.checkout.dto.promo

import com.google.gson.annotations.SerializedName

data class PromoData(
    @SerializedName("id")
     var id: Int = 0,
    @SerializedName("code")
     val code: String = "",

    @SerializedName("discount")
     val discount: String = "",

    @SerializedName("description")
     val description: String = "",

    @SerializedName("expire_at")
     val expireAt: String = "",
)
