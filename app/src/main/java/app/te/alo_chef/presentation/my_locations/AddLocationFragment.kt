package app.te.alo_chef.presentation.my_locations

import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentAddPlaceBinding
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.domain.utils.showCityPopUp
import app.te.alo_chef.domain.utils.showRegionsPopUp
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.backToPreviousScreen
import app.te.alo_chef.presentation.base.extensions.handleApiError
import app.te.alo_chef.presentation.base.extensions.hideKeyboard
import app.te.alo_chef.presentation.base.utils.showSuccessAlert
import app.te.alo_chef.presentation.my_locations.listeners.AddLocationListener
import app.te.alo_chef.presentation.my_locations.view_models.LocationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLocationFragment : BaseFragment<FragmentAddPlaceBinding>(), AddLocationListener {

    private val viewModel: LocationsViewModel by activityViewModels()

    override fun setBindingVariables() {
        binding.event = this
        binding.uiState = viewModel.addLocationUiState
        viewModel.getCities()
    }

    override fun observeAPICall() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.citiesResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        viewModel.addLocationUiState.cities = it.value.data
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        handleApiError(it)
                    }
                    else -> {}
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.addLocationResponse.collect {
                when (it) {
                    Resource.Loading -> {
                        hideKeyboard()
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        showSuccessAlert(requireActivity(), it.value.message)
                        backToPreviousScreen()
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


    override
    fun getLayoutId() = R.layout.fragment_add_place
    override fun addNewLocation() {
        viewModel.addNewLocation()
    }

    override fun openMap() {

    }

    override fun chooseCities() {
        if (viewModel.addLocationUiState.cities.isNotEmpty()) {
            val popUp = showCityPopUp(
                requireActivity(),
                binding.inputCity,
                viewModel.addLocationUiState.cities
            )
            popUp.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    viewModel.addLocationUiState.updateCity(item.itemId)
                    return true
                }

            })
        } else {
            viewModel.getCities()
        }
    }

    override fun chooseRegions() {
        if (viewModel.addLocationUiState.regions.isNotEmpty()) {
            val popUp = showRegionsPopUp(
                requireActivity(),
                binding.inputRegion,
                viewModel.addLocationUiState.regions
            )
            popUp.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    viewModel.addLocationUiState.updateRegions(item.itemId)
                    return true
                }

            })
        }
    }

}