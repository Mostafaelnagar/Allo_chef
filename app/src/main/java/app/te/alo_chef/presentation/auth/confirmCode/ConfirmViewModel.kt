package app.te.alo_chef.presentation.auth.confirmCode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.ForgetPasswordRequest
import app.te.alo_chef.domain.auth.entity.request.RegisterRequest
import app.te.alo_chef.domain.auth.use_case.ResendUseCase
import app.te.alo_chef.domain.auth.use_case.VerifyAccountUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import app.te.alo_chef.presentation.base.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val verifyAccountUseCase: VerifyAccountUseCase,
    private val resendUseCase: ResendUseCase,
    val savedStateHandle: SavedStateHandle
) :
    BaseViewModel() {
    var forgetRequest = ForgetPasswordRequest()

    private val _verifyResponse =
        MutableStateFlow<Resource<BaseResponse<UserResponse>>>(Resource.Default)
    val verifyResponse = _verifyResponse
    private val _verifyForgetResponse =
        MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val verifyForgetResponse = _verifyForgetResponse
    private val _resendResponse = MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val resendResponse = _resendResponse

    init {
        savedStateHandle.get<String>("email")?.let { email ->
            forgetRequest.phone = email
        }

    }

    fun verifyAccount() {
        if (forgetRequest.type == Constants.VERIFY)
            verifyAccountUseCase.verifyAccount(forgetRequest)
                .onEach { result ->
                    _verifyResponse.value = result
                }
                .launchIn(viewModelScope)
        else {
            verifyAccountUseCase.verifyPassword(forgetRequest)
                .onEach { result ->
                    _verifyForgetResponse.value = result
                }
                .launchIn(viewModelScope)
        }
    }

    fun resendCode() {
        resendUseCase(forgetRequest)
            .onEach { result ->
                _resendResponse.value = result
            }
            .launchIn(viewModelScope)
    }

}