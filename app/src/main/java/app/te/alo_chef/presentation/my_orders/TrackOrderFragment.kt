package app.te.alo_chef.presentation.my_orders

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentTrackOrderBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.backToPreviousScreen
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.my_orders.ui_state.OrderItemUiState
import app.te.alo_chef.presentation.my_orders.view_model.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrackOrderFragment : BaseFragment<FragmentTrackOrderBinding>() {
    private val viewModel: OrdersViewModel by viewModels()
    private val args: TrackOrderFragmentArgs by navArgs()

    override fun setUpViews() {
        if (args.orderId == 0)
            backToPreviousScreen()
        else
            viewModel.getOrderDetails(args.orderId)
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.orderDetailsResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                binding.uiState = OrderItemUiState(
                                    it.value.data, null
                                )
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

    override
    fun getLayoutId() = R.layout.fragment_track_order
}