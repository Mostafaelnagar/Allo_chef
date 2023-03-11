package app.te.alo_chef.data.payment.repository

import app.te.alo_chef.data.payment.data_source.PaymentDataSource
import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.domain.payment.entity.PaymentRequest
import app.te.alo_chef.domain.payment.repository.PaymentRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val remoteDataSource: PaymentDataSource
) : PaymentRepository {

    override suspend fun getPaymentData(
        paymentRequest: PaymentRequest
    ): Resource<BaseResponse<PaymentResponse>> {
        return remoteDataSource.getPaymentData(paymentRequest)
    }

    override suspend fun paymentCallBack(
        paymentId:String
    ): Resource<BaseResponse<*>> {
        return remoteDataSource.paymentCallBack(paymentId)
    }

}