package app.te.alo_chef.domain.utils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentBaseResponse<T>(
    @SerializedName("Data")
    val data: T,
    @SerializedName("Message")
    val message: String,
    @SerializedName("ValidationErrors")
    var validationErrors: String,
    @SerializedName("IsSuccess")
    val status: Boolean,
)