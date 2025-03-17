package com.chatapp.demo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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
        WHERE senderId = :currentUserId OR receiverId = :currentUserId 
        GROUP BY CASE 
            WHEN senderId = :currentUserId THEN receiverId 
            ELSE senderId 
        END
        ORDER BY timestamp DESC
    """)
    suspend fun getLastMessagesForEachUser(currentUserId: String): List<MessageEntity>

}