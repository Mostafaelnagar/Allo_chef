package app.te.alo_chef.domain.auth.entity.request

import androidx.annotation.Keep

@Keep
class SocialLogInRequest {
  var social_type: String = ""
  var social_id: String = ""
  var device_token: String = ""
  var email: String = ""
}

