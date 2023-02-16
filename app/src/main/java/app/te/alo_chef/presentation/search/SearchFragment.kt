package app.te.alo_chef.presentation.search

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.databinding.FragmentSearchBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.home.adapters.ProductsAdapter
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), HomeEventListener {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override
    fun getLayoutId() = R.layout.fragment_search

    override
    fun setBindingVariables() {
        productsAdapter = ProductsAdapter()
        binding.rcProducts.adapter = productsAdapter
        binding.cancelAction.setOnClickListener { productsAdapter.differ.submitList(emptyList()) }
    }

    override fun setUpViews() {
        viewModel.checkUserLogged()
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch {
                delay(500)
                if (text?.isEmpty() == true)
                    productsAdapter.differ.submitList(emptyList())
                else
                    viewModel.searchMeals(text.toString())
            }
        }
    }

    override
    fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.filterResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                binding.searchProgress.show()
                            }
                            is Resource.Success -> {
                                binding.searchProgress.hide()
                                updateMeals(it.value.data)
                            }
                            is Resource.Failure -> {
                                binding.searchProgress.hide()
                                handleApiError(it)
                            }
                            else -> {}
                        }
                    }
                }
            }

        }
    }

    private fun updateMeals(mealsDataList: List<MealsData>) {
            productsAdapter.differ.submitList(mealsDataList.map { meal ->
                MealsUiState(
                    meal,
                    this@SearchFragment
                )
            })
    }

    override fun openProductDetails(productId: Int) {
//        navigateSafe(VipFragmentDirections.actionVipFragmentToProductDetailsFragment(productId))
    }

    override fun changeLike(mealId: Int) {
        viewModel.changeLike(mealId)
    }

    override fun addToCart(homeMealsData: MealsData, addToCart: Int) {
        if (addToCart == Constants.ADD_TO_CART_KEY)
            viewModel.addToCart(homeMealsData)

    }

    override fun tryLogin() {
        openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }
}