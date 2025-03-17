package com.chatapp.demo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chatapp.demo.domain.Message

@Dao
interface ChatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(chatMessage: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(users: List<MessageEntity>) // Insert list properly


    @Query("SELECT * FROM chats WHERE (senderId = :user1 AND receiverId = :user2) OR (senderId = :user2 AND receiverId = :user1) ORDER BY timestamp ASC")
    fun getChatBetweenUsers(user1: String, user2: String): LiveData<List<MessageEntity>>

    @Query("""
        SELECT * FROM chats 
        WHERE (senderId = :user1 AND receiverId = :user2) 
           OR (senderId = :user2 AND receiverId = :user1) 
        ORDER BY timestamp DESC 
        LIMIT 1
    """)
   suspend fun getLastMessageBetweenUsers(user1: String, user2: String): MessageEntity

}