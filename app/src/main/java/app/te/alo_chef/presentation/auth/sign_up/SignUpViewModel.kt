package app.te.alo_chef.presentation.auth.sign_up

import androidx.lifecycle.viewModelScope
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
    private val registerUseCase: RegisterUseCase
    ) :
    BaseViewModel() {
    lateinit var registerUiState: RegisterUiState
    private val _registerResponse = MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val registerResponse = _registerResponse

    fun register() {
        registerUseCase(registerUiState.request)
            .catch { exception -> }
            .onEach { result ->
                _registerResponse.value = result
            }
            .launchIn(viewModelScope)
    }

}