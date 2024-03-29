package app.te.alo_chef.presentation.auth.confirmCode

import android.os.CountDownTimer
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentConfirmCodeBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ConfirmCodeFragment : BaseFragment<FragmentConfirmCodeBinding>(), ConfirmCodeEventListener {

    private val viewModel: ConfirmViewModel by viewModels()
    private lateinit var countDownTimer: CountDownTimer

    override
    fun getLayoutId() = R.layout.fragment_confirm_code

    override
    fun setBindingVariables() {
        binding.request = viewModel.forgetRequest
        binding.eventListener = this
        startTimer()
    }

    override
    fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.verifyResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        openHome()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it, retryAction = { viewModel.verifyAccount() })
                    }
                    Resource.Default -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.verifyForgetResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        changePassword()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it, retryAction = { viewModel.verifyAccount() })
                    }
                    Resource.Default -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.resendResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        showSuccessAlert(requireActivity(), it.value.message)
                        binding.tvResend.isEnabled = false
                        startTimer()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it, retryAction = { viewModel.verifyAccount() })
                    }
                    Resource.Default -> {
                    }
                }
            }
        }
    }

    private fun changePassword() {
        navigateSafe(
            ConfirmCodeFragmentDirections.actionConfirmCodeFragmentToChangePasswordFragment2(
                viewModel.forgetRequest.phone
            )
        )
    }

    private fun openHome() {
        openActivityAndClearStack(HomeActivity::class.java)
    }


    override fun checkCode() {
        viewModel.verifyAccount()
    }

    override fun resendCode() {
        viewModel.resendCode()
    }

    override fun back() {
        backToPreviousScreen()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec =
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                val min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val time = "" + String.format(Locale.ENGLISH, "%02d : %02d ", min, sec)
                binding.tvForgetTimer.text = time
            }

            override fun onFinish() {
                binding.tvResend.isEnabled = true
            }
        }.start()
    }

}