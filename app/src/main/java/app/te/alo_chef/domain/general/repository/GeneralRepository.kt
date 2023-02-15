package app.te.alo_chef.domain.general.repository

import app.te.alo_chef.domain.general.entity.UpdateFirebaseTokenRequest
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource


interface GeneralRepository {
    suspend fun getCities(): Resource<BaseResponse<List<CityModel>>>
    suspend fun updateFirebaseTokenRequest(updateFirebaseTokenRequest: UpdateFirebaseTokenRequest): Resource<BaseResponse<*>>
}