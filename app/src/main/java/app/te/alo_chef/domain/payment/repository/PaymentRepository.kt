package app.te.alo_chef.domain.payment.repository

import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.domain.payment.entity.PaymentRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource


interface PaymentRepository {
    suspend fun getPaymentData(
        paymentRequest: PaymentRequest
    ): Resource<BaseResponse<PaymentResponse>>
}