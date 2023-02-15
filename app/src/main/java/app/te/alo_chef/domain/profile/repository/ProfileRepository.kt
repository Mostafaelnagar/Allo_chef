package app.te.alo_chef.domain.profile.repository

import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.profile.entity.UpdateProfileRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource

interface ProfileRepository {
  suspend fun getProfile(): Resource<BaseResponse<UserResponse>>
  suspend fun updateProfile(request: UpdateProfileRequest): Resource<BaseResponse<UserResponse>>
}