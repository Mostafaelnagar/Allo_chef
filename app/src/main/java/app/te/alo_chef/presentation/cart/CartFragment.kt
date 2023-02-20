package app.te.alo_chef.presentation.cart

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentCartBinding
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.cart.adapters.CartAdapter
import app.te.alo_chef.presentation.cart.adapters.CartDeliveryDatesAdapter
import app.te.alo_chef.presentation.cart.listener.CartListener
import app.te.alo_chef.presentation.cart.ui_state.CheckoutUiState
import app.te.alo_chef.presentation.cart.ui_state.ItemCartUiState
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(), CartListener {
    private var datesSize: Int = 0
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter = CartAdapter()
    private val cartDeliveryDatesAdapter = CartDeliveryDatesAdapter(this)

    private lateinit var checkoutUiState: CheckoutUiState

    override fun setBindingVariables() {
        checkoutUiState = CheckoutUiState(requireActivity())
        binding.uiState = checkoutUiState
        binding.event = this
        initCartRecycler()
        initCartDeliveryDatesRecycler()
        viewModel.getCartItems()
        viewModel.getCartItemsTotal()
        viewModel.getDeliveryDates()
    }

    private fun initCartRecycler() {
        binding.recCart.adapter = cartAdapter
    }

    private fun initCartDeliveryDatesRecycler() {
        binding.rcDelivery.adapter = cartDeliveryDatesAdapter
    }

    override fun observeAPICall() {
        lifecycleScope.launchWhenResumed {
            viewModel.cartItems.collect {
                mapCartItemsToUiState(it)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.cartItemsTotalFlow.collect {
                checkoutUiState.updateCartItemTotal(it)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.cartDeliveryDates.collect {
                cartDeliveryDatesAdapter.differ.submitList(it)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.deliveryFeeFlow.collect { delivery ->
                checkoutUiState.updateDeliveryFees(delivery.second * datesSize)
                checkoutUiState.deliveryRegion =
                    delivery.first.ifEmpty { getString(R.string.pickup_your_location) }
            }
        }

    }

    private fun mapCartItemsToUiState(mealCarts: List<MealCart>) {
        val cartItems: MutableList<ItemCartUiState> = mutableListOf()
        mealCarts.map { mealCart ->
            cartItems.add(ItemCartUiState(mealCart, this))
        }
        cartAdapter.differ.submitList(cartItems)
    }

    override
    fun getLayoutId() = R.layout.fragment_cart

    override fun updateProductQuantity(productId: Int, quantity: Int) {
        viewModel.updateProductQuantity(quantity = quantity, productId = productId)
    }

    override fun deleteItem(roomId: Int) {
        viewModel.deleteItemFromCart(roomId)
    }

    override fun callSavedLocation(dates: Int) {
        datesSize = dates
        viewModel.getDeliveryFeeFromSavedLocation()
    }

    override fun changeDeliveryAddress() {
        findNavController().navigate(R.id.openMyLocation)
    }
}