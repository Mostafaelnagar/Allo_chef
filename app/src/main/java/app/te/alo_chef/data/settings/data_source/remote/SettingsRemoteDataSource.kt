package app.te.alo_chef.data.settings.data_source.remote

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.settings.models.ContactUsRequest
import javax.inject.Inject

class SettingsRemoteDataSource @Inject constructor(private val apiService: SettingsServices) :
  BaseRemoteDataSource() {

  suspend fun about(page: String) = safeApiCall {
    apiService.about(page)
  }

  suspend fun getTeam() = safeApiCall {
    apiService.teams()
  }

  suspend fun sendContacts(contactUsRequest: ContactUsRequest) = safeApiCall {
    apiService.sendContacts(contactUsRequest)
  }

}