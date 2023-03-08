package app.te.alo_chef.data.payment.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class PaymentResponse(
    @SerializedName("Message")
    val message: String,
    @SerializedName("ValidationErrors")
    var validationErrors: String,
    @SerializedName("IsSuccess")
    val status: Boolean,
    @SerializedName("Data")
    val paymentData: PaymentData
)
