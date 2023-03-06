package app.te.alo_chef.presentation.subscriptions

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.payment.dto.PaymentData
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.databinding.FragmentSubscriptionsBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.showNoApiErrorAlert
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.subscriptions.adapters.SubscriptionsAdapters
import app.te.alo_chef.presentation.subscriptions.listener.SubscriptionsListener
import app.te.alo_chef.presentation.subscriptions.ui_state.SubscriptionItemUiState
import app.te.alo_chef.presentation.subscriptions.view_model.SubscriptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SubscriptionsFragment : BaseFragment<FragmentSubscriptionsBinding>(), SubscriptionsListener {

    private val viewModel: SubscriptionsViewModel by viewModels()

    override fun setBindingVariables() {
        viewModel.subscriptionsAdapters = SubscriptionsAdapters()
        binding.viewPager.adapter = viewModel.subscriptionsAdapters
        viewModel.getSubscriptions()
        listenToResult()
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.subscriptionsResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                updateSubscriptionsAdapter(it.value.data)
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
            viewModel.paymentResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        val response = it.value.data
                        openPaymentPage(
                            isSuccess = response.status,
                            payment_data = response.paymentData
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
        lifecycleScope.launchWhenResumed {
            viewModel.subscribeResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        viewModel.userResponse = it.value.data
                        openPaymentPage(
                            it.value.data.payment.paymentData,
                            it.value.data.payment.status
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

    private fun updateSubscriptionsAdapter(data: List<SubscriptionData>) {
        viewModel.subscriptionsAdapters.differ.submitList(data.map { item ->
            SubscriptionItemUiState(
                item, this
            )
        })
    }

    private fun openPaymentPage(payment_data: PaymentData, isSuccess: Boolean) {
        if (isSuccess) {
            navigateSafe(
                DeepLinks.openPayment(
                    title = getString(R.string.checkout),
                    invoiceURL = payment_data.invoiceURL,
                    responseURL = payment_data.responseURL,
                )
            )
        }
    }

    override
    fun getLayoutId() = R.layout.fragment_subscriptions

    override fun subscribeNow(subscriptionData: SubscriptionData) {
        viewModel.makeSubscribe(subscriptionData)
    }

    private fun listenToResult() {
        setFragmentResultListener(Constants.PAYMENT_SUCCESS) { _: String, bundle: Bundle ->
            if (bundle.getBoolean(Constants.PAYMENT_SUCCESS)) {
                viewModel.updateLocalUser()
                showSuccessAlert(requireActivity(), getString(R.string.subscribe_done))
            } else
                showNoApiErrorAlert(requireActivity(), getString(R.string.payment_cancelled))
        }
    }
}