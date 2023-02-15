package app.te.alo_chef.domain.profile.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.ObservableField
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
class UpdateProfileRequest : Parcelable {
  var name: String = ""
    set(value) {
      validation.nameError.set(null)
      field = value
    }

  var phone: String = ""
    set(value) {
      validation.phoneError.set(null)
      field = value
    }

  var city_id: String = ""
    set(value) {
      validation.cityError.set(null)
      field = value
    }

  @Transient
  var cityName: String = ""


  @Transient
  var validation: UpdateProfileValidationException = UpdateProfileValidationException()

}


@Keep
class UpdateProfileValidationException {

  @Transient
  var nameError: ObservableField<String> = ObservableField<String>()

  @Transient
  var cityError: ObservableField<String> = ObservableField<String>()

  @Transient
  var phoneError: ObservableField<String> = ObservableField<String>()

}