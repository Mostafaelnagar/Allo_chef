package app.te.alo_chef.data.my_locations.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import javax.inject.Inject

class MyLocationsDataSource @Inject constructor(private val apiService: MyLocationsServices) :
    BaseRemoteDataSource() {

    suspend fun getMyLocations() = safeApiCall {
        apiService.getMyLocations()
    }

}