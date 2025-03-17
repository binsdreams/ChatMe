package com.chatapp.demo.domain.repo

import androidx.lifecycle.LiveData
import com.chatapp.demo.data.db.MessageEntity
import com.chatapp.demo.domain.Message

interface ChatRepository {

    fun sendMessage(message: Message, onComplete: (Boolean) -> Unit)

    fun getMessages(senderId: String, receiverId: String, onResult: (List<Message>) -> Unit)

    fun getAllMessages(onResult: (List<MessageEntity>) -> Unit)

    suspend fun getLastChats(): List<MessageEntity>
}