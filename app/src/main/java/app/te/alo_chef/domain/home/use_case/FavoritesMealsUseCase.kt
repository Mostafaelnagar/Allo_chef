package app.te.alo_chef.domain.home.use_case

import app.te.alo_chef.domain.home.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FavoritesMealsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            homeRepository.getFavoritesMeal()
        }

}
