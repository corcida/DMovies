package com.corcida.dmovie.framework.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse (
    val results: List<Movie>
)

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val image: String
): Parcelable
