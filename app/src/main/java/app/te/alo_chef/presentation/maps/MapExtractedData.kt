package app.te.alo_chef.presentation.maps

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MapExtractedData(
  var address: String = "",
  var city: String = "",
  var latitude: Double = 0.0,
  var longitude: Double = 0.0,
) : Parcelable
