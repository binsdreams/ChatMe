package com.chatapp.demo.domain

// Data Model for User
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val online: Boolean = false
)

// Data Model for Messages
data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
