package com.chatapp.demo.data

import android.util.Log
import com.chatapp.demo.data.db.ChatsDao
import com.chatapp.demo.data.db.MessageEntity
import com.chatapp.demo.domain.Message
import com.chatapp.demo.domain.repo.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Repository for Firebase Operations
class ChatRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore,
                                             private val chatsDao: ChatsDao) : ChatRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun sendMessage(message: Message, onComplete: (Boolean) -> Unit) {
        firestore.collection("chats").add(message)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    override fun getMessages(
        senderId: String,
        receiverId: String,
        onResult: (List<Message>) -> Unit) {

        firestore.collection("chats")
            .where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("senderId", senderId),
                        Filter.equalTo("receiverId", receiverId)
                    ),
                    Filter.and(
                        Filter.equalTo("senderId", receiverId),
                        Filter.equalTo("receiverId", senderId)
                    )
                )
            )
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val messages = it.documents.mapNotNull { doc -> doc.toObject(Message::class.java) }
                    onResult(messages)
                }
            }

    }

    override fun getAllMessages(messageStatus: (Boolean) -> Unit){
        firestore.collection("chats")
            .get()
            .addOnSuccessListener { documents ->
                val messageList = documents.mapNotNull { it.toObject(MessageEntity::class.java) }
                CoroutineScope(Dispatchers.IO).launch {
                    chatsDao.insertChats(messageList) // Save to Room DB
                    messageStatus(true)
                }
            }
            .addOnFailureListener { e ->
                Log.e("CHATME","Firestore Fetch Failed: ${e.message}")
                messageStatus(false)
            }
    }

    override suspend fun getLastMessageBetweenUsers(user2: String): MessageEntity {
        auth.currentUser?.uid?.let {
            return chatsDao.getLastMessageBetweenUsers(it,user2)
        }
        return MessageEntity()
    }
}