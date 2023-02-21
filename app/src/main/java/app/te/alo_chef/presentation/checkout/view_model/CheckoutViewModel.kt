package app.te.alo_chef.presentation.checkout.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.checkout.dto.DeliveryTimes
import app.te.alo_chef.data.checkout.dto.promo.PromoData
import app.te.alo_chef.domain.checkout.use_case.CheckPromoCodeUseCase
import app.te.alo_chef.domain.checkout.use_case.DeliveryTimesUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.home.dialogs.PromoCodeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val checkPromoCodeUseCase: CheckPromoCodeUseCase,
    private val deliveryTimesUseCase: DeliveryTimesUseCase
) : ViewModel() {
    private val _checkPromoResponse =
        MutableStateFlow<Resource<BaseResponse<PromoData>>>(Resource.Default)
    val checkPromoResponse = _checkPromoResponse

    private val _deliveryTimesPromoResponse =
        MutableStateFlow<Resource<BaseResponse<List<DeliveryTimes>>>>(Resource.Default)
    val deliveryTimesPromoResponse = _deliveryTimesPromoResponse

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

}