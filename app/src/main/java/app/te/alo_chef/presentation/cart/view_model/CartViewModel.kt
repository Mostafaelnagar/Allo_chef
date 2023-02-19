package app.te.alo_chef.presentation.cart.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.domain.cart.use_case.AddToCartUseCase
import app.te.alo_chef.domain.cart.use_case.EmptyUseCase
import app.te.alo_chef.domain.cart.use_case.GetCartCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartCountUseCase: GetCartCountUseCase,
    private val addToCartCountUseCase: AddToCartUseCase,
    private val emptyUseCase: EmptyUseCase
) : ViewModel() {
    private val _cartCountFlow =
        MutableStateFlow(0)
    val cartCountFlow = _cartCountFlow

    fun getCartCount() {
        viewModelScope.launch {
            cartCountUseCase.getCartCount(Dispatchers.IO).collect {
                _cartCountFlow.value = it
            }
        }
    }

    fun addToCart(mealsData: MealsData) {
        val mealCart = MealCart(
            image = mealsData.image,
            description = mealsData.description,
            name = mealsData.name,
            priceItem = mealsData.priceAfter.toFloat(),
            priceAfter = mealsData.priceAfter,
            product_id = mealsData.id,
            publishDate = mealsData.publishDate,
            points = mealsData.points,
            quantity = mealsData.quantity
        )
        viewModelScope.launch {
            addToCartCountUseCase.addToCart(Dispatchers.IO, mealCart)
        }
    }

    fun emptyCart() {
        viewModelScope.launch {
            emptyUseCase.emptyCart(Dispatchers.IO)
        }
    }
}