package app.te.alo_chef.data.home.data_source.local

import app.te.alo_chef.data.home.data_source.dto.MealsData
import javax.inject.Inject

class HomeLocalRemoteDataSource @Inject constructor(private val homeDao: HomeDao) {

  fun homeStudentLocal() = homeDao.getNews()
  suspend fun insertHomeStudent(homeMainData: MealsData)=homeDao.insertHomeData(homeMainData)
  fun homeStudentDelete() = homeDao.deleteHomeData()

}