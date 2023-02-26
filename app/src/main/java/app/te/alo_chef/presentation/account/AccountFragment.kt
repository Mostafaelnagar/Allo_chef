package app.te.alo_chef.presentation.account

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentAccountBinding
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.extensions.*
import codes.grand.pretty_pop_up.PrettyPopUpHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(), AccountEventListener {
    private val accountViewModel: AccountViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_account
    override fun setBindingVariables() {
        binding.eventListener = this
        accountViewModel.getSavedLocation()
        accountViewModel.getUserFromLocal()
    }


    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            accountViewModel.userData.collect {
                binding.uiState = it
            }
        }
        lifecycleScope.launchWhenResumed {
            accountViewModel.saveLocation.collect {
                binding.region = it
            }
        }
    }

    private fun showLogOutPopUp() {
        PrettyPopUpHelper.Builder(childFragmentManager)
            .setStyle(PrettyPopUpHelper.Style.STYLE_1_HORIZONTAL_BUTTONS)
            .setTitle(R.string.log_out)
            .setTitleColor(getMyColor(R.color.black))
            .setContent(R.string.log_out_hint)
            .setContentColor(getMyColor(R.color.black))
            .setPositiveButtonBackground(R.drawable.corner_view_primary_dark)
            .setPositiveButtonTextColor(getMyColor(R.color.white))
            .setImage(R.drawable.ic_logout)
            .setPositiveButton(R.string.log_out) {
                it.dismiss()
                openLogInScreen()
                accountViewModel.logOut()
            }
            .setNegativeButtonBackground(R.drawable.corner_view_gray_border)
            .setNegativeButtonTextColor(getMyColor(R.color.black))
            .setNegativeButton(getMyString(R.string.cancel), null)
            .create()
    }

    private fun openLogInScreen() {
        openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
    }

    override fun openWallet() {
        if (binding.uiState?.accessAccount == true)
            navigateSafe(DeepLinks.WALLET_LINK)
        else
            openLogInScreen()
    }

    override fun openMyOrders() {
        if (binding.uiState?.accessAccount == true)
            navigateSafe(DeepLinks.ORDERS_LINK)
        else
            openLogInScreen()
    }

    override fun openMyLocations() {
        if (binding.uiState?.accessAccount == true)
            navigateSafe(DeepLinks.LOCATIONS_LINK)
        else
            openLogInScreen()
    }

    override fun openProfile() {
        if (binding.uiState?.accessAccount == true)
            navigateSafe(DeepLinks.PROFILE_LINK)
        else
            openLogInScreen()
    }

    override fun openSubscribe() {
        if (binding.uiState?.accessAccount == true)
            navigateSafe(DeepLinks.SUBSCRIPTIONS_LINK)
        else
            openLogInScreen()
    }

    override fun logout() {
        if (binding.uiState?.accessAccount == true)
            showLogOutPopUp()
        else {
            openIntentActivity(AuthActivity::class.java, R.id.logInFragment)
        }
    }
}