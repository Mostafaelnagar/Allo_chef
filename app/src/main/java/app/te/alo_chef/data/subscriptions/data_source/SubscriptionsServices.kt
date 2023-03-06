package app.te.alo_chef.data.subscriptions.data_source

import app.te.alo_chef.data.subscriptions.dto.MakeSubscriptionData
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.subscriptions.entity.SubscribeRequest
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SubscriptionsServices {

    @GET("user/get-packages")
    suspend fun getSubscriptions(): BaseResponse<List<SubscriptionData>>

    @POST("user/make-subscription")
    suspend fun subscribe(
        @Body subscribeRequest: SubscribeRequest,
    ): BaseResponse<MakeSubscriptionData>

}