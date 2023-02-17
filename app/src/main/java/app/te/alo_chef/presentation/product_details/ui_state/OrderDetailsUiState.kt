package app.te.alo_chef.presentation.product_details.ui_state

import android.content.Context
import app.te.alo_chef.R
import app.te.alo_chef.data.meal_details.dto.MainDetails
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.product_details.listeners.ProductDetailsListener

class OrderDetailsUiState(
    val mainDetails: MainDetails,
    val productDetailsListener: ProductDetailsListener
) :
    MealsUiState(homeMealsData = mainDetails.meal, productDetailsListener) {

    fun mealWeight(context: Context) =
        homeMealsData.grams.plus(" ${context.getString(R.string.gm)}")

    fun mealCapacity(context: Context) =
        homeMealsData.persons.plus(" ${context.getString(R.string.persons)}")

    fun mealRatings(context: Context) =
        homeMealsData.rate.plus("\n ${context.getString(R.string.ratings)}")

    fun mealFavorites(context: Context) =
        homeMealsData.userLikesCount.toString().plus("\n ${context.getString(R.string.favorite)}")

    fun mealPhotos(context: Context) =
        homeMealsData.imagesCount.toString().plus("\n ${context.getString(R.string.photo)}")

}