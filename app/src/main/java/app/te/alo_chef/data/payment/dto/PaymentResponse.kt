package app.te.alo_chef.data.payment.dto

import com.google.gson.annotations.SerializedName

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
