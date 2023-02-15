package app.te.alo_chef.domain.auth.use_case

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.ForgetPasswordRequest
import app.te.alo_chef.domain.auth.entity.request.RegisterRequest
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class VerifyAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

     fun verifyAccount(request: ForgetPasswordRequest): Flow<Resource<BaseResponse<UserResponse>>> =
        flow {
            if (request.code.isNotEmpty()) {
                emit(Resource.Loading)
                emit(authRepository.verifyAccount(request))
            }
        }.flowOn(Dispatchers.IO)

     fun verifyPassword(request: ForgetPasswordRequest): Flow<Resource<BaseResponse<*>>> =
        flow {
            if (request.code.isNotEmpty()) {
                emit(Resource.Loading)
                emit(authRepository.verifyAccount(request))
            }
        }.flowOn(Dispatchers.IO)

}