package com.chatapp.demo.data

import androidx.lifecycle.LiveData
import com.chatapp.demo.data.cache.CacheManager
import com.chatapp.demo.data.db.UserDao
import com.chatapp.demo.data.db.UserEntity
import com.chatapp.demo.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Repository for Firebase Operations
class UserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore,
                                             private val userDao: UserDao,private val cacheManager: CacheManager) : UserRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun getAllUsers(): LiveData<List<UserEntity>> {
        return userDao.getAllUsers(auth.currentUser?.uid?:"")
    }

    override fun getUsers() {
        firestore.collection("users").get().addOnSuccessListener { result ->
            val users = result.documents.mapNotNull { it.toObject(UserEntity::class.java) }
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertUsers(users.filter { it.uid != auth.currentUser?.uid })
            }
        }
    }


    override fun clearAndLogout() {
        cacheManager.clear()
    }
}