package app.te.alo_chef.data.payment.repository

import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.data.payment.data_source.PaymentDataSource
import app.te.alo_chef.data.payment.dto.PaymentData
import app.te.alo_chef.domain.payment.repository.PaymentRepository
import app.te.alo_chef.domain.utils.PaymentBaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val remoteDataSource: PaymentDataSource,
    private val appPreferences: AppPreferences
) : PaymentRepository {

    override suspend fun getPaymentData(
        invoice_value: Float
    ): Resource<PaymentBaseResponse<PaymentData>> {
        val fullName = appPreferences.getUserValue().name
        return remoteDataSource.getPaymentData(fullName, invoice_value)
    }

}