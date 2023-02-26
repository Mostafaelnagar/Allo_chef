package app.te.alo_chef.presentation.checkout.listener

import app.te.alo_chef.data.payment.dto.PaymentData

interface CheckoutListener {
    fun callSavedLocation(dates: Int)
    fun changeDeliveryAddress()
    fun openPromoDialog()
    fun openDeliveryTimes()
    fun finishOrder()
    fun onPaymentChange(paymentId: Int)
    fun openPaymentPage(payment_data :PaymentData, isSuccess :Boolean)
}