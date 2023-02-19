package app.te.alo_chef.domain.account.use_case

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.account.repository.AccountRepository
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import com.structure.base_mvvm.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(user: UserResponse) {
        accountRepository.saveUserToLocal(user)
    }

    suspend operator fun invoke(): Flow<User> = accountRepository.getUserToLocal()

    suspend fun logOut() = accountRepository.clearPreferences()

    suspend fun saveDefaultLocation(item: LocationsData) {
        accountRepository.saveDefaultLocation(item)
    }

    suspend fun getSavedLocationFlow() = accountRepository.getDefaultLocationFlow()
    suspend fun getSavedLocation() = accountRepository.getDefaultLocation()

}