package app.te.alo_chef.domain.home.use_case

import app.te.alo_chef.domain.home.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FilterByDateUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun filterMeals(date: String, filter: String, dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            homeRepository.filterMeals(date, filter)
        }

}
