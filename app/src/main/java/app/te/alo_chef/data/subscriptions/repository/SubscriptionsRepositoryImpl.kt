package app.te.alo_chef.data.subscriptions.repository

import app.te.alo_chef.data.subscriptions.data_source.SubscriptionsDataSource
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.domain.subscriptions.repository.SubscriptionsRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class SubscriptionsRepositoryImpl @Inject constructor(private val remoteDataSource: SubscriptionsDataSource) :
    SubscriptionsRepository {

    override suspend fun getSubscriptions(): Resource<BaseResponse<List<SubscriptionData>>> =
        remoteDataSource.getSubscriptions()
}