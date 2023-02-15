package app.te.alo_chef.data.auth.data_source.remote

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.auth.entity.request.*
import app.te.alo_chef.domain.profile.entity.UpdatePassword
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val apiService: AuthServices
) : BaseRemoteDataSource() {

    suspend fun logIn(request: LogInRequest) = safeApiCall {
        apiService.logIn(request)
    }


    suspend fun forgetPassword(request: ForgetPasswordRequest) = safeApiCall {
        apiService.forgetPassword(request)
    }

    suspend fun resendCode(request: ForgetPasswordRequest) = safeApiCall {
        apiService.resendCode(request)
    }

    suspend fun verifyAccount(request: ForgetPasswordRequest) = safeApiCall {
        apiService.verifyAccount(request)
    }

    suspend fun changePassword(request: UpdatePassword) = safeApiCall {
        apiService.changePassword(request)
    }

    suspend fun authChangePassword(request: UpdatePassword) = safeApiCall {
        apiService.authChangePassword(request)
    }

    suspend fun register(request: RegisterRequest) = safeApiCall {
        apiService.register(request)
    }

}