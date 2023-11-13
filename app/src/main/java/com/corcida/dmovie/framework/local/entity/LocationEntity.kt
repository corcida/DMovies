package com.corcida.dmovie.framework.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.corcida.dmovie.framework.local.converter.UserConverters
import com.corcida.domain.Movie

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: String
)