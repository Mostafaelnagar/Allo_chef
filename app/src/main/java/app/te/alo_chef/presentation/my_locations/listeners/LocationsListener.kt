package app.te.alo_chef.presentation.my_locations.listeners

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest

interface LocationsListener {
    fun openEdit(item: LocationsData)
    fun deleteLocation(locationId: Int)
    fun toAddPlace()
    fun toEditPlace(request: AddLocationRequest)
    fun saveAsDefault(item: LocationsData)
}