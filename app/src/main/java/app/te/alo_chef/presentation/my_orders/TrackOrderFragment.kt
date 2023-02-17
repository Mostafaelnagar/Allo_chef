package app.te.alo_chef.presentation.my_orders

import androidx.fragment.app.activityViewModels
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentTrackOrderBinding
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.my_orders.view_model.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrackOrderFragment : BaseFragment<FragmentTrackOrderBinding>() {

    private val viewModel: OrdersViewModel by activityViewModels()
    override fun setUpViews() {
        binding.uiState = viewModel.selectedItemUiState
    }

    override
    fun getLayoutId() = R.layout.fragment_track_order
}