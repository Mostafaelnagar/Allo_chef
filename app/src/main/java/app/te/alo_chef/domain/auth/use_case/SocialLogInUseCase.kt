package app.te.alo_chef.domain.auth.use_case

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.SocialLogInRequest
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SocialLogInUseCase @Inject constructor(
  private val authRepository: AuthRepository,
) {
  operator fun invoke(
    request: SocialLogInRequest
  ): Flow<Resource<BaseResponse<UserResponse>>> = flow {
    emit(Resource.Loading)
    emit(authRepository.socialLogIn(request))
  }.flowOn(Dispatchers.IO)

}