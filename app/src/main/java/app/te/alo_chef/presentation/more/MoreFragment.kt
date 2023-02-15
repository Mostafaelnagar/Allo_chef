package app.te.alo_chef.presentation.more

import app.te.alo_chef.BuildConfig
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentMoreBinding
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(), MoreEventListener {

    override
    fun getLayoutId() = R.layout.fragment_more
    override fun setBindingVariables() {
        updateVersionName()
        binding.eventListener = this
    }

    override fun onResume() {
        super.onResume()
        setupStatusBar(R.color.account_status_bar)
    }
    private fun updateVersionName() {
        binding.versionName.text =
            getMyString(R.string.version_name).plus(" ( ").plus(BuildConfig.VERSION_NAME).plus(" )")
    }

    override fun openTeam() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToTeamFragment())
    }

    override fun openAbout() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToAboutFragment())
    }

    override fun openAbout80Fekra() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToAboutFekraFragment())
    }

    override fun openPrivacyPolicy() {
        navigateSafe(MoreFragmentDirections.actionMoreFragmentToPrivacyFragment())
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