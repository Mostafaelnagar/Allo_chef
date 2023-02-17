package app.te.alo_chef.data.meal_details.dto

import com.google.gson.annotations.SerializedName

data class IngredientsItem(
    @SerializedName("name")
    var name: String = "",

    @SerializedName("id")
    val id: Int = 0
)
