package app.te.alo_chef.domain.settings.use_case

import app.te.alo_chef.domain.settings.models.AboutData
import app.te.alo_chef.domain.settings.repository.SettingsRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class AboutUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {

  fun aboutData(page:String): Flow<Resource<BaseResponse<AboutData>>> =
    flow {
      emit(Resource.Loading)
      val result = settingsRepository.about(page)
      emit(result)
    }.flowOn(Dispatchers.IO)
}