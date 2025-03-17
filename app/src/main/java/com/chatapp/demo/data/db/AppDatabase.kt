package com.chatapp.demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class,MessageEntity::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun chatsDao(): ChatsDao
}