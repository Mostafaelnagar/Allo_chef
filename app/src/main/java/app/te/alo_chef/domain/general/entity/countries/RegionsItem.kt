package app.te.alo_chef.domain.general.entity.countries

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegionsItem(
    @SerializedName("name")
    val name: String = "",

    @SerializedName("id")
    val id: Int = 0
)
