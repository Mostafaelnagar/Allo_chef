package app.te.alo_chef.domain.general.paginate

import androidx.annotation.Keep

@Keep
open class Paginate(
     val meta: Meta = Meta(),
     val links: Links = Links(),
)