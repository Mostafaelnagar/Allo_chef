package app.te.alo_chef.presentation.my_locations

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.te.alo_chef.R
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.databinding.FragmentMyLocationBinding
import app.te.alo_chef.domain.my_locations.entity.AddLocationRequest
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.*
import app.te.alo_chef.presentation.my_locations.adapters.MyLocationsAdapters
import app.te.alo_chef.presentation.my_locations.listeners.LocationsListener
import app.te.alo_chef.presentation.my_locations.view_models.LocationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyLocationsFragment : BaseFragment<FragmentMyLocationBinding>(), LocationsListener {

    private val viewModel: LocationsViewModel by viewModels()

    override fun setBindingVariables() {
        binding.event = this
        viewModel.locationsAdapters = MyLocationsAdapters(this)
        binding.rcLocations.adapter = viewModel.locationsAdapters
    }

    override fun observeAPICall() {
        lifecycleScope.launchWhenResumed {
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                viewModel.deleteLocationResponse.collect {
                    when (it) {
                        Resource.Loading -> {
                            hideKeyboard()
                            showLoading()
                        }

                        is Resource.Success -> {
                            hideLoading()
                            viewModel.getMyLocations()
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
        val request = AddLocationRequest().apply {
            title = item.title
            street = item.street
            floor = item.floor
            region_id = item.region_id
            city_id = item.city_id
            location_id = item.id.toString()
            lat = item.lat
            lng = item.lng
            cityName = item.cityName
            regionName = item.regionName
        }
        toEditPlace(request)
    }

    override fun deleteLocation(locationId: Int) {
        val request = AddLocationRequest()
        request.location_id = locationId.toString()
        viewModel.deleteLocation(request)
    }

    override fun toAddPlace() {
        navigateSafe(
            MyLocationsFragmentDirections.actionMyLocationsFragmentToAddLocationFragment(
                AddLocationRequest()
            )
        )
    }

    override fun toEditPlace(request: AddLocationRequest) {
        navigateSafe(
            MyLocationsFragmentDirections.actionMyLocationsFragmentToAddLocationFragment(request)
        )
    }

    override fun saveAsDefault(item: LocationsData) {
        viewModel.saveDefaultLocation(item)
    }

    override fun onResume() {
        super.onResume()
        showMessage("RESUME")
        viewModel.getMyLocations()
    }
}