package app.te.alo_chef.data.checkout.dto

import com.google.gson.annotations.SerializedName

data class DeliveryTimes(
    @SerializedName("from")
    var from: String = "",

    @SerializedName("to")
    val to: String = "",

    @SerializedName("id")
    val id: Int = 0
) {
    fun getTimeSlot(): String = from.plus(" - $to")
}
