package app.te.alo_chef.data.profile.repository

import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.profile.repository.ProfileRepository
import app.te.alo_chef.data.profile.data_source.ProfileDataSource
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileDataSource,
    private val appPreferences: AppPreferences
) : ProfileRepository {
    override suspend fun getProfile(): Resource<BaseResponse<UserResponse>> =
        remoteDataSource.getProfile()

    override suspend fun updateProfile(request: UpdateProfileRequest): Resource<BaseResponse<UserResponse>> {
        val response = remoteDataSource.updateProfile(request)
        if (response is Resource.Success) {
            appPreferences.saveUser(response.value.data)
        }
        return response
    }


}