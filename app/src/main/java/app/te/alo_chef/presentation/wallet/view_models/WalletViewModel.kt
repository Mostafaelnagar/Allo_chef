package app.te.alo_chef.presentation.wallet.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.domain.wallet.use_case.WalletTransactionsUseCase
import app.te.alo_chef.presentation.account.AccountUiState
import app.te.alo_chef.presentation.wallet.adapters.WalletTransactionsAdapter
import app.te.alo_chef.presentation.wallet.ui_state.WalletUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletTransactionsUseCase: WalletTransactionsUseCase,
    val userLocalUseCase: UserLocalUseCase
) :
    ViewModel() {
    val walletTransactionsAdapter = WalletTransactionsAdapter()
    private val _walletTransactionsResponse =
        MutableStateFlow<Resource<BaseResponse<List<TransActionsHistoryItem>>>>(Resource.Default)
    val walletTransactionsResponse = _walletTransactionsResponse

    private val _userData = MutableStateFlow(WalletUiState())
    val userData = _userData

    fun getUserFromLocal() {
        viewModelScope.launch {
            userLocalUseCase.invoke().collect {
                val uiState = WalletUiState()
                uiState.updateUi(it)
                _userData.value = uiState
            }
        }
    }
    fun getWalletTransactions() {
        viewModelScope.launch {
            _walletTransactionsResponse.value = Resource.Loading
            _walletTransactionsResponse.value = walletTransactionsUseCase.invoke(Dispatchers.IO)
        }
    }
}