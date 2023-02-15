package app.te.alo_chef.domain.intro.repository

import app.te.alo_chef.domain.intro.entity.AppTutorialModel
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface IntroRepository {
  suspend fun intro(): Resource<BaseResponse<List<AppTutorialModel>>>
}