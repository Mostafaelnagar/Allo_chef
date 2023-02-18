package app.te.alo_chef.presentation.my_locations.listeners

import app.te.alo_chef.data.my_locations.dto.LocationsData

interface LocationsListener {
    fun openEdit(item: LocationsData)
    fun deleteLocation(locationId: Int)
    fun toAddPlace()
    fun saveAsDefault(item: LocationsData)
}