package com.chatapp.demo.domain.repo

interface AuthRepository {

    fun loginUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit)
    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    )
    fun isUserLoggedIn() :Boolean

    fun clearAndLogout()
}