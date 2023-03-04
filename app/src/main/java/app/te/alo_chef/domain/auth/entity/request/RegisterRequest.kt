package app.te.alo_chef.domain.auth.entity.request

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.ObservableField
import app.te.alo_chef.domain.utils.BaseRequest
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class RegisterRequest : BaseRequest(), Parcelable {

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
    @Transient
    var confirmPassword: String = ""
        set(value) {
            validation.confirmPasswordError.set(null)
            field = value
        }
    var password: String = ""
        set(value) {
            validation.passwordError.set(null)
            field = value
        }
    var email: String = ""
    var gender: String = "0"
    var firebase_token: String = "0"
    var provider_id: String? = null
    var objective: String? = null


    @Transient
    var validation: RegisterValidationException = RegisterValidationException()

}

@Keep
class RegisterValidationException {
    var nameError: ObservableField<String> = ObservableField<String>()
    var phoneError: ObservableField<String> = ObservableField<String>()
    var passwordError: ObservableField<String> = ObservableField<String>()
    var confirmPasswordError: ObservableField<String> = ObservableField<String>()
    var emailError: ObservableField<String> = ObservableField<String>()


}
