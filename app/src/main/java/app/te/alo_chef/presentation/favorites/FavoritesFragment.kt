package app.te.alo_chef.presentation.favorites

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.databinding.FragmentFavoritesBinding
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
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(), HomeEventListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override
    fun getLayoutId() = R.layout.fragment_favorites

    override
    fun setBindingVariables() {
        binding.event = this
        productsAdapter = ProductsAdapter()
        binding.rcProducts.adapter = productsAdapter

    }

    override fun setUpViews() {
        viewModel.checkUserLogged()
    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.isLogged.collect { isLogged ->
                if (isLogged)
                    viewModel.getFavoritesMeals()
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
    }

    private fun updateMeals(mealsDataList: List<MealsData>) {
        if (mealsDataList.isEmpty())
            checkEmptyLayout(true)
        else
            productsAdapter.differ.submitList(mealsDataList.map { meal ->
                MealsUiState(
                    meal,
                    this@FavoritesFragment
                )
            })
    }

    private fun checkEmptyLayout(isLogged: Boolean) {
        binding.layoutTryToLogin.container.show()
        binding.layoutTryToLogin.tvVipWarning.show()
        binding.layoutTryToLogin.icEmptyIcon.show()
        binding.layoutTryToLogin.tvVipWarning.text = getString(R.string.empty_favorites)
        if (!isLogged)
            binding.layoutTryToLogin.tryLogin.show()
    }

    override fun openProductDetails(productId: Int) {
        navigateSafe(
            FavoritesFragmentDirections.actionFavoriteFragmentToProductDetailsFragment(
                productId
            )
        )
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

    override fun openSearch() {
        navigateSafe(FavoritesFragmentDirections.actionFavoriteFragmentToSearchFragment())
    }
}