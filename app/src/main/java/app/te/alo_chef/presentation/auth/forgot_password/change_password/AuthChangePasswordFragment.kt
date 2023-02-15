package app.te.alo_chef.presentation.auth.forgot_password.change_password

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentAuthChangePasswordBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.backToPreviousScreen
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.profile.changePassword.ChangePasswordEventListener
import app.te.alo_chef.presentation.profile.changePassword.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthChangePasswordFragment : BaseFragment<FragmentAuthChangePasswordBinding>(),
    ChangePasswordEventListener {

    private val viewModel: ChangePasswordViewModel by viewModels()
    val args: AuthChangePasswordFragmentArgs by navArgs()

    override
    fun getLayoutId() = R.layout.fragment_auth_change_password

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

}