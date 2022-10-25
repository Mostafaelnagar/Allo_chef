package app.te.architecture.presentation.auth.forgot_password

import androidx.lifecycle.viewModelScope
import app.te.architecture.domain.auth.entity.request.ForgetPasswordRequest
import app.te.architecture.domain.auth.use_case.ForgetPasswordUseCase
import app.te.architecture.domain.utils.BaseResponse
import app.te.architecture.domain.utils.Resource
import app.te.architecture.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
  private val forgetPasswordUseCase: ForgetPasswordUseCase
) :
  BaseViewModel() {
  var request = ForgetPasswordRequest()
  private val _forgetPasswordResponse =
    MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
  val forgetPasswordResponse = _forgetPasswordResponse

  fun sendCode() {
    forgetPasswordUseCase(request)
      .onEach { result ->
        _forgetPasswordResponse.value = result
      }
      .launchIn(viewModelScope)
  }
}