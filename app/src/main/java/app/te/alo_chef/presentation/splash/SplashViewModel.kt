package app.te.alo_chef.presentation.splash

import androidx.lifecycle.viewModelScope
import app.te.alo_chef.domain.general.use_case.GeneralUseCases
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val generalUseCases: GeneralUseCases) :
    BaseViewModel() {
    lateinit var eventListener: SplashEventListener

    init {
        viewModelScope.launch {
            delay(3000)
            generalUseCases.checkFirstTimeUseCase().collect { isFirst ->
                if (isFirst) {
                    eventListener.openOnBoarding()
                } else {
                    eventListener.openHome()
                }
            }
        }
    }
}