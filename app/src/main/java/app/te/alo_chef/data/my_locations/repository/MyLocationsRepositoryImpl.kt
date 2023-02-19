package app.te.alo_chef.data.my_locations.repository

import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.data.my_locations.data_source.MyLocationsDataSource
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.domain.my_locations.repository.MyLocationsRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class MyLocationsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MyLocationsDataSource,
    private val appPreferences: AppPreferences
) : MyLocationsRepository {

    override suspend fun getMyLocations(): Resource<BaseResponse<List<LocationsData>>> {
        val response = remoteDataSource.getMyLocations()
        if (response is Resource.Success) {
            return Resource.Success(
                BaseResponse(
                    data = mapLocationsWithLocale(response.value.data),
                    message = response.value.message,
                    status = response.value.status
                )
            )
        }
        return response
    }

    override suspend fun addNewLocation(addLocationRequest: AddLocationRequest): Resource<BaseResponse<*>> =
        remoteDataSource.addNewLocation(addLocationRequest)

    override suspend fun deleteLocation(addLocationRequest: AddLocationRequest): Resource<BaseResponse<*>> =
        remoteDataSource.deleteLocation(addLocationRequest)

    override suspend fun updateLocation(addLocationRequest: AddLocationRequest): Resource<BaseResponse<*>> =
        remoteDataSource.updateLocation(addLocationRequest)

    private suspend fun mapLocationsWithLocale(data: List<LocationsData>): List<LocationsData> {
        val newItems: MutableList<LocationsData> = mutableListOf()
        val location = appPreferences.getDefaultLocationValue()
        data.map {
            if (location.id != 0)
                if (location.id == it.id) {
                    newItems.add(it.copy(main = "1", selected = true))
                } else
                    newItems.add(it.copy(main = "0", selected = false))
            else {
                appPreferences.saveDefaultLocation(it)
                return data
            }

        }
        return newItems
    }
}