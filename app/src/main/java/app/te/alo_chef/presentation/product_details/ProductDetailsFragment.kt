package app.te.alo_chef.presentation.product_details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentProductDetailsBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import android.os.Handler
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel
import app.te.alo_chef.presentation.product_details.adapters.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var adapter: ImageAdapter

    override
    fun getLayoutId() = R.layout.fragment_product_details

    override
    fun setBindingVariables() {
//        productsAdapter = ProductsAdapter(this)
//        viewModel.getHomeData(1)
    }

    override fun setUpViews() {
        initAdapter()
        setUpTransformer()
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
//                                categoriesAdapter.differ.submitList(it.value.data.categories.map { catItem ->
//                                    CategoriesUiItemState(
//                                        catItem
//                                    )
//                                })
//                                binding.rcOffers.setUpAdapter(categoriesAdapter, "2", "1")
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

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
    }

    private fun initAdapter() {

    }
}