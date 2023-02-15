package app.te.alo_chef.data.home.data_source.dto

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
@Keep
data class MealsData(
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0L,
    @SerializedName("id") @Expose
    var id: Int = 0,
    @SerializedName("name")
    @Expose
    val name: String = "",

    @SerializedName("grams")
    @Expose
    val grams: String = "",

    @SerializedName("is_liked")
    @Expose
    var isLiked: Int = 0,

    @SerializedName("lable")
    @Expose
    val lable: String = "",

    @SerializedName("price_before")
    @Expose
    val priceBefore: String = "",

    @SerializedName("price_after")
    @Expose
    val priceAfter: String = "",

    @SerializedName("image")
    @Expose
    val image: String = "",

    @SerializedName("description")
    @Expose
    val description: String = "",

    @SerializedName("publish_date")
    @Expose
    val publishDate: String = "",

    @SerializedName("add_to_cart")
    @Expose
    val add_to_cart: Int = 0,

    @SerializedName("points")
    val points: Int = 0
)
