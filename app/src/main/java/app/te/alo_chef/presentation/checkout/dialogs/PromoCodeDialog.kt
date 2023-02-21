package app.te.alo_chef.presentation.checkout.dialogs

import android.view.Window
import androidx.fragment.app.activityViewModels
import app.te.alo_chef.R
import app.te.alo_chef.databinding.PromoCodeDialogBinding
import app.te.alo_chef.presentation.base.BaseDialog
import app.te.alo_chef.presentation.base.extensions.getMyDrawable
import app.te.alo_chef.presentation.checkout.view_model.CheckoutViewModel

class PromoCodeDialog : BaseDialog<PromoCodeDialogBinding>() {
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.promo_code_dialog

    override fun setUpViews() {
        binding.promo = checkoutViewModel.promoCodeRequest
        binding.agree.setOnClickListener {
            if (checkoutViewModel.promoCodeRequest.code.isNotEmpty()) {
                dismiss()
                checkoutViewModel.checkPromoCode()
            }
        }

    }

    override fun updateLayoutParams() {
        val params = dialog!!.window!!.attributes
        dialog!!.window!!.attributes = params
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(getMyDrawable(R.drawable.corner_view_transparent))

    }
}