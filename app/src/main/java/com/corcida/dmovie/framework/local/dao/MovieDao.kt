package com.corcida.dmovie.framework.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corcida.dmovie.framework.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE type = :type")
    fun findMovies(type: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT COUNT(id) FROM movie WHERE type = :type")
    fun moviesCount(type: String): Int

}