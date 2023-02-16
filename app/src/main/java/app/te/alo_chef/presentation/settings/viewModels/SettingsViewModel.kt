package app.te.alo_chef.presentation.settings.viewModels

import androidx.lifecycle.viewModelScope
import app.te.alo_chef.domain.settings.models.AboutData
import app.te.alo_chef.domain.settings.models.ContactUsRequest
import app.te.alo_chef.domain.settings.use_case.AboutUseCase
import app.te.alo_chef.domain.settings.use_case.ContactUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val aboutUseCase: AboutUseCase,
    private val contactUseCase: ContactUseCase,
) : BaseViewModel() {

    val contactUsRequest = ContactUsRequest()
    private val _aboutResponse =
        MutableStateFlow<Resource<BaseResponse<AboutData>>>(Resource.Default)
    val aboutResponse = _aboutResponse

    private val _contactResponse =
        MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val contactResponse = _contactResponse

    fun pages(page: String) {
        aboutUseCase.aboutData(page)
            .onEach { result ->
                _aboutResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    fun sendContact() {
        contactUseCase.invoke(contactUsRequest)
            .onEach { result ->
                _contactResponse.value = result
            }
            .launchIn(viewModelScope)
    }

}