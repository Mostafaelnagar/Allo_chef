package app.te.alo_chef.data.home.data_source.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HomeData(
    @SerializedName("days")
    var homeDaysDataList: List<HomeDaysData> = emptyList(),
    @SerializedName("meals")
    val mealsDataList: List<MealsData> = emptyList(),
    @SerializedName("day_name")
     var dayName: String = "",
    @SerializedName("day_date")
     val day_date: String = ""
)