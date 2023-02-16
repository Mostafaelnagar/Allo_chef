package app.te.alo_chef.data.home.data_source.remote

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.home.enity.FilterRequest
import app.te.alo_chef.domain.intro.entity.MealRequest
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val apiService: HomeServices) :
    BaseRemoteDataSource() {

    suspend fun getHomeData(date: String) = safeApiCall {
        apiService.getHomeData(date)
    }

    suspend fun filterMeals(date: String, filter: String) = safeApiCall {
        apiService.filterMeals(date, filter)
    }

    suspend fun changeLike(mealRequest: MealRequest) = safeApiCall {
        apiService.changeLike(mealRequest)
    }

    suspend fun getVipMeals() = safeApiCall {
        apiService.getVipMeals()
    }

    suspend fun getFavoritesMeal() = safeApiCall {
        apiService.getFavoritesMeal()
    }

    suspend fun search(searchKey: String) = safeApiCall {
        apiService.search(searchKey)
    }

}