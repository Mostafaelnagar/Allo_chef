package app.te.alo_chef.domain.auth.use_case

import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.profile.entity.UpdatePassword
import app.te.alo_chef.domain.utils.*
import app.te.alo_chef.presentation.base.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(request: UpdatePassword): Flow<Resource<BaseResponse<*>>> = flow {
        if (checkUpdatePasswordValidation(request)) {
            if (request.password_confirmation != request.password) {
                emit(
                    Resource.Failure(
                        FailureStatus.EMPTY,
                        message = Constants.NOT_MATCH_PASSWORD.toString(),
                    )
                )
            } else {
                emit(Resource.Loading)
                val result = authRepository.changePassword(request)
                emit(result)
            }
        }
    }.flowOn(Dispatchers.IO)

    fun authChangePassword(request: UpdatePassword): Flow<Resource<BaseResponse<*>>> = flow {
        if (checkChangePasswordValidation(request)) {
            if (request.password_confirmation != request.password) {
                emit(
                    Resource.Failure(
                        FailureStatus.EMPTY,
                        message = Constants.NOT_MATCH_PASSWORD.toString(),
                    )
                )
            } else {
                emit(Resource.Loading)
                val result = authRepository.authChangePassword(request)
                emit(result)
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun checkUpdatePasswordValidation(request: UpdatePassword): Boolean {
        var isValid = true
        if (!request.isForget && request.old_password.isEmpty()) {
            request.validation.oldPasswordError.set(Constants.EMPTY)
            isValid = false
        }
        if (request.password.isEmpty()) {
            request.validation.newPasswordError.set(Constants.EMPTY)
            isValid = false
        }
        if (request.password_confirmation.isEmpty()) {
            request.validation.newPasswordConfirmError.set(Constants.EMPTY)
            isValid = false
        }
        return isValid
    }

    private fun checkChangePasswordValidation(request: UpdatePassword): Boolean {
        var isValid = true
        if (request.password.isEmpty()) {
            request.validation.newPasswordError.set(Constants.EMPTY)
            isValid = false
        }
        if (request.password_confirmation.isEmpty()) {
            request.validation.newPasswordConfirmError.set(Constants.EMPTY)
            isValid = false
        }
        return isValid
    }

}