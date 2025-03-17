package com.chatapp.demo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity) // Ensure you're inserting `UserEntity`

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>) // Insert list properly

    @Query("SELECT * FROM users WHERE uid != :myUid")
    fun getAllUsers(myUid :String): LiveData<List<UserEntity>>
}