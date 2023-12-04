package app.te.alo_chef.presentation.profile.viewModels

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.BR
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.profile.use_case.UpdateProfileUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.domain.utils.isValidEmail
import app.te.alo_chef.domain.utils.validation.ValidatePhone
import app.te.alo_chef.presentation.base.BaseViewModel
import app.te.alo_chef.presentation.base.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userLocalUseCase: UserLocalUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val validatePhone: ValidatePhone
) : BaseViewModel() {
    @Bindable
    var request = UpdateProfileRequest()
        set(value) {
            notifyPropertyChanged(BR.request)
            field = value
        }
    private val _updateProfileResponse =
        MutableStateFlow<Resource<BaseResponse<UserResponse>>>(Resource.Default)
    val updateProfileResponse = _updateProfileResponse

    init {
        updateRequest()
    }

    fun updateProfile() {
        viewModelScope.launch {
            if (checkValidation()) {
                _updateProfileResponse.value = Resource.Loading
                _updateProfileResponse.value = updateProfileUseCase.invoke(request, Dispatchers.IO)
            }
        }
    }

    private fun checkValidation(): Boolean {
        var isValid = true
        if (request.name.isEmpty()) {
            request.validation.nameError.set(Constants.EMPTY)
            isValid = false
        }
        if (request.email.isEmpty() && !request.email.isValidEmail()) {
            request.validation.emailError.set(Constants.EMPTY)
            isValid = false
        }
        val phoneResult = validatePhone.invoke(request.phone)

        if (!phoneResult.successful) {
            request.validation.phoneError.set(phoneResult.errorMessage)
            isValid = false
        }

        return isValid
    }

    private fun updateRequest() {
        viewModelScope.launch {
            userLocalUseCase.invoke().collect { userLocal ->
                request.name = userLocal.name
                request.phone = userLocal.phone
                request.email = userLocal.email
                request.userImage = userLocal.image
            }
        }
    }
}