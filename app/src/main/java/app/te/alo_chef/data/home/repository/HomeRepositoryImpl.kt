package app.te.alo_chef.data.home.repository

import app.te.alo_chef.data.home.data_source.remote.HomeRemoteDataSource
import app.te.alo_chef.data.home.data_source.dto.HomeData
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.domain.home.enity.FilterRequest
import app.te.alo_chef.domain.home.repository.HomeRepository
import app.te.alo_chef.domain.intro.entity.MealRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteDataSource: HomeRemoteDataSource) :
    HomeRepository {
    override suspend fun getHome(date: String): Resource<BaseResponse<HomeData>> =
        remoteDataSource.getHomeData(date)

    override suspend fun filterMeals(
        date: String,
        filter: String
    ): Resource<BaseResponse<List<MealsData>>> =
        remoteDataSource.filterMeals(date, filter)

    override suspend fun getVipMeals(): Resource<BaseResponse<List<MealsData>>> =
        remoteDataSource.getVipMeals()

    override suspend fun getFavoritesMeal(): Resource<BaseResponse<List<MealsData>>> =
        remoteDataSource.getFavoritesMeal()

    override suspend fun search(searchKey: String): Resource<BaseResponse<List<MealsData>>> =
        remoteDataSource.search(searchKey)


    override suspend fun changeLike(
        mealRequest: MealRequest
    ): Resource<BaseResponse<*>> =
        remoteDataSource.changeLike(mealRequest)

}