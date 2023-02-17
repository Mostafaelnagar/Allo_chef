package app.te.alo_chef.domain.wallet.use_case

import app.te.alo_chef.domain.wallet.repository.WalletRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WalletTransactionsUseCase @Inject constructor(private val walletRepository: WalletRepository) {

    suspend operator fun invoke(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        walletRepository.getWalletTransaction()
    }
}