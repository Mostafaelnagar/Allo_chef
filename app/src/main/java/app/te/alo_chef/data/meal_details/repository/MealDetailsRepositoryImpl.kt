package app.te.alo_chef.data.meal_details.repository

import app.te.alo_chef.data.meal_details.data_source.MealDetailsDataSource
import app.te.alo_chef.data.meal_details.dto.MainDetails
import app.te.alo_chef.domain.meal_details.repository.MealDetailsRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class MealDetailsRepositoryImpl @Inject constructor(private val remoteDataSource: MealDetailsDataSource) :
    MealDetailsRepository {
    override suspend fun getMealDetails(
        mealId: Int,
        date: String
    ): Resource<BaseResponse<MainDetails>> =
        remoteDataSource.getMealDetails(mealId, date)
}