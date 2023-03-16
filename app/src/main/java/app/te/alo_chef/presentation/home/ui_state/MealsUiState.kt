package app.te.alo_chef.presentation.home.ui_state

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.presentation.base.BaseUiState
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener

open class MealsUiState(val homeMealsData: MealsData, val homeEventListener: HomeEventListener) :
    BaseUiState() {
    @Bindable
    var likeIcon: Int =
        if (homeMealsData.isLiked == 0) R.drawable.ic_favorite_border else R.drawable.ic_account_favorite
        set(value) {
            notifyPropertyChanged(BR.likeIcon)
            field = value
        }


    fun changeLike() {
        if (homeMealsData.isLiked == 0) {
            homeMealsData.isLiked = 1
            likeIcon = R.drawable.ic_account_favorite
            homeEventListener.changeLike(homeMealsData.id)
        } else {
            homeMealsData.isLiked = 0
            likeIcon = R.drawable.ic_favorite_border
            homeEventListener.changeLike(homeMealsData.id)
        }

    }

    fun labelVisibility(): Int = if (homeMealsData.lable.isNotEmpty()) View.VISIBLE else View.GONE

    fun point(context: Context): String =
        "${homeMealsData.points} ${context.getString(R.string.point)}"

    fun pointsVisibility(): Int = if (homeMealsData.points > 0) View.VISIBLE else View.GONE

    fun priceBefore(context: Context): String =
        if (homeMealsData.priceBefore.isNotEmpty()) "${homeMealsData.priceBefore} ${
            context.getString(R.string.coin)
        }"
        else
            ""

    fun priceBeforeVisibility(): Int =
        if (homeMealsData.priceBefore != "0") View.VISIBLE else View.GONE

    fun priceAfterVisibility(): Int =
        if (homeMealsData.priceAfter != 0.0) View.VISIBLE else View.GONE

    fun priceAfter(context: Context): String =
        if (homeMealsData.priceAfter != 0.0)
            "${homeMealsData.priceAfter} ${context.getString(R.string.coin)}"
        else
            "0.0"

    fun btnText(context: Context): String =
        if (homeMealsData.add_to_cart == Constants.ADD_TO_CART_KEY)
            context.getString(R.string.add_cart)
        else
            context.getString(R.string.subscribe)

    open fun btnClick() {
        homeEventListener.addToCart(homeMealsData, homeMealsData.add_to_cart)
    }

    fun openDetails() {
        homeEventListener.openProductDetails(homeMealsData.id, homeMealsData.publishDate)
    }

}