package app.te.alo_chef.domain.settings.repository

import app.te.alo_chef.domain.settings.models.AboutData
import app.te.alo_chef.domain.settings.models.ContactUsRequest
import app.te.alo_chef.domain.settings.models.Teams
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface SettingsRepository {
  suspend fun about(page: String): Resource<BaseResponse<AboutData>>
  suspend fun getTeam(): Resource<BaseResponse<List<Teams>>>
  suspend fun sendContacts(contactUsRequest: ContactUsRequest): Resource<BaseResponse<*>>
}