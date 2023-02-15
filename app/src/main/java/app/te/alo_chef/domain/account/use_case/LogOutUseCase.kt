package app.te.alo_chef.domain.account.use_case

import app.te.alo_chef.domain.account.repository.AccountRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    operator fun invoke(): Flow<Resource<BaseResponse<*>>> = flow {
        accountRepository.clearPreferences()
        val result = accountRepository.logOut()
        emit(result)
    }.flowOn(Dispatchers.IO)
}