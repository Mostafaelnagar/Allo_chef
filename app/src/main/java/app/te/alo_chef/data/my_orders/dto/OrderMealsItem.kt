package app.te.alo_chef.data.my_orders.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class OrderMealsItem(
    @SerializedName("meal_image")
     var mealImage: String = "",

    @SerializedName("order_date")
     val orderDate: String = "",

    @SerializedName("quantity")
     val quantity: String = "",

    @SerializedName("price")
     val price: String = "",

    @SerializedName("meal_name")
     val mealName: String = "",

    @SerializedName("created_at")
     val createdAt: String = "",

    @SerializedName("id")
     val id: Int = 0,

    @SerializedName("order_id")
     val orderId: String = "",

    @SerializedName("meal_id")
     val mealId: String = "",

    @SerializedName("meal_description")
     val mealDescription: String = "",
) 
