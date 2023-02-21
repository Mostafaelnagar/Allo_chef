package app.te.alo_chef.presentation.checkout.listener

interface CheckoutListener {
    fun callSavedLocation(dates: Int)
    fun changeDeliveryAddress()
    fun openPromoDialog()
    fun openDeliveryTimes()
}