package app.te.alo_chef.presentation.account

interface AccountEventListener {
    fun openWallet()
    fun openMyOrders()
    fun openMyLocations()
    fun openProfile()
    fun openFavorite()
    fun openSubscribe()
    fun logout()
}