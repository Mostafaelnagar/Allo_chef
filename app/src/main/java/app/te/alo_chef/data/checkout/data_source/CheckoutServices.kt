package app.te.alo_chef.data.checkout.data_source

import app.te.alo_chef.data.checkout.dto.DeliveryTimes
import app.te.alo_chef.data.checkout.dto.promo.PromoData
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.checkout.entity.NewOrderRequest
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CheckoutServices {
    @GET("user/check-copon")
    suspend fun checkPromoCode(
        @Query("code") code: String,
    ): BaseResponse<PromoData>

    @GET("user/get-periods")
    suspend fun getDeliveryTimes(): BaseResponse<List<DeliveryTimes>>

    @POST("user/make-order")
    suspend fun checkout(@Body newOrderRequest: NewOrderRequest): BaseResponse<UserResponse>

}