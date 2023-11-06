package app.te.alo_chef.domain.utils.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)