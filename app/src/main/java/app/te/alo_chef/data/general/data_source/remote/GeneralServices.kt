package app.te.alo_chef.data.general.data_source.remote

import app.te.alo_chef.domain.general.entity.UpdateFirebaseTokenRequest
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GeneralServices {

    @GET("app/cities")
    suspend fun getCities(): BaseResponse<List<CityModel>>

    @POST("v1/user/update_fcm_token")
    suspend fun updateFirebaseToken(@Body updateFirebaseTokenRequest: UpdateFirebaseTokenRequest): BaseResponse<*>

}