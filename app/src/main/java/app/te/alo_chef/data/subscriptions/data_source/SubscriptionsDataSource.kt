package app.te.alo_chef.data.subscriptions.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.subscriptions.entity.SubscribeRequest
import javax.inject.Inject

class SubscriptionsDataSource @Inject constructor(private val apiService: SubscriptionsServices) :
    BaseRemoteDataSource() {

    suspend fun getSubscriptions() = safeApiCall {
        apiService.getSubscriptions()
    }

    suspend fun subscribe(subscribeRequest: SubscribeRequest) = safeApiCall {
        apiService.subscribe(subscribeRequest)
    }

}