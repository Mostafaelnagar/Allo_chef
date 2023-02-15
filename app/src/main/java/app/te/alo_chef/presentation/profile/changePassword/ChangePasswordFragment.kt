package app.te.alo_chef.presentation.profile.changePassword

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentChangePasswordBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.backToPreviousScreen
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(),
    ChangePasswordEventListener {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_change_password
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
        viewModel.updatePassword()
    }

}