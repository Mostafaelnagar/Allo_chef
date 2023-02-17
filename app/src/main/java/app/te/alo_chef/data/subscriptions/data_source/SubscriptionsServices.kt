package app.te.alo_chef.data.subscriptions.data_source

import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET

interface SubscriptionsServices {
    @GET("user/get-packages")
    suspend fun getSubscriptions(): BaseResponse<List<SubscriptionData>>
}