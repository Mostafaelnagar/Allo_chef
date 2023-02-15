package app.te.alo_chef.data.general.repository

import app.te.alo_chef.data.general.data_source.remote.GeneralRemoteDataSource
import app.te.alo_chef.domain.general.entity.UpdateFirebaseTokenRequest
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.general.repository.GeneralRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class GeneralRepositoryImpl @Inject constructor(
    private val remoteDataSource: GeneralRemoteDataSource
) : GeneralRepository {

    override suspend fun getCities(): Resource<BaseResponse<List<CityModel>>> =
        remoteDataSource.getCities()

    override suspend fun updateFirebaseTokenRequest(updateFirebaseTokenRequest: UpdateFirebaseTokenRequest): Resource<BaseResponse<*>> =
        remoteDataSource.updateFirebaseToken(updateFirebaseTokenRequest)
}