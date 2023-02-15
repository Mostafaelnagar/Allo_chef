package app.te.alo_chef.presentation.settings

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.R
import app.te.alo_chef.data.settings.mapToUiState
import app.te.alo_chef.databinding.FragmentAboutBinding
import app.te.alo_chef.presentation.settings.viewModels.SettingsViewModel
import app.te.alo_chef.presentation.base.events.BaseEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(), BaseEventListener {

  private val viewModel: SettingsViewModel by viewModels()

  override
  fun getLayoutId() = R.layout.fragment_about
  override fun setBindingVariables() {
    binding.eventListener = this
    viewModel.pages("about")
  }

  override fun onResume() {
    super.onResume()
    setupStatusBar(R.color.details_status_bar)
  }
  override fun setupObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.aboutResponse.collect {
        when (it) {
          Resource.Loading -> {
            hideKeyboard()
            showLoading()
          }
          is Resource.Success -> {
            hideLoading()
            binding.uiState = it.value.data.mapToUiState()
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
  override fun back() {
    backToPreviousScreen()
  }
}