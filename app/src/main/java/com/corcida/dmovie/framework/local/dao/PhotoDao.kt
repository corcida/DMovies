package com.corcida.dmovie.framework.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corcida.dmovie.framework.local.entity.PhotoEntity

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo")
    fun getLocations(): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(photos: List<PhotoEntity>)

    @Query("SELECT COUNT(id) FROM photo")
    fun photoCount(): Int

    @Query("DELETE FROM photo")
    fun deleteAll()
}