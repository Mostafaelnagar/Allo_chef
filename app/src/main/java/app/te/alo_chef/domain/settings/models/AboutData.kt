package app.te.alo_chef.domain.settings.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AboutData(
  @SerializedName("title")
  val title: String = "",

  @SerializedName("image")
  val image: String = "",

  @SerializedName("id")
  val id: Int = 0,


  )
