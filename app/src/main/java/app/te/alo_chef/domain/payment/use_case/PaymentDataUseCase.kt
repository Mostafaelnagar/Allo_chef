package app.te.alo_chef.domain.payment.use_case

import app.te.alo_chef.domain.payment.entity.PaymentRequest
import app.te.alo_chef.domain.payment.repository.PaymentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentDataUseCase @Inject constructor(private val repository: PaymentRepository) {
    suspend fun getPaymentData(
        paymentRequest: PaymentRequest, dispatcher: CoroutineDispatcher
    ) =
        withContext(dispatcher) {
            repository.getPaymentData(paymentRequest)
        }
}