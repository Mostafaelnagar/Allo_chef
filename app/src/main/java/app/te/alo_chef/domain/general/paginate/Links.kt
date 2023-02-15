package app.te.alo_chef.domain.general.paginate

import androidx.annotation.Keep

@Keep
open class Links(
  val next: String? = null,
  val last: String = "",
  val prev: String = "",
  val first: String = "",
)