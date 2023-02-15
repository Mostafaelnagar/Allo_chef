package app.te.alo_chef.presentation.auth.sign_up

import app.te.alo_chef.presentation.base.events.BaseEventListener


interface RegisterEventListener : BaseEventListener {
  fun signUp()
}