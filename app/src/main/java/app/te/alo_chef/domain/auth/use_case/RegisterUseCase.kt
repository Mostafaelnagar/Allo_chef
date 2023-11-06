package app.te.alo_chef.domain.auth.use_case

import app.te.alo_chef.domain.auth.entity.request.LogInRequest
import app.te.alo_chef.domain.auth.entity.request.RegisterRequest
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class RegisterUseCase @Inject constructor(
  private val authRepository: AuthRepository
) {

  operator fun invoke(request: RegisterRequest): Flow<Resource<BaseResponse<*>>> = flow {
      emit(Resource.Loading)
      val result = authRepository.register(request)
      emit(result)
  }.flowOn(Dispatchers.IO)

}