package app.te.alo_chef.domain.general.use_case

import app.te.alo_chef.domain.account.repository.AccountRepository
import javax.inject.Inject

class LanguageUseCase @Inject constructor(private val accountRepository: AccountRepository) {
  suspend fun invoke() = accountRepository.getLang()
  suspend fun invoke(lang: String) = accountRepository.setLang(lang)
}