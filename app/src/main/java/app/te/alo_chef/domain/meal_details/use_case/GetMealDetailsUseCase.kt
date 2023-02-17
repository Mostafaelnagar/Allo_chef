package app.te.alo_chef.domain.meal_details.use_case

import app.te.alo_chef.domain.meal_details.repository.MealDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetMealDetailsUseCase @Inject constructor(
    private val mealDetailsRepository: MealDetailsRepository
) {
    suspend fun getMealDetails(mealId: Int, date: String, dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            mealDetailsRepository.getMealDetails(mealId, date)
        }

}
