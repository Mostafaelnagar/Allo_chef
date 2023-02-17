package app.te.alo_chef.presentation.vip

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.databinding.FragmentVipBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VipFragment : BaseFragment<FragmentVipBinding>(), HomeEventListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var vipMealsAdapter: VipMealsAdapter

    override
    fun getLayoutId() = R.layout.fragment_vip

    override
    fun setBindingVariables() {
        binding.event = this
        vipMealsAdapter = VipMealsAdapter()
        binding.rcProducts.adapter = vipMealsAdapter

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
        navigateSafe(
            VipFragmentDirections.actionVipFragmentToProductDetailsFragment(
                productId,
                publishDate
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
        navigateSafe(VipFragmentDirections.actionVipFragmentToSearchFragment())
    }
}