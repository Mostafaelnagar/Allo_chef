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
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userLocalUseCase: UserLocalUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
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
            _updateProfileResponse.value = Resource.Loading
            _updateProfileResponse.value = updateProfileUseCase.invoke(request, Dispatchers.IO)
        }
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