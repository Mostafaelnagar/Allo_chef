package app.te.alo_chef.data.meal_details.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class MealImages(
    @SerializedName("image")
     var image: String = "",

    @SerializedName("updated_at")
     val updatedAt: String = "",

    @SerializedName("created_at")
     val createdAt: String = "",

    @SerializedName("id")
     val id: Int = 0,

    @SerializedName("meal_id")
     val mealId: String= "",
)
