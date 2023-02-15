package app.te.alo_chef.domain.profile.use_case

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.profile.repository.ProfileRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(): Flow<Resource<BaseResponse<UserResponse>>> = flow {
        emit(Resource.Loading)
        val result = profileRepository.getProfile()
        emit(result)
    }.flowOn(Dispatchers.IO)

}