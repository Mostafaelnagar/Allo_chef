package app.te.alo_chef.presentation.checkout.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.checkout.dto.DeliveryTimes
import app.te.alo_chef.data.checkout.dto.promo.PromoData
import app.te.alo_chef.data.general.dto.config.GeneralConfig
import app.te.alo_chef.data.payment.dto.PaymentData
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.domain.checkout.use_case.CheckPromoCodeUseCase
import app.te.alo_chef.domain.checkout.use_case.CheckoutUseCase
import app.te.alo_chef.domain.checkout.use_case.DeliveryTimesUseCase
import app.te.alo_chef.domain.general.use_case.local.GetSavedGeneralConfig
import app.te.alo_chef.domain.payment.use_case.PaymentDataUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.PaymentBaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.PaymentTypes
import app.te.alo_chef.presentation.checkout.ui_state.CheckoutUiState
import app.te.alo_chef.presentation.home.dialogs.PromoCodeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val checkPromoCodeUseCase: CheckPromoCodeUseCase,
    private val deliveryTimesUseCase: DeliveryTimesUseCase,
    val checkoutUiState: CheckoutUiState,
    private val paymentDataUseCase: PaymentDataUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val savedGeneralConfigUseCase: GetSavedGeneralConfig
) : ViewModel() {

    private val _checkoutResponse =
        MutableStateFlow<Resource<BaseResponse<UserResponse>>>(Resource.Default)
    val checkoutResponse = _checkoutResponse

    private val _paymentResponse =
        MutableStateFlow<Resource<PaymentBaseResponse<PaymentData>>>(Resource.Default)
    val paymentResponse = _paymentResponse

    private val _checkPromoResponse =
        MutableStateFlow<Resource<BaseResponse<PromoData>>>(Resource.Default)
    val checkPromoResponse = _checkPromoResponse

    private val _deliveryTimesPromoResponse =
        MutableStateFlow<Resource<BaseResponse<List<DeliveryTimes>>>>(Resource.Default)
    val deliveryTimesPromoResponse = _deliveryTimesPromoResponse

    private val _savedGeneralConfig =
        MutableStateFlow(GeneralConfig())
    val savedGeneralConfig = _savedGeneralConfig

    val promoCodeRequest = PromoCodeRequest()

    fun checkPromoCode() {
        viewModelScope.launch {
            _checkPromoResponse.value = Resource.Loading
            _checkPromoResponse.value =
                checkPromoCodeUseCase.checkPromoCode(promoCodeRequest.code, Dispatchers.IO)
        }
    }

    fun getDeliveryTimes() {
        viewModelScope.launch {
            _deliveryTimesPromoResponse.value = Resource.Loading
            _deliveryTimesPromoResponse.value =
                deliveryTimesUseCase.getDeliveryTimes(Dispatchers.IO)
        }
    }

    fun submitOrder(cartMeals: List<MealCart>) {
        checkoutUiState.newOrderRequest.mealCarts = cartMeals
        viewModelScope.launch {
            _checkoutResponse.value = Resource.Loading
            _checkoutResponse.value =
                checkoutUseCase.checkout(checkoutUiState.newOrderRequest, Dispatchers.IO)
        }
    }

    fun getPaymentData() {
        viewModelScope.launch {
            _paymentResponse.value = Resource.Loading
//            _paymentResponse.value =
//                paymentDataUseCase.getPaymentData(checkoutUiState.orderTotal, Dispatchers.IO)
        }
    }

    fun getGeneralConfig() {
        viewModelScope.launch {
            savedGeneralConfigUseCase.getLocalConfig(Dispatchers.IO).collect {
                _savedGeneralConfig.value = it
            }
        }
    }
}