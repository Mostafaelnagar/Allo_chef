package app.te.alo_chef.domain.auth.repository

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.*
import app.te.alo_chef.domain.profile.entity.UpdatePassword
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface AuthRepository {

    suspend fun logIn(request: LogInRequest): Resource<BaseResponse<UserResponse>>
    suspend fun socialLogIn(request: SocialLogInRequest): Resource<BaseResponse<UserResponse>>
    suspend fun changePassword(request: UpdatePassword): Resource<BaseResponse<*>>
    suspend fun authChangePassword(request: UpdatePassword): Resource<BaseResponse<*>>
    suspend fun forgetPassword(request: ForgetPasswordRequest): Resource<BaseResponse<*>>
    suspend fun resendCode(request: ForgetPasswordRequest): Resource<BaseResponse<*>>
    suspend fun register(request: RegisterRequest): Resource<BaseResponse<*>>
    suspend fun verifyAccount(request: ForgetPasswordRequest): Resource<BaseResponse<UserResponse>>
}