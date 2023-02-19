package app.te.alo_chef.domain.my_locations.use_case

import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.domain.my_locations.repository.MyLocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(private val myLocationsRepository: MyLocationsRepository) {

    suspend operator fun invoke(addLocationRequest: AddLocationRequest,dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        myLocationsRepository.addNewLocation(addLocationRequest)
    }
}