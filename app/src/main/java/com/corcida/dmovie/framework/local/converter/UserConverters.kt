package com.corcida.dmovie.framework.local.converter

import androidx.room.TypeConverter
import com.corcida.domain.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UserConverters {

    @TypeConverter
    fun stringToMovies(json: String?): List<Movie>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Movie>?>() {}.type
        return gson.fromJson<List<Movie>>(json, type)
    }

    @TypeConverter
    fun moviesToString(list: List<Movie>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Movie>?>() {}.type
        return gson.toJson(list, type)
    }

}