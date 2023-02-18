package app.te.alo_chef.domain.my_locations.repository

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface MyLocationsRepository {
    suspend fun getMyLocations(): Resource<BaseResponse<List<LocationsData>>>
}