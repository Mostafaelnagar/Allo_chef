package app.te.alo_chef.domain.settings.models

import androidx.annotation.Keep
import androidx.databinding.ObservableField

@Keep
class ContactUsRequest {
    var name: String = ""
        set(value) {
            validation.nameError.set(null)
            field = value
        }
    var email: String = ""
        set(value) {
            validation.emailError.set(null)
            field = value
        }
    var message: String = ""
        set(value) {
            validation.messageError.set(null)
            field = value
        }

    @Transient
    var validation: ContactUsRequestValidationException = ContactUsRequestValidationException()

}

@Keep
class ContactUsRequestValidationException {
    @Transient
    var emailError: ObservableField<String> = ObservableField<String>()

    @Transient
    var nameError: ObservableField<String> = ObservableField<String>()

    @Transient
    var messageError: ObservableField<String> = ObservableField<String>()

}
