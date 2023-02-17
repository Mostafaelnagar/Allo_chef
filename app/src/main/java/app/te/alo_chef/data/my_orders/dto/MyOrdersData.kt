package app.te.alo_chef.data.my_orders.dto

import com.google.gson.annotations.SerializedName

data class MyOrdersData(
    @SerializedName("copon_id")
     var coponId: String = "",

    @SerializedName("notes")
     val notes: String = "",

    @SerializedName("total_delivery")
     val totalDelivery: String = "",

    @SerializedName("order_number")
     val orderNumber: String = "",

    @SerializedName("discount")
     val discount: String = "",

    @SerializedName("created_at")
     val createdAt: String = "",

    @SerializedName("received")
     val received: String = "",

    @SerializedName("on_way")
     val onWay: String? = null,

    @SerializedName("delivered")
     val delivered: String? = null,

    @SerializedName("location_id")
     val locationId: String = "",

    @SerializedName("total_after")
     val totalAfter: String = "",

    @SerializedName("rate")
     val rate: String = "",

    @SerializedName("id")
     val id: Int = 0,

    @SerializedName("period_id")
     val periodId: String = "",

    @SerializedName("total_before")
     val totalBefore: String = "",

    @SerializedName("order_meals")
     val orderMeals: List<OrderMealsItem> = listOf()
)
