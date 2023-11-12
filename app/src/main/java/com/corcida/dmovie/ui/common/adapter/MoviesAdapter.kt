package com.corcida.dmovie.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.R
import com.corcida.dmovie.ui.common.basicDiffUtil
import com.corcida.domain.Movie

class MoviesAdapter : RecyclerView.Adapter<MoviesViewHolder>() {

    var movies: List<Movie?> by basicDiffUtil(
        listOf(null, null, null),
        areItemsTheSame = { old, new -> old?.id == new?.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.node_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = movies[position]
        holder.render(item)
    }

    override fun getItemCount(): Int =  movies.size

}