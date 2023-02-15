package app.te.alo_chef.data.remote

import app.te.alo_chef.data.local.preferences.AppPreferences
import javax.inject.Inject

class BaseRemoteRepositoryImpl @Inject constructor(
  private val appPreferences: AppPreferences
) : BaseRemoteRepository {
  override
  suspend fun clearPreferences() = appPreferences.clearPreferences()
}