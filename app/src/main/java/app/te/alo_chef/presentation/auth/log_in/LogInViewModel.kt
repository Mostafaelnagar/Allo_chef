package app.te.alo_chef.presentation.auth.log_in

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.core.notifications.notification_manager.FCMManager
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.LogInRequest
import app.te.alo_chef.domain.auth.entity.request.SocialLogInRequest
import app.te.alo_chef.domain.auth.use_case.LogInUseCase
import app.te.alo_chef.domain.auth.use_case.SocialLogInUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val socialLogInUseCase: SocialLogInUseCase,
    val userLocalUseCase: UserLocalUseCase

) : BaseViewModel() {

    var request = LogInRequest()
    private val _logInResponse =
        MutableStateFlow<Resource<BaseResponse<UserResponse>>>(Resource.Default)
    val logInResponse = _logInResponse


    fun onLogInClicked() {
        logInUseCase(request)
            .catch { exception ->
            }
            .onEach { result ->
                _logInResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    fun updateFireBaseToken(context: Context) {
        FCMManager.generateFCMToken(context) { token ->
            request.token = token
        }
    }

    fun socialLogin(request: SocialLogInRequest) {
        socialLogInUseCase(request)
            .catch { exception ->
                Log.e(
                    "onLogInClicked",
                    "onLogInClicked: ${exception.printStackTrace()}"
                )
            }
            .onEach { result ->
                _logInResponse.value = result
            }
            .launchIn(viewModelScope)
    }


}