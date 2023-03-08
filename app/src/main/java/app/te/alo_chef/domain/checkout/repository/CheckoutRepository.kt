package app.te.alo_chef.domain.checkout.repository

import app.te.alo_chef.data.checkout.dto.CheckoutResponse
import app.te.alo_chef.data.checkout.dto.DeliveryTimes
import app.te.alo_chef.data.checkout.dto.promo.PromoData
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.checkout.entity.NewOrderRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface CheckoutRepository {
    suspend fun checkPromoCode(code: String): Resource<BaseResponse<PromoData>>
    suspend fun getDeliveryTimes(): Resource<BaseResponse<List<DeliveryTimes>>>
    suspend fun checkout(newOrderRequest: NewOrderRequest): Resource<BaseResponse<CheckoutResponse>>
}