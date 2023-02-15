package app.te.alo_chef.presentation.account

import androidx.lifecycle.viewModelScope
import app.te.alo_chef.domain.account.use_case.AccountUseCases
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
    val userLocalUseCase: UserLocalUseCase
) : BaseViewModel() {

    private val _logOuResponse = MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)

    private val _userData = MutableStateFlow(AccountUiState())
    val userData = _userData

    fun getUserFromLocal() {
        viewModelScope.launch {
            userLocalUseCase.invoke().collect {
                val uiState = AccountUiState()
                uiState.updateUi(it)
                _userData.value = uiState
            }
        }
    }

    fun logOut() {
        accountUseCases.logOutUseCase()
            .onEach { result ->
                _logOuResponse.value = result
            }
            .launchIn(viewModelScope)
    }

}