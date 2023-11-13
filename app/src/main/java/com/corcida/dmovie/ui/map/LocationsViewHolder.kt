package com.corcida.dmovie.ui.map

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.databinding.NodeLocationsBinding

class LocationsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeLocationsBinding.bind(view)

    fun render(location: LocationUIModel?) {
        location?.let {
            binding.shimmerViewContainer.stopShimmer()
            binding.informationLayout.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
            if (location.selected) binding.card.elevation = 5f else 0f
        }?:run {
            binding.shimmerViewContainer.startShimmer()
        }

    }

}