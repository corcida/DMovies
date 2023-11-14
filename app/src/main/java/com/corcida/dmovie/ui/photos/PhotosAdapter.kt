package com.corcida.dmovie.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.R
import com.corcida.dmovie.ui.common.basicDiffUtil
import com.corcida.domain.Movie

class PhotosAdapter : RecyclerView.Adapter<PhotosViewHolder>() {

    var photos: List<PhotoModel?> by basicDiffUtil(
        listOf(null, null, null, null),
        areItemsTheSame = { old, new -> old?.id == new?.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhotosViewHolder(layoutInflater.inflate(R.layout.node_photo, parent, false))
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = photos[position]
        holder.render(item)
    }

    override fun getItemCount(): Int =  photos.size

}