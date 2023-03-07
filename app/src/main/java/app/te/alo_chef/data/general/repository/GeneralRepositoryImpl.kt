package app.te.alo_chef.data.general.repository

import app.te.alo_chef.data.general.data_source.local.GeneralLocalDataSource
import app.te.alo_chef.data.general.data_source.remote.GeneralRemoteDataSource
import app.te.alo_chef.data.general.dto.config.GeneralConfig
import app.te.alo_chef.domain.general.entity.UpdateFirebaseTokenRequest
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.general.repository.GeneralRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class GeneralRepositoryImpl @Inject constructor(
    private val remoteDataSource: GeneralRemoteDataSource,
    private val generalLocalDataSource: GeneralLocalDataSource,
) : GeneralRepository {

    override suspend fun getCities(): Resource<BaseResponse<List<CityModel>>> =
        remoteDataSource.getCities()

    override suspend fun updateFirebaseTokenRequest(updateFirebaseTokenRequest: UpdateFirebaseTokenRequest): Resource<BaseResponse<*>> =
        remoteDataSource.updateFirebaseToken(updateFirebaseTokenRequest)

    override suspend fun getGeneralConfig(): Resource<BaseResponse<GeneralConfig>> {
        val response = remoteDataSource.getGeneralConfig()
        if (response is Resource.Success) {
            generalLocalDataSource.emptyGeneralConfig()
            generalLocalDataSource.addGeneralConfig(response.value.data)
        }
        return response
    }


}