package app.te.alo_chef.domain.home.enity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilterRequest(
    @SerializedName("category_id") @Expose
     var categoriesId: List<Int> = listOf(),
    @SerializedName("name")
    @Expose
     var name: String = ""
)
