package com.chatapp.demo.domain.repo

import com.chatapp.demo.data.db.MessageEntity
import com.chatapp.demo.domain.Message

interface ChatRepository {

    fun sendMessage(message: Message, onComplete: (Boolean) -> Unit)

    fun getMessages(senderId: String, receiverId: String, onResult: (List<Message>) -> Unit)

    fun getAllMessages(messageStatus: (Boolean) -> Unit)

    suspend fun getLastMessageBetweenUsers (user2: String): MessageEntity
}