package app.te.alo_chef.domain.account.use_case

import app.te.alo_chef.domain.account.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    operator fun invoke() = flow {
        emit(accountRepository.logOut())
    }.flowOn(Dispatchers.IO)
}