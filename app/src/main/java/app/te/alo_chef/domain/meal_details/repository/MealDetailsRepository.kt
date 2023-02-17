package app.te.alo_chef.domain.meal_details.repository

import app.te.alo_chef.data.meal_details.dto.MainDetails
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface MealDetailsRepository {
    suspend fun getMealDetails(mealId: Int, date: String): Resource<BaseResponse<MainDetails>>
}