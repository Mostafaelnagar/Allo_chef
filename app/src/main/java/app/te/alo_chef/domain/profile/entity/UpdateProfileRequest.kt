package app.te.alo_chef.domain.profile.entity

import androidx.annotation.Keep
import androidx.databinding.ObservableField
import app.te.alo_chef.domain.utils.BaseRequest

@Keep
class UpdateProfileRequest : BaseRequest() {
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

    var email: String = ""
        set(value) {
            validation.emailError.set(null)
            field = value
        }
    var password: String? = null
        set(value) {
            validation.passwordError.set(null)
            field = value
        }

    @Transient
    var validation: UpdateProfileValidationException = UpdateProfileValidationException()

    @Transient
    var userImage: String = ""

}


@Keep
class UpdateProfileValidationException {

    @Transient
    var nameError: ObservableField<String> = ObservableField<String>()

    @Transient
    var emailError: ObservableField<String> = ObservableField<String>()

    @Transient
    var phoneError: ObservableField<String> = ObservableField<String>()

    var passwordError: ObservableField<String> = ObservableField<String>()

}