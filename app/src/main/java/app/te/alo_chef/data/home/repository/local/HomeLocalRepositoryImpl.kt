package app.te.alo_chef.data.home.repository.local

import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.data.home.data_source.local.HomeLocalRemoteDataSource
import app.te.alo_chef.domain.home.repository.local.HomeLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeLocalRepositoryImpl @Inject constructor(private val homeLocalRemoteDataSource: HomeLocalRemoteDataSource) :
  HomeLocalRepository {
  override fun studentHomeLocal(): Flow<MealsData> =
    homeLocalRemoteDataSource.homeStudentLocal()

  override suspend fun insertStudentHomeLocal(homeMainData: MealsData) =
    homeLocalRemoteDataSource.insertHomeStudent(homeMainData)
  override suspend fun deleteAll()  = homeLocalRemoteDataSource.homeStudentDelete()

}