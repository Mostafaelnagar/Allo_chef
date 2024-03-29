package app.te.alo_chef.data.payment.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentResponse(
//    @SerializedName("original")
//    val original: Original
    @SerializedName("Message")
    val message: String,
    @SerializedName("IsSuccess")
    val status: Boolean,

    @SerializedName("ValidationErrors")
    var validationErrors: String,
    @SerializedName("Data")
    val paymentData: PaymentData
)
