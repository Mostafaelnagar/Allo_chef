package app.te.architecture.domain.auth.entity.request

import androidx.annotation.Keep
import androidx.databinding.ObservableField

@Keep
class LogInRequest {
  var phone: String = ""
    set(value) {
      validation.emailError.set(null)
      field = value
    }
  var password: String = ""
    set(value) {
      validation.passwordError.set(null)
      field = value
    }
  @Transient
  var validation: LogInValidationException = LogInValidationException()
}

@Keep
class LogInValidationException {
  @Transient
  var emailError: ObservableField<String> = ObservableField<String>()
  @Transient
  var passwordError: ObservableField<String> = ObservableField<String>()

}
