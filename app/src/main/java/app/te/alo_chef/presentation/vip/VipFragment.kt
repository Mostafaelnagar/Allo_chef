package app.te.alo_chef.presentation.vip

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.databinding.FragmentVipBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VipFragment : BaseFragment<FragmentVipBinding>(), HomeEventListener {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var vipMealsAdapter: VipMealsAdapter
    private val cartViewModel: CartViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_vip

    override
    fun setBindingVariables() {
        binding.event = this
        vipMealsAdapter = VipMealsAdapter()
        binding.rcProducts.adapter = vipMealsAdapter
        cartViewModel.getCartCount()

    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.isLogged.collect { isLogged ->
                if (isLogged)
                    viewModel.getVipMeals()
                else
                    checkEmptyLayout(isLogged)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.filterResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                updateMeals(it.value.data)
                            }
                            is Resource.Failure -> {
                                hideLoading()
                                handleApiError(it)
                            }
                            else -> {}
                        }
                    }
                }
            }

        }
        lifecycleScope.launchWhenResumed {
            cartViewModel.cartCountFlow.collect {
                binding.cartCount = it
            }
        }
    }

    private fun updateMeals(mealsDataList: List<MealsData>) {
        if (mealsDataList.isEmpty())
            checkEmptyLayout(true)
        else
            vipMealsAdapter.differ.submitList(mealsDataList.map { meal ->
                MealsUiState(
                    meal,
                    this@VipFragment
                )
            })
    }

    private fun checkEmptyLayout(isLogged: Boolean) {
        binding.layoutTryToLogin.container.show()
        binding.layoutTryToLogin.tvVipWarning.show()
        binding.layoutTryToLogin.tvVipWarning.text = getString(R.string.vip_warning)
        if (!isLogged)
            binding.layoutTryToLogin.tryLogin.show()
    }

    override fun openProductDetails(productId: Int, publishDate: String) {
        val bundle = Bundle()
        bundle.putInt("meal_id", productId)
        bundle.putString("date", publishDate)
        findNavController().navigate(R.id.openProductDetails, args = bundle)
    }

    override fun changeLike(mealId: Int) {
        viewModel.changeLike(mealId)
    }

    override fun addToCart(homeMealsData: MealsData, addToCart: Int) {
        if (addToCart == Constants.ADD_TO_CART_KEY) {
            showSuccessAlert(requireActivity(), getString(R.string.added_cart))
            cartViewModel.addToCart(homeMealsData)
        }

    }

    override fun tryLogin() {
        openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }

    override fun openSearch() {
        navigateSafe(VipFragmentDirections.actionVipFragmentToSearchFragment())
    }

    override fun openCart() {
        navigateSafe(DeepLinks.openCart(cartViewModel.cartCountFlow.value))
    }
}