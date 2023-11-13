package com.corcida.dmovie.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.R
import com.corcida.dmovie.ui.common.adapter.MoviesViewHolder
import com.corcida.dmovie.ui.common.basicDiffUtil
import com.corcida.domain.Movie

class LocationsAdapter(
    private val listener: (LocationUIModel) -> Unit
) : RecyclerView.Adapter<LocationsViewHolder>() {

    var locations: List<LocationUIModel?> by basicDiffUtil(
        listOf(null, null, null),
        areItemsTheSame = { old, new -> old?.id == new?.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocationsViewHolder(layoutInflater.inflate(R.layout.node_locations, parent, false))
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val item = locations[position]
        holder.render(item)
        holder.itemView.setOnClickListener { item?.let { it1 -> listener(it1) } }
    }

    override fun getItemCount(): Int =  locations.size

}