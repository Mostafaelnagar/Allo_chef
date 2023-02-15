package app.te.alo_chef.domain.settings.models

import androidx.annotation.Keep

@Keep
data class Teams(
  val id: Int = 0,
  val title: String = "",
  val job: String = "",
  val image: String = "",
)
