package app.te.alo_chef.presentation.auth.sign_up

import android.content.Context
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.core.notifications.notification_manager.FCMManager
import app.te.alo_chef.domain.auth.use_case.RegisterUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    val registerUiState: RegisterUiState
) :
    BaseViewModel() {
    private val _registerResponse = MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val registerResponse = _registerResponse

    fun register() {
        registerUseCase(registerUiState.request)
            .catch { }
            .onEach { result ->
                _registerResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    fun updateFireBaseToken(context: Context) {
        FCMManager.generateFCMToken(context) { token ->
            registerUiState.request.firebase_token = token
        }
    }
}