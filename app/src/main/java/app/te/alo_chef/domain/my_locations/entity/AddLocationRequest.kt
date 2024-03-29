package app.te.alo_chef.domain.my_locations.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.ObservableField
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class AddLocationRequest : Parcelable {

    @SerializedName("lat")
    var lat: String = "0.0"
        set(value) {
            latError.set(null)
            field = value
        }

    @SerializedName("lng")
    var lng: String = "0.0"

    @SerializedName("title")
    var title: String = ""
        set(value) {
            titleError.set(null)
            field = value
        }

    @SerializedName("street")
    var street: String = ""
        set(value) {
            streetError.set(null)
            field = value
        }

    @SerializedName("floor")
    var floor: String = ""
        set(value) {
            floorError.set(null)
            field = value
        }

    @SerializedName("appartment")
    var appartment: String = ""

    @SerializedName("region_id")
    var region_id: String = ""

    @SerializedName("city_id")
    var city_id: String = ""

    @SerializedName("location_id")
    var location_id: String? = null

    @Transient
    var cityName: String = ""

    @Transient
    var regionName: String = ""


    @Transient
    var latError = ObservableField<String>()

    @Transient
    var titleError = ObservableField<String>()

    @Transient
    var streetError = ObservableField<String>()

    @Transient
    var floorError = ObservableField<String>()

    @Transient
    var cityError = ObservableField<String>()

    @Transient
    var regionError = ObservableField<String>()

}
