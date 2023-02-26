package app.te.alo_chef.presentation.more

import android.net.Uri
import androidx.navigation.fragment.findNavController
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentMoreBinding
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.rateYourApp
import app.te.alo_chef.presentation.base.utils.shareYourApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(), MoreEventListener {

    override
    fun getLayoutId() = R.layout.fragment_more
    override fun setBindingVariables() {
        updateVersionName()
        binding.eventListener = this
    }

    private fun updateVersionName() {
//        binding.versionName.text =
//            getMyString(R.string.version_name).plus(" ( ").plus(BuildConfig.VERSION_NAME).plus(" )")
    }

    override fun openSupport() {
        navigateSafe(DeepLinks.SUPPORT_LINK.plus(encodeUrl(Constants.SUPPORT)))
    }

    override fun openAbout() {
        navigateSafe(DeepLinks.ABOUT_LINK)
    }

    override fun shareApp() {
        shareYourApp(requireActivity())
    }

    override fun rateApp() {
        rateYourApp(requireActivity())
    }

    override fun openTerms() {
        navigateSafe(DeepLinks.TERMS_LINK)
    }

    override fun openContact() {
        navigateSafe(DeepLinks.CONTACT_LINK)
    }

    override fun openTesDialog() {
        navigateSafe(DeepLinks.TES_DIALOG_LINK)
    }
}