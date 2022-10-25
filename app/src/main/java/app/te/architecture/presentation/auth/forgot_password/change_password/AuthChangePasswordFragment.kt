package app.te.architecture.presentation.auth.forgot_password.change_password

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import app.te.architecture.R
import app.te.architecture.databinding.FragmentAuthChangePasswordBinding
import app.te.architecture.domain.utils.Resource
import app.te.architecture.presentation.base.BaseFragment
import app.te.architecture.presentation.base.extensions.backToPreviousScreen
import app.te.architecture.presentation.base.extensions.handleApiError
import app.te.architecture.presentation.base.extensions.hideKeyboard
import app.te.architecture.presentation.base.utils.showSuccessAlert
import app.te.architecture.presentation.profile.changePassword.ChangePasswordEventListener
import app.te.architecture.presentation.profile.changePassword.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthChangePasswordFragment : BaseFragment<FragmentAuthChangePasswordBinding>(),
    ChangePasswordEventListener {

    private val viewModel: ChangePasswordViewModel by viewModels()
    val args: AuthChangePasswordFragmentArgs by navArgs()

    override
    fun getLayoutId() = R.layout.fragment_auth_change_password
    override fun onResume() {
        super.onResume()
        setupStatusBar(R.color.details_status_bar)
    }
    override
    fun setBindingVariables() {
        binding.request = viewModel.request
        binding.eventListener = this
    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.changePasswordResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        showSuccessAlert(requireActivity(), it.value.message)
                        backToPreviousScreen()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it, retryAction = { viewModel.updatePassword() })
                    }
                    Resource.Default -> {
                    }
                }
            }
        }
    }

    override fun changePassword() {
        viewModel.changePassword(args.phone)
    }

    override fun back() {
        backToPreviousScreen()
    }
}