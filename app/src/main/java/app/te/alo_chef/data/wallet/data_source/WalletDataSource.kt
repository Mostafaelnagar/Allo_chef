package app.te.alo_chef.data.wallet.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import javax.inject.Inject

class WalletDataSource @Inject constructor(private val apiService: WalletServices) :
    BaseRemoteDataSource() {

    suspend fun getTransactionsHistory() = safeApiCall {
        apiService.getTransactionsHistory()
    }

}