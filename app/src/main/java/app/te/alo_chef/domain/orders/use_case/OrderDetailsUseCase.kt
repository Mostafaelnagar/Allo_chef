package app.te.alo_chef.domain.orders.use_case

import app.te.alo_chef.domain.orders.repository.OrdersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderDetailsUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(dispatcher: CoroutineDispatcher, orderId: Int) =
        withContext(dispatcher) {
            ordersRepository.orderDetails(orderId)
        }
}