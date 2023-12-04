package app.te.alo_chef.presentation.splash

import androidx.fragment.app.viewModels
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentSplashBinding
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.navigateSafe
import app.te.alo_chef.presentation.base.extensions.openActivityAndClearStack
import app.te.alo_chef.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(), SplashEventListener {
    private val viewModel: SplashViewModel by viewModels()
    override fun setBindingVariables() {
        viewModel.eventListener = this
    }

    override
    fun getLayoutId() = R.layout.fragment_splash

    override fun openHome() {
        openActivityAndClearStack(HomeActivity::class.java)
    }

    override fun openOnBoarding() {
        setLanguage(Locale.getDefault().language)
        navigateSafe(SplashFragmentDirections.actionSplashFragmentToTutorialFragment())
    }

}