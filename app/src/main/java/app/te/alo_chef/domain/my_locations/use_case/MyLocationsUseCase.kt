package app.te.alo_chef.domain.my_locations.use_case

import app.te.alo_chef.domain.my_locations.repository.MyLocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyLocationsUseCase @Inject constructor(private val myLocationsRepository: MyLocationsRepository) {

    suspend operator fun invoke(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        myLocationsRepository.getMyLocations()
    }
}