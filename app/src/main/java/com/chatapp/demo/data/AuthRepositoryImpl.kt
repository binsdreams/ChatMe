package com.chatapp.demo.data

import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

// Repository for Firebase Operations
class AuthRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    AuthRepository {

   private val auth = FirebaseAuth.getInstance()

    override fun loginUser(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(auth.currentUser?.uid ?: "", name, email, phone, true)
                    firestore.collection("users").document(user.uid).set(user)
                        .addOnSuccessListener { onComplete(true, null) }
                        .addOnFailureListener { onComplete(false, it.message) }
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
}