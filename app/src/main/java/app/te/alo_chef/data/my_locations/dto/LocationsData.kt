package app.te.alo_chef.data.my_locations.dto

import com.google.gson.annotations.SerializedName

data class LocationsData(
    @SerializedName("main")
    var main: String = "",
    var selected: Boolean = false,
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("user_id")
    val user_id: String = "",

    @SerializedName("region_id")
    val region_id: String = "",

    @SerializedName("city_id")
    val city_id: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("street")
    val street: String = "",

    @SerializedName("floor")
    val floor: String = "",

    @SerializedName("appartment")
    val appartment: String? = null,

    @SerializedName("region_name")
    val regionName: String = "",

    @SerializedName("city_name")
    val cityName: String = "",

    @SerializedName("lat")
    val lat: String = "",

    @SerializedName("lng")
    val lng: String = "",

    @SerializedName("delivery")
    val delivery: Int = 0
)
