package app.te.alo_chef.presentation.web_view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentWebViewBinding
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.backToPreviousScreen
import app.te.alo_chef.presentation.base.extensions.decodeUrl
import app.te.alo_chef.presentation.base.extensions.onBackPressedCustomAction
import app.te.alo_chef.presentation.base.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import im.delight.android.webview.AdvancedWebView

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentWebViewBinding>(), AdvancedWebView.Listener {
    private val args: PaymentFragmentArgs by navArgs()

    override
    fun getLayoutId() = R.layout.fragment_web_view

    override
    fun setBindingVariables() {
        binding.webview.setListener(requireActivity(), this)
        binding.webview.setMixedContentAllowed(false)
        binding.webview.setDesktopMode(true)
        binding.webview.settings.setSupportMultipleWindows(true)
        binding.webview.loadUrl(decodeUrl(args.invoiceUrl))
    }

    override fun setUpViews() {
        onBackPressedCustomAction(action = {
            finishWithResult(false)
        })

    }

    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        binding.webview.onResume()
    }

    @SuppressLint("NewApi")
    override fun onPause() {
        binding.webview.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.webview.onDestroy()
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        binding.webview.onActivityResult(requestCode, resultCode, intent)
    }


    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        binding.webview.visibility = View.VISIBLE
    }

    override fun onPageFinished(url: String) {
        if (url.contains(decodeUrl(args.responseUrl))) {
            finishWithResult(true)
        }
        binding.webProgress.visibility = View.GONE
    }

    override fun onPageError(errorCode: Int, description: String, failingUrl: String?) {
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
    }

    override fun onExternalPageRequest(url: String?) {}

    private fun finishWithResult(isSuccess: Boolean) {
        val bundle = Bundle()

        bundle.putBoolean(
            Constants.PAYMENT_SUCCESS,
            isSuccess
        )
        setFragmentResult(Constants.PAYMENT_SUCCESS, bundle)

        backToPreviousScreen()
    }

}