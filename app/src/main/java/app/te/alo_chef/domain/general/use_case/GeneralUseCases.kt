package app.te.alo_chef.domain.general.use_case

import app.te.alo_chef.domain.account.use_case.CheckFirstTimeUseCase
import app.te.alo_chef.domain.account.use_case.CheckLoggedInUserUseCase
import app.te.alo_chef.domain.account.use_case.SetFirstTimeUseCase
import app.te.alo_chef.domain.account.use_case.ConfigUseCase

class GeneralUseCases(
  val checkFirstTimeUseCase: CheckFirstTimeUseCase,
  val checkLoggedInUserUseCase: CheckLoggedInUserUseCase,
  val setFirstTimeUseCase: SetFirstTimeUseCase,
  val configUseCase: ConfigUseCase,
  val languageUseCase: LanguageUseCase
)