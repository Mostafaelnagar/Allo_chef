package app.te.alo_chef.presentation.wallet

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentWalletBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.wallet.ui_state.ItemTransactionUiState
import app.te.alo_chef.presentation.wallet.view_models.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>() {

    private val viewModel: WalletViewModel by viewModels()

    override fun setBindingVariables() {
        binding.rcTransactions.adapter = viewModel.walletTransactionsAdapter
        viewModel.getUserFromLocal()
        viewModel.getWalletTransactions()
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.walletTransactionsResponse.collect {
                        when (it) {
                            Resource.Loading -> {
                                hideKeyboard()
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                viewModel.walletTransactionsAdapter.differ.submitList(it.value.data.map { item ->
                                    ItemTransactionUiState(
                                        item
                                    )
                                })
                            }
                            is Resource.Failure -> {
                                hideLoading()
                                handleApiError(it)
                            }
                            else -> {}
                        }
                    }
                }
                launch {
                    viewModel.userData.collect {
                        binding.uiState = it
                    }
                }
            }

        }
    }

    override
    fun getLayoutId() = R.layout.fragment_wallet


}