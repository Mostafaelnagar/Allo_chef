package app.te.alo_chef.presentation.contactus

import app.te.alo_chef.presentation.base.events.BaseEventListener

interface ContactUsEventListeners : BaseEventListener {
  fun openContactUrl(url: String)
}