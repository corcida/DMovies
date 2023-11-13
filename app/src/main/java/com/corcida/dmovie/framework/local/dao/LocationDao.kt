package com.corcida.dmovie.framework.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corcida.dmovie.framework.local.entity.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocations(locations: List<LocationEntity>)

    @Query("SELECT COUNT(id) FROM location")
    fun locationCount(): Int

    @Query("DELETE FROM location")
    fun deleteAll()
}