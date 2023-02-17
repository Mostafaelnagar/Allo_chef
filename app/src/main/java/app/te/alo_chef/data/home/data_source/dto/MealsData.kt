package app.te.alo_chef.data.home.data_source.dto

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.te.alo_chef.data.meal_details.dto.IngredientsItem
import app.te.alo_chef.data.meal_details.dto.MealImages
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
@Keep
data class MealsData(
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0L,
    @SerializedName("id") @Expose
    @Ignore
    var id: Int = 0,
    @SerializedName("name")
    @Ignore
    @Expose
    val name: String = "",

    @SerializedName("grams")
    @Ignore
    @Expose
    val grams: String = "",

    @SerializedName("is_liked")
    @Ignore
    @Expose
    var isLiked: Int = 0,

    @SerializedName("lable")
    @Ignore
    @Expose
    val lable: String = "",

    @SerializedName("price_before")
    @Ignore
    @Expose
    val priceBefore: String = "",

    @SerializedName("price_after")
    @Ignore
    @Expose
    val priceAfter: String = "",

    @SerializedName("image")
    @Ignore
    @Expose
    val image: String = "",

    @SerializedName("description")
    @Ignore
    @Expose
    val description: String = "",

    @SerializedName("publish_date")
    @Ignore
    @Expose
    val publishDate: String = "",

    @SerializedName("add_to_cart")
    @Ignore
    @Expose
    val add_to_cart: Int = 0,

    @SerializedName("points")
    @Ignore
    val points: Int = 0,

    @SerializedName("images")
    @Ignore
    val mealImagesList: ArrayList<MealImages> = arrayListOf(),

    @SerializedName("method")
    @Ignore
    val method: String = "",

    @SerializedName("images_count")
    @Ignore
    val imagesCount: Int = 0,

    @SerializedName("persons")
    @Ignore
    val persons: String = "",

    @SerializedName("rate")
    @Ignore
    val rate: String = "",

    @SerializedName("user_likes_count")
    @Ignore
    val userLikesCount: Int = 0,

    @SerializedName("free_delivery")
    @Ignore
    val freeDelivery: Int = 0,

    @SerializedName("ingredients")
    @Ignore
    val ingredients: List<IngredientsItem> = emptyList(),

    )
