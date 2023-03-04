package app.te.alo_chef.data.auth.repository

import app.te.alo_chef.data.auth.data_source.remote.AuthRemoteDataSource
import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.*
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.profile.entity.UpdatePassword
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.utils.Constants
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val appPreferences: AppPreferences
) : AuthRepository {

    override
    suspend fun logIn(request: LogInRequest): Resource<BaseResponse<UserResponse>> {
        val response = remoteDataSource.logIn(request)
        if (response is Resource.Success) {
            appPreferences.userToken(response.value.data.jwt)
            appPreferences.saveUser(response.value.data)
        }
        return response
    }

    override suspend fun socialLogIn(request: SocialLogInRequest): Resource<BaseResponse<UserResponse>> {
        val response = remoteDataSource.socialLogIn(request)
        if (response is Resource.Success) {
            appPreferences.userToken(response.value.data.jwt)
            appPreferences.saveUser(response.value.data)
        }
        return response
    }

    override suspend fun changePassword(request: UpdatePassword): Resource<BaseResponse<*>> =
        remoteDataSource.changePassword(request)

    override suspend fun authChangePassword(request: UpdatePassword): Resource<BaseResponse<*>> =
        remoteDataSource.authChangePassword(request)

    override suspend fun forgetPassword(request: ForgetPasswordRequest) =
        remoteDataSource.forgetPassword(request)

    override suspend fun resendCode(request: ForgetPasswordRequest) =
        remoteDataSource.resendCode(request)

    override suspend fun register(request: RegisterRequest): Resource<BaseResponse<*>> =
        remoteDataSource.register(request)

    override suspend fun verifyAccount(request: ForgetPasswordRequest): Resource<BaseResponse<UserResponse>> {
        val response = remoteDataSource.verifyAccount(request)
        if (response is Resource.Success) {
            appPreferences.userToken(response.value.data.jwt)
            if (request.type == Constants.VERIFY)
                appPreferences.saveUser(response.value.data)
        }
        return response
    }

}

