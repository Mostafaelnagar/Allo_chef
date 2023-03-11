package app.te.alo_chef.domain.cart.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Keep
@Entity(tableName = "MealCart")
data class MealCart(
    @SerializedName("image")
    var image: String = "",

    @SerializedName("quantity")
    var quantity: Int = 0,

    @SerializedName("price_after")
    var priceAfter: Double = 0.0,

    @SerializedName("points")
    var points: Int = 0,

    @SerializedName("description")
    var description: String = "",

    @SerializedName("vip")
    var vip: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("publish_date")
    var publishDate: String = "",
    var expired: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var product_room_id: Int = 0,

    @SerializedName("id")
    var product_id: Int = 0,
    var priceItem: Float = 0f,
)