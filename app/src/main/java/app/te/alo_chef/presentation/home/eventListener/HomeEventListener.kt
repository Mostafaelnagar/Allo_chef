package app.te.alo_chef.presentation.home.eventListener

import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.presentation.base.events.BaseEventListener

interface HomeEventListener :BaseEventListener {
    fun changeDay(date: String  ){return}
    fun openProductDetails(productId: Int)
    fun changeLike(mealId: Int){return}
    fun addToCart(homeMealsData: MealsData, addToCart: Int)
    fun openFilter(){return}
    fun openSubscriptions(){return}
}