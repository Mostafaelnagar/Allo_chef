package app.te.alo_chef.presentation.my_locations.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.my_locations.use_case.MyLocationsUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.my_locations.adapters.MyLocationsAdapters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val myLocationsUseCase: MyLocationsUseCase,
    private val userLocalUseCase: UserLocalUseCase
) :
    ViewModel() {
    lateinit var locationsAdapters: MyLocationsAdapters
    private val _locationsResponse =
        MutableStateFlow<Resource<BaseResponse<List<LocationsData>>>>(Resource.Default)
    val locationsResponse = _locationsResponse

    fun getMyLocations() {
        viewModelScope.launch {
            _locationsResponse.value = Resource.Loading
            _locationsResponse.value = myLocationsUseCase.invoke(Dispatchers.IO)
        }
    }

    fun saveDefaultLocation(item: LocationsData) {
        viewModelScope.launch(Dispatchers.IO) {
            userLocalUseCase.saveDefaultLocation(item)
        }
    }
}