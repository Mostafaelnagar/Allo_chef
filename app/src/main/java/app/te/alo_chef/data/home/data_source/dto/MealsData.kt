package app.te.alo_chef.data.home.data_source.dto

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import app.te.alo_chef.data.meal_details.dto.IngredientsItem
import app.te.alo_chef.data.meal_details.dto.MealImages
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class MealsData(
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
    val priceAfter: Double = 0.0,

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
    val points: Int = 0,

    @SerializedName("images")
    val mealImagesList: ArrayList<MealImages> = arrayListOf(),

    @SerializedName("method")
    val method: String = "",

    @SerializedName("images_count")
    val imagesCount: Int = 0,

    @SerializedName("persons")
    val persons: String = "",

    @SerializedName("rate")
    val rate: String = "",

    @SerializedName("user_likes_count")
    val userLikesCount: Int = 0,

    @SerializedName("free_delivery")
    val freeDelivery: Int = 0,
    var quantity: Int = 1,

    @SerializedName("ingredients")
    val ingredients: List<IngredientsItem> = emptyList(),

    )
