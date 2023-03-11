package app.te.alo_chef.domain.payment.use_case

import app.te.alo_chef.domain.payment.repository.PaymentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckPaymentCallBackUseCase @Inject constructor(private val repository: PaymentRepository) {
    suspend fun paymentCallBack(
        paymentId: String, dispatcher: CoroutineDispatcher
    ) =
        withContext(dispatcher) {
            repository.paymentCallBack(paymentId)
        }
}