package app.te.alo_chef.domain.home.use_case

import app.te.alo_chef.domain.home.repository.HomeRepository
import app.te.alo_chef.domain.intro.entity.MealRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeLikeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun changeLike(mealRequest: MealRequest, dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            homeRepository.changeLike(mealRequest)
        }

}
