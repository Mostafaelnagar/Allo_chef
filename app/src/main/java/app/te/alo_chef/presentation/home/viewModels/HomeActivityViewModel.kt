package app.te.alo_chef.presentation.home.viewModels

import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
   val userLocalUseCase: UserLocalUseCase
) : BaseViewModel()