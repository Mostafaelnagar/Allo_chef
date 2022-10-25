package app.te.architecture.presentation.profile.changePassword

import android.util.Log
import androidx.lifecycle.viewModelScope
import app.te.architecture.domain.account.use_case.UserLocalUseCase
import app.te.architecture.domain.auth.use_case.ChangePasswordUseCase
import app.te.architecture.domain.profile.entity.UpdatePassword
import app.te.architecture.domain.utils.BaseResponse
import app.te.architecture.domain.utils.Resource
import app.te.architecture.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    val userLocalUseCase: UserLocalUseCase
) : BaseViewModel() {
    var request = UpdatePassword()
    private val _changePasswordResponse =
        MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val changePasswordResponse = _changePasswordResponse

    fun updatePassword() {
        changePasswordUseCase.invoke(request)
            .catch { exception -> Log.e("changePassword", "changePassword: " + exception.message) }
            .onEach { result ->
                _changePasswordResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    fun changePassword(phone: String) {
        request.phone = phone
        changePasswordUseCase.authChangePassword(request)
            .catch { exception -> Log.e("changePassword", "changePassword: " + exception.message) }
            .onEach { result ->
                _changePasswordResponse.value = result
            }
            .launchIn(viewModelScope)
    }

}