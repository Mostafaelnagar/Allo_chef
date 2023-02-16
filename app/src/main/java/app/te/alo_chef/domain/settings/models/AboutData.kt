package app.te.alo_chef.domain.settings.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AboutData(
  @SerializedName("body")
  val body: String = "",

  @SerializedName("image")
  val image: String = "",

  @SerializedName("name")
  val name: String = "",


  )
