package app.te.alo_chef.presentation.cart

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentCartBinding
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.navigateSafe
import app.te.alo_chef.presentation.cart.adapters.CartAdapter
import app.te.alo_chef.presentation.cart.listener.CartListener
import app.te.alo_chef.presentation.cart.ui_state.ItemCartUiState
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(), CartListener {
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter = CartAdapter()

    override fun setBindingVariables() {
        binding.event = this
        initCartRecycler()
        viewModel.getCartItems()
        viewModel.getCartItemsTotal()
    }

    private fun initCartRecycler() {
        binding.recCart.adapter = cartAdapter
    }

    override fun observeAPICall() {
        lifecycleScope.launchWhenResumed {
            viewModel.cartItems.collect {
                mapCartItemsToUiState(it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.cartItemsTotalFlow.collect {
                binding.btnAddCart.text =
                    getString(R.string.checkout).plus(" ($it ${getString(R.string.coin)})")
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

    override fun openCheckout() {
        navigateSafe(CartFragmentDirections.actionCartFragmentToCheckoutFragment(viewModel.cartItemsTotalFlow.value.toFloat()))
    }

}