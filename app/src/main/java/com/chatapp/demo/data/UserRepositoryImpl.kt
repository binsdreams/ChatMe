package com.chatapp.demo.data

import com.chatapp.demo.domain.User
import com.chatapp.demo.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

// Repository for Firebase Operations
class UserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : UserRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun getUsers(onResult: (List<User>) -> Unit) {
        firestore.collection("users").get().addOnSuccessListener { result ->
            val users = result.documents.mapNotNull { it.toObject(User::class.java) }
            onResult(users.filter { it.uid != auth.currentUser?.uid }) // Exclude the current user
        }
    }
}