package app.te.alo_chef.presentation.maps

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FragmentMapBinding
import app.te.alo_chef.presentation.base.BaseFragment
import app.te.alo_chef.presentation.base.extensions.setNavigationResult
import app.te.alo_chef.presentation.base.utils.showNoApiErrorAlert
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback, MapEventListener {

    override
    fun getLayoutId() = R.layout.fragment_map

    @Inject
    lateinit var permissionManager: PermissionManager
    lateinit var googleMap: GoogleMap
    lateinit var latLng: LatLng

    @Inject
    lateinit var locationManager: LocationManager

    private val permissionsResult = requestAppPermissions { allIsGranted, _ ->
        if (allIsGranted) {
            checkIfLocationEnabled()
        } else {
            showNoApiErrorAlert(requireActivity(), getString(R.string.not_all_permission_accepted))
        }
    }

    override fun setBindingVariables() {
        binding.mapEventListener = this
        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        //init map here
        mapFragment?.getMapAsync(this)

        if (permissionManager.hasAllLocationPermissions()) {
            checkIfLocationEnabled()
        } else {
            permissionsResult?.launch(permissionManager.getAllLocationPermissions())
        }
    }

    private fun checkIfLocationEnabled() {
        if (locationManager.isLocationEnabled(requireContext())) {
            getLocationNow()
        } else {
            buildAlertMessageNoGps()
        }
    }

    private fun getLocationNow() {
        locationManager.requestNewLocationData(false) {
            latLng = LatLng(it.latitude, it.longitude)
            addMarker()
        }
    }

    private fun addMarker() {
        googleMap.setOnMapLoadedCallback {
            onCameraChangeListener()
//      googleMap.addMarker(
//        MarkerOptions()
//          .title(getString(R.string.your_location))
//          .position(latLng)
//      )
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLng, 16F
                )
            )
        }

    }

    private fun onCameraChangeListener() {
        googleMap.setOnCameraIdleListener { // camera not moving
            latLng = googleMap.cameraPosition.target
        }
    }

    private fun buildAlertMessageNoGps() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.location_is_off))
            .setMessage(getString(R.string.location_off_body_message))
            .setPositiveButton(R.string.yes) { dialog, _ ->
                locationManager.openLocationGPS(requireActivity()) {
                    if (it is Boolean) getLocationNow()
                }
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap
        mMap.isBuildingsEnabled = true
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
    }

    override fun detectLocation() {
        if (this::latLng.isInitialized) {
            val mapExtractedData = MapExtractedData()
            mapExtractedData.latitude = latLng.latitude
            mapExtractedData.longitude = latLng.longitude
            val returnedLiveData = MutableLiveData<MapExtractedData>()
            returnedLiveData.value = mapExtractedData
            Log.e("detectLocation", "detectLocation: $mapExtractedData")
            setNavigationResult(returnedLiveData)
        }
    }

}