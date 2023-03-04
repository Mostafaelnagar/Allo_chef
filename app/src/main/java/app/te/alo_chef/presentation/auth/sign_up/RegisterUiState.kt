package app.te.alo_chef.presentation.auth.sign_up

import android.content.Context
import android.widget.RadioGroup
import androidx.databinding.Bindable
import app.te.alo_chef.R
import app.te.alo_chef.domain.auth.entity.request.RegisterRequest
import app.te.alo_chef.domain.utils.isValidEmail
import app.te.alo_chef.presentation.base.BaseUiState

class RegisterUiState : BaseUiState() {
    lateinit var context: Context

    @Bindable
    var request = RegisterRequest()

    /**
    check email validation if
    - email match regex no error show
    - email not match regex error of invalid email will show
     */
    fun onMailChange(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty() && !s.toString().isValidEmail())
            request.validation.emailError.set(context.getString(R.string.email_not_valid))
        else
            request.validation.emailError.set(null)
    }


    fun checkValidation(): Boolean {
        var isValid = true
        if (request.name.isEmpty()) {
            request.validation.nameError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (request.phone.isEmpty()) {
            request.validation.phoneError.set(context.getString(R.string.empty_warning))
            isValid = false
        }

        if (request.email.isEmpty()) {
            request.validation.emailError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (request.password.isEmpty()) {
            request.validation.passwordError.set(context.getString(R.string.empty_warning))
            isValid = false
        }

        if (request.confirmPassword.isEmpty()) {
            request.validation.confirmPasswordError.set(context.getString(R.string.empty_warning))
            isValid = false
        }
        if (request.confirmPassword != request.password) {
            request.validation.confirmPasswordError.set(context.getString(R.string.not_match_password))
            isValid = false
        }

        return isValid
    }

    fun onGenderChange(radioGroup: RadioGroup?, id: Int) {
        if (id == R.id.radio_male) {
            request.gender = "0"
        } else request.gender = "1"
    }

}