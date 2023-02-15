package app.te.alo_chef.domain.settings.use_case

import app.te.alo_chef.domain.settings.models.Teams
import app.te.alo_chef.domain.settings.repository.SettingsRepository
import app.te.alo_chef.domain.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TeamUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  operator fun invoke(): Flow<Resource<BaseResponse<List<Teams>>>> = flow {
    emit(Resource.Loading)
    val result = settingsRepository.getTeam()
    emit(result)
  }.flowOn(Dispatchers.IO)

}