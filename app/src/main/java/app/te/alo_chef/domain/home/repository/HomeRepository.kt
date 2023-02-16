package app.te.alo_chef.domain.home.repository

import app.te.alo_chef.data.home.data_source.dto.HomeData
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.domain.home.enity.FilterRequest
import app.te.alo_chef.domain.intro.entity.MealRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface HomeRepository {
    suspend fun getHome(date: String): Resource<BaseResponse<HomeData>>
    suspend fun filterMeals(
        date: String,
        filter: String
    ): Resource<BaseResponse<List<MealsData>>>

    suspend fun getVipMeals(): Resource<BaseResponse<List<MealsData>>>
    suspend fun getFavoritesMeal(): Resource<BaseResponse<List<MealsData>>>
    suspend fun search(searchKey: String): Resource<BaseResponse<List<MealsData>>>
    suspend fun changeLike(
        mealRequest: MealRequest
    ): Resource<BaseResponse<*>>
}