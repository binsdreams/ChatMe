package com.chatapp.demo.data

import com.chatapp.demo.domain.Message
import com.chatapp.demo.domain.repo.ChatRepository
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

// Repository for Firebase Operations
class ChatRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    ChatRepository {

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
}