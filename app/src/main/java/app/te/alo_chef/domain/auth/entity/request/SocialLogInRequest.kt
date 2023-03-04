package app.te.alo_chef.domain.auth.entity.request

import androidx.annotation.Keep

@Keep
data class SocialLogInRequest(
    var google_id: String? = null,
    var facebook_id: String? = null
)

