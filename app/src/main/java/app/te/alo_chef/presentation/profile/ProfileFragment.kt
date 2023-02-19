package app.te.alo_chef.presentation.profile

import android.os.Build
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentProfileBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseActivity
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.profile.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), ProfileEventListener {

    private val viewModel: ProfileViewModel by viewModels()

    override
    fun getLayoutId() = R.layout.fragment_profile

    override
    fun setBindingVariables() {
        binding.request = viewModel.request
        binding.eventListener = this
    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.updateProfileResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        showSuccessAlert(requireActivity(), it.value.message)
                        back()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(
                            it,
                            retryAction = { viewModel.updateProfile() })
                    }
                    else -> {}
                }
            }
        }
    }

    override fun updateProfile() {
        viewModel.updateProfile()
    }

    override fun pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkStorage13Permissions(requireActivity()) {
                selectImage()
            }
        } else {
            checkStoragePermissions(requireActivity()) {
                selectImage()
            }
        }

    }

    private fun selectImage() {
        (requireActivity() as BaseActivity<*>).filePicker.pickImage() { result ->
            val file: File? = result?.second
            if (file != null) {
                viewModel.request.setImage(file.path, Constants.IMAGE)
                binding.memberProfile.setImageBitmap(result.first)
            }
        }
    }

    override fun back() {
        backToPreviousScreen()
    }

}