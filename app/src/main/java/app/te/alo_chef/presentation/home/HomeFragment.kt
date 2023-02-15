package app.te.alo_chef.presentation.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.HomeDaysData
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.databinding.FragmentHomeBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.getToday
import app.te.alo_chef.presentation.home.adapters.DaysAdapter
import app.te.alo_chef.presentation.home.adapters.ProductsAdapter
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener
import app.te.alo_chef.presentation.home.ui_state.HomeDaysUiItemState
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeEventListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var daysAdapter: DaysAdapter
    private lateinit var productsAdapter: ProductsAdapter

    override
    fun getLayoutId() = R.layout.fragment_home

    override
    fun setBindingVariables() {
        binding.event = this
        daysAdapter = DaysAdapter(this)
        productsAdapter = ProductsAdapter()
        binding.rcDays.adapter = daysAdapter
        binding.rcProducts.adapter = productsAdapter
        viewModel.getHomeData(getToday())
    }

    override fun setUpViews() {
        viewModel.checkUserLogged()
        checkNotificationsPermissions(requireActivity()) {}
    }

    override
    fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.homeResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                viewModel.dayDate = it.value.data.day_date
                                updateDays(it.value.data.homeDaysDataList)
                                updateMeals(it.value.data.mealsDataList)
                            }
                            is Resource.Failure -> {
                                hideLoading()
                                handleApiError(it)
                            }
                            else -> {}
                        }
                    }
                }
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
        productsAdapter.differ.submitList(mealsDataList.map { meal ->
            MealsUiState(
                meal,
                this@HomeFragment
            )
        })
    }

    private fun updateDays(homeDaysDataList: List<HomeDaysData>) {
        daysAdapter.differ.submitList(homeDaysDataList.map { days ->
            HomeDaysUiItemState(
                days
            )
        })
    }

    override fun changeDay(date: String) {
        viewModel.filterMeals(date, "1")
    }

    override fun openProductDetails(productId: Int) {

    }

    override fun changeLike(mealId: Int) {
        if (viewModel.isLogged)
            viewModel.changeLike(mealId)
        else
            openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }

    override fun addToCart(homeMealsData: MealsData, addToCart: Int) {
        if (viewModel.isLogged) {
            if (addToCart == Constants.ADD_TO_CART_KEY)
                viewModel.addToCart(homeMealsData)
            else
                openSubscriptions()
        }

    }

    override fun openSubscriptions() {
        //TODO openSubscriptions
    }

    override fun openFilter() {
        navigateSafe(HomeFragmentDirections.actionHomeFragmentToMealsFilterDialog())
    }

}