package com.corcida.dmovie.ui.map

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.databinding.NodeLocationsBinding

class LocationsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeLocationsBinding.bind(view)

    fun render(location: LocationModel?) {
        location?.let {
            binding.shimmerViewContainer.stopShimmer()
            binding.informationLayout.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
            binding.card.cardElevation = if (location.selected)  12f else 0f
            binding.locationDate.text = location.date
            binding.locationTitle.text = location.address
        }?:run {
            binding.shimmerViewContainer.startShimmer()
        }

    }

}