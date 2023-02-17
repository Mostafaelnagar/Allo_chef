package app.te.alo_chef.data.subscriptions.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import javax.inject.Inject

class SubscriptionsDataSource @Inject constructor(private val apiService: SubscriptionsServices) :
    BaseRemoteDataSource() {

    suspend fun getSubscriptions() = safeApiCall {
        apiService.getSubscriptions()
    }

}