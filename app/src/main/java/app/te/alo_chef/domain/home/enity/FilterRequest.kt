package app.te.alo_chef.domain.home.enity

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Keep
data class FilterRequest(
    @SerializedName("category_id") @Expose
     var categoriesId: List<Int> = listOf(),
    @SerializedName("name")
    @Expose
     var name: String = ""
)
