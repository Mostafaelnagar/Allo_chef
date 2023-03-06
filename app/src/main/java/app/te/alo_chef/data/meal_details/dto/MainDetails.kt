package app.te.alo_chef.data.meal_details.dto

import app.te.alo_chef.data.home.data_source.dto.MealsData
import com.google.gson.annotations.SerializedName

data class MainDetails(
    @SerializedName("meal")
    var meal: MealsData? = MealsData(),

    @SerializedName("other_meals")
    val otherMeals: List<MealsData> = emptyList()
)
