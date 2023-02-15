package app.te.alo_chef.presentation.base.custom_views

import android.view.Window
import app.te.alo_chef.R
import app.te.alo_chef.databinding.LayoutTesBinding
import app.te.alo_chef.presentation.base.BaseDialog
import app.te.alo_chef.presentation.base.extensions.getMyDrawable
import app.te.alo_chef.presentation.base.utils.openBrowser

class TesDialog : BaseDialog<LayoutTesBinding>() {
    override fun getLayoutId(): Int = R.layout.layout_tes
    override fun setUpViews() {
        binding.tvGrandUrl.setOnClickListener {
            openBrowser(requireActivity(), binding.tvGrandUrl.text.toString())
        }
        binding.imgGrandClose.setOnClickListener {
            dismiss()
        }
    }

    override fun updateLayoutParams() {
        val params = dialog!!.window!!.attributes
        dialog!!.window!!.attributes = params
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(getMyDrawable(R.drawable.corner_view_gray_border))

    }
}