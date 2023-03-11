package app.te.alo_chef.data.wallet.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class TransActionsHistoryItem(
    @SerializedName("subscription_id")
    var subscriptionId: String = "",

    @SerializedName("wallet_new_value")
    val walletNewValue: String = "",

    @SerializedName("user_id")
    val userId: Int = 0,

    @SerializedName("points_old_value")
    val pointsOldValue: String = "",

    @SerializedName("wallet_old_value")
    val walletOldValue: String = "",

    @SerializedName("points_new_value")
    val pointsNewValue: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("created_at")
    val createdAt: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("order_id")
    val orderId: Int = 0
)
