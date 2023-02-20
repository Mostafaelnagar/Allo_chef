package app.te.alo_chef.presentation.cart.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.domain.cart.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val cartCountUseCase: GetCartCountUseCase,
    private val addToCartCountUseCase: AddToCartUseCase,
    private val deleteItemCartUseCase: DeleteItemCartUseCase,
    private val updateQuantityCartUseCase: UpdateQuantityCartUseCase,
    private val cartItemsTotalUseCase: GetCartItemsTotalUseCase,
    private val deliveryDatesTotalUseCase: GetDeliveryDatesTotalUseCase,
    private val emptyUseCase: EmptyUseCase,
    private val userLocalUseCase: UserLocalUseCase
) : ViewModel() {
    private val _cartCountFlow =
        MutableStateFlow(0)
    val cartCountFlow = _cartCountFlow

    private val _deliveryFeeFlow =
        MutableStateFlow(Pair("", 0f))
    val deliveryFeeFlow = _deliveryFeeFlow

    private val _cartItemsTotalFlow =
        MutableStateFlow("0.0")
    val cartItemsTotalFlow = _cartItemsTotalFlow

    private val _cartDeliveryDates =
        MutableStateFlow<List<String>>(listOf())
    val cartDeliveryDates = _cartDeliveryDates

    private val _cartItems =
        MutableStateFlow<List<MealCart>>(listOf())
    val cartItems = _cartItems

    fun getCartItems() {
        viewModelScope.launch {
            getCartUseCase.getCart(Dispatchers.IO).collect {
                _cartItems.value = it
            }
        }
    }

    fun getCartItemsTotal() {
        viewModelScope.launch {
            cartItemsTotalUseCase.getCartItemsTotal(Dispatchers.IO).collect {
                _cartItemsTotalFlow.value = it
            }
        }
    }

    fun getCartCount() {
        viewModelScope.launch {
            cartCountUseCase.getCartCount(Dispatchers.IO).collect {
                _cartCountFlow.value = it
            }
        }
    }

    fun getDeliveryDates() {
        viewModelScope.launch {
            deliveryDatesTotalUseCase.getDeliveryDates(Dispatchers.IO).collect {
                _cartDeliveryDates.value = it
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

    fun deleteItemFromCart(roomId: Int) {
        viewModelScope.launch {
            deleteItemCartUseCase.deleteItemCart(Dispatchers.IO, roomId)
        }
    }

    fun updateProductQuantity(quantity: Int, productId: Int) {
        viewModelScope.launch {
            updateQuantityCartUseCase.updateQuantityCart(
                Dispatchers.IO,
                productId = productId,
                quantity = quantity
            )
        }
    }

    fun emptyCart() {
        viewModelScope.launch {
            emptyUseCase.emptyCart(Dispatchers.IO)
        }
    }

    fun getDeliveryFeeFromSavedLocation() {
        viewModelScope.launch {
            userLocalUseCase.getSavedLocationFlow().collect {
                _deliveryFeeFlow.value = Pair(first = it.regionName, second = it.delivery)
            }
        }
    }
}