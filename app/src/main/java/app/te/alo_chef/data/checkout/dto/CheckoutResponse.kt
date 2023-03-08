package app.te.alo_chef.data.checkout.dto

import androidx.annotation.Keep
import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutResponse(
    @SerializedName("payment")
    val payment: PaymentResponse? = null
) : UserResponse()
