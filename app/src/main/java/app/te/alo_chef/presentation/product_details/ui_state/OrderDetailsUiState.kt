package app.te.alo_chef.presentation.product_details.ui_state

import android.content.Context
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.data.meal_details.dto.MainDetails
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.product_details.listeners.ProductDetailsListener

class OrderDetailsUiState(
    val mainDetails: MainDetails,
    val productDetailsListener: ProductDetailsListener
) :
    MealsUiState(homeMealsData = mainDetails.meal ?: MealsData(), productDetailsListener) {
    @Bindable
    var iconLike: Int =
        if (homeMealsData.isLiked == 0) R.drawable.ic_favorite_border else R.drawable.ic_account_favorite
        set(value) {
            notifyPropertyChanged(BR.iconLike)
            field = value
        }

    @Bindable
    var count: Int = 1
        set(value) {
            notifyPropertyChanged(BR.count)
            field = value
        }

    fun changeLikeDetails() {
        if (homeMealsData.isLiked == 0) {
            homeMealsData.isLiked = 1
            iconLike = R.drawable.ic_account_favorite
            homeEventListener.changeLike(homeMealsData.id)
        } else {
            homeMealsData.isLiked = 0
            iconLike = R.drawable.ic_favorite_border
            homeEventListener.changeLike(homeMealsData.id)
        }

    }


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

    fun plus() {
        count = count.plus(1)
    }

    fun minus() {
        if (count > 1)
            count = count.minus(1)
    }

    override fun btnClick() {
        homeMealsData.quantity = count
        homeEventListener.addToCart(homeMealsData, homeMealsData.add_to_cart)
    }
}