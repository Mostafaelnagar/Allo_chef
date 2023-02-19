package app.te.alo_chef.data.my_locations.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import javax.inject.Inject

class MyLocationsDataSource @Inject constructor(private val apiService: MyLocationsServices) :
    BaseRemoteDataSource() {

    suspend fun getMyLocations() = safeApiCall {
        apiService.getMyLocations()
    }

    suspend fun addNewLocation(addLocationRequest: AddLocationRequest) = safeApiCall {
        apiService.addNewLocation(addLocationRequest)
    }

    suspend fun deleteLocation(addLocationRequest: AddLocationRequest) = safeApiCall {
        apiService.deleteLocation(addLocationRequest)
    }

    suspend fun updateLocation(addLocationRequest: AddLocationRequest) = safeApiCall {
        apiService.updateLocation(addLocationRequest)
    }

}