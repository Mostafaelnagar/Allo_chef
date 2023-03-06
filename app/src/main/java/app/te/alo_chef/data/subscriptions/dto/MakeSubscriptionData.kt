package app.te.alo_chef.data.subscriptions.dto

import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import com.google.gson.annotations.SerializedName

data class MakeSubscriptionData(
    @SerializedName("payment")
    val payment: PaymentResponse
) :UserResponse()
