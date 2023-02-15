package app.te.alo_chef.domain.home.repository.local

import app.te.alo_chef.data.home.data_source.dto.MealsData
import kotlinx.coroutines.flow.Flow


interface HomeLocalRepository {
  fun studentHomeLocal(): Flow<MealsData>
  suspend fun insertStudentHomeLocal(homeMainData: MealsData)
  suspend fun deleteAll()

}