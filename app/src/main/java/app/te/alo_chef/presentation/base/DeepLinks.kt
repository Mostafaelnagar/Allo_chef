package app.te.alo_chef.presentation.base

import androidx.navigation.NavOptions
import app.te.alo_chef.R
import app.te.alo_chef.presentation.base.extensions.encodeUrl

object DeepLinks {

    fun setNavOptions(): NavOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.anim_slide_in_right)
            .setExitAnim(R.anim.anim_slide_out_left)
            .setPopEnterAnim(R.anim.anim_slide_in_left)
            .setPopExitAnim(R.anim.anim_slide_out_right)
            .build()

    /**
     * More Deep links
     */
    const val ABOUT_LINK = "android-app://about.alo_chef"
    const val TERMS_LINK = "android-app://terms.alo_chef"
    const val PRIVACY_LINK = "android-app://privacy.alo_chef"
    const val CONTACT_LINK = "android-app://contact_us.alo_chef"
    const val SUPPORT_LINK = "android-app://support.alo_chef/"
    const val TES_DIALOG_LINK = "android-app://owner.alo_chef"

    /**
     * Account Deep links
     */
    const val LANG_LINK = "android-app://language.alo_chef"
    const val PROFILE_LINK = "android-app://profile.alo_chef"
    const val WALLET_LINK = "android-app://wallet.alo_chef"
    const val ORDERS_LINK = "android-app://orders.alo_chef"
    const val LOCATIONS_LINK = "android-app://locations.alo_chef"
    const val SUBSCRIPTIONS_LINK = "android-app://subscriptions.alo_chef"
    const val CART_LINK = "android-app://cart.alo_chef/"
    const val TRACK_ORDER_LINK = "android-app://track_order.alo_chef/"

    fun openPayment(invoiceURL: String, responseURL: String, title: String): String =
        "android-app://payment.alo_chef/${encodeUrl(invoiceURL)}/${encodeUrl(responseURL)}/${title}"

    fun openCart(cartCount: Int): String = CART_LINK.plus(cartCount)
    fun openOrderDetails(orderId: Int): String = TRACK_ORDER_LINK.plus(orderId)


}