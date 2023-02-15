package app.te.alo_chef.data.intro.repository

import app.te.alo_chef.data.intro.data_source.IntroRemoteDataSource
import app.te.alo_chef.domain.intro.entity.AppTutorialModel
import app.te.alo_chef.domain.intro.repository.IntroRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
  private val remoteDataSource: IntroRemoteDataSource
) : IntroRepository {
  override suspend fun intro(): Resource<BaseResponse<List<AppTutorialModel>>> =
    remoteDataSource.intro()

}