package app.te.alo_chef.presentation.auth.forgot_password

import app.te.alo_chef.presentation.base.events.BaseEventListener


interface ForgetPasswordEventListener : BaseEventListener {
  fun openConfirm(message: String)
  fun sendCode()
}