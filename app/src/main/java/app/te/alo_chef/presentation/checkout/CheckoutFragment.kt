package app.te.alo_chef.presentation.checkout

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentCheckoutBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.PaymentTypes
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.base.extensions.navigateSafe
import app.te.alo_chef.presentation.checkout.adapters.CartDeliveryDatesAdapter
import app.te.alo_chef.presentation.checkout.adapters.PaymentTypesAdapter
import app.te.alo_chef.presentation.checkout.ui_state.CheckoutUiState
import app.te.alo_chef.presentation.checkout.ui_state.ItemPayment
import app.te.alo_chef.presentation.cart.view_model.CartViewModel
import app.te.alo_chef.presentation.checkout.listener.CheckoutListener
import app.te.alo_chef.presentation.checkout.view_model.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(), CheckoutListener {
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    private val args: CheckoutFragmentArgs by navArgs()
    private var datesSize: Int = 0
    private val viewModel: CartViewModel by viewModels()
    private val paymentTypesAdapter = PaymentTypesAdapter()
    private val cartDeliveryDatesAdapter = CartDeliveryDatesAdapter(this)

    private lateinit var checkoutUiState: CheckoutUiState

    override fun setBindingVariables() {
        checkoutUiState = CheckoutUiState(requireActivity())
        binding.uiState = checkoutUiState
        binding.event = this
        updateCartItemsTotal()
        initCartDeliveryDatesRecycler()
        viewModel.getDeliveryDates()
        viewModel.getWalletAndPoints()
    }

    private fun updateCartItemsTotal() {
        checkoutUiState.updateCartItemTotal(args.cartTotal)
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
                checkoutUiState.updateDeliveryFees(delivery.second * datesSize)
                checkoutUiState.deliveryRegion =
                    delivery.first.ifEmpty { getString(R.string.pickup_your_location) }
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.walletPointFlow.collect { data ->
                setUpPaymentTypesList(data.first, data.second)
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
                        checkoutUiState.updateDiscount(it.value.data.discount)
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

    private fun setUpPaymentTypesList(points: Long, wallet: Float) {
        val paymentList: MutableList<ItemPayment> = mutableListOf()
        paymentList.add(
            ItemPayment(
                PaymentTypes.WALLET.ordinal,
                getString(R.string.tv_wallet),
                R.drawable.ic_wallet,
                "$wallet ${getString(R.string.coin)}",
                ""
            )
        )
        paymentList.add(
            ItemPayment(
                PaymentTypes.POINTS.ordinal,
                getString(R.string.point),
                R.drawable.ic_point_payment,
                "$points ${getString(R.string.point)}",
                ""
            )
        )
        paymentList.add(
            ItemPayment(
                PaymentTypes.ONLINE.ordinal,
                getString(R.string.online),
                R.drawable.ic_online,
                "",
                ""
            )
        )
        paymentTypesAdapter.differ.submitList(paymentList)
        binding.rcDeliveryPayment.adapter = paymentTypesAdapter
    }

    override fun onResume() {
        super.onResume()
        checkoutUiState.updateDeliveryTimeText(checkoutViewModel.newOrderRequest.deliveryTimeText)
    }
    override
    fun getLayoutId() = R.layout.fragment_checkout

    override fun callSavedLocation(dates: Int) {
        datesSize = dates
        viewModel.getDeliveryFeeFromSavedLocation()
    }

    override fun changeDeliveryAddress() {
        findNavController().navigate(R.id.openMyLocation)
    }

    override fun openPromoDialog() {
        navigateSafe(CheckoutFragmentDirections.actionCheckoutFragmentToPromoCodeDialog())
    }

    override fun openDeliveryTimes() {
        navigateSafe(CheckoutFragmentDirections.actionCheckoutFragmentToChooseDeliveryTimeFragment())
    }
}