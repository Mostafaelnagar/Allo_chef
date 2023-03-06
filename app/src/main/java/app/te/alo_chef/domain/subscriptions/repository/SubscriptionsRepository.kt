package app.te.alo_chef.domain.subscriptions.repository

import app.te.alo_chef.data.subscriptions.dto.MakeSubscriptionData
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.subscriptions.entity.SubscribeRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface SubscriptionsRepository {
    suspend fun getSubscriptions(): Resource<BaseResponse<List<SubscriptionData>>>
    suspend fun subscribe(subscribeRequest: SubscribeRequest): Resource<BaseResponse<MakeSubscriptionData>>
}