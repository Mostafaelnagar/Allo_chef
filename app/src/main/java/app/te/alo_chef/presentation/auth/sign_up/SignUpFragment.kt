package app.te.alo_chef.presentation.auth.sign_up

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentSignUpBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.showNoApiErrorAlert
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(), RegisterEventListener {

    private val viewModel: SignUpViewModel by activityViewModels()

    override
    fun getLayoutId() = R.layout.fragment_sign_up

    override
    fun setBindingVariables() {
        viewModel.registerUiState.context = requireActivity()
        binding.uiState = viewModel.registerUiState
        binding.eventListener = this
    }

    override fun setUpViews() {
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) openPrivacy()
        }
        viewModel.updateFireBaseToken(requireActivity())

    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.registerResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        showSuccessAlert(requireActivity(), it.value.message)
                        openConfirmCode()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it, retryAction = { viewModel.register() })
                    }
                    else -> {}
                }
            }
        }
    }

    private fun openConfirmCode() {
        navigateSafe(
            SignUpFragmentDirections.actionSignUpFragmentToConfirmCodeFragment(
                getString(R.string.confirm),
                viewModel.registerUiState.request.phone,
                Constants.VERIFY
            )
        )
    }

    private fun openPrivacy() {

    }

    override fun signUp() {
        if (viewModel.registerUiState.checkValidation())
            if (binding.checkbox.isChecked)
                viewModel.register()
            else
                showNoApiErrorAlert(requireActivity(), getString(R.string.terms_accepted))
    }

    override fun back() {
        backToPreviousScreen()
    }

}