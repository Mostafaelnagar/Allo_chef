package app.te.alo_chef.domain.general.entity

import androidx.annotation.Keep
import app.te.alo_chef.data.remote.Keys

@Keep
data class UpdateFirebaseTokenRequest(
    var fcm_token: String = "",
    var platform: String = Keys.platForm()
)
