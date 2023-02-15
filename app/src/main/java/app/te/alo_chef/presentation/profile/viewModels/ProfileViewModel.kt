package app.te.alo_chef.presentation.profile.viewModels

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.BR
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.general.use_case.CitiesUseCase
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.profile.use_case.ProfileUseCase
import app.te.alo_chef.domain.profile.use_case.UpdateProfileUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val profileUseCase: ProfileUseCase,
    private val cityUseCases: CitiesUseCase
) : BaseViewModel() {

    @Bindable
    var request = UpdateProfileRequest()
    private val _profileResponse =
        MutableStateFlow<Resource<BaseResponse<UserResponse>>>(Resource.Default)

    val profileResponse = _profileResponse
    private val _updateProfileResponse =
        MutableStateFlow<Resource<BaseResponse<UserResponse>>>(Resource.Default)
    val updateProfileResponse = _updateProfileResponse

    private val _citiesResponse =
        MutableStateFlow<Resource<BaseResponse<List<CityModel>>>>(Resource.Default)
    val citiesResponse = _citiesResponse
    var cities: List<CityModel> = listOf()


    init {
        getProfile()
    }

    private fun getProfile() {
        profileUseCase.invoke().catch { exception ->
            Log.e(
                "updateProfile",
                "updateProfile: ${exception.printStackTrace()}"
            )
        }.onEach { result ->
            _profileResponse.value = result
        }.launchIn(viewModelScope)
    }

    fun updateProfile() {
        updateProfileUseCase.invoke(request).catch { exception ->
            Log.e(
                "updateProfile",
                "updateProfile: ${exception.printStackTrace()}"
            )
        }.onEach { result ->
            _updateProfileResponse.value = result
        }.launchIn(viewModelScope)
    }

    fun getCities() {
        cityUseCases()
            .catch { exception -> }
            .onEach { result ->
                _citiesResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    fun updateRequest(data: UserResponse) {
        request.name = request.name.ifEmpty { data.name }
        request.phone = data.phone
//        request.city_id = data.city_id.toString()
//        request.cityName = data.city_name
        notifyPropertyChanged(BR.request)
    }
}