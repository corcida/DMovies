package com.corcida.dmovie.framework.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corcida.dmovie.framework.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun findUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT COUNT(id) FROM user")
    fun userCount(): Int

    @Query("DELETE FROM user")
    fun deleteAll()
}