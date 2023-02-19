package app.te.alo_chef.data.profile.data_source

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.utils.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileServices {

    @GET("v1/user/profile")
    suspend fun getProfile(): BaseResponse<UserResponse>

    @POST("user/update-profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): BaseResponse<UserResponse>
    @Multipart
    @POST("user/update-profile")
    suspend fun updateProfile(
        @QueryMap map: Map<String, String>,
        @Part image: MultipartBody.Part?
    ): BaseResponse<UserResponse>

}