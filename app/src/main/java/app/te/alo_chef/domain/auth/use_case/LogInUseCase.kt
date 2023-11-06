package app.te.alo_chef.domain.auth.use_case

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.request.LogInRequest
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.utils.*
import app.te.alo_chef.domain.utils.validation.ValidatePhone
import app.te.alo_chef.presentation.base.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validatePhone: ValidatePhone
) {
    operator fun invoke(
        request: LogInRequest
    ): Flow<Resource<BaseResponse<UserResponse>>> = flow {
        if (checkValidation(request)) {
            emit(Resource.Loading)
            val result = authRepository.logIn(request)
            emit(result)
        }
    }.flowOn(Dispatchers.IO)

    private fun checkValidation(request: LogInRequest): Boolean {
        var isValid = true
        val phoneResult = validatePhone.invoke(request.phone)

        if (!phoneResult.successful) {
            request.validation.emailError.set(phoneResult.errorMessage)
            isValid = false
        }
        if (request.password.isEmpty()) {
            request.validation.passwordError.set(Constants.EMPTY)
            isValid = false
        }

        return isValid
    }
}