package app.te.alo_chef.data.payment.repository

import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.data.payment.data_source.PaymentDataSource
import app.te.alo_chef.data.payment.dto.PaymentResponse
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.payment.entity.PaymentRequest
import app.te.alo_chef.domain.payment.repository.PaymentRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val remoteDataSource: PaymentDataSource,
    private val appPreferences: AppPreferences
) : PaymentRepository {

    override suspend fun getPaymentData(
        paymentRequest: PaymentRequest
    ): Resource<BaseResponse<PaymentResponse>> {
        return remoteDataSource.getPaymentData(paymentRequest)
    }

    override suspend fun paymentCallBack(
        paymentId: String
    ): Resource<BaseResponse<UserResponse>> {

        val response = remoteDataSource.paymentCallBack(paymentId)
        if (response is Resource.Success) {
            appPreferences.saveUser(response.value.data)
        }
        return response
    }

}