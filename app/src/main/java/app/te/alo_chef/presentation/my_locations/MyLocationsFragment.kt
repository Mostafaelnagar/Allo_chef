package app.te.alo_chef.presentation.my_locations

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.databinding.FragmentMyLocationBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.base.extensions.show
import app.te.alo_chef.presentation.my_locations.adapters.MyLocationsAdapters
import app.te.alo_chef.presentation.my_locations.listeners.LocationsListener
import app.te.alo_chef.presentation.my_locations.view_models.LocationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLocationsFragment : BaseFragment<FragmentMyLocationBinding>(), LocationsListener {

    private val viewModel: LocationsViewModel by activityViewModels()

    override fun setBindingVariables() {
        viewModel.locationsAdapters = MyLocationsAdapters(this)
        binding.rcLocations.adapter = viewModel.locationsAdapters
        viewModel.getMyLocations()
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.locationsResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        updateLocationsAdapter(it.value.data)
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

    private fun updateLocationsAdapter(data: List<LocationsData>) {
        if (data.isEmpty())
            checkEmptyLayout()
        viewModel.locationsAdapters.differ.submitList(data)
    }

    private fun checkEmptyLayout() {
        binding.layoutTryToLogin.container.show()
        binding.layoutTryToLogin.tvVipWarning.show()
        binding.layoutTryToLogin.icEmptyIcon.show()
        binding.layoutTryToLogin.icEmptyIcon.setImageResource(R.drawable.ic_no_places)
        binding.layoutTryToLogin.tvVipWarning.text = getString(R.string.no_places)
    }


    override
    fun getLayoutId() = R.layout.fragment_my_location


    override fun openEdit(item: LocationsData) {

    }

    override fun deleteLocation(locationId: Int) {
    }

    override fun toAddPlace() {
    }

    override fun saveAsDefault(item: LocationsData) {
        viewModel.saveDefaultLocation(item)
    }
}