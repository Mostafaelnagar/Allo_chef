package app.te.alo_chef.presentation.auth.log_in

import app.te.alo_chef.presentation.base.events.BaseEventListener

interface LoginEventListener : BaseEventListener {
    fun login()
    fun toRegister()
    fun forgetPassword()
    fun openHome()
    fun socialAction(action: String)
}