package app.te.alo_chef.presentation.my_orders

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.databinding.FragmentPreviousOrdersBinding
import app.te.alo_chef.domain.orders.entity.OrderHistoryType
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.base.extensions.navigateSafe
import app.te.alo_chef.presentation.base.extensions.show
import app.te.alo_chef.presentation.my_orders.adapters.OrdersAdapter
import app.te.alo_chef.presentation.my_orders.listener.OrdersListener
import app.te.alo_chef.presentation.my_orders.ui_state.OrderItemUiState
import app.te.alo_chef.presentation.my_orders.view_model.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PreviousOrdersFragment : BaseFragment<FragmentPreviousOrdersBinding>(), OrdersListener {

    private val viewModel: OrdersViewModel by viewModels()

    override fun setBindingVariables() {
        viewModel.ordersAdapter = OrdersAdapter()
        binding.rcOrder.adapter = viewModel.ordersAdapter
        viewModel.getMyOrders(OrderHistoryType.PREVIOUS.type)
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.ordersResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                updateOrderAdapter(it.value.data)
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

    private fun updateOrderAdapter(data: List<MyOrdersData>) {
        if (data.isEmpty())
            updateEmptyLayout()
        viewModel.ordersAdapter.differ.submitList(data.map { item ->
            OrderItemUiState(
                item, this
            )
        })
    }

    private fun updateEmptyLayout() {
        binding.layoutTryToLogin.container.show()
        binding.layoutTryToLogin.tvVipWarning.show()
        binding.layoutTryToLogin.icEmptyIcon.show()
        binding.layoutTryToLogin.icEmptyIcon.setImageResource(R.drawable.ic_empty_orders)
        binding.layoutTryToLogin.tvVipWarning.text = getString(R.string.empty_orders)
    }

    override
    fun getLayoutId() = R.layout.fragment_previous_orders

    override fun openOrderDetails(selectedItemUiState: OrderItemUiState) {
        navigateSafe(DeepLinks.openOrderDetails(selectedItemUiState.myOrdersData.id))
    }


}