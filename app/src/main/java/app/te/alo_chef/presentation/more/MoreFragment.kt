package app.te.alo_chef.presentation.more

import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentMoreBinding
import app.te.alo_chef.presentation.base.BaseFragment
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
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToSupportFragment(Constants.SUPPORT))
    }

    override fun openAbout() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToAboutFragment())
    }

    override fun shareApp() {
        shareYourApp(requireActivity())
    }

    override fun rateApp() {
        rateYourApp(requireActivity())
    }

    override fun openTerms() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToTermsFragment())
    }

    override fun openContact() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToContactFragment())
    }

    override fun openTesDialog() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToNavTes())
    }
}