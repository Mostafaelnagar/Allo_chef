package app.te.alo_chef.data.home.data_source.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class HomeDaysData(
    @SerializedName("id")
     var id: Int = 0,

    @SerializedName("name")
     val name: String = "",

    @SerializedName("date")
     val date: String = "",

    @SerializedName("selected")
     val selected: Int = 0
)
