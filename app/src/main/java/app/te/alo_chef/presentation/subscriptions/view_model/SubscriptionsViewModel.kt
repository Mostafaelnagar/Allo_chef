package app.te.alo_chef.presentation.subscriptions.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
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
class SubscriptionsViewModel @Inject constructor(private val subscriptionsUseCase: SubscriptionsUseCase) :
    ViewModel() {
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
}