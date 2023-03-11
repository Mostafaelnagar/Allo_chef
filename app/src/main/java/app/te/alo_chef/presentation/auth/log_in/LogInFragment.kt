package app.te.alo_chef.presentation.auth.log_in

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentLogInBinding
import app.te.alo_chef.domain.auth.entity.request.SocialLogInRequest
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.auth.sign_up.SignUpViewModel
import app.te.alo_chef.presentation.auth.social.SocialHelper
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(), LoginEventListener {
    private val viewModelRegister: SignUpViewModel by activityViewModels()
    private val viewModel: LogInViewModel by viewModels()
    private val socialHelper = SocialHelper()

    override
    fun getLayoutId() = R.layout.fragment_log_in

    override
    fun setBindingVariables() {
        binding.request = viewModel.request
        binding.eventListener = this
        socialHelper.setUpGoogleOneTap(requireActivity())

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
        lifecycleScope.launch {
            viewModel.userLocalUseCase.invoke().collect { user ->
                openActivityAndClearStack(HomeActivity::class.java)
            }
        }

    }

    override fun socialAction(action: String) {
        if (action == Constants.GOOGLE_SIGN_IN)
            socialHelper.displaySignIn(oneTapResult)
        else {
            binding.loginButton.performClick()
            viewModelRegister.registerUiState.request =
                socialHelper.setUpFacebook(binding.loginButton)

        }
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

    private val oneTapResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            socialHelper.callbackManager.onActivityResult(
                Constants.RC_SIGN_IN,
                result.resultCode,
                result.data
            )
            viewModelRegister.registerUiState.request = socialHelper.googleSignResult(result.data)
            viewModel.socialLogin(
                if (viewModelRegister.registerUiState.request.objective == "google")
                    SocialLogInRequest(
                        google_id = viewModelRegister.registerUiState.request.provider_id
                    )
                else
                    SocialLogInRequest(
                        facebook_id = viewModelRegister.registerUiState.request.provider_id
                    )
            )
        }
}