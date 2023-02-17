package app.te.alo_chef.data.wallet.repository

import app.te.alo_chef.data.wallet.data_source.WalletDataSource
import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.domain.wallet.repository.WalletRepository
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(private val remoteDataSource: WalletDataSource) :
    WalletRepository {

    override suspend fun getWalletTransaction(): Resource<BaseResponse<List<TransActionsHistoryItem>>> =
        remoteDataSource.getTransactionsHistory()
}