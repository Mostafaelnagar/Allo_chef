package app.te.alo_chef.data.meal_details.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import javax.inject.Inject

class MealDetailsDataSource @Inject constructor(private val apiService: MealDetailsServices) :
    BaseRemoteDataSource() {

    suspend fun getMealDetails(mealId: Int, date: String) = safeApiCall {
        apiService.getMealDetails(mealId, date)
    }

}