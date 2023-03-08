package app.te.alo_chef.presentation.subscriptions.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.data.subscriptions.dto.MakeSubscriptionData
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.payment.entity.PaymentRequest
import app.te.alo_chef.domain.payment.entity.SendPaymentType
import app.te.alo_chef.domain.payment.use_case.PaymentDataUseCase
import app.te.alo_chef.domain.subscriptions.entity.SubscribeRequest
import app.te.alo_chef.domain.subscriptions.use_case.SubscribeUseCase
import app.te.alo_chef.domain.subscriptions.use_case.SubscriptionsUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.subscriptions.adapters.SubscriptionsAdapters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val subscriptionsUseCase: SubscriptionsUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val paymentDataUseCase: PaymentDataUseCase,
    private val userLocalUseCase: UserLocalUseCase
) : ViewModel() {
    lateinit var subscriptionData: SubscriptionData
    lateinit var userResponse: UserResponse
    private val _paymentResponse =
        MutableStateFlow<Resource<BaseResponse<PaymentResponse>>>(Resource.Default)
    val paymentResponse = _paymentResponse

    private val _subscribeResponse =
        MutableStateFlow<Resource<BaseResponse<MakeSubscriptionData>>>(Resource.Default)
    val subscribeResponse = _subscribeResponse

    lateinit var subscriptionsAdapters: SubscriptionsAdapters

    private val _subscriptionsResponse =
        MutableStateFlow<Resource<BaseResponse<List<SubscriptionData>>>>(Resource.Default)
    val subscriptionsResponse = _subscriptionsResponse

    fun getSubscriptions() {
        viewModelScope.launch {
            _subscriptionsResponse.value = Resource.Loading
            _subscriptionsResponse.value = subscriptionsUseCase.invoke(Dispatchers.IO)
        }
    }

    fun makeSubscribe(subscriptionData: SubscriptionData) {
        this.subscriptionData = subscriptionData

        viewModelScope.launch {
            _subscribeResponse.value = Resource.Loading
            _subscribeResponse.value =
                subscribeUseCase.invoke(Dispatchers.IO, SubscribeRequest(subscriptionData.id))
        }
    }

    fun getPaymentData() {
        viewModelScope.launch {
            _paymentResponse.value = Resource.Loading
            _paymentResponse.value =
                paymentDataUseCase.getPaymentData(
                    PaymentRequest(
                        invoice_value = subscriptionData.price.toFloat(),
                        id = subscriptionData.id,
                        type = SendPaymentType.SUBSCRIPTION.type
                    ), Dispatchers.IO
                )
        }
    }

    fun updateLocalUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (this@SubscriptionsViewModel::userResponse.isInitialized) {
                userResponse.points += subscriptionData.points
                userResponse.wallet += subscriptionData.price
                userLocalUseCase.invoke(userResponse)
            }
        }
    }
}