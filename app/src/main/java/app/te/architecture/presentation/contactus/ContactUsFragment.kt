package app.te.architecture.presentation.contactus

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.architecture.domain.utils.Resource
import app.te.architecture.presentation.base.BaseFragment
import app.te.architecture.presentation.base.extensions.*
import app.te.architecture.R
import app.te.architecture.databinding.FragmentContactUsBinding
import app.te.architecture.domain.settings.models.ContactUs
import app.te.architecture.presentation.base.utils.openBrowser
import app.te.architecture.presentation.base.utils.openDial
import app.te.architecture.presentation.settings.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import app.te.architecture.presentation.settings.adapters.ContactUsAdapter

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>(), ContactUsEventListeners {
  private val viewModel: SettingsViewModel by viewModels()
  private lateinit var contactUsAdapter: ContactUsAdapter

  override
  fun getLayoutId() = R.layout.fragment_contact_us
  override fun setBindingVariables() {
    binding.eventListener = this
    contactUsAdapter = ContactUsAdapter(this)
    viewModel.getContactLinks()
  }

  override fun onResume() {
    super.onResume()
    setupStatusBar(R.color.details_status_bar)
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
            setUpContact(it.value.data)
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

  private fun setUpContact(data: List<ContactUs>) {
    contactUsAdapter.differ.submitList(viewModel.mapToContactUiState(data))
    binding.rcContact.setUpAdapter(contactUsAdapter, "3", "1")

  }

  override fun openContactUrl(url: String) {
    if (url.isNumeric(url))
      openDial(requireContext(), url)
    else
      openBrowser(requireContext(), url)
  }

  override fun back() {
    backToPreviousScreen()
  }
}