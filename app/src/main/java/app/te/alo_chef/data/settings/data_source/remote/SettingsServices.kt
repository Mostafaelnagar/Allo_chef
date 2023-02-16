package app.te.alo_chef.data.settings.data_source.remote

import app.te.alo_chef.domain.settings.models.AboutData
import app.te.alo_chef.domain.settings.models.ContactUsRequest
import app.te.alo_chef.domain.settings.models.Teams
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.*

interface SettingsServices {
  @GET("app/{page}")
  suspend fun about(@Path("page") page: String): BaseResponse<AboutData>

  @GET("app/teams")
  suspend fun teams(): BaseResponse<List<Teams>>

  @POST("app/contact-us")
  suspend fun sendContacts(@Body contactUsRequest: ContactUsRequest): BaseResponse<*>


}