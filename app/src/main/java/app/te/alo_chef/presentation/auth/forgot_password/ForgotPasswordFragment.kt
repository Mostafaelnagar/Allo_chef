package app.te.alo_chef.presentation.auth.forgot_password

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentForgotPasswordBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(),
    ForgetPasswordEventListener {
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_forgot_password

    override
    fun setBindingVariables() {
        binding.request = viewModel.request
        binding.eventListener = this
    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.forgetPasswordResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        openConfirm(it.value.message)
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it, retryAction = { viewModel.sendCode() })
                    }
                    Resource.Default -> {
                    }
                }
            }
        }
    }

    override fun openConfirm(message: String) {
        navigateSafe(
            ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToConfirmCodeFragment(
                getString(R.string.forget_password), viewModel.request.phone, Constants.FORGET
            )
        )
    }

    override fun sendCode() {
        viewModel.sendCode()
    }

    override fun back() {
        backToPreviousScreen()
    }


}