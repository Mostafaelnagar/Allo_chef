package app.te.alo_chef.data.home.data_source.remote

import app.te.alo_chef.data.home.data_source.dto.HomeData
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.domain.intro.entity.MealRequest
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeServices {
    @GET("user/get-meals")
    suspend fun getHomeData(
        @Query("date") date: String
    ): BaseResponse<HomeData>

    @GET("user/get-meals")
    suspend fun filterMeals(
        @Query("date") date: String,
        @Query("filter") filter: String,
    ): BaseResponse<List<MealsData>>

    @POST("user/like-meal")
    suspend fun changeLike(
        @Body mealRequest: MealRequest
    ): BaseResponse<*>

    @GET("user/get-vip-meals")
    suspend fun getVipMeals(): BaseResponse<List<MealsData>>

}