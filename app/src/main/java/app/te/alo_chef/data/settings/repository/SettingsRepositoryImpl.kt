package app.te.alo_chef.data.settings.repository

import app.te.alo_chef.data.settings.data_source.remote.SettingsRemoteDataSource
import app.te.alo_chef.domain.settings.models.AboutData
import app.te.alo_chef.domain.settings.models.ContactUs
import app.te.alo_chef.domain.settings.models.Teams
import app.te.alo_chef.domain.settings.repository.SettingsRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val remoteDataSource: SettingsRemoteDataSource) :
  SettingsRepository {
  override suspend fun about(page: String): Resource<BaseResponse<AboutData>> =
    remoteDataSource.about(page)

  override suspend fun getTeam(): Resource<BaseResponse<List<Teams>>> = remoteDataSource.getTeam()
  override suspend fun getContact(): Resource<BaseResponse<List<ContactUs>>> =
    remoteDataSource.getContacts()


}