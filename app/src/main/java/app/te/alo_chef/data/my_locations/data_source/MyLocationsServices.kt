package app.te.alo_chef.data.my_locations.data_source

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.data.my_orders.dto.MyOrdersData
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET

interface MyLocationsServices {
    @GET("user/get-user-locations")
    suspend fun getMyLocations(): BaseResponse<List<LocationsData>>
}