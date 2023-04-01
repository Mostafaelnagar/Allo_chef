package app.te.alo_chef.presentation.account

interface AccountEventListener {
    fun openWallet()
    fun openMyOrders()
    fun openMyLocations()
    fun openProfile()
    fun openSubscribe()
    fun changeLanguage()
    fun logout()
}