package app.te.alo_chef.data.profile.data_source

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.*

interface ProfileServices {

    @GET("v1/user/profile")
    suspend fun getProfile(): BaseResponse<UserResponse>

    @POST("v1/user/profile/update")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): BaseResponse<UserResponse>

}