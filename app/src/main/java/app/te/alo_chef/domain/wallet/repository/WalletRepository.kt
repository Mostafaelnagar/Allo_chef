package app.te.alo_chef.domain.wallet.repository

import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface WalletRepository {
    suspend fun getWalletTransaction(): Resource<BaseResponse<List<TransActionsHistoryItem>>>
}