package app.te.alo_chef.presentation.profile

import app.te.alo_chef.presentation.base.events.BaseEventListener

interface ProfileEventListener : BaseEventListener {
  fun updateProfile()
  fun pickImage()
}