package app.te.alo_chef.data.meal_details.dto

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Keep
data class Meal(
    @SerializedName("image")
    var image: String = "",

    @SerializedName("images")
    val mealImagesList: ArrayList<MealImages> = arrayListOf(),

    @SerializedName("method")
    val method: String = "",

    @SerializedName("price_after")
    val priceAfter: String = "",

    @SerializedName("images_count")
    val imagesCount: Int = 0,

    @SerializedName("description")
    val description: String = "",

    @SerializedName("points")
    val points: String = "",

    @SerializedName("persons")
    val persons: String = "",

    @SerializedName("price_before")
    val priceBefore: String = "",

    @SerializedName("rate")
    val rate: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("user_likes_count")
    val userLikesCount: Int = 0,

    @SerializedName("free_delivery")
    val freeDelivery: Int = 0,

    @SerializedName("lable")
    val lable: String = "",

    @SerializedName("ingredients")
    val ingredients: List<IngredientsItem> = emptyList(),

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("grams")
    val grams: String = "",

    @SerializedName("publish_date")
    val publishDate: String = "",

    @SerializedName("is_liked")
    val isLiked: Int = 0,

    @SerializedName("add_to_cart")
    @Expose
    val add_to_cart: Int = 0
)
