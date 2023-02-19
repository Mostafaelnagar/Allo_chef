package app.te.alo_chef.domain.my_locations.repository

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface MyLocationsRepository {
    suspend fun getMyLocations(): Resource<BaseResponse<List<LocationsData>>>
    suspend fun addNewLocation(addLocationRequest: AddLocationRequest): Resource<BaseResponse<*>>
    suspend fun deleteLocation(addLocationRequest: AddLocationRequest): Resource<BaseResponse<*>>
    suspend fun updateLocation(addLocationRequest: AddLocationRequest): Resource<BaseResponse<*>>
}