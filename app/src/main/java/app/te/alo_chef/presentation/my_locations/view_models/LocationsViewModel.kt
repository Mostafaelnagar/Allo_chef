package app.te.alo_chef.presentation.my_locations.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.account.use_case.UserLocalUseCase
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.general.use_case.CitiesUseCase
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.domain.my_locations.use_case.AddLocationUseCase
import app.te.alo_chef.domain.my_locations.use_case.DeleteLocationUseCase
import app.te.alo_chef.domain.my_locations.use_case.MyLocationsUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.my_locations.adapters.MyLocationsAdapters
import app.te.alo_chef.presentation.my_locations.ui_state.AddLocationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val myLocationsUseCase: MyLocationsUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    private val editLocationUseCase: AddLocationUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val userLocalUseCase: UserLocalUseCase,
    private val citiesUseCase: CitiesUseCase,
    var addLocationUiState: AddLocationUiState
) : ViewModel() {

    lateinit var locationsAdapters: MyLocationsAdapters

    private val _locationsResponse =
        MutableStateFlow<Resource<BaseResponse<List<LocationsData>>>>(Resource.Default)
    val locationsResponse = _locationsResponse

    private val _citiesResponse =
        MutableStateFlow<Resource<BaseResponse<List<CityModel>>>>(Resource.Default)
    val citiesResponse = _citiesResponse

    private val _addLocationResponse =
        MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val addLocationResponse = _addLocationResponse
    private val _deleteLocationResponse =
        MutableStateFlow<Resource<BaseResponse<*>>>(Resource.Default)
    val deleteLocationResponse = _deleteLocationResponse

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

    fun getCities() {
        viewModelScope.launch {
            _citiesResponse.value = Resource.Loading
            _citiesResponse.value = citiesUseCase.invoke(Dispatchers.IO)
        }
    }

    fun addNewLocation() {
        if (addLocationUiState.validate())
            viewModelScope.launch {
                _addLocationResponse.value = Resource.Loading
                _addLocationResponse.value =
                    if (addLocationUiState.request.location_id == null)
                        addLocationUseCase.invoke(addLocationUiState.request, Dispatchers.IO)
                    else
                        editLocationUseCase.invoke(addLocationUiState.request, Dispatchers.IO)
            }
    }

    fun deleteLocation(addLocationRequest: AddLocationRequest) {
        viewModelScope.launch {
            _deleteLocationResponse.value = Resource.Loading
            _deleteLocationResponse.value =
                deleteLocationUseCase.invoke(
                    addLocationRequest,
                    Dispatchers.IO
                )
        }
    }

}