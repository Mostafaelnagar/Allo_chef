package app.te.alo_chef.data.checkout.repository

import app.te.alo_chef.data.checkout.data_source.CheckoutDataSource
import app.te.alo_chef.data.checkout.dto.CheckoutResponse
import app.te.alo_chef.data.checkout.dto.DeliveryTimes
import app.te.alo_chef.data.checkout.dto.promo.PromoData
import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.domain.checkout.entity.NewOrderRequest
import app.te.alo_chef.domain.checkout.repository.CheckoutRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
    private val remoteDataSource: CheckoutDataSource,
    private val appPreferences: AppPreferences
) :
    CheckoutRepository {

    override suspend fun checkPromoCode(code: String): Resource<BaseResponse<PromoData>> =
        remoteDataSource.checkPromoCode(code)

    override suspend fun getDeliveryTimes(): Resource<BaseResponse<List<DeliveryTimes>>> =
        remoteDataSource.getDeliveryTimes()

    override suspend fun checkout(newOrderRequest: NewOrderRequest): Resource<BaseResponse<CheckoutResponse>> {
        val response = remoteDataSource.checkout(newOrderRequest)
        if (response is Resource.Success) {
            appPreferences.userToken(response.value.data.jwt)
            appPreferences.saveUser(response.value.data)
        }
        return response
    }


}