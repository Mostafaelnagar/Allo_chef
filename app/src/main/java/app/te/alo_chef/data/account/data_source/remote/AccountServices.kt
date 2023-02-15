package app.te.alo_chef.data.account.data_source.remote

import app.te.alo_chef.domain.account.entity.request.SendFirebaseTokenRequest
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountServices {

  @PUT("v1/firebase_token")
  suspend fun sendFirebaseToken(@Body request: SendFirebaseTokenRequest): BaseResponse<*>

  @POST("v1/user/logout")
  suspend fun logOut(): BaseResponse<*>
}