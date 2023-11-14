package com.corcida.dmovie.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.corcida.dmovie.R
import com.corcida.dmovie.databinding.FragmentMapBinding
import com.corcida.dmovie.ui.common.NoDataDialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback{

    private var _binding: FragmentMapBinding? = null
    private lateinit var map: GoogleMap
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var noDataDialogFragment : NoDataDialogFragment

    private val binding get() = _binding!!
    private val viewModel by viewModels<MapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        setViews()
        viewModel.model.observe(viewLifecycleOwner, Observer(this::observeUiModel))
        return binding.root
    }

    override fun onMapReady(googlMap: GoogleMap) {
        map = googlMap
        viewModel.getData()
    }

    private fun setViews() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationsAdapter = LocationsAdapter { viewModel.selectALocation(it) }
        binding.locations.adapter = locationsAdapter
        noDataDialogFragment = NoDataDialogFragment {
            noDataDialogFragment.dismiss()
            viewModel.getData()
        }
        binding.refreshLayout.isEnabled = false
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getData()
        }
    }

    private fun observeUiModel(model: MapUiModel){
        when (model) {
            is MapUiModel.Content -> {
                setLoading(false)
                binding.refreshLayout.isRefreshing = false
                binding.refreshLayout.isEnabled = true
                if (model.locations.isNotEmpty()) {
                    locationsAdapter.locations = model.locations
                    model.locations.firstOrNull { it.selected }?.let { placeMarker(it) }
                }
                setDataVisibility(model.locations.isNotEmpty())
            }
            is MapUiModel.Error -> {
                binding.refreshLayout.isEnabled = true
                binding.refreshLayout.isRefreshing = false
                noDataDialogFragment.show(childFragmentManager, "No data dialog")
            }
            MapUiModel.Loading -> {
                binding.refreshLayout.isEnabled = false
                setLoading(true)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.mapLayout.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
        if (isLoading) binding.shimmerViewContainer.startShimmer() else binding.shimmerViewContainer.hideShimmer()
        binding.shimmerViewContainer.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setDataVisibility(isDataVisible : Boolean){
        binding.locations.visibility = if (isDataVisible) View.VISIBLE else View.GONE
        binding.locationsError.root.visibility = if (!isDataVisible) View.VISIBLE else View.GONE
    }

    private fun placeMarker(location: LocationModel){
        val place = LatLng(location.latitude, location.longitude)
        if (this::map.isInitialized) {
            map.addMarker(
                MarkerOptions()
                    .position(place)
                    .title(location.address)
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}