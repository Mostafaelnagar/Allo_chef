package app.te.alo_chef.presentation.contactus

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentContactUsBinding
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.settings.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>(), ContactUsEventListeners {
    private val viewModel: SettingsViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_contact_us
    override fun setBindingVariables() {
        binding.eventListener = this
        binding.request = viewModel.contactUsRequest
    }

    override fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.contactResponse.collect {
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
                        handleApiError(it)
                    }
                    else -> {}
                }
            }
        }

    }

    override fun sendContact() {
        viewModel.sendContact()
    }

}