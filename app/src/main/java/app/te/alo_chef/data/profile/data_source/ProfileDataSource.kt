package app.te.alo_chef.data.profile.data_source

import app.te.alo_chef.data.remote.BaseRemoteDataSource
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import javax.inject.Inject

class ProfileDataSource @Inject constructor(private val apiService: ProfileServices) :
  BaseRemoteDataSource() {

  suspend fun getProfile() = safeApiCall {
    apiService.getProfile()
  }
 suspend fun updateProfile(request: UpdateProfileRequest) = safeApiCall {
    apiService.updateProfile(request)
  }

}