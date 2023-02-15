package app.te.alo_chef.data.remote

interface BaseRemoteRepository {
  suspend fun clearPreferences()
}