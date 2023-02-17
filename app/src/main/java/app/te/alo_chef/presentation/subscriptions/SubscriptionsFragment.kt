package app.te.alo_chef.presentation.subscriptions

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.subscriptions.dto.SubscriptionData
import app.te.alo_chef.databinding.FragmentSubscriptionsBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.subscriptions.adapters.SubscriptionsAdapters
import app.te.alo_chef.presentation.subscriptions.listener.SubscriptionsListener
import app.te.alo_chef.presentation.subscriptions.ui_state.SubscriptionItemUiState
import app.te.alo_chef.presentation.subscriptions.view_model.SubscriptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SubscriptionsFragment : BaseFragment<FragmentSubscriptionsBinding>(), SubscriptionsListener {

    private val viewModel: SubscriptionsViewModel by viewModels()

    override fun setBindingVariables() {
        viewModel.subscriptionsAdapters = SubscriptionsAdapters()
        binding.viewPager.adapter = viewModel.subscriptionsAdapters
        viewModel.getSubscriptions()
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.subscriptionsResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                updateSubscriptionsAdapter(it.value.data)
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

        }
    }

    private fun updateSubscriptionsAdapter(data: List<SubscriptionData>) {
        viewModel.subscriptionsAdapters.differ.submitList(data.map { item ->
            SubscriptionItemUiState(
                item, this
            )
        })
    }


    override
    fun getLayoutId() = R.layout.fragment_subscriptions

    override fun subscribeNow() {

    }
}