package com.corcida.dmovie.framework.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse (
    val results: List<User>
)

@Parcelize
data class User(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val image: String,
    val popularity: Float,
    @SerializedName("known_for")
    val movies: List<Movie>
): Parcelable
