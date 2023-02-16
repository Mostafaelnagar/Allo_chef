package app.te.alo_chef.domain.settings.use_case

import app.te.alo_chef.domain.settings.models.ContactUsRequest
import app.te.alo_chef.domain.settings.repository.SettingsRepository
import app.te.alo_chef.domain.utils.*
import app.te.alo_chef.presentation.base.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ContactUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(contactUsRequest: ContactUsRequest): Flow<Resource<BaseResponse<*>>> =
        flow {
            if (checkValidation(contactUsRequest)) {
                emit(Resource.Loading)
                emit(settingsRepository.sendContacts(contactUsRequest))
            }
        }.flowOn(Dispatchers.IO)

    private fun checkValidation(request: ContactUsRequest): Boolean {
        var isValid = true
        if (request.name.isEmpty()) {
            request.validation.nameError.set(Constants.EMPTY)
            isValid = false
        }
        if (request.email.isEmpty()) {
            request.validation.emailError.set(Constants.EMPTY)
            isValid = false
        }
        if (request.message.isEmpty()) {
            request.validation.messageError.set(Constants.EMPTY)
            isValid = false
        }

        return isValid
    }
}