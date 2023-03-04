package app.te.alo_chef.data.auth.data_source.remote

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.*
import app.te.alo_chef.domain.profile.entity.UpdatePassword
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.*

interface AuthServices {

    @POST("user/login")
    suspend fun logIn(@Body request: LogInRequest): BaseResponse<UserResponse>

    @POST("user/code-send")
    suspend fun forgetPassword(@Body request: ForgetPasswordRequest): BaseResponse<*>

    @POST("user/code-send")
    suspend fun resendCode(@Body request: ForgetPasswordRequest): BaseResponse<*>

    @POST("user/code-check")
    suspend fun verifyAccount(@Body request: ForgetPasswordRequest): BaseResponse<UserResponse>

    @POST("v1/user/profile/update_password")
    suspend fun changePassword(@Body request: UpdatePassword): BaseResponse<*>

    @POST("user/change-password")
    suspend fun authChangePassword(@Body request: UpdatePassword): BaseResponse<*>

    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<*>

    @POST("user/check-social")
    suspend fun socialLogIn(@Body request: SocialLogInRequest): BaseResponse<UserResponse>

}