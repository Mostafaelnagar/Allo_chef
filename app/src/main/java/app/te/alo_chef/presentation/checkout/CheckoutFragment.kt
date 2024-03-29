package app.te.alo_chef.presentation.checkout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import app.te.alo_chef.R
import app.te.alo_chef.data.general.dto.config.GeneralConfig
import app.te.alo_chef.data.payment.dto.PaymentData
import app.te.alo_chef.databinding.FragmentCheckoutBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.PaymentTypes
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.showNoApiErrorAlert
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.checkout.adapters.CartDeliveryDatesAdapter
import app.te.alo_chef.presentation.checkout.adapters.PaymentTypesAdapter
import app.te.alo_chef.presentation.checkout.ui_state.ItemPayment
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import app.te.alo_chef.presentation.checkout.listener.CheckoutListener
import app.te.alo_chef.presentation.checkout.view_model.CheckoutViewModel
import app.te.alo_chef.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(), CheckoutListener {
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()
    private val args: CheckoutFragmentArgs by navArgs()
    private var datesSize: Int = 0
    private val viewModel: CartViewModel by activityViewModels()
    private val paymentTypesAdapter = PaymentTypesAdapter(this)
    private val cartDeliveryDatesAdapter = CartDeliveryDatesAdapter(this)
    override fun setBindingVariables() {
        binding.uiState = checkoutViewModel.checkoutUiState
        binding.event = this
        updateCartItemsTotal()
        initCartDeliveryDatesRecycler()
        viewModel.getDeliveryDates()
        viewModel.getWalletAndPoints()
        listenToResult()
        checkoutViewModel.getGeneralConfig()
    }

    private fun updateCartItemsTotal() {
        checkoutViewModel.checkoutUiState.updateCartItemTotal(args.cartTotal)
    }

    private fun initCartDeliveryDatesRecycler() {
        binding.rcDelivery.adapter = cartDeliveryDatesAdapter
    }

    override fun observeAPICall() {
        lifecycleScope.launchWhenResumed {
            viewModel.cartDeliveryDates.collect {
                cartDeliveryDatesAdapter.differ.submitList(it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.deliveryFeeFlow.collect { delivery ->
                checkoutViewModel.checkoutUiState.updateLocation(delivery, datesSize)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.walletPointFlow.collect { data ->
                checkoutViewModel.checkoutUiState.totalPoints = data.first
                checkoutViewModel.checkoutUiState.totalWallet = data.second
            }
        }
        lifecycleScope.launchWhenResumed {
            checkoutViewModel.savedGeneralConfig.collect { data ->
                setUpPaymentTypesList(data)
            }
        }
        lifecycleScope.launchWhenResumed {
            checkoutViewModel.checkPromoResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }

                    is Resource.Success -> {
                        hideLoading()
                        checkoutViewModel.checkoutUiState.updateDiscount(it.value.data)
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
            checkoutViewModel.paymentResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }

                    is Resource.Success -> {
                        hideLoading()
                        val response = it.value
                        openPaymentPage(
                            isSuccess = response.status,
                            payment_data = response.data
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
            checkoutViewModel.checkoutResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }

                    is Resource.Success -> {
                        hideLoading()
                        val response = it.value.data
                        if (response.payment != null)
                            it.value.data.payment?.paymentData?.let { it1 ->
                                openPaymentPage(
                                    isSuccess = it.value.data.payment.status,
                                    payment_data = it1
                                )
                            }
                        else
                            openMyOrders()
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

    private fun openMyOrders() {
        showSuccessAlert(requireActivity(), getString(R.string.order_received))
        viewModel.emptyCart()
        lifecycleScope.launchWhenResumed {
            delay(500)
            openActivityAndClearStack(HomeActivity::class.java)
        }

    }

    private fun setUpPaymentTypesList(data: GeneralConfig) {
        val paymentList: MutableList<ItemPayment> = mutableListOf()
        data.paymentWays.forEach { paymentItem ->
            if (paymentItem.id == PaymentTypes.WALLET.paymentType)
                paymentList.add(
                    ItemPayment(
                        PaymentTypes.WALLET.paymentType,
                        paymentItem.name,
                        R.drawable.ic_wallet,
                        amount = "${checkoutViewModel.checkoutUiState.totalWallet} ${getString(R.string.coin)}",
                        points = "${checkoutViewModel.checkoutUiState.totalPoints * data.setting.pointEqualityInEgp} ${
                            getString(
                                R.string.coin
                            )
                        }",
                        pointVisibility = View.VISIBLE
                    )
                )
            if (paymentItem.id == PaymentTypes.ONLINE.paymentType)
                paymentList.add(
                    ItemPayment(
                        PaymentTypes.ONLINE.paymentType,
                        paymentItem.name,
                        R.drawable.ic_online,
                        amount = ""
                    )
                )
            if (paymentItem.id == PaymentTypes.CASH.paymentType)
                paymentList.add(
                    ItemPayment(
                        PaymentTypes.CASH.paymentType,
                        paymentItem.name,
                        R.drawable.ic_cash,
                        amount = ""
                    )
                )
        }
        paymentTypesAdapter.differ.submitList(paymentList)
        binding.rcDeliveryPayment.adapter = paymentTypesAdapter
    }

    override
    fun getLayoutId() = R.layout.fragment_checkout

    override fun callSavedLocation(dates: Int) {
        datesSize = dates
        viewModel.getDeliveryFeeFromSavedLocation()
    }

    override fun changeDeliveryAddress() {
        navigateSafe(DeepLinks.LOCATIONS_LINK)
    }

    override fun openPromoDialog() {
        navigateSafe(CheckoutFragmentDirections.actionCheckoutFragmentToPromoCodeDialog())
    }

    override fun openDeliveryTimes() {
        navigateSafe(CheckoutFragmentDirections.actionCheckoutFragmentToChooseDeliveryTimeFragment())
    }

    override fun finishOrder() {
        checkoutViewModel.checkoutUiState.checkoutPreValidation(
            showValidationError = { message ->
                showNoApiErrorAlert(requireActivity(), message)
            }, openTime = {
                openDeliveryTimes()
            },
            openPayment = {
                checkoutViewModel.getPaymentData()
            },
            finishOrder = { checkoutViewModel.submitOrder(viewModel.cartItems.value) }
        )

    }

    override fun onPaymentChange(paymentId: Int) {
        checkoutViewModel.checkoutUiState.newOrderRequest.paymentMethod = paymentId
    }

    override fun openPaymentPage(payment_data: PaymentData, isSuccess: Boolean) {
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

    private fun listenToResult() {
        setFragmentResultListener(Constants.PAYMENT_SUCCESS) { _: String, bundle: Bundle ->
            if (bundle.getBoolean(Constants.PAYMENT_SUCCESS)) {
                bundle.getString(Constants.PAYMENT_ID)
                    ?.let { checkoutViewModel.paymentCallBack(it) }
                openMyOrders()
            } else {
                showNoApiErrorAlert(requireActivity(), getString(R.string.payment_cancelled))
            }
        }
    }
}