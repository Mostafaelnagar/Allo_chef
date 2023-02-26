package app.te.alo_chef.presentation.checkout

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentChooseDeliveryBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.backToPreviousScreen
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.checkout.adapters.DeliveryTimesAdapter
import app.te.alo_chef.presentation.checkout.listener.DeliveryTimesListener
import app.te.alo_chef.presentation.checkout.view_model.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChooseDeliveryTimeFragment : BaseFragment<FragmentChooseDeliveryBinding>(),
    DeliveryTimesListener {
    private val deliveryTimesAdapter = DeliveryTimesAdapter()
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    override fun setBindingVariables() {
        binding.event = this
        binding.uiState = checkoutViewModel.checkoutUiState
        binding.rcDeliveryTimes.adapter = deliveryTimesAdapter
        checkoutViewModel.getDeliveryTimes()
    }

    override fun observeAPICall() {
        lifecycleScope.launchWhenResumed {
            checkoutViewModel.deliveryTimesPromoResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        deliveryTimesAdapter.differ.submitList(it.value.data)
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

    override
    fun getLayoutId() = R.layout.fragment_choose_delivery

    override fun confirmSelection() {
        if (deliveryTimesAdapter.lastSelected != -1) {
            checkoutViewModel.checkoutUiState.newOrderRequest.deliveryTime =
                deliveryTimesAdapter.differ.currentList[deliveryTimesAdapter.lastSelected].id
            checkoutViewModel.checkoutUiState.newOrderRequest.deliveryTimeText =
                deliveryTimesAdapter.differ.currentList[deliveryTimesAdapter.lastSelected].getTimeSlot()
            checkoutViewModel.checkoutUiState.updateDeliveryTimeText()
            backToPreviousScreen()
        }
    }

}