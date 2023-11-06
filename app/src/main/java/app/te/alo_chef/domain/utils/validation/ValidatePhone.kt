package app.te.alo_chef.domain.utils.validation

import android.content.Context
import app.te.alo_chef.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePhone @Inject constructor(@ApplicationContext val context: Context) {
    operator fun invoke(phone: String): ValidationResult {
        if (phone.isBlank())
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.empty_warning)
            )
        if (!Pattern.matches("(01)[0-9]{9}",phone)) {
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.phone_not_valid)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}