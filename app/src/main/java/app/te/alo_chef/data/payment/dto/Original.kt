package app.te.alo_chef.data.payment.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Original(
    @SerializedName("msg")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val paymentMain: PaymentMain
)
