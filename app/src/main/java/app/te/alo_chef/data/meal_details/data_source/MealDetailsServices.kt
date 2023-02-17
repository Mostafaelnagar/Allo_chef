package app.te.alo_chef.data.meal_details.data_source

import app.te.alo_chef.data.meal_details.dto.MainDetails
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDetailsServices {
    @GET("user/get-meal-details")
    suspend fun getMealDetails(
        @Query("meal_id") meal_id: Int,
        @Query("date") date: String
    ): BaseResponse<MainDetails>
}