package app.te.alo_chef.presentation.auth.confirmCode

import app.te.alo_chef.presentation.base.events.BaseEventListener


interface ConfirmCodeEventListener :BaseEventListener {
  fun checkCode()
  fun resendCode()
}