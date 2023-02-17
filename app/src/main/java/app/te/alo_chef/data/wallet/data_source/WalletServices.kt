package app.te.alo_chef.data.wallet.data_source

import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET

interface WalletServices {
    @GET("user/get-profile-data")
    suspend fun getTransactionsHistory(): BaseResponse<List<TransActionsHistoryItem>>
}