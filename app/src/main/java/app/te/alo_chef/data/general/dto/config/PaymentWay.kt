package app.te.alo_chef.data.general.dto.config


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class PaymentWay(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
)