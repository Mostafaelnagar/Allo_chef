package app.te.alo_chef.presentation.product_details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentProductDetailsBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.data.meal_details.dto.IngredientsItem
import app.te.alo_chef.data.meal_details.dto.MealImages
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import app.te.alo_chef.presentation.home.adapters.ProductsAdapter
import app.te.alo_chef.presentation.home.ui_state.MealsUiState
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel
import app.te.alo_chef.presentation.product_details.adapters.ImageAdapter
import app.te.alo_chef.presentation.product_details.adapters.IngredientsAdapter
import app.te.alo_chef.presentation.product_details.listeners.ProductDetailsListener
import app.te.alo_chef.presentation.product_details.ui_state.OrderDetailsUiState
import app.te.alo_chef.presentation.product_details.view_model.MealDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>(),
    ProductDetailsListener {
    private val viewModel: MealDetailsViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private var handler: Handler = Handler(Looper.myLooper()!!)
    private lateinit var adapter: ImageAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override
    fun getLayoutId() = R.layout.fragment_product_details

    override
    fun setBindingVariables() {
        productsAdapter = ProductsAdapter()
        ingredientsAdapter = IngredientsAdapter()
        binding.rcIngredients.adapter = ingredientsAdapter
        binding.rcSimilar.adapter = productsAdapter
        viewModel.getDetails()
        cartViewModel.getCartCount()
    }

    override
    fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.detailsResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                viewModel.detailsUiState =
                                    OrderDetailsUiState(it.value.data, this@ProductDetailsFragment)
                                binding.uiState = viewModel.detailsUiState
                                initAdapter(
                                    viewModel.detailsUiState.mainDetails.meal?.mealImagesList
                                        ?: arrayListOf()
                                )
                                updateIngredients(
                                    viewModel.detailsUiState.mainDetails.meal?.ingredients
                                        ?: listOf()
                                )
                                updateMeals(viewModel.detailsUiState.mainDetails.otherMeals)
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

    private fun updateIngredients(ingredients: List<IngredientsItem>) {
        ingredientsAdapter.differ.submitList(ingredients)
    }

    private fun updateMeals(mealsDataList: List<MealsData>) {
        productsAdapter.differ.submitList(mealsDataList.map { meal ->
            MealsUiState(
                meal,
                this@ProductDetailsFragment
            )
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    private val runnable = Runnable {
        binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.viewPager2.setPageTransformer(transformer)
    }

    private fun initAdapter(mealImagesList: ArrayList<MealImages>) {
        adapter = ImageAdapter(mealImagesList, binding.viewPager2)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setUpTransformer()
        setUpInfiniteScroll()
    }

    private fun setUpInfiniteScroll() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
    }

    override fun openProductDetails(productId: Int, publishDate: String) {
        val bundle = Bundle()
        bundle.putInt("meal_id", productId)
        bundle.putString("date", publishDate)
        findNavController().navigate(R.id.openProductDetails, args = bundle)
    }

    override fun changeLike(mealId: Int) {
        if (homeViewModel.isLogged.value)
            homeViewModel.changeLike(mealId)
        else
            openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }

    override fun addToCart(homeMealsData: MealsData, addToCart: Int) {
        if (homeViewModel.isLogged.value) {
            if (addToCart == Constants.ADD_TO_CART_KEY)
                cartViewModel.addToCart(homeMealsData)
            else
                openSubscriptions()
        } else
            opnLogin()

    }

    private fun opnLogin() {
        openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }

    override fun openCart() {
        navigateSafe(DeepLinks.openCart(cartViewModel.cartCountFlow.value))
    }

    override fun back() {
        backToPreviousScreen()
    }
}