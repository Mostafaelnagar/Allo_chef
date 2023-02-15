package app.te.alo_chef.data.intro.data_source

import app.te.alo_chef.domain.intro.entity.AppTutorialModel
import app.te.alo_chef.domain.utils.BaseResponse
import retrofit2.http.GET

interface IntroServices {

  @GET("app/app-explanation/0")
  suspend fun intro(): BaseResponse<List<AppTutorialModel>>

}