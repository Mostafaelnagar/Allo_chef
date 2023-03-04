package app.te.alo_chef.domain.subscriptions.use_case

import app.te.alo_chef.domain.subscriptions.entity.SubscribeRequest
import app.te.alo_chef.domain.subscriptions.repository.SubscriptionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubscribeUseCase @Inject constructor(private val subscriptionsRepository: SubscriptionsRepository) {
    suspend operator fun invoke(
        dispatcher: CoroutineDispatcher,
        subscribeRequest: SubscribeRequest
    ) = withContext(dispatcher) {
        subscriptionsRepository.subscribe(subscribeRequest)
    }
}