package app.te.alo_chef.domain.subscriptions.repository

import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface SubscriptionsRepository {
    suspend fun getSubscriptions(): Resource<BaseResponse<List<SubscriptionData>>>
}