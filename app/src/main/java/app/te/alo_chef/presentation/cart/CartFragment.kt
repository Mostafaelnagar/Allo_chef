package app.te.alo_chef.presentation.cart

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentCartBinding
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.hide
import app.te.alo_chef.presentation.base.extensions.navigateSafe
import app.te.alo_chef.presentation.base.extensions.openIntentActivity
import app.te.alo_chef.presentation.base.extensions.show
import app.te.alo_chef.presentation.cart.adapters.CartAdapter
import app.te.alo_chef.presentation.cart.listener.CartListener
import app.te.alo_chef.presentation.cart.ui_state.ItemCartUiState
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(), CartListener {
    private val viewModel: CartViewModel by activityViewModels()
    private val cartAdapter = CartAdapter()
    private val args: CartFragmentArgs by navArgs()
    private var cartTotal: Float = 0f
    override fun setBindingVariables() {
        binding.event = this
        initCartRecycler()
    }

    private fun initCartRecycler() {
        binding.recCart.adapter = cartAdapter
    }

    override fun observeAPICall() {
        lifecycleScope.launchWhenResumed {
            viewModel.isLogged.collect { isLogged ->
                if (isLogged && args.cartCount > 0) {
                    viewModel.getCartItems()
                    viewModel.getCartItemsTotal()
                } else
                    checkEmptyLayout(isLogged)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.cartItems.collect {
                mapCartItemsToUiState(it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.cartItemsTotalFlow.collect {
                if (it == null || it == "0.0")
                    checkEmptyLayout(true)
                else {
                    binding.btnAddCart.show()
                    cartTotal = it.toFloat()
                    binding.btnAddCart.text =
                        getString(R.string.checkout).plus(" ($it ${getString(R.string.coin)})")
                }
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

    private fun checkEmptyLayout(isLogged: Boolean) {
        binding.layoutTryToLogin.container.show()
        binding.layoutTryToLogin.tvVipWarning.show()
        binding.layoutTryToLogin.icEmptyIcon.show()
        binding.layoutTryToLogin.icEmptyIcon.setImageResource(R.drawable.empty_cart)
        binding.layoutTryToLogin.tvVipWarning.text = getString(R.string.cart_empty)
        binding.btnAddCart.hide()
        if (!isLogged) {
            binding.layoutTryToLogin.tryLogin.show()
        }
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
        navigateSafe(CartFragmentDirections.actionCartFragmentToCheckoutFragment(cartTotal))
    }

    override fun tryLogin() {
        openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }

}