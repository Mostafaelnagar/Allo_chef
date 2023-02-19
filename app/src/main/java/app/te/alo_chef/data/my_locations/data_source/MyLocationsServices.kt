package app.te.alo_chef.data.my_locations.data_source

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyLocationsServices {
    @GET("user/get-user-locations")
    suspend fun getMyLocations(): BaseResponse<List<LocationsData>>

    @POST("user/add-user-location")
    suspend fun addNewLocation(@Body addLocationRequest: AddLocationRequest): BaseResponse<*>

    @POST("user/add-user-location")
    suspend fun updateLocation(@Body addLocationRequest: AddLocationRequest): BaseResponse<*>

    @POST("user/delete-location")
    suspend fun deleteLocation(@Body addLocationRequest: AddLocationRequest): BaseResponse<*>

}