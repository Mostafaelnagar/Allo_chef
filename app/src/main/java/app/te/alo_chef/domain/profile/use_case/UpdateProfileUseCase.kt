package app.te.alo_chef.domain.profile.use_case

import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(
        request: UpdateProfileRequest,
        dispatcher: CoroutineDispatcher
    ) = withContext(dispatcher) {
        profileRepository.updateProfile(request)
    }
}
