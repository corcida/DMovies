package com.corcida.dmovie.ui.common.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.databinding.NodeMovieBinding
import com.corcida.dmovie.ui.common.loadUrl
import com.corcida.domain.Movie

class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeMovieBinding.bind(view)

    fun render(movie: Movie?) {
        movie?.let {
            binding.photo.loadUrl(it.image)
            binding.label.text = it.title
            binding.shimmerViewContainer.stopShimmer()
            binding.photo.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
        }?:run {
            binding.shimmerViewContainer.startShimmer()
        }

    }

}