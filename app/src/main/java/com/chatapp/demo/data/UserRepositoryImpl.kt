package com.chatapp.demo.data

import androidx.lifecycle.LiveData
import com.chatapp.demo.data.db.UserDao
import com.chatapp.demo.data.db.UserEntity
import com.chatapp.demo.domain.Message
import com.chatapp.demo.domain.User
import com.chatapp.demo.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Repository for Firebase Operations
class UserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore,
                                             private val userDao: UserDao) : UserRepository {


    override fun getAllUsers(): LiveData<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    private val auth = FirebaseAuth.getInstance()

    override fun getUsers() {
        firestore.collection("users").get().addOnSuccessListener { result ->
            val users = result.documents.mapNotNull { it.toObject(UserEntity::class.java) }
//            onResult(users.filter { it.uid != auth.currentUser?.uid }) // Exclude the current user
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertUsers(users.filter { it.uid != auth.currentUser?.uid })
            }
//            getLatestMessages(users.filter { it.uid != auth.currentUser?.uid }) {
//                onResult(it)
//            }
        }
    }

    private fun getLatestMessages(senders: List<User>, callback: (List<User>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val chatCollection = db.collection("chats")

        val userAId = auth.currentUser?.uid ?: return
        val senderIds = senders.map { it.uid } // Extract sender IDs

        chatCollection
            .whereIn("senderId", senderIds + userAId)
            .whereIn("receiverId", senderIds + userAId)
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort newest first
            .get()
            .addOnSuccessListener { documents ->
                val latestMessages = documents
                    .mapNotNull { doc ->
                        val senderId = doc.getString("senderId") ?: return@mapNotNull null
                        val receiverId = doc.getString("receiverId") ?: return@mapNotNull null
                        val messageText = doc.getString("message") ?: "No message"
                        val timestamp = doc.getLong("timestamp") ?: 0L

                        // Identify the conversation partner (the other user)
                        val chatPartnerId = if (senderId == userAId) receiverId else senderId

                        chatPartnerId to Message(senderId=chatPartnerId, receiverId = auth.currentUser?.uid?:"",
                            message = messageText, timestamp=timestamp)
                    }
                    .distinctBy { it.first } // Keep only the first message per user
                    .associate { it.first to it.second } // Convert to Map<chatPartnerId, Message>

                // Update users with their latest messages
                val updatedUsers = senders.toMutableList().onEach { user ->
                    user.message = latestMessages[user.uid]
                }

                callback(updatedUsers)
            }
            .addOnFailureListener { e ->
                println("Error fetching messages: ${e.message}")
                callback(senders) // Return users without messages on failure
            }
    }
}