package app.te.alo_chef.presentation.account

interface AccountEventListener {
  fun openProfile()
  fun openFavorite()
  fun openSubscribe()
  fun openChangeLanguage()
  fun logout()
}