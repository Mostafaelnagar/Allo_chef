package app.te.alo_chef.presentation.my_locations.ui_state

import android.content.Context
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.general.entity.countries.RegionsItem
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.presentation.base.BaseUiState
import javax.inject.Inject

class AddLocationUiState @Inject constructor(private val context: Context) : BaseUiState() {
    @Bindable
    var request = AddLocationRequest()
        set(value) {
            notifyPropertyChanged(BR.request)
            field = value
        }

    @Bindable
    var cityName: String = ""
        set(value) {
            request.cityError.set(null)
            notifyPropertyChanged(BR.cityName)
            field = value
        }

    @Bindable
    var regionName: String = ""
        set(value) {
            request.regionError.set(null)
            notifyPropertyChanged(BR.regionName)
            field = value
        }

    @Bindable
    var address: String = "New Cairo"
        set(value) {
            request.latError.set(null)
            notifyPropertyChanged(BR.address)
            field = value
        }
    var cities: List<CityModel> = emptyList()

    var regions: List<RegionsItem> = emptyList()


    fun validate(): Boolean {
        var isValid = true
        if (request.title.isEmpty()) {
            request.titleError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (request.street.isEmpty()) {
            request.streetError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (address.isEmpty()) {
            request.latError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (request.floor.isEmpty()) {
            request.floorError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (cityName.isEmpty()) {
            request.cityError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (regionName.isEmpty()) {
            request.regionError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        return isValid
    }

    fun updateCity(position: Int) {
        cityName = cities[position].name

        request.city_id =
            cities[position].id.toString()

        regions = cities[position].regions
        if (cities[position].regions.isNotEmpty())
            updateRegions(0)
    }

    fun updateRegions(position: Int) {
        regionName = regions[position].name

        request.region_id =
            regions[position].id.toString()
    }

    fun prepareRequestForEdit(item: LocationsData) {
        cityName = item.cityName
        regionName = item.regionName
        request.apply {
            title = item.title
            street = item.street
            floor = item.floor
            region_id = item.region_id
            city_id = item.city_id
            location_id = item.id.toString()
            lat = item.lat
            lng = item.lng
        }
    }
}