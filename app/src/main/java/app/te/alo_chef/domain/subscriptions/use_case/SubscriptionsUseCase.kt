package app.te.alo_chef.domain.subscriptions.use_case

import app.te.alo_chef.domain.subscriptions.repository.SubscriptionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubscriptionsUseCase @Inject constructor(private val subscriptionsRepository: SubscriptionsRepository) {
    suspend operator fun invoke(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        subscriptionsRepository.getSubscriptions()
    }
}