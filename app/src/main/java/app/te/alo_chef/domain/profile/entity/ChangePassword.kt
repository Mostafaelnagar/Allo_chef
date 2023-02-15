package app.te.alo_chef.domain.profile.entity

import androidx.annotation.Keep
import androidx.databinding.ObservableField

@Keep
class UpdatePassword {
    var old_password: String = ""
        set(value) {
            validation.oldPasswordError.set(null)
            field = value
        }
    var password: String = ""
        set(value) {
            validation.newPasswordError.set(null)
            field = value
        }
    var password_confirmation: String = ""
        set(value) {
            validation.newPasswordConfirmError.set(null)
            field = value
        }
    var phone: String = ""

    @Transient
    var isForget: Boolean = false

    @Transient
    var validation: UpdateValidationException = UpdateValidationException()
}

@Keep
class UpdateValidationException {
    @Transient
    var oldPasswordError: ObservableField<String> = ObservableField<String>()

    @Transient
    var newPasswordError: ObservableField<String> = ObservableField<String>()

    @Transient
    var newPasswordConfirmError: ObservableField<String> = ObservableField<String>()

}