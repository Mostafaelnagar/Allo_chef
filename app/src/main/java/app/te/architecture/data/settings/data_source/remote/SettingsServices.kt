package app.te.architecture.data.settings.data_source.remote

import app.te.architecture.domain.settings.models.AboutData
import app.te.architecture.domain.settings.models.ContactUs
import app.te.architecture.domain.settings.models.Teams
import app.te.architecture.domain.utils.BaseResponse
import retrofit2.http.*

interface SettingsServices {
  @GET("v1/app/pages/{page}")
  suspend fun about(@Path("page") page: String): BaseResponse<AboutData>

  @GET("v1/app/teams")
  suspend fun teams(): BaseResponse<List<Teams>>

  @GET("v1/app/links")
  suspend fun getContacts(): BaseResponse<List<ContactUs>>


}