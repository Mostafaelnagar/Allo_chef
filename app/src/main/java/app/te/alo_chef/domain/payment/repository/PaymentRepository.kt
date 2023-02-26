package app.te.alo_chef.domain.payment.repository

import app.te.alo_chef.data.payment.dto.PaymentData
import app.te.alo_chef.domain.utils.PaymentBaseResponse
import app.te.alo_chef.domain.utils.Resource


interface PaymentRepository {
    suspend fun getPaymentData(
        invoice_value: Float
    ): Resource<PaymentBaseResponse<PaymentData>>
}