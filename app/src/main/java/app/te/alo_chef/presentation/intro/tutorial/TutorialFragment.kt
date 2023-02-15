package app.te.alo_chef.presentation.intro.tutorial

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.domain.intro.entity.AppTutorialModel
import app.te.alo_chef.appTutorial.AppTutorialHelper
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.R
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.databinding.FragmentTutorialBinding
import app.te.alo_chef.presentation.base.extensions.openActivityAndClearStack
import app.te.alo_chef.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialFragment : BaseFragment<FragmentTutorialBinding>() {

  private val viewModel: TutorialViewModel by viewModels()

  override
  fun getLayoutId() = R.layout.fragment_tutorial

  override
  fun setBindingVariables() {
    binding.viewModel = viewModel
  }

  private fun setUpAppTutorial(tutorialModelData: List<AppTutorialModel> = ArrayList()) {
    AppTutorialHelper.Builder(requireActivity(), lifecycle)
      .setTutorialData(tutorialModelData)
      .setTitleColor(R.color.black)
      .setContentColor(R.color.medium_color)
      .setSliderContainerResourceID(R.id.tutorial_container)
      .setActiveIndicatorColor(R.color.colorPrimaryDark)
      .setInActiveIndicatorColor(R.color.black_light)
      .setAutoScrolling(false)
      .setNextButtonTextColor(R.color.white)
      .setNextButtonBackground(R.color.colorPrimaryDark)
//      .setPreviousTextColor(R.color.black)
      .setOpenAppTextColor(R.color.white)
      .setSkipTutorialClick { openIntro() }
      .create()
  }

  override
  fun setupObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.appTutorialResponse.collect {
        when (it) {
          Resource.Loading -> {
            hideKeyboard()
            showLoading()
          }
          is Resource.Success -> {
            hideLoading()
            setUpAppTutorial(it.value.data)
          }
          is Resource.Failure -> {
            hideLoading()
            handleApiError(it)
          }
          else -> {
          }
        }
      }
    }
  }

  private fun openIntro() {
    viewModel.setFirstTime(false)
    requireActivity().openActivityAndClearStack(HomeActivity::class.java)
  }
}