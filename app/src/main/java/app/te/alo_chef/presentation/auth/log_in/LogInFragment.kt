package app.te.alo_chef.presentation.auth.log_in

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentLogInBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(), LoginEventListener {

    private val viewModel: LogInViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_log_in

    override
    fun setBindingVariables() {
        binding.request = viewModel.request
        binding.eventListener = this
    }

    override fun setUpViews() {
        viewModel.updateFireBaseToken(requireActivity())
    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.logInResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        openHome()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(
                            it,
                            retryAction = { viewModel.onLogInClicked() })
                    }
                    else -> {}
                }
            }
        }
    }


    override fun openHome() {
        back()
    }

    override fun socialAction(action: String) {

    }

    override fun login() {
        viewModel.onLogInClicked()
    }

    override fun toRegister() {
        navigateSafe(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
    }

    override fun forgetPassword() {
        navigateSafe(LogInFragmentDirections.actionLogInFragmentToForgotPasswordFragment())
    }

    override fun back() {
        requireActivity().finish()
    }


}