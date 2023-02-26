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

    fun openPayment(invoiceURL: String, responseURL: String, title: String): String =
          "android-app://payment.alo_chef/${encodeUrl(invoiceURL)}/${encodeUrl(responseURL)}/${title}"


}